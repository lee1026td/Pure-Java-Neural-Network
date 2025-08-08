package net;

import datastruct.Matrix;
import net.loss.LossFunction;
import net.metric.Metric;
import net.optimizer.Optimizer;

import java.util.Arrays;
import java.util.List;

public class Network {

    private List<Layer> layers;

    private Optimizer optimizer;
    private LossFunction lossFunction;
    private Metric metric;

    public Network(Layer ... layers) {
        this.layers = Arrays.asList(layers);
    }

    public void compile(Optimizer optimizer, LossFunction lossFunction, Metric metric) {
        this.optimizer = optimizer;
        this.lossFunction = lossFunction;
        this.metric = metric;
    }

    public Matrix forward(Matrix input) {
        Matrix output = input;

        for(Layer l : layers) {
            output = l.forward(output);
        }
        return output;
    }

    public void backward(Matrix predicted, Matrix actual) {
        Matrix delta = lossFunction.calcDerivative(predicted, actual);

        for(int i=layers.size() - 1;i>=0;i--) {
            Layer layer = layers.get(i);
            delta = layer.backward(delta);

            String layerKey = "L" + i;

            Matrix W = layer.getWeights();
            Matrix B = layer.getBias();

            Matrix dW = layer.getWeightGradient();
            Matrix dB = layer.getBiasGradient();

            optimizer.update(W, dW, B, dB);
        }
    }

    public void train(Matrix X, Matrix Y, int epochs, int logStep) {
        for(int i=1;i<=epochs;i++) {
            Matrix P = forward(X);

            double loss = lossFunction.calcLoss(P, Y);
            backward(P, Y);

            if(i % logStep == 0 || i == 1 || i == epochs) {
                System.out.println("Epoch : " + i + " | Loss : " + loss);
            }

        }
    }

    public Matrix predict(Matrix X) {
        Matrix out = X;
        for(Layer l : layers) {
            out = l.forward(out);
        }
        return out;
    }

    public double getAccuracy(Matrix predicted, Matrix actual) {
        return metric.getAccuracy(predicted, actual);
    }

    public Layer getLayer(int i) {
        return layers.get(i);
    }

}
