package Calculator;

import org.apache.commons.math3.stat.StatUtils;

import java.util.List;

public class GeometricMeanCalculator {

    public static double calculateGeometricMean(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double[] array = sample.stream().mapToDouble(Double::doubleValue).toArray();
        return StatUtils.geometricMean(array);
    }
}
