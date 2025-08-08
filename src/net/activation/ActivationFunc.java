package net.activation;

import datastruct.Matrix;

public abstract class ActivationFunc {

    public abstract double calc(double d);

    public abstract double calcD(double d);

    public Matrix calcActivationResult(Matrix mat) {
        double[][] res = new double[mat.getRowDim()][mat.getColDim()];
        double[][] arr = mat.getArray();

        for(int i=0;i<mat.getRowDim();i++) {
            for(int j=0;j<mat.getColDim();j++) {
                res[i][j] = calc(arr[i][j]);
            }
        }

        return new Matrix(res);
    }

    public Matrix calcDerivationResult(Matrix mat) {
        double[][] res = new double[mat.getRowDim()][mat.getColDim()];
        double[][] arr = mat.getArray();

        for(int i=0;i<mat.getRowDim();i++) {
            for(int j=0;j<mat.getColDim();j++) {
                res[i][j] = calcD(arr[i][j]);
            }
        }

        return new Matrix(res);
    }

}
