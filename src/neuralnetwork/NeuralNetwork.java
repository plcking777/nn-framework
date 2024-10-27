package neuralnetwork;

import math.matrix.Matrix;
import neuralnetwork.utils.ActivationFunction;

import java.util.List;

public class NeuralNetwork {

    //private static final double LEARNING_RATE = 0.0001;
    private final double LEARNING_RATE;

    private List<Integer> layers;
    private final Matrix[] weights;
    private final Matrix[] biases;

    private Matrix[] activations;




    /**
     *
     * @param layers A list of ints were it's size represents the amount of layers (including input & output layers);
     *               and the value of the int on each index represents the amount of nodes for the layer on that index
     */
    public NeuralNetwork(List<Integer> layers, double learningRate) {
        this.layers = layers;
        this.activations = new Matrix[layers.size()];
        this.LEARNING_RATE = learningRate;

        this.weights = new Matrix[layers.size() - 1];
        this.biases = new Matrix[layers.size() - 1];

        for (int i = 0; i < layers.size() - 1; i++) {
            int nodes = layers.get(i);
            int next_nodes = layers.get(i + 1);

            this.weights[i] = new Matrix(nodes, next_nodes, true);
            this.biases[i] = new Matrix(1, next_nodes, true);
        }


    }

    public void forward() {
        Matrix prevActivation = this.getInput();
        for (int i = 0; i < this.weights.length; i++) {
            Matrix n = prevActivation.multiply(this.weights[i]).add(biases[i]);
            //Matrix n = prevActivation.multiply(this.weights[i]);
            prevActivation = ActivationFunction.mapActivation(n, ActivationFunction.SIGMOID);
            this.activations[i+1] = prevActivation;
        }
        setOutput(prevActivation);
    }

    public void backward(Matrix target) {
        Matrix[] weightDerivatives = new Matrix[this.weights.length];
        Matrix[] biasDerivatives = new Matrix[this.biases.length];

        Matrix n = costDerivative(target).transpose();

        for (int i = 0; i < this.weights.length; i++) {
            weightDerivatives[this.weights.length - (i+1)] = n.multiply(this.activations[this.activations.length - (i+2)]);
            biasDerivatives[this.biases.length - (i+1)] = n;
            n = this.weights[this.weights.length - (i+1)].multiply(n);
        }


        this.updateWeightsAndBiases(weightDerivatives, biasDerivatives);
    }

    public double cost(Matrix target) {
        Matrix output = getOutput();
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
        return this.getOutput().subtract(target).elementMultiply(2.d);
    }

    private void updateWeightsAndBiases(Matrix[] weightDerivatives, Matrix[] biasDerivatives) {
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] = this.weights[i].subtract(weightDerivatives[i].transpose().elementMultiply(LEARNING_RATE));
        }

        for (int i = 0; i < this.biases.length; i++) {
            this.biases[i] = this.biases[i].subtract(biasDerivatives[i].transpose().elementMultiply(LEARNING_RATE));
        }
    }

    public Matrix getInput() {
        return this.activations[0];
    }

    public void setInput(Matrix input) {
        if (input.getRows() != 1 || input.getCols() != this.layers.getFirst()) {
            throw new IllegalArgumentException("An invalid input was given with the wrong size.");
        }
        this.activations[0] = input;
    }

    public void setInput(List<Double> input) {
        if (input.size() != this.layers.getFirst()) {
            throw new IllegalArgumentException("An invalid input was given with the wrong size.");
        }
        double[][] values = new double[1][input.size()];
        for (int i = 0; i < input.size(); i++) {
            values[0][i] = input.get(i);
        }
        this.activations[0] = new Matrix(values);
    }


    public Matrix getOutput() {
        return this.activations[this.activations.length - 1];
    }

    private void setOutput(Matrix output) {
        this.activations[this.activations.length - 1] = output;
    }
}
