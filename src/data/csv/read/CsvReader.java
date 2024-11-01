package data.csv.read;

import data.DataReader;
import math.matrix.Matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader implements DataReader {

    private final String filepath;
    private final String delimiter;

    public CsvReader(String filepath, String delimiter) {
        this.filepath = filepath;
        this.delimiter = delimiter;
    }


    @Override
    public Matrix read(int rows, int offset) throws IOException {
        String line;

        double[][] data = new double[rows][];

        BufferedReader br = new BufferedReader(new FileReader(this.filepath));

        int index = 0;

        while ((line = br.readLine()) != null) {
            if (index < offset) {
                continue;
            }
            if (index - offset >= rows) {
                 break;
            }

            String[] values = line.split(this.delimiter);
            double[] parsed = new double[values.length];
            for (int i = 0; i < values.length; i++) {
                double value = Double.parseDouble(values[i].trim());
                parsed[i] = value;
            }
            data[index - offset] = parsed;
            index++;
        }
        return new Matrix(data);
    }
}
