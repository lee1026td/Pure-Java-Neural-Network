package net.optimizer;

import datastruct.Matrix;

import java.util.IdentityHashMap;
import java.util.Map;

public class AdamOptimizer implements Optimizer {

    private final double learningRate;
    private final double beta1;
    private final double beta2;
    private final double epsilon;

    private final Map<Matrix, Matrix> mWMap = new IdentityHashMap();
    private final Map<Matrix, Matrix> vWMap = new IdentityHashMap();
    private final Map<Matrix, Matrix> mBMap = new IdentityHashMap();
    private final Map<Matrix, Matrix> vBMap = new IdentityHashMap();

    private int t = 0;

    public AdamOptimizer(double learningRate, double beta1, double beta2, double epsilon) {
        this.learningRate = learningRate;
        this.beta1 = beta1;
        this.beta2 = beta2;
        this.epsilon = epsilon;
    }

    @Override
    public void update(Matrix W, Matrix dW, Matrix B, Matrix dB) {
        t++;

        Matrix mW = mWMap.get(W);
        if (mW == null) {
            mW = new Matrix(W.getRowDim(), W.getColDim());
            mWMap.put(W, mW);
        }

        Matrix vW = vWMap.get(W);
        if (vW == null) {
            vW = new Matrix(W.getRowDim(), W.getColDim());
            vWMap.put(W, vW);
        }

        Matrix mB = mBMap.get(B);
        if (mB == null) {
            mB = new Matrix(B.getRowDim(), B.getColDim());
            mBMap.put(B, mB);
        }

        Matrix vB = vBMap.get(B);
        if (vB == null) {
            vB = new Matrix(B.getRowDim(), B.getColDim());
            vBMap.put(B, vB);
        }

        mW = mW.mulScalar(beta1).addMat(dW.mulScalar(1 - beta1));
        vW = vW.mulScalar(beta2).addMat(dW.mulHadamard(dW).mulScalar(1 - beta2));

        mB = mB.mulScalar(beta1).addMat(dB.mulScalar(1 - beta1));
        vB = vB.mulScalar(beta2).addMat(dB.mulHadamard(dB).mulScalar(1 - beta2));

        double biasCorrection1 = 1.0 - Math.pow(beta1, t);
        double biasCorrection2 = 1.0 - Math.pow(beta2, t);

        Matrix mWHat = mW.mulScalar(1.0 / biasCorrection1);
        Matrix vWHat = vW.mulScalar(1.0 / biasCorrection2);
        Matrix mBHat = mB.mulScalar(1.0 / biasCorrection1);
        Matrix vBHat = vB.mulScalar(1.0 / biasCorrection2);

        Matrix WUpdate = mWHat.divHadamard(vWHat.sqrt().addScalar(epsilon)).mulScalar(learningRate);
        Matrix BUpdate = mBHat.divHadamard(vBHat.sqrt().addScalar(epsilon)).mulScalar(learningRate);

        W.subInPlace(WUpdate);
        B.subInPlace(BUpdate);

        mWMap.put(W, mW);
        vWMap.put(W, vW);
        mBMap.put(B, mB);
        vBMap.put(B, vB);
    }
}