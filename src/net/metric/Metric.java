package net.metric;

import datastruct.Matrix;

public interface Metric {
    double getAccuracy(Matrix predicted, Matrix actual);
}
