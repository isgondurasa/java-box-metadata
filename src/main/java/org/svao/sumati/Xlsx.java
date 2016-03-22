/**
 * Created by asviridov on 21/03/16.
 */

package org.svao.sumati;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Xlsx {

    public DataSet readFile(String filePath) throws IOException{
        FileInputStream inputStream = new FileInputStream(new File(filePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator <Row> it = sheet.iterator();


        DataSet ds = DataSet.getInstance();


        while (it.hasNext()) {
            Row nextRow = it.next();

            Iterator <Cell> cellIterator = nextRow.cellIterator();

            ds.createRow();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        ds.addToRow(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        ds.addToRow(cell.getNumericCellValue());
                        break;
                }
                System.out.print(" - ");
            }
            System.out.println();
        }
        workbook.close();
        inputStream.close();
        return ds;
    }
}
