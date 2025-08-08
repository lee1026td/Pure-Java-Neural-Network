package datastruct;

public class Matrix {
    private double[][] elements;
    private int rowDim;
    private int colDim;

    public Matrix(int rowDim, int colDim) {
        this.rowDim = rowDim;
        this.colDim = colDim;
        elements = new double[rowDim][colDim];
    }

    public Matrix(double[][] elements) {
        this.elements = elements;
        rowDim = elements.length;
        colDim = elements[0].length;
    }

    public Matrix addMat(Matrix mat) {
        Matrix res = new Matrix(rowDim, colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.elements[i][j] = elements[i][j] + mat.elements[i][j];
            }
        }

        return res;
    }

    public Matrix subMat(Matrix mat) {
        Matrix res = new Matrix(rowDim, colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.elements[i][j] = elements[i][j] - mat.elements[i][j];
            }
        }

        return res;
    }

    public Matrix addScalar(double scalar) {
        Matrix res = new Matrix(rowDim, colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.elements[i][j] = elements[i][j] + scalar;
            }
        }

        return res;
    }

    public Matrix subScalar(double scalar) {
        return addScalar(-scalar);
    }

    public void addInPlace(Matrix mat) {
        subInPlace(mat.mulScalar(-1));
    }

    public void subInPlace(Matrix mat) {
        for (int i=0;i<rowDim;i++) {
            for (int j=0;j<colDim;j++) {
                this.elements[i][j] -= mat.getArray()[i][j];
            }
        }
    }

    public Matrix mulMat(Matrix mat) {
        Matrix res = new Matrix(rowDim, mat.colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<mat.colDim;j++) {
                for(int k=0;k<colDim;k++) {
                    res.elements[i][j] += elements[i][k] * mat.elements[k][j];
                }
            }
        }

        return res;
    }

    public Matrix mulScalar(double scalar) {
        Matrix res = new Matrix(rowDim, colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.elements[i][j] = elements[i][j] * scalar;
            }
        }

        return res;
    }

    public Matrix mulHadamard(Matrix mat) {
        Matrix res = new Matrix(rowDim, colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.elements[i][j] = elements[i][j] * mat.elements[i][j];
            }
        }

        return res;
    }

    public Matrix divHadamard(Matrix mat) {
        Matrix res = new Matrix(this.rowDim, this.colDim);
        for (int i = 0; i < rowDim; i++) {
            for (int j = 0; j < colDim; j++) {
                double denom = mat.getArray()[i][j];
                double numer = elements[i][j];

                if (Double.isNaN(denom) || denom == 0.0 || Double.isInfinite(denom)) {
                    res.elements[i][j] = 0.0;
                } else {
                    res.elements[i][j] = numer / denom;
                }
            }
        }
        return res;
    }

    public Matrix sqrt() {
        Matrix res = new Matrix(rowDim, colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.elements[i][j] = Math.sqrt(elements[i][j]);
            }
        }
        return res;
    }

    public Matrix inverseElements() {
        Matrix res = new Matrix(rowDim, colDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.elements[i][j] = 1.0 / res.elements[i][j];
            }
        }

        return res;
    }

    public Matrix transpose() {
        Matrix res = new Matrix(colDim, rowDim);

        for(int i=0;i<rowDim;i++) {
            for(int j=0;j<colDim;j++) {
                res.elements[j][i] = elements[i][j];
            }
        }

        return res;
    }

    public double sum() {
        assert this.colDim == 1;

        double sum = 0.0;
        for(int i=0;i<rowDim;i++) {
            sum += elements[i][0];
        }

        return sum;
    }

    public double getRowMax(int row) {
        double max = Double.MIN_VALUE;
        for(double d : elements[row]) {
            if(d > max) max = d;
        }

        return max;
    }

    public Matrix getColumnTo(int startCol, int endCol) {
        int ncol = endCol - startCol + 1;

        double[][] res = new double[rowDim][ncol];

        if(ncol <= colDim) {

            for(int i=0;i<rowDim;i++) {
                int index = 0;
                for(int j=startCol;j<=endCol;j++) {
                    res[i][index++] = elements[i][j];
                }
            }
            return new Matrix(res);
        } else {
            return null;
        }
    }

    public Matrix padCol(int colSize) {
        double[][] padded = new double[this.rowDim][colSize];
        for (int i = 0; i < this.rowDim; i++) {
            double b = this.getArray()[i][0];
            for (int j = 0; j < colSize; j++) {
                padded[i][j] = b;
            }
        }
        return new Matrix(padded);
    }

    public Matrix sumCols() {
        double[][] result = new double[rowDim][1];

        for(int i = 0; i < rowDim; i++) {
            double sum = 0;
            for(int j = 0; j < colDim; j++) {
                sum += elements[i][j];
            }
            result[i][0] = sum;
        }

        return new Matrix(result);
    }

    public double[][] getArray() {
        return elements;
    }

    public int getRowDim() {
        return rowDim;
    }

    public int getColDim() {
        return colDim;
    }

    public void printMatrix() {
        for(double[] arr : elements) {
            for(double d : arr) {
                System.out.print(d + ", ");
            }
            System.out.println();
        }
    }

    public String shape() {
        return "[" + rowDim + ", " + colDim + "]";
    }
}
