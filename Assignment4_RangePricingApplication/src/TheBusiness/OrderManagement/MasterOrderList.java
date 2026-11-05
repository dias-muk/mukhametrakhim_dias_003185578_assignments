/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 *
 * Starter code by Prof. Kal Bugrara.
 * Comments added by Dias Mukhametrakhim, NUID 003185578.
 */
package TheBusiness.OrderManagement;

import java.util.ArrayList;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.SalesManagement.SalesPersonProfile;

/**
 * Every order in the business, in the order it was placed. The sales volume of
 * the whole business is the sum of these order totals.
 *
 * @author kal bugrara
 */
public class MasterOrderList {
    ArrayList<Order> orders;
    MasterOrderReport masterorderreport;
    
    public MasterOrderList(){
        orders = new ArrayList();
       
    }
    
    public Order newOrder(CustomerProfile cp){
        Order o= new Order(cp);
        orders.add(o);
        return o;
            
 
    }
    public Order newOrder(CustomerProfile cp, SalesPersonProfile spp){
        Order o= new Order(cp, spp);
        orders.add(o);
        return o;
            
 
    }
    
    public ArrayList<Order> getOrderList(){
        return orders;
    }
    
    public MasterOrderReport generateMasterOrderReport(){
    masterorderreport = new MasterOrderReport();
        
    return masterorderreport;
        
}

public int getSalesVolume(){

int sum = 0;
for(Order order: orders){
    sum = sum + order.getOrderTotal();
}
return sum;
    }

}
