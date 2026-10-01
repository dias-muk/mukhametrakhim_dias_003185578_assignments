/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */

package Business.Person;

/**
 * A real person in the system, whatever roles they hold. The id is the
 * person's NUID: it is unique across everyone and never changes once the
 * person is created.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class Person {

    String id;
    private String name;
    private String email = "";
    private String phone = "";

    public Person(String nuid, String name) {
        this.id = nuid;
        this.name = name;
    }

    public String getPersonId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** True when this person has the given NUID. */
    public boolean isMatch(String id) {
        return getPersonId().equals(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
