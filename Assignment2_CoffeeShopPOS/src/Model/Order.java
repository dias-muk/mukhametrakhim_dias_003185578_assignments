package Model;

import java.time.LocalDateTime;

/**
 *
 * @author dias
 */
public class Order {
    private int orderId;
    private LocalDateTime dateTime;
    private OrderType type;
    private PaymentMethod payment;
    private OrderStatus status;
    private int quantity;
    private boolean paid;
    private Customer customer;
    private Product product;
    
    public Order(
            int orderId, 
            Customer customer, 
            Product product, 
            int quantity,
            OrderType type, 
            PaymentMethod payment, 
            OrderStatus status, 
            boolean paid
    ) {
        this.orderId = orderId;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.type = type;
        this.payment = payment;
        this.status = status;
        this.paid = paid;
        this.dateTime = LocalDateTime.now();
    }
    
    public double getTotal(){
        return product.getPrice() * quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public PaymentMethod getPayment() {
        return payment;
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
}
