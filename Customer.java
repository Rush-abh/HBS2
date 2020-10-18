/*
 * Student Names: Orville Shane D Silva & Rushabh Pancholi
 * Student IDs: 12091423 & 12096494 respectively
 * Course Unit: COIT20256(HT1, 2019)DATA STRUCTURES AND ALGORITHMS
 * Tutor: Ranasundara Senarathna
 * Campus: Melbourne
 */

/**
 *Represents an object of type Customer.
 *
 * @author Orville Shane D Silva & Rushabh Pancholi
 * @version 1.0
 */
public class Customer {

    private String title; //Holds the title of the customer
    private String firstName; // holds the first name of the customer
    private String lastName; // holds the last name of the customer
    private String email; // holds the email of the customer
    private String phone; // holds the phone number of the customer

    /**
     * A no argument constructor that creates an object of type Customer and
     * initializes its variables with null values
     */
    public Customer() {
        this.title = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.phone = null;

    } //end no argument constructor

    /**
     * A parameterized constructor that creates an object of type Customer and
     * initializes its variables with the values of the specified parameters
     *
     * @param title The customer's title
     * @param firstName The customer's first name
     * @param lastName The customer's last name
     * @param email The customer's email
     * @param phone The customer's phone number
     */
    public Customer(String title, String firstName, String lastName, String email,
                    String phone) {
        
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;

    }//end parameterized constructor

    /**
     * A method that sets the customer's title with the specified string
     *
     * @param title The string containing customer's title
     */
    public void setTitle(String title) {
        this.title = title;

    } //end setTitle method

    /**
     * A method that returns the customer's title
     *
     * @return String representing the customer's title
     */
    public String getTitle() {
        return title;

    }//end getTitle method

    /**
     * A method that sets the customer's first name with the specified string
     *
     * @param firstName String containing the customer's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;

    }//end setFirstName method

    /**
     * A method that returns the customer's first name
     *
     * @return String representing the customer's first name
     */
    public String getFirstName() {
        return firstName;

    }//end getFirstName method

    /**
     * A method that sets the customer's last name with the specified string
     *
     * @param lastName String containing the customer's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;

    }//end setLastName method

    /**
     * A method that returns the customer's last name
     *
     * @return String representing the customer's last name
     */
    public String getLastName() {
        return lastName;

    }//end getLastName method

    /**
     * A method that sets the customer's email with the specified string
     *
     * @param email String containing the customer's email
     */
    public void setEmail(String email) {
        this.email = email;

    }//end setEmail method

    /**
     * A method that returns the customer's email
     *
     * @return String representing the customer's email
     */
    public String getEmail() {
        return email;

    }//end of getEmail method

    /**
     * A method that sets the customer's phone number with the specified string
     *
     * @param phone String containing the customer's phone
     */
    public void setPhone(String phone) {
        this.phone = phone;

    }//end setPhone method

    /**
     * A method that returns the customer's phone number
     *
     * @return String representing the customer's phone number
     */
    public String getPhone() {
        return phone;

    }//end getPhone method

    /**
     * A method that returns a formatted string of the customer's details
     *
     * @return Formatted string representing all of the customer class variables
     */
    @Override
    public String toString() {
        return String.format("%n%s %s %s, %s, %s,", this.getTitle(), this.getFirstName(),
                             this.getLastName(), this.getEmail(), this.getPhone());

    }//end toString method

}//end Customer class
