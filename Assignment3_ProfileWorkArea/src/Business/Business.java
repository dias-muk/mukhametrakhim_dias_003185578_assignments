/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
}
