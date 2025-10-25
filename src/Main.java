import data.DataReader;
import data.csv.read.CsvReader;
import math.matrix.Matrix;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.utils.ActivationFunction;

import java.io.IOException;
import java.util.List;

public class Main {


    private static final long ITERATIONS = 10000;


    public static void main(String[] args) throws IOException {

        int rowsOfData = 1;

        DataReader dataReader = new CsvReader("C:\\github\\gaming\\out.csv", ",");

        Matrix trainData = dataReader.read(rowsOfData, null, 0).mapFn(x -> x / 255.0);
        Matrix trainLabels = dataReader.read(rowsOfData, null, 0).mapFn(x -> x / 255.0);



        System.out.println("start");
        long start = System.currentTimeMillis();


        NeuralNetwork nn = new NeuralNetwork(List.of(784, 256, 256, 784), 0.001, true, ActivationFunction.RELU);

        for (int i = 0; i < ITERATIONS; i++) {
            double cost = train(nn, trainData, trainLabels, rowsOfData);
            if (i % 1000 == 0) {
                System.out.println("Cost: " + cost);
            }
        }



        System.out.println(System.currentTimeMillis() - start + "ms");


        System.out.println("\n\n --- Testing ---");

        /*
        nn.setInput(List.of(0d, 0d));
        nn.forward();
        System.out.println(" -> " + Arrays.deepToString(nn.getOutput().getValues()));


        nn.setInput(List.of(0d, 1d));
        nn.forward();
        System.out.println(" -> " + Arrays.deepToString(nn.getOutput().getValues()));


        nn.setInput(List.of(1d, 1d));
        nn.forward();
        System.out.println(" -> " + Arrays.deepToString(nn.getOutput().getValues()));
         */


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