package ua.nure.msit.dvortsov.wumpus;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import ua.nure.msit.dvortsov.wumpus.agent.NavigatorAgent;
import ua.nure.msit.dvortsov.wumpus.agent.SpeleologistAgent;
import ua.nure.msit.dvortsov.wumpus.agent.WumpusWorldAgent;

import static jade.core.Runtime.instance;

public class App {

    public static void main(String[] args) throws StaleProxyException {
        Properties properties = new Properties();
        properties.setProperty(Profile.GUI, Boolean.TRUE.toString());

        Profile profile = new ProfileImpl(properties);
        AgentContainer agentContainer = instance().createMainContainer(profile);

        agentContainer.acceptNewAgent(WumpusWorldAgent.class.getSimpleName(), new WumpusWorldAgent()).start();
        agentContainer.acceptNewAgent(NavigatorAgent.class.getSimpleName(), new NavigatorAgent()).start();
        agentContainer.acceptNewAgent(SpeleologistAgent.class.getSimpleName(), new SpeleologistAgent()).start();
    }

}
