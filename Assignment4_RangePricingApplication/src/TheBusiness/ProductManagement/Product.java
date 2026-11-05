/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 *
 * Starter code by Prof. Kal Bugrara.
 * Comments added by Dias Mukhametrakhim, NUID 003185578.
 */
package TheBusiness.ProductManagement;

import java.util.ArrayList;
import TheBusiness.OrderManagement.OrderItem;

/**
 * A product sold by a supplier. Range pricing means it carries three prices: a
 * floor it should not be sold below, a ceiling it should not exceed, and a
 * target the business aims for. The product also keeps every order item that
 * sold it, which is how the reports measure its performance.
 *
 * @author kal bugrara
 */
public class Product {
    private String name;
    private int floorPrice;
    private int ceilingPrice;
    private int targetPrice;
    ArrayList<OrderItem> orderitems;
    
    public Product( int fp, int cp, int tp) {

        floorPrice = fp;
        ceilingPrice = cp;
        targetPrice = tp;
        orderitems = new ArrayList();
        
    }
        
    public Product(String n, int fp, int cp, int tp) {
        name = n;
        floorPrice = fp;
        ceilingPrice = cp;
        targetPrice = tp;
        orderitems = new ArrayList();
    }
    
    public Product updateProduct(int fp, int cp, int tp) {
        floorPrice = fp;
        ceilingPrice = cp;
        targetPrice = tp;
        return this; //returns itself
    }
    
    public int getTargetPrice() {
        return targetPrice;
    }
    
    public void addOrderItem(OrderItem oi){     
        orderitems.add(oi);
    }
    
    //Number of item sales above target 
    public int getNumberOfProductSalesAboveTarget(){
        int sum = 0;
        for (OrderItem oi: orderitems){
            if(oi.isActualAboveTarget()==true) sum = sum +1;
        }
        return sum;
    }
    
    public int getNumberOfProductSalesBelowTarget(){
        int sum = 0;
        for (OrderItem oi: orderitems){
            if(oi.isActualBelowTarget()==true) sum = sum +1;
        }
        return sum;
    }    
    
    public boolean isProductAlwaysAboveTarget(){
        
        for (OrderItem oi: orderitems){
            if(oi.isActualAboveTarget()==false) return false; //
        }
        return true;
    }
    //calculates the revenues gained or lost (in relation to the target)
    //For example, if target is at $2000 and actual is $2500 then revenue gained
    // is $500 above the expected target. If the actual is $1800 then the lose will be $200
    // Add all these difference to get the total including wins and loses
    
    public int getOrderPricePerformance() {
        int sum = 0;
        for (OrderItem oi : orderitems) {
            sum = sum + oi.calculatePricePerformance();     //positive and negative values       
        }
        return sum;
    }
    
    public int getSalesVolume() {
        int sum = 0;
        for (OrderItem oi : orderitems) {
            sum = sum + oi.getOrderItemTotal();     //positive and negative values       
        }
        return sum;
    }
    
    public void setName(String n){
        name = n;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public int getFloorPrice(){
        return floorPrice;
    }
    
    public int getCeilingPrice(){
        return ceilingPrice;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<OrderItem> getOrderItems(){
        return orderitems;
    }

}
