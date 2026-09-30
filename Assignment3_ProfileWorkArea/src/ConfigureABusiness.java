/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

*/

import Business.Business;
import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.EmployeeProfile;
import Business.Profiles.FacultyDirectory;
import Business.Profiles.FacultyProfile;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;
import Business.UserAccounts.UserAccountDirectory;


/**
 *
 * @author kal bugrara
 */
class ConfigureABusiness {

    static Business initialize() {
        Business business = new Business("Information Systems");

// Create persons
        PersonDirectory persondirectory = business.getPersonDirectory();

        Person john = persondirectory.newPerson("001000001", "John Smith");
        john.setEmail("j.smith@northeastern.edu");
        john.setPhone("617-555-0101");
        
        Person gina = persondirectory.newPerson("001000002", "Gina Montana");
        gina.setEmail("g.montana@northeastern.edu");
        gina.setPhone("617-555-0102");

        Person adam = persondirectory.newPerson("002000001", "Adam Rollen");
        adam.setEmail("rollen.a@northeastern.edu");
        adam.setPhone("617-555-0103");
        
        Person laura = persondirectory.newPerson("002000002", "Laura Brown");
        laura.setEmail("brown.l@northeastern.edu");
        laura.setPhone("617-555-0104");

// Create the admin who manages the business (admins are employees)
        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
        EmployeeProfile johnAdmin = employeedirectory.newEmployeeProfile(john);
        johnAdmin.setDepartment("IT Services");
        johnAdmin.setTitle("System Administrator");
        
// Create a faculty member
        FacultyDirectory facultydirectory = business.getFacultyDirectory();
        FacultyProfile ginaFaculty = facultydirectory.newFacultyProfile(gina);
        ginaFaculty.setDepartment("Information Systems");
        ginaFaculty.setTitle("Associate Professor");

// Create a student
        StudentDirectory studentdirectory = business.getStudentDirectory();
        StudentProfile adamStudent = studentdirectory.newStudentProfile(adam);
        adamStudent.setProgram("MS Information Systems");
        
        
        StudentProfile lauraStudent = studentdirectory.newStudentProfile(laura);
        lauraStudent.setProgram("MS Data Analytics");     // no account: Laura signs up herself

// Create user accounts that link to specific profiles
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        uadirectory.newUserAccount(johnAdmin, "admin", "admin123");
        uadirectory.newUserAccount(ginaFaculty, "gina", "gina1234");
        uadirectory.newUserAccount(adamStudent, "adam", "adam1234");

        return business;
    }

}
