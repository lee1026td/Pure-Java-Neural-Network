package net.metric;

import datastruct.Matrix;

public class BinaryAccuracy implements Metric {
    @Override
    public double getAccuracy(Matrix predicted, Matrix actual) {
        int correct = 0;
        for (int i = 0; i < predicted.getColDim(); i++) {
            double p = predicted.getArray()[0][i];
            double y = actual.getArray()[i][0];
            if ((p >= 0.5 && y == 1) || (p < 0.5 && y == 0)) correct++;
        }
        return (correct * 1.0 / predicted.getColDim());
    }
}
