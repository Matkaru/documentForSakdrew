package org.example.assortment;
import org.example.enums.Unit;
import org.example.enums.Vat;
import org.example.appConfig.WindowIconSetter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class EditDialog extends JDialog {

    private final JTextField idFiled;
    private final JTextField nameField;
    private final JComboBox<Unit> unitField;
    private final JTextField priceField;
    private final JComboBox<Long> vatFiled;
    private boolean confirmed;

    public boolean isConfirmed() {
        return confirmed;
    }

    public EditDialog(AssortmentButtonListener parent, long id, String name, double price) {
        super();
        //Set the dialog title and modal mode
        setLayout(new GridLayout(6, 4));
        WindowIconSetter.setWindowIcon(this,"src/main/resources/dfsc syst.png");

        AssortmentMethod.setCodeAndNameList();

        long value0 = Vat.ZERO.getValue();
        long value5 = Vat.FIVE.getValue();
        long value8 = Vat.EIGHT.getValue();
        long value23 = Vat.TWO_THREE.getValue();

        Long[] values = {value0, value5, value8, value23};

        //Adding text boxes to the dialog box
        idFiled = new JTextField(Long.toString(id));
        idFiled.setEditable(false);
        nameField = new JTextField(name);
        unitField = new JComboBox<>(Unit.values());
        priceField = new JTextField(Double.toString(price));
        vatFiled = new JComboBox<>(values);

        add(new JLabel("Kod"));
        add(idFiled);
        add(new JLabel("Nazwa:"));
        add(nameField);
        add(new JLabel("Cena:"));
        add(priceField);
        add(new JLabel("Jednostka:"));
        add(unitField);
        add(new JLabel("VAT"));
        add(vatFiled);

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

    public String getName() {
        return nameField.getText();
    }

    public String getUnit() {
        return Objects.requireNonNull(unitField.getSelectedItem()).toString();
    }

    public double getPrice() {
        try {
            return Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getVat() {
        return Objects.requireNonNull(vatFiled.getSelectedItem()).toString();
    }


    public long getId() {
        return Long.parseLong(idFiled.getText());
    }
}



