package ua.nure.msit.dvortsov.examples.mobile;

import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;

/*
 * This behaviour extends SimpleAchieveREInitiator in order
 * to request to the AMS the list of available locations where
 * the agent can move.
 * Then, it displays these locations into the GUI
 * @author Fabio Bellifemine - CSELT S.p.A.
 * @version $Date: 2003-02-25 13:29:42 +0100 (mar, 25 feb 2003) $ $Revision: 3687 $
 */
public class GetAvailableLocationsBehaviour extends SimpleAchieveREInitiator {

    private ACLMessage request;

    public GetAvailableLocationsBehaviour(MobileAgent a) {
        // call the constructor of FipaRequestInitiatorBehaviour
        super(a, new ACLMessage(ACLMessage.REQUEST));
        request = (ACLMessage) getDataStore().get(REQUEST_KEY);
        // fills all parameters of the request ACLMessage
        request.clearAllReceiver();
        request.addReceiver(a.getAMS());
        request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
        request.setOntology(MobilityOntology.NAME);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        // creates the content of the ACLMessage
        try {
            Action action = new Action();
            action.setActor(a.getAMS());
            action.setAction(new QueryPlatformLocationsAction());
            a.getContentManager().fillContent(request, action);
        } catch (Exception fe) {
            fe.printStackTrace();
        }
        // creates the Message Template
        // template = MessageTemplate.and(MessageTemplate.MatchOntology(MobilityOntology.NAME),template);
        // reset the fiparequestinitiatorbheaviour in order to put new values
        // for the request aclmessage and the template
        reset(request);
    }

    protected void handleNotUnderstood(ACLMessage reply) {
        System.out.println(myAgent.getLocalName() + " handleNotUnderstood : " + reply.toString());
    }

    protected void handleRefuse(ACLMessage reply) {
        System.out.println(myAgent.getLocalName() + " handleRefuse : " + reply.toString());
    }

    protected void handleFailure(ACLMessage reply) {
        System.out.println(myAgent.getLocalName() + " handleFailure : " + reply.toString());
    }

    protected void handleAgree(ACLMessage reply) {
    }

    protected void handleInform(ACLMessage inform) {
        String content = inform.getContent();
        //System.out.println(inform.toString());
        try {
            Result results = (Result) myAgent.getContentManager().extractContent(inform);
            //update the GUI
            ((MobileAgent) myAgent).gui.updateLocations(results.getItems().iterator());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
