/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Holds every order placed at the coffee shop.
 *
 * @author Dias Mukhametrakhim
 */
public class OrderDirectory {

    private ArrayList<Order> orderList;

    public OrderDirectory() {
        this.orderList = new ArrayList<>();
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public Order newOrder(
            int orderId, 
            Customer customer, 
            Product product, 
            int quantity,
            OrderType type, 
            PaymentMethod payment,
            OrderStatus status, 
            boolean paid
    ) {
        Order order = new Order(orderId, customer, product, quantity,
                                type, payment, status, paid);
        orderList.add(order);
        return order;
    }

    public Order findById(int orderId) {
        for (Order o : orderList) {
            if (o.getOrderId() == orderId) {
                return o;
            }
        }
        return null;
    }

    public ArrayList<Order> findByCustomer(Customer customer) {
        ArrayList<Order> matches = new ArrayList<>();
        for (Order o : orderList) {
            if (o.getCustomer().getCustomerId() == customer.getCustomerId()) {
                matches.add(o);
            }
        }
        return matches;
    }

    public boolean hasOpenOrder(Customer customer) {
        for (Order o : orderList) {
            if (o.getCustomer().getCustomerId() == customer.getCustomerId()
             && o.getStatus() != OrderStatus.COMPLETED) {
                return true;
            }
        }
        return false;
    }

    public int nextOrderId() {
        int max = 5000;
        for (Order o : orderList) {
            if (o.getOrderId() > max) {
                max = o.getOrderId();
            }
        }
        return max + 1;
    }

    public boolean deleteOrder(Order order) {
        return orderList.remove(order);
    }

    public void deleteOrdersOf(Customer customer) {
        Iterator<Order> it = orderList.iterator();
        while (it.hasNext()) {
            if (it.next().getCustomer().getCustomerId() == customer.getCustomerId()) {
                it.remove();
            }
        }
    }
    
    public boolean isProductInUse(Product product) {
        for (Order o : orderList) {
            if (o.getProduct().getProductId() == product.getProductId()) {
                return true;
            }
        }
        
        return false;
    }
}