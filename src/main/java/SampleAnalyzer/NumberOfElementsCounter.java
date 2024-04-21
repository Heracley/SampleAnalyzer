package SampleAnalyzer;
import java.util.List;

public class NumberOfElementsCounter implements Calculator<Integer, List<Double>>  {
    private Integer result;

    public void calculate(List<Double> sample) {
        if (sample == null) {
            result = 0;
        }

        result = sample.size();
    }

    public Integer getResult() {
        return result;
    }

    public String getStringResult() {
        return result.toString();
    }

}
