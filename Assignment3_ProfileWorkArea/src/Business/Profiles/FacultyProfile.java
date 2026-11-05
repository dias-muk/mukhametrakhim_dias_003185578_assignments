/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */

package Business.Profiles;

import Business.Person.Person;

/**
 * The faculty role: a person who teaches, with a department and an academic
 * title. Faculty logins are created by the admin; sign-up never makes faculty.
 *
 * @author Dias Mukhametrakhim
 */
public class FacultyProfile extends Profile {
    private String department = "";
    private String title = "";
    
    
    public FacultyProfile(Person p) {
        super(p);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String getRole() {
        return "Faculty";
    }
}
