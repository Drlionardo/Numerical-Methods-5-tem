package minimization;

import utils.Matrix;

public class Main {
    public static void main(String[] args) {
        final int N = 15;
        final double EPS = 10E-6;

        GradientDescent descent = new GradientDescent();
        Matrix a = new Matrix(new double[][]{
                {4, 1, 1},
                {1, 2 * (3 + 0.1 * N), -1},
                {1, -1, 2 * (4 + 0.1 * N)}});
        Matrix b = new Matrix(new double[][]{
                {1},
                {-2},
                {3}});

        descent.setA(a);
        descent.setB(b);
        descent.setConstant(N);

        descent.minimize(EPS);
    }
}
