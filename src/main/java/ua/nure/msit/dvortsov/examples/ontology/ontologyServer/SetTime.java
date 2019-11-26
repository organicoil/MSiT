package ua.nure.msit.dvortsov.examples.ontology.ontologyServer;

import jade.content.AgentAction;

import java.util.Date;

public class SetTime implements AgentAction {
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
