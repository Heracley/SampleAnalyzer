package SampleAnalyzer;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.StatUtils;

import java.util.ArrayList;
import java.util.List;


public class MeanConfidenceIntervalCalculator implements Calculator<double[], List<Double>> {
    private double[] result;

    public void calculate(List<Double> sample) {

        double mean = StatUtils.mean(sample.stream().mapToDouble(Double::doubleValue).toArray());
        double sd = StatUtils.variance((sample.stream().mapToDouble(Double::doubleValue).toArray()));
        int size = sample.size();
        double confidenceLevel = 0.95;
        NormalDistribution normalDistribution = new NormalDistribution();
        double quant = normalDistribution.inverseCumulativeProbability(1 - (1 - confidenceLevel) / 2);
        double marginOfError = quant * (sd / size);
        double lowerBound = mean - marginOfError;
        double upperBound = mean + marginOfError;

        result = new double[]{lowerBound, upperBound};
        }

    public double[] getResult() {
        return result;
    }

    public String getStringResult() {
        return "[" + String.format("%.4f", result[0]) + "; " + String.format("%.4f", result[1]) + "]";
    }
}
