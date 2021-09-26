package extrapolation;

import java.util.function.Function;

public class InterpolationExample {
    static final double EPS = 10E-4;

    public static void main(String[] args) {
        final int N = 10; // nodes number for Interp. poly
        final int M = 30; // nodes number for values printing
        final Function<Double,Double> F = x -> 1 / Math.tan(x) - x;
        final double LEFT = EPS;
        final double RIGHT = Math.PI - EPS;

        double[] equalNodes = getEqualNodes(LEFT, RIGHT, N);
        double[] equalNodesValues = getFunctionValues(equalNodes, F);
        var equalPoly = InterpolationPoly.build(equalNodes, equalNodesValues);

        double[] optimalNodes = getOptimalNodes(LEFT, RIGHT, N);
        double[] optimalNodesValues = getFunctionValues(optimalNodes, F);
        var optimalPoly = InterpolationPoly.build(optimalNodes, optimalNodesValues);

        double[] x = getEqualNodes(LEFT, RIGHT, M);
        for(double t : x) {
            double equalPolyVal = equalPoly.apply(t);
            double optimalPolyVal = optimalPoly.apply(t);
            double functionVal = F.apply(t);
            double equalDifference = Math.abs(functionVal - equalPolyVal);
            double optimalDifference = Math.abs(functionVal - optimalPolyVal);
            System.out.printf("x = %.8f  ->  P:=%.8f f:=%.8f equalDifference: %.8f OptimalDifference: %.8f\n",
                    t, equalPolyVal, functionVal, equalDifference, optimalDifference);
        }
    }

    private static double[] getFunctionValues(double[] x, Function<Double, Double> f) {
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = f.apply(x[i]);
        }
        return y;
    }

    private static double[] getOptimalNodes(double a, double b, int n) {
        double[] nodes = new double[n];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = 0.5 * ((b - a) * Math.cos(Math.PI * (2 * i + 1.0) / (2 * (n + 1))) + b + a);
        }
        return nodes;
    }

    private static double[] getEqualNodes(double a, double b, int n) {
        double[] nodes = new double[n];
        nodes[0] = a;
        double step = (b-a) / (n-1);
        for (int i = 1; i < n; i++) {
            nodes[i] = nodes[i-1] + step;
        }
        return nodes;
    }
}
