package Model;

/**
 *
 * @author dias
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
    
    
}
