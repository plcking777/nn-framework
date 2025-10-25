import data.DataReader;
import data.csv.read.CsvReader;
import math.matrix.Matrix;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.utils.ActivationFunction;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {


    private static final long ITERATIONS = 1000;


    public static void main(String[] args) throws IOException {

        int rowsOfData = 4;

        DataReader dataReader = new CsvReader("test-xor.csv", ",");

        Matrix trainData = dataReader.read(rowsOfData, List.of(0, 1), 1);
        Matrix trainLabels = dataReader.read(rowsOfData, List.of(2), 1);



        System.out.println("start");
        long start = System.currentTimeMillis();


        NeuralNetwork nn = new NeuralNetwork(List.of(2, 10, 1), 0.1, true, ActivationFunction.RELU);

        for (int i = 0; i < ITERATIONS; i++) {
            double cost = train(nn, trainData, trainLabels, rowsOfData);
            if (i % 100 == 0) {
                System.out.println("Cost: " + cost);
            }
        }



        System.out.println(System.currentTimeMillis() - start + "ms");


        System.out.println("\n\n --- Testing ---");

        nn.setInput(List.of(0d, 0d));
        nn.forward();
        System.out.println(" -> " + Arrays.deepToString(nn.getOutput().getValues()));


        nn.setInput(List.of(0d, 1d));
        nn.forward();
        System.out.println(" -> " + Arrays.deepToString(nn.getOutput().getValues()));


        nn.setInput(List.of(1d, 1d));
        nn.forward();
        System.out.println(" -> " + Arrays.deepToString(nn.getOutput().getValues()));


    }


    private static double train(NeuralNetwork nn, Matrix trainData, Matrix labels, int rowsOfData) {
        Matrix target;
        double totalCost = 0.0d;
        for (int j = 0; j < rowsOfData; j++) {
            Matrix input = trainData.getRowAsMatrix(j);
            nn.setInput(input);
            target = labels.getRowAsMatrix(j);

            nn.forward();
            totalCost += nn.cost(target);
            nn.backward(target);
        }
        return totalCost / rowsOfData;
    }


}