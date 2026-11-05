/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package Model;

/**
 * One item the coffee shop sells, held in the ProductCatalog.
 *
 * @author Dias Mukhametrakhim
 */
public class Product {
    private int productId;
    private String name;
    private String category;
    private double price;
    private int number;
    private int prepTime;
    
    public Product(
            int productId, 
            String name, 
            String category, 
            double price,
            int number, 
            int prepTime
    ) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.number = number;
        this.prepTime = prepTime;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    
}
