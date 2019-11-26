package ua.nure.msit.dvortsov.examples.protocols;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

import java.util.Date;
import java.util.Vector;

/**
 * This example shows how to implement the initiator role in
 * a FIPA-request interaction protocol. In this case in particular
 * we use an <code>AchieveREInitiator</code> ("Achieve Rational effect")
 * to request a dummy action to a number of agents (whose local
 * names must be specified as arguments).
 *
 * @author Giovanni Caire - TILAB
 */
public class FIPARequestInitiatorAgent extends Agent {
    private int nResponders;

    protected void setup() {
        // Read names of responders as arguments
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            nResponders = args.length;
            System.out.println("Requesting dummy-action to " + nResponders + " responders.");

            // Fill the REQUEST message
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            for (int i = 0; i < args.length; ++i) {
                msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
            }
            msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            // We want to receive a reply in 10 secs
            msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
            msg.setContent("dummy-action");

            addBehaviour(new AchieveREInitiator(this, msg) {
                protected void handleInform(ACLMessage inform) {
                    System.out.println("Agent " + inform.getSender().getName() + " successfully performed the requested action");
                }

                protected void handleRefuse(ACLMessage refuse) {
                    System.out.println("Agent " + refuse.getSender().getName() + " refused to perform the requested action");
                    nResponders--;
                }

                protected void handleFailure(ACLMessage failure) {
                    if (failure.getSender().equals(myAgent.getAMS())) {
                        // FAILURE notification from the JADE runtime: the receiver
                        // does not exist
                        System.out.println("Responder does not exist");
                    } else {
                        System.out.println("Agent " + failure.getSender().getName() + " failed to perform the requested action");
                    }
                }

                protected void handleAllResultNotifications(Vector notifications) {
                    if (notifications.size() < nResponders) {
                        // Some responder didn't reply within the specified timeout
                        System.out.println("Timeout expired: missing " + (nResponders - notifications.size()) + " responses");
                    }
                }
            });
        } else {
            System.out.println("No responder specified.");
        }
    }
}

