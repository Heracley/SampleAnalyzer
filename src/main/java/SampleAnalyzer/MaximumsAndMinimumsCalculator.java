package SampleAnalyzer;
import java.util.List;

public class MaximumsAndMinimumsCalculator implements Calculator<MaximumsAndMinimumsCalculator.MinMax, List<Double>>  {
    private MinMax result;

    public static class MinMax {
        private double min;
        private double max;

        public MinMax(double min, double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String toString() {
            return "min: " + min + "\nmax: " + max;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }
    }

    public void calculate(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (double value : sample) {
            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }
        }

        result = new MinMax(min, max);
    }

    public MinMax getResult() {
        return result;
    }

    public String getStringResult() {
        return result.toString();
    }
}
