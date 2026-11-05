/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 *
 * Starter code by Prof. Kal Bugrara.
 * Comments added by Dias Mukhametrakhim, NUID 003185578.
 */
package TheBusiness.OrderManagement;

/**
 * Summary figures extracted from a single order.
 *
 * @author kal bugrara
 */
public class OrderSummary {
    Order subjectorder;
    int salesvolume;
    boolean totalabovetarget;
    int orderpriceperformance;
    int numberofOrderitemsabovetarget;
    int numberofOrdeitemsbelowtarget;
    
public OrderSummary(Order o){
    subjectorder = o;
    salesvolume = o.getOrderTotal();
    totalabovetarget = o.isOrderAboveTotalTarget();
    orderpriceperformance = o.getOrderPricePerformance();
    numberofOrderitemsabovetarget = o.getNumberOfOrderItemsAboveTarget();
}

public Order getSubjectOrder(){ return subjectorder;}

public int getOrderProfit(){
    return orderpriceperformance;
}

}
