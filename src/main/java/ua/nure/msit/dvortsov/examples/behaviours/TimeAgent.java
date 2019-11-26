package ua.nure.msit.dvortsov.examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;

/**
 * This example shows the usage of the behaviours that allow scheduling actions
 * at a given point in time: <code>WakerBehaviour</code> and
 * <code>TickerBehaviour</code>.
 * More in details this agent executes a <code>TickerBehaviour</code>
 * that prints the agent name every second and a <code>WakerBehaviour</code> ]
 * that kill the agent after 10 seconds.
 *
 * @author Giovanni Caire - TILAB
 */
public class TimeAgent extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");

        // Add the TickerBehaviour (period 1 sec)
        addBehaviour(new TickerBehaviour(this, 1000) {
            protected void onTick() {
                System.out.println("Agent " + myAgent.getLocalName() + ": tick=" + getTickCount());
            }
        });

        // Add the WakerBehaviour (wakeup-time 10 secs)
        addBehaviour(new WakerBehaviour(this, 10000) {
            protected void handleElapsedTimeout() {
                System.out.println("Agent " + myAgent.getLocalName() + ": It's wakeup-time. Bye...");
                myAgent.doDelete();
            }
        });
    }
}
