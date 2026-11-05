/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package Model;

/**
 * A customer of the coffee shop, registered by the manager.
 *
 * @author Dias Mukhametrakhim
 */
public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private long contact;
    
    public Customer(
            int customerId,
            String firstName,
            String lastName,
            long contact
    ) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return String.valueOf(customerId);
    }
    
    
}
