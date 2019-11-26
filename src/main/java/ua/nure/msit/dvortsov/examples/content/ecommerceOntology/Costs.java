package ua.nure.msit.dvortsov.examples.content.ecommerceOntology;

import jade.content.Predicate;

public class Costs implements Predicate {
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
