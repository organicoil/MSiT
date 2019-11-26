package ua.nure.msit.dvortsov.examples.content.eco.elements;

import jade.content.Concept;
import jade.content.onto.annotations.Slot;

import java.util.Date;

public class CreditCard implements Concept {
    private static final long serialVersionUID = 1L;

    private String type = null;
    private long number;
    private Date expirationDate = null;

    public CreditCard() {
    }

    public CreditCard(String type, long number, Date expirationDate) {
        setType(type);
        setNumber(number);
        setExpirationDate(expirationDate);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Slot(mandatory = false, name = "expirationdate")
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String toString() {
        return type + " N. " + number + " Exp. " + expirationDate;
    }
}
