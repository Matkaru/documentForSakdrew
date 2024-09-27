package org.example.assortment;
import org.example.startWindow.StartWindow;
import org.example.enums.Unit;
import org.example.enums.Vat;
import org.example.startWindow.StartWindowButtonListener;
import org.example.appConfig.WindowIconSetter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class Assortment extends JFrame {


    public static JTextField newItemCodeField;
    public static JTextField newItemNameField;
    public static JTextField newItemPriceField;
    public static JComboBox<Unit> newItemUnitComboBox;
    public static JComboBox<Long> newItemVatComboBox;
    public static JTable assortmentTable;


    public static void sortAssortmentTableByCode() {

        DefaultTableModel model = (DefaultTableModel) assortmentTable.getModel();
        int rowCount = model.getRowCount();

        List<List<Object>> data = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            List<Object> row = new ArrayList<>();
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
        for (List<Object> row : data) {
            model.addRow(row.toArray());
        }
    }

    public Assortment() throws IOException {
        super("Asortyment");
        int widthScreen = Toolkit.getDefaultToolkit().getScreenSize().width;
        int heightScreen = Toolkit.getDefaultToolkit().getScreenSize().height;
        int frameWidth = (widthScreen/2);
        int frameHeight = (heightScreen/2);
        this.setSize(frameWidth,frameHeight);
        setLocationRelativeTo(null);

        WindowIconSetter.setWindowIcon(this,"src/main/resources/dfsc syst.png");
        setLocationRelativeTo(null);


        String[] columnNames = {"Kod", "Nazwa", "Cena", "Jednostka", "VAT"};
        TableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing of all cells
            }
        };
        assortmentTable = new JTable(model);
        assortmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assortmentTable.getTableHeader().setReorderingAllowed(false);


        AssortmentMethod.loadAssortmentFromFile();

    sortAssortmentTableByCode();

    JScrollPane scrollPane = new JScrollPane(assortmentTable);


    //Create an assortment list

    long value0 = Vat.ZERO.getValue();
    long value5 = Vat.FIVE.getValue();
    long value8 = Vat.EIGHT.getValue();
    long value23 = Vat.TWO_THREE.getValue();

    Long[] values = {value0, value5, value8, value23};


    JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane,BorderLayout.CENTER);

    add(tablePanel, BorderLayout.CENTER);

    newItemCodeField =new

    JTextField();

    newItemNameField =new

    JTextField();

    newItemPriceField =new

    JTextField();

    newItemUnitComboBox =new JComboBox<>(Unit.values());
    newItemVatComboBox =new JComboBox<>(values);

    //Creation of buttons
    JButton addButton = new JButton("Dodaj");
    JButton editButton = new JButton("Edytuj");
    JButton deleteButton = new JButton("Usuń");

    JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new

    JLabel("   Kod: "));
        formPanel.add(newItemCodeField);
        formPanel.add(new

    JLabel("   Nazwa: "));
        formPanel.add(newItemNameField);
        formPanel.add(new

    JLabel("   Cena: "));
        formPanel.add(newItemPriceField);
        formPanel.add(new

    JLabel("   Jednostka miary: "));
        formPanel.add(newItemUnitComboBox);
        formPanel.add(new

    JLabel("   VAT"));
        formPanel.add(newItemVatComboBox);


    // Adding buttons to the window
    JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);


    JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(formPanel,BorderLayout.NORTH);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);

    add(mainPanel, BorderLayout.SOUTH);


    //below we add actions on buttons
    addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing (WindowEvent e){
            StartWindowButtonListener buttonListener = new StartWindowButtonListener();
            StartWindow startWindow = new StartWindow(buttonListener);
            startWindow.setVisible(true);
            dispose();
        }
    });

        AssortmentButtonListener assortmentButtonListener = new AssortmentButtonListener();
        addButton.addActionListener(assortmentButtonListener);
        editButton.addActionListener(assortmentButtonListener);
        deleteButton.addActionListener(assortmentButtonListener);


}

}
