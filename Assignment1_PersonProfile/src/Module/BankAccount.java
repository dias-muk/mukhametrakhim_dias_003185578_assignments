/*
 * INFO 5100 - Application Engineering and Development
 * Assessment 1 - Person Profile
 * Dias Mukhametrakhim, NUID 003185578
 */
package Module;

/**
 * A bank account belonging to exactly one {@link Person}.
 *
 * <p>All five account attributes are stored as {@code String} values,
 * including the balance, which is kept as text rather than a number because
 * the assessment requires every attribute to be a {@code String}.</p>
 *
 * <p>As with {@link Address}, the owner's name is kept as its own attribute
 * and {@code person} carries the one-to-one link back to the owner.</p>
 *
 * @author dias
 */
public class BankAccount {
    // The five attributes of the account itself.
    private String accountNumber;
    private String bankName;
    private String accountType;
    private String branchCode;
    private String balance;
    // The owner's name, held as its own String attribute as the assessment
    // requires, rather than only being read through the person reference.
    private String personName;
    
    // The one-to-one link back to the owner of this account.
    private Person person;
    
    /**
     * Returns the owner's name as it was when the link was made.
     *
     * @return the stored person name, or null if no person has been linked
     */
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Person getPerson() {
        return person;
    }

    /**
     * Links this account to a person and copies that person's name into
     * {@link #personName}.
     *
     * <p>The name is copied at the moment the link is made, so the stored
     * attribute reflects the person's name as it stood then.</p>
     *
     * @param person the owner of this account
     */
    public void setPerson(Person person) {
        this.person = person;
        if (person != null) {
            this.personName = person.getName();
        }
    }

    // --- Account attribute getters and setters -----------------------------
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
