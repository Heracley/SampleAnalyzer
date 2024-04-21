import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SampleAnalyzer.SampleAnalyzer;


public class Window extends JFrame {

    private JButton chooseButtom;
    private JComboBox chooseSheetComboBox;
    private JPanel panel;
    private JLabel chosenLabel;
    private JButton calculateButton;
    private JComboBox chooseSampleComboBox;
    private JLabel geometricMean;
    private JLabel arithmeticMean;
    private JLabel standardDeviation;
    private JLabel range;
    private JLabel covarianceCoefficient;
    private JLabel numberOfElements;
    private JLabel coefficientOfVariation;
    private JLabel meanConfidenceInterval;
    private JLabel varianceEstimate;
    private JLabel minimus;
    private JLabel maximus;
    private JLabel sampleLabel;
    private JButton exportButton;
    private JPanel calculatePanel;
    private JPanel metricPanel;
    private HashMap<String, String> analysis;
    private final SampleAnalyzer sampleAnalyzer = new SampleAnalyzer();

    public Window() {
        setContentPane(panel);
        setTitle("Sample Analyzer");
        setSize(550, 500);

        addListeners();

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void addListeners() {
        chooseButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("Файлы Excel (*.xlsx)", "xlsx");
                fileChooser.setFileFilter(filter);

                File defaultDirectory = new File("./");
                fileChooser.setCurrentDirectory(defaultDirectory);

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    sampleAnalyzer.loadData(fileChooser.getSelectedFile());
                    chosenLabel.setText("Выбран датасет: " + sampleAnalyzer.getFileName());

                    loadSheetNamesIntoComboBox();
                    loadSampleNamesIntoComboBox();
                }
            }
        });

        chooseSheetComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseSampleComboBox.removeAllItems();
                chooseSampleComboBox.setEnabled(false);
                sampleLabel.setEnabled(false);
                calculateButton.setEnabled(false);
                exportButton.setEnabled(false);
                clearMetrics();
                loadSampleNamesIntoComboBox();
            }
        });

        chooseSampleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMetrics();
                String sampleName = (String) chooseSampleComboBox.getSelectedItem();
                exportButton.setEnabled(false);
                if (sampleName != null) {
                    sampleAnalyzer.setSampleName(sampleName);
                }

            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analysis = sampleAnalyzer.getStringAnalysis();
                // Проходимся по каждой записи в HashMap
                for (Map.Entry<String, String> entry : analysis.entrySet()) {
                    String fieldName = entry.getKey();
                    String value = entry.getValue();

                    // Получаем ссылку на поле JLabel по его имени
                    try {
                        Field field = Window.class.getDeclaredField(fieldName);
                        JLabel label = (JLabel) field.get(Window.this);

                        if (fieldName.equals("covarianceCoefficient")) {
                            label.setText("Показать матрицу");
                        } else {
                            label.setText(value);
                        }

                    } catch (NoSuchFieldException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }

                exportButton.setEnabled(true);
            }
        });

        covarianceCoefficient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!covarianceCoefficient.getText().toString().equals("-")) {
                    String matrixString = analysis.get("covarianceCoefficient");
                    JTextArea textArea = new JTextArea(matrixString);
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(Window.this, scrollPane, "Матрица ковариации", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Создаем диалоговое окно выбора папки
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберите папку для сохранения файла");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    String selectedPath = selectedDirectory.getAbsolutePath();
                    sampleAnalyzer.saveAnalysis(selectedPath);
                    JOptionPane.showMessageDialog(Window.this, "Файл успешно сохранен по пути: " + selectedPath + File.separator + "analysis.xlsx");
                }
            }
        });
    }

    private void loadSheetNamesIntoComboBox() {
        List<String> sheetNames = sampleAnalyzer.getSheetNames();
        if (!sheetNames.isEmpty()) {
            chooseSheetComboBox.removeAllItems();
            for (String sheetName : sheetNames) {
                chooseSheetComboBox.addItem(sheetName);
            }
            chooseSheetComboBox.setEnabled(true);
        }
    }

    private void loadSampleNamesIntoComboBox() {
        sampleAnalyzer.setSheetName((String) chooseSheetComboBox.getSelectedItem());
        List<String> sampleNames = sampleAnalyzer.getSampleNames();
        if (!sampleNames.isEmpty()) {
            chooseSampleComboBox.removeAllItems();
            for (String sheetName : sampleNames) {
                chooseSampleComboBox.addItem(sheetName);
            }
            chooseSampleComboBox.setEnabled(true);
            sampleLabel.setEnabled(true);
            calculateButton.setEnabled(true);
        }
    }

    private void clearMetrics() {
        for (String metricName: sampleAnalyzer.getMetrics()) {
            try {
                Field field = Window.class.getDeclaredField(metricName);
                JLabel label = (JLabel) field.get(Window.this); // Window.this используется, так как поля принадлежат классу Window
                label.setText("-");
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        Window window = new Window();
    }
}

