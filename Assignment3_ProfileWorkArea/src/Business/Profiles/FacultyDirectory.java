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
 * All faculty profiles. Creates them, finds them by NUID and removes them.
 *
 * @author Dias Mukhametrakhim
 */
public class FacultyDirectory {
    
    Business business;
    ArrayList<FacultyProfile> facultyList;
    
    public FacultyDirectory(Business b) {
        business = b;
        facultyList = new ArrayList<FacultyProfile>();
    }
    
    /** Creates a faculty profile for the person, stores and returns it. */
    public FacultyProfile newFacultyProfile(Person p){
        FacultyProfile fp = new FacultyProfile(p);
        facultyList.add(fp);
        return fp;
    }
    
    /** The faculty member with this NUID, or null when there is none. */
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
    
    /** Removes the faculty profile (Business.deleteProfile also removes the login). */
    public void removeFaculty(FacultyProfile faculty){
        facultyList.remove(faculty);
    }
    
}
