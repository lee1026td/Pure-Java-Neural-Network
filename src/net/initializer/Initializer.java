package net.initializer;

import datastruct.Matrix;

import java.util.Random;

public class Initializer {

    public enum InitType { XAVIER, HE, NORMAL, ZERO }

    private static Random r = new Random();

    public static Matrix init(int rowDim, int colDim, InitType type) {
        switch(type) {
            case XAVIER: return xavier(rowDim, colDim);
            case HE: return he(rowDim, colDim);
            case NORMAL: return normal(rowDim, colDim);
            case ZERO: return zeros(rowDim, colDim);
            default: throw new IllegalArgumentException("Unknown Init Type");
        }
    }

    private static Matrix xavier(int rowDim, int colDim) {

        double[][] arr = new double[rowDim][colDim];
        double std = Math.sqrt((double) 2 / (rowDim + colDim));

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                arr[i][j] = r.nextGaussian() * std;
            }
        }

        return new Matrix(arr);
    }

    private static Matrix he(int rowDim, int colDim) {

        double[][] arr = new double[rowDim][colDim];
        double std = Math.sqrt((double) 2 / colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                arr[i][j] = r.nextGaussian() * std;
            }
        }

        return new Matrix(arr);
    }

    private static Matrix zeros(int rowDim, int colDim) {
        return new Matrix(rowDim, colDim);
    }

    private static Matrix normal(int rowDim, int colDim) {

        double[][] arr = new double[rowDim][colDim];

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                arr[i][j] = r.nextGaussian() * 0.1;
            }
        }

        return new Matrix(arr);
    }

    public static Matrix fill(int rowDim, int colDim, double fillValue) {
        Matrix res = new Matrix(rowDim, colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.getArray()[i][j] = fillValue;
            }
        }

        return res;
    }

    public static Matrix randomBernoulli(int rowDim, int colDim, double prob) {
        Matrix res = new Matrix(rowDim, colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.getArray()[i][j] = (r.nextDouble() < prob) ? 1.0 : 0.0;
            }
        }

        return res;
    }
}
