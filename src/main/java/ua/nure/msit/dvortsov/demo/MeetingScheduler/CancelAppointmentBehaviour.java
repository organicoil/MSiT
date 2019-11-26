package ua.nure.msit.dvortsov.demo.MeetingScheduler;

import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ua.nure.msit.dvortsov.demo.MeetingScheduler.Ontology.Appointment;

/**
 * This behaviour serves all CANCEL messages received by the agent.
 *
 * @author Fabio Bellifemine - CSELT S.p.A
 * @version $Date: 2003-03-19 16:07:33 +0100 (mer, 19 mar 2003) $ $Revision: 3843 $
 */
public class CancelAppointmentBehaviour extends CyclicBehaviour {

    private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CANCEL);
    private ACLMessage cancel;
    private MeetingSchedulerAgent myAgent;

    CancelAppointmentBehaviour(MeetingSchedulerAgent a) {
        super(a);
        myAgent = a;
    }

    public void action() {
        cancel = myAgent.receive(mt);
        if (cancel == null) {
            block();
            return;
        }
        //System.err.println("CancelAppointmentBehaviour: received "+cancel.toString());
        try {
            Appointment app = myAgent.extractAppointment(cancel);
            if (app.getInviter().equals(myAgent.getAID()))
                // I called the appointment and I have to inform other agents of that
                myAgent.cancelAppointment(app.getFixedDate());
            else
                myAgent.removeMyAppointment(app);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}


