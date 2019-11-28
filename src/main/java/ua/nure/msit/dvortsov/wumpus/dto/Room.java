package ua.nure.msit.dvortsov.wumpus.dto;

import ua.nure.msit.dvortsov.wumpus.agent.WumpusWorldAgent;

import java.util.ArrayList;

public class Room {

    private ArrayList<String> events = new ArrayList<>();

    public Room(int... args) {
        for (int i : args) {
            events.add(WumpusWorldAgent.roomCodes.get(i));
        }
    }

    public ArrayList<String> getEvents() {
        return events;
    }

}
