package net.optimizer;

import datastruct.Matrix;

import java.util.IdentityHashMap;
import java.util.Map;

public class RMSPropOptimizer implements Optimizer {

    private double learningRate;
    private double beta;
    private double epsilon;

    private Map<Matrix, Matrix> mWMap = new IdentityHashMap();
    private Map<Matrix, Matrix> mBMap = new IdentityHashMap();

    public RMSPropOptimizer(double learningRate, double beta, double epsilon) {
        this.learningRate = learningRate;
        this.beta = beta;
        this.epsilon = epsilon;
    }

    @Override
    public void update(Matrix W, Matrix dW, Matrix B, Matrix dB) {
        Matrix mW = mWMap.get(W);

        if(mW == null) {
            mW = new Matrix(W.getRowDim(), W.getColDim());
            mWMap.put(W, mW);
        }

        Matrix mB = mBMap.get(B);

        if(mB == null) {
            mB = new Matrix(B.getRowDim(), B.getColDim());
            mBMap.put(B, mB);
        }

        mW = mW.mulScalar(beta).addMat(dW.mulHadamard(dW).mulScalar(1 - beta));
        mB = mB.mulScalar(beta).addMat(dB.mulHadamard(dB).mulScalar(1 - beta));

        Matrix WUpdate = dW.divHadamard(mW.sqrt().addScalar(epsilon)).mulScalar(learningRate);
        Matrix BUpdate = dB.divHadamard(mB.sqrt().addScalar(epsilon)).mulScalar(learningRate);

        W.subInPlace(WUpdate);
        B.subInPlace(BUpdate);

        mWMap.put(W, mW);
        mBMap.put(B, mB);
    }
}
