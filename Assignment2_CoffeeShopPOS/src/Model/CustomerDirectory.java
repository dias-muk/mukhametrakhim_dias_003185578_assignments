/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package Model;

import java.util.ArrayList;

/**
 * Holds every customer registered by the manager.
 *
 * @author Dias Mukhametrakhim
 */
public class CustomerDirectory {

    private ArrayList<Customer> customerList;

    public CustomerDirectory() {
        this.customerList = new ArrayList<>();
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public Customer newCustomer(
            int customerId, 
            String firstName,
            String lastName,
            long contact
    ) {
        Customer customer = new Customer(customerId, firstName, lastName, contact);
        customerList.add(customer);
        return customer;
    }

    public Customer findById(int customerId) {
        for (Customer c : customerList) {
            if (c.getCustomerId() == customerId) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Customer> findByName(String query) {
        ArrayList<Customer> matches = new ArrayList<>();
        String q = query.trim().toLowerCase();
        for (Customer c : customerList) {
            // Matching on "first last" finds "John", "Smith" and "John Smith".
            String fullName = (c.getFirstName() + " " + c.getLastName()).toLowerCase();
            if (fullName.contains(q)) {
                matches.add(c);
            }
        }
        return matches;
    }

    public boolean isIdTaken(int customerId) {
        return findById(customerId) != null;
    }

    public boolean deleteCustomer(Customer customer) {
        return customerList.remove(customer);
    }
}