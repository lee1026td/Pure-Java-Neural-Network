package net;

import datastruct.Matrix;
import net.activation.ActivationFunc;
import net.activation.Sigmoid;
import net.initializer.Initializer;

public class Layer {

    private Matrix W;
    private Matrix B;
    private Matrix X;
    private Matrix Z;
    private Matrix out;

    private Matrix dZ;
    private Matrix dW;
    private Matrix dB;

    private ActivationFunc func;

    public Layer(int inputDim, int outputDim, ActivationFunc func, Initializer.InitType initType) {
        this.W = Initializer.init(outputDim, inputDim, initType);
        this.B = Initializer.init(outputDim, 1, Initializer.InitType.NORMAL);
        this.func = func;
    }

    public Matrix forward(Matrix X) {
        this.X = X;
        Z = W.mulMat(X);

        int colSize = Z.getColDim();

        Z = Z.addMat(B.padCol(colSize));

        out = func.calcActivationResult(Z);     // A = f(Z)
        return out;
    }

    public Matrix backward(Matrix delta) {

        dZ = delta.mulHadamard(func.calcDerivationResult(Z));
        dW = dZ.mulMat(X.transpose()).mulScalar((double) 1 / X.getColDim());
        dB = dZ.sumCols().mulScalar((double) 1 / X.getColDim());

        return W.transpose().mulMat(dZ);
    }

    public Matrix getWeights() { return W; }
    public Matrix getBias() { return B; }
    public Matrix getWeightGradient() { return dW; }
    public Matrix getBiasGradient() { return dB; }
    public Matrix getZ() { return Z; }

    public void setWeights(Matrix W) {
        this.W = W;
    }

    public void setBias(Matrix B) {
        this.B = B;
    }

    public Matrix getRes() {
        return out;
    }
}
