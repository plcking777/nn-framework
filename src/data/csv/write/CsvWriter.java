package data.csv.write;

import data.DataWriter;
import math.matrix.Matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvWriter implements DataWriter {

    private final String delimiter;

    public CsvWriter(String delimiter) {
        this.delimiter = delimiter;
    }


    @Override
    public void write(String filepath, Matrix data) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filepath));
        for (int row = 0; row < data.getRows(); row++) {
            double[] rowData = data.getRow(row);
            String[] stringData = dataToString(rowData);
            String line = String.join(delimiter, stringData);
            writer.println(line);
            writer.flush();
        }
    }

    private static String[] dataToString(double[] data) {
        String[] outp = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            outp[i] = String.valueOf(Math.max(0.01, Math.min(1.00, data[i])));
        }
        return outp;
    }
}
