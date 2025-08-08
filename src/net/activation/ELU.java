package net.activation;

public class ELU extends ActivationFunc {

    private final double alpha;

    public ELU(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public double calc(double d) {
        return d > 0 ? d : alpha * (Math.exp(d) - 1);
    }

    @Override
    public double calcD(double d) {
        return d > 0 ? 1 : alpha * Math.exp(d);
    }
}
