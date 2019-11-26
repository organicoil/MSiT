package ua.nure.msit.dvortsov.examples.messaging;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * This example shows an agent able to respond to other agents wishing
 * to know if it is alive. More in details we use a
 * <code>CyclicBehaviour</code> that receives only messages of type
 * <code>QUERY_IF</code> and using the <code>presence</code> ontology.
 * All other messages are ignored. This is achieved by specifying a
 * proper <code>MessageTemplate</code>. Whenever such a message is
 * received an <code>INFORM</code> message is sent back as reply.
 *
 * @author Giovanni Caire - TILAB
 */
public class PingAgent extends Agent {
    private MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF),
            MessageTemplate.MatchOntology("presence"));

    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = myAgent.receive(template);
                if (msg != null) {
                    System.out.println("Received QUERY_IF message from agent " + msg.getSender().getName());
                    ACLMessage reply = msg.createReply();
                    if ("alive".equals(msg.getContent())) {
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("alive");
                    } else {
                        reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                        reply.setContent("Unknown-content");
                    }
                    myAgent.send(reply);
                } else {
                    block();
                }
            }
        });
    }
}
