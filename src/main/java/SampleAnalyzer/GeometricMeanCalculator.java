package SampleAnalyzer;

import org.apache.commons.math3.stat.StatUtils;

import java.util.Arrays;
import java.util.List;

public class GeometricMeanCalculator implements Calculator<Double, List<Double>> {
    private Double result;

    public void calculate(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double[] array = new double[sample.size()];
        for (int i = 0; i < sample.size(); i++) {
            array[i] = sample.get(i);
        }

        result = StatUtils.geometricMean(array);
    }

    public Double getResult() {
        return result;
    }

    public String getStringResult() {
        return result.toString();
    }
}
