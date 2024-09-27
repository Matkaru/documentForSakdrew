package org.example.contractor;

import org.example.startWindow.StartWindow;
import org.example.startWindow.StartWindowButtonListener;
import org.example.appConfig.WindowIconSetter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class Contractor extends JFrame {

    public static JTextField newItemCompanyIdField;
    public static JTextField newItemCompanyNameField;
    public static JTextField newItemNipField;
    public static JTextField newItemRegonField;
    public static JTextField newItemAddressField;
    public static JTextField newItemEmailField;
    public static JTextField newItemPhoneNumberField;
    public static JTable contractorTable;

    public static void sortContractorTableByCode() {

        DefaultTableModel model = (DefaultTableModel) contractorTable.getModel();
        int rowCount = model.getRowCount();


        List<Vector<Object>> data = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            Vector<Object> row = new Vector<>();
            for (int j = 0; j < model.getColumnCount(); j++) {
                row.add(model.getValueAt(i, j));
            }
            data.add(row);
        }
        data.sort((row1, row2) -> {
            Long code1 = (Long) row1.get(0);
            Long code2 = (Long) row2.get(0);
            return Long.compare(code1, code2);
        });

        model.setRowCount(0); // The table is cleared

        //adding the newly sorted values to the table
        for (Vector<Object> row : data) {
            model.addRow(row);
        }
    }


    public Contractor() throws IOException {
        super("Kontrahenci");
        int widthScreen = Toolkit.getDefaultToolkit().getScreenSize().width;
        int heightScreen = Toolkit.getDefaultToolkit().getScreenSize().height;
        int frameWidth = (widthScreen / 2);
        int frameHeight = (heightScreen / 2);
        this.setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);

        WindowIconSetter.setWindowIcon(this, "src/main/resources/dfsc syst.png");
        String[] columnNames = {"Id", "Nazwa firmy", "NIP", "REGON", "Adres", "Email", "Nr telefonu"};
        TableModel model = new DefaultTableModel(columnNames, 0);

        contractorTable = new JTable(model);
        contractorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contractorTable.getTableHeader().setReorderingAllowed(false);

        ContractorsMethod.loadContractorsFromFile();
        sortContractorTableByCode();

        JScrollPane scrollPane = new JScrollPane(contractorTable);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);


        newItemCompanyIdField = new JTextField();
        newItemCompanyNameField = new JTextField();
        newItemNipField = new JTextField();
        newItemRegonField = new JTextField();
        newItemAddressField = new JTextField();
        newItemEmailField = new JTextField();
        newItemPhoneNumberField = new JTextField();

        JButton addButton = new JButton("Dodaj");
        JButton editButton = new JButton("Edytuj");
        JButton deleteButton = new JButton("Usuń");

        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        formPanel.add(new JLabel("  Id: "));
        formPanel.add(newItemCompanyIdField);
        formPanel.add(new JLabel("  Nazwa firmy: "));
        formPanel.add(newItemCompanyNameField);
        formPanel.add(new JLabel("  NIP: "));
        formPanel.add(newItemNipField);
        formPanel.add(new JLabel("  REGON: "));
        formPanel.add(newItemRegonField);
        formPanel.add(new JLabel("  Adres: "));
        formPanel.add(newItemAddressField);
        formPanel.add(new JLabel("  Email: "));
        formPanel.add(newItemEmailField);
        formPanel.add(new JLabel("  Nr telefonu: "));
        formPanel.add(newItemPhoneNumberField);

        // Adding buttons to the window
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
         @Override
          public void windowClosing(WindowEvent e) {
             StartWindowButtonListener buttonListener = new StartWindowButtonListener();
             StartWindow startWindow = new StartWindow(buttonListener);
             startWindow.setVisible(true);
             dispose();
          }
        });

        ContractorButtonListener contractorButtonListener = new ContractorButtonListener();
        addButton.addActionListener(contractorButtonListener);
        editButton.addActionListener(contractorButtonListener);
        deleteButton.addActionListener(contractorButtonListener);
    }


}
