package ua.nure.msit.dvortsov.examples.Base64;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

/**
 * This agent makes the following task:
 * 1. registers itself with the df as a reader;
 * 2. waits a message from its companion, the ObjectWriterAgent;
 * 3. reads the content of the message;
 * if the language was set to "JavaSerialization", then
 * the agent knows a-priori that
 * it is encoded in Base64 and contains a Java object. Notice that
 * this is a private a-priori agreement between the Writer and the Reader and
 * it does not comply to any standard.
 * <p>
 * Becase this agent implements a single sequential task, it does not
 * use any Behaviour.
 *
 * @author Fabio Bellifemine - CSELT S.p.A
 * @version $Date: 2001-12-06 11:51:17 +0100 (gio, 06 dic 2001) $ $Revision: 2911 $
 */

public class ObjectReaderAgent extends Agent {

    protected void setup() {
        /** Registration with the DF */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("ObjectReaderAgent");
        sd.setName(getName());
        sd.setOwnership("ExampleOfJADE");
        dfd.addServices(sd);
        dfd.setName(getAID());
        dfd.addOntologies("Test_Example");
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
            doDelete();
        }
        /** End registration with the DF **/
        System.out.println(getLocalName() + " succeeded in registration with DF");

        while (true) {
            try {
                System.out.println(getLocalName() + " is waiting for a message");
                ACLMessage msg = blockingReceive();
                System.out.println(getLocalName() + " rx msg" + msg);

                if ("JavaSerialization".equals(msg.getLanguage())) {
                    Person p = (Person) msg.getContentObject();
                    System.out.println(getLocalName() + " read Java Object " + p.getClass().getName() + p.toString());
                } else
                    System.out.println(getLocalName() + " read Java String " + msg.getContent());

            } catch (UnreadableException e3) {
                System.err.println(getLocalName() + " catched exception " + e3.getMessage());
            }
        }
    }

    public void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " deregistration with DF unsucceeded. Reason: " + e.getMessage());
        }
    }

}
