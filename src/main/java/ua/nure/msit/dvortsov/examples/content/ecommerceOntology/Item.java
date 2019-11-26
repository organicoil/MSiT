package ua.nure.msit.dvortsov.examples.content.ecommerceOntology;

import jade.content.Concept;

public class Item implements Concept {
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


