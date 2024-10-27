import math.matrix.Matrix;
import neuralnetwork.NeuralNetwork;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("start");
        long start = System.currentTimeMillis();


        HashMap<List<Double>, double[][]> train = new HashMap<>();

        // Binary adder
        train.put(List.of(0d, 0d, 0d, 0d), new double[][] {{ 0d, 0d, 0d, 0d }});
        train.put(List.of(0d, 0d, 0d, 1d), new double[][] {{ 0d, 0d, 0d, 1d }});
        train.put(List.of(0d, 0d, 1d, 0d), new double[][] {{ 0d, 0d, 1d, 0d }});
        train.put(List.of(0d, 0d, 1d, 1d), new double[][] {{ 0d, 0d, 1d, 1d }});
        train.put(List.of(0d, 1d, 0d, 0d), new double[][] {{ 0d, 0d, 0d, 1d }});
        train.put(List.of(0d, 1d, 0d, 1d), new double[][] {{ 0d, 0d, 1d, 0d }});
        train.put(List.of(0d, 1d, 1d, 0d), new double[][] {{ 0d, 0d, 1d, 1d }});
        train.put(List.of(0d, 1d, 1d, 1d), new double[][] {{ 0d, 1d, 0d, 0d }});
        train.put(List.of(1d, 0d, 1d, 1d), new double[][] {{ 0d, 1d, 0d, 1d }});
        train.put(List.of(1d, 1d, 1d, 1d), new double[][] {{ 0d, 1d, 1d, 0d }});



        NeuralNetwork nn = new NeuralNetwork(List.of(4, 100, 100, 4));
        Matrix target;

        double prev = Double.MAX_VALUE;

        for (int i = 0; i < 1000; i++) {


            for (List<Double> input : train.keySet()) {
                nn.setInput(input);
                target = new Matrix(train.get(input));

                nn.forward();

                if (i % 10000 == 0) {
                    double cost = nn.cost(target);
                    if (cost > prev) {
                        //throw new RuntimeException("Cost of the network increased");
                        System.out.println("Cost of the network increased");
                    }
                    prev = cost;

                    //System.out.println("Cost: " + cost);
                }

                nn.backward(target);
            }

        }



        System.out.println(System.currentTimeMillis() - start + "ms");


        System.out.println("\n\n --- Testing ---");

        nn.setInput(List.of(0d, 1d, 1d, 1d));
        nn.forward();
        System.out.println(Arrays.deepToString(nn.getOutput().getValues()));

        nn.setInput(List.of(0d, 0d, 0d, 0d));
        nn.forward();
        System.out.println(Arrays.deepToString(nn.getOutput().getValues()));

        nn.setInput(List.of(1d, 0d, 1d, 1d));
        nn.forward();
        System.out.println(Arrays.deepToString(nn.getOutput().getValues()));
    }
}