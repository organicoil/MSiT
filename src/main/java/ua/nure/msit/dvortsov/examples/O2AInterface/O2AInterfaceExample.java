package ua.nure.msit.dvortsov.examples.O2AInterface;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 * This class shows an example of how to run JADE as a library from an external program
 * and in particular how to start an agent and interact with it by  means of the
 * Object-to-Agent (O2A) interface.
 *
 * @author Giovanni Iavarone - Michele Izzo
 */

public class O2AInterfaceExample {

    public static void main(String[] args) throws StaleProxyException, InterruptedException {
        // Get a hold to the JADE runtime
        Runtime rt = Runtime.instance();

        // Launch the Main Container (with the administration GUI on top) listening on port 8888
        System.out.println(">>>>>>>>>>>>>>> Launching the platform Main Container...");
        Profile pMain = new ProfileImpl(null, 8888, null);
        pMain.setParameter(Profile.GUI, "true");
        ContainerController mainCtrl = rt.createMainContainer(pMain);

        // Create and start an agent of class CounterAgent
        System.out.println(">>>>>>>>>>>>>>> Starting up a CounterAgent...");
        AgentController agentCtrl = mainCtrl.createNewAgent("CounterAgent", CounterAgent.class.getName(), new Object[0]);
        agentCtrl.start();

        // Wait a bit
        System.out.println(">>>>>>>>>>>>>>> Wait a bit...");
        Thread.sleep(10000);

        try {
            // Retrieve O2A interface CounterManager1 exposed by the agent to make it activate the counter
            System.out.println(">>>>>>>>>>>>>>> Activate counter");
            CounterManager1 o2a1 = agentCtrl.getO2AInterface(CounterManager1.class);
            o2a1.activateCounter();

            // Wait a bit
            System.out.println(">>>>>>>>>>>>>>> Wait a bit...");
            Thread.sleep(30000);

            // Retrieve O2A interface CounterManager2 exposed by the agent to make it de-activate the counter
            System.out.println(">>>>>>>>>>>>>>> Deactivate counter");
            CounterManager2 o2a2 = agentCtrl.getO2AInterface(CounterManager2.class);
            o2a2.deactivateCounter();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}