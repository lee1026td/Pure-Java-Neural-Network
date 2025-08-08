package net.loss;

import datastruct.Matrix;

public class BinaryCrossEntropyLoss implements LossFunction {
    private static double delta = 1e-7;

    @Override
    public double calcLoss(Matrix predicted, Matrix actual) {
        double loss = 0;
        for(int i=0;i<predicted.getColDim();i++) {
            double t = actual.getArray()[i][0];
            double p = predicted.getArray()[0][i];

            p = Math.max(delta, Math.min(1 - delta, p));

            loss += -t * Math.log(p) - (1 - t) * Math.log(1 - p);
        }

        return loss / predicted.getColDim();
    }

    @Override
    public Matrix calcDerivative(Matrix predicted, Matrix actual) {
        double[][] grad = new double[1][predicted.getColDim()];

        for(int i=0;i<predicted.getColDim();i++) {
            double t = actual.getArray()[i][0];
            double p = predicted.getArray()[0][i];

            p = Math.max(delta, Math.min(1 - delta, p));

            grad[0][i] = -((t / p) - (1 - t) / (1 - p));

        }

        return new Matrix(grad);
    }
}