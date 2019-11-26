package ua.nure.msit.dvortsov.examples.content.ecommerceOntology;

import jade.content.Concept;

public class Price implements Concept {
    private float value;
    private String currency;

    public Price() {
    }

    public Price(float value, String currency) {
        setValue(value);
        setCurrency(currency);
    }

    // VALUE
    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    // CURRENCY
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toString() {
        return value + "-" + currency;
    }
}


