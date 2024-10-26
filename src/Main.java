import math.matrix.Matrix;
import neuralnetwork.NeuralNetwork;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("start");

        long start = System.currentTimeMillis();
        NeuralNetwork nn = new NeuralNetwork(List.of(5, 100, 100, 3));

        Matrix target = new Matrix(new double[][] {{ 1d, 0.5d, 0d }});

        double prev = Double.MAX_VALUE;

        for (int i = 0; i < 100000; i++) {
            nn.setInput(List.of(1.0d, 0.0d, 0d, 0d, 0d));
            nn.forward();
            /*
            System.out.println("target: " + target.getValue(0, 0));
            System.out.println("Outp: " + nn.getOutput().getValue(0, 0));
            System.out.println("Cost: " + nn.cost(target));
            System.out.println(" ----  -----");
             */

            if (i % 100 == 0) {
                double cost = nn.cost(target);
                if (cost > prev) {
                    //throw new RuntimeException("Cost of the network increased");
                    System.out.println("\u001B[31m" + "Cost of the network increased");
                }
                prev = cost;

                System.out.println("Cost: " + cost);
            }

            nn.backward(target);
        }



        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}