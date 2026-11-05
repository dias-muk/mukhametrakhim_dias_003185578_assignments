/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package Model;

/**
 * Where an order is in the preparation workflow.
 *
 * @author Dias Mukhametrakhim
 */
public enum OrderStatus {
    PENDING("Pending"),
    PREPARING("Preparing"),
    READY("Ready"),
    COMPLETED("Completed");

    private final String label;

    OrderStatus(String label) {
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