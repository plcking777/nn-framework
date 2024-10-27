package data;

import math.matrix.Matrix;

import java.io.IOException;

public interface DataWriter {

    void write(String filepath, Matrix data) throws IOException;
}
