package ua.nure.msit.dvortsov.examples.content.ecommerceOntology;

import jade.content.Concept;

import java.util.Date;

public class CreditCard implements Concept {
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

    // TYPE
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // NUMBER
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    // EXPIRATIONDATE
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


