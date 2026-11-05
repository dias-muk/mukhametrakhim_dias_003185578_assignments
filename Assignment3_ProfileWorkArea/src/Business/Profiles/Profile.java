/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */

package Business.Profiles;

import Business.Person.Person;

/**
 * A role a person plays in the university: admin (employee), faculty or
 * student. Each subclass names its role in getRole(), and login uses the
 * profile's type to decide which work area opens. Keeping the role apart
 * from Person lets one person hold several roles.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public abstract class Profile {
    Person person;
    
    public Profile(Person p){
        person = p;
        
    }
    
    /** The role name: "Admin", "Faculty" or "Student". */
    public abstract String getRole();
    
    public Person getPerson(){
        return person;
    }
     

    /** True when this profile's person has the given NUID. */
    public boolean isMatch(String id) {
        if (person.getPersonId().equals(id)) {
            return true;
        }
        return false;
    }

}
