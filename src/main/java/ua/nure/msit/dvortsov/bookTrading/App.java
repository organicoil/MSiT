package ua.nure.msit.dvortsov.bookTrading;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import ua.nure.msit.dvortsov.bookTrading.agent.BookBuyerAgent;
import ua.nure.msit.dvortsov.bookTrading.agent.BookSellerAgent;
import ua.nure.msit.dvortsov.bookTrading.dto.BookBuyerAgentConfig;

import static jade.core.Runtime.instance;

public class App {

    public static void main(String[] args) throws StaleProxyException {
        Properties properties = new Properties();
        properties.setProperty(Profile.GUI, Boolean.TRUE.toString());

        Profile profile = new ProfileImpl(properties);
        AgentContainer agentContainer = instance().createMainContainer(profile);

        BookBuyerAgentConfig[] buyerArgs = {new BookBuyerAgentConfig("Title")};
        agentContainer.createNewAgent(BookBuyerAgent.class.getSimpleName(), BookBuyerAgent.class.getName(), buyerArgs).start();

        agentContainer.acceptNewAgent(BookSellerAgent.class.getSimpleName(), new BookSellerAgent()).start();
    }

}
