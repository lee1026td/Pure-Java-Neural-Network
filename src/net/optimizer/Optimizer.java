package net.optimizer;

import datastruct.Matrix;

public interface Optimizer {

    void update(Matrix W, Matrix dW, Matrix B, Matrix dB);

}
