/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */

package Business.Person;

import java.util.ArrayList;

/**
 * Every person in the business, whatever their role. Creates people and
 * finds them by NUID.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class PersonDirectory {
    
    ArrayList<Person> personlist ;
    
    public PersonDirectory (){
        personlist = new ArrayList();
    }

    /** Creates a person with this NUID and name, stores and returns them. */
    public Person newPerson(String nuid, String name) {
        Person p = new Person(nuid, name);
        personlist.add(p);
        return p;
    }

    /** The person with this NUID, or null when there is none. */
    public Person findPerson(String nuid) {
         for (Person p : personlist) {
             if (p.isMatch(nuid)) {
                 return p;
             }
         }
         return null; //not found after going through the whole list
    }
    
    /** Removes the person from the directory. */
    public void removePerson(Person p) {
        personlist.remove(p);
    }

    public ArrayList<Person> getPersonList() {
        return personlist;
    }
    
}
