package ua.nure.msit.dvortsov.bookTrading.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.msit.dvortsov.bookTrading.ui.BookSellerGui;
import ua.nure.msit.dvortsov.bookTrading.util.Const;

import java.util.Hashtable;

public class BookSellerAgent extends Agent {

    private static final Logger LOG = LoggerFactory.getLogger(BookSellerAgent.class);

    // The catalogue of books for sale (maps the title of a book to its price)
    private Hashtable catalogue;
    // The GUI by means of which the user can add books in the catalogue
    private BookSellerGui bookSellerGui;

    // Put agent initializations here
    protected void setup() {
        // Create the catalogue
        catalogue = new Hashtable();

        // Create and show the GUI
        bookSellerGui = new BookSellerGui(this);
        bookSellerGui.start();

        // Register the book-selling service in the yellow pages
        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        dfAgentDescription.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(Const.BOOK_SELLING_SERVICE_TYPE);
        serviceDescription.setName(Const.BOOK_SELLING_SERVICE_NAME);
        dfAgentDescription.addServices(serviceDescription);
        try {
            DFService.register(this, dfAgentDescription);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        // Add the behaviour serving queries from buyer agents
        addBehaviour(new OfferRequestsServer());

        // Add the behaviour serving purchase orders from buyer agents
        addBehaviour(new PurchaseOrdersServer());
    }

    // Put agent clean-up operations here
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        // Close the GUI
        bookSellerGui.dispose();
        // Printout a dismissal message
        System.out.println("Seller-agent " + getAID().getName() + " terminating.");
    }

    /**
     * This is invoked by the GUI when the user adds a new book for sale
     */
    public void updateCatalogue(final String title, final int price) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                catalogue.put(title, new Integer(price));
                System.out.println(title + " inserted into catalogue. Price = " + price);
            }
        });
    }

    /**
     * Inner class OfferRequestsServer.
     * This is the behaviour used by Book-seller agents to serve incoming requests
     * for offer from buyer agents.
     * If the requested book is in the local catalogue the seller agent replies
     * with a PROPOSE message specifying the price. Otherwise a REFUSE message is
     * sent back.
     */
    private class OfferRequestsServer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage aclMessage = myAgent.receive(mt);
            if (aclMessage != null) {
                // CFP Message received. Process it
                String title = aclMessage.getContent();
                ACLMessage reply = aclMessage.createReply();

                Integer price = (Integer) catalogue.get(title);
                if (price != null) {
                    // The requested book is available for sale. Reply with the price
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(String.valueOf(price.intValue()));
                } else {
                    // The requested book is NOT available for sale.
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    /**
     * Inner class PurchaseOrdersServer.
     * This is the behaviour used by Book-seller agents to serve incoming
     * offer acceptances (i.e. purchase orders) from buyer agents.
     * The seller agent removes the purchased book from its catalogue
     * and replies with an INFORM message to notify the buyer that the
     * purchase has been sucesfully completed.
     */
    private class PurchaseOrdersServer extends CyclicBehaviour {
        public void action() {
            MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage aclMessage = myAgent.receive(messageTemplate);
            if (aclMessage != null) {
                // ACCEPT_PROPOSAL Message received. Process it
                String title = aclMessage.getContent();
                ACLMessage reply = aclMessage.createReply();

                Integer price = (Integer) catalogue.remove(title);
                if (price != null) {
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println(title + " sold to agent " + aclMessage.getSender().getName());
                } else {
                    // The requested book has been sold to another buyer in the meanwhile .
                    reply.setPerformative(ACLMessage.FAILURE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

}
