package ua.nure.msit.dvortsov.examples.content.eco.elements;

import jade.content.Predicate;

public class Costs implements Predicate {
    private static final long serialVersionUID = 1L;

    public static final String NAME = "Costs";

    public static final String ITEM = "item";
    public static final String PRICE = "price";

    private Item item;
    private Price price;

    public Item getItem() {
        return item;
    }

    public void setItem(Item i) {
        item = i;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price p) {
        price = p;
    }

}
