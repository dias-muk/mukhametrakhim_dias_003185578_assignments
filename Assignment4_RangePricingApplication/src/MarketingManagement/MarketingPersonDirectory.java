/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 *
 * Starter code by Prof. Kal Bugrara.
 * Comments added by Dias Mukhametrakhim, NUID 003185578.
 */
package MarketingManagement;

import TheBusiness.Business.Business;
import java.util.ArrayList;
import TheBusiness.Personnel.Person;

/**
 * The marketing people of the business.
 *
 * @author kal bugrara
 */
public class MarketingPersonDirectory {

    Business business;
    ArrayList<MarketingPersonProfile> marketingpersonlist;

    public MarketingPersonDirectory(Business d) {

        business = d;
        marketingpersonlist = new ArrayList();

    }

    public MarketingPersonProfile newMarketingPersonProfile(Person p) {

        MarketingPersonProfile sp = new MarketingPersonProfile(p);
        marketingpersonlist.add(sp);
        return sp;
    }

    public MarketingPersonProfile findMarketingPerson(String id) {

        for (MarketingPersonProfile sp : marketingpersonlist) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
            return null; //not found after going through the whole list
         }

}
