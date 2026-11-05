/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package TheBusiness;

import MarketingManagement.MarketingPersonDirectory;
import MarketingManagement.MarketingPersonProfile;
import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerDirectory;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.MasterOrderList;
import TheBusiness.OrderManagement.Order;
import TheBusiness.Personnel.Person;
import TheBusiness.Personnel.PersonDirectory;
import TheBusiness.ProductManagement.Product;
import TheBusiness.ProductManagement.ProductCatalog;
import TheBusiness.SalesManagement.SalesPersonDirectory;
import TheBusiness.SalesManagement.SalesPersonProfile;
import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;
import TheBusiness.UserAccountManagement.UserAccountDirectory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Builds the data set the application starts with, as required by Part 1 of
 * the assignment: one business, 50 suppliers, 50 products for 30 of those
 * suppliers, 300 customers, one to three orders per customer and up to ten
 * items per order.
 *
 * The generator uses a fixed seed, so every run produces exactly the same
 * data. That keeps the reports reproducible, which makes them much easier to
 * check and to demo.
 */
public class DataGenerator {

    /** Fixed seed, so the same data set is produced on every run. */
    private static final long SEED = 5100L;

    public static final int SUPPLIER_COUNT = 50;
    public static final int SUPPLIERS_WITH_PRODUCTS = 30;
    public static final int PRODUCTS_PER_SUPPLIER = 50;
    public static final int CUSTOMER_COUNT = 300;
    public static final int MIN_ORDERS_PER_CUSTOMER = 1;
    public static final int MAX_ORDERS_PER_CUSTOMER = 3;
    public static final int MAX_ITEMS_PER_ORDER = 10;
    public static final int MAX_QUANTITY_PER_ITEM = 5;

    private static final String[] SUPPLIER_NAMES = {
        "Lenovo", "Epson", "Canon Imaging", "Brother Office", "Kyocera Docs",
        "Ricoh Systems", "Sharp Business", "Toshiba Imaging", "Konica Graphics", "Oki Print",
        "Lexmark Works", "Samsung Office", "Panasonic Docs", "Fujitsu Scan", "Dell Peripherals",
        "HP Workgroup", "Xerox Partners", "Zebra Labels", "Pantum Print", "Riso Graphics",
        "Avision Scan", "Plustek Imaging", "Visioneer Docs", "Contex Wide", "Mutoh Plotters",
        "Roland Graphics", "Mimaki Print", "Graphtec Cutters", "Primera Labels", "Dymo Office",
        "Fellowes Paper", "GBC Binding", "Swingline Works", "Rexel Office", "Acco Supplies",
        "Bosch Office", "Siemens Docs", "Philips Imaging", "Olivetti Print", "Develop Graphics",
        "Triumph Adler", "Utax Systems", "Muratec Docs", "Duplo Finishing", "Horizon Binding",
        "Morgana Press", "Neopost Mail", "Pitney Works", "Formax Paper", "Intimus Shred"
    };

    private static final String[] PRODUCT_FAMILIES = {
        "Scanner", "Printer", "Photocopier", "Projector", "Shredder",
        "Plotter", "Label Printer", "Fax Machine", "Laminator", "Binding Machine"
    };

    /** 30 bases x 10 suffixes gives exactly 300 unique customer names. */
    private static final String[] CUSTOMER_BASES = {
        "Dell", "Microsoft", "Google", "JP Morgan", "State Street",
        "Fidelity", "Raytheon", "Liberty", "Beacon", "Harbor",
        "Summit", "Pioneer", "Granite", "Atlantic", "Redstone",
        "Nordic", "Silverline", "Brightpath", "Ironwood", "Clearwater",
        "Copley", "Fenway", "Charles River", "Back Bay", "Seaport",
        "Longwood", "Allston", "Brookline", "Quincy", "Revere"
    };

    private static final String[] CUSTOMER_SUFFIXES = {
        "Systems", "Logistics", "Health", "Capital", "Media",
        "Energy", "Retail", "Analytics", "Robotics", "Insurance"
    };

    /**
     * Creates the business and fills it with the full demo data set.
     *
     * @return a fully populated business
     */
    public static Business generateBusiness() {

        Random random = new Random(SEED);
        Business business = new Business("Xerox");

        SalesPersonProfile salesperson = createStaff(business);
        List<Supplier> suppliers = createSuppliers(business, random);
        List<Supplier> sellingsuppliers = suppliers.subList(0, SUPPLIERS_WITH_PRODUCTS);
        List<CustomerProfile> customers = createCustomers(business);
        createOrders(business, customers, sellingsuppliers, salesperson, random);

        return business;
    }

    /**
     * Creates the sales and marketing people and the two user accounts the
     * login screen expects.
     *
     * @return the sales person every generated order is booked against
     */
    private static SalesPersonProfile createStaff(Business business) {

        PersonDirectory persondirectory = business.getPersonDirectory();
        Person salesperson = persondirectory.newPerson("Xerox sales");
        Person marketingperson = persondirectory.newPerson("Xerox marketing");

        SalesPersonDirectory salespersondirectory = business.getSalesPersonDirectory();
        SalesPersonProfile salespersonprofile
                = salespersondirectory.newSalesPersonProfile(salesperson);

        MarketingPersonDirectory marketingpersondirectory = business.getMarketingPersonDirectory();
        MarketingPersonProfile marketingpersonprofile
                = marketingpersondirectory.newMarketingPersonProfile(marketingperson);

        UserAccountDirectory useraccountdirectory = business.getUserAccountDirectory();
        useraccountdirectory.newUserAccount(salespersonprofile, "Sales", "XXXX");
        useraccountdirectory.newUserAccount(marketingpersonprofile, "Marketing", "XXXX");

        return salespersonprofile;
    }

    /**
     * Creates the 50 suppliers and gives the first 30 of them a catalog of 50
     * products each. Every product gets a floor price below its target price
     * and a ceiling price above it, so the range pricing reports have
     * something meaningful to measure.
     */
    private static List<Supplier> createSuppliers(Business business, Random random) {

        SupplierDirectory supplierdirectory = business.getSupplierDirectory();
        List<Supplier> suppliers = new ArrayList<>();

        for (int i = 0; i < SUPPLIER_COUNT; i++) {
            Supplier supplier = supplierdirectory.newSupplier(SUPPLIER_NAMES[i]);
            suppliers.add(supplier);

            if (i >= SUPPLIERS_WITH_PRODUCTS) {
                continue; // only the first 30 suppliers sell products
            }

            ProductCatalog productcatalog = supplier.getProductCatalog();
            for (int p = 0; p < PRODUCTS_PER_SUPPLIER; p++) {
                String name = PRODUCT_FAMILIES[p % PRODUCT_FAMILIES.length] + " " + (100 + p);
                int floorprice = 200 + random.nextInt(4800);
                int targetprice = floorprice + 100 + random.nextInt(2000);
                int ceilingprice = targetprice + 100 + random.nextInt(2000);
                productcatalog.newProduct(name, floorprice, ceilingprice, targetprice);
            }
        }
        return suppliers;
    }

    /**
     * Creates 300 customers. Each name is a base company name plus an industry
     * suffix, which keeps all 300 names unique.
     */
    private static List<CustomerProfile> createCustomers(Business business) {

        PersonDirectory persondirectory = business.getPersonDirectory();
        CustomerDirectory customerdirectory = business.getCustomerDirectory();
        List<CustomerProfile> customers = new ArrayList<>();

        for (int i = 0; i < CUSTOMER_COUNT; i++) {
            String name = CUSTOMER_BASES[i / CUSTOMER_SUFFIXES.length] + " "
                    + CUSTOMER_SUFFIXES[i % CUSTOMER_SUFFIXES.length];
            Person person = persondirectory.newPerson(name);
            customers.add(customerdirectory.newCustomerProfile(person));
        }
        return customers;
    }

    /**
     * Gives every customer one to three orders. Each order holds up to ten
     * items taken from a single supplier's catalog, and every item is sold
     * somewhere between the product's floor and ceiling price, so some sales
     * land above the target price and some below it.
     */
    private static void createOrders(Business business, List<CustomerProfile> customers,
            List<Supplier> sellingsuppliers, SalesPersonProfile salesperson, Random random) {

        MasterOrderList masterorderlist = business.getMasterOrderList();

        for (CustomerProfile customer : customers) {

            int ordercount = MIN_ORDERS_PER_CUSTOMER
                    + random.nextInt(MAX_ORDERS_PER_CUSTOMER - MIN_ORDERS_PER_CUSTOMER + 1);

            for (int o = 0; o < ordercount; o++) {

                Order order = masterorderlist.newOrder(customer, salesperson);

                Supplier supplier = sellingsuppliers.get(random.nextInt(sellingsuppliers.size()));
                List<Product> catalog = new ArrayList<>(supplier.getProductCatalog().getProductList());
                Collections.shuffle(catalog, random);

                int itemcount = 1 + random.nextInt(MAX_ITEMS_PER_ORDER);
                for (int i = 0; i < itemcount; i++) {
                    Product product = catalog.get(i);
                    int floorprice = product.getFloorPrice();
                    int ceilingprice = product.getCeilingPrice();
                    int actualprice = floorprice + random.nextInt(ceilingprice - floorprice + 1);
                    int quantity = 1 + random.nextInt(MAX_QUANTITY_PER_ITEM);
                    order.newOrderItem(product, actualprice, quantity);
                }
                order.Submit();
            }
        }
    }

    private DataGenerator() {
        // utility class, not meant to be instantiated
    }
}