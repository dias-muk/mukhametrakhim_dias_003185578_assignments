/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package Model;

/**
 * Creates the Business and populates it with startup demo data.
 *
 * @author Dias Mukhametrakhim
 */
public class ConfigureABusiness {

    private ConfigureABusiness() {
    }

    public static Business initialize() {
        Business business = new Business("Northeastern Coffee House");
        seedProducts(business);
        seedCustomers(business);
        seedOrders(business);
        return business;
    }

    private static void seedProducts(Business business) {
        ProductCatalog catalog = business.getProductCatalog();

        catalog.newProduct(101, "Latte", "Coffee", 4.75, 50, 4);
        catalog.newProduct(102, "Cappuccino", "Coffee", 4.50, 40, 4);
        catalog.newProduct(103, "Espresso", "Coffee", 3.25, 60, 2);
        catalog.newProduct(104, "Green tea", "Tea", 3.00, 35, 3);
        catalog.newProduct(105, "Croissant", "Pastry", 3.75, 20, 1);
        catalog.newProduct(106, "Blueberry muffin", "Pastry", 3.50, 25, 1);
        catalog.newProduct(107, "Turkey sandwich", "Sandwich", 8.95, 15, 6);
    }

    private static void seedCustomers(Business business) {
        CustomerDirectory customers = business.getCustomerDirectory();

        // Three customers share the name John Smith so that search by name
        // can be shown returning multiple matches.
        customers.newCustomer(1001, "John", "Smith", 8573952435L);
        customers.newCustomer(1002, "John", "Smith", 6175550118L);
        customers.newCustomer(1003, "John", "Smith", 9785550947L);

        customers.newCustomer(1004, "Priya", "Nair", 6179994521L);
        customers.newCustomer(1005, "Miguel", "Torres", 8572223390L);
        customers.newCustomer(1006, "Aisha", "Rahman", 6178887744L);
        customers.newCustomer(1007, "Chen", "Wei", 9781112233L);
    }

    private static void seedOrders(Business business) {
        OrderDirectory orders = business.getOrderDirectory();
        CustomerDirectory customers = business.getCustomerDirectory();
        ProductCatalog catalog = business.getProductCatalog();

        orders.newOrder(5001, customers.findById(1001), catalog.findById(101), 2,
                OrderType.DINE_IN, PaymentMethod.CARD, OrderStatus.COMPLETED, true);

        orders.newOrder(5002, customers.findById(1002), catalog.findById(107), 1,
                OrderType.TAKEOUT, PaymentMethod.CASH, OrderStatus.PREPARING, false);

        orders.newOrder(5003, customers.findById(1003), catalog.findById(103), 3,
                OrderType.PICKUP, PaymentMethod.MOBILE, OrderStatus.READY, true);

        orders.newOrder(5004, customers.findById(1004), catalog.findById(105), 2,
                OrderType.DINE_IN, PaymentMethod.CARD, OrderStatus.PENDING, false);

        orders.newOrder(5005, customers.findById(1005), catalog.findById(101), 1,
                OrderType.TAKEOUT, PaymentMethod.MOBILE, OrderStatus.COMPLETED, true);
    }
}