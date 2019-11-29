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
import ua.nure.msit.dvortsov.wumpus.dto.ImaginaryRoom;
import ua.nure.msit.dvortsov.wumpus.dto.ImaginaryWumpusWorld;
import ua.nure.msit.dvortsov.wumpus.dto.Position;
import ua.nure.msit.dvortsov.wumpus.util.Const;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

public class NavigatorAgent extends Agent {

    private static final String SERVICE_DESCRIPTION = "NAVIGATOR_AGENT";

    private Hashtable<AID, Position> agents_coords;
    private Hashtable<AID, LinkedList<int[]>> agentsWayStory;

    private int agentX;
    private int agentY;

    private ImaginaryWumpusWorld world;

    @Override
    protected void setup() {
        world = new ImaginaryWumpusWorld();
        agentsWayStory = new Hashtable<>();
        agents_coords = new Hashtable<>();

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(SpeleologistAgent.NAVIGATOR_AGENT_TYPE);
        sd.setName(SERVICE_DESCRIPTION);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new LocationRequestsServer());
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("NavigatorAgent: Navigator-agent " + getAID().getName() + " terminating.");
    }

    private class LocationRequestsServer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                AID request_agent = msg.getSender();
                if (agentsWayStory.get(request_agent) == null) {
                    LinkedList<int[]> agentWay = new LinkedList<>();
                    agentsWayStory.put(request_agent, agentWay);
                }
                Position request_agent_position = agents_coords.get(request_agent);
                if (request_agent_position == null) {
                    request_agent_position = new Position();
                    agents_coords.put(request_agent, request_agent_position);
                }
                String location = msg.getContent();
                location = location.substring(1, location.length() - 1);
                String[] room_info = location.split(", ");
                System.out.println("NavigatorAgent: ROOM INFO: " + Arrays.toString(room_info));
                System.out.println("NavigatorAgent: AGENT INFO: " + request_agent_position.getX() + " " + request_agent_position.getY());
                String[] actions = get_actions(request_agent, request_agent_position, room_info);
                ACLMessage reply = msg.createReply();

                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(Arrays.toString(actions));
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    private String[] get_actions(AID request_agent, Position request_agent_position, String[] room_info) {
        System.out.println("NavigatorAgent: Agent pos before: " + request_agent_position.getX() + " | " + request_agent_position.getY());
        int[] actions;
        ImaginaryRoom checking_room = world.getWorldGrid().get(request_agent_position);
        if (checking_room == null) {
            checking_room = new ImaginaryRoom();
            world.getWorldGrid().put(request_agent_position, checking_room);
        }

        if (!Arrays.asList(room_info).contains(Const.Room.Events.BUMP)) {
            LinkedList<int[]> agentStory = agentsWayStory.get(request_agent);
            agentStory.add(new int[]{request_agent_position.getX(), request_agent_position.getY()});
            request_agent_position.setX(agentX);
            request_agent_position.setY(agentY);
            if (world.getWorldGrid().get(request_agent_position).getExist() != Const.Room.Status.TRUE) {
                world.getWorldGrid().get(request_agent_position).setExist(Const.Room.Status.TRUE);
                System.out.println("NavigatorAgent: MARKED THE EXISTENCE");
            }
        } else {
            Position helpPosition = new Position(agentX, agentY);
            world.getWorldGrid().get(helpPosition).setExist(Const.Room.Status.FALSE);
        }
        checking_room = world.getWorldGrid().get(request_agent_position);
        if (checking_room == null) {
            checking_room = new ImaginaryRoom();
            world.getWorldGrid().put(request_agent_position, checking_room);
        }

        if (checking_room.getOk() != Const.Room.Status.TRUE) {
            checking_room.setOk(Const.Room.Status.TRUE);
        }
        for (String event : room_info) {
            checking_room.addEvent(event);
        }
        updateNeighbors(request_agent_position);
        if (world.isWumpusAlive() && world.getWumpusRoomCount() > 2) {
            Position wampusPosition = world.getWumpusCoords();
            actions = getNextRoomAction(request_agent_position, wampusPosition, SpeleologistAgent.SHOOT_ARROW);
        } else {
            Position[] nextOkRooms = getOkNeighbors(request_agent, request_agent_position);
            // TODO: Нужно еще отсечь тех, у кого нет пути к золоту
            int best_candidate = -1;
            int candidate_status = -1;
            for (int i = 0; i < nextOkRooms.length; ++i) {
                Position candidate_room = nextOkRooms[i];
                System.out.println("NavigatorAgent: CANDIDATE CHECKING: " + candidate_room.getX() + " " + candidate_room.getY());
                System.out.println("NavigatorAgent: AGENT CHECKING: " + request_agent_position.getX() + " " + request_agent_position.getY());
                if (candidate_room.getX() > request_agent_position.getX()) {
                    best_candidate = i;
                    System.out.println("NavigatorAgent: 1");
                    break;
                } else if (candidate_room.getY() > request_agent_position.getY()) {
                    if (candidate_status < 3) {
                        System.out.println("NavigatorAgent: 2");
                        candidate_status = 3;
                    } else {
                        continue;
                    }
                } else if (candidate_room.getX() < request_agent_position.getX()) { // влево
                    if (candidate_status < 2) {
                        System.out.println("NavigatorAgent: 3");
                        candidate_status = 2;
                    } else {
                        continue;
                    }
                } else { // вниз
                    if (candidate_status < 1) {
                        System.out.println("NavigatorAgent: 4");
                        candidate_status = 1;
                    } else {
                        continue;
                    }
                }
                best_candidate = i;
            }
            System.out.println("NavigatorAgent: OK ROOMS COUNT IS: " + nextOkRooms.length);
            System.out.println("NavigatorAgent: ADVICE POSITION IS: " + nextOkRooms[best_candidate].getX() + " | " + nextOkRooms[best_candidate].getY());
            actions = getNextRoomAction(request_agent_position, nextOkRooms[best_candidate], SpeleologistAgent.MOVE);
            System.out.println("NavigatorAgent: ADVICE ACTIONS IS: " + Arrays.toString(actions));
        }

        String[] language_actions = new String[actions.length];
        for (int i = 0; i < actions.length; ++i) {
            language_actions[i] = SpeleologistAgent.actionCodes.get(actions[i]);
        }
        return language_actions;
    }

    private int[] getNextRoomAction(Position request_agent_position, Position nextOkRoom, int action) {
        agentX = request_agent_position.getX();
        agentY = request_agent_position.getY();
        int look;
        if (request_agent_position.getY() < nextOkRoom.getY()) {
            agentY += 1;
            look = SpeleologistAgent.LOOK_UP;
        } else if (request_agent_position.getY() > nextOkRoom.getY()) {
            agentY -= 1;
            look = SpeleologistAgent.LOOK_DOWN;
        } else if (request_agent_position.getX() < nextOkRoom.getX()) {
            agentX += 1;
            look = SpeleologistAgent.LOOK_RIGHT;
        } else {
            agentX -= 1;
            look = SpeleologistAgent.LOOK_LEFT;
        }

        return new int[]{look, action};
    }

    private Position[] getOkNeighbors(AID request_agent, Position request_agent_position) {
        Position[] okNeighbors = getNeighborsPosition(request_agent_position);
        ArrayList<Position> okPositions = new ArrayList<>();
        for (Position position : okNeighbors) {
            this.world.getWorldGrid().putIfAbsent(position, new ImaginaryRoom()); // если комнаты
            // не существует - добавляем новую комнату на карте
            if ((this.world.getWorldGrid().get(position).getOk() == Const.Room.Status.TRUE
                    && this.world.getWorldGrid().get(position).getNoWay() != Const.Room.Status.TRUE
                    && this.world.getWorldGrid().get(position).getExist() != Const.Room.Status.FALSE
            ) ||
                    this.world.getWorldGrid().get(position).getOk() == Const.Room.Status.NO_STATUS) {
                okPositions.add(position);
            }
        }
        if (okPositions.size() == 0) {
            int x = agentsWayStory.get(request_agent).getLast()[0];
            int y = agentsWayStory.get(request_agent).getLast()[1];
            okPositions.add(new Position(x, y));
            this.world.getWorldGrid().get(request_agent_position).setNoWay(Const.Room.Status.TRUE);
        }
        return okPositions.toArray(new Position[0]);
    }

    private ImaginaryRoom[] getNeighborsImaginaryRoom(Position request_agent_position) {
        Position rightNeighbor = new Position(request_agent_position.getX() + 1, request_agent_position.getY());
        Position upNeighbor = new Position(request_agent_position.getX(), request_agent_position.getY() + 1);
        Position leftNeighbor = new Position(request_agent_position.getX() - 1, request_agent_position.getY());
        Position bottomNeighbor = new Position(request_agent_position.getX(), request_agent_position.getY() - 1);
        ImaginaryRoom rightRoom = world.getWorldGrid().get(rightNeighbor);
        if (rightRoom == null) {
            rightRoom = new ImaginaryRoom();
            world.getWorldGrid().put(rightNeighbor, rightRoom);
        }
        ImaginaryRoom upRoom = world.getWorldGrid().get(upNeighbor);
        if (upRoom == null) {
            upRoom = new ImaginaryRoom();
            world.getWorldGrid().put(rightNeighbor, upRoom);
        }
        ImaginaryRoom leftRoom = world.getWorldGrid().get(leftNeighbor);
        if (leftRoom == null) {
            leftRoom = new ImaginaryRoom();
            world.getWorldGrid().put(rightNeighbor, leftRoom);
        }
        ImaginaryRoom bottomRoom = world.getWorldGrid().get(bottomNeighbor);
        if (bottomRoom == null) {
            bottomRoom = new ImaginaryRoom();
            world.getWorldGrid().put(rightNeighbor, bottomRoom);
        }
        ImaginaryRoom[] rooms = new ImaginaryRoom[]{rightRoom, upRoom, leftRoom, bottomRoom};
        return rooms;
    }

    private Position[] getNeighborsPosition(Position request_agent_position) {
        Position rightNeighbor = new Position(request_agent_position.getX() + 1, request_agent_position.getY());
        Position upNeighbor = new Position(request_agent_position.getX(), request_agent_position.getY() + 1);
        Position leftNeighbor = new Position(request_agent_position.getX() - 1, request_agent_position.getY());
        Position bottomNeighbor = new Position(request_agent_position.getX(), request_agent_position.getY() - 1);

        return new Position[]{rightNeighbor, upNeighbor, leftNeighbor, bottomNeighbor};
    }

    private void updateNeighbors(Position request_agent_position) {
        ImaginaryRoom currentRoom = world.getWorldGrid().get(request_agent_position);
        ImaginaryRoom[] roomList = getNeighborsImaginaryRoom(request_agent_position);

        if (currentRoom.getStench() == Const.Room.Status.TRUE) {
            world.setWumpusRoomCount(world.getWumpusRoomCount() + 1);
            for (ImaginaryRoom room : roomList) {
                if (room.getWumpus() == Const.Room.Status.NO_STATUS) {
                    room.setOk(Const.Room.Status.POSSIBLE);
                    room.setWumpus(Const.Room.Status.POSSIBLE);
                }
            }
        }
        if (currentRoom.getBreeze() == Const.Room.Status.TRUE) {
            for (ImaginaryRoom room : roomList) {
                if (room.getPit() == Const.Room.Status.NO_STATUS) {
                    room.setOk(Const.Room.Status.POSSIBLE);
                    room.setPit(Const.Room.Status.POSSIBLE);
                }
            }
        }
        if (currentRoom.getBreeze() == Const.Room.Status.FALSE && currentRoom.getStench() == Const.Room.Status.FALSE) {
            for (ImaginaryRoom room : roomList) {
                room.setOk(Const.Room.Status.TRUE);
                room.setWumpus(Const.Room.Status.FALSE);
                room.setPit(Const.Room.Status.FALSE);
            }
        }
    }

}

