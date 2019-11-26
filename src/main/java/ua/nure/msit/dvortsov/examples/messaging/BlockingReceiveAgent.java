package ua.nure.msit.dvortsov.examples.messaging;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * This example shows how to receive messages in blocking mode
 * by means of the <code>blockingReceive()</code> method. Note that
 * this method blocks the whole agent. If you call it from within
 * a behaviour you have to take into account that all other behaviours
 * will not be able to run until the call to <code>blockingReceive()</code>
 * returns.
 *
 * @author Giovanni Caire - TILAB
 */
public class BlockingReceiveAgent extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + ": waiting for REQUEST message...");
        ACLMessage msg = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        System.out.println("Agent " + getLocalName() + ": REQUEST message received. Reply and exit.");
        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
        reply.addReceiver(msg.getSender());
        reply.setContent("exiting");
        send(reply);
        doDelete();
    }

    protected void takeDown() {
        System.out.println("Agent " + getLocalName() + ": terminating");
    }
}
