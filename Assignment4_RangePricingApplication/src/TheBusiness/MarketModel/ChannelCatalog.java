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
 * All channels the business can sell through.
 *
 * @author kal bugrara
 */
public class ChannelCatalog {
    ArrayList<Channel> channellist ;
    public ChannelCatalog(){
    channellist = new ArrayList();
    }
    
    public Channel newChannel(String type){
        
        Channel c = new Channel(type);
        channellist.add(c);
        return c;
    }
    
    public Channel findChannel(String type){
        
        for( Channel c: channellist ){
            
            if (c.getChannelType().equalsIgnoreCase(type)) return c;
            
        }
        return null; //not found
    }
    
    
}
