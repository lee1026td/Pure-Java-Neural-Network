package filereader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    private int size;
    private String fileLocation;
    private String[] headers;

    private List<double[]> dataset;

    public CSVReader(String fileLocation) {
        this.fileLocation = fileLocation;
        dataset = new ArrayList<double[]>();
        size = -1;
    }

    public double[][] readCSV() {

        File csvFile = new File(fileLocation);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            String line;

            if((line = br.readLine()) != null) headers = line.split(",");

            size++;

            while((line = br.readLine()) != null) {
                String[] vals = line.split(",");
                double[] dVals = new double[vals.length];
                for(int i=0;i<vals.length;i++) {
                    dVals[i] = Double.parseDouble(vals[i]);
                }

                dataset.add(dVals);
                size++;
            }
        } catch (FileNotFoundException fe) {
            System.out.println(fileLocation + " : File Not Found");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        double[][] res = new double[size][headers.length];

        for(int i=0;i<size;i++) {
            res[i] = dataset.get(i);
        }

        return res;
    }

    public void print() {
        for(double[] arr : dataset) {
            for(double d : arr) {
                System.out.print(d + ", ");
            }
            System.out.println();
        }
    }
}
