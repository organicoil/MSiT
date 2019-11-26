package ua.nure.msit.dvortsov.examples.content.eco.elements;

import jade.content.Predicate;
import jade.core.AID;

public class Owns implements Predicate {
    private static final long serialVersionUID = 1L;

    private AID owner;
    private Item item;

    public AID getOwner() {
        return owner;
    }

    public void setOwner(AID id) {
        owner = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item i) {
        item = i;
    }
}
