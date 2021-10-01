package minimization;

import utils.Matrix;

public class CoordinateDescent extends Descent {

    public CoordinateDescent(Matrix a, Matrix b, double constant, double minEig) {
        super(a, b, constant, minEig);
    }

    @Override
    public void minimize(final double EPS) {
        checkDimensions();
        //Set start approximation with 0 vector
        Matrix xCurrent = new Matrix(a.getRowDimension(), 1);
        Matrix xPrev;
        int step = 0;
        System.out.printf("%8s %12s %12s %12s\n", "x", "y", "z", "f(x,y,z)");
        do {
            xPrev = xCurrent.copy();
            xCurrent = getNextX(xPrev, step);
            printResult(xCurrent);
            step = (step >= a.getRowDimension() - 1) ? 0 : step + 1;
        } while (getMinimizationError(xCurrent) > EPS);
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
}
