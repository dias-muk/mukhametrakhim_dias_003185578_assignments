/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package TheBusiness.ProductManagement;

/**
 * One row of the most expensive products report: a product, the supplier that
 * sells it and its target price.
 */
public class ProductPriceSummary {

    Product product;
    String suppliername;

    public ProductPriceSummary(Product p, String suppliername) {
        product = p;
        this.suppliername = suppliername;
    }

    public String getProductName() {
        return product.getName();
    }

    public String getSupplierName() {
        return suppliername;
    }

    public int getPrice() {
        return product.getTargetPrice();
    }

    public Product getProduct() {
        return product;
    }
}