/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */

package Business.Profiles;

import Business.Person.Person;

import java.util.ArrayList;


/**
 * All student profiles.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class StudentDirectory {

    ArrayList<StudentProfile> studentlist;

    public StudentDirectory() {
        studentlist = new ArrayList<>();
    }

    /** Creates a student profile for the person, stores and returns it. */
    public StudentProfile newStudentProfile(Person p) {
        StudentProfile sp = new StudentProfile(p);
        studentlist.add(sp);
        return sp;
    }

    /** The student with this NUID, or null when there is none. */
    public StudentProfile findStudent(String nuid) {
        for (StudentProfile sp : studentlist) {
            if (sp.isMatch(nuid)) {
                return sp;
            }
        }
        return null; //not found after going through the whole list
    }

    /** Removes the student profile (Business.deleteProfile also removes the login). */
    public void removeStudent(StudentProfile student) {
        studentlist.remove(student);
    }

    public ArrayList<StudentProfile> getStudentList() {
        return studentlist;
    }
}
