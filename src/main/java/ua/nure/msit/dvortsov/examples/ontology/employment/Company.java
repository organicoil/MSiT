package ua.nure.msit.dvortsov.examples.ontology.employment;

import jade.content.Concept;

/**
 * @author Angelo Difino - CSELT S.p.A
 * @version $Date: 2002-07-31 17:27:34 +0200 (mer, 31 lug 2002) $ $Revision: 3315 $
 */
public class Company implements Concept {

    private String _name;                        //Company's name
    private Address _address;                    //Headquarter's address

    // Methods required to use this class to represent the COMPANY role
    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setAddress(Address address) {
        _address = address;
    }

    public Address getAddress() {
        return _address;
    }

    // Other application specific methods
    public boolean equals(Company c) {
        return (_name.equalsIgnoreCase(c.getName()));
    }
}