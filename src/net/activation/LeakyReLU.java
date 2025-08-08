package net.activation;

public class LeakyReLU extends ActivationFunc {
    @Override
    public double calc(double d) {
        return d > 0 ? d : 0.01 * d;
    }

    @Override
    public double calcD(double d) {
        return d > 0 ? 1.0 : 0.01;
    }
}
