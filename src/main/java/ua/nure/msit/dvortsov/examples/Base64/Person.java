package ua.nure.msit.dvortsov.examples.Base64;

import java.io.Serializable;
import java.util.Date;

/**
 * This is  just a support class for the object reader and writer agents.
 * Refer to them for any documentation.
 *
 * @author Fabio Bellifemine - CSELT S.p.A
 * @version $Date: 2001-09-17 19:22:31 +0200 (lun, 17 set 2001) $ $Revision: 2685 $
 */

public class Person implements Serializable {

    String name;
    String surname;
    Date birthdate;
    int age;

    Person(String n, String s, Date d, int a) {
        name = n;
        surname = s;
        birthdate = d;
        age = a;
    }

    public String toString() {
        return (name + " " + surname + " born on " + birthdate.toString() + " age = " + age);
    }

}
