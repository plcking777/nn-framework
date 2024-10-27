package data;

import math.matrix.Matrix;
import java.io.IOException;

public interface DataReader {

    Matrix read(int rows, int offset) throws IOException;

}
