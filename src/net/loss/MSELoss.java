package net.loss;

import datastruct.Matrix;

public class MSELoss implements LossFunction {
    @Override
    public double calcLoss(Matrix predicted, Matrix actual) {
        Matrix diff = predicted.subMat(actual.transpose());
        Matrix squared = diff.mulHadamard(diff);
        return ((double) 1 / predicted.getColDim()) * squared.sum();
    }

    @Override
    public Matrix calcDerivative(Matrix predicted, Matrix actual) {
        Matrix diff = predicted.subMat(actual.transpose());
        return diff.mulScalar((double) 2 / predicted.getColDim());
    }
}
