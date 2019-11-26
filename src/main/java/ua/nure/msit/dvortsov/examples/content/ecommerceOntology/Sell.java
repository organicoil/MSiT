package ua.nure.msit.dvortsov.examples.content.ecommerceOntology;

import jade.content.AgentAction;
import jade.core.AID;

public class Sell implements AgentAction {
    private AID buyer;
    private Item item;
    private CreditCard creditCard;

    public Sell() {
    }

    public Sell(AID buyer, Item item, CreditCard cc) {
        setBuyer(buyer);
        setItem(item);
        setCreditCard(cc);
    }

    public AID getBuyer() {
        return buyer;
    }

    public void setBuyer(AID id) {
        buyer = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item i) {
        item = i;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
