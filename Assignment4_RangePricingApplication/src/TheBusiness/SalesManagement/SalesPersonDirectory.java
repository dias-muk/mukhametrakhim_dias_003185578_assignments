/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 *
 * Starter code by Prof. Kal Bugrara.
 * Comments added by Dias Mukhametrakhim, NUID 003185578.
 */
package TheBusiness.SalesManagement;

import TheBusiness.Business.Business;
import java.util.ArrayList;
import TheBusiness.Personnel.Person;

/**
 * The sales people of the business.
 *
 * @author kal bugrara
 */
public class SalesPersonDirectory {

    Business business;
    ArrayList<SalesPersonProfile> salespersonlist;

    public SalesPersonDirectory(Business d) {

        business = d;
        salespersonlist = new ArrayList();

    }

    public SalesPersonProfile newSalesPersonProfile(Person p) {

        SalesPersonProfile sp = new SalesPersonProfile(p);
        salespersonlist.add(sp);
        return sp;
    }

    public SalesPersonProfile findSalesPerson(String id) {

        for (SalesPersonProfile sp : salespersonlist) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
            return null; //not found after going through the whole list
         }

}
