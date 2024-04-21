package SampleAnalyzer;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.List;

public class StandardDeviationEstimator implements Calculator<Double, List<Double>>  {
    private Double result;

    public void calculate(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double[] array = sample.stream().mapToDouble(Double::doubleValue).toArray();
        DescriptiveStatistics stats = new DescriptiveStatistics(array);
        result = stats.getStandardDeviation();
    }

    public Double getResult() {
        return result;
    }

    public String getStringResult() {
        return result.toString();
    }


}
