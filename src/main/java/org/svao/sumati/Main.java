/**
 * Created by asviridov on 21/03/16.
 */

package org.svao.sumati;

import org.svao.sumati.config.Box;
import com.box.sdk.BoxAPIConnection;

import com.box.sdk.BoxFolder;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxItem;
import com.box.sdk.BoxUser;
import com.box.sdk.Metadata;


public class Main {


    private static void listFolder(BoxFolder folder, int depth) {
        for (BoxItem.Info itemInfo: folder) {

            System.out.println(itemInfo.getName());

            if (itemInfo instanceof BoxFolder.Info) {
                BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
                if (depth < Box.MAX_DEPTH) {
                    listFolder(childFolder, depth + 1);
                }
            } else if (itemInfo instanceof BoxFile.Info) {
                BoxFile boxFile = (BoxFile) itemInfo.getResource();
                if (itemInfo.getName().equals("avi kapoor.txt")) {
                    Metadata metadata = boxFile.getMetadata("andrey");
                    System.out.println(boxFile.toString());
                }
            }
        }
    }

    private static void connectToBox() {
        BoxAPIConnection boxConnection = new BoxAPIConnection(Box.DEV_TOKEN);
        System.out.println(boxConnection);

        BoxUser.Info userInfo = BoxUser.getCurrentUser(boxConnection).getInfo();
        System.out.println(userInfo.getLogin());


        BoxFolder rootFolder = BoxFolder.getRootFolder(boxConnection);
        listFolder(rootFolder, 0);
    }

    private void createDataset() {

    }


    public static void main(String [] args) {
        System.out.println("Start test application");
        Xlsx xlsxReader = new Xlsx();
        try {
            xlsxReader.readFile("/Users/asviridov/projects/box-contracts/scripts/test_metadata_sheet.xlsx");
        } catch(Exception e) {
            System.out.println(e);
        }

        //testing
        connectToBox();
    }
}
