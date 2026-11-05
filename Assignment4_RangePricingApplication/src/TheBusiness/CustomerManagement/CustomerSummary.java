/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package TheBusiness.CustomerManagement;

/**
 * One row of the most valuable customers report: a customer and everything
 * that customer has ever bought.
 */
public class CustomerSummary {

    CustomerProfile customer;
    int ordertotal;

    public CustomerSummary(CustomerProfile cp) {
        customer = cp;
        ordertotal = cp.getTotalSales();
    }

    public String getCustomerName() {
        return customer.getCustomerId();
    }

    public int getTotalSales() {
        return ordertotal;
    }

    public int getNumberOfOrders() {
        return customer.getOrders().size();
    }

    public CustomerProfile getCustomer() {
        return customer;
    }
}