package org.example.assortment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.table.DefaultTableModel;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class AssortmentMethod {
    private static List<Long> codeList = new ArrayList();

    public static String fileName = "src/main/resources/assortment_data.json";

    public static void loadAssortmentFromFile() throws IOException {

        // Files.readAllBytes(Paths.get(fileName)
        List<String> jsonStr = new ArrayList<>(Files.readAllLines(Paths.get("src/main/resources/assortment_data.json")));

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                // Jeśli plik nie istnieje, zakończ wczytywanie
                return;
            }
            if (jsonStr.isEmpty()) {
                DefaultTableModel model = (DefaultTableModel) Assortment.assortmentTable.getModel();
                model.setRowCount(0);
            }

            // Wczytanie danych z pliku JSON
            if (!jsonStr.isEmpty()) {
                JSONParser parser = new JSONParser();
                JSONArray dane = (JSONArray) parser.parse(new FileReader(fileName));

                // Wyczyszczenie tabeli przed wczytaniem nowych danych

                DefaultTableModel model = (DefaultTableModel) Assortment.assortmentTable.getModel();
                model.setRowCount(0);

                // Dodanie wczytanych danych do tabeli
                for (Object object : dane) {

                    JSONObject product = (JSONObject) object;
                    long id = (Long) product.get("Kod");
                    String name = (String) product.get("Nazwa");
                    double price = (double) product.get("Cena");
                    String unit = (String) product.get("Jednostka");
                    long vat = (Long) product.get("VAT");
                    model.addRow(new Object[]{id, name, price, unit, vat});
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCodeList() {

        JSONParser parser = new JSONParser();
        JSONArray dane = null;
        try {
            dane = (JSONArray) parser.parse(new FileReader(fileName));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
//        wczytywanie kodów do listy
        for (Object object : dane) {
            JSONObject product = (JSONObject) object;
            long id = (Long) product.get("Kod");
            codeList.add(id);
        }
    }

    public static List<Long> getCodeList(){
        return codeList;

    }
}


