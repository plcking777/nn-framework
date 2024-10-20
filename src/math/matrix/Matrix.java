package math.matrix;

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
    public Matrix add(Matrix matrix) {
        double[][] out = new double[this.rows][this.cols];
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                out[row][col] = this.values[row][col] + matrix.getValue(row, col);
            }
        }
        return new Matrix(out);
    }

    public Matrix subtract(Matrix matrix) {
        double[][] out = new double[this.rows][this.cols];
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                out[row][col] = this.values[row][col] - matrix.getValue(row, col);
            }
        }
        return new Matrix(out);
    }


    public Matrix multiply(Matrix matrix) {

        if (this.cols != matrix.getRows()) {
            throw new IllegalArgumentException("Unable to do matrix multiplication. wrong size");
        }

        double[][] out = new double[this.rows][];

        for (int row = 0; row < this.rows; row++) {
            for (int col2 = 0; col2 < matrix.getCols(); col2++) {
                for (int col = 0; col < this.cols; col++) {
                    out[row][col2] += this.values[row][col] * matrix.getValue(col, col2);
                }
            }
        }
        return new Matrix(out);
    }

    public Matrix elementMultiply(double n) {
        double[][] out = new double[this.rows][this.cols];
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                out[row][col] = this.values[row][col] * n;
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

    public int getCols() {
        return cols;
    }
}
