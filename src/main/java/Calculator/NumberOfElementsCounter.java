package Calculator;
import java.util.List;

public class NumberOfElementsCounter {

    public static int countNumberOfElements(List<Double> sample) {
        if (sample == null) {
            return 0;
        }

        return sample.size();
    }
}
