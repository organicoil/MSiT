package ua.nure.msit.dvortsov.examples.messaging;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * This example shows how to create a custom <code>MessageTemplate</code>.
 * In this case we define a template matching only REQUEST messages where
 * the ontology starts with "X".
 *
 * @author Giovanni Caire - TILAB
 */
public class CustomTemplateAgent extends Agent {

    /**
     * Inner class MatchXOntology
     */
    private class MatchXOntology implements MessageTemplate.MatchExpression {

        public boolean match(ACLMessage msg) {
            String ontology = msg.getOntology();
            return (ontology != null && ontology.startsWith("X"));
        }
    } // END of inner class MatchXOntology

    private MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
            new MessageTemplate(new MatchXOntology()));

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " is ready.");

        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = myAgent.receive(template);
                if (msg != null) {
                    System.out.println("Message matching custom template received:");
                    System.out.println(msg);
                } else {
                    block();
                }
            }
        });
    }
}
