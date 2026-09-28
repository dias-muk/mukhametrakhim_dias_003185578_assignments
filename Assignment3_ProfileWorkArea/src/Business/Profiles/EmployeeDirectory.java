/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public EmployeeProfile newEmployeeProfile(Person p) {
        EmployeeProfile sp = new EmployeeProfile(p);
        employeelist.add(sp);
        return sp;
    }

    public EmployeeProfile findEmployee(String nuid) {
        for (EmployeeProfile sp : employeelist) {
            if (sp.isMatch(nuid)) {
                return sp;
            }
        }
        return null; //not found after going through the whole list
    }

    public void removeEmployee(EmployeeProfile employee) {
        employeelist.remove(employee);
    }

    public ArrayList<EmployeeProfile> getEmployeeList() {
        return employeelist;
    }
}
