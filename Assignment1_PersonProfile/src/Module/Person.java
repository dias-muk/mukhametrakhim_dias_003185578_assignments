/*
 * INFO 5100 - Application Engineering and Development
 * Assessment 1 - Person Profile
 * Dias Mukhametrakhim, NUID 003185578
 */
package Module;

/**
 * A person profile and the root of the object model for this assessment.
 *
 * <p>All five of a person's own attributes are stored as {@code String}
 * values. On top of those, a person owns three one-to-one relationships: a
 * home address, a local address, and a bank account. Each of those objects
 * keeps a reference back to its person, so a relationship can be followed
 * from either end.</p>
 *
 * @author dias
 */
public class Person {
    // The five String attributes that belong to the person itself.
    private String name;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
    
    // The three one-to-one relationships. Each object below also holds a
    // reference back to this person, which is what makes each relationship
    // navigable from both ends.
    private Address homeAddress;
    private Address localAddress;
    private BankAccount bankAccount;
    
    /**
     * Creates a person together with the three objects it is related to.
     *
     * <p>The related objects are created here rather than left null so that
     * a form always has something to write into, and each one is pointed
     * straight back at this person to complete the one-to-one link.</p>
     */
    public Person(){
        homeAddress = new Address();
        homeAddress.setPerson(this);

        localAddress = new Address();
        localAddress.setPerson(this);

        bankAccount = new BankAccount();
        bankAccount.setPerson(this);

    }
    

    // --- One-to-one relationships ------------------------------------------
    public Address getHomeAddress() {
        return homeAddress;
    }

    /**
     * Replaces the home address and repairs the link in both directions.
     *
     * @param homeAddress the address to attach to this person
     */
    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
        homeAddress.setPerson(this);
    }

    public Address getLocalAddress() {
        return localAddress;
    }

    /**
     * Replaces the local address and repairs the link in both directions.
     *
     * @param localAddress the address to attach to this person
     */
    public void setLocalAddress(Address localAddress) {
        this.localAddress = localAddress;
        localAddress.setPerson(this);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /**
     * Replaces the bank account and repairs the link in both directions.
     *
     * @param bankAccount the account to attach to this person
     */
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        bankAccount.setPerson(this);
    }

    // --- Attribute getters and setters -------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
