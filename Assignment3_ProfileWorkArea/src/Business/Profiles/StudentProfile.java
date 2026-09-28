/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */
package Business.Profiles;

import Business.Person.Person;

/**
 * The student role of a person. The Person itself is kept in Profile, so this
 * class must not declare its own person field.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class StudentProfile extends Profile {

    private String program = "";

    public StudentProfile(Person p) {
        super(p);
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    @Override
    public String getRole() {
        return "Student";
    }
}
