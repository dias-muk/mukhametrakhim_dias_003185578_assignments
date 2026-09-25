/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package Model;

import java.util.ArrayList;

/**
 * Holds every product the coffee shop offers.
 *
 * @author Dias Mukhametrakhim
 */
public class ProductCatalog {

    private ArrayList<Product> catalog;

    public ProductCatalog() {
        this.catalog = new ArrayList<>();
    }

    public ArrayList<Product> getCatalog() {
        return catalog;
    }

    public Product newProduct(
            int productId, 
            String name, 
            String category,
            double price, 
            int number, 
            int prepTime
    ) {
        Product product = new Product(productId, name, category, price, number, prepTime);
        catalog.add(product);
        return product;
    }

    public Product findById(int productId) {
        for (Product p : catalog) {
            if (p.getProductId() == productId) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Product> searchByName(String query) {
        ArrayList<Product> matches = new ArrayList<>();
        String q = query.trim().toLowerCase();
        for (Product p : catalog) {
            if (p.getName().toLowerCase().contains(q)) {
                matches.add(p);
            }
        }
        return matches;
    }

    public boolean isIdTaken(int productId) {
        return findById(productId) != null;
    }

    public boolean deleteProduct(Product product) {
        return catalog.remove(product);
    }
}