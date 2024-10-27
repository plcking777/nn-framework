import data.DataReader;
import data.DataWriter;
import data.csv.read.CsvReader;
import data.csv.write.CsvWriter;
import math.matrix.Matrix;
import neuralnetwork.NeuralNetwork;

import java.io.IOException;
import java.util.List;

public class Main {


    private static final long ITERATIONS = 10000;


    public static void main(String[] args) throws IOException {

        int rowsOfData = 1;

        DataReader dataReader = new CsvReader("/some/path/here/test.csv", ",");

        Matrix trainData = dataReader.read(rowsOfData, 0);





        System.out.println("start");
        long start = System.currentTimeMillis();




        NeuralNetwork nn = new NeuralNetwork(List.of(1, 10, 100, 154882), 0.1);
        Matrix target;

        double prev = Double.MAX_VALUE;

        for (int i = 0; i < ITERATIONS; i++) {


            for (int j = 0; j < rowsOfData; j++) {
                Matrix input = trainData.getRowAsMatrix(j);
                nn.setInput(List.of(0d));
                target = input;

                nn.forward();

                if (i % 10 == 0) {
                    System.out.println(i / 100 + " / " + ITERATIONS / 100);
                    double cost = nn.cost(target);
                    if (cost > prev) {
                        //throw new RuntimeException("Cost of the network increased");
                        System.out.println("Cost of the network increased");
                    }
                    prev = cost;

                    System.out.println("Cost: " + cost);
                }

                nn.backward(target);
            }

        }



        System.out.println(System.currentTimeMillis() - start + "ms");


        System.out.println("\n\n --- Testing ---");
        /*
        nn.setInput(List.of(0d, 1d, 1d, 1d));
        nn.forward();
        System.out.println(Arrays.deepToString(nn.getOutput().getValues()));

        nn.setInput(List.of(0d, 0d, 0d, 0d));
        nn.forward();
        System.out.println(Arrays.deepToString(nn.getOutput().getValues()));

        nn.setInput(List.of(1d, 0d, 1d, 1d));
        nn.forward();
        System.out.println(Arrays.deepToString(nn.getOutput().getValues()));
         */

        //nn.setInput(trainData.getRowAsMatrix(0));
        nn.setInput(List.of(0d));
        nn.forward();
        DataWriter dataWriter = new CsvWriter(",");
        dataWriter.write("/some/path/here/test.csv", nn.getOutput());
    }
}