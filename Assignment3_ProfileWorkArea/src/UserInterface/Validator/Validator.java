/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */
package UserInterface.Validator;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Input checks shared by every screen. The isFilled methods also tell the
 * user which field is empty and put the cursor in it.
 *
 * @author Dias Mukhametrakhim
 */
public class Validator {

    public static final String USERNAME_RULE
            = "Username must be 3-20 characters long and use only letters, digits, dots or underscores.";
    public static final String PASSWORD_RULE
            = "Password must be at least 6 characters long, contain at least one letter and one digit, and have no spaces.";

    /** True when the field has text; otherwise warns "<fieldName> cannot be empty." and focuses it. */
    public static boolean isFilled(Component parent, JTextField field, String fieldName) {
        if (field.getText().trim().isEmpty()) {
            warnEmpty(parent, field, fieldName);
            return false;
        }
        return true;
    }

    /** The same check for a password field, which is read with getPassword(). */
    public static boolean isFilled(Component parent, JPasswordField field, String fieldName) {
        if (field.getPassword().length == 0) {
            warnEmpty(parent, field, fieldName);
            return false;
        }
        return true;
    }

    public static boolean isValidUsername(String username) {
        return username.matches("[A-Za-z0-9._]{3,20}");
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6
                && password.matches(".*[A-Za-z].*")
                && password.matches(".*[0-9].*")
                && !password.matches(".*\\s.*");
    }

    private static void warnEmpty(Component parent, JTextField field, String fieldName) {
        JOptionPane.showMessageDialog(parent, fieldName + " cannot be empty.",
                "Missing information", JOptionPane.WARNING_MESSAGE);
        field.requestFocus();
    }
}