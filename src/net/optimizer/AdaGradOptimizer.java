package net.optimizer;

import datastruct.Matrix;
import net.initializer.Initializer;

import java.util.IdentityHashMap;
import java.util.Map;

public class AdaGradOptimizer implements Optimizer {

    private final double learningRate;
    private final double epsilon;

    private final Map<Matrix, Matrix> gWMap = new IdentityHashMap();
    private final Map<Matrix, Matrix> gBMap = new IdentityHashMap();

    public AdaGradOptimizer(double learningRate, double epsilon) {
        this.learningRate = learningRate;
        this.epsilon = epsilon;
    }

    @Override
    public void update(Matrix W, Matrix dW, Matrix B, Matrix dB) {

        // === W ===
        Matrix gW = gWMap.get(W);
        if (gW == null) {
            gW = new Matrix(W.getRowDim(), W.getColDim());
            gWMap.put(W, gW);
        }

        gW = gW.addMat(dW.mulHadamard(dW));  // G += dW^2

        Matrix adjustedDW = dW.divHadamard(gW.sqrt().addScalar(epsilon));

        W.subInPlace(adjustedDW.mulScalar(learningRate));
        gWMap.put(W, gW);

        // === B ===
        Matrix gB = gBMap.get(B);
        if (gB == null) {
            gB = new Matrix(B.getRowDim(), B.getColDim());
            gBMap.put(B, gB);
        }

        gB = gB.addMat(dB.mulHadamard(dB));  // G += dB^2
        Matrix adjustedDB = dB.divHadamard(gB.sqrt().addScalar(epsilon));
        B.subInPlace(adjustedDB.mulScalar(learningRate));
        gBMap.put(B, gB);
    }
}