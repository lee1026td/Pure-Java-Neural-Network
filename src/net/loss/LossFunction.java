package net.loss;

import datastruct.Matrix;

public interface LossFunction {

    double calcLoss(Matrix predicted, Matrix actual);

    Matrix calcDerivative(Matrix predicted, Matrix actual);

}
