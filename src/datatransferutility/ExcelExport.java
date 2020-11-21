/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatransferutility;

import java.io.File;
import javafx.scene.control.TableView;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author EPS01
 */
public class ExcelExport<T> {

    public void export(TableView<T> tableView, File file) {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Sheet1");
        HSSFRow firstRow = hssfSheet.createRow(0);

        ///set titles of columns
        for (int i = 0; i < tableView.getColumns().size(); i++) {

            firstRow.createCell(i).setCellValue(tableView.getColumns().get(i).getText());

        }

        for (int row = 0; row < tableView.getItems().size(); row++) {

            HSSFRow hssfRow = hssfSheet.createRow(row + 1);

            for (int col = 0; col < tableView.getColumns().size(); col++) {

                Object celValue = tableView.getColumns().get(col).getCellObservableValue(row).getValue();

                try {
                    if (celValue != null && Double.parseDouble(celValue.toString()) != 0.0) {
                        hssfRow.createCell(col).setCellValue(Double.parseDouble(celValue.toString()));
                    }
                } catch (NumberFormatException e) {

                    hssfRow.createCell(col).setCellValue(celValue.toString());
                }

            }

        }
        
        autoSizeColumns(hssfWorkbook);

        //save excel file and close the workbook
        try {
            hssfWorkbook.write(new FileOutputStream(file));
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(0);
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }

}
