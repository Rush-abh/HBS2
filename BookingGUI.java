/*
 * Student Names: Orville Shane D Silva & Rushabh Pancholi
 * Student IDs: 12091423 & 12096494 respectively
 * Course Unit: COIT20256(HT1, 2019)DATA STRUCTURES AND ALGORITHMS
 * Tutor: Ranasundara Senarathna
 * Campus: Melbourne
 */

import com.sun.glass.events.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 *Represents an object of type BookingGUI.
 * 
 * This class is the main class of the NEXT Hotel Booking System. It contains
 * the necessary code to create the GUI of the application. It also has
 * various validation methods for the input fields in the GUI. The class may
 * also handle various events that may occur from user interaction.
 *
 * @author Orville Shane D Silva & Rushabh Pancholi
 * @version 1.0
 */
public class BookingGUI extends JFrame {

    static final String DOM_CUSTOMER_COMMAND = "Domestic Customer"; //Action command string for domCustomer
    static final String INTL_CUSTOMER_COMMAND = "International Customer"; //Action command string for intlCustomer
    
    private JPanel topPanel; // The top panel of the frame
    private JPanel customerTypePanel; //The panel in the top panel which contains the customerType button group
    private ButtonGroup customerType; //The button group containing the radio buttons
    private JRadioButton domCustomer; //The domestic customer radio button
    private JRadioButton intlCustomer; //The international customer radio button
    private JPanel inputContainer; //The panel containing the inputPanel and emptyPanel
    private JPanel inputPanel; //The panel containing the input fields
    
    private JPanel emptyPanel; //An empty panel that appears before clicking the radiobuttons
    private JLabel customerHeaderLabel; //"Customer" header label above the customer fields
    private JComboBox<String> titleBox; //The combobox containing the title of the customer
    private JLabel titleBoxLabel; //Label for the titleBox combobox
    private JTextField firstName; //The textfield for the customer's first name
    private JLabel firstNameLabel; //Label for the firstName textfield
    
    private JTextField lastName; //The textfield for the customer's last name
    private JLabel lastNameLabel; //Label for the lastName textfield
    private JTextField email; //The textfield for the customer's email
    private JLabel emailLabel; //Label for email textfield
    private JTextField phone; //The textfield for the customer's phone number
    private JLabel phoneLabel; //Label for the phone textfield
    
    private JTextField driverLicense; //The textfield for the customer's driver's license number
    private JLabel driverLicenseLabel; //Label for the driverLicense textfield
    private JTextField passport; //The textfield for the customer;s passport number
    private JLabel passportLabel; //Label for the passport textfield
    private JComboBox<String> countryList; //The combobox containing the list of countries
    private JLabel countryListLabel; //Label for countryList combobox
    
    private JFormattedTextField expiryDate; //The formatted field for expiry date of either passport or DriverLicense
    private JLabel expiryDateLabel; //Label for expiryDate formatted textfield
    private JLabel bookingHeaderLabel; //"Booking" header label above the booking fields
    private JFormattedTextField checkInDate; //The formatted field for check-in date
    private JLabel checkInLabel; //Label for checkInDate formatted textfield
    private JFormattedTextField checkOutDate; //The formatted field for check-out date
    private JLabel checkOutLabel; //Label for checkOutDate formatted textfield
    
    private JComboBox<Integer> guestNumberBox; //The combobox containing the number of passengers
    private JLabel guestNumberLabel1; //Label for the guestNumberBox
    private JComboBox<String> roomTypeBox; //The combobox containing the room types
    private JLabel roomTypeLabel; //Label for the roomTypeBox
    private JPanel middlePanel; //The middle panel of the frame
    private JTextArea displayArea; //The display area for the application
    private JScrollPane dsplayAreaScrollPane; //THe scroll pane for the dislay area 
    

    private JPanel bottomPanel; //The bottom panel of the frame containing the command buttons
    private JButton enterDataBtn; //The "enter data" button
    private JButton saveBookingBtn; //The "Save data" button
    private JButton clearDisplayBtn; //The "clear display" button
    private JButton quitBtn; //The "quit" button
   
    private CardLayout inputPanelManager; //Used to switch "cards" in the cardlayout of inputContainer
       
    private boolean enterDataPanelFlag; //Check if top panel is visible or not
    private boolean checkDatesFlag; //Check if dates are validated 
    private boolean bookingFlag; //Check  if booking has already been confirmed
    
    private final String BOOKING_FILE_NAME = "Booking.csv"; //File name of the file storing the booking details
    private DataFile dataFile; //The object used to open and write to the "booking.csv" file
    private ArrayList<Booking> bookingList; //List of Booking objects
    Booking tempBooking; //Temporary Booking object to hold the current booking information
    private LinkedList<Booking> bookingLinkedList; //Linked list of Booking objects

    // Constants for the different types of rooms and their rates
    private final String SUPERIOR_QUEEN = "Superior Queen";
    private final String BREAKFAST_QUEEN = "Breakfast Queen";
    private final String DELUXE_KING = "Deluxe King";
    private final double SUP_QUEEN_RATE = 250;
    private final double BREAK_QUEEN_RATE = 270;
    private final double DEL_KING_RATE = 240;
    
    private javax.swing.JButton displayChoicesBtn;
    private javax.swing.JPanel displayChoicesPanel;
    private javax.swing.JLabel choiceCheckoutDateLabel;
    private javax.swing.JList<String> choiceDateList;
    private javax.swing.JLabel choiceNameLabel;
    private javax.swing.JList<String> choiceNameList;
    private javax.swing.JButton searchDateBtn;
    private javax.swing.JScrollPane searchDateScrollPane;
    private javax.swing.JButton searchNameBtn;
    private javax.swing.JScrollPane searchNameScrollPane;
    
    private final String DEFAULT_DISPLAY_TEXT = "Welcome to the NEXT Hotel Booking System\n\nWhat would you like to do?"; //Welcome message

    private final int MAX_PHONE_LENGTH = 10; //Maximum Australian phone number length  

    //Australian driver's license numbers should be between 6 - 10 characters with the exception of 8 characters
    //No Australian DL numbers are 8 characters long
    private final int MIN_DLNUMBER_LENGTH = 6;
    private final int MAX_DLNUMBER_LENGTH = 10;
    private final int INVALID_DLNUMBER_LENGTH = 8;

   
    /**
     * A no argument constructor that creates an object of type BookingGUI and
     * calls the iniComponens method to create and show the GUI.
     */
    public BookingGUI() {
        initComponents();
        
        
    }//end no argument constructor

    /**
     * A method that initializes all of the class' variables along with 
     * creating and showing the GUI and its components
     */
    private void initComponents() {
        
        customerType = new ButtonGroup();
        dataFile = new DataFile(BOOKING_FILE_NAME);
        enterDataPanelFlag = false;
        topPanel = new JPanel();
        customerTypePanel = new JPanel();
        domCustomer = new JRadioButton();
        intlCustomer = new JRadioButton();
        bookingList = new ArrayList<>();
        bookingLinkedList = new LinkedList<>();
        inputContainer = new JPanel();
        inputPanel = new JPanel();
        roomTypeLabel = new JLabel();
        roomTypeBox = new JComboBox<>();
        email = new JTextField();
        emailLabel = new JLabel();
        passport = new JTextField();
        passportLabel = new JLabel();
        lastName = new JTextField();
        lastNameLabel = new JLabel();
        firstName = new JTextField();
        firstNameLabel = new JLabel();
        phone = new JTextField();
        driverLicenseLabel = new JLabel();
        checkInLabel = new JLabel();
        titleBox = new JComboBox<>();
        titleBoxLabel = new JLabel();
        bookingHeaderLabel = new JLabel();
        customerHeaderLabel = new JLabel();
        phoneLabel = new JLabel();
        expiryDateLabel = new JLabel();
        checkOutLabel = new JLabel();
        countryList = new JComboBox<>(getAllCountries());
        countryListLabel = new JLabel();
        guestNumberBox = new JComboBox<>();
        guestNumberLabel1 = new JLabel();
        checkOutDate = new JFormattedTextField();
        checkInDate = new JFormattedTextField();
        expiryDate = new JFormattedTextField();
        driverLicense = new JTextField();
        emptyPanel = new JPanel();
        middlePanel = new JPanel();
        dsplayAreaScrollPane = new JScrollPane();
        displayArea = new JTextArea(DEFAULT_DISPLAY_TEXT);
        bottomPanel = new JPanel();
        enterDataBtn = new JButton();
        saveBookingBtn = new JButton();
        clearDisplayBtn = new JButton();
        quitBtn = new JButton();
        displayChoicesPanel = new javax.swing.JPanel();
        displayChoicesBtn = new javax.swing.JButton();
        choiceNameList = new javax.swing.JList<>();
        choiceNameLabel = new javax.swing.JLabel();
        choiceCheckoutDateLabel = new javax.swing.JLabel();
        choiceDateList = new javax.swing.JList<>();
        searchNameScrollPane = new javax.swing.JScrollPane();
        searchDateScrollPane = new javax.swing.JScrollPane();
        searchNameBtn = new javax.swing.JButton();
        searchDateBtn = new javax.swing.JButton();
        GridBagConstraints gridBagConstraints; //Used to position the 3 panels on the frame

        /*
        *Create the frame with GridBagLayout
        */
        setTitle("NEXT Hotel Booking System");
        setBounds(new Rectangle(0, 0, 0, 0));
        setLocation(new Point(0, 0));
        setMinimumSize(new Dimension(650, 830));
        setPreferredSize(new Dimension(900, 700));
        getContentPane().setLayout(new GridBagLayout());

        customerTypePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        domCustomer.setActionCommand(DOM_CUSTOMER_COMMAND);
        customerType.add(domCustomer);
        domCustomer.setText("Domestic Customer");
        
        /**
         * Attaches an ItemListener to the domestic customer radio button
         */
        domCustomer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                domCustomerItemStateChanged(evt);
            }
        });

        intlCustomer.setActionCommand(INTL_CUSTOMER_COMMAND);
        customerType.add(intlCustomer);
        intlCustomer.setText("International Customer");
        
        /**
         * Attaches an ItemListener to the international customer radio button
         */
        intlCustomer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                intlCustomerItemStateChanged(evt);
            }
        });

        /*
        *Grouping and positioning the radio buttons in the customerTypePanel
        */
        GroupLayout customerTypePanelLayout = new GroupLayout(customerTypePanel);
        customerTypePanel.setLayout(customerTypePanelLayout);
        customerTypePanelLayout.setHorizontalGroup(
                customerTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(customerTypePanelLayout.createSequentialGroup()
                                .addGap(209, 209, 209)
                                .addComponent(domCustomer)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(intlCustomer)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        customerTypePanelLayout.setVerticalGroup(
                customerTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, customerTypePanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(customerTypePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(domCustomer)
                                        .addComponent(intlCustomer))
                                .addContainerGap())
        );

        /*
        *sets the container of the inputPanel and emptyPanel as CardLayout
        */
        inputContainer.setLayout(new CardLayout());

        /*
        *Design the inputPanel
        */
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        inputPanel.setMinimumSize(new Dimension(713, 512));
        inputPanel.setPreferredSize(new Dimension(713, 512));
        inputPanel.setLayout(null);

        /*
        *Create, design and position all the input fields in the inputPanel
        */
        roomTypeLabel.setText("Room Type*:");
        inputPanel.add(roomTypeLabel);
        roomTypeLabel.setBounds(120, 440, 110, 14);

        roomTypeBox.setModel(new DefaultComboBoxModel<>(new String[]{"Superior Queen", "Breakfast Queen", "Deluxe King"}));

        inputPanel.add(roomTypeBox);
        roomTypeBox.setBounds(120, 460, 140, 30);

        email.setMinimumSize(new Dimension(6, 23));
        email.setPreferredSize(new Dimension(100, 23));

        /**
         * Attaches a KeyListener to the email text field. 
         * 
         * On pressing a key,
         * emailKeyPressed method gets called with the KeyEvent as a parameter
         * which performs the necessary function.
         * 
         *@param evt The KeyEvent that occurred 
         */
        email.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emailKeyPressed(evt);
            }
        }); //end addKeyListener
        
        inputPanel.add(email);
        email.setBounds(440, 80, 140, 30);

        emailLabel.setText("Email*:");
        emailLabel.setPreferredSize(new Dimension(24, 14));
        inputPanel.add(emailLabel);
        emailLabel.setBounds(440, 60, 70, 14);

        passport.setMinimumSize(new Dimension(6, 23));
        passport.setPreferredSize(new Dimension(100, 23));

        /**
         * Attaches a KeyListener to the passport text field.
         * 
         * On pressing a key,
         * passportKeyPressed method gets called with the KeyEvent as
         * a parameter which performs the necessary function.
         * 
         *@param evt The KeyEvent that occurred 
         */
        passport.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passportKeyPressed(evt);
            }
        });//end addKeyListener
        
        inputPanel.add(passport);
        passport.setBounds(440, 150, 140, 30);

        passportLabel.setText("Passport Number*:");
        passportLabel.setPreferredSize(new Dimension(24, 14));
        inputPanel.add(passportLabel);
        passportLabel.setBounds(440, 130, 120, 14);

        lastName.setMinimumSize(new Dimension(6, 23));
        lastName.setPreferredSize(new Dimension(100, 23));
        
        /**
         * Attaches a KeyListener to the lastName text field.
         * 
         * On pressing a key,
         * lastNameKeyPressed method gets called with the KeyEvent as a
         * parameter which performs the necessary function.
         * 
         *@param evt The KeyEvent that occurred 
         */
        lastName.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lastNameKeyPressed(evt);
            }
        });//end addKeyListener
        
        inputPanel.add(lastName);
        lastName.setBounds(280, 80, 140, 30);

        lastNameLabel.setText("Last Name*:");
        lastNameLabel.setPreferredSize(new java.awt.Dimension(24, 14));
        inputPanel.add(lastNameLabel);
        lastNameLabel.setBounds(280, 60, 70, 14);

        firstName.setMinimumSize(new Dimension(6, 23));
        firstName.setPreferredSize(new Dimension(100, 23));
        
        /**
         * Attaches a KeyListener to the firstName text field.
         * 
         * On pressing a key,
         * firstNameKeyPressed method gets called with the KeyEvent as a
         * parameter which performs the necessary function.
         * 
         *@param evt The KeyEvent that occurred 
         */
        firstName.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                firstNameKeyPressed(evt);
            }
        });//end addKeyListener
        
        inputPanel.add(firstName);
        firstName.setBounds(120, 80, 140, 30);

        firstNameLabel.setText("First Name*:");
        firstNameLabel.setPreferredSize(new Dimension(24, 14));
        inputPanel.add(firstNameLabel);
        firstNameLabel.setBounds(120, 60, 70, 14);

        phone.setMinimumSize(new Dimension(6, 23));
        phone.setPreferredSize(new Dimension(100, 23));
        
        /**
         * Attaches a FocusListener to the phone text field.
         * 
         * On pressing a key,
         * phoneFocusGained method gets called with the FocusEvent as a
         * parameter which performs the necessary function.
         * 
         *@param evt The FocusEvent that occurred 
         */
        phone.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                phoneFocusGained(evt);
            }
        });//end addFocusListener
        
        /**
         * Attaches a KeyListener to the phone text field.
         * 
         * On pressing a key,
         * another method gets called with the KeyEvent as a parameter which 
         * performs the necessary function.
         * 
         *@param evt The KeyEvent that occurred 
         */
        phone.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                phoneKeyPressed(evt);
            }
        });//end addKeyListener
        
        inputPanel.add(phone);
        phone.setBounds(120, 150, 140, 30);

        driverLicenseLabel.setText("Driver License No.*:");
        driverLicenseLabel.setPreferredSize(new Dimension(24, 14));
        inputPanel.add(driverLicenseLabel);
        driverLicenseLabel.setBounds(280, 130, 120, 14);

        checkInLabel.setText("Check-In (dd/MM/yyyy)*:");
        checkInLabel.setPreferredSize(new Dimension(24, 14));
        inputPanel.add(checkInLabel);
        checkInLabel.setBounds(120, 360, 150, 14);

        titleBox.setModel(new DefaultComboBoxModel<>(new String[]{"Mr.", "Mrs.",
                                                    "Ms.", "Dr.", "Other"}));

        inputPanel.add(titleBox);
        titleBox.setBounds(20, 80, 70, 30);

        titleBoxLabel.setText("Title*:");
        inputPanel.add(titleBoxLabel);
        titleBoxLabel.setBounds(20, 60, 70, 14);

        bookingHeaderLabel.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        bookingHeaderLabel.setText("Booking Details");
        bookingHeaderLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        inputPanel.add(bookingHeaderLabel);
        bookingHeaderLabel.setBounds(280, 300, 150, 30);

        customerHeaderLabel.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        customerHeaderLabel.setText("Customer Details");
        customerHeaderLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        inputPanel.add(customerHeaderLabel);
        customerHeaderLabel.setBounds(280, 10, 150, 30);

        phoneLabel.setText("Phone (10-digit)*:");
        phoneLabel.setPreferredSize(new Dimension(24, 14));
        inputPanel.add(phoneLabel);
        phoneLabel.setBounds(120, 130, 100, 14);

        expiryDateLabel.setText("Expiry Date (MM/yyyy)*:");
        expiryDateLabel.setPreferredSize(new Dimension(24, 14));
        inputPanel.add(expiryDateLabel);
        expiryDateLabel.setBounds(280, 200, 140, 14);

        checkOutLabel.setText("Check-Out (dd/MM/yyyy)*:");
        checkOutLabel.setPreferredSize(new Dimension(24, 14));
        inputPanel.add(checkOutLabel);
        checkOutLabel.setBounds(280, 360, 160, 14);

        countryList.setBackground(Color.white);

        inputPanel.add(countryList);
        countryList.setBounds(120, 220, 140, 30);

        countryListLabel.setText("Country of Issue*:");
        inputPanel.add(countryListLabel);
        countryListLabel.setBounds(120, 200, 110, 14);

        guestNumberBox.setModel(new DefaultComboBoxModel<>(new Integer[]{1, 2, 3, 4, 5}));

        inputPanel.add(guestNumberBox);
        guestNumberBox.setBounds(440, 380, 140, 30);

        guestNumberLabel1.setText("No. of Guests*:");
        inputPanel.add(guestNumberLabel1);
        guestNumberLabel1.setBounds(440, 360, 110, 14);

        checkOutDate.setFormatterFactory(new DefaultFormatterFactory
                                        (new DateFormatter(new SimpleDateFormat
                                                          ("dd/MM/yyyy"))));
        checkOutDate.setPreferredSize(new Dimension(100, 23));
        
        /**
         * Attaches a FocusListener to the checkOutDate formatted text field.
         * 
         * On pressing a key,
         * checkOutDateFocusGained method gets called with the FocusEvent as a 
         * parameter which performs the necessary function.
         * 
         *@param evt The FocusEvent that occurred 
         */
        checkOutDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                checkOutDateFocusGained(evt);
            }
        });//end addFocusListener
        
        /**
         * Attaches a KeyListener to the checkOutDate formatted text field.
         * 
         * On pressing a key,
         * checkOutDateKeyPressed method gets called with the KeyEvent as a
         * parameter which performs the necessary function.
         * 
         *@param evt The KeyEvent that occurred 
         */
        checkOutDate.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                checkOutDateKeyPressed(evt);
            }
        });//end addKeyListener
        
        inputPanel.add(checkOutDate);
        checkOutDate.setBounds(280, 380, 140, 30);

        checkInDate.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter
                                       (new SimpleDateFormat("dd/MM/yyyy"))));
        checkInDate.setPreferredSize(new Dimension(100, 23));
        
        /**
         * Attaches a FocusListener to the checkInDate formatted text field.
         * 
         * On pressing a key,
         * checkInDateFocusGained method gets called with the FocusEvent as a 
         * parameter which performs the necessary function.
         * 
         *@param evt The FocusEvent that occurred 
         */
        checkInDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                checkInDateFocusGained(evt);
            }
        });//end addFocusListener
        
        /**
         * Attaches a KeyListener to the checkInDate formatted text field.
         * 
         * On pressing a key,
         * checkInDateKeyPressed method gets called with the KeyEvent as a 
         * parameter which performs the necessary function.
         * 
         *@param evt The KeyEvent that occurred 
         */
        checkInDate.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                checkInDateKeyPressed(evt);
            }
        });//end addKeyListener
        
        inputPanel.add(checkInDate);
        checkInDate.setBounds(120, 380, 140, 30);

        expiryDate.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter
                                      (new SimpleDateFormat("MM/yyyy"))));
        expiryDate.setPreferredSize(new Dimension(100, 23));
        
        /**
         * Attaches a FocusListener to the expiryDate formatted text field.
         * 
         * On pressing a key,
         * expiryDateFocusGained method gets called with the FocusEvent as a 
         * parameter which performs the necessary function.
         * 
         *@param evt The FocusEvent that occurred 
         */
        expiryDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                expiryDateFocusGained(evt);
            }
        });//end addFocusListener
        
        /**
         * Attaches a KeyListener to the expiryDate formatted text field.
         * 
         * On pressing a key,
         * expiryDateKeyPressed method gets called with the KeyEvent as a 
         * parameter which performs the necessary function.
         * 
         *@param evt The KeyEvent that occurred 
         */
        expiryDate.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                expiryDateKeyPressed(evt);
            }
        });//end addKeyListener
        
        inputPanel.add(expiryDate);
        expiryDate.setBounds(280, 220, 140, 30);

        driverLicense.setMinimumSize(new Dimension(6, 23));
        driverLicense.setPreferredSize(new Dimension(100, 23));

        /**
         * Attaches a KeyListener to the driverLicense text field.
         * 
         * On pressing a key,
         * driverLicenseKeyPressed method gets called with the KeyEvent as a 
         * parameter which performs the necessary function.
         * 
         *@param evt The KeyEvent that occurred 
         */
        driverLicense.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                driverLicenseKeyPressed(evt);
            }
        });//end addKeyListener
        
        inputPanel.add(driverLicense);
        driverLicense.setBounds(280, 150, 140, 30);

        inputContainer.add(inputPanel, "card2");

        /*
        *Position the empty panel and add it to the inputContainer
        */
        GroupLayout emptyPanelLayout = new GroupLayout(emptyPanel);
        emptyPanel.setLayout(emptyPanelLayout);
        emptyPanelLayout.setHorizontalGroup(
                emptyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 713, Short.MAX_VALUE)
        );
        emptyPanelLayout.setVerticalGroup(
                emptyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 512, Short.MAX_VALUE)
        );

        inputContainer.add(emptyPanel, "card3");
        
        choiceNameList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        searchNameScrollPane.setViewportView(choiceNameList);

        choiceDateList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        searchDateScrollPane.setViewportView(choiceDateList);

        choiceNameLabel.setText("Customer Name:");

        choiceCheckoutDateLabel.setText("Check-out Date:");

        searchNameBtn.setText("Search by Name");
        searchNameBtn.setPreferredSize(new java.awt.Dimension(100, 50));
        searchNameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNameBtnActionPerformed(evt);
            }
        });

        searchDateBtn.setText("Search by Date");
        searchDateBtn.setMaximumSize(new java.awt.Dimension(111, 23));
        searchDateBtn.setMinimumSize(new java.awt.Dimension(111, 23));
        searchDateBtn.setPreferredSize(new java.awt.Dimension(100, 50));
        searchDateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchDateBtnActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout displayChoicesPanelLayout = new javax.swing.GroupLayout(displayChoicesPanel);
        displayChoicesPanel.setLayout(displayChoicesPanelLayout);
        displayChoicesPanelLayout.setHorizontalGroup(
            displayChoicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, displayChoicesPanelLayout.createSequentialGroup()
                .addContainerGap(190, Short.MAX_VALUE)
                .addGroup(displayChoicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(displayChoicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(searchNameScrollPane)
                        .addComponent(searchNameBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                    .addComponent(choiceNameLabel))
                .addGap(67, 67, 67)
                .addGroup(displayChoicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choiceCheckoutDateLabel)
                    .addGroup(displayChoicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(searchDateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addComponent(searchDateScrollPane)))
                .addGap(194, 194, 194))
        );
        displayChoicesPanelLayout.setVerticalGroup(
            displayChoicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayChoicesPanelLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(displayChoicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(choiceNameLabel)
                    .addComponent(choiceCheckoutDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(displayChoicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchNameScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(searchDateScrollPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(displayChoicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchNameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(175, Short.MAX_VALUE))
        );

        inputContainer.add(displayChoicesPanel, "card4");

        /*
        * Position the customerTypePanel and nputContainer in topPanel
        */
        GroupLayout topPanelLayout = new GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
                topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(customerTypePanel, GroupLayout.DEFAULT_SIZE, 
                                              GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addComponent(inputContainer, GroupLayout.PREFERRED_SIZE, 
                                              713, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        topPanelLayout.setVerticalGroup(
                topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addComponent(customerTypePanel, GroupLayout.PREFERRED_SIZE,
                                              GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputContainer, GroupLayout.PREFERRED_SIZE,
                                              512, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //Show emptyPanel as the default panel on Eneter Data button click
        inputPanelManager = new CardLayout();
        inputPanelManager = (CardLayout) inputContainer.getLayout();
        inputPanelManager.show(this.inputContainer, "card3");
        
        

        

        /*
        *Position topPanel in the frame and set visibility to false
        */
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(topPanel, gridBagConstraints);
        topPanel.setVisible(false);

        //set the size of the middle panel
        middlePanel.setPreferredSize(new Dimension(705, 160));

        /*
        *Create the displayArea
        */
        displayArea.setColumns(50);
        displayArea.setRows(10);
        displayArea.setFocusable(false);
        displayArea.setHighlighter(null);
        displayArea.setPreferredSize(new Dimension(705, 94));
        dsplayAreaScrollPane.setViewportView(displayArea);

        /*
        *position and add the scroll pane to the middle panel 
        */
        GroupLayout middlePanelLayout = new GroupLayout(middlePanel);
        middlePanel.setLayout(middlePanelLayout);
        middlePanelLayout.setHorizontalGroup(
                middlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(middlePanelLayout.createSequentialGroup()
                                .addComponent(dsplayAreaScrollPane, GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                                .addContainerGap())
        );
        middlePanelLayout.setVerticalGroup(
                middlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(middlePanelLayout.createSequentialGroup()
                                .addComponent(dsplayAreaScrollPane, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 19, Short.MAX_VALUE))
        );

        /*
        *Position the middlePanel in the frame
        */
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(middlePanel, gridBagConstraints);

        //Create the bottomPanel and the buttons
        bottomPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        bottomPanel.setPreferredSize(new java.awt.Dimension(705, 60));

        enterDataBtn.setText("Enter Booking");
        enterDataBtn.setPreferredSize(new Dimension(110, 50));
        
        /**
         * Attaches a ActionListener to the enterDataBtn button 
         * 
         * On clicking the button,
         * enterDataBtnActionPerformed method gets called with the ActionEvent as a 
         * parameter which performs the necessary function.
         * 
         *@param evt The ActionEvent that occurred 
         */
        enterDataBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                enterDataBtnActionPerformed(evt);
            }
        });//end addActionListener
        
        bottomPanel.add(enterDataBtn);
        
        displayChoicesBtn.setText("Display Choices");
        displayChoicesBtn.setPreferredSize(new java.awt.Dimension(110, 50));
        displayChoicesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayChoicesBtnActionPerformed(evt);
            }
        });
        bottomPanel.add(displayChoicesBtn);

        saveBookingBtn.setText("Save Booking");
        saveBookingBtn.setPreferredSize(new Dimension(110, 50));
        
        /**
         * Attaches a ActionListener to the saveBookingBtn button 
         * 
         * On clicking the button,
         * saveBookingBtnActionPerformed method gets called with the 
         * ActionEvent as a parameter which performs the necessary function.
         * 
         *@param evt The ActionEvent that occurred 
         */
        saveBookingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveBookingBtnActionPerformed(evt);
            }
        });//end addActionListener
        
        bottomPanel.add(saveBookingBtn);

        clearDisplayBtn.setText("Clear Display");
        clearDisplayBtn.setPreferredSize(new Dimension(110, 50));
        
        /**
         * Attaches a ActionListener to the clearDisplayBtn button 
         * 
         * On clicking the button,
         * clearDisplayBtnActionPerformed method gets called with the 
         * ActionEvent as a parameter which performs the necessary function.
         * 
         *@param evt The ActionEvent that occurred 
         */
        clearDisplayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                clearDisplayBtnActionPerformed(evt);
            }
        });//end addActionListener
        
        bottomPanel.add(clearDisplayBtn);

        quitBtn.setText("Quit");
        quitBtn.setPreferredSize(new Dimension(100, 50));
        
        /**
         * Attaches a ActionListener to the quitBtn button 
         * 
         * On clicking the button,
         * quitBtnActionPerformed method gets called with the 
         * ActionEvent as a parameter which performs the necessary function.
         * 
         *@param evt The ActionEvent that occurred 
         */
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                quitBtnActionPerformed(evt);
            }
        });//end addActionListen
        
        bottomPanel.add(quitBtn);

        /*
        *Position the bottomPanel in the frame
        */
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(bottomPanel, gridBagConstraints);
        
        loadData();

    }//end initComponents method

    /**
     * A method that makes the topPanel visible for user input
     * 
     * This method sets the visibility of the topPanel and also 
     * displays an error message if the user continously clicks the 
     * "Enter data" button. Furthermore, this method also sets the 
     * checkDatesFlag and bookingFlag to false if and only if the the topPanel
     * was made visible.
     * 
     * @param evt The source of the event
     */
    private void enterDataBtnActionPerformed(ActionEvent evt) {
        topPanel.setVisible(true);
        inputPanelManager.show(this.inputContainer, "card3");
        
        if ((topPanel.isVisible() == true) && (enterDataPanelFlag == true)) {
            JOptionPane.showMessageDialog(null, "Enter Data Panel Already Visible",
                                         "Enter Data", JOptionPane.ERROR_MESSAGE);
        } else {
            displayArea.setText("");
            displayArea.append("Please Select a Customer Type");
            enterDataPanelFlag = true;
            checkDatesFlag = false;
            bookingFlag = false;
            customerTypePanel.setVisible(true);
        }

    }//end enterDataBtnActionPerformed method
    
    /**
     * A method that opens the display choices panel
     * 
     * This method is only responsible for making the display choices panel
     * visible.
     * 
     * @param evt the source of the event
     */
    private void displayChoicesBtnActionPerformed(java.awt.event.ActionEvent evt) {  
        
        if(displayChoicesPanel.isVisible()==true)
        {
            JOptionPane.showMessageDialog(null,"Display Choices Panel is already visible",
                                          "Display Choices",JOptionPane.ERROR_MESSAGE);
        }
        
        topPanel.setVisible(true);
        enterDataPanelFlag = false;
        customerTypePanel.setVisible(false);
        inputContainer.setVisible(true);
        inputPanelManager.show(this.inputContainer, "card4");
        displayArea.setText("");
        displayArea.setText("Use ctl+click to select multiple items from the "
                            + "displayed List of check out dates or Customer names.");
        populateJList(choiceNameList);
        populateJList(choiceDateList);
        
    }//end displayChoicesBtnActionPerformed method                                             

    /**
     * A method that saves the booking information entered by the user
     * 
     * This method performs the save operations needed to save the information
     * to the file. It displays an error message in case when the topPanel or 
     * the inputPanel are set to visible. It also a displays an error message
     * if the user tries to click the save button continously after the booking
     * was saved once. It calls the checkFields and checkDates methods for 
     * validation prior to saving. 
     * 
     * @param evt the source of the event
     */
    private void saveBookingBtnActionPerformed(ActionEvent evt) {

        /*
        * Check if the topPanel or the inputPanel are visible. Display error
        * in case either are false
        */
        if (topPanel.isVisible() == false || inputPanel.isVisible() == false) {
            JOptionPane.showMessageDialog(null, "Input Panel is Not Visible",
                                         "Save Data", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*
        * Proceed only if checkFields method returns false
        */
        if (!checkFields()) {

            /*
            * Display error if user continously clicks the save button after the
            * booking was saved once
            */
            if (bookingFlag == true) {
                JOptionPane.showMessageDialog(null, "Booking Already Confirmed",
                                             "Save Data", JOptionPane.ERROR_MESSAGE);

            }

            /*
            * call for validation of correct date format entered by user
            */
            checkDatesFlag = checkDates(expiryDate) || checkDates(checkInDate) 
                                                    || checkDates(checkOutDate);

            /*
            * proceed to save the booking only if both checkDatesFlag 
            * and bookingFlag are false
            */
            if (!(checkDatesFlag) && !(bookingFlag)) {

                tempBooking = new Booking();
                Date date = null;
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

                try {
                    date = sdf.parse(expiryDate.getText());
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Date Format", 
                                                 "Save Data", JOptionPane.ERROR_MESSAGE);
                }

                /*
                * Save the booking information if choice is domestic customer
                * else save the booking information if choice is international
                * customer
                */
                if (customerType.getSelection().getActionCommand().equalsIgnoreCase(DOM_CUSTOMER_COMMAND)) {
                    DomesticCustomer domesticCustomer = new DomesticCustomer();
                    domesticCustomer.setTitle(titleBox.getSelectedItem().toString());
                    domesticCustomer.setFirstName(firstName.getText());
                    domesticCustomer.setLastName(lastName.getText());
                    domesticCustomer.setEmail(email.getText());
                    domesticCustomer.setPhone(phone.getText());
                    domesticCustomer.setDriverLicenseNo(driverLicense.getText());
                    domesticCustomer.setExpiryDate(date);
                    tempBooking.setCustomerType(DOM_CUSTOMER_COMMAND);
                    tempBooking.setCustomer(domesticCustomer);

                } else if (customerType.getSelection().getActionCommand().equalsIgnoreCase(INTL_CUSTOMER_COMMAND)) {
                    InternationalCustomer internationalCustomer = new InternationalCustomer();
                    internationalCustomer.setTitle(titleBox.getSelectedItem().toString());
                    internationalCustomer.setFirstName(firstName.getText());
                    internationalCustomer.setLastName(lastName.getText());
                    internationalCustomer.setEmail(email.getText());
                    internationalCustomer.setPhone(phone.getText());
                    internationalCustomer.setPassportNumber(passport.getText());
                    internationalCustomer.setCountry(countryList.getSelectedItem().toString());
                    internationalCustomer.setExpiryDate(date);
                    tempBooking.setCustomerType(INTL_CUSTOMER_COMMAND);
                    tempBooking.setCustomer(internationalCustomer);
                }

                sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    tempBooking.setCheckIn(sdf.parse(checkInDate.getText()));
                    tempBooking.setCheckOut(sdf.parse(checkOutDate.getText()));
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Date Format in Booking Dates", "Save Data", JOptionPane.ERROR_MESSAGE);
                }

                tempBooking.setGuestNumber(guestNumberBox.getSelectedIndex() + 1);
                tempBooking.setRoomType(roomTypeBox.getSelectedItem().toString());
                if (tempBooking.getRoomType().equalsIgnoreCase(SUPERIOR_QUEEN)) {
                    tempBooking.setRoomRate(SUP_QUEEN_RATE);
                } else if (tempBooking.getRoomType().equalsIgnoreCase(BREAKFAST_QUEEN)) {
                    tempBooking.setRoomRate(BREAK_QUEEN_RATE);
                } else if (tempBooking.getRoomType().equalsIgnoreCase(DELUXE_KING)) {
                    tempBooking.setRoomRate(DEL_KING_RATE);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Room Type", "Save Data", JOptionPane.ERROR_MESSAGE);
                }

                /*
                * add current booking to the booking list and save to the file
                */
                bookingLinkedList.add(tempBooking);
                DatabaseUtility dbUtility = new DatabaseUtility();
                dbUtility.insertBooking(tempBooking);
                
                
                dataFile.openFile();
                if (dataFile.writeData(new ArrayList<Booking>(bookingLinkedList))) {
                    displayArea.setText("");
                    displayArea.append("Hello " + tempBooking.getCustomer().getFirstName() + ".");
                    displayArea.append(" Thank you for using the NEXT Hotel Booking System.");
                    displayArea.append("\n\nYour booking as shown below is confirmed.\n");
                    displayArea.append(tempBooking.toString());
                    bookingFlag = true;
                    dataFile.closeFile();

                } else {
                    JOptionPane.showMessageDialog(null, "Error Saving to File", "Save Data", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }//end saveBookingBtnActionPerformed method
    
    /**
     * A method that calls the reset method to clear the display, textfields 
     * and all data structures
     * 
     * @param evt The source of the event
     */
    private void clearDisplayBtnActionPerformed(ActionEvent evt) {
        reset();

    }//end clearDisplayBtnActionPerformed method
    
     /**
     * A method that gets called when the user clicks the "quit" button
     * 
     * This method closes the application if the inputPanel is not visible.
     * However, the application will display a yes/no option
     * dialog if the inputPanel is visible
     * 
     * @param evt The source of the event
     */
    private void quitBtnActionPerformed(ActionEvent evt) {
        if (inputPanel.isVisible() == true) {
            int quitOption = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit Application", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (quitOption == 0) {
                System.exit(0);
            } 
        } else {
            System.exit(0);
        }
    }//end quitBtnActionPerformed method
    
    /**
     * A method that gets called when the user clicks the "Search by Name" button
     * 
     * This method retrieves the relevant customer and booking information selected
     * by the user based on customer names.
     * 
     * @param evt The source of the event
     */
    private void searchNameBtnActionPerformed(java.awt.event.ActionEvent evt) {                                              
            
        if(choiceNameList.isSelectionEmpty())
        {
            JOptionPane.showMessageDialog(null,"Please make a selection","Search", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          
            String url = "jdbc:mysql://127.0.0.1/hotelDB";
            try {
                displayArea.setText("");
                Connection con = DriverManager.getConnection(url,"root","");
                PreparedStatement stmt1 = null;
                PreparedStatement stmt2 = null;
                
                ResultSet rs1 = null;
                ResultSet rs2 = null;
                ArrayList<String> list = new ArrayList<>();
                list.addAll(choiceNameList.getSelectedValuesList());

                for(int i = 0; i < list.size(); i++)
                {
                    stmt1 = con.prepareStatement("SELECT Title, CONCAT(FirstName,' ',LastName) AS Name, Email, Phone, Passport_DLicense, Country, ExpiryDate FROM Customer WHERE CONCAT(FirstName,' ',LastName) LIKE '"+list.get(i)+"%'");
                    stmt2 = con.prepareStatement("SELECT CheckinDate, CheckOutDate, NumOfGuests, RoomType, RoomRate FROM Customer,Booking WHERE Customer.CustomerID = Booking.CustomerID AND CONCAT(FirstName,' ',LastName) LIKE '"+list.get(i)+"%'");
                    rs1 = stmt1.executeQuery();
                    rs1.next();
                    if(rs1.getString("Country")==null)
                    {
                         displayArea.append(rs1.getString("Title")+" "+rs1.getString("Name")+", "+rs1.getString("Email")+", "+rs1.getString("Phone")+", "+rs1.getString("Passport_DLicense")+", "+rs1.getString("ExpiryDate")+"\n");
                    }
                    else
                    {
                         displayArea.append(rs1.getString("Title")+" "+rs1.getString("Name")+", "+rs1.getString("Email")+", "+rs1.getString("Phone")+", "+rs1.getString("Passport_DLicense")+", "+rs1.getString("country")+", "+rs1.getString("ExpiryDate")+"\n");
                       
                    }
                    rs2 = stmt2.executeQuery();
                    while(rs2.next())
                    {
                        displayArea.append("Check-In: "+rs2.getString("CheckInDate")+", Check-Out: "+rs2.getString("CheckOutDate")+", Guests: "+rs2.getInt("NumOFGuests")+", "+rs2.getString("RoomType")+", "+rs2.getFloat("RoomRate")+"\n");
                    }
                    
                }
                stmt1.close();
                stmt2.close();
                con.close();
            } catch (SQLException ex) {
               
            } 
        }
        
    }//end searchNameBtnActionPerformed method
    
    /**
     * A method that gets called when the user clicks the "Search by Date" button
     * 
     * This method retrieves the relevant customer and booking information selected
     * by the user based on CheckOut Dates.
     * 
     * @param evt The source of the event
     */
    private void searchDateBtnActionPerformed(java.awt.event.ActionEvent evt) {                                              
        
        if(choiceDateList.isSelectionEmpty())
        {
            JOptionPane.showMessageDialog(null,"Please make a selection","Search", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            String url = "jdbc:mysql://127.0.0.1/hotelDB";
            try {
                displayArea.setText("");
                Connection con = DriverManager.getConnection(url,"root","");
                PreparedStatement stmt = null;
                ResultSet rs = null;
                ArrayList<String> list = new ArrayList<>();
                list.addAll(choiceDateList.getSelectedValuesList());

                for(int i = 0; i < list.size(); i++)
                {
                    stmt = con.prepareStatement("SELECT * FROM Customer, Booking WHERE Customer.CustomerID = Booking.CustomerID AND CheckOutDate LIKE '"+list.get(i)+"'");
                    rs = stmt.executeQuery();
                    while(rs.next())
                    {
                        if(rs.getString("Country")==null)
                        {
                             displayArea.append(rs.getString("Title")+" "+rs.getString("FirstName")+" "+rs.getString("LastName")+", "+rs.getString("Email")+", "+rs.getString("Phone")+", "+rs.getString("Passport_DLicense")+", "+rs.getString("ExpiryDate")+"\n");
                        }
                        else
                        {
                             displayArea.append(rs.getString("Title")+" "+rs.getString("FirstName")+" "+rs.getString("LastName")+", "+rs.getString("Email")+", "+rs.getString("Phone")+", "+rs.getString("Passport_DLicense")+", "+rs.getString("country")+", "+rs.getString("ExpiryDate")+"\n");

                        }
                        
                        displayArea.append("Check-In: "+rs.getString("CheckInDate")+", Check-Out: "+rs.getString("CheckOutDate")+", Guests: "+rs.getInt("NumOFGuests")+", "+rs.getString("RoomType")+", "+rs.getFloat("RoomRate")+"\n");
//                      
                    }
                    
                    
                }
                stmt.close();
                con.close();
            } catch (SQLException ex) {
               
            }
        }
        
    }// end searchDateBtnActionPerformed method
    
    /**
     * A method to populate the JLists
     * 
     * This method takes in a parameter of type JList and populates it 
     * according to the source of the JList
     * 
     * @param list The JList to populate
     */
    public void populateJList(JList list)
    {
        try {
            DefaultListModel model = new DefaultListModel(); //create a new list model
            String url = "jdbc:mysql://127.0.0.1/hotelDB";
            Connection con = DriverManager.getConnection(url,"root","");
            PreparedStatement stmt = null;
            ResultSet resultSet = null;
            if(list == choiceNameList)
            {
                stmt = con.prepareStatement("SELECT CONCAT(FirstName,' ',LastName) AS Name FROM Customer");
                resultSet = stmt.executeQuery(); //run your query

                while (resultSet.next()) //go through each row that your query returns
                {
                    String itemCode = resultSet.getString("Name"); //get the element in column "item_code"
                    model.addElement(itemCode); //add each item to the model
                }
                list.setModel(model);
            }
            else
            {
                stmt = con.prepareStatement("SELECT DISTINCT CheckOutDate AS Date FROM Booking");
                resultSet = stmt.executeQuery(); //run your query

                while (resultSet.next()) //go through each row that your query returns
                {
                    String itemCode = resultSet.getString("Date"); //get the element in column "item_code"
                    model.addElement(itemCode); //add each item to the model
                }
                list.setModel(model);
            }
            
            resultSet.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error populating list","Populate JLiist", JOptionPane.ERROR_MESSAGE);
        }

    }//end populateJList method

    /**
     * A method that is called when user makes a customer type choice
     * 
     * This methods sets the inputPanel to visible and enables those 
     * fields appropriate for an international customer. 
     * The method switches the cards of the inputContainer from the emptyPanel
     * to the inputPanel
     * 
     * @param evt The source of the event
     */
    private void intlCustomerItemStateChanged(ItemEvent evt) {
        inputContainer.setVisible(true);
        inputPanelManager.show(this.inputContainer, "card2");
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            passport.setEnabled(true);
            countryList.setEnabled(true);
            driverLicense.setEnabled(false);
            driverLicenseLabel.setForeground(Color.black);
        }
    }//end intlCustomerItemStateChanged method

    /**
     * A method that is called when user makes a customer type choice
     * 
     * This methods sets the inputPanel to visible and enables those 
     * fields appropriate for a domestic customer. 
     * The method switches the cards of the inputContainer from the emptyPanel
     * to the inputPanel
     * 
     * @param evt The source of the event
     */
    private void domCustomerItemStateChanged(ItemEvent evt) {
        inputContainer.setVisible(true);
        inputPanelManager.show(this.inputContainer, "card2");
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            passport.setEnabled(false);
            countryList.setEnabled(false);
            driverLicense.setEnabled(true);
            passportLabel.setForeground(Color.black);
        }
    }//end domCustomerItemStateChanged method
    
    /**
     * A method that gets called when the user presses a key when focus is on 
     * expiryDate text field
     * 
     * This method sets the foreground color of the expiryDateLabel
     * to black if the label was red due to an error occurring. It also calls the 
     * checkDatesKeyTyped method. It sets the the foreground color of the 
     * corresponding label to red if the user has entered an invalid character
     * 
     * @param evt The source of the event
     */
    private void expiryDateKeyPressed(java.awt.event.KeyEvent evt) {
        expiryDateLabel.setForeground(Color.black);
        if (!checkDateKeyTyped(expiryDate, evt)) {
            expiryDateLabel.setForeground(Color.red);
        }
        
    }//end expiryDateKeyPressed method

    /**
     * A method that gets called when the user presses a key when focus is on 
     * checkInDate text field
     * 
     * This method sets the foreground color of the checkInLabel
     * to black if the label was red due to an error occurring. It also calls the 
     * checkDatesKeyTyped method. It sets the the foreground color of the 
     * corresponding label to red if the user has entered an invalid character
     * 
     * @param evt The source of the event
     */
    private void checkInDateKeyPressed(java.awt.event.KeyEvent evt) {
        checkInLabel.setForeground(Color.black);
        if (!checkDateKeyTyped(checkInDate, evt)) {
            checkInLabel.setForeground(Color.red);
        }
    }//end checkInDateKeyPressed method
    
    /**
     * A method that gets called when the user presses a key when focus is on 
     * checkOutDate text field
     * 
     * This method sets the foreground color of the checkOutLabel
     * to black if the label was red due to an error occurring. It also calls the 
     * checkDatesKeyTyped method. It sets the the foreground color of the 
     * corresponding label to red if the user has entered an invalid character
     * 
     * @param evt The source of the event
     */
    private void checkOutDateKeyPressed(java.awt.event.KeyEvent evt) {
        checkOutLabel.setForeground(Color.black);
        if (!checkDateKeyTyped(checkOutDate, evt)) {
            checkOutLabel.setForeground(Color.red);
        }
    }//end checkOutDateKeyPressed method

    /**
     * A method that gets called when the user presses a key when focus is on 
     * phone text field
     * 
     * This method sets the foreground color of the phoneLabel
     * to black if the label was red due to an error occurring. It sets the 
     * "editable" property of the textfield to true if the user enters only
     * numbers with the exception of the backspace, delete and arrow keys. 
     * It displays appropriate error messages if users tries to enter more than
     * 10 numbers and if the user enters any other characters  
     * 
     * @param evt The source of the event
     */
    private void phoneKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {
            phoneLabel.setForeground(Color.black);
            if (phone.getText().length() < 10) {
                phone.setEditable(true);

            } else {
                phone.setEditable(false);
                JOptionPane.showMessageDialog(null, "Australian Phone should be only 10 digits",
                                             "Phone Number", JOptionPane.ERROR_MESSAGE);

            }
        } else {
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACKSPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE 
                                                                  || evt.getExtendedKeyCode() == KeyEvent.VK_LEFT 
                                                                  || evt.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {
                phone.setEditable(true);
            } else {
                phone.setEditable(false);
                if (phone.getText().length() < 10) {
                    phoneLabel.setForeground(Color.red);
                }

                phone.setBackground(Color.white);
                JOptionPane.showMessageDialog(null, "Invalid Character", "Phone Number",
                                              JOptionPane.ERROR_MESSAGE);

            }
        }
    }//end phoneKeyPressed method

    /**
     * A method that gets called when the user presses a key when focus is on 
     * firstName text field
     * 
     * This method sets the foreground of the firstNameLabel to black
     * 
     * @param evt The source of the event
     */
    private void firstNameKeyPressed(java.awt.event.KeyEvent evt) {
        firstNameLabel.setForeground(Color.black);

    }//end firstNameKeyPressed method

    /**
     * A method that gets called when the user presses a key when focus is on 
     * lastName text field
     * 
     * This method sets the foreground of the lastNameLabel to black
     * 
     * @param evt The source of the event
     */
    private void lastNameKeyPressed(java.awt.event.KeyEvent evt) {
        lastNameLabel.setForeground(Color.black);

    }//end lastNameKeyPressed method

    /**
     * A method that gets called when the user presses a key when focus is on 
     * passport text field
     * 
     * This method sets the foreground of the passportLabel to black
     * 
     * @param evt The source of the event
     */
    private void passportKeyPressed(java.awt.event.KeyEvent evt) {
        passportLabel.setForeground(Color.black);

    } //end passportKeyPressed method

    /**
     * A method that gets called when the user presses a key when focus is on 
     * email text field
     * 
     * This method sets the foreground of the emailLabel to black
     * 
     * @param evt The source of the event
     */
    private void emailKeyPressed(java.awt.event.KeyEvent evt) {
        emailLabel.setForeground(Color.black);

    }//end emailKeyPressed method

    /**
     * A method that gets called when the user presses a key when focus is on 
     * driverLicense text field
     * 
     * This method sets the foreground of the driverLicenseLabel to black
     * 
     * @param evt The source of the event
     */
    private void driverLicenseKeyPressed(java.awt.event.KeyEvent evt) {
        driverLicenseLabel.setForeground(Color.black);

    }//end driverLicenseKeyPressed method

    /**
     * A method that gets called when focus is on phone text field
     * 
     * This method sets the editable property of the driverLicense textfield
     * to true
     * 
     * @param evt The source of the event
     */
    private void phoneFocusGained(FocusEvent evt) {
        phone.setEditable(true);

    }//end phoneFocusGained method

    /**
     * A method that gets called when focus is on expiryDate text field
     * 
     * This method sets the editable property of the expiryDate textfield
     * to true
     * 
     * @param evt The source of the event
     */
    private void expiryDateFocusGained(FocusEvent evt) {
        expiryDate.setEditable(true);

    }//end expiryDateFocusGained method

    /**
     * A method that gets called when focus is on checkInDate text field
     * 
     * This method sets the editable property of the checkInDate textfield
     * to true
     * 
     * @param evt The source of the event
     */
    private void checkInDateFocusGained(FocusEvent evt) {
        checkInDate.setEditable(true);

    }//end checkInDateFocusGained method

    /**
     * A method that gets called when focus is on checkOutDate text field
     * 
     * This method sets the editable property of the checkOutDate textfield
     * to true
     * 
     * @param evt The source of the event
     */
    private void checkOutDateFocusGained(FocusEvent evt) {
        checkOutDate.setEditable(true);

    }//end checkOutDateFocusGained method

    /**
     *A method that fills the countryList combobox with all the country names
     * 
     * @return String array containing all the country names
     */
    public String[] getAllCountries() {
        String[] countries = new String[Locale.getISOCountries().length];
        String[] countryCodes = Locale.getISOCountries();
        for (int i = 0; i < countryCodes.length; i++) {
            Locale obj = new Locale("", countryCodes[i]);
            countries[i] = obj.getDisplayCountry();
        }
        return countries;
        
    }//end getAllCountries method

    /**
     *A method that clears all the textfields and displayArea. It also sets all 
     * the flags to false
     * 
     */
    public void reset() {
        domCustomer.setSelected(false);
        intlCustomer.setSelected(false);
        titleBox.setSelectedIndex(0);
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        driverLicense.setText("");
        phone.setText("");
        passport.setText("");
        countryList.setSelectedIndex(0);
        expiryDate.setText("");
        checkInDate.setText("");
        checkOutDate.setText("");
        guestNumberBox.setSelectedIndex(0);
        roomTypeBox.setSelectedIndex(0);
        tempBooking = null;
        checkDatesFlag = false;
        bookingFlag = false;
        displayArea.setText("");

    }//end reset method

    /**
     *A method that checks the pattern of the email
     * 
     * This method takes in the email entered by users and checks if it matches
     * the correct format. Returns true if it matches and false if it doesn't.
     * 
     * @param email The email entered by users 
     * @return boolean value 
     */
    public boolean isEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" 
                               + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return email.matches(EMAIL_PATTERN);

    }//end isEmail method

    /**
     *A method that checks if the date entered by users is in the correct format
     * 
     * This method returns true if the date is in the correct format depending 
     * on the source and false if it doesn't match.
     * 
     * @param date the textfield / source of the date
     * @return boolean value
     */
    public boolean isDate(JFormattedTextField date) {
        SimpleDateFormat sdf;
        
        if (date == expiryDate) {
            sdf = new SimpleDateFormat("MM/yyyy");
        } else {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
        }

        sdf.setLenient(false);
        Date dt;
        try {
            dt = sdf.parse(date.getText());
        } catch (ParseException ex) {
            return false;
        }

        return true;
        
    }//end isDate method

    /**
     *A method that sets the appropriate label foreground to red
     * 
     * This  method checks if the fields are empty and changes the foreground
     * of the corresponding label to red when the user clicks the save button
     * 
     */
    public void doValidation() {
        if (firstName.getText().isEmpty()) {
            firstNameLabel.setForeground(Color.red);
        }
        if (lastName.getText().isEmpty()) {
            lastNameLabel.setForeground(Color.red);
        }
        if (email.getText().isEmpty()) {
            emailLabel.setForeground(Color.red);
        }
        if (phone.getText().isEmpty() || phone.getText().length() < MAX_PHONE_LENGTH) {
            phoneLabel.setForeground(Color.red);
        }
        if (expiryDate.getText().isEmpty()) {
            expiryDateLabel.setForeground(Color.red);
        }
        if (checkInDate.getText().isEmpty()) {
            checkInLabel.setForeground(Color.red);
        }
        if (checkOutDate.getText().isEmpty()) {

            checkOutLabel.setForeground(Color.red);
        }
        if ((customerType.getSelection().getActionCommand().equalsIgnoreCase(DOM_CUSTOMER_COMMAND)) 
                                                          && (driverLicense.getText().isEmpty()
                                                          || (driverLicense.getText().length() == INVALID_DLNUMBER_LENGTH)
                                                          || (driverLicense.getText().length() < MIN_DLNUMBER_LENGTH)
                                                          || (driverLicense.getText().length() > MAX_DLNUMBER_LENGTH))) {
            driverLicenseLabel.setForeground(Color.red);
        } else {
            driverLicenseLabel.setForeground(Color.black);
        }
        if ((customerType.getSelection().getActionCommand().equalsIgnoreCase(INTL_CUSTOMER_COMMAND))
                                                           && (passport.getText().isEmpty())) {
            passportLabel.setForeground(Color.red);
        } else {
            passportLabel.setForeground(Color.black);
        }

    }//end doValidation method

    /**
     * A method to check and validate the textfields.
     * 
     * This method does all the validation required for the application to 
     * function properly. It displays the appropriate error message
     * and then returns true. If there are no errors, it returns false
     * 
     * @return boolean value 
     */
    public boolean checkFields() {

        if (customerType.getSelection().getActionCommand().equalsIgnoreCase(DOM_CUSTOMER_COMMAND)) {

            /*
            * Check if the fields for domestic customer are empty after clicking
            * Save button
            */
            if (firstName.getText().isEmpty() || lastName.getText().isEmpty()
                                              || email.getText().isEmpty()
                                              || phone.getText().isEmpty()
                                              || driverLicense.getText().isEmpty()
                                              || expiryDate.getText().isEmpty()) {
                
                doValidation();
                JOptionPane.showMessageDialog(null, "Please Enter ALL Appropriate Fields",
                                             "Save Data", JOptionPane.ERROR_MESSAGE);
                return true;
            }

        } 
        /*
         * Check if the fields for international customer are empty after 
         * clicking the save button
         */
        else if (customerType.getSelection().getActionCommand().equalsIgnoreCase(INTL_CUSTOMER_COMMAND)) {
            if (firstName.getText().isEmpty() || lastName.getText().isEmpty()
                                              || email.getText().isEmpty()
                                              || phone.getText().isEmpty()
                                              || passport.getText().isEmpty()
                                              || expiryDate.getText().isEmpty()) {
                
                doValidation();
                JOptionPane.showMessageDialog(null, "Please Enter ALL Appropriate Fields", "Save Data", JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }
        /*
        * Check if email is correct format
        */
        if (!isEmail(email.getText())) {
            emailLabel.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "Incorrect Email Format", "Save Data",
                                          JOptionPane.ERROR_MESSAGE);
            
            return true;
        }
        /*
        * Check if booking dates are empty
        */
        if (checkInDate.getText().isEmpty() || checkOutDate.getText().isEmpty()) {
            doValidation();
            JOptionPane.showMessageDialog(null, "Please Enter All Booking Dates",
                                          "Save Data", JOptionPane.ERROR_MESSAGE);
            
            return true;

        }
        /*
        * Check if all dates are correct format
        */
        if ((!isDate(checkInDate)) || (!isDate(checkOutDate)) || (!isDate(expiryDate))) {

            JOptionPane.showMessageDialog(null, "Invalid Date Format", "Save Data",
                                          JOptionPane.ERROR_MESSAGE);
            
            return true;
        }
        /*
        * Check if phone number is of correct length
        */
        if (phone.getText().length() < MAX_PHONE_LENGTH) {
            doValidation();
            JOptionPane.showMessageDialog(null, "Invalid Phone Number", "Phone Number",
                                          JOptionPane.ERROR_MESSAGE);
            
            return true;

        }
        
        if (customerType.getSelection().getActionCommand().equals(DOM_CUSTOMER_COMMAND)) {

            /*
            * Check if driver license number is of correct length
            */
            if ((driverLicense.getText().length() == INVALID_DLNUMBER_LENGTH) ||
                                                                              (driverLicense.getText().length() < MIN_DLNUMBER_LENGTH)
                                                                              || (driverLicense.getText().length() > MAX_DLNUMBER_LENGTH)) {
                
                doValidation();
                JOptionPane.showMessageDialog(null, "Australian DL must be either 6,7,9 or 10 characters ",
                                             "Driver's License Number Data",
                                             JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }
        return false;
    }//end chekFields method

    /**
     * A method that checks and validates all the dates entered by the user
     * 
     * This methods performs validation for the different scenarios that 
     * shouldn't occur pertaining to the dates entered when user
     * tries to save the booking. Returns true if there are errors and false if
     * there are none.
     *      * 
     * @param date The source of the date
     * @return boolean value
     */
    public boolean checkDates(JFormattedTextField date) {
        SimpleDateFormat sdf;
        
        /*
        * Check if source is expiryDate
        */
        if (date == expiryDate) {
            sdf = new SimpleDateFormat("MM/yyyy");

            try {
                /*
                * Check if the expiryDate is prior to current date
                */
                if (sdf.parse(date.getText()).compareTo(new Date()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Expiry Date must be greater than the current date",
                                                  "Date Check", JOptionPane.ERROR_MESSAGE);
                    expiryDateLabel.setForeground(Color.red);
                    return true;

                }
                /*
                * Check if expiryDate is less than checkOutDate. Customers must 
                * have a valid ID to be able to make a booking
                */
                
                
                if (new SimpleDateFormat("dd/MM/yyyy").parse("01/"+expiryDate.getText()).before(new SimpleDateFormat("dd/MM/yyyy").parse(checkOutDate.getText()))) {
                    JOptionPane.showMessageDialog(null, "Expiry Date must be greater than the Check-out date",
                                                 "Date Check", JOptionPane.ERROR_MESSAGE);
                    expiryDateLabel.setForeground(Color.red);
                    return true;

                }

            } catch (ParseException ex) {

                expiryDateLabel.setForeground(Color.red);
                JOptionPane.showMessageDialog(null, "Invalid Date Format", "Date Check",
                                             JOptionPane.ERROR_MESSAGE);
                return true;
            }

        } 
        /*
        * Check if source is checkInDate
        */
        else if (date == checkInDate) {
            sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                /*
                * Check if check in date is prior to current date
                */
                if (sdf.parse(date.getText()).compareTo(new Date()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Check-in Date must be greater than the current date",
                                                 "Date Check", JOptionPane.ERROR_MESSAGE);
                    
                    checkInLabel.setForeground(Color.red);
                    return true;

                }
                /*
                * Check if check in date is after the check out date
                */
                if (sdf.parse(checkInDate.getText()).compareTo(sdf.parse(checkOutDate.getText())) >= 0) {
                    JOptionPane.showMessageDialog(null, "Check-in Date must be before than the Check-out date",
                                                 "Date Check", JOptionPane.ERROR_MESSAGE);
                    
                    checkInLabel.setForeground(Color.red);
                    return true;

                }

            } catch (ParseException ex) {

                JOptionPane.showMessageDialog(null, "Invalid Date Format", "Date Check",
                                             JOptionPane.ERROR_MESSAGE);
                
                checkInLabel.setForeground(Color.red);
                return true;

            }
        } 
        /*
        * Check if source is checkOutDate
        */
        else {
            sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                /*
                * check if check out ate is prior to current date
                */
                if (sdf.parse(date.getText()).compareTo(new Date()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Check-out Date must be greater than the current date",
                                                 "Date Check", JOptionPane.ERROR_MESSAGE);
                    
                    checkOutLabel.setForeground(Color.red);
                    return true;

                }
                

            } catch (ParseException ex) {

                JOptionPane.showMessageDialog(null, "Invalid Date Format", "Date Check",
                                             JOptionPane.ERROR_MESSAGE);
               
                checkOutLabel.setForeground(Color.red);
                return true;
            }
        }
        return false;
        
    }//end checkDates method

    /**
     *a method to that checks the key typed when the focus is on a date field
     * 
     * This method is called by the KeyListener of the date fields
     * to check whether the user has typed an invalid character in
     * the date field. It displays an error message for each time the user
     * types an invalid character.
     * Returns false if the user typed an invalid character and true if the 
     * character is valid
     * 
     * @param date The source of the date
     * @param evt The source of the event that occurred
     * @return boolean value
     */
    public boolean checkDateKeyTyped(JFormattedTextField date, java.awt.event.KeyEvent evt) {
        if (!((evt.getKeyChar() >= '0' && (evt.getKeyChar()) <= '9') || evt.getKeyChar() == '/' || evt.getExtendedKeyCode() == KeyEvent.VK_BACKSPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE || evt.getExtendedKeyCode() == KeyEvent.VK_LEFT || evt.getExtendedKeyCode() == KeyEvent.VK_RIGHT)) {

            date.setEditable(false);
            JOptionPane.showMessageDialog(null, "Invalid Character", "Date Format", JOptionPane.ERROR_MESSAGE);
            return false;

        } else {
            date.setEditable(true);
            return true;
        }

    }//end checkDateKeyTyped method

    /**
     * The main method the application
     * 
     * This method creates a new object of type BookingGUI to start the 
     * application. It also sets the 'Look and Feel' of the application
     * to the System's 'Look and Feel'
     * 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                BookingGUI bookingGUI = new BookingGUI();
                bookingGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                bookingGUI.setVisible(true);
                bookingGUI.pack();
                bookingGUI.setLocationRelativeTo(null);
                SwingUtilities.updateComponentTreeUI(bookingGUI);
            }
        });
    }//end main method

    /**
     * A method that gets called on application startup
     * 
     * This method is responsible for generating data from the Booking.csv file
     * onto the database
     * 
     */
    private void loadData() 
    {
     
        DatabaseUtility dbUtility = new DatabaseUtility();
        
        if(!dbUtility.connectDatabase())
        {
            JOptionPane.showMessageDialog(null,"Error connecting to database","Database",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Successfully connected to the database","Database",JOptionPane.INFORMATION_MESSAGE);
        }
        
        if(!dbUtility.createTables())
        {
            JOptionPane.showMessageDialog(null,"Error creating database","Database",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Successfully created the database","Database",JOptionPane.INFORMATION_MESSAGE);
        }
        
        dataFile.openFile();
        
        if(!dataFile.readData(bookingLinkedList))
        {
            JOptionPane.showMessageDialog(null,"Error adding Booking information to Linked List","Booking List",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            
            for (Booking booking : bookingLinkedList) {
                dbUtility.insertBooking(booking);
            }
            JOptionPane.showMessageDialog(null,"Successfully added bookings to Linked List","Booking List",JOptionPane.INFORMATION_MESSAGE);
            dbUtility.closeConnection();
        }
    }
    
}//end BookingGUI class
