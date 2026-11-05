/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 *
 * Starter code by Prof. Kal Bugrara.
 * Comments added by Dias Mukhametrakhim, NUID 003185578.
 */
package TheBusiness.MarketModel;

/**
 * A route to market, such as tv or web.
 *
 * @author kal bugrara
 */
public class Channel {
    String channeltype; //tv, internet, mobile app..
    public Channel(String t){
        channeltype = t;
    }
    public boolean isMatch(String t){
        if (channeltype.equalsIgnoreCase(t))return true;
        else return false;
    }
    public String getChannelType( ){
        return channeltype;
    }
}
