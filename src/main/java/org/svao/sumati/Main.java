/**
 * Created by asviridov on 21/03/16.
 */

package org.svao.sumati;

import org.svao.sumati.config.Box;
import org.svao.sumati.config.XlsxConfig;



public class Main {


    private void importDataToBox(DataSet dateSet) {
        BoxHelper boxHelper = new BoxHelper(Box.DEV_TOKEN);
        boxHelper.connect();
        boxHelper.importTemplate(Box.BOX_FOLDER_ID);
        boxHelper.getFiles();
    }

    public static void main(String [] args) {
        System.out.println("Start test application");
        Xlsx xlsxReader = new Xlsx();

        try {

            System.out.println("Reading file");
            DataSet excelData = xlsxReader.readFile(XlsxConfig.FILE_PATH);

            if (excelData == null) {
                System.out.println("No Data. Exiting..");
                System.exit(0);
            }

            Main m = new Main();
            m.importDataToBox(excelData);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
