# Pure Java Neural Network Project
## Environments
- Java JDK 21

## About Project
In this project, I implemented a simple form of Artificial Neural Network (ANN) using pure Java only.

## Supported Algorithms (Not Finished yet)
### Activation Functions
- Identity
- ReLU (Rectified Linear Unit)
- ELU (Exponential Linear Unit)
- LeakyReLU
- Sigmoid
- Tanh (Hyperbolic Tangent)
- Softmax
### Loss Functions
- MSELoss
- BinaryCrossEntropyLoss
### Optimizer
- Stochastic Gradient Descent (Including SGD Momentum)
- RMSProp
- AdaGrad
- Adam
### Metric
- Binary accuracy
### Scalar
- Min-Max Scalar

### Usage (Example)
```Java
public class Main {

    public static void main(String[] args) {

        CSVReader reader = new CSVReader("G:\\Datasets\\moon_dataset.csv");
        double[][] arr = reader.readCSV();

        Network nn = new Network(
                new Layer(2, 4, new LeakyReLU(), Initializer.InitType.HE),
                new Layer(4, 6, new LeakyReLU(), Initializer.InitType.HE),
                new Layer(6, 6, new LeakyReLU(), Initializer.InitType.HE),
                new Layer(6, 4, new LeakyReLU(), Initializer.InitType.HE),
                new Layer(4, 1, new Sigmoid(), Initializer.InitType.XAVIER)
        );

        nn.compile(new AdamOptimizer(0.01, 0.9, 0.99, 1e-8), new BinaryCrossEntropyLoss(), new BinaryAccuracy());

        Matrix datasets = new Matrix(arr);

        /* Select columns to divide by data and label values */
        Matrix X = datasets.getColumnTo(0, 1).transpose();
        Matrix Y = datasets.getColumnTo(2, 2);

        /* To normalize your input dataset */
        //Matrix X_norm = MinMaxScalar.minMaxScalar(X);

        nn.train(X, Y, 10000, 100);  // train set, labels, epochs, log steps
        Matrix res = nn.predict(X); // Prediction

        System.out.println("Prediction Accuracy : " + nn.getAccuracy(res, Y));
    }
}
```

## Tested Datasets
- Scikit-learn "make_moons" Dataset
  > https://scikit-learn.org/stable/modules/generated/sklearn.datasets.make_moons.html
- Kaggle "Raisin Binary Classification" Dataset
  > https://www.kaggle.com/datasets/nimapourmoradi/raisin-binary-classification/data

## Example Results (Using Python pyplot)
- "make_moons"
  <img width="800" height="600" alt="Image" src="https://github.com/user-attachments/assets/59c4c434-64c1-4419-98c3-ae47f9cebc3e" />

## TODO
- Add comments
- Add Optimizers
- Applying Dropouts
- Regularization
- Gradient Clipping
- Batch train
