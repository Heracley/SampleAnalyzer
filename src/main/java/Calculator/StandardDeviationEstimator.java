package Calculator;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.List;

public class StandardDeviationEstimator {

    public static double estimateStandardDeviation(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double[] array = sample.stream().mapToDouble(Double::doubleValue).toArray();
        DescriptiveStatistics stats = new DescriptiveStatistics(array);
        return stats.getStandardDeviation();
    }
}
