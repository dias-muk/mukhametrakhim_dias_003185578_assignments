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