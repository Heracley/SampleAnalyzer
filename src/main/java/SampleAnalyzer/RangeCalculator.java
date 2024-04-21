package SampleAnalyzer;
import java.util.List;


public class RangeCalculator implements Calculator<Double, List<Double>>  {
    private Double result;

    public void calculate(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        MaximumsAndMinimumsCalculator maximumsAndMinimumsCalculator = new MaximumsAndMinimumsCalculator();
        maximumsAndMinimumsCalculator.calculate(sample);

        MaximumsAndMinimumsCalculator.MinMax minMax = maximumsAndMinimumsCalculator.getResult();
        double max = minMax.getMax();
        double min = minMax.getMin();

        result = max - min;
    }

    public Double getResult() {
        return result;
    }

    public String getStringResult() {
        return result.toString();
    }
}
