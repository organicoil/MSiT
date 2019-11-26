package ua.nure.msit.dvortsov.examples.ontology.employment;

import jade.content.Predicate;

/**
 * @author Giovanni Caire - CSELT S.p.A
 * @version $Date: 2002-07-31 17:27:34 +0200 (mer, 31 lug 2002) $ $Revision: 3315 $
 */
public class WorksFor implements Predicate {

    private Company _company;                            //Company employer
    private Person _person;                            //Person employee

    //These methods are used by the JADE-framework
    public void setPerson(Person person) {
        _person = person;
    }

    public Person getPerson() {
        return _person;
    }

    public void setCompany(Company company) {
        _company = company;
    }

    public Company getCompany() {
        return _company;
    }

}