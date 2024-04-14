package Calculator;
import java.util.List;

public class CoefficientOfVariationCalculator {

    public static double calculateCoefficientOfVariation(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double stdDev = StandardDeviationEstimator.estimateStandardDeviation(sample);
        double mean = ArithmeticMeanCalculator.calculateArithmeticMean(sample);

        return (stdDev / mean) * 100; // В процентах
    }
}
