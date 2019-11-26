package ua.nure.msit.dvortsov.demo.MeetingScheduler.Ontology;

import jade.content.Concept;
import jade.core.AID;

/**
 * @author Fabio Bellifemine - CSELT S.p.A
 * @version $Date: 2003-03-19 16:07:33 +0100 (mer, 19 mar 2003) $ $Revision: 3843 $
 */

public class Person implements Concept {
    String name;   // name of the person
    AID dfName; // name of the DF with which this person is known
    AID aid;   // aid of the agent

    // used by the Ontology support //
    public Person() {
    }

    public Person(String userName) {
        this(userName, new AID(userName, AID.ISLOCALNAME), new AID("unkwnown", AID.ISLOCALNAME));
    }

    public Person(String userName, AID agentName, AID dfName) {
        name = userName;
        this.dfName = dfName;
        aid = agentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public void setDFName(AID df) {
        dfName = df;
    }

    public AID getDFName() {
        return dfName;
    }

    /**
     * This method returns the AID of the agent corresponding to this person
     **/
    public AID getAID() {
        return aid;
    }

    public void setAID(AID n) {
        aid = n;
    }

    public String toString() {
        return "Mr./Mrs. " + name + " - " + aid.toString() + " registered with DF " + dfName.getName();
    }

}
