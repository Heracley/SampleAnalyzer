package SampleAnalyzer;
import java.util.List;

public class CoefficientOfVariationCalculator implements Calculator<Double, List<Double>>  {
    private Double result;

    public void calculate(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        StandardDeviationEstimator standardDeviationEstimator = new StandardDeviationEstimator();
        ArithmeticMeanCalculator arithmeticMeanCalculator = new ArithmeticMeanCalculator();

        standardDeviationEstimator.calculate(sample);
        double stdDev = standardDeviationEstimator.getResult();

        arithmeticMeanCalculator.calculate(sample);
        double mean = arithmeticMeanCalculator.getResult();

        result =  (stdDev / mean) * 100; // В процентах
    }

    public Double getResult() {
        return result;
    }

    public String getStringResult() {
        return result.toString();
    }
}
