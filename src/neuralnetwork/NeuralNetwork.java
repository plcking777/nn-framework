package neuralnetwork;

import math.matrix.Matrix;
import neuralnetwork.utils.ActivationFunction;

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

    // TODO keep track of every activation to be able to do the backprop
    public void forward() {
        Matrix prevActivation = this.input;
        for (int i = 0; i < this.weights.length; i++) {
            Matrix n = prevActivation.multiply(this.weights[i]).add(biases[i]);
            prevActivation = ActivationFunction.mapActivation(n, ActivationFunction.SIGMOID);
        }
        setOutput(prevActivation);
    }

    public void backward(Matrix target) {

        Matrix[] weightDerivatives = new Matrix[this.weights.length];
        Matrix[] biasDerivatives = new Matrix[this.biases.length];

        Matrix previouseActivation = this.output;
        Matrix n = costDerivative(target);
        // weightDerivatives[this.weights.length - 1] = n.multiply();
        for (int i = this.weights.length - 1; i > 0; i--) {

        }
    }

    public double cost(Matrix target) {
        if (output.getRows() != target.getRows() || output.getCols() != target.getCols()) {
            throw new IllegalArgumentException("The output and the target should have the same size.");
        }

        double average = 0.d;
        for (int row = 0; row < output.getRows(); row++) {
            for (int col = 0; col < output.getCols(); col++) {
                average += (output.getValue(row, col) - target.getValue(row, col));
            }
        }
        return Math.pow(average / (output.getRows() * output.getCols()), 2);
    }

    public Matrix costDerivative(Matrix target) {
        return this.output.subtract(target).elementMultiply(2.d);
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
