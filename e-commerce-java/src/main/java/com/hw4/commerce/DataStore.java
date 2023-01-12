package com.hw4.commerce;

import com.opencsv.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    //    /.../wildfly-23.0.2.Final/standalone/data/ecommercedb.csv
    private static String filePath = System.getProperty("jboss.server.data.dir") + File.separator + "ecommercedb.csv";

    // Data Persistence

    public static List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        try {
            CSVReader csvReader = getCSVReader();
            String[] nextRow;
            while ((nextRow = csvReader.readNext()) != null) {
                Item item = Item.fromCSVRow(nextRow);
                items.add(item);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void updateItems(List<Item> items) {
        try {
            CSVWriter csvWriter = getCSVWriter();
            items.forEach(
                item -> csvWriter.writeNext(item.toCSVRow())
            );
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    CSV IO

    private static CSVReader getCSVReader() throws FileNotFoundException {
        FileReader fileReader = new FileReader(filePath);
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(csvParser).build();
        return csvReader;
    }

    private static CSVWriter getCSVWriter() throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        CSVWriter csvWriter = new CSVWriter(fileWriter, ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        return csvWriter;
    }

}
