package utils;

public class Matrix {
    private double[][] array;
    private int rows, columns;

    public Matrix (int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        array = new double[rows][columns];
    }

    public Matrix (double[][] array) {
        rows = array.length;
        columns = array[0].length;
        for (int i = 0; i < rows; i++) {
            if (array[i].length != columns) {
                throw new IllegalArgumentException("All rows must have the same length.");
            }
        }
        this.array = array;
    }

    public Matrix (double[][] array, int rows, int columns) {
        this.array = array;
        this.rows = rows;
        this.columns = columns;
    }

    public Matrix copy () {
        Matrix X = new Matrix(rows, columns);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                C[i][j] = array[i][j];
            }
        }
        return X;
    }

    public double[][] getArray () {
        return array;
    }

    public double[][] getArrayCopy () {
        double[][] C = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                C[i][j] = array[i][j];
            }
        }
        return C;
    }

    public int getRowDimension () {
        return rows;
    }

    public int getColumnDimension () {
        return columns;
    }

    public double get (int i, int j) {
        return array[i][j];
    }

    public Matrix getMatrix (int[] r, int j0, int j1) {
        Matrix X = new Matrix(r.length,j1-j0+1);
        double[][] B = X.getArray();
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = j0; j <= j1; j++) {
                    B[i][j-j0] = array[r[i]][j];
                }
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    public Matrix getMatrix (int i0, int i1, int j0, int j1) {
        Matrix X = new Matrix(i1-i0+1,j1-j0+1);
        double[][] B = X.getArray();
        try {
            for (int i = i0; i <= i1; i++) {
                for (int j = j0; j <= j1; j++) {
                    B[i-i0][j-j0] = array[i][j];
                }
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }


    public void set (int i, int j, double s) {
        array[i][j] = s;
    }

    public Matrix transpose () {
        Matrix X = new Matrix(columns, rows);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                C[j][i] = array[i][j];
            }
        }
        return X;
    }


    public double normInf () {
        double f = 0;
        for (int i = 0; i < rows; i++) {
            double s = 0;
            for (int j = 0; j < columns; j++) {
                s += Math.abs(array[i][j]);
            }
            f = Math.max(f,s);
        }
        return f;
    }

    public double norm2 () {
        double norm = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                norm += array[i][j] * array[i][j];
            }
        }
        return norm;
    }

    public Matrix plus (Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(rows, columns);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                C[i][j] = array[i][j] + B.array[i][j];
            }
        }
        return X;
    }

    public Matrix minus (Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(rows, columns);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                C[i][j] = array[i][j] - B.array[i][j];
            }
        }
        return X;
    }

    public Matrix times (double s) {
        Matrix X = new Matrix(rows, columns);
        double[][] C = X.getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                C[i][j] = s* array[i][j];
            }
        }
        return X;
    }

    public Matrix times (Matrix B) {
        if (B.rows != columns) {
            throw new IllegalArgumentException("Matrix.Matrix inner dimensions must agree.");
        }
        Matrix X = new Matrix(rows,B.columns);
        double[][] C = X.getArray();
        double[] Bcolj = new double[columns];
        for (int j = 0; j < B.columns; j++) {
            for (int k = 0; k < columns; k++) {
                Bcolj[k] = B.array[k][j];
            }
            for (int i = 0; i < rows; i++) {
                double[] Arowi = array[i];
                double s = 0;
                for (int k = 0; k < columns; k++) {
                    s += Arowi[k]*Bcolj[k];
                }
                C[i][j] = s;
            }
        }
        return X;
    }

    public static Matrix identity (int m, int n) {
        Matrix A = new Matrix(m,n);
        double[][] X = A.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                X[i][j] = (i == j ? 1.0 : 0.0);
            }
        }
        return A;
    }

    private void checkMatrixDimensions (Matrix B) {
        if (B.rows != rows || B.columns != columns) {
            throw new IllegalArgumentException("Matrix.Matrix dimensions must agree.");
        }
    }
}