/******************************************************************************
 * JAVA Application to Operate Contact Manager Database.
 *
 * This program keeps a record of the contacts. It can add, update and delete
 * contacts. It is designed to work for contacts with single line addresses
 * only.
 *
 * The user gets option to refresh the screen as well. That is, if the database
 * is down, the user doesn't have to close the application and wait for the
 * database to get started. The user can simply click the refresh button to
 * get the updated database as soon as it is online.
 *
 * The user will have a table. They can double click individual rows to select
 * the contact from the database and choose either to delete the selected
 * contact or update it.
 *
 * If the user selects to update it, the Update Contact button enables and
 * Add Contact button disables. The user refresh anytime to go to view the
 * entire table, and clear the input fields.
 *
 * The program will create a new contact on the press of Add Contact button.
 *
 * The format of date of birth should be yyyy-MM-dd.
 *
 * If the user keeps the first option from the drop down for Sex, i.e empty
 * string, the same field won't be added in the database.
 *
 *
 * Written by Nirbhay Sibal (nxs180002) at The University of Texas at Dallas
 * starting October 19, 2018.
 ******************************************************************************/

package com.sibalnirbhay;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {

    /**************************************************************************
     * Main function
     **************************************************************************/

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, ParseException, SQLException {

        ContactManager cm = new ContactManager();


    }
}