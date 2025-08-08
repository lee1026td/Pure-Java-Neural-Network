import datastruct.Matrix;
import filereader.CSVReader;
import net.Layer;
import net.Network;
import net.activation.*;
import net.initializer.Initializer;
import net.loss.*;
import net.metric.BinaryAccuracy;
import net.optimizer.*;
import scalar.MinMaxScalar;


public class Main {

    public static void main(String[] args) {

        CSVReader reader = new CSVReader("G:\\Datasets\\moon_dataset.csv");;

        Matrix datasets = new Matrix(reader.readCSV());

        Matrix X = MinMaxScalar.minMaxScalar(datasets.getColumnTo(0, 1).transpose());
        Matrix Y = datasets.getColumnTo(2, 2);

        CSVReader val = new CSVReader("G:\\Datasets\\make_moons.csv");

        Matrix V_sets = new Matrix(val.readCSV());

        Matrix V_X = MinMaxScalar.minMaxScalar(V_sets.getColumnTo(0, 1).transpose());
        Matrix V_Y = V_sets.getColumnTo(2, 2);

        Network nn = new Network(
                new Layer(2, 4, new LeakyReLU(), Initializer.InitType.HE, 0.2),
                new Layer(4, 6, new LeakyReLU(), Initializer.InitType.HE, 0.5),
                new Layer(6, 6, new LeakyReLU(), Initializer.InitType.HE, 0.5),
                new Layer(6, 4, new LeakyReLU(), Initializer.InitType.HE, 0.5),
                new Layer(4, 1, new Sigmoid(), Initializer.InitType.XAVIER)
        );

        nn.compile(new AdamOptimizer(0.01, 0.9, 0.99, 1e-8), new BinaryCrossEntropyLoss(), new BinaryAccuracy());

        nn.train(X, Y, 10000, 100);
        Matrix res = nn.predict(V_X); // Prediction

        System.out.println("Prediction Accuracy : " + nn.getAccuracy(res, V_Y));

/*
        try {
            PrintWriter writer = new PrintWriter("pred.csv");
            writer.println("x1,x2,label,pred");

            for(int i=0;i<X.getColDim();i++) {
                double x1 = X.getArray()[0][i];
                double x2 = X.getArray()[1][i];


                double label = Y.getArray()[i][0];

                double pred = res.getArray()[0][i];

                writer.printf("%f,%f,%f,%f\n", x1, x2, label, pred);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            GridPredictor.predict(nn);
        } catch (Exception e) {
            e.printStackTrace();
        }

         */
    }
}
