/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */

package Business.Profiles;

import Business.Person.Person;

/**
 * The employee role. Every employee in this application is an
 * administrator, so getRole() returns "Admin" and login opens the admin
 * work area.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class EmployeeProfile extends Profile {

    private String department = "";
    private String title = "";

    public EmployeeProfile(Person p) {
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
        return "Admin";
    }
}