package ua.nure.msit.dvortsov.examples.content.eco.elements;

import jade.content.Concept;

public class Price implements Concept {
    private static final long serialVersionUID = 1L;

    public static final String NAME = "Price";

    private float value;
    private String currency;

    public Price() {
    }

    public Price(float value, String currency) {
        setValue(value);
        setCurrency(currency);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

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
