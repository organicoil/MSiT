package ua.nure.msit.dvortsov.examples.content.eco.elements;

import jade.content.Concept;

public class Item implements Concept {
    private static final long serialVersionUID = 1L;

    private int serialID;

    public Item() {
    }

    public Item(int id) {
        setSerialID(id);
    }

    public void setSerialID(int id) {
        serialID = id;
    }

    public int getSerialID() {
        return serialID;
    }
}
