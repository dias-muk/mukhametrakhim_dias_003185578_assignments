/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Person;

/**
 *
 * @author kal bugrara
 */
public class Person {

    String id;
    private String name;
    private String email = "";
    private String phone = "";

    public Person(String nuid, String name) {
        this.id = nuid;
        this.name = name;
    }

    public String getPersonId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isMatch(String id) {
        return getPersonId().equals(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
