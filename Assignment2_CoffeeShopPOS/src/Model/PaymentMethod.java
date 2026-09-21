package Model;

/**
 * How the customer paid for an order.
 *
 * @author Dias Mukhametrakhim
 */
public enum PaymentMethod {
    CASH("Cash"),
    CARD("Card"),
    MOBILE("Mobile");

    private final String label;

    PaymentMethod(String label) {
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
