/**
 * Created by asviridov on 21/03/16.
 */

package org.svao.sumati;

import org.svao.sumati.config.BoxConfig;
import org.svao.sumati.config.XlsxConfig;

import java.util.HashMap;


public class Main {


    private void importDataToBox(HashMap dataSet) {
        BoxHelper boxHelper = new BoxHelper(BoxConfig.DEV_TOKEN);
        boxHelper.connect();
        boxHelper.importTemplate(BoxConfig.BOX_FOLDER_ID, dataSet);
        boxHelper.getFiles();
    }

    public static void main(String [] args) {
        System.out.println("Start test application");
        Xlsx xlsxReader = new Xlsx();

        try {

            System.out.println("Reading file");
            DataSet excelData = xlsxReader.readFile(XlsxConfig.FILE_PATH);
            HashMap map = excelData.toMapOfList();

            if (excelData == null) {
                System.out.println("No Data. Exiting..");
                System.exit(0);
            }

            Main m = new Main();
            m.importDataToBox(map);

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
