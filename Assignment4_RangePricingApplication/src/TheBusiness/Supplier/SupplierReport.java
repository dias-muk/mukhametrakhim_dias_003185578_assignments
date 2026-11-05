/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package TheBusiness.Supplier;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Report #3 - one {@link SupplierSummary} per supplier, biggest seller first.
 */
public class SupplierReport {

    ArrayList<SupplierSummary> suppliersummarylist;

    /**
     * @param supplierdirectory all suppliers of the business
     * @param totalcustomercount number of customers in the whole business
     */
    public SupplierReport(SupplierDirectory supplierdirectory, int totalcustomercount) {

        suppliersummarylist = new ArrayList<>();

        for (Supplier supplier : supplierdirectory.getSuplierList()) {
            suppliersummarylist.add(new SupplierSummary(supplier, totalcustomercount));
        }

        suppliersummarylist.sort(new Comparator<SupplierSummary>() {
            @Override
            public int compare(SupplierSummary a, SupplierSummary b) {
                return Integer.compare(b.getTotalSales(), a.getTotalSales());
            }
        });
    }

    public ArrayList<SupplierSummary> getSupplierSummaryList() {
        return suppliersummarylist;
    }
}