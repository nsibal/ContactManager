package com.sibalnirbhay;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**************************************************************************
 * App class has been coded to create the Swing user interface for the
 * program.
 **************************************************************************/

public class App extends JFrame {

    private JPanel panelMain;

    private JTextField firstName;
    private JTextField minIt;
    private JTextField lastName;

    /*
     * Sex is a drop down with two options, i.e M and F,
     * and a default selected item, which is an empty
     * string, and this empty string won't be considered
     * if the user keeps it unchanged while adding a new
     * contact or updating an existing one.
     */
    private JComboBox sex;

    private JTextField dateOfBirth;
    private JTextField phoneNumber;
    private JTextField emailAddress;
    private JTextField streetAddress;
    private JTextField city;
    private JTextField stateArea;
    private JTextField zip;
    private JTextField country;

    /*
     * Add Contact button adds a contact to the database
     * if all the inputs are valid.
     */
    private JButton addContactButton;

    /*
     * Update Contact button update a contact in the database
     * if all the inputs are valid.
     */
    private JButton updateButton;

    /*
     * Refresh button refreshes the screen.
     */
    private JButton refreshButton;
    private JScrollPane scrollPane;

    /*
     * Output Table shows the entire database.
     */
    private JTable outputTable;

    /*
     * Status bar at the bottom of the panel to display the
     * status and sometimes the outputs of few function calls.
     */
    private JLabel status;

    public String getFirstName() {
        return firstName.getText();
    }

    public String getMinIt() {
        return minIt.getText();
    }

    public String getLastName() {
        return lastName.getText();
    }

    public String getSex() {
        return sex.getSelectedItem().toString();
    }

    public String getDateOfBirth() {
        return dateOfBirth.getText();
    }

    public String getPhoneNumber() {
        return phoneNumber.getText();
    }

    public String getEmailAddress() {
        return emailAddress.getText();
    }

    public String getStreetAddress() {
        return streetAddress.getText();
    }

    public String getCity() {
        return city.getText();
    }

    public String getStateArea() {
        return stateArea.getText();
    }

    public String getZip() {
        return zip.getText();
    }

    public String getCountry() {
        return country.getText();
    }

    public JButton getAddContactButton() {
        return addContactButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JTable getOutputTable() {
        return outputTable;
    }

    public void setOutputTableModel(DefaultTableModel model) {
        outputTable.setModel(model);
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }


    /**************************************************************************
     * This function enables or disables the items in the Panel based on the
     * input, which is a boolean.
     * All the items are generally disabled if the database can't be reached.
     **************************************************************************/
    void setEnabledItems(boolean b) {
        firstName.setEnabled(b);
        minIt.setEnabled(b);
        lastName.setEnabled(b);
        emailAddress.setEnabled(b);
        sex.setEnabled(b);
        dateOfBirth.setEnabled(b);
        phoneNumber.setEnabled(b);
        phoneNumber.setEnabled(b);
        streetAddress.setEnabled(b);
        outputTable.setEnabled(b);

        /*
         * If the input b is false, the table is cleared.
         */
        if (!b) {
            outputTable.setModel(new DefaultTableModel());
        }
        outputTable.setRowSelectionAllowed(b);
        scrollPane.setEnabled(b);
        city.setEnabled(b);
        country.setEnabled(b);
        zip.setEnabled(b);
        stateArea.setEnabled(b);
        scrollPane.setEnabled(b);
        addContactButton.setEnabled(b);
        updateButton.setEnabled(!b);
    }

    /**************************************************************************
     * Clears all the Fields to their default position, i.e empty.
     * Also sets the staus to "Online".
     **************************************************************************/
    void refreshFields() {
        firstName.setText("");
        minIt.setText("");
        lastName.setText("");
        sex.setSelectedIndex(0);
        dateOfBirth.setText("");
        emailAddress.setText("");
        phoneNumber.setText("");
        streetAddress.setText("");
        city.setText("");
        stateArea.setText("");
        zip.setText("");
        country.setText("");
        status.setText("Online");

    }

    /**************************************************************************
     * When a row is selected, and the update option is chosen, the contents of
     * the selected row are fetched to their corresponding fields for the
     * purpose of updation. The update button is enabled, the add button is
     * disabled, and the table is cleared until either the screen is refreshed
     * or the contact is updated.
     **************************************************************************/
    void setFieldsFromRow(int rowNo) {
        addContactButton.setEnabled(false);
        updateButton.setEnabled(true);
        status.setText("Change the fields to update");

        String firstName = (String) this.outputTable.getModel().getValueAt(rowNo, 1);
        this.firstName.setText(firstName == null ? "" : firstName);

        String minIt = (String) this.outputTable.getModel().getValueAt(rowNo, 2);
        this.minIt.setText(minIt == null ? "" : minIt);

        String lastName = (String) this.outputTable.getModel().getValueAt(rowNo, 3);
        this.lastName.setText(lastName == null ? "" : lastName);

        String sex = (String) this.outputTable.getModel().getValueAt(rowNo, 4);
        this.sex.setSelectedItem(sex == null ? "" : sex);

        /*
         * If there is no date of birth for the selected contact, the type would be null,
         * else the type would be java.sql.Date.
         */
        Object type = this.outputTable.getModel().getValueAt(rowNo, 5);
        if (type == null) {
            this.dateOfBirth.setText("");
        } else {
            String dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").format((Date) type);
            this.dateOfBirth.setText(dateOfBirth);
        }

        String phoneNumber = (String) this.outputTable.getModel().getValueAt(rowNo, 6);
        this.phoneNumber.setText(phoneNumber == null ? "" : phoneNumber);

        String emailAddress = (String) this.outputTable.getModel().getValueAt(rowNo, 7);
        this.emailAddress.setText(emailAddress == null ? "" : emailAddress);

        String streetAddress = (String) this.outputTable.getModel().getValueAt(rowNo, 8);
        this.streetAddress.setText(streetAddress == null ? "" : streetAddress);

        String city = (String) this.outputTable.getModel().getValueAt(rowNo, 9);
        this.city.setText(city == null ? "" : city);

        String stateArea = (String) this.outputTable.getModel().getValueAt(rowNo, 10);
        this.stateArea.setText(stateArea == null ? "" : stateArea);

        String zip = (String) this.outputTable.getModel().getValueAt(rowNo, 11);
        this.zip.setText(zip == null ? "" : zip);

        String country = (String) this.outputTable.getModel().getValueAt(rowNo, 12);
        this.country.setText(country == null ? "" : country);

        outputTable.setModel(new DefaultTableModel());
    }

    /**************************************************************************
     * This function gives the option to either update the selected contact
     * or delete it. This function is invoked upon double click only.
     **************************************************************************/
    int chooseOptionFromDialog() {
        String[] options = {"Delete Contact", "Update Contact"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Delete or Update?",
                "Choose an option",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null
        );
        return choice;
    }


    App() {
        add(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Contact Manager");
        setSize(1600, 800);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 10, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), null));
        final JLabel label1 = new JLabel();
        label1.setText("First Name");
        panelMain.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firstName = new JTextField();
        panelMain.add(firstName, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(174, 30), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("M.I.");
        panelMain.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        minIt = new JTextField();
        panelMain.add(minIt, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Last\nName");
        panelMain.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lastName = new JTextField();
        panelMain.add(lastName, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Phone Number");
        panelMain.add(label4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        phoneNumber = new JTextField();
        panelMain.add(phoneNumber, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(174, 30), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Sex");
        panelMain.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sex = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("");
        defaultComboBoxModel1.addElement("M");
        defaultComboBoxModel1.addElement("F");
        sex.setModel(defaultComboBoxModel1);
        panelMain.add(sex, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(43, 30), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("DOB");
        panelMain.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dateOfBirth = new JTextField();
        dateOfBirth.setText("");
        panelMain.add(dateOfBirth, new com.intellij.uiDesigner.core.GridConstraints(0, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        emailAddress = new JTextField();
        panelMain.add(emailAddress, new com.intellij.uiDesigner.core.GridConstraints(1, 5, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Street Address");
        panelMain.add(label7, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        streetAddress = new JTextField();
        panelMain.add(streetAddress, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        updateButton = new JButton();
        updateButton.setEnabled(false);
        updateButton.setText("Update");
        panelMain.add(updateButton, new com.intellij.uiDesigner.core.GridConstraints(4, 4, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        city = new JTextField();
        panelMain.add(city, new com.intellij.uiDesigner.core.GridConstraints(2, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("City");
        panelMain.add(label8, new com.intellij.uiDesigner.core.GridConstraints(2, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("State");
        panelMain.add(label9, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("ZIP");
        panelMain.add(label10, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Country");
        panelMain.add(label11, new com.intellij.uiDesigner.core.GridConstraints(3, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        country = new JTextField();
        panelMain.add(country, new com.intellij.uiDesigner.core.GridConstraints(3, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        zip = new JTextField();
        panelMain.add(zip, new com.intellij.uiDesigner.core.GridConstraints(3, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        stateArea = new JTextField();
        panelMain.add(stateArea, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        scrollPane = new JScrollPane();
        panelMain.add(scrollPane, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 10, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), "CONTACTS", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, this.$$$getFont$$$(null, -1, -1, scrollPane.getFont())));
        outputTable = new JTable();
        Font outputTableFont = UIManager.getFont("Table.font");
        if (outputTableFont != null) outputTable.setFont(outputTableFont);
        outputTable.setGridColor(new Color(-16777216));
        outputTable.setInheritsPopupMenu(false);
        outputTable.setRowHeight(20);
        outputTable.setSurrendersFocusOnKeystroke(true);
        scrollPane.setViewportView(outputTable);
        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        panelMain.add(refreshButton, new com.intellij.uiDesigner.core.GridConstraints(4, 8, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addContactButton = new JButton();
        addContactButton.setText("Add Contact");
        panelMain.add(addContactButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        status = new JLabel();
        status.setEnabled(true);
        status.setName("");
        status.setText("Online");
        panelMain.add(status, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 10, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Email Address");
        panelMain.add(label12, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    //    private void createUIComponents() {
//        // TODO: place custom component creation code here
//    }
}
