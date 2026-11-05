/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package UI;

import Model.Order;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Input checks shared by every form in the application.
 *
 * <p>Each method takes the trimmed text of one field and answers whether it
 * can be turned into the value the model needs. Every parse is wrapped in a
 * try/catch here, so a bad value ends up as an error dialog in the panel
 * instead of a stack trace in the terminal.</p>
 *
 * @author Dias Mukhametrakhim
 */
public class Validator {
    
    private Validator(){}
    
     /**
     * Letters, spaces, apostrophes and hyphens, starting with a letter,
     * e.g. "Mary-Jane" or "O'Neil".
     */
    public static boolean isValidName(String text) {
        return text.matches("[A-Za-z][A-Za-z '-]*");
    }
    
    /** A whole number greater than 0 (IDs, quantity, preparation time). */
    public static boolean isPositiveInt(String text){
        try {
            return Integer.parseInt(text) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /** A whole number of 0 or more (products in stock). */
    public static boolean isNonNegativeInt(String text) {
        try {
            return Integer.parseInt(text) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /** A decimal number greater than 0 (price). */
    public static boolean isPositiveDouble(String text) {
        try {
            double value = Double.parseDouble(text);
            return value > 0 && !Double.isInfinite(value);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Exactly 10 digits and not starting with 0, because the contact is
     * stored as a long and a long would silently drop a leading zero.
     */
    public static boolean isValidContact(String text) {
        return text.matches("[1-9][0-9]{9}");
    }

    /** A date and time typed as Order.DATE_TIME_FORMAT, e.g. 2026-09-23 14:05. */
    public static boolean isValidDateTime(String text) {
        try {
            LocalDateTime.parse(text, Order.DATE_TIME_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    
}
