package ExcelTools;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {
    private Workbook workbook;
    private Sheet sheet;
    private int rowNum;

    public ExcelWriter() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("analysis");
        rowNum = 0;
    }

    public void writeRow(String name, Object value, CellType cellType) {
        Row row = sheet.createRow(rowNum++);
        Cell nameCell = row.createCell(0);
        nameCell.setCellValue(name);
        Cell valueCell = row.createCell(1);
        if (cellType == CellType.NUMERIC) {
            valueCell.setCellValue(Double.parseDouble(value.toString()));
        } else {
            valueCell.setCellValue((String) value);
        }
    }

    public void writeRow(String name, Object value) {
        writeRow(name, value, CellType.STRING); // По умолчанию тип данных - строковый
    }

    public void writeMatrix(List<List<Double>> matrix) {
        for (List<Double> rowValues : matrix) {
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            for (Double value : rowValues) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(value);
            }
        }
    }

    public void saveFile(String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }
}
