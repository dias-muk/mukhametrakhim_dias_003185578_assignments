/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 *
 * Starter code by Prof. Kal Bugrara.
 * Comments added by Dias Mukhametrakhim, NUID 003185578.
 */
package TheBusiness.MarketModel;

import java.util.ArrayList;

/**
 * A group of customers the business sells to, together with the channels that
 * are valid for reaching it.
 *
 * @author kal bugrara
 */
public class Market {

    String name;
    ArrayList<Channel> validchannels;
    ArrayList<String> characteristics; //a way to describe in plain language what is that group
    ArrayList<Market> submarkets;

    public Market(String m) {
        name = m;
        characteristics = new ArrayList();
        submarkets = new ArrayList();
        validchannels = new ArrayList();
    }

    public void addCharactersitic(String c) {
        characteristics.add(c);
    }

    public void addValidChannel(Channel c) {

        validchannels.add(c);
    }
    

}
