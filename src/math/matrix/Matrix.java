package math.matrix;

import java.util.function.DoubleFunction;

public class Matrix {

    private int rows;
    private int cols;
    private double[][] values;

    public Matrix(int rows, int cols, boolean random) {
        this.rows = rows;
        this.cols = cols;
        this.values = new double[rows][cols];

        if (random) {
            for (int row = 0; row < this.rows; row++) {
                for (int col = 0; col < this.cols; col++) {
                    this.values[row][col] = generateRandom(-10.0, 10.0);
                }
            }
        }

    }

    public Matrix(double[][] values) {
        this.rows = values.length;
        this.cols = values[0].length;
        this.values = values;
    }


    private double generateRandom(double min, double max) {
        return min + (Math.random() * (max - min));
    }

    /**
     * This method adds 2 matrices of the same size
     * @param matrix Matrix to add with
     */
    public Matrix add(Matrix matrix) {
        if (this.rows != matrix.getRows() || this.cols != matrix.getCols()) {
            throw new IllegalArgumentException("Wrong sizes for Matrix addition: ("
                    + this.rows + "x" + this.cols + "   " + matrix.getRows() + "x" + matrix.getCols() + ")");
        }
        double[][] out = new double[this.rows][this.cols];
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                out[row][col] = this.values[row][col] + matrix.getValue(row, col);
            }
        }
        return new Matrix(out);
    }

    /**
     * This method subtracts 2 matrices of the same sizes (every element)
     * @param matrix Matrix to subtract with
     */
    public Matrix subtract(Matrix matrix) {
        double[][] out = new double[this.rows][this.cols];
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                out[row][col] = this.values[row][col] - matrix.getValue(row, col);
            }
        }
        return new Matrix(out);
    }


    /**
     * This method multiplies 2 matrices
     * @param matrix Matrix to multiply by
     */
    public Matrix multiply(Matrix matrix) {

        if (this.cols != matrix.getRows()) {
            throw new IllegalArgumentException("Unable to do matrix multiplication. Wrong size ("
                    + this.rows + "x"  + this.cols + "    " + matrix.getRows() + "x" + matrix.getCols() + ")");
        }

        double[][] out = new double[this.rows][matrix.getCols()];

        for (int row = 0; row < this.rows; row++) {
            for (int col2 = 0; col2 < matrix.getCols(); col2++) {
                for (int col = 0; col < this.cols; col++) {
                    out[row][col2] += this.values[row][col] * matrix.getValue(col, col2);
                }
            }
        }
        return new Matrix(out);
    }

    /**
     * This method multiplies every single element of the matrix with a given parameter
     * @param n to number that multiplies every element
     */
    public Matrix multiplyEach(double n) {
        double[][] out = new double[this.rows][this.cols];
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                out[row][col] = this.values[row][col] * n;
            }
        }
        return new Matrix(out);
    }


    public Matrix elementMultiply(Matrix matrix) {
        if (this.rows != matrix.getRows() || this.cols != matrix.getCols()) {
            throw new IllegalArgumentException("To use element-multiply both matrixes need to be the same size (" +
                    this.rows + "x" + this.cols + " != " + matrix.getRows() + "x" + matrix.getCols());
        }
        double[][] out = new double[this.rows][this.cols];
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                out[row][col] = this.values[row][col] * matrix.getValue(row, col);
            }
        }
        return new Matrix(out);
    }

    /**
     * Transposes the matrix
     */
    public Matrix transpose() {
        double[][] out = new double[this.cols][this.rows];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                out[col][row] = this.values[row][col];
            }
        }

        return new Matrix(out);
    }


    public Matrix mapFn(DoubleFunction fn) {
        double[][] out = new double[this.rows][this.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                out[row][col] = (double) fn.apply(this.values[row][col]);
            }
        }

        return new Matrix(out);
    }




    public double getValue(int row, int col) {
        return this.values[row][col];
    }

    public int getRows() {
        return rows;
    }

    public double[] getRow(int row) {
        return this.values[row].clone();
    }

    public Matrix getRowAsMatrix(int row) {
        return new Matrix(new double[][] { this.values[row].clone() });
    }


    public int getCols() {
        return cols;
    }

    public double[][] getValues() {
        return this.values;
    }

    public void setValues(double[][] values) {
        this.values = values;
    }
}
