package net.activation;

public class Sigmoid extends ActivationFunc {
    @Override
    public double calc(double d) {
        return 1.0 / (1 + Math.exp(-d));
    }

    @Override
    public double calcD(double d) {
        double a = calc(d);
        return a * (1 - a);
    }
}
