package ua.nure.msit.dvortsov.bookTrading.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookBuyerAgent extends Agent {

    private static final Logger LOG = LoggerFactory.getLogger(BookBuyerAgent.class);

    private static final int TICKER_PERIOD = 2000;

    @Override
    protected void setup() {
        LOG.debug("Buyer-agent {} is ready.", getAID().getName());

        String targetBookTitle = getBookToBuyTitle();
        if (targetBookTitle == null) {
            LOG.debug("No target book title specified");
            doDelete();
        }

        addTickerBehaviour(targetBookTitle);
    }

    private String getBookToBuyTitle() {
        Object[] arguments = getArguments();
        if (arguments == null || arguments.length == 0) {
            return null;
        }

        String targetBookTitle = (String) arguments[0];
        LOG.debug("Target book is {}", targetBookTitle);
        return targetBookTitle;
    }

    private void addTickerBehaviour(String targetBookTitle) {
        addBehaviour(new TickerBehaviour(this, TICKER_PERIOD) {
            protected void onTick() {
                LOG.debug("Trying to buy {}", targetBookTitle);

                DFAgentDescription template = getTemplate();
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    List<AID> sellerAgents = Arrays.stream(result)
                                                   .map(DFAgentDescription::getName)
                                                   .collect(Collectors.toList());
                    LOG.debug("Found {} seller agents", sellerAgents.size());
                    myAgent.addBehaviour(new RequestPerformer(targetBookTitle, sellerAgents));
                } catch (FIPAException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        });
    }

    private DFAgentDescription getTemplate() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("book-selling");
        template.addServices(serviceDescription);

        return template;
    }

    @Override
    protected void takeDown() {
        LOG.debug("Buyer-agent {} terminating.", getAID().getName());
    }

    private class RequestPerformer extends Behaviour {

        private String targetBookTitle;
        private List<AID> sellerAgents;

        public RequestPerformer(String targetBookTitle, List<AID> sellerAgents) {
            this.targetBookTitle = targetBookTitle;
            this.sellerAgents = sellerAgents;
        }

        private AID bestSeller;
        private int bestPrice;
        private int repliesCnt = 0;
        private MessageTemplate mt;
        private int step = 0;

        public void action() {
            switch (step) {
                case 0:
                    // Send the cfp to all sellers
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (AID sellerAgent : sellerAgents) {
                        cfp.addReceiver(sellerAgent);
                    }
                    cfp.setContent(targetBookTitle);
                    cfp.setConversationId("book-trade");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);
                    // Prepare the template to get proposals
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    LOG.trace("Finished step 0 of BookBuyerAgent.RequestPerformer");
                    break;
                case 1:
                    // Receive all proposals/refusals from seller agents
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            // This is an offer 
                            int price = Integer.parseInt(reply.getContent());
                            if (bestSeller == null || price < bestPrice) {
                                // This is the best offer at present
                                bestPrice = price;
                                bestSeller = reply.getSender();
                            }
                        }
                        repliesCnt++;
                        if (repliesCnt >= sellerAgents.size()) {
                            // We received all replies
                            step = 2;
                            LOG.trace("Finished step 1 of BookBuyerAgent.RequestPerformer");
                        }
                    } else {
                        block();
                        LOG.trace("Failed to finish step 1 of BookBuyerAgent.RequestPerformer");
                    }
                    break;
                case 2:
                    // Send the purchase order to the seller that provided the best offer
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    order.addReceiver(bestSeller);
                    order.setContent(targetBookTitle);
                    order.setConversationId("book-trade");
                    order.setReplyWith("order" + System.currentTimeMillis());
                    myAgent.send(order);
                    // Prepare the template to get the purchase order reply
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
                            MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = 3;
                    LOG.trace("Finished step 2 of BookBuyerAgent.RequestPerformer");
                    break;
                case 3:
                    // Receive the purchase order reply
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Purchase order reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            // Purchase successful. We can terminate
                            LOG.debug("{} successfully purchased from agent {}", targetBookTitle, reply.getSender().getName());
                            LOG.debug("Price = {}", bestPrice);
                            myAgent.doDelete();
                        } else {
                            LOG.debug("Attempt failed: requested book already sold.");
                        }

                        step = 4;
                        LOG.trace("Finished step 3 of BookBuyerAgent.RequestPerformer");
                    } else {
                        block();
                        LOG.trace("Failed to finish step 3 of BookBuyerAgent.RequestPerformer");
                    }
                    break;
            }
        }

        public boolean done() {
            LOG.trace("Checking if BookBuyerAgent.RequestPerformer is done, step = " + step);
            if (step == 2 && bestSeller == null) {
                LOG.debug("Attempt failed: {} not available for sale", targetBookTitle);
            }
            return ((step == 2 && bestSeller == null) || step == 4);
        }
    }

}
