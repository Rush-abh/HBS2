/*
 * Student Names: Orville Shane D Silva & Rushabh Pancholi
 * Student IDs: 12091423 & 12096494 respectively
 * Course Unit: COIT20256(HT1, 2019)DATA STRUCTURES AND ALGORITHMS
 * Tutor: Ranasundara Senarathna
 * Campus: Melbourne
 */

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an object of type International Customer.
 *
 * This class is a subclass of the Customer class. It inherits all of its
 * parent's methods.
 *
 * @author Orville Shane D Silva & Rushabh Pancholi
 * @version 1.0
 */
public class InternationalCustomer extends Customer {

    private String passportNumber; //holds the customer's passport number
    private String country; //holds the customer's passport country of origin 
    private Date expiryDate; //holds expiry date of passport in MM/yyyy format

    /**
     * A no argument constructor that creates an object of type
     * InternationalCustomer and initializes its variables with null values
     */
    public InternationalCustomer() {
        super();
        this.passportNumber = null;
        this.country = null;
        this.expiryDate = null;

    }//end no argument constructor

    /**
     * A parameterized constructor that creates an object of type
     * InternationalCustomer and initializes its variables with the values of
     * the specified parameters
     *
     * @param title The customer's title
     * @param firstName The customer's first name
     * @param lastName The customer's last name
     * @param email The customer's email
     * @param phone The customer's phone number
     * @param passportNumber The customer's passport number
     * @param country the country of origin of the customer's passport
     * @param expiryDate the expiry date of the passport
     */
    public InternationalCustomer(String title, String firstName, String lastName,
                                 String email, String phone, String passportNumber,
                                 String country, Date expiryDate) {
        
        super(title, firstName, lastName, email, phone);
        this.passportNumber = passportNumber;
        this.country = country;
        this.expiryDate = expiryDate;

    }//end parameterized constructor

    /**
     * A copy constructor that creates an object of type InternationalCustomer
     * and initializes its variables with the values of the 
     * InternationalCustomer object in the specified parameter
     *
     * @param customer the InternationalCustomer object that needs to be copied
     */
    public InternationalCustomer(InternationalCustomer customer) {
        
        super(customer.getTitle(), customer.getFirstName(), customer.getLastName(),
              customer.getEmail(), customer.getPhone());
        
        this.passportNumber = customer.getPassportNumber();
        this.country = customer.getCountry();
        this.expiryDate = customer.getExpiryDate();

    }//end copy constructor

    /**
     * A method that sets the customer's passport number with the specified
     * string
     *
     * @param passportNumber String containing the customer's passport number
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;

    }//end setPassportNumber method

    /**
     * A method that returns the customer's passport number
     *
     * @return String representing the customer's passport number
     */
    public String getPassportNumber() {
        return passportNumber;

    }//end getPassportNumber  method

    /**
     * A method that sets the passport's country of origin with the specified
     * string
     *
     * @param country String containing the passport's country of origin
     */
    public void setCountry(String country) {
        this.country = country;

    }//end setCountry method

    /**
     * A method that returns the passport's country of origin
     *
     * @return String representing the passport's country of origin
     */
    public String getCountry() {
        return country;

    }//end getCountry method

    /**
     * A method that sets the passport's expiry date with the specified date
     *
     * @param expiryDate 'Date' type object containing the passport's expiry
     * date
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;

    }//end setExpiryDate method

    /**
     * A method that returns the passport's expiry date in date (MM/yyyy) format
     *
     * @return 'date' type object representing the passport's expiry date
     */
    public Date getExpiryDate() {
        return expiryDate;

    }//end getExpiryDate method

    /**
     * A method that returns the passport's expiry date in String format
     *
     * @return String representing the passport's expiry date
     */
    public String getExpiryDateInString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        sdf.setLenient(false);

        return sdf.format(expiryDate);

    }//end getEx[iryDateInString method

    /**
     * A method that returns a formatted string of the Internal Customer's
     * details
     *
     * @return Formatted string representing all of the InternationalCustomer
     * class variables
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" %-4s, %-4s, %-4s,", this.getPassportNumber(),
                                                this.getCountry(), this.getExpiryDateInString());

    }//end toString method

}//end InternationalCustomer class
