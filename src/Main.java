import math.matrix.Matrix;
import neuralnetwork.utils.ActivationFunction;

public class Main {
    public static void main(String[] args) {

        Matrix m1 = new Matrix(1, 1, true);
        Matrix w1 = new Matrix(1, 2, true);
        Matrix w2 = new Matrix(1, 2, true);


        Matrix n1 = m1.multiply(w1);
        Matrix a1 = ActivationFunction.mapActivation(n1, ActivationFunction.SIGMOID);

        Matrix n2 = a1.multiply(w2.transpose());
        Matrix a2 = ActivationFunction.mapActivation(n2, ActivationFunction.SIGMOID);

    }
}