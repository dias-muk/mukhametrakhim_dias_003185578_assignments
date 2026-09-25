/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package Model;

/**
 * Fulfillment type for a coffee shop order.
 *
 * @author Dias Mukhametrakhim
 */
public enum OrderType {
    DINE_IN("Dine-in"),
    TAKEOUT("Takeout"),
    PICKUP("Pickup");

    private final String label;

    OrderType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
