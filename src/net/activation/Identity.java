package net.activation;

public class Identity extends ActivationFunc {
    @Override
    public double calc(double d) {
        return d;
    }

    @Override
    public double calcD(double d) {
        return 1;
    }
}
