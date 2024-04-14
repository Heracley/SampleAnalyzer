package Calculator;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.List;

public class MeanConfidenceIntervalCalculator {

    public static double[] calculateMeanConfidenceInterval(List<Double> sample, double confidenceLevel) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double mean = ArithmeticMeanCalculator.calculateArithmeticMean(sample);
        double stdDev = StandardDeviationEstimator.estimateStandardDeviation(sample);
        int n = NumberOfElementsCounter.countNumberOfElements(sample);

        // Вычисление доверительного интервала
        double marginOfError = calculateMarginOfError(stdDev, n, confidenceLevel);
        double[] confidenceInterval = new double[2];
        confidenceInterval[0] = mean - marginOfError;
        confidenceInterval[1] = mean + marginOfError;

        return confidenceInterval;
    }

    private static double calculateMarginOfError(double stdDev, int n, double confidenceLevel) {
        // Расчет стандартной ошибки среднего
        double standardError = stdDev / Math.sqrt(n);

        // Нахождение критического значения z
        NormalDistribution normalDistribution = new NormalDistribution();
        double z = normalDistribution.inverseCumulativeProbability(1 - (1 - confidenceLevel) / 2);

        // Расчет предельной ошибки
        return z * standardError;
    }
}
