package minimization;

import utils.Matrix;

public abstract class Descent {
    protected Matrix a;
    protected Matrix b;
    protected double constant;
    protected double minEig; //минимальное собственное число Matrix 'a' для оценки погрешности

    public Descent(Matrix a, Matrix b, double constant, double minEig) {
        this.a = a;
        this.b = b;
        this.constant = constant;
        this.minEig = minEig;
    }

    public void setA(Matrix a) {
        this.a = a;
    }

    public void setB(Matrix b) {
        this.b = b;
    }

    public void setConstant(double constant) {
        this.constant = constant;
    }

    public double getMinEig() {
        return minEig;
    }

    public void setMinEig(double minEig) {
        this.minEig = minEig;
    }

    public void minimize(final double EPS) {
    }

    double function(Matrix x) {
        return (x.times(0.5).transpose().times(a).times(x)).plus((x.transpose().times(b))).get(0, 0) + constant;
    }

    double getMinimizationError(Matrix xCurrent) {
        double m = 3.267; //TODO: заменить методом нахождения мин. собственного числа
        return (a.times(xCurrent).plus(b)).norm2() * (1 / m);
    }

    void printResult(Matrix x) {
        double result = function(x);
        var array = x.getArray();
        System.out.printf("%8.8f %12.8f %12.8f %12.8f\n",array[0][0], array[1][0], array[2][0], result);
    }

    void checkDimensions() {
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
