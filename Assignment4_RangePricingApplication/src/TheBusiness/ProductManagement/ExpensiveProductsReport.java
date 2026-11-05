/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package TheBusiness.ProductManagement;

import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Report #1 - every product in the business, most expensive first.
 */
public class ExpensiveProductsReport {

    ArrayList<ProductPriceSummary> productsummarylist;

    public ExpensiveProductsReport(SupplierDirectory supplierdirectory) {

        productsummarylist = new ArrayList<>();

        for (Supplier supplier : supplierdirectory.getSuplierList()) {
            for (Product p : supplier.getProductCatalog().getProductList()) {
                productsummarylist.add(new ProductPriceSummary(p, supplier.getName()));
            }
        }

        productsummarylist.sort(new Comparator<ProductPriceSummary>() {
            @Override
            public int compare(ProductPriceSummary a, ProductPriceSummary b) {
                return Integer.compare(b.getPrice(), a.getPrice()); // highest price first
            }
        });
    }

    public ArrayList<ProductPriceSummary> getProductSummaryList() {
        return productsummarylist;
    }

    /**
     * @param n how many rows to return
     * @return the n most expensive products, or all of them if there are fewer
     */
    public ArrayList<ProductPriceSummary> getTopProducts(int n) {
        ArrayList<ProductPriceSummary> top = new ArrayList<>();
        for (int i = 0; i < n && i < productsummarylist.size(); i++) {
            top.add(productsummarylist.get(i));
        }
        return top;
    }
}