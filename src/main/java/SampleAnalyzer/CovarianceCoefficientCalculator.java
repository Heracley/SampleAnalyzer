package SampleAnalyzer;

import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.ArrayList;
import java.util.List;

public class CovarianceCoefficientCalculator implements Calculator<List<List<Double>>, List<List<Double>>>  {
    private List<List<Double>> result;
    public void calculate(List<List<Double>> samples) {

        List<List<Double>> covarianceMatrix = new ArrayList<>();

        Covariance covariance = new Covariance();
        for (List<Double> sample1: samples) {
            List<Double> row = new ArrayList<>();
            for (List<Double> sample2: samples) {
                row.add(
                    covariance.covariance(
                        sample1.stream().mapToDouble(Double::doubleValue).toArray(),
                        sample2.stream().mapToDouble(Double::doubleValue).toArray()
                    )
                );
            }
            covarianceMatrix.add(row);
        }

        result = covarianceMatrix;
    }

    public List<List<Double>> getResult() {
        return result;
    }

    public String getStringResult() {
        StringBuilder matrixString = new StringBuilder();
        for (List<Double> row : result) {
            for (Double value : row) {
                matrixString.append(String.format("%.4f", value)).append("\t");
            }
            matrixString.append("\n");
        }

        return matrixString.toString();
    }
}
