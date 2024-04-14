package Calculator;
import java.util.List;

public class MaximumsAndMinimumsCalculator {

    public static double calculateMaximum(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double max = Double.MIN_VALUE;
        for (double value : sample) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    public static double calculateMinimum(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double min = Double.MAX_VALUE;
        for (double value : sample) {
            if (value < min) {
                min = value;
            }
        }

        return min;
    }
}
