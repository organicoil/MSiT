package ua.nure.msit.dvortsov.examples.content;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsContentElement;
import jade.content.abs.AbsIRE;
import jade.content.abs.AbsPredicate;
import jade.content.lang.Codec;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.msit.dvortsov.examples.content.ontology.FatherOf;
import ua.nure.msit.dvortsov.examples.content.ontology.PeopleOntology;

public class Receiver extends Agent {
    private ContentManager manager = getContentManager();
    private Codec codec = new LEAPCodec();
    private Ontology ontology = PeopleOntology.getInstance();
    private FatherOf proposition = null;

    class ReceiverBehaviour extends SimpleBehaviour {
        private boolean finished = false;

        public ReceiverBehaviour(Agent a) {
            super(a);
        }

        public boolean done() {
            return finished;
        }

        public void action() {
            for (int c = 0; c < 2; c++) {
                try {
                    System.out.println("[" + getLocalName() + "] Waiting for a message...");

                    ACLMessage msg = blockingReceive();

                    if (msg != null) {
                        switch (msg.getPerformative()) {
                            case ACLMessage.INFORM:
                                ContentElement p = manager.extractContent(msg);
                                if (p instanceof FatherOf) {
                                    proposition = (FatherOf) p;
                                    System.out.println("[" + getLocalName() + "] Receiver inform message: information stored.");
                                    break;
                                }
                            case ACLMessage.QUERY_REF:
                                AbsContentElement abs = manager.extractAbsContent(msg);
                                if (abs instanceof AbsIRE) {
                                    AbsIRE ire = (AbsIRE) abs;

                                    ACLMessage reply = msg.createReply();
                                    reply.setPerformative(ACLMessage.INFORM);

                                    AID sender = new AID("sender", false);

                                    reply.setSender(getAID());
                                    reply.addReceiver(sender);
                                    reply.setLanguage(codec.getName());
                                    reply.setOntology(ontology.getName());

                                    AbsConcept absFather = (AbsConcept) ontology.fromObject(proposition.getFather());

                                    AbsPredicate absEquals = new AbsPredicate(BasicOntology.EQUALS);
                                    absEquals.set(BasicOntology.EQUALS_LEFT, ire);
                                    absEquals.set(BasicOntology.EQUALS_RIGHT, absFather);

                                    manager.fillContent(reply, absEquals);

                                    send(reply);

                                    System.out.println("[" + getLocalName() + "] Received query-ref message: reply sent:");
                                    System.out.println(absEquals);
                                    break;
                                }
                            default:
                                System.out.println("[" + getLocalName() + "] Malformed message.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            finished = true;
        }
    }

    protected void setup() {
        manager.registerLanguage(codec);
        manager.registerOntology(ontology);

        addBehaviour(new ReceiverBehaviour(this));
    }
}
