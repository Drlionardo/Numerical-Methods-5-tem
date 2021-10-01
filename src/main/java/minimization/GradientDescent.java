package minimization;

import utils.Matrix;

public class GradientDescent extends Descent{

    public GradientDescent(Matrix a, Matrix b, double constant, double minEig) {
        super(a, b, constant, minEig);
    }

    public void minimize(final double EPS) {
        checkDimensions();
        //Set start approximation of minimum with 0 vector
        Matrix xCurrent = new Matrix(a.getRowDimension(), 1);
        Matrix xPrev;

        System.out.printf("%8s %12s %12s %12s\n","x","y","z","f(z,y,z)");
        do {
            xPrev = xCurrent.copy();
            xCurrent = getNextX(xPrev);
            printResult(xCurrent);
        } while (getMinimizationError(xCurrent) > EPS);
    }

    private Matrix getNextX(Matrix xPrev) {
        var q = a.times(xPrev).plus(b);
        var mu = -1 * q.norm2() / (q.transpose().times(a).times(q)).get(0, 0);
        return xPrev.plus(q.times(mu));
    }
}
