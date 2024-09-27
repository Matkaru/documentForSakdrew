package org.example.contractor;
import org.example.appConfig.WindowIconSetter;

import javax.swing.*;
import java.awt.*;


public class EditDialogFromContractor extends JDialog {
    private final JTextField idFiled;
    private final JTextField companyNameField;
    private final JTextField nipField;
    private final JTextField regonField;
    private final JTextField addressField;
    private final JTextField emailField;
    private final JTextField phoneNumberField;

    private boolean confirmed;

    public boolean isConfirmed() {
        return confirmed;
    }

    public EditDialogFromContractor (ContractorButtonListener parent, long id, String companyName, long nip, long regon, String address, String email, long phoneNumber) {
        super();
        //Set the dialog title and modal mode
        setLayout(new GridLayout(7, 4));
        WindowIconSetter.setWindowIcon(this,"src/main/resources/dfsc syst.png");
     //   ContractorsMethod.setIdAndCompanyNameList();


        //Adding text boxes to the dialog box
        idFiled = new JTextField(Long.toString(id));
        idFiled.setEditable(false);
        companyNameField = new JTextField(companyName);
        nipField = new JTextField(Long.toString(nip));
        nipField.setEditable(false);
        regonField = new JTextField(Long.toString(regon));
        regonField.setEditable(false);
        addressField = new JTextField(address);
        emailField = new JTextField(email);
        phoneNumberField = new JTextField(Long.toString(phoneNumber));
        phoneNumberField.setEditable(false);

        add(new JLabel("Id"));
        add(idFiled);
        add(new JLabel("Nazwa firmy:"));
        add(companyNameField);
        add(new JLabel("NIP:"));
        add(nipField);
        add(new JLabel("REGON:"));
        add(regonField);
        add(new JLabel("Adres"));
        add(addressField);
        add(new JLabel("Email"));
        add(emailField);
        add(new JLabel("Nr telefonu"));
        add(phoneNumberField);

        // Adding buttons to the dialog box
        JButton confirmButton = new JButton("Potwierdź");
        JButton cancelButton = new JButton("Anuluj");

        confirmButton.addActionListener(e -> {
            confirmed = true;
            dispose(); //Closing the dialog box
        });
        cancelButton.addActionListener(e -> {

            confirmed = false;
            dispose(); //Closing the dialog box
        });
        add(confirmButton);
        add(cancelButton);

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }

    public String getCompanyName() {
        return companyNameField.getText();
    }

    public long getNip() {
        return Long.parseLong(nipField.getText());
    }

    public long getRegon() {
        return Long.parseLong(regonField.getText());
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public long getPhoneNumber() {
        return Long.parseLong(phoneNumberField.getText());
    }

    public long getId() {
        return Long.parseLong(idFiled.getText());
    }
}

