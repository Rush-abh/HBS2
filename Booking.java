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
 * Represents an object of type Booking.
 *
 * This class holds the relevant information pertaining to a booking such as the
 * check-in and check-out dates, the number of guests, the type of room opted
 * for, the going rate of that particular room type, the type of customer who
 * made the booking and an object holding the customer details
 *
 * @author Orville Shane D Silva & Rushabh Pancholi
 * @version 1.0
 */
public class Booking {

    private Date checkIn; //holds the check-in date in dd/MM/yyyy format
    private Date checkOut; //holds the check-out date in dd/MM/yyyy format
    private int guestNumber; //holds the number of guests for the booking
    private String roomType; //holds the type of room the booking was made for
    private double roomRate; //holds the going rate for the room type
    private Customer customer; //holds the details of the customer making the booking
    private String customerType; //holds the type of customer who made the booking

    /**
     * A no argument constructor that creates an object of type Booking and
     * initializes its variables with null values
     */
    public Booking() {
        this.checkIn = null;
        this.checkOut = null;
        this.guestNumber = 0;
        this.roomType = null;
        this.roomRate = 0;
        this.customer = new Customer();
        this.customerType = null;

    }//end no argument constructor

    /**
     * A parameterized constructor that creates an object of type Booking and
     * initializes its variables with the values of the specified parameters
     *
     * @param checkIn The check-in date of the booking
     * @param checkOut The check-out date of the booking
     * @param guestNumber The number of guests for the booking
     * @param roomType The type of room the booking was made for
     * @param roomRate The going rate for the type of room
     * @param customer The customer who made the booking
     * @param customerType the type of customer who made the booking
     */
    public Booking(Date checkIn, Date checkOut, int guestNumber, String roomType, 
                   int roomRate, Customer customer, String customerType) {
        
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestNumber = guestNumber;
        this.roomType = roomType;
        this.roomRate = roomRate;
        this.customer = customer;
        this.customerType = customerType;

    }//end parameterized constructor

    /**
     * A copy constructor that creates an object of type Booking and initializes
     * its variables with the values of the Booking object in the specified
     * parameter
     *
     * @param booking the Booking object that needs to be copied
     */
    public Booking(Booking booking) {
        this.checkIn = booking.getCheckIn();
        this.checkOut = booking.getCheckOut();
        this.guestNumber = booking.getGuestNumber();
        this.roomType = booking.getRoomType();
        this.roomRate = booking.getRoomRate();
        this.customer = booking.getCustomer();
        this.customerType = booking.getCustomerType();

    }//end copy constructor

    /**
     * A method that sets the check-in date of the booking with the specified
     * Date in dd/MM/yyyy format
     *
     * @param checkIn 'Date' type object containing the check-in date
     */
    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;

    }//end setCheckIn method

    /**
     * A method that returns the check-in date for a booking
     *
     * @return 'Date' type object representing the check-in date
     */
    public Date getCheckIn() {
        return checkIn;

    }//end getCheckIn method

    /**
     * A method that sets the check-out date of the booking with the specified
     * Date in dd/MM/yyyy format
     *
     * @param checkOut 'Date' type object containing the check-out date
     */
    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;

    }//end setCheckOut method

    /**
     * A method that returns the check-out date for a booking
     *
     * @return 'Date' type object representing the check-out date
     */
    public Date getCheckOut() {
        return checkOut;

    }//end getCheckOut method

    /**
     * A method that sets the number of guests for the booking with the
     * specified value
     *
     * @param guestNumber 'int' type object containing the number of guests
     */
    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;

    }//end setGuestNumber method

    /**
     * A method that returns the number of guests for a booking
     *
     * @return int value representing the number of guests
     */
    public int getGuestNumber() {
        return guestNumber;

    }//end getGuestNumber method

    /**
     * A method that sets the room type for the booking with the specified value
     *
     * @param roomType String containing the room type
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;

    }//end setRoomType method

    /**
     * A method that returns the type of room chosen in a booking
     *
     * @return String representing the room type
     */
    public String getRoomType() {
        return roomType;

    }//end getRoomType method

    /**
     * A method that sets the rate of the room type chosen for the booking with
     * the specified value
     *
     * @param roomRate 'double' type object containing the rate for the room
     * type
     */
    public void setRoomRate(double roomRate) {
        this.roomRate = roomRate;

    }//end setRoomRate method

    /**
     * A method that returns the rate of the room chosen in a booking
     *
     * @return 'double' type value representing the room rate
     */
    public double getRoomRate() {
        return roomRate;

    }//end getRoomRate method

    /**
     * A method that sets the customer who made the booking with the specified
     * customer object
     *
     * @param customer 'Customer' type object containing the details of the
     * customer who made the booking
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;

    }//end setCustomer method

    /**
     * A method that returns the customer who made the booking
     *
     * @return 'Customer' type object representing the customer who made the
     * booking
     */
    public Customer getCustomer() {
        return customer;

    }//end getCustomer method

    /**
     * A method that sets the type of the customer who made the booking with the
     * specified type
     *
     * @param customerType String containing the type of customer who made the
     * booking
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;

    }//end setCustomerType method

    /**
     * A method that returns the type of the customer who made the booking
     *
     * @return String representing the type of the customer who made the booking
     */
    public String getCustomerType() {
        return this.customerType;

    }//end of customerType

    /**
     * A method that returns the check-in date in String format
     *
     * @return String representing the check-in date;
     */
    public String getCheckInDateInString() {
        return getDateInString(this.checkIn);

    }//end getCheckInDateInString method

    /**
     * A method that returns the check out date in String format
     *
     * @return String representing the check-out date
     */
    public String getCheckOutDateInString() {
        return getDateInString(this.checkOut);

    }//end getCheckOutDateInString method

    /**
     * A method that takes in a 'date' type object and returns it in
     * "dd/MM/yyyy" format
     *
     * @param date The date to be parsed in "dd/MM/yyyy" format
     * @return String representing the date passed as parameter
     */
    public String getDateInString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        return sdf.format(date);

    }//end getDateInString method

    /**
     * A method that returns the appropriate string of booking and customer
     * details based on the type of customer who made the booking
     *
     * @return String representing the booking and customer details
     */
    @Override
    public String toString() {
        if (customerType.equalsIgnoreCase(BookingGUI.DOM_CUSTOMER_COMMAND)) {
            DomesticCustomer customer = (DomesticCustomer) this.getCustomer();
            return customer.toString() + String.format("%nCheck-in %s, Check-out %s, No. of Guests: %d, "
                                                      + "%s, $%.2f per night", this.getCheckInDateInString(),
                                                      this.getCheckOutDateInString(), this.getGuestNumber(),
                                                      this.getRoomType(), this.getRoomRate());
        } else {
            InternationalCustomer customer = (InternationalCustomer) this.getCustomer();
            return customer.toString() + String.format("%nCheck-in %s, Check-out %s, No. of Guests: %d, "
                                                      + "%s, $%.2f per night", this.getCheckInDateInString(),
                                                      this.getCheckOutDateInString(), this.getGuestNumber(), 
                                                      this.getRoomType(), this.getRoomRate());

        }
    }//end toString method

}//end Booking class
