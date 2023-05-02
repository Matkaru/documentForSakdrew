package org.example.assortment;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.StartWindow;
import org.example.assortment.enums.Unit;
import org.example.assortment.enums.Vat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Assortment extends JFrame {

    private static Assortment instance;

     public static DefaultTableModel DefaultTableModel = new DefaultTableModel();

    private final JTextField newItemCodeField;
    private final JTextField newItemNameField;
    private final JTextField newItemPriceField;
    private final JComboBox<Unit> newItemUnitComboBox;
    private final JComboBox<Long> newItemVatComboBox;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private final String fileName = "src/main/resources/assortment_data.json";
    public static JTable assortmentTable;

    public Assortment() throws IOException {

        setTitle("Asortyment");
        setSize(700, 800);


        String[] columnNames = {"Kod", "Nazwa", "Cena", "Jednostka", "VAT"};
        TableModel model = new DefaultTableModel(columnNames, 0);
        assortmentTable = new JTable(model);
        assortmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assortmentTable.getTableHeader().setReorderingAllowed(false);

        AssortmentMethod.loadAssortmentFromFile();

        JScrollPane scrollPane = new JScrollPane(assortmentTable);
        // Utworzenie listy asortymentu

        Long value0 = Vat.ZERO.getValue();
        Long value5 = Vat.FIVE.getValue();
        Long value8 = Vat.EIGHT.getValue();
        Long value23 = Vat.TWO_THREE.getValue();

        Long[] values = {value0, value5, value8, value23};

//        values.add(Vat.ZERO.getValue());
//        values.add(Vat.FIVE.getValue());
//        values.add(Vat.EIGHT.getValue());
//        values.add(Vat.TWO_THREE.getValue());




        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);


        newItemCodeField = new JTextField();
        newItemNameField = new JTextField();
        newItemPriceField = new JTextField();
        newItemUnitComboBox = new JComboBox<>(Unit.values());
        newItemVatComboBox = new JComboBox<>(values);


        // Utworzenie przycisków
        addButton = new JButton("Dodaj");
        editButton = new JButton("Edytuj");
        deleteButton = new JButton("Usuń");

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Kod: "));
        formPanel.add(newItemCodeField);
        formPanel.add(new JLabel("Nazwa: "));
        formPanel.add(newItemNameField);
        formPanel.add(new JLabel("Cena: "));
        formPanel.add(newItemPriceField);
        formPanel.add(new JLabel("Jednostka miary: "));
        formPanel.add(newItemUnitComboBox);
        formPanel.add(new JLabel("VAT"));
        formPanel.add(newItemVatComboBox);


        // Dodanie przycisków do okienka
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);




        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.SOUTH);


// poniżej dodajemy akcje na przyciskach
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TableModel model = assortmentTable.getModel();

                List<Product> productList = new ArrayList<>();
                for (int i = 0; i < model.getRowCount(); i++) {

                    Product product = new Product();

                    long itemCodeString = (long) model.getValueAt(i, 0);
                    product.setItemCode(Long.parseLong(String.valueOf(itemCodeString)));
                    product.setItemName((String) model.getValueAt(i, 1));
                    double itemPriceString = (double) model.getValueAt(i, 2);
                    product.setItemPrice(Double.parseDouble(String.valueOf(itemPriceString)));
                    product.setItemUnit((String) model.getValueAt(i, 3));
                    long itemVatString = (long) model.getValueAt(i, 4);
                    product.setItemVat(Long.parseLong(String.valueOf(itemVatString)));

                    productList.add(product);
                }
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    // Utworzenie obiektu JsonGenerator
                    JsonFactory jsonFactory = objectMapper.getFactory();
                    JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter("src/main/resources/assortment_data.json"));

                    // Rozpoczęcie zapisu do pliku JSON
                    jsonGenerator.writeStartArray();
                    for (Product product : productList) {
                        // Zapisanie pojedynczego obiektu jako JSON
                        jsonGenerator.writeStartObject();
                        jsonGenerator.writeNumberField("Kod", product.getId());
                        jsonGenerator.writeStringField("Nazwa", product.getName());
                        jsonGenerator.writeNumberField("Cena", product.getPrice());
                        jsonGenerator.writeStringField("Jednostka", product.getQuantity());
                        jsonGenerator.writeNumberField("VAT", product.getVat());

                        jsonGenerator.writeEndObject();
                    }
                    jsonGenerator.writeEndArray();

                    // Zakończenie zapisu
                    jsonGenerator.flush();
                    jsonGenerator.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


                StartWindow startWindow = new StartWindow();
                startWindow.setVisible(true);
                dispose();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = newItemCodeField.getText();
                String name = newItemNameField.getText();
                String price = newItemPriceField.getText();
                String unit = Objects.requireNonNull(newItemUnitComboBox.getSelectedItem()).toString();
                String vat = Objects.requireNonNull(newItemVatComboBox.getSelectedItem()).toString();

                // Sprawdzenie, czy wszystkie pola są wypełnione
                if (code.isEmpty() || name.isEmpty() || price.isEmpty() || vat.isEmpty()) {
                    JOptionPane.showMessageDialog(Assortment.this, "Wypełnij wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                long code2 = Long.parseLong(code);
                double price2 = Double.parseDouble(price);
                long vat2 = Long.parseLong(vat);

                // Dodanie nowego produktu do tabeli
                DefaultTableModel model = (DefaultTableModel) assortmentTable.getModel();
                model.addRow(new Object[]{code2, name, price2, unit, vat2});

                // Wyczyszczenie pól formularza po dodaniu wpisu
                newItemCodeField.setText("");
                newItemNameField.setText("");
                newItemPriceField.setText("");
                newItemUnitComboBox.setSelectedIndex(0);
                newItemVatComboBox.setSelectedIndex(0);

                JOptionPane.showMessageDialog(Assortment.this, "Dodano nowy wpis do tabeli.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = assortmentTable.getSelectedRow(); // Pobranie zaznaczonego wiersza
                if (selectedRow == -1) {
                    // Sprawdzenie, czy wiersz został zaznaczony
                    JOptionPane.showMessageDialog(Assortment.this, "Zaznacz wiersz do edycji.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Pobranie danych z zaznaczonego wiersza
                long id = (long) assortmentTable.getValueAt(selectedRow, 0);
                String name = (String) assortmentTable.getValueAt(selectedRow, 1);
                double price = (double) assortmentTable.getValueAt(selectedRow, 2);
                String category = (String) assortmentTable.getValueAt(selectedRow, 3);
                long vat = (long) (assortmentTable.getValueAt(selectedRow, 4));
                // Wyświetlenie okna dialogowego z formularzem edycji danych
                EditDialog editDialog = new EditDialog(Assortment.this, id, name, category, price, vat);
                // Zakładamy, że mamy zdefiniowany własny dialog o nazwie EditDialog
                editDialog.setVisible(true);

                if (editDialog.isConfirmed()) {
                    // Jeśli użytkownik potwierdzi zmiany, pobieramy zmodyfikowane dane z dialogu
                    long editId = editDialog.getId();
                    String editedName = editDialog.getName();
                    String editedCategory = editDialog.getUnit();
                    double editedPrice = editDialog.getPrice();
                    long editedVat = Long.parseLong(editDialog.getVat());

                    // Aktualizacja danych w tabeli
                    DefaultTableModel model = (DefaultTableModel) assortmentTable.getModel();
                    model.setValueAt(editId, selectedRow, 0);
                    model.setValueAt(editedName, selectedRow, 1);
                    model.setValueAt(editedPrice, selectedRow, 2);
                    model.setValueAt(editedCategory, selectedRow, 3);
                    model.setValueAt(editedVat, selectedRow, 4);

                    JOptionPane.showMessageDialog(Assortment.this, "Dane zostały zaktualizowane.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = assortmentTable.getSelectedRow(); // Pobranie zaznaczonego wiersza
                if (selectedRow == -1) {
                    // Sprawdzenie, czy wiersz został zaznaczony
                    JOptionPane.showMessageDialog(Assortment.this, "Zaznacz wiersz do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Wyświetlenie okna dialogowego z pytaniem o potwierdzenie usunięcia
                int confirm = JOptionPane.showConfirmDialog(Assortment.this, "Czy na pewno chcesz usunąć zaznaczony wpis?", "Potwierdzenie usunięcia", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Usunięcie zaznaczonego wiersza z tabeli
                    DefaultTableModel model = (DefaultTableModel) assortmentTable.getModel();
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(Assortment.this, "Wpis został usunięty.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }
 }
