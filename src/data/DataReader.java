package data;

import math.matrix.Matrix;
import java.io.IOException;
import java.util.List;

public interface DataReader {

    Matrix read(int rows, List<Integer> idxs, int offset) throws IOException;

}
