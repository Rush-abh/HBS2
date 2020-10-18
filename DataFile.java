/*
 * Student Names: Orville Shane D Silva & Rushabh Pancholi
 * Student IDs: 12091423 & 12096494 respectively
 * Course Unit: COIT20256(HT1, 2019)DATA STRUCTURES AND ALGORITHMS
 * Tutor: Ranasundara Senarathna
 * Campus: Melbourne
 */

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 * Represents an object of type DataFile.
 *
 * This class takes handle the opening, creation, writing and closing of files.
 * It takes in the file name of the file to be written.
 *
 * @author Rushabh Pancholi
 * @version 1.0
 */
public class DataFile {

    private String fileName; //holds the file name
    FileWriter file; //holds the reference to the file for writing 
    PrintWriter printWriter; //used to print to the file in a particular string format
    BufferedWriter bufferedWriter; //used to output text to charater output stream
    private final String DOM_CUSTOMER = "Domestic Customer";
    
    
     // Constants for the different types of rooms and their rates
    private final String SUPERIOR_QUEEN = "Superior Queen";
    private final String BREAKFAST_QUEEN = "Breakfast Queen";
    private final String DELUXE_KING = "Deluxe King";
    private final double SUP_QUEEN_RATE = 250;
    private final double BREAK_QUEEN_RATE = 270;
    private final double DEL_KING_RATE = 240;

    /**
     * A no argument constructor that creates an object of type DataFile and
     * initializes its variables with a null value
     */
    public DataFile() {
        this.fileName = null;

    }//end no argument constructor

    /**
     * A parameterized constructor that creates an object of type dataFile and
     * initializes its variable with the value of the specified string
     *
     * @param fileName the name of the file to write to
     */
    public DataFile(String fileName) {
        this.fileName = fileName;

    }//end parameterized constructor

    /**
     * A method that sets the file name of the file to be written with the
     * specified string
     *
     * @param fileName String containing the file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;

    }//end setFileName;

    /**
     * A method that returns the name of the file
     *
     * @return String representing the file name
     */
    public String getFileName() {
        return fileName;

    }//end getFileName

    /**
     * A method that opens the file to write data to
     *
     * catches exceptions FileNotFound and IOException
     *
     */
    public void openFile() {

        try {
            file = new FileWriter(this.fileName, true);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error - File Not Found", "Save Data", 
                                         JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error Opening File", "Save Data",
                                         JOptionPane.ERROR_MESSAGE);
        }
    }//end openFile method
    
    public boolean readData(LinkedList<Booking> booking)
    {
        SimpleDateFormat sdf = null;
        
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String title; 
            String fName;
            String lName;
            String email;
            String phone;
            String legal;
            String country;
            Date expiryDate;
            Date checkInDate;
            Date checkOutDate;
            int guestNum;
            String roomType;
            double roomRate;
            String customerType;
           
            int i = 0;
           String next = bufferedReader.readLine();
            while(next!=null )
            {
                String temp[] = next.split(",");
                Booking tempBooking = new Booking();
                customerType = temp[0];
                title  = temp[1];
                fName = temp[2];
                lName = temp[3];
                email = temp[4];
                phone = temp[5];
                legal = temp[6];
                if(temp[0].equalsIgnoreCase(DOM_CUSTOMER))
                {
                    sdf = new SimpleDateFormat("MM/yyyy");
                    expiryDate = sdf.parse(temp[7]);
                    sdf = new SimpleDateFormat("dd/MM/yyyy");
                    checkInDate = sdf.parse(temp[8]);
                    checkOutDate = sdf.parse(temp[9]);
                    guestNum = Integer.parseInt(temp[10]);
                    roomType = temp[11];
                    roomRate = Double.parseDouble(temp[12]);
                    DomesticCustomer domCustomer = new DomesticCustomer(title,
                                                   fName,lName,email,phone,
                                                   legal,expiryDate);
                    tempBooking.setCustomerType(customerType);
                    tempBooking.setCustomer(domCustomer);
                     tempBooking.setCheckIn(checkInDate);
                tempBooking.setCheckOut(checkOutDate);
                tempBooking.setGuestNumber(guestNum);
                tempBooking.setRoomType(roomType);
                tempBooking.setRoomRate(roomRate);
                  booking.add(tempBooking);
                
                next = bufferedReader.readLine();
                
                    
                }
                else
                {
                    country = temp[7];
                    sdf = new SimpleDateFormat("MM/yyyy");
                    expiryDate = sdf.parse(temp[8]);
                    sdf = new SimpleDateFormat("dd/MM/yyyy");
                    checkInDate = sdf.parse(temp[9]);
                    checkOutDate = sdf.parse(temp[10]);
                    guestNum = Integer.parseInt(temp[11]);
                    roomType = temp[12];
                    roomRate = Double.parseDouble(temp[13]);
                    InternationalCustomer intlCustomer = new InternationalCustomer (
                                                         title,fName,lName,email,
                                                         phone,legal,country,
                                                         expiryDate);
                    tempBooking.setCustomerType(customerType);
                    tempBooking.setCustomer(intlCustomer);
                     tempBooking.setCheckIn(checkInDate);
                tempBooking.setCheckOut(checkOutDate);
                tempBooking.setGuestNumber(guestNum);
                tempBooking.setRoomType(roomType);
                tempBooking.setRoomRate(roomRate);
                  booking.add(tempBooking);
                
                next = bufferedReader.readLine();
                
                }
               
                
            }
            bufferedReader.close();
            return true;
            
        } catch (FileNotFoundException ex) {
           return false;
        } catch (IOException | ParseException ex) {
           return false;
        }
        
        
    }

    /**
     * A method that writes data to the file. Returns true if successful and
     * false if unsuccessful
     *
     * Catches any type of exception from Exception and its subclasses
     *
     * @param bookingList The list of bookings to write to the file
     * @return boolean value
     */
    public boolean writeData(ArrayList<Booking> bookingList) {
        bufferedWriter = new BufferedWriter(file);
        printWriter = new PrintWriter(bufferedWriter);
        final int bookingListPosition = bookingList.size() - 1;
        Booking booking = new Booking(bookingList.get(bookingListPosition));

        try {

            printWriter.printf("%s,%s,%s,%s,%s,%s,", booking.getCustomerType(), booking.getCustomer().getTitle(), booking.getCustomer().getFirstName(),
                                                       booking.getCustomer().getLastName(), booking.getCustomer().getEmail(),
                                                       booking.getCustomer().getPhone());
            if (booking.getCustomerType().equalsIgnoreCase(DOM_CUSTOMER)) {
                DomesticCustomer domesticCustomer = (DomesticCustomer) booking.getCustomer();
                printWriter.printf("%s,%s,", domesticCustomer.getDriverLicenseNo(), domesticCustomer.getExpiryDateInString());
            } else {
                InternationalCustomer intlCustomer = (InternationalCustomer) booking.getCustomer();
                printWriter.printf("%s,%s,%s,", intlCustomer.getPassportNumber(), intlCustomer.getCountry(),
                                                   intlCustomer.getExpiryDateInString());
            }
            printWriter.printf("%s,%s,%d,%s,%.2f%n", booking.getCheckInDateInString(), booking.getCheckOutDateInString(), 
                                                           booking.getGuestNumber(), booking.getRoomType(), booking.getRoomRate());
            printWriter.flush();

        } catch (Exception e) {
            return false;
        }

        return true;
    }//end writeData method

    /**
     * A method that closes the file
     *
     */
    public void closeFile() {
        printWriter.close();

    }//end closeFile method
    
    

}//end DataFile class
