/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package TheBusiness.Supplier;

import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.Order;
import TheBusiness.OrderManagement.OrderItem;
import TheBusiness.ProductManagement.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * One row of the supplier report. It walks everything sold out of a supplier's
 * catalog and works out how concentrated that business is: how many different
 * customers it reaches, how much each of them spends on average, and how much
 * of it comes from just five customers.
 */
public class SupplierSummary {

    private static final int TOP_CUSTOMER_COUNT = 5;

    Supplier supplier;
    int totalsales;
    int totalcustomercount;          // customers in the whole business
    Map<CustomerProfile, Integer> salesbycustomer;

    /**
     * @param supplier the supplier being summarized
     * @param totalcustomercount number of customers in the whole business,
     *                           used as the denominator of the loyalty score
     */
    public SupplierSummary(Supplier supplier, int totalcustomercount) {

        this.supplier = supplier;
        this.totalcustomercount = totalcustomercount;
        this.salesbycustomer = new HashMap<>();
        this.totalsales = 0;

        for (Product product : supplier.getProductCatalog().getProductList()) {
            for (OrderItem oi : product.getOrderItems()) {

                int linetotal = oi.getOrderItemTotal();
                totalsales = totalsales + linetotal;

                Order order = oi.getOrder();
                if (order == null) {
                    continue; // item was never attached to an order
                }
                CustomerProfile customer = order.getCustomer();
                if (customer == null) {
                    continue;
                }
                Integer sofar = salesbycustomer.get(customer);
                salesbycustomer.put(customer, (sofar == null ? 0 : sofar) + linetotal);
            }
        }
    }

    public String getSupplierName() {
        return supplier.getName();
    }

    public int getTotalSales() {
        return totalsales;
    }

    /** How many different customers bought something from this supplier. */
    public int getCustomerCount() {
        return salesbycustomer.size();
    }

    /**
     * Share of all the business's customers that this supplier reaches,
     * between 0 and 1.
     */
    public double getLoyaltyScore() {
        if (totalcustomercount == 0) {
            return 0;
        }
        return (double) salesbycustomer.size() / totalcustomercount;
    }

    /** Total sales divided by the number of different customers. */
    public double getAverageSpendingPerCustomer() {
        if (salesbycustomer.isEmpty()) {
            return 0;
        }
        return (double) totalsales / salesbycustomer.size();
    }

    /**
     * Share of the supplier's sales that comes from its five biggest
     * customers, between 0 and 1. A score near 1 means the supplier depends on
     * a handful of customers.
     */
    public double getTopFiveSalesScore() {
        if (totalsales == 0) {
            return 0;
        }
        ArrayList<Integer> totals = new ArrayList<>(salesbycustomer.values());
        Collections.sort(totals, Collections.reverseOrder());

        int topsales = 0;
        for (int i = 0; i < TOP_CUSTOMER_COUNT && i < totals.size(); i++) {
            topsales = topsales + totals.get(i);
        }
        return (double) topsales / totalsales;
    }

    public Supplier getSupplier() {
        return supplier;
    }
}