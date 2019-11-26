package ua.nure.msit.dvortsov.examples.ontology.employment;

import jade.content.Concept;

/**
 * @author Angelo Difino - CSELT S.p.A
 * @version $Date: 2002-07-31 17:27:34 +0200 (mer, 31 lug 2002) $ $Revision: 3315 $
 */
public class Address implements Concept {

    private String _street;                    // Street name
    private Long _number;          // Street number
    private String _city;                        // City

    // Methods required to use this class to represent the ADDRESS role
    public void setStreet(String street) {
        _street = street;
    }

    public String getStreet() {
        return _street;
    }

    public void setNumber(Long number) {
        _number = number;
    }

    public Long getNumber() {
        return _number;
    }

    public void setCity(String city) {
        _city = city;
    }

    public String getCity() {
        return _city;
    }

    // Other application specific methods
    public boolean equals(Address a) {
        if (!_street.equalsIgnoreCase(a.getStreet()))
            return false;
        if (_number.longValue() != a.getNumber().longValue())
            return false;
        return _city.equalsIgnoreCase(a.getCity());
    }
}