/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public StudentProfile newStudentProfile(Person p) {
        StudentProfile sp = new StudentProfile(p);
        studentlist.add(sp);
        return sp;
    }

    public StudentProfile findStudent(String nuid) {
        for (StudentProfile sp : studentlist) {
            if (sp.isMatch(nuid)) {
                return sp;
            }
        }
        return null; //not found after going through the whole list
    }

    public void removeStudent(StudentProfile student) {
        studentlist.remove(student);
    }

    public ArrayList<StudentProfile> getStudentList() {
        return studentlist;
    }
}
