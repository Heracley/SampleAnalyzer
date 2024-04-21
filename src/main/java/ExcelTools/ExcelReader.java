package ExcelTools;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public static List<String> getSheetNames(File file) {
        List<String> sheetNames = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(file))) {
            sheetNames = new ArrayList<>();
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetNames.add(workbook.getSheetName(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sheetNames;
    }

    public static List<String> getHeads(File file, String sheetName) {
        List<String> heads = new ArrayList<>();

        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet != null) {
                Row headerRow = sheet.getRow(0);

                if (headerRow != null) {
                    Iterator<Cell> cellIterator = headerRow.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        heads.add(cell.toString());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла Excel: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        return heads;
    }

    public static List<Double> getColumnDataByHead(File file, String sheetName, String head) {
        List<Double> columnData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet != null) {
                Row headerRow = sheet.getRow(0);
                int columnIndex = -1;

                // Находим индекс столбца с указанным именем выборки
                for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                    Cell cell = headerRow.getCell(i);
                    if (cell != null && cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(head)) {
                        columnIndex = i;
                        break;
                    }
                }

                if (columnIndex != -1) {
                    // Извлекаем значения из столбца, начиная со второй строки
                    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row row = sheet.getRow(rowIndex);
                        if (row != null) {
                            Cell cell = row.getCell(columnIndex);
                            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                                double value = cell.getNumericCellValue();
                                columnData.add(value); // Добавляем значение в список значений выборки
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла Excel: " + e.getMessage());
        }

        return columnData;
    }
}
