package net.activation;

public class Tanh extends ActivationFunc {
    @Override
    public double calc(double d) {
        return Math.tanh(d);
    }

    @Override
    public double calcD(double d) {
        return 1 - Math.pow(d, 2);
    }
}
