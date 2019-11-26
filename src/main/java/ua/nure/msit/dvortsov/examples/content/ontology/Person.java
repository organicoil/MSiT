package ua.nure.msit.dvortsov.examples.content.ontology;

import jade.content.Concept;

/**
 * @author Federico Bergenti - Universita` di Parma
 */

public class Person implements Concept {
    private String name = null;
    private Address address = null;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }
}
