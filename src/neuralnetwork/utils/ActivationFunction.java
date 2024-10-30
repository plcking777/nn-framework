package neuralnetwork.utils;

import math.matrix.Matrix;

public class ActivationFunction {

    public static final String SIGMOID = "sigmoid";
    public static final String SIGMOID_DERIVATIVE = "sigmoid-derivative";

    public static final String RELU = "relu";
    public static final String RELU_DERIVATIVE = "relu-derivative";


    private static double sigmoid(double x) {
        if (x >= 0) {
            return 1.d / (1.d + Math.exp(-x));
        }
        return Math.exp(x) / (1.d + Math.exp(x));
    }

    private static double sigmoidDerivative(double x) {
        return x * (1.d - x);
    }


    private static double relu(double x) {
        return Math.max(0d, x);
    }

    private static double reluDerivative(double x) {
        if (x <= 0d) return 0d;
        return 1d;
    }

    private static Matrix mapSigmoid(Matrix matrix) {
        double[][] values = matrix.getValues();
        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getCols(); col++) {
                values[row][col] = sigmoid(values[row][col]);
            }
        }
        return new Matrix(values);
    }

    private static Matrix mapSigmoidDerivative(Matrix matrix) {
        double[][] values = matrix.getValues();
        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getCols(); col++) {
                values[row][col] = sigmoidDerivative(values[row][col]);
            }
        }
        return new Matrix(values);
    }


    private static Matrix mapRelu(Matrix matrix) {
        double[][] values = matrix.getValues();
        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getCols(); col++) {
                values[row][col] = relu(values[row][col]);
            }
        }
        return new Matrix(values);
    }

    private static Matrix mapReluDerivative(Matrix matrix) {
        double[][] values = matrix.getValues();
        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getCols(); col++) {
                values[row][col] = reluDerivative(values[row][col]);
            }
        }
        return new Matrix(values);
    }

    public static Matrix mapActivation(Matrix matrix, String activation) {
        switch(activation) {
            case SIGMOID:
                return mapSigmoid(matrix);
            case SIGMOID_DERIVATIVE:
                return mapSigmoidDerivative(matrix);
            case RELU:
                return mapRelu(matrix);
            case RELU_DERIVATIVE:
                return mapReluDerivative(matrix);
            default:
                throw new IllegalArgumentException("No correct activation function was provided");
        }
    }
}
