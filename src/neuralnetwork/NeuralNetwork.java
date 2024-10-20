package neuralnetwork;

import math.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    List<Matrix> weights;
    List<Matrix> biases;

    /**
     *
     * @param layers A list of ints were it's size represents the amount of layers (including input & output layers);
     *               and the value of the int on each index represents the amount of nodes for the layer on that index
     */
    public NeuralNetwork(List<Integer> layers) {
        // TODO what type of list should this be
        weights = new ArrayList<>(layers.size() - 1);
        biases = new ArrayList<>(layers.size() - 1);

        for (int i = 0; i < layers.size() - 1; i++) {
            int nodes = layers.get(i);
            int next_nodes = layers.get(i + 1);

            weights.set(i, new Matrix(nodes, next_nodes, true));
            biases.set(i, new Matrix(1, next_nodes, true));
        }

    }

    public void forward() {

    }

    public void backward() {

    }
}
