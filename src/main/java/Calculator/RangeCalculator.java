package Calculator;
import java.util.List;

public class RangeCalculator {

    public static double calculateRange(List<Double> sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Список выборки не может быть пустым");
        }

        double max = MaximumsAndMinimumsCalculator.calculateMaximum(sample);
        double min = MaximumsAndMinimumsCalculator.calculateMinimum(sample);

        return max - min;
    }
}
