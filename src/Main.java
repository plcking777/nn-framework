import data.DataReader;
import data.csv.read.CsvReader;
import graphics.window.MainWindow;
import math.matrix.Matrix;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.utils.ActivationFunction;

import java.io.IOException;
import java.util.List;

public class Main {


    private static final long ITERATIONS = 10000;


    public static void main(String[] args) throws IOException {

        int rowsOfData = 1;

        DataReader dataReader = new CsvReader("data\\numbers\\number-7.csv", ",");


        double[][] inp = new double[784][2];
        for (int x = 0; x < 28; x++) {
            for (int y = 0; y < 28; y++) {
                inp[x * 28 + y] = new double[] {x / 28.0, y / 28.0};
            }
        }

        Matrix trainData = new Matrix(inp);
        Matrix trainLabels = dataReader.read(rowsOfData, null, 0).mapFn(x -> x / 255.0).transpose();



        System.out.println("start");
        long start = System.currentTimeMillis();


        NeuralNetwork nn = new NeuralNetwork(List.of(2, 256, 1), 0.001, true, ActivationFunction.RELU);

        double[][] renderInp = new double[256 * 256][2];
        for (int x = 0; x < 256; x++) {
            for (int y = 0; y < 256; y++) {
                renderInp[x * 256 + y] = new double[] {x / 256.0, y / 256.0};
            }
        }

        Matrix renderData = new Matrix(renderInp);


        MainWindow window = new MainWindow(nn, 800, 600, 256, 256, 2);
        window.setVisible(true);


        for (int i = 0; i < ITERATIONS; i++) {
            double cost = train(nn, trainData, trainLabels, rowsOfData);
            if (i % 100 == 0) {
                System.out.println("Cost: " + cost);

                nn.setInput(renderData);
                nn.forward();
                renderImage(nn, window);
            }
            window.setProgressBar((float)i / ITERATIONS);
        }
        window.setProgressBar(1.00f);



        System.out.println(System.currentTimeMillis() - start + "ms");
    }


    private static double train(NeuralNetwork nn, Matrix trainData, Matrix labels, int rowsOfData) {
        Matrix target;
        Matrix input = trainData;
        target = labels;

        nn.setInput(input);
        nn.forward();
        double totalCost = nn.cost(target);
        nn.backward(target);
        return totalCost;
    }



    private static void renderImage(NeuralNetwork nn, MainWindow window) {
        double[] data = new double[256 * 256];
        for (int i = 0; i < 256 * 256; i++) {
            data[i] = nn.getOutput().getRow(i)[0];
        }
        window.setImage(data);
    }

}