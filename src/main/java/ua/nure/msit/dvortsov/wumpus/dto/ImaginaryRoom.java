package ua.nure.msit.dvortsov.wumpus.dto;

import ua.nure.msit.dvortsov.wumpus.util.Const;

public class ImaginaryRoom {

    private int exist;
    private int stench;
    private int breeze;
    private int pit;
    private int wumpus;
    private int ok;
    private int gold;
    private int noWay;

    public ImaginaryRoom() {
        this.exist = Const.Room.Status.NO_STATUS;
        this.stench = Const.Room.Status.NO_STATUS;
        this.breeze = Const.Room.Status.NO_STATUS;
        this.pit = Const.Room.Status.NO_STATUS;
        this.wumpus = Const.Room.Status.NO_STATUS;
        this.ok = Const.Room.Status.NO_STATUS;
        this.gold = Const.Room.Status.NO_STATUS;
        this.noWay = Const.Room.Status.NO_STATUS;
    }

    public void addEvent(String event_name) {
        switch (event_name) {
            case Const.Room.Events.START: {
                break;
            }
            case Const.Room.Events.WUMPUS: {
                this.setWumpus(Const.Room.Status.TRUE);
                break;
            }
            case Const.Room.Events.PIT: {
                this.setPit(Const.Room.Status.TRUE);
                break;
            }
            case Const.Room.Events.BREEZE: {
                this.setBreeze(Const.Room.Status.TRUE);
                break;
            }
            case Const.Room.Events.STENCH: {
                this.setStench(Const.Room.Status.TRUE);
                break;
            }
            case Const.Room.Events.SCREAM: {
                break;
            }
            case Const.Room.Events.GOLD: {
                this.setGold(Const.Room.Status.TRUE);
                break;
            }
            case Const.Room.Events.BUMP: {
                break;
            }
        }
    }

    public int getExist() {
        return exist;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }

    public int getStench() {
        return stench;
    }

    public void setStench(int stench) {
        this.stench = stench;
    }

    public int getBreeze() {
        return breeze;
    }

    public void setBreeze(int breeze) {
        this.breeze = breeze;
    }

    public int getPit() {
        return pit;
    }

    public void setPit(int pit) {
        this.pit = pit;
    }

    public int getWumpus() {
        return wumpus;
    }

    public void setWumpus(int wumpus) {
        this.wumpus = wumpus;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getNoWay() {
        return noWay;
    }

    public void setNoWay(int noWay) {
        this.noWay = noWay;
    }

}
