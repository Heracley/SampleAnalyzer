package SampleAnalyzer;

import ExcelTools.ExcelReader;
import ExcelTools.ExcelWriter;
import org.apache.poi.ss.usermodel.CellType;

import java.io.File;
import java.util.*;


public class SampleAnalyzer {
    private File file;
    private String sheetName;
    private String sampleName;
    private final Map<String, Calculator<?, ?>> metricCalculators = new HashMap<>() {
        {
            put("geometricMean", new GeometricMeanCalculator());
            put("arithmeticMean", new ArithmeticMeanCalculator());
            put("standardDeviation", new StandardDeviationEstimator());
            put("range", new RangeCalculator());
            put("covarianceCoefficient", new CovarianceCoefficientCalculator()); //
            put("numberOfElements", new NumberOfElementsCounter()); //
            put("coefficientOfVariation", new CoefficientOfVariationCalculator());
            put("meanConfidenceInterval", new MeanConfidenceIntervalCalculator()); //
            put("varianceEstimate", new VarianceEstimator());
            put("maximumsAndMinimums", new MaximumsAndMinimumsCalculator()); //
        }
    };

    public HashMap<String, String> getStringAnalysis() {
        HashMap<String, String> analysis = new HashMap<>();

        for (String metricName : metricCalculators.keySet()) {
            Calculator<?, ?> calculator = metricCalculators.get(metricName);
            switch (metricName) {
                case "covarianceCoefficient" -> {
                    ((CovarianceCoefficientCalculator) calculator).calculate(getAllSamples());
                    analysis.put(metricName, calculator.getStringResult());
                }
                case "maximumsAndMinimums" -> {
                    ((MaximumsAndMinimumsCalculator) calculator).calculate(getSample());
                    MaximumsAndMinimumsCalculator.MinMax minMax = (MaximumsAndMinimumsCalculator.MinMax) calculator.getResult();
                    analysis.put("maximus", String.valueOf(minMax.getMax()));
                    analysis.put("minimus", String.valueOf(minMax.getMin()));
                }
                default -> {
                    ((Calculator<?, List<Double>>) calculator).calculate(getSample());
                    analysis.put(metricName, calculator.getStringResult());
                }
            }
        }

        return analysis;
    }

    public void saveAnalysis(String path) {
        ExcelWriter excelWriter = new ExcelWriter();

        for (String metricName : metricCalculators.keySet()) {
            Calculator<?, ?> calculator = metricCalculators.get(metricName);
            switch (metricName) {
                case "covarianceCoefficient" -> {
                    ((CovarianceCoefficientCalculator) calculator).calculate(getAllSamples());
                    excelWriter.writeRow("", "");
                    excelWriter.writeRow("Covariance Matrix " + getSampleNames().toString(), "");
                    excelWriter.writeMatrix(((CovarianceCoefficientCalculator) calculator).getResult());
                    excelWriter.writeRow("", "");
                }
                case "maximumsAndMinimums" -> {
                    ((MaximumsAndMinimumsCalculator) calculator).calculate(getSample());
                    MaximumsAndMinimumsCalculator.MinMax minMax = (MaximumsAndMinimumsCalculator.MinMax) calculator.getResult();
                    excelWriter.writeRow("maximus", minMax.getMax(), CellType.NUMERIC);
                    excelWriter.writeRow("minimus", minMax.getMin(), CellType.NUMERIC);
                }
                case "meanConfidenceInterval" -> {
                    ((Calculator<?, List<Double>>) calculator).calculate(getSample());
                    excelWriter.writeRow(metricName, calculator.getStringResult());
                }
                default -> {
                    ((Calculator<?, List<Double>>) calculator).calculate(getSample());
                    excelWriter.writeRow(metricName, calculator.getResult(), CellType.NUMERIC);
                }
            }
        }

        excelWriter.saveFile(path + File.separator + "analysis.xlsx");
    }

    public void setSampleName(String sampleName) {
        if (file == null || sheetName == null) {
            throw new IllegalStateException("Не загружены данные файла или не установлено имя листа.");
        }
        List<String> sampleNames = getSampleNames();
        if (!sampleNames.contains(sampleName)) {
            throw new IllegalArgumentException("Указанное имя выборки не найдено в листе.");
        }
        this.sampleName = sampleName;
    }

    public void setSheetName(String sheetName) {
        if (file == null) {
            throw new IllegalStateException("Не загружены данные файла.");
        }
        List<String> sheetNames = getSheetNames();
        if (!sheetNames.contains(sheetName)) {
            throw new IllegalArgumentException("Указанное имя листа не найдено в файле.");
        }
        this.sheetName = sheetName;
    }

    public List<Double> getSample() {
        if (file == null || sheetName == null || sampleName == null) {
            throw new IllegalStateException("Не загружены данные файла или не установлены имена листа и выборки.");
        }
        return ExcelReader.getColumnDataByHead(file, sheetName, sampleName);
    }

    public List<Double> getSample(String name) {
        if (file == null || sheetName == null) {
            throw new IllegalStateException("Не загружены данные файла или не установлены имена листа и выборки.");
        }
        return ExcelReader.getColumnDataByHead(file, sheetName, name);
    }

    public List<List<Double>> getAllSamples() {
        List<List<Double>> samples = new ArrayList<>();
        if (file == null || sheetName == null) {
            throw new IllegalStateException("Не загружены данные файла или не установлено имя листа.");
        }

        for (String sampleName: getSampleNames()) {
            samples.add(getSample(sampleName));
        }
        return samples;
    }

    public List<String> getSampleNames() {
        if (file == null || sheetName == null) {
            throw new IllegalStateException("Не загружены данные файла или не установлено имя листа.");
        }
        return ExcelReader.getHeads(file, sheetName);
    }

    public List<String> getSheetNames() {
        if (file == null) {
            throw new IllegalStateException("Не загружены данные файла.");
        }
        return ExcelReader.getSheetNames(file);
    }

    public String getFileName() {
        if (file == null) {
            throw new IllegalStateException("Не загружены данные файла.");
        }
        return file.getName();
    }

    public Set<String> getMetrics() {
        Set<String> metrics = new HashSet<>(metricCalculators.keySet());
        metrics.remove("maximumsAndMinimums");
        metrics.add("maximus");
        metrics.add("minimus");
        return metrics;
    }

    public void loadData(File file) {
        this.file = file;
    }
}
