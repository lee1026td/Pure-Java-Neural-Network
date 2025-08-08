
import datastruct.Matrix;
import net.Network;

import java.io.*;

public class GridPredictor {
    public static void predict(Network network) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("G:\\PYTHON\\grid.csv"));
        PrintWriter writer = new PrintWriter(new FileWriter("grid_pred.csv"));

        writer.println("x1,x2,pred");  // CSV header

        String line = reader.readLine(); // Skip header line

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length < 2) continue;

            double x1 = Double.parseDouble(tokens[0]);
            double x2 = Double.parseDouble(tokens[1]);

            Matrix input = new Matrix(new double[][]{{x1, x2}}).transpose();
            Matrix output = network.predict(input);
            double pred = output.getArray()[0][0];  // sigmoid 확률값

            writer.printf("%f,%f,%f\n", x1, x2, pred);
        }

        reader.close();
        writer.close();
    }
}
