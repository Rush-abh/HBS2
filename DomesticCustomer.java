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
 * Represents an object of type Domestic Customer.
 *
 * This class is a subclass of the Customer class. It inherits all of its
 * parent's methods.
 *
 * @author Orville Shane D Silva & Rushabh Pancholi
 * @version 1.0
 */
public class DomesticCustomer extends Customer {

    private String driverLicenseNo; //holds the customer's driver's license number
    private Date expiryDate; // holds the expiry date of the customer's driver's license in MM/yyyy format

    /**
     * A no argument constructor that creates an object of type DomesticCustomer
     * and initializes its variables with null values
     */
    public DomesticCustomer() {
        super();
        this.driverLicenseNo = null;
        this.expiryDate = null;

    }//end no argument constructor

    /**
     * A parameterized constructor that creates an object of type
     * DomesticCustomer and initializes its variables with the values of the
     * specified parameters
     *
     * @param title The customer's title
     * @param firstName The customer's first name
     * @param lastName The customer's last name
     * @param email The customer's email
     * @param phone The customer's phone number
     * @param driverLicenseNo the customer's driver's license number
     * @param expiryDate the driver's license expiry date in MM/yyyy format
     */
    public DomesticCustomer(String title, String firstName, String lastName,
                            String email, String phone, String driverLicenseNo,
                            Date expiryDate) {
        
        super(title, firstName, lastName, email, phone);
        this.driverLicenseNo = driverLicenseNo;
        this.expiryDate = expiryDate;

    }//end parameterized constructor

    /**
     * A copy constructor that creates an object of type DomesticCustomer and
     * initializes its variables with the values of the 
     * DomesticCustomer object in the specified parameter
     *
     * @param customer the DomesticCustomer object that needs to be copied
     */
    public DomesticCustomer(DomesticCustomer customer) {
        
        super(customer.getTitle(), customer.getFirstName(), customer.getLastName(),
              customer.getEmail(), customer.getPhone());
        
        this.driverLicenseNo = customer.getDriverLicenseNo();
        this.expiryDate = customer.getExpiryDate();

    }//end copy constructor

    /**
     * A method that sets the customer's driver's license number with the
     * specified string
     *
     * @param driverLicenseNo String containing the customer's driver's license
     * number
     */
    public void setDriverLicenseNo(String driverLicenseNo) {
        this.driverLicenseNo = driverLicenseNo;

    }//end setDriverLicenseNo method

    /**
     * A method that returns the customer's driver's license number
     *
     * @return String representing the customer's driver's license number
     */
    public String getDriverLicenseNo() {
        return driverLicenseNo;

    }//end getDriverLicenseNo method

    /**
     * A method that sets the expiry date of the customer's driver's license
     *
     * @param expiryDate 'Date' type object containing the customer's driver's
     * license expiry date
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;

    }//end setExpiryDate method

    /**
     * A method that returns the expiry date of the customer's driver's license
     * in Date (MM/yyyy) format
     *
     * @return 'Date' type representation of the customer's driver's license
     * expiry date
     */
    public Date getExpiryDate() {
        return expiryDate;

    }//end getExpiryDate method

    /**
     * A method that returns the expiry date of the customer's driver's license
     * in String format
     *
     * @return String representing the customer's driver's license expiry date
     */
    public String getExpiryDateInString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        sdf.setLenient(false);

        return sdf.format(expiryDate);

    }//end getExpiryDateInString method

    /**
     * A method that returns a formatted string of the Domestic Customer's
     * details
     *
     * @return Formatted string representing all of the DomesticCustomer class
     * variables
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" %-4s, %-4s,", this.getDriverLicenseNo(),
                                                this.getExpiryDateInString());

    }//end toString method

}//end DomesricCustomer class
