/*
 * Student Names: Orville Shane D Silva & Rushabh Pancholi
 * Student IDs: 12091423 & 12096494 respectively
 * Course Unit: COIT20256(HT1, 2019)DATA STRUCTURES AND ALGORITHMS
 * Tutor: Ranasundara Senarathna
 * Campus: Melbourne
 */

import java.sql.*;
import java.util.ArrayList;

/**
 *Represents an object of type DatabaseUtility.
 *
 * @author Orville Shane D Silva & Rushabh Pancholi
 * @version 1.0
 */
public class DatabaseUtility {
    
    private String url; // Holds the url 
    private Connection sqlConnection; // Holds the connnection string to the database
    private String username; //Holds the username for the database
    private String password; //Holds the password for the database
    private ArrayList<Booking> Booking; //Holds the list of booking
    
    /**
     * A no argument constructor that creates an object of type DatabaseUtility
     * and initializes its variables with default values
     */
    public DatabaseUtility()
    {
        url = "jdbc:mysql://127.0.0.1/";
        Booking = new ArrayList<>(); 
        username = "root";
        password = "";
    }//end no argument constructor
    
    /**
     * A method that connects to the MySQL server and creates a database
     * 
     * This method creates a new connection to the server and creates a database
     * Returns true if successful and false if not.
     * 
     * @return boolean 
     */
    public boolean connectDatabase()
    {
        PreparedStatement stmt = null;
        try {  
            Class.forName("com.mysql.jdbc.Driver"); 
           
            sqlConnection = DriverManager.getConnection(url,username,password); 
            stmt = sqlConnection.prepareStatement("Create Database hotelDB");
            stmt.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
           return false;
        }
        
    }//end connectDatabase method
    
    /**
     * A method that creates the Customer and Booking tables in the database.
     * 
     * This method connects to the databse and creates new Customer and Booking 
     * tables in the database.
     * Returns true if successful and false if not.
     * 
     * @return boolean
     */
    public boolean createTables()
    {
        PreparedStatement stmt = null;
        try {
            
            sqlConnection = DriverManager.getConnection(url+"hotelDB",username,password);
            stmt = sqlConnection.prepareStatement("CREATE TABLE Customer (CustomerId int NOT NULL AUTO_INCREMENT PRIMARY KEY, Title varchar(4) NOT NULL, FirstName varchar(150) NOT NULL, LastName varchar(150) NOT NULL, Email varchar(255) NOT NULL, Phone varchar(15) NOT NULL, Passport_DLicense varchar(15) NOT NULL, Country varchar(100), ExpiryDate varchar(10) NOT NULL)");
            stmt.executeUpdate();
            
            stmt = sqlConnection.prepareStatement("CREATE TABLE Booking (BookingId int NOT NULL AUTO_INCREMENT PRIMARY KEY, CustomerId int NOT NULL, CheckInDate varchar(10) NOT NULL, CheckOutDate varchar(10) NOT NULL,NumOfGuests int NOT NULL, RoomType varchar(25) NOT NULL, RoomRate float NOT NULL, FOREIGN KEY(CustomerId) REFERENCES Customer(CustomerId))");
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
           ex.printStackTrace();
          // System.out.println(ex);
        }
        finally
        {
            try
            {
                if(stmt!=null)
                {
                    stmt.close();
                }
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        return false;
    }// end createTables method
    
    /**
     * A method that inserts a record in the Customer table.
     * 
     * This method takes a Customer object and inserts its information in the 
     * Customer table.
     * 
     * @param customer The customer object to insert 
     * @param customerType The type of customer
     */
    public void insertCustomer(Customer customer, String customerType) 
    {
         PreparedStatement stmt = null;
         String DOM_CUSTOMER = "Domestic Customer";
        try {
            sqlConnection = DriverManager.getConnection(url+"hotelDB",username,password);
            if(customerType.equalsIgnoreCase(DOM_CUSTOMER))
            {
                DomesticCustomer domCustomer = (DomesticCustomer)customer;
                stmt = sqlConnection.prepareStatement("INSERT INTO Customer(Title,FirstName,LastName,Email,Phone,Passport_DLicense,ExpiryDate) VALUES(?,?,?,?,?,?,?)");
                stmt.setString(1,domCustomer.getTitle());
                stmt.setString(2, domCustomer.getFirstName());
                stmt.setString(3,domCustomer.getLastName());
                stmt.setString(4,domCustomer.getEmail());
                stmt.setString(5,domCustomer.getPhone());
                stmt.setString(6,domCustomer.getDriverLicenseNo());
                stmt.setString(7, domCustomer.getExpiryDateInString());
                
            }
            else
            {
                InternationalCustomer intlCustomer = (InternationalCustomer)customer;
                stmt = sqlConnection.prepareStatement("INSERT INTO Customer(Title,FirstName,LastName,Email,Phone,Passport_DLicense,Country,ExpiryDate) VALUES(?,?,?,?,?,?,?,?)");
                stmt.setString(1,intlCustomer.getTitle());
                stmt.setString(2, intlCustomer.getFirstName());
                stmt.setString(3,intlCustomer.getLastName());
                stmt.setString(4,intlCustomer.getEmail());
                stmt.setString(5,intlCustomer.getPhone());
                stmt.setString(6,intlCustomer.getPassportNumber());
                stmt.setString(7,intlCustomer.getCountry());
                stmt.setString(8, intlCustomer.getExpiryDateInString());
            }
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(stmt!=null)
                {
                    stmt.close();
                }
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }

    }// end insertCustomer method
    
    /**
     * A method that inserts a record in the Booking table.
     * 
     * This method takes a Booking object and inserts its information in the 
     * Booking table.
     * 
     * @param booking The booking to insert
     */
    public void insertBooking(Booking booking) 
    {
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        String DOM_CUSTOMER = "Domestic Customer";
        try {
            sqlConnection = DriverManager.getConnection(url+"hotelDB",username,password);
            int pk;
            stmt = sqlConnection.prepareStatement("INSERT INTO Booking(CustomerID, CheckInDate, CheckOutDate, NumOfGuests, RoomType, RoomRate) VALUES(?,?,?,?,?,?)");
            if(booking.getCustomerType().equalsIgnoreCase(DOM_CUSTOMER))
            {
                DomesticCustomer domCustomer = (DomesticCustomer)booking.getCustomer();
                stmt2 = sqlConnection.prepareStatement("SELECT CustomerID FROM Customer where Title = ? AND FirstName =? AND LastName =? AND Email =? AND Phone =? AND Passport_DLicense =? AND ExpiryDate =?");
                stmt2.setString(1, domCustomer.getTitle());
                stmt2.setString(2, domCustomer.getFirstName());
                stmt2.setString(3, domCustomer.getLastName());
                stmt2.setString(4, domCustomer.getEmail());
                stmt2.setString(5, domCustomer.getPhone());
                stmt2.setString(6, domCustomer.getDriverLicenseNo());
                stmt2.setString(7, domCustomer.getExpiryDateInString());
             
                ResultSet rs = stmt2.executeQuery();
               
                if(rs.next())
                {
                     pk = rs.getInt(1);
                  
                    
                }
                else
                {
                    insertCustomer(booking.getCustomer(), booking.getCustomerType());
                    rs = stmt2.executeQuery();
                    rs.next();
                    pk = rs.getInt(1);
                    
                    
                }
                
            }
            else
            {
                InternationalCustomer intlCustomer = (InternationalCustomer)booking.getCustomer();
                
                stmt2 = sqlConnection.prepareStatement("SELECT CustomerID FROM Customer where Title = ? AND FirstName =? AND LastName =? AND Email =? AND Phone =? AND Passport_DLicense =? AND Country =? AND ExpiryDate =?");
                stmt2.setString(1, intlCustomer.getTitle());
                stmt2.setString(2, intlCustomer.getFirstName());
                stmt2.setString(3, intlCustomer.getLastName());
                stmt2.setString(4, intlCustomer.getEmail());
                stmt2.setString(5, intlCustomer.getPhone());
                stmt2.setString(6, intlCustomer.getPassportNumber());
                stmt2.setString(7, intlCustomer.getCountry());
                stmt2.setString(8, intlCustomer.getExpiryDateInString());
               
                ResultSet rs = stmt2.executeQuery();
                
                if(rs.next())
                {
                    pk = rs.getInt(1);
                    
                    
                }
                else
                {
                    insertCustomer(booking.getCustomer(), booking.getCustomerType());
                    rs = stmt2.executeQuery();
                    rs.next();
                    pk = rs.getInt(1);
                    
                    
                }
            }
            
            stmt.setInt(1, pk);
            stmt.setString(2,  booking.getCheckInDateInString());
            stmt.setString(3,  booking.getCheckOutDateInString());
            stmt.setInt(4, booking.getGuestNumber());
            stmt.setString(5, booking.getRoomType());
            stmt.setDouble(6, booking.getRoomRate());
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(stmt!=null)
                {
                    stmt.close();
                }
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        
         
    }// end insertBooking method
    
    /**
     * A method that closes the sql connection
     * 
     * This method simply closes the opened sql connection
     * 
     */
    public void closeConnection()
    {
        try
        {
            if(sqlConnection!=null)
                sqlConnection.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }//end closeConnection method
}
