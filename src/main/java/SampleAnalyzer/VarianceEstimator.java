package SampleAnalyzer;

import org.apache.commons.math3.stat.StatUtils;

import java.util.List;

public class VarianceEstimator implements Calculator<Double, List<Double>>  {
    private Double result;

    public void calculate(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double[] array = sample.stream().mapToDouble(Double::doubleValue).toArray();
        result = StatUtils.variance(array);
    }

    public Double getResult() {
        return result;
    }

    public String getStringResult() {
        return result.toString();
    }
}
