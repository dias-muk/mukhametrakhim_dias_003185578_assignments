package UI;

import Module.Person;
import Module.Address;
import Module.BankAccount;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.JTextField;

public final class PersonRelationshipTest {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("Ada Lovelace");

        assertEquals("Ada Lovelace", person.getHomeAddress().getPersonName(),
                "Home address should expose its linked person's current name");
        assertEquals("Ada Lovelace", person.getLocalAddress().getPersonName(),
                "Local address should expose its linked person's current name");
        assertEquals("Ada Lovelace", person.getBankAccount().getPersonName(),
                "Bank account should expose its linked person's current name");

        addressCreatePanelSavesItsFields(person);
        bankAccountCreatePanelSavesItsFields(person);
    }

    private static void addressCreatePanelSavesItsFields(Person person) {
        try {
            Class<?> panelType = Class.forName("UI.CreateAddressJPanel");
            Constructor<?> constructor = panelType.getConstructor(
                    Address.class, String.class);
            Object panel = constructor.newInstance(
                    person.getHomeAddress(), "Create Home Address");
            Method saveAddress = panelType.getMethod("saveAddress", String.class,
                    String.class, String.class, String.class, String.class);

            saveAddress.invoke(panel, "1 Main Street", "Boston", "MA", "02115", "4B");

            assertEquals("1 Main Street", person.getHomeAddress().getStreet(),
                    "Create address panel should save street");
            assertEquals("Boston", person.getHomeAddress().getCity(),
                    "Create address panel should save city");
            viewAddressPanelDisplaysSavedFields(person);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("CreateAddressJPanel should save an Address passed to it", exception);
        }
    }

    private static void viewAddressPanelDisplaysSavedFields(Person person) {
        try {
            Class<?> panelType = Class.forName("UI.ViewAddressJPanel");
            Constructor<?> constructor = panelType.getConstructor(Address.class, String.class);
            Object panel = constructor.newInstance(person.getHomeAddress(), "View Home Address");
            Field streetField = panelType.getDeclaredField("streetField");
            streetField.setAccessible(true);
            assertEquals("1 Main Street", ((JTextField) streetField.get(panel)).getText(),
                    "View address panel should display the saved street");
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("ViewAddressJPanel should display an Address passed to it", exception);
        }
    }

    private static void bankAccountCreatePanelSavesItsFields(Person person) {
        try {
            Class<?> panelType = Class.forName("UI.CreateBankAccountJPanel");
            Constructor<?> constructor = panelType.getConstructor(BankAccount.class, String.class);
            Object panel = constructor.newInstance(person.getBankAccount(), "Create Bank Account");
            Method saveAccount = panelType.getMethod("saveAccount", String.class, String.class,
                    String.class, String.class, String.class);

            saveAccount.invoke(panel, "123456", "Example Bank", "Checking", "0001", "1250.00");

            assertEquals("123456", person.getBankAccount().getAccountNumber(),
                    "Create bank-account panel should save account number");
            assertEquals("Example Bank", person.getBankAccount().getBankName(),
                    "Create bank-account panel should save bank name");
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("CreateBankAccountJPanel should save a BankAccount passed to it", exception);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + ": expected " + expected + ", got " + actual);
        }
    }
}
