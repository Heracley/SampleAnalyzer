import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import ExcelTools.ExcelReader;


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
    private JLabel maximumsAndMinimums;
    private JLabel sampleLabel;
    private File file;

    public Window() {
        setContentPane(panel);
        setTitle("Sample Analyzer");
        setSize(500, 500);

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
                    file = fileChooser.getSelectedFile();
                    chosenLabel.setText("Выбран датасет: " + file.getName());

                    loadSheetNamesIntoComboBox(file);
                    loadSampleNamesIntoComboBox(file);
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
                loadSampleNamesIntoComboBox(file);
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, List<Double>> sample = ExcelReader.getSample(
                        file,
                        (String) chooseSheetComboBox.getSelectedItem(),
                        (String) chooseSampleComboBox.getSelectedItem()
                );
            }
        });
    }

    private void loadSheetNamesIntoComboBox(File file) {
        List<String> sheetNames = ExcelReader.getSheetNames(file);
        if (!sheetNames.isEmpty()) {
            chooseSheetComboBox.removeAllItems();
            for (String sheetName : sheetNames) {
                chooseSheetComboBox.addItem(sheetName);
            }
            chooseSheetComboBox.setEnabled(true);
        }
    }

    private void loadSampleNamesIntoComboBox(File file) {
        List<String> sampleNames = ExcelReader.getSampleNames(file, (String) chooseSheetComboBox.getSelectedItem());
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



    public static void main(String[] args) {
        Window window = new Window();
    }
}

