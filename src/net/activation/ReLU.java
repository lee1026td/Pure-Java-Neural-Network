package net.activation;

import datastruct.Matrix;

public class ReLU extends ActivationFunc {
    @Override
    public double calc(double d) {
        return Math.max(d, 0);
    }

    @Override
    public double calcD(double d) {
        return d > 0 ? 1.0 : 0;
    }

}
