/*
 * INFO 5100 - Application Engineering and Development
 * Assessment 1 - Person Profile
 * Dias Mukhametrakhim, NUID 003185578
 */
package Module;

/**
 * A postal address belonging to exactly one {@link Person}.
 *
 * <p>The same class serves as both the home address and the local address.
 * The two are told apart by which field of {@link Person} holds them rather
 * than by type, because their attributes are identical and a second class
 * would only duplicate this one.</p>
 *
 * <p>Besides the five address attributes, the owner's name is kept as its own
 * {@code String} attribute, and {@code person} carries the one-to-one link
 * back to the owner.</p>
 *
 * @author dias
 */
public class Address {
    // The five attributes of the address itself.
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String unitNumber;
    // The owner's name, held as its own String attribute as the assessment
    // requires, rather than only being read through the person reference.
    private String personName; 
    
    // The one-to-one link back to the owner of this address.
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
     * Links this address to a person and copies that person's name into
     * {@link #personName}.
     *
     * <p>The name is copied at the moment the link is made, so the stored
     * attribute reflects the person's name as it stood then.</p>
     *
     * @param person the owner of this address
     */
    public void setPerson(Person person) {
        this.person = person;
        if (person != null) {
            this.personName = person.getName();
        }
    }

    // --- Address attribute getters and setters -----------------------------
    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
