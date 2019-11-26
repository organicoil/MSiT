package ua.nure.msit.dvortsov.examples.ontology.employment;

import jade.content.Predicate;

/**
 * @author Angelo Difino - CSELT S.p.A
 * @version $Date: 2002-07-31 17:27:34 +0200 (mer, 31 lug 2002) $ $Revision: 3315 $
 */
public class Person implements Predicate {

    private String _name;                        //Person's name
    private Long _age;                            //Person's age
    private Address _address;                    //Address' age

    // Methods required to use this class to represent the PERSON role
    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setAge(Long age) {
        _age = age;
    }

    public Long getAge() {
        return _age;
    }

    public void setAddress(Address address) {
        _address = address;
    }

    public Address getAddress() {
        return _address;
    }

    // Other application specific methods
    public boolean equals(Person p) {
        if (!_name.equalsIgnoreCase(p.getName()))
            return false;
        if (_age != null && p.getAge() != null) // Age is an optional field
            if (_age.longValue() != p.getAge().longValue())
                return false;
        if (_address != null && p.getAddress() != null) // Address is an optional field
            return _address.equals(p.getAddress());
        return true;
    }
}