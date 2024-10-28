import data.DataReader;
import data.DataWriter;
import data.csv.read.CsvReader;
import data.csv.write.CsvWriter;
import math.matrix.Matrix;
import neuralnetwork.NeuralNetwork;

import java.io.IOException;
import java.util.List;

public class Main {


    private static final long ITERATIONS = 1000;


    public static void main(String[] args) throws IOException {

        int rowsOfData = 1;

        DataReader dataReader = new CsvReader("/some/path/here/test.csv", ",");

        Matrix trainData = dataReader.read(rowsOfData, 0);





        System.out.println("start");
        long start = System.currentTimeMillis();




        NeuralNetwork nn = new NeuralNetwork(List.of(1, 10, 154882), 0.1, true);

        for (int i = 0; i < ITERATIONS; i++) {
            double cost = train(nn, trainData, rowsOfData);
            if (i % 100 == 0) {
                System.out.println("Cost: " + cost);
            }
        }



        System.out.println(System.currentTimeMillis() - start + "ms");


        System.out.println("\n\n --- Testing ---");

        nn.setInput(List.of(0d));
        nn.forward();
        DataWriter dataWriter = new CsvWriter(",");
        dataWriter.write("/some/path/here/test.csv", nn.getOutput());
    }


    private static double train(NeuralNetwork nn, Matrix trainData, int rowsOfData) {
        Matrix target;
        double totalCost = 0.0d;
        for (int j = 0; j < rowsOfData; j++) {
            Matrix input = trainData.getRowAsMatrix(j);
            nn.setInput(List.of(0d));
            target = input;

            nn.forward();
            totalCost += nn.cost(target);
            nn.backward(target);
        }
        return totalCost / rowsOfData;
    }


}