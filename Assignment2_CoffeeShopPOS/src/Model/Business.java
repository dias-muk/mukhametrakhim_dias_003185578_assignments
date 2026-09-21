package Model;

/**
 * Root of the coffee shop model. Owns one directory per entity type.
 *
 * @author Dias Mukhametrakhim
 */
public class Business {

    private String name;
    private ProductCatalog productCatalog;
    private CustomerDirectory customerDirectory;
    private OrderDirectory orderDirectory;

    public Business(String name) {
        this.name = name;
        this.productCatalog = new ProductCatalog();
        this.customerDirectory = new CustomerDirectory();
        this.orderDirectory = new OrderDirectory();
    }

    public String getName() {
        return name;
    }

    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    public CustomerDirectory getCustomerDirectory() {
        return customerDirectory;
    }

    public OrderDirectory getOrderDirectory() {
        return orderDirectory;
    }
}