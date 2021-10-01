package minimization;

import utils.Matrix;

public class MinimizationExample {
    public static void main(String[] args) {
        final int N = 15;
        final double EPS = 10E-6;
        double minEig = 3.618;

        Matrix a = new Matrix(new double[][]{
                {4, 1, 1},
                {1, 2 * (3 + 0.1 * N), -1},
                {1, -1, 2 * (4 + 0.1 * N)}});
        Matrix b = new Matrix(new double[][]{
                {1},
                {-2},
                {3}});

        GradientDescent gradientDescent = new GradientDescent(a, b, N, minEig);
        System.out.println("GradientDescend:");
        gradientDescent.minimize(EPS);

        CoordinateDescent coordinateDescent = new CoordinateDescent(a, b, N, minEig);
        System.out.println("CoordinateDescent");
        coordinateDescent.minimize(EPS);

    }
}
