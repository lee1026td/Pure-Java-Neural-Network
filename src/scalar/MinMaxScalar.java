package scalar;

import datastruct.Matrix;

public class MinMaxScalar {
    public static Matrix minMaxScalar(Matrix mat) {
        int rows = mat.getRowDim();
        int cols = mat.getColDim();

        double[][] res = new double[rows][cols];

        double[] min = new double[rows];
        double[] max = new double[rows];


        for(int i=0;i<rows;i++) {
            min[i] = Double.MAX_VALUE;
            max[i] = -Double.MAX_VALUE;

            for(int j=0;j<cols;j++) {
                double val = mat.getArray()[i][j];

                if(val < min[i]) min[i] = val;
                if(val > max[i]) max[i] = val;
            }

            //System.out.println("feature " + i + " : min = " + min[i] + ", max = " + max[i]);
        }

        for(int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {
                double numerator = mat.getArray()[i][j] - min[i];
                double denominator = max[i] - min[i];

                res[i][j] = numerator / denominator;
            }
        }

        return new Matrix(res);
    }

}
