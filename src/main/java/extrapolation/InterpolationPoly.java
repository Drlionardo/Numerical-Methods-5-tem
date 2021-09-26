package extrapolation;

import java.util.Arrays;
import java.util.function.Function;

public final class InterpolationPoly {
    private InterpolationPoly() {
    }

    public static Function<Double, Double> build(double[] x, double[] y) {
        return t -> {
            double value = 0.0;
            double[] l = new double[x.length];
            Arrays.fill(l, 1);
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x.length; j++) {
                    if (i != j) {
                        l[i] *= (t - x[j]) / (x[i] - x[j]);
                    }
                }
                value += l[i] * y[i];
            }
            return value;
        };
    }
}
