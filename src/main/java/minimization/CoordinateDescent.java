package minimization;

import utils.Matrix;

public class CoordinateDescent {
    private Matrix a;
    private Matrix b;
    private double constant;


    public void setA(Matrix a) {
        this.a = a;
    }

    public void setB(Matrix b) {
        this.b = b;
    }

    public void setConstant(double constant) {
        this.constant = constant;
    }

    public double function(Matrix x) {
        return (x.times(0.5).transpose().times(a).times(x)).plus((x.transpose().times(b))).get(0, 0) + constant;
    }

    public void minimize(final double EPS) {
        checkDimensions();
        //Set start approximation of minimum with 0 vector
        Matrix xCurrent = new Matrix(a.getRowDimension(), 1);
        Matrix xPrev;
        int step = 0;
        do {
            xPrev = xCurrent.copy();
            xCurrent = getNextX(xPrev, step);
            printResult(xCurrent);
            step = (step >= a.getRowDimension() - 1) ? 0 : step + 1;

        } while (Math.sqrt((xCurrent.minus(xPrev).norm2())) > EPS);
    }

    private double getDistance(Matrix xPrev, Matrix xCurrent) {
        return Math.sqrt((xCurrent.minus(xPrev).norm2()));
    }

    private Matrix getNextX(Matrix xPrev, int step) {
        var ort = ortI(step);
        var t1 = (ort.transpose()).times(a.times(xPrev).plus(b)).get(0, 0);
        var t2 = (ort.transpose()).times(a.times(ort)).get(0, 0);
        var mu = -1 * t1 / t2;
        return xPrev.plus(ort.times(mu));
    }

    private Matrix ortI(int i) {
        Matrix qI = new Matrix(a.getRowDimension(), 1);
        qI.set(i, 0, 1);
        return qI;
    }

    private void printResult(Matrix x) {
        double result = function(x);
        var array = x.getArray();
        StringBuilder xString = new StringBuilder();
        for (double[] doubles : array) {
            xString.append(doubles[0]).append(" ");
        }
        xString.append("-> ").append(result);
        System.out.println(xString);
    }

    private void checkDimensions() {
        if (a.getColumnDimension() != a.getRowDimension()) {
            throw new IllegalArgumentException("Matrix 'a' must be square");
        }
        if (b.getColumnDimension() != 1) {
            throw new IllegalArgumentException("Matrix 'b' must be a Nx1 vector");
        }
        if (a.getRowDimension() != b.getRowDimension()) {
            throw new IllegalArgumentException("Incompatible matrices 'a' and 'b' dimensions. Expected NxN , Nx1");
        }
    }
}
