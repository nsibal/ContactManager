package com.sibalnirbhay;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ContactManager extends App{


    App app = new App();

    private static String url = "jdbc:mysql://localhost:3306/ContactManager?" +
            "autoReconnect=true" +
            "&useSSL=false" +
            "&useUnicode=true" +
            "&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false" +
            "&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "root1234";
    Connection connection;


    ContactManager() throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException, SQLException {

        /*
         * ActionListener for Add Contact Button
         */
        app.getAddContactButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        /*
         * ActionListener for Refresh button
         */
        app.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshScreen();
            }
        });

        /*
         * ActionListener the table.
         * Invokes a dialog box upon receiving a double click on a row.
         * Gives option either to delete the contact, or update it.
         */
        app.getOutputTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = app.getOutputTable().getSelectedRow();
                    int contactID = (Integer) app.getOutputTable().getModel().getValueAt(selectedRow, 0);
                    int choice = chooseOptionFromDialog();
                    if (choice == 0) {
                        deleteContact(contactID);
                    } else if (choice == 1) {
                        update(contactID, selectedRow);
                    }
                }
            }
        });


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                app.setVisible(true);
            }
        });
        initialize();
    }

    /**************************************************************************
     * This updates the selected contact, which is visually a row in the table.
     *
     * @param
     * contactID: ContactID of the selected contact
     * selectedRow: Row number of the selected row in the table.
     **************************************************************************/
    void update(int contactID, int selectedRow) {
        app.setFieldsFromRow(selectedRow);
        String phoneInst1 = app.getPhoneNumber();
        String emailInst1 = app.getEmailAddress();
        String streetAddressInst1 = app.getStreetAddress();
        String cityInst1 = app.getCity();
        String stateInst1 = app.getStateArea();
        String zipInst1 = app.getZip();
        String countryInst1 = app.getCountry();
        app.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact(contactID, phoneInst1, emailInst1, streetAddressInst1,
                        cityInst1, stateInst1, zipInst1, countryInst1);
            }
        });
    }

    /**************************************************************************
     * Refreshes the screen
     * Clears the input fields and recreates the table.
     **************************************************************************/
    void refreshScreen() {
        initialize();
        app.refreshFields();
    }

    /**************************************************************************
     * Directs some of the errors (like telling the user that the entered phone
     * number already exists) to the stauts bar.
     **************************************************************************/
    void errorResponse(Exception ex) {
        app.setStatus("Error in connection: "+ex.getMessage());
        app.setEnabledItems(false);
    }

    /**************************************************************************
     * Creates a connection to the database.
     **************************************************************************/
    void initialize() {
        try {
            String sql = "SELECT CONTACT.ContactID, CONTACT.FirstName, CONTACT.Minit, " +
                    "CONTACT.LastName, CONTACT.Sex, CONTACT.DateOfBirth, " +
                    "PHONENUMBER.PhoneNumber, " +
                    "EMAILADDRESS.EmailAddress, " +
                    "ADDRESS.StreetAddress, ADDRESS.City, ADDRESS.State, ADDRESS.ZIP, ADDRESS.Country " +
                    "FROM CONTACT LEFT JOIN PHONENUMBER ON CONTACT.ContactID = PHONENUMBER.ContactID " +
                    "LEFT JOIN EMAILADDRESS ON CONTACT.ContactID = EMAILADDRESS.ContactID " +
                    "LEFT JOIN ADDRESS ON CONTACT.ContactID = ADDRESS.ContactID " +
                    "ORDER BY CONTACT.FirstName ASC, CONTACT.LastName ASC;";

            //Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            generateTable(resultSet);
            resultSet.close();

            app.setEnabledItems(true);
            //initialize();
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Creates the table for output
     **************************************************************************/
    void generateTable(ResultSet resultSet) {

        Vector columnNames = new Vector();
        columnNames.add("Contact ID");
        columnNames.add("First Name");
        columnNames.add("M.I.");
        columnNames.add("Last Name");
        columnNames.add("Sex");
        columnNames.add("DOB");
        columnNames.add("Phone Number");
        columnNames.add("Email Address");
        columnNames.add("Street Address");
        columnNames.add("City");
        columnNames.add("State");
        columnNames.add("ZIP");
        columnNames.add("Country");

        Vector data = new Vector();

        try {

            while (resultSet.next()) {
                Vector row = new Vector();

                row.add(resultSet.getInt("CONTACT.ContactID"));
                row.add(resultSet.getString("CONTACT.FirstName"));
                row.add(resultSet.getString("CONTACT.Minit"));
                row.add(resultSet.getString("CONTACT.LastName"));
                row.add(resultSet.getString("CONTACT.Sex"));
                row.add(resultSet.getDate("CONTACT.DateOfBirth", Calendar.getInstance()));
                row.add(resultSet.getString("PHONENUMBER.PhoneNumber"));
                row.add(resultSet.getString("EMAILADDRESS.EmailAddress"));
                row.add(resultSet.getString("ADDRESS.StreetAddress"));
                row.add(resultSet.getString("ADDRESS.City"));
                row.add(resultSet.getString("ADDRESS.State"));
                row.add(resultSet.getString("ADDRESS.ZIP"));
                row.add(resultSet.getString("ADDRESS.Country"));

                data.add(row);
            }

            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            app.setOutputTableModel(tableModel);
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Deletes a contact
     *
     * @param
     * contactId: Deletes all the details of a person based on this contactId
     **************************************************************************/
    void deleteContact (int contactId) {
        String sql = "DELETE FROM CONTACT "+
                "WHERE ContactID= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            int deleteCount = preparedStatement.executeUpdate();
            refreshScreen();
            app.setStatus("Contacts deleted: "+deleteCount);
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Adds new contact to the database.
     * Entering the first name is crucial, rest all the details are optional
     **************************************************************************/
    void addContact () {
        boolean addPhoneNumber = false;
        boolean addEmailAddress = false;
        boolean addAddress = false;

        int contactId = getNextContactID();

        String firstName = app.getFirstName();
        String minIt = app.getMinIt();
        String lastName = app.getLastName();
        String sex = app.getSex();
        String dateOfBirth = app.getDateOfBirth();
        String phoneNumber = app.getPhoneNumber();
        String emailAddress = app.getEmailAddress();
        String streetAddress = app.getStreetAddress();
        String city = app.getCity();
        String stateArea = app.getStateArea();
        String zip = app.getZip();
        String country = app.getCountry();

        String sql = "INSERT INTO CONTACT(ContactID,FirstName,Minit,LastName,Sex,DateOfBirth) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            preparedStatement.setNull(2, Types.VARCHAR);
            preparedStatement.setNull(3, Types.VARCHAR);
            preparedStatement.setNull(4, Types.VARCHAR);
            preparedStatement.setNull(5, Types.CHAR);
            preparedStatement.setNull(6, Types.DATE);

            /**************************************************************************
             * Validates First Name
             **************************************************************************/
            if (!firstName.equals(null) && !firstName.equals("")) {
                if (firstName.length()<=20) {
                    preparedStatement.setString(2, firstName);
                } else {
                    throw new DataTooLongException("First Name");
                }
            } else {
                throw new NullFieldException("First Name");
            }


            /**************************************************************************
             * Validates Middle Name
             **************************************************************************/
            if(!minIt.equals(null) && !minIt.equals("")) {
                if(minIt.length()==1) {
                    preparedStatement.setString(3, minIt);
                } else {
                    throw new DataTooLongException("M.I.");
                }
            }

            /**************************************************************************
             * Validates Last Name
             **************************************************************************/
            if(!lastName.equals(null) && !lastName.equals("")) {
                if(lastName.length()<=30) {
                    preparedStatement.setString(4, lastName);
                } else {
                    throw new DataTooLongException("Last Name");
                }
            }

            /**************************************************************************
             * Validates Sex
             **************************************************************************/
            if(!sex.equals(null) && !sex.equals("")) {
                preparedStatement.setString(5, sex);
            }

            /**************************************************************************
             * Validates Date Of Birth
             **************************************************************************/
            if(!dateOfBirth.equals(null) && !dateOfBirth.equals("")) {
                if (checkDateFormat(dateOfBirth)) {
                    preparedStatement.setDate(6, java.sql.Date.valueOf(dateOfBirth));
                } else {
                    throw new InvalidDateException("DOB");
                }
            }

            /**************************************************************************
             * Validates Phone Number
             **************************************************************************/
            if(!phoneNumber.equals(null) && !phoneNumber.equals("")) {
                if(phoneNumber.length()<=20) {
                    //System.out.println("before validation");
                    if (validatePhoneNumber(phoneNumber)) {
                        //System.out.println("after validation");
                        addPhoneNumber = true;
                    } else {
                        throw new DuplicateDataException("Phone Number");
                    }
                } else {
                    throw new DataTooLongException("Phone Number");
                }
            }

            /**************************************************************************
             * Validates Email Address
             **************************************************************************/
            if(!emailAddress.equals(null) && !emailAddress.equals("")) {
                if(emailAddress.length()<=320) {
                    if (validateEmailAddress(emailAddress)) {
                        addEmailAddress = true;
                    } else {
                        throw new DuplicateDataException("Email Address");
                    }
                } else {
                    throw new DataTooLongException("Email Address");
                }
            }

            if((!streetAddress.equals(null) && !streetAddress.equals(""))
                    || (!city.equals(null) && !city.equals(""))
                    || (!stateArea.equals(null) && !stateArea.equals(""))
                    || (!zip.equals(null) && !zip.equals(""))
                    || (!country.equals(null) && !country.equals(""))) {

                if (!streetAddress.equals(null) && !streetAddress.equals("")) {
                    if (streetAddress.length()<=50) {
                        addAddress = true;
                    } else {
                        throw new DataTooLongException("Street Address");
                    }
                } else {
                    streetAddress = null;
                }

                if (!city.equals(null) && !city.equals("")) {
                    if (city.length()<=50) {
                        addAddress = true;
                    } else {
                        throw new DataTooLongException("City");
                    }
                } else {
                    city = null;
                }

                if (!stateArea.equals(null) && !stateArea.equals("")) {
                    if (stateArea.length()<=50) {
                        addAddress = true;
                    } else {
                        throw new DataTooLongException("State");
                    }
                } else {
                    stateArea = null;
                }

                if (!zip.equals(null) && !zip.equals("")) {
                    if (zip.length()<=15) {
                        addAddress = true;
                    } else {
                        throw new DataTooLongException("Zip");
                    }
                } else {
                    zip = null;
                }

                if (!country.equals(null) && !country.equals("")) {
                    if (country.length()<=40) {
                        addAddress = true;
                    } else {
                        throw new DataTooLongException("Country");
                    }
                } else {
                    country = null;
                }

            }

            int count = preparedStatement.executeUpdate();

            if (addPhoneNumber) {
                addPhoneNumber(contactId,phoneNumber);
            }

            if (addEmailAddress) {
                addEmailAddress(contactId,emailAddress);
            }

            if (addAddress) {
                addAddress(contactId,streetAddress,city,stateArea,zip,country);
            }

            refreshScreen();
            app.setStatus("Contacts added: "+count);
        } catch (NullFieldException ex) {
            app.setStatus(ex.getMessage()+" can't be left blank.");
        } catch (DataTooLongException ex) {
            app.setStatus("Data too long for field "+ex.getMessage()+".");
        } catch (InvalidDateException ex) {
            app.setStatus("Entered " + ex.getMessage() + " is not between 1850-01-01 and today's date!");
        } catch (DuplicateDataException ex) {
            app.setStatus(ex.getMessage()+" already exists in the database for some contact!");
        } catch (ParseException ex) {
            app.setStatus(ex.getMessage());
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Returns an integer, which is 1 greater than the maximum ContactID
     **************************************************************************/
    int getNextContactID() {
        int maxID = 0;
        String sql = "SELECT MAX(ContactID) " +
                "FROM CONTACT;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maxID = resultSet.getInt("MAX(ContactID)");
            }
            resultSet.close();
        } catch (Exception ex) {
            errorResponse(ex);
        }

        return maxID+1;
    }

    /**************************************************************************
     * Add Phone Number
     **************************************************************************/
    void addPhoneNumber (int contactId, String phoneNumber) {
        String sql = "INSERT INTO PHONENUMBER(ContactID,PhoneNumber) " +
                "VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Add Email Address
     **************************************************************************/
    void addEmailAddress (int contactId, String emailAddress) {
        String sql = "INSERT INTO EMAILADDRESS(ContactID,EmailAddress) " +
                "VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            preparedStatement.setString(2, emailAddress);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Add Address
     **************************************************************************/
    void addAddress (int contactId, String streetAddress, String city, String stateArea, String zip, String country) {
        String sql = "INSERT INTO ADDRESS(ContactID,StreetAddress,City,State,Zip,Country) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            preparedStatement.setString(2,streetAddress);
            preparedStatement.setString(3,city);
            preparedStatement.setString(4,stateArea);
            preparedStatement.setString(5,zip);
            preparedStatement.setString(6,country);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Checks if the date of birth is entered in the following format:
     * yyyy-MM-dd
     *
     * Also checks if the date is less than or equal to the current date, and
     * greater than or equal to January 01, 1850.
     **************************************************************************/
    boolean checkDateFormat(String date) throws ParseException {
        boolean check = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dateFormat.parse(date);
        int year = Integer.parseInt(date.substring(0,4));
        if (year < 1850) {
            check = false;
            ;
        } else {
            java.util.Date currentDate = new Date();
            long dateDiff = (dateFormat.parse(dateFormat.format(currentDate)).getTime()
                    - dateFormat.parse(date).getTime())
                    / (24 * 60 * 60 * 1000);
            if (dateDiff >= 0) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }

    /**************************************************************************
     * Validates Phone Number
     * Returns false if the passed phone number already exists in the database.
     **************************************************************************/
    boolean validatePhoneNumber(String phoneNumber) {
        boolean notFound = true;
        String sql = "SELECT * "+
                "FROM PHONENUMBER " +
                "WHERE PhoneNumber= ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                notFound = false;
            }
            resultSet.close();
        } catch (Exception ex) {
            errorResponse(ex);
        }
        return notFound;
    }

    /**************************************************************************
     * Validates Email Address
     * Returns false if the passed email address already exists in the database.
     **************************************************************************/
    boolean validateEmailAddress(String emailAddress) {
        boolean notFound = true;
        String sql = "SELECT * "+
                "FROM EMAILADDRESS " +
                "WHERE EmailAddress= ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, emailAddress);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                notFound = false;
            }
            resultSet.close();
        } catch (Exception ex) {
            errorResponse(ex);
        }
        return notFound;
    }

    /**************************************************************************
     * Updates the contact
     *
     * @param
     * contactId: The id of the contact whose details have to be updated.
     *
     * phoneInst1: The phone number of that particular contact which is currently
     *          present in the database.
     *
     * emailInst1: The email address of that particular contact which is currently
     *        present in the database.
     *
     * streetAddressInst1: Existing street address of the contact
     *
     * cityInst1: Existing City
     *
     * stateInst1: Existing State
     *
     * zipInst1: Existing ZIP
     *
     * countryInst1: Existing Country
     **************************************************************************/
    void updateContact(int contactId, String phoneInst1, String emailInst1, String streetAddressInst1,
                       String cityInst1, String stateInst1, String zipInst1, String countryInst1) {

        String firstName = app.getFirstName();
        String minIt = app.getMinIt();
        String lastName = app.getLastName();
        String sex = app.getSex();
        String dateOfBirth = app.getDateOfBirth();
        String phoneInst2 = app.getPhoneNumber();
        String emailInst2 = app.getEmailAddress();
        String streetAddressInst2 = app.getStreetAddress();
        String cityInst2 = app.getCity();
        String stateInst2 = app.getStateArea();
        String zipInst2 = app.getZip();
        String countryInst2 = app.getCountry();

        boolean addAddress = false;

        String sql = "UPDATE CONTACT SET " +
                "FirstName = ?, " +
                "Minit = ?, " +
                "LastName = ?, " +
                "Sex = ?, " +
                "DateOfBirth = ? " +
                "WHERE ContactID = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            if(!firstName.equals(null) && !firstName.equals("")) {
                if (firstName.length()<=20) {
                    preparedStatement.setString(1, firstName);
                } else {
                    throw new DataTooLongException("First Name");
                }

            } else {
                throw new NullFieldException("First Name");
            }

            if(!minIt.equals(null) && !minIt.equals("")) {
                if(minIt.length()==1) {
                    preparedStatement.setString(2, minIt);
                } else {
                    throw new DataTooLongException("M.I.");
                }
            } else {
                preparedStatement.setString(2, null);
            }

            if(!lastName.equals(null) && !lastName.equals("")) {
                if(lastName.length()<=30) {
                    preparedStatement.setString(3, lastName);
                } else {
                    throw new DataTooLongException("Last Name");
                }
            } else {
                preparedStatement.setString(3, null);
            }

            preparedStatement.setString(4, sex==""?null:sex);

            if(!dateOfBirth.equals(null) && !dateOfBirth.equals("")) {
                if (checkDateFormat(dateOfBirth)) {
                    preparedStatement.setDate(5, java.sql.Date.valueOf(dateOfBirth));
                } else {
                    throw new InvalidDateException("DOB");
                }
            } else {
                preparedStatement.setString(5, null);
            }

            preparedStatement.setString(1,firstName);
            preparedStatement.setInt(6, contactId);

            if (!phoneInst1.equals(phoneInst2)) {
                if (phoneInst1.equals(null) || phoneInst1.equals("")) {
                    addPhoneNumber(contactId, phoneInst2);
                } else if (phoneInst2.equals(null) || phoneInst2.equals("")) {
                    deletePhone(phoneInst1);
                } else {
                    if (validatePhoneNumber(phoneInst2)) {
                        updatePhone(contactId,phoneInst2);
                    } else {
                        throw new DuplicateDataException("Phone Number");
                    }
                }
            }

            if (!emailInst1.equals(emailInst2)) {
                if (emailInst1.equals(null) || emailInst1.equals("")) {
                    addEmailAddress(contactId, emailInst2);
                } else if (emailInst2.equals(null) || emailInst2.equals("")) {
                    deleteEmail(emailInst1);
                } else {
                    if (validateEmailAddress(emailInst2)) {
                        updateEmail(contactId,emailInst2);
                    } else {
                        throw new DuplicateDataException("Email Address");
                    }
                }
            }

            if ((!streetAddressInst1.equals(streetAddressInst2)) || (!cityInst1.equals(cityInst2))
                    || (!stateInst1.equals(stateInst2)) || (!zipInst1.equals(zipInst2))
                    || (!countryInst1.equals(countryInst2))) {
                if((!streetAddressInst2.equals(null) && !streetAddressInst2.equals(""))
                        || (!cityInst2.equals(null) && !cityInst2.equals(""))
                        || (!stateInst2.equals(null) && !stateInst2.equals(""))
                        || (!zipInst2.equals(null) && !zipInst2.equals(""))
                        || (!countryInst2.equals(null) && !countryInst2.equals(""))) {

                    if (!streetAddressInst2.equals(null) && !streetAddressInst2.equals("")) {
                        if (streetAddressInst2.length()<=50) {
                            addAddress = true;
                        } else {
                            throw new DataTooLongException("Street Address");
                        }
                    } else {
                        streetAddressInst2 = null;
                    }

                    if (!cityInst2.equals(null) && !cityInst2.equals("")) {
                        if (cityInst2.length()<=50) {
                            addAddress = true;
                        } else {
                            throw new DataTooLongException("City");
                        }
                    } else {
                        cityInst2 = null;
                    }

                    if (!stateInst2.equals(null) && !stateInst2.equals("")) {
                        if (stateInst2.length()<=50) {
                            addAddress = true;
                        } else {
                            throw new DataTooLongException("State");
                        }
                    } else {
                        stateInst2 = null;
                    }

                    if (!zipInst2.equals(null) && !zipInst2.equals("")) {
                        if (zipInst2.length()<=15) {
                            addAddress = true;
                        } else {
                            throw new DataTooLongException("Zip");
                        }
                    } else {
                        zipInst2 = null;
                    }

                    if (!countryInst2.equals(null) && !countryInst2.equals("")) {
                        if (countryInst2.length()<=40) {
                            addAddress = true;
                        } else {
                            throw new DataTooLongException("Country");
                        }
                    } else {
                        countryInst2 = null;
                    }

                }
                removeAddress(contactId);
                if (addAddress) {
                    addAddress(contactId,streetAddressInst2,cityInst2,stateInst2,zipInst2,countryInst2);
                }
            }

            int n = preparedStatement.executeUpdate();
            refreshScreen();
            app.setStatus("Contacts updated: "+n);

        } catch (NullFieldException ex) {
            app.setStatus(ex.getMessage()+" can't be left blank.");
        } catch (DataTooLongException ex) {
            app.setStatus("Data too long for field "+ex.getMessage()+".");
        } catch (DuplicateDataException ex) {
            app.setStatus(ex.getMessage()+" already exists in the database for some contact!");
        } catch (InvalidDateException ex) {
            app.setStatus("Entered " + ex.getMessage() + " is not between 1850-01-01 and today's date!");
        }  catch (ParseException ex) {
            app.setStatus(ex.getMessage());
        } catch (Exception ex) {
            errorResponse(ex);
        }

    }

    /**************************************************************************
     * Deletes the phone number from the database.
     **************************************************************************/
    void deletePhone (String phoneNumber) {
        String sql = "DELETE FROM PHONENUMBER "+
                "WHERE PhoneNumber= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phoneNumber);
            int deleteCount = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Deletes the email from the database.
     **************************************************************************/
    void deleteEmail (String emailAddress) {
        String sql = "DELETE FROM EMAILADDRESS "+
                "WHERE EmailAddress= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, emailAddress);
            int deleteCount = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Updates phone number for the Contact
     **************************************************************************/
    void updatePhone (int contactId, String phoneInst2) {
        String sql = "UPDATE PHONENUMBER SET " +
                "PhoneNumber= ? " +
                "WHERE ContactId= ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phoneInst2);
            preparedStatement.setInt(2, contactId);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Updates email address for the Contact
     **************************************************************************/
    void updateEmail (int contactId, String emailInst2) {
        String sql = "UPDATE EMAILADDRESS SET " +
                "EmailAddress= ? " +
                "WHERE ContactId= ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, emailInst2);
            preparedStatement.setInt(2, contactId);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }

    /**************************************************************************
     * Deletes the address for the contact.
     **************************************************************************/
    void removeAddress (int contactId) {
        String sql = "DELETE FROM ADDRESS " +
                "WHERE ContactID= ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            errorResponse(ex);
        }
    }
}
