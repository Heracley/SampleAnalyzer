package Calculator;

import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.ArrayList;
import java.util.List;

public class CovarianceCoefficientCalculator {

    public static List<Double> calculateCovarianceCoefficients(List<List<Double>> samples) {
        if (samples == null || samples.isEmpty() || samples.size() < 2) {
            throw new IllegalArgumentException("Список выборок должен содержать как минимум две выборки");
        }

        List<Double> covarianceCoefficients = new ArrayList<>();

        int numSamples = samples.size();
        double[][] data = new double[numSamples][];

        for (int i = 0; i < numSamples; i++) {
            List<Double> sample = samples.get(i);
            if (sample == null || sample.isEmpty()) {
                throw new IllegalArgumentException("Каждая выборка должна содержать как минимум одно значение");
            }
            data[i] = sample.stream().mapToDouble(Double::doubleValue).toArray();
        }

        Covariance covariance = new Covariance(data);
        double[][] covarianceMatrix = covariance.getCovarianceMatrix().getData();

        for (int i = 0; i < covarianceMatrix.length; i++) {
            for (int j = i + 1; j < covarianceMatrix[i].length; j++) {
                covarianceCoefficients.add(covarianceMatrix[i][j]);
            }
        }

        return covarianceCoefficients;
    }
}
