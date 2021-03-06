/**
 * Created by asviridov on 21/03/16.
 */

package org.svao.sumati;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.svao.sumati.config.XlsxConfig;

public class Xlsx {

    public DataSet readFile(String filePath) throws IOException{
        FileInputStream inputStream = new FileInputStream(new File(filePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator <Row> it = sheet.iterator();


        DataSet ds = DataSet.getInstance();

        ArrayList <String> headers = new ArrayList<String>();

        int rowNum = 0;
        while (it.hasNext()) {
            Row nextRow = it.next();
            Iterator <Cell> cellIterator = nextRow.cellIterator();


            if (rowNum > XlsxConfig.TYPE_POS) {
                ds.createRow();
            }

            int colNum = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:

                        if (rowNum == XlsxConfig.HEADER_POS){
                            ds.addToHeaders(cell.getStringCellValue());
                        } else if (rowNum == XlsxConfig.TYPE_POS) {
                            ds.addToTypes(cell.getStringCellValue());
                        } else {
                            ds.addToDataRow(cell.getStringCellValue());
                        }
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        String t = (String) ds.getTypes().get(colNum);
                        if (t.equals("Date")) {
                            ds.addToDataRow(cell.getDateCellValue());
                        } else {
                            ds.addToDataRow(cell.getNumericCellValue());
                        }
                        break;
                }
                colNum++;
            }
            rowNum++;
        }
        workbook.close();
        inputStream.close();
        return ds;
    }
}
