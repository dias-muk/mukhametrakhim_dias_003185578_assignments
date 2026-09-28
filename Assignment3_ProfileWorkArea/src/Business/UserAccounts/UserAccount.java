/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */
package Business.UserAccounts;

import Business.Profiles.Profile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The login of one profile: username, password, whether it may log in, and
 * when it was last used and last changed.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class UserAccount {
    
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Profile profile;
    String username;
    String password;
    private boolean active = true;
    private LocalDateTime lastAccessed;     // null until the first login
    private LocalDateTime lastUpdated;
    
    public UserAccount (Profile profile, String un, String pw){
        username = un;
        password = pw;
        this.profile = profile;
        lastUpdated = LocalDateTime.now();
    }

    public String getPersonId() {
        return profile.getPerson().getPersonId();
    }

    public String getUserLoginName() {
        return username;
    }

    public boolean isMatch(String id) {
        return getPersonId().equals(id);
    }

    public boolean IsValidUser(String un, String pw) {
        return username.equalsIgnoreCase(un) && password.equals(pw);
    }

    public String getRole() {
        return profile.getRole();
    }

    public Profile getAssociatedPersonProfile() {
        return profile;
    }
    
       public void setUsername(String username) {
        if (!this.username.equals(username)) {
            this.username = username;
            lastUpdated = LocalDateTime.now();
        }
    }

    public void setPassword(String password) {
        if (!this.password.equals(password)) {
            this.password = password;
            lastUpdated = LocalDateTime.now();
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (this.active != active) {
            this.active = active;
            lastUpdated = LocalDateTime.now();
        }
    }

    /** "Active" or "Disabled", for tables and profile pages. */
    public String getStatus() {
        return active ? "Active" : "Disabled";
    }

    /** Called on every successful login. */
    public void recordLogin() {
        lastAccessed = LocalDateTime.now();
    }

    public String getLastAccessedText() {
        return lastAccessed == null ? "Never" : lastAccessed.format(FORMAT);
    }

    public String getLastUpdatedText() {
        return lastUpdated.format(FORMAT);
    }

    @Override
    public String toString() {
        return getUserLoginName();
    }
        
}

