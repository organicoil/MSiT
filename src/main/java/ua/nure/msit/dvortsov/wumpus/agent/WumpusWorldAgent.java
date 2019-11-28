package ua.nure.msit.dvortsov.wumpus.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ua.nure.msit.dvortsov.wumpus.dto.Coords;
import ua.nure.msit.dvortsov.wumpus.dto.Room;
import ua.nure.msit.dvortsov.wumpus.util.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class WumpusWorldAgent extends Agent {

    public static String SERVICE_DESCRIPTION = "WUMPUS-WORLD";

    private static int START = -1;
    //    private static int EMPTY = 0;
    private static int WAMPUS = 1;
    private static int PIT = 2;
    private static int BREEZE = 3;
    private static int STENCH = 4;
    private static int SCREAM = 5;
    private static int GOLD = 6;
    private static int BUMP = 7;

    public static HashMap<Integer, String> roomCodes = new HashMap<Integer, String>() {{
        put(START, Const.Room.Events.START);
        put(WAMPUS, Const.Room.Events.WUMPUS);
        put(PIT, Const.Room.Events.PIT);
        put(BREEZE, Const.Room.Events.BREEZE);
        put(STENCH, Const.Room.Events.STENCH);
        put(SCREAM, Const.Room.Events.SCREAM);
        put(GOLD, Const.Room.Events.GOLD);
        put(BUMP, Const.Room.Events.BUMP);
    }};
    private static int NUM_OF_ROWS = 4;
    private static int NUM_OF_COLUMNS = 4;

    private Room[][] wampusMap;
    private HashMap<AID, Coords> Speleologists;

    String nickname = "WampusWorld";
    AID id = new AID(nickname, AID.ISLOCALNAME);

    @Override
    protected void setup() {
        System.out.println("WumpusWorldAgent: WampusWorld-agent " + getAID().getName() + " is ready.");
        Speleologists = new HashMap<>();
        generateMap();
        showMap();
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(SpeleologistAgent.WUMPUS_WORLD_TYPE);
        sd.setName(SERVICE_DESCRIPTION);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        addBehaviour(new SpeleologistConnectPerformer());
        addBehaviour(new SpeleologistArrowPerformer());
        addBehaviour(new SpeleologistGoldPerformer());
        addBehaviour(new SpeleologistMovePerformer());
    }

    private void generateMap() {
        wampusMap = new Room[NUM_OF_ROWS][NUM_OF_COLUMNS];

        wampusMap[0][0] = new Room();
        wampusMap[0][1] = new Room(BREEZE);
        wampusMap[0][2] = new Room(PIT);
        wampusMap[0][3] = new Room(BREEZE);

        wampusMap[1][0] = new Room(STENCH);
        wampusMap[1][1] = new Room();
        wampusMap[1][2] = new Room(BREEZE);
        wampusMap[1][3] = new Room();

        wampusMap[2][0] = new Room(WAMPUS);
        wampusMap[2][1] = new Room(BREEZE, GOLD, STENCH);
        wampusMap[2][2] = new Room(PIT);
        wampusMap[2][3] = new Room(BREEZE);

        wampusMap[3][0] = new Room(STENCH);
        wampusMap[3][1] = new Room();
        wampusMap[3][2] = new Room(BREEZE);
        wampusMap[3][3] = new Room(PIT);
    }

    private void showMap() {
        System.out.println("WumpusWorldAgent: MAP:");
        for (Room[] rooms : this.wampusMap) {
            for (Room room : rooms) {
                System.out.print(String.format("%24s", room.getEvents()));
            }
            System.out.println();
        }
    }

    private class SpeleologistConnectPerformer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String message = msg.getContent();
                if (Objects.equals(message, SpeleologistAgent.GO_INSIDE)) {
                    AID current_Speleologist = msg.getSender();
                    Coords Speleologist_coords = Speleologists.get(current_Speleologist);
                    if (Speleologist_coords == null) {
                        Speleologists.put(current_Speleologist, new Coords(0, 0));
                    } else {
                        Speleologists.put(current_Speleologist, new Coords(0, 0));
                    }
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.CONFIRM);
                    reply.setContent(wampusMap[0][0].getEvents().toString());
                    myAgent.send(reply);
                }
//
            } else {
                block();
            }
        }
    }

    private class SpeleologistArrowPerformer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(SpeleologistAgent.SHOOT_ARROW);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(SpeleologistAgent.SHOOT_ARROW);

                String message = msg.getContent();
                AID current_Speleologist = msg.getSender();
                Coords Speleologist_coords = Speleologists.get(current_Speleologist);

                int row = Speleologist_coords.getRow();
                int column = Speleologist_coords.getColumn();
                String answer = "";
                if (message.equals(SpeleologistAgent.actionCodes.get(SpeleologistAgent.LOOK_DOWN))) {
                    for (int i = 0; i < row; ++i) {
                        if (wampusMap[i][column].getEvents().contains(WumpusWorldAgent.roomCodes.get(WAMPUS))) {
                            answer = Const.Room.Events.SCREAM;
                        }
                    }
                } else if (message.equals(SpeleologistAgent.actionCodes.get(SpeleologistAgent.LOOK_UP))) {
                    for (int i = row + 1; i < NUM_OF_ROWS; ++i) {
                        if (wampusMap[i][column].getEvents().contains(WumpusWorldAgent.roomCodes.get(WAMPUS))) {
                            answer = Const.Room.Events.SCREAM;
                        }
                    }
                } else if (message.equals(SpeleologistAgent.actionCodes.get(SpeleologistAgent.LOOK_LEFT))) {
                    for (int i = 0; i < column; ++i) {
                        if (wampusMap[row][i].getEvents().contains(WumpusWorldAgent.roomCodes.get(WAMPUS))) {
                            answer = Const.Room.Events.SCREAM;
                        }
                    }
                } else if (message.equals(SpeleologistAgent.actionCodes.get(SpeleologistAgent.LOOK_RIGHT))) {
                    for (int i = column + 1; i < NUM_OF_COLUMNS; ++i) {
                        if (wampusMap[row][i].getEvents().contains(WumpusWorldAgent.roomCodes.get(WAMPUS))) {
                            answer = Const.Room.Events.SCREAM;
                        }
                    }
                }

                reply.setContent(answer);

                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    private class SpeleologistMovePerformer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(SpeleologistAgent.MOVE);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(SpeleologistAgent.MOVE);

                String message = msg.getContent();
                AID current_Speleologist = msg.getSender();
                Coords speleologistCoords = Speleologists.get(current_Speleologist);
                System.out.println("WumpusWorldAgent: World say: Current agent coords: " + speleologistCoords.getRow() + " | " + speleologistCoords.getColumn());
                if (speleologistCoords == null) {
                    Speleologists.put(current_Speleologist, new Coords(0, 0));
                    speleologistCoords = Speleologists.get(current_Speleologist);
                }
                int row = speleologistCoords.getRow();
                int column = speleologistCoords.getColumn();
                if (message.equals(SpeleologistAgent.actionCodes.get(SpeleologistAgent.LOOK_DOWN))) {
                    row -= 1;
                } else if (message.equals(SpeleologistAgent.actionCodes.get(SpeleologistAgent.LOOK_UP))) {
                    row += 1;
                } else if (message.equals(SpeleologistAgent.actionCodes.get(SpeleologistAgent.LOOK_LEFT))) {
                    column -= 1;
                } else if (message.equals(SpeleologistAgent.actionCodes.get(SpeleologistAgent.LOOK_RIGHT))) {
                    column += 1;
                }
                if (row > -1 && column > -1 && row < NUM_OF_ROWS && column < NUM_OF_COLUMNS) {
                    speleologistCoords.setColumn(column);
                    speleologistCoords.setRow(row);
                    reply.setContent(wampusMap[row][column].getEvents().toString());
                } else {
                    reply.setContent(String.valueOf(new ArrayList<String>() {{
                        add(Const.Room.Events.BUMP);
                    }}));
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    private class SpeleologistGoldPerformer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(SpeleologistAgent.TAKE_GOLD);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String message = msg.getContent();
                AID current_Speleologist = msg.getSender();
                Coords Speleologist_coords = Speleologists.get(current_Speleologist);
                if (Speleologist_coords == null) {
                    Speleologists.put(current_Speleologist, new Coords(0, 0));
                } else {
                    if (wampusMap[Speleologist_coords.getRow()][Speleologist_coords.getColumn()].getEvents().contains(WumpusWorldAgent.roomCodes.get(GOLD))) {
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(SpeleologistAgent.TAKE_GOLD);
                        reply.setContent("GOLD");
                        myAgent.send(reply);
                    }
                }
            } else {
                block();
            }
        }
    }
}
