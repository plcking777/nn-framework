package neuralnetwork;

import math.matrix.Matrix;
import neuralnetwork.utils.ActivationFunction;

import java.util.Arrays;
import java.util.List;

public class NeuralNetwork {

    private List<Integer> layers;
    private final Matrix[] weights;
    private final Matrix[] biases;

    private Matrix input;
    private Matrix output;

    /**
     *
     * @param layers A list of ints were it's size represents the amount of layers (including input & output layers);
     *               and the value of the int on each index represents the amount of nodes for the layer on that index
     */
    public NeuralNetwork(List<Integer> layers) {
        // TODO what type of list should this be
        weights = new Matrix[layers.size() - 1];
        biases = new Matrix[layers.size() - 1];

        for (int i = 0; i < layers.size() - 1; i++) {
            int nodes = layers.get(i);
            int next_nodes = layers.get(i + 1);

            weights[i] = new Matrix(nodes, next_nodes, true);
            biases[i] = new Matrix(1, next_nodes, true);
        }

    }

    public void forward() {
        Matrix prevActivation = this.input;
        for (int i = 0; i < this.weights.length; i++) {
            Matrix n = prevActivation.multiply(this.weights[i]).add(biases[i]);
            prevActivation = ActivationFunction.mapActivation(n, ActivationFunction.SIGMOID);
        }
        setOutput(prevActivation);
        System.out.println(Arrays.deepToString(output.getValues()));
    }

    public void backward() {

    }

    public Matrix getInput() {
        return input;
    }

    public void setInput(Matrix input) {
        this.input = input;
    }

    public void setInput(List<Double> input) {
        double[][] values = new double[1][input.size()];
        for (int i = 0; i < input.size(); i++) {
            values[0][i] = input.get(i);
        }
        this.input = new Matrix(values);
    }


    public Matrix getOutput() {
        return output;
    }

    private void setOutput(Matrix output) {
        this.output = output;
    }
}
