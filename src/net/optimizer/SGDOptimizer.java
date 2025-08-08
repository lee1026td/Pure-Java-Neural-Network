package net.optimizer;

import datastruct.Matrix;

import java.util.IdentityHashMap;
import java.util.Map;

public class SGDOptimizer implements Optimizer {

    private double learningRate;
    private double momentum;

    private final Map<Matrix, Matrix> vWMap = new IdentityHashMap();
    private final Map<Matrix, Matrix> vBMap = new IdentityHashMap();

    public SGDOptimizer(double learningRate) {
        this.learningRate = learningRate;
    }

    public SGDOptimizer(double learningRate, double momentum) {
        this(learningRate);
        this.momentum = momentum;
    }

    @Override
    public void update(Matrix W, Matrix dW, Matrix B, Matrix dB) {
        if (momentum > 0.0) {
            Matrix vW = vWMap.get(W);
            if (vW == null) {
                vW = new Matrix(W.getRowDim(), W.getColDim());
                vWMap.put(W, vW);
            }

            Matrix vB = vBMap.get(B);
            if (vB == null) {
                vB = new Matrix(B.getRowDim(), B.getColDim());
                vBMap.put(B, vB);
            }

            vW = vW.mulScalar(momentum).addMat(dW);
            vB = vB.mulScalar(momentum).addMat(dB);

            W.subInPlace(vW.mulScalar(learningRate));
            B.subInPlace(vB.mulScalar(learningRate));

            vWMap.put(W, vW);
            vBMap.put(B, vB);
        } else {
            W.subInPlace(dW.mulScalar(learningRate));
            B.subInPlace(dB.mulScalar(learningRate));
        }
    }

}
