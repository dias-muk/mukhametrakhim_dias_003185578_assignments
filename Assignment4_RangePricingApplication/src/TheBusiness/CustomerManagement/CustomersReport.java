/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package TheBusiness.CustomerManagement;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Report #2 - customers ranked by how much they have bought.
 */
public class CustomersReport {

    ArrayList<CustomerSummary> customerlist;

    public CustomersReport() {
        customerlist = new ArrayList<>();
    }

    public void addCustomerSummary(CustomerSummary cs) {
        customerlist.add(cs);
    }

    /**
     * @return the summaries sorted from the highest total sales to the lowest
     */
    public ArrayList<CustomerSummary> getCustomerSummariesByValue() {
        ArrayList<CustomerSummary> sorted = new ArrayList<>(customerlist);
        sorted.sort(new Comparator<CustomerSummary>() {
            @Override
            public int compare(CustomerSummary a, CustomerSummary b) {
                return Integer.compare(b.getTotalSales(), a.getTotalSales());
            }
        });
        return sorted;
    }

    public ArrayList<CustomerSummary> getCustomerSummaryList() {
        return customerlist;
    }
}