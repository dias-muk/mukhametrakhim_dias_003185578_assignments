/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */
package Business.UserAccounts;

import Business.Profiles.Profile;

import java.util.ArrayList;

/**
 * Every user account. Creates accounts, checks logins, and finds an account
 * by person, username or profile.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class UserAccountDirectory {
    
    ArrayList<UserAccount> useraccountlist ;
    
    public UserAccountDirectory (){      
        useraccountlist = new ArrayList();
    }

    public UserAccount newUserAccount(Profile p, String un, String pw) {
        UserAccount ua = new UserAccount (p,  un,  pw);
        useraccountlist.add(ua);
        return ua;
    }

    public UserAccount findUserAccount(String id) {
        for (UserAccount ua : useraccountlist) {
            if (ua.isMatch(id)) {
                return ua;
            }
        }
        
        return null; //not found after going through the whole list
    }
    
    public UserAccount AuthenticateUser(String un, String pw) {
        for (UserAccount ua : useraccountlist) {
            if (ua.IsValidUser(un, pw)) {
                return ua;
            }
        }
        
        return null; //not found after going through the whole list
    }
    
    /** The account with this username, ignoring case the same way login does. */
    public UserAccount findByUsername(String un) {
        for (UserAccount ua : useraccountlist) {
            if (ua.getUserLoginName().equalsIgnoreCase(un)) {
                return ua;
            }
        }
        return null;
    }

    /** The account that belongs to this profile, or null if it has none. */
    public UserAccount findByProfile(Profile p) {
        for (UserAccount ua : useraccountlist) {
            if (ua.getAssociatedPersonProfile() == p) {
                return ua;
            }
        }
        return null;
    }

    public void removeUserAccount(UserAccount ua) {
        useraccountlist.remove(ua);
    }
    
    public ArrayList<UserAccount> getUserAccountList(){
        return useraccountlist;
    }
}
