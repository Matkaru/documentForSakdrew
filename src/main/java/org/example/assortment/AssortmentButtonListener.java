package org.example.assortment;
import org.example.jsonSave.SaveAssortment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static org.example.assortment.Assortment.*;

public class AssortmentButtonListener extends Component implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton sourceButton) {
            if (sourceButton.getText().equals("Dodaj")) {
                try {
                    onAddButtonClicked();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (sourceButton.getText().equals("Edytuj")) {
                try {
                    onEditButtonClicked(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (sourceButton.getText().equals("Usuń")) {
                onDeleteButtonClicked();

            }
        }
    }

    private void onAddButtonClicked() throws IOException {
        AssortmentMethod.setCodeAndNameList();
        List<Long> codeList = AssortmentMethod.getCodeList();
        List<String> nameList = AssortmentMethod.getNameList();

        String code = newItemCodeField.getText();
        String name = newItemNameField.getText();
        String price = newItemPriceField.getText().replace(",", ".");
        String unit = Objects.requireNonNull(newItemUnitComboBox.getSelectedItem()).toString();
        String vat = Objects.requireNonNull(newItemVatComboBox.getSelectedItem()).toString();


        if (codeList.contains(Long.parseLong(code))) {
            JOptionPane.showMessageDialog(AssortmentButtonListener.this, "Podany kod już istnieje w bazie!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nameList.contains(name)) {
            JOptionPane.showMessageDialog(AssortmentButtonListener.this, "Podana nazwa już istnieje w bazie!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Checking if all fields are filled in
        if (code.isEmpty() || name.isEmpty() || price.isEmpty() || vat.isEmpty()) {
            JOptionPane.showMessageDialog(AssortmentButtonListener.this, "Wypełnij wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean codeValid = code.matches("[0-9]{4,}");
        boolean priceValid = price.matches("[0-9]+(\\.[0-9]{1,2})?");

        if (!codeValid || !priceValid) {
            JOptionPane.showMessageDialog(AssortmentButtonListener.this, " Kod musi zawierać minimum 4 cyfry od 0 do 9 ! \n Cena musi zawierać cyfry i dwa miejsca po przecinku", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        long code2 = Long.parseLong(code);
        double price2 = Double.parseDouble(price);
        long vat2 = Long.parseLong(vat);

        //Adding a new product to the table
        DefaultTableModel model1 = (DefaultTableModel) assortmentTable.getModel();
        model1.addRow(new Object[]{code2, name, price2, unit, vat2});

        // Clearing form fields after adding an entry
        newItemCodeField.setText("");
        newItemNameField.setText("");
        newItemPriceField.setText("");
        newItemUnitComboBox.setSelectedIndex(0);
        newItemVatComboBox.setSelectedIndex(0);

        JOptionPane.showMessageDialog(AssortmentButtonListener.this, "Dodano nowy wpis do tabeli.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        Assortment.sortAssortmentTableByCode();
        SaveAssortment.saveAssortment();

    }
    private void onDeleteButtonClicked () {
        int selectedRow;

        selectedRow = assortmentTable.getSelectedRow(); // Fetch the selected row
        if (selectedRow == -1) {
            //Checks if a row has been selected
            JOptionPane.showMessageDialog(AssortmentButtonListener.this, "Zaznacz wiersz do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Displays a dialog asking you to confirm the deletion
        int confirm = JOptionPane.showConfirmDialog(AssortmentButtonListener.this, "Czy na pewno chcesz usunąć zaznaczony wpis?", "Potwierdzenie usunięcia", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            //Delete the selected row from the table
            DefaultTableModel model = (DefaultTableModel) assortmentTable.getModel();
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(AssortmentButtonListener.this, "Wpis został usunięty.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            Assortment.sortAssortmentTableByCode();
            SaveAssortment.saveAssortment();
        }
    }

    void onEditButtonClicked(ActionEvent e) throws IOException {
        int selectedRow = assortmentTable.getSelectedRow(); // Fetch the selected row
        if (selectedRow == -1) {
            // Checks if a row has been selected
            JOptionPane.showMessageDialog(AssortmentButtonListener.this, "Zaznacz wiersz do edycji.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Retrieve data from the selected row
        long id = (long) assortmentTable.getValueAt(selectedRow, 0);
        String name = (String) assortmentTable.getValueAt(selectedRow, 1);
        double price = (double) assortmentTable.getValueAt(selectedRow, 2);

        EditDialog editDialog = new EditDialog(AssortmentButtonListener.this, id, name, price);
        editDialog.setVisible(true);


        Assortment assortment = (Assortment) SwingUtilities.getWindowAncestor((Component)e.getSource());
        assortment.setEnabled(false);


        editDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {

                assortment.setEnabled(true);
                if (editDialog.isConfirmed()) {
                    List<String> nameList = new ArrayList<>(AssortmentMethod.getNameList());

                    HashSet<String> set = new HashSet<>(nameList);
                    nameList.clear();
                    nameList.addAll(set);
                    nameList.remove(name);

                    //If the user confirms the changes, we fetch the modified data from the dialog
                    long editId = editDialog.getId();
                    String editedName = editDialog.getName();
                    String editedCategory = editDialog.getUnit();
                    double editedPrice = editDialog.getPrice();
                    long editedVat = Long.parseLong(editDialog.getVat());

                    if (nameList.contains(editedName)) {
                        JOptionPane.showMessageDialog(AssortmentButtonListener.this, "Podana nazwa już istnieje w bazie!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //Updating data in the table
                        DefaultTableModel model12 = (DefaultTableModel) assortmentTable.getModel();
                        model12.setValueAt(editId, selectedRow, 0);
                        model12.setValueAt(editedName, selectedRow, 1);
                        model12.setValueAt(editedPrice, selectedRow, 2);
                        model12.setValueAt(editedCategory, selectedRow, 3);
                        model12.setValueAt(editedVat, selectedRow, 4);

                        JOptionPane.showMessageDialog(AssortmentButtonListener.this, "Dane zostały zaktualizowane.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                        nameList.clear();
                        Assortment.sortAssortmentTableByCode();
                        SaveAssortment.saveAssortment();


                    }
                }

            }
            @Override
            public void windowClosing(WindowEvent e) {
                assortment.setEnabled(true);
            }
        });

    }

}


