package net.activation;

import datastruct.Matrix;

public class Softmax extends ActivationFunc {
    @Override
    public double calc(double d) {
        return 0;
    }

    @Override
    public double calcD(double d) {
        return 0;
    }

    @Override
    public Matrix calcActivationResult(Matrix mat) {
        int row = mat.getRowDim();
        int col = mat.getColDim();
        double[][] res = new double[row][col];

        for(int i=0;i<row;i++) {
            double max = mat.getRowMax(i);

            double sum = 0;
            for(int j=0;j<col;j++) {
                res[i][j] = Math.exp(mat.getArray()[i][j] - max);
                sum += res[i][j];
            }

            for(int j=0;j<col;j++) {
                res[i][j] /= sum;
            }
        }

        return new Matrix(res);
    }
}
