/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */

package Business.Profiles;

import Business.Business;
import Business.Person.Person;

import java.util.ArrayList;

/**
 * All employee (admin) profiles.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class EmployeeDirectory {

    Business business;
    ArrayList<EmployeeProfile> employeelist;

    public EmployeeDirectory(Business d) {
        business = d;
        employeelist = new ArrayList<>();
    }

    /** Creates an employee profile for the person, stores and returns it. */
    public EmployeeProfile newEmployeeProfile(Person p) {
        EmployeeProfile sp = new EmployeeProfile(p);
        employeelist.add(sp);
        return sp;
    }

    /** The employee with this NUID, or null when there is none. */
    public EmployeeProfile findEmployee(String nuid) {
        for (EmployeeProfile sp : employeelist) {
            if (sp.isMatch(nuid)) {
                return sp;
            }
        }
        return null; //not found after going through the whole list
    }

    /** Removes the employee profile (Business.deleteProfile also removes the login). */
    public void removeEmployee(EmployeeProfile employee) {
        employeelist.remove(employee);
    }

    public ArrayList<EmployeeProfile> getEmployeeList() {
        return employeelist;
    }
}
