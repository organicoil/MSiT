package ua.nure.msit.dvortsov.examples.content.ontology;

import jade.content.Concept;

/**
 * @author Federico Bergenti - Universita` di Parma
 */

public class Address implements Concept {
    private String city = null;
    private String street = null;
    private int number = 0;

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }
}
