/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Person;

import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class PersonDirectory {
    
    ArrayList<Person> personlist ;
    
    public PersonDirectory (){
        personlist = new ArrayList();
    }

    public Person newPerson(String nuid, String name) {
        Person p = new Person(nuid, name);
        personlist.add(p);
        return p;
    }

    public Person findPerson(String nuid) {
         for (Person p : personlist) {
             if (p.isMatch(nuid)) {
                 return p;
             }
         }
         return null; //not found after going through the whole list
    }
    
    public void removePerson(Person p) {
        personlist.remove(p);
    }

    public ArrayList<Person> getPersonList() {
        return personlist;
    }
    
}
