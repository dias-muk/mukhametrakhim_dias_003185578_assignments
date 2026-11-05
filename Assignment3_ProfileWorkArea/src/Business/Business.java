/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */

package Business;

import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.EmployeeProfile;
import Business.Profiles.FacultyDirectory;
import Business.Profiles.FacultyProfile;
import Business.Profiles.Profile;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;
import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;

/**
 * The root of the model: it owns one directory for each kind of object.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class Business {

    String name;
    PersonDirectory persondirectory; //all people profiles regardless of the role
    EmployeeDirectory employeedirectory;
    UserAccountDirectory useraccountdirectory;
    StudentDirectory studentdirectory;
    FacultyDirectory facultydirectory;
    


    public Business(String n) {
        name = n;

        persondirectory = new PersonDirectory();
        employeedirectory = new EmployeeDirectory(this);
        useraccountdirectory = new UserAccountDirectory();
        studentdirectory = new StudentDirectory();
        facultydirectory = new FacultyDirectory(this);
    }

    public PersonDirectory getPersonDirectory() {
        return persondirectory;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return useraccountdirectory;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeedirectory;
    }

    public StudentDirectory getStudentDirectory(){
        return studentdirectory;
    }
    
    public FacultyDirectory getFacultyDirectory(){
        return facultydirectory;
    }
    
        /**
     * Finds the profile of the person with this NUID, whatever their role.
     * Returns null when nobody has that NUID.
     */
    public Profile findProfile(String nuid) {
        Profile profile = employeedirectory.findEmployee(nuid);
        if (profile == null) {
            profile = facultydirectory.findFaculty(nuid);
        }
        if (profile == null) {
            profile = studentdirectory.findStudent(nuid);
        }
        return profile;
    }
    
        /**
     * Deletes a profile together with its user account. The person is removed
     * too when no other profile uses their NUID.
     */
    public void deleteProfile(Profile profile) {
        UserAccount account = useraccountdirectory.findByProfile(profile);
        if (account != null) {
            useraccountdirectory.removeUserAccount(account);
        }
        if (profile instanceof EmployeeProfile) {
            employeedirectory.removeEmployee((EmployeeProfile) profile);
        } else if (profile instanceof FacultyProfile) {
            facultydirectory.removeFaculty((FacultyProfile) profile);
        } else if (profile instanceof StudentProfile) {
            studentdirectory.removeStudent((StudentProfile) profile);
        }
        Person person = profile.getPerson();
        if (findProfile(person.getPersonId()) == null) {
            persondirectory.removePerson(person);
        }
    }
    
        /**
     * Creates a student account from the Sign Up page. Sign-up only ever makes
     * students: if the admin already added this NUID as a student without a
     * login, the new account is linked to that record (the name must match);
     * an NUID nobody has registered becomes a new student. Throws
     * IllegalArgumentException with a message for the user when the account
     * cannot be created.
     */
    public UserAccount signUpStudent(String name, String nuid, String username, String password) {
        if (useraccountdirectory.findByUsername(username) != null) {
            throw new IllegalArgumentException("The username \"" + username + "\" is already taken.");
        }
        StudentProfile student;
        Person person = persondirectory.findPerson(nuid);
        if (person == null) {
            person = persondirectory.newPerson(nuid, name);
            student = studentdirectory.newStudentProfile(person);
        } else {
            student = studentdirectory.findStudent(nuid);
            if (student == null) {
                throw new IllegalArgumentException("NUID " + nuid + " belongs to a staff member. "
                        + "Staff accounts are created by the administrator.");
            }
            if (useraccountdirectory.findByProfile(student) != null) {
                throw new IllegalArgumentException("NUID " + nuid + " already has an account. Please log in.");
            }
            if (!person.getName().trim().equalsIgnoreCase(name.trim())) {
                throw new IllegalArgumentException("NUID " + nuid + " is registered under a different name. "
                        + "Enter your name as the admin registered it, or contact the administrator.");
            }
        }
        return useraccountdirectory.newUserAccount(student, username, password);
    }
}
