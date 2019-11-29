package ua.nure.msit.dvortsov.wumpus.dto;

import ua.nure.msit.dvortsov.wumpus.util.Const;

import java.util.Hashtable;
import java.util.Set;

public class ImaginaryWumpusWorld {

    private Hashtable<Position, ImaginaryRoom> worldGrid;
    private boolean isWumpusAlive;
    private int wumpusRoomCount;

    public ImaginaryWumpusWorld(){
        worldGrid = new Hashtable<>();
        isWumpusAlive = true;
        wumpusRoomCount = 0;
    }

    public Position getWumpusCoords(){
        int xWumpusCoord = 0;
        int yWumpusCoord = 0;

        Set<Position> keys = worldGrid.keySet();
        for (Position roomPosition : keys) {
            ImaginaryRoom room = worldGrid.get(roomPosition);
            if (room.getWumpus() == Const.Room.Status.POSSIBLE) {
                xWumpusCoord += roomPosition.getX();
                yWumpusCoord += roomPosition.getY();
            }
        }
        xWumpusCoord /= wumpusRoomCount;
        yWumpusCoord /= wumpusRoomCount;

        return new Position(xWumpusCoord, yWumpusCoord);
    }

    public Hashtable<Position, ImaginaryRoom> getWorldGrid() {
        return worldGrid;
    }


    public boolean isWumpusAlive() {
        return isWumpusAlive;
    }

    public void setWumpusAlive(boolean wumpusAlive) {
        isWumpusAlive = wumpusAlive;
    }

    public int getWumpusRoomCount() {
        return wumpusRoomCount;
    }

    public void setWumpusRoomCount(int wumpusRoomCount) {
        this.wumpusRoomCount = wumpusRoomCount;
    }
}
