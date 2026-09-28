/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Profiles;

import Business.Business;
import Business.Person.Person;
import java.util.ArrayList;
/**
 *
 * @author dias
 */

public class FacultyDirectory {
    
    Business business;
    ArrayList<FacultyProfile> facultyList;
    
    public FacultyDirectory(Business b) {
        business = b;
        facultyList = new ArrayList<FacultyProfile>();
    }
    
    public FacultyProfile newFacultyProfile(Person p){
        FacultyProfile fp = new FacultyProfile(p);
        facultyList.add(fp);
        return fp;
    }
    
    public FacultyProfile findFaculty(String id){
        for(FacultyProfile fp: facultyList) {
            if(fp.isMatch(id)){
                return fp;
            }
        }
        return null;
    }

    public ArrayList<FacultyProfile> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(ArrayList<FacultyProfile> facultyList) {
        this.facultyList = facultyList;
    }
    
    public void removeFaculty(FacultyProfile faculty){
        
    }
    
}
