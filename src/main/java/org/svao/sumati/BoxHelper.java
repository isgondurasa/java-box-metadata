package org.svao.sumati;

/**
 * Created by asviridov on 22/03/16.
 */

import com.box.sdk.*;

import org.svao.sumati.config.BoxConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Date;
import java.text.DateFormat;

public class BoxHelper {

    private String devToken;
    private BoxAPIConnection boxConnection;
    private ArrayList<BoxFile> fileList = new ArrayList<BoxFile>();

    public BoxHelper(String _devToken) {
        setDevToken(_devToken);
    }

    public String getDevToken() {
        return devToken;
    }

    public void setDevToken(String _devToken) {
        devToken = _devToken;
    }

    public BoxAPIConnection getBoxConnection() {
        if (boxConnection == null) {
            return setBoxConnection(getDevToken());
        }
        return boxConnection;
    }

    private BoxAPIConnection setBoxConnection(String devToken) {
        boxConnection = new BoxAPIConnection(devToken);
        return boxConnection;
    }

    public ArrayList getFiles() {
        return fileList;
    }

    public void appendFile(BoxFile f) {
        fileList.add(f);
    }

    private void listFolder(BoxFolder folder, int depth) {
        for (BoxItem.Info itemInfo: folder) {

            System.out.println(itemInfo.getName());

            if (itemInfo instanceof BoxFolder.Info) {
                BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
                if (depth < BoxConfig.MAX_DEPTH) {
                    listFolder(childFolder, depth + 1);
                }
            } else if (itemInfo instanceof BoxFile.Info) {
                BoxFile boxFile = (BoxFile) itemInfo.getResource();
                appendFile(boxFile);
            }
        }
    }

    public void connect() {
        BoxAPIConnection boxConnection = getBoxConnection();
        BoxUser.Info userInfo = BoxUser.getCurrentUser(boxConnection).getInfo();
        System.out.println(userInfo.getLogin());
    }

    private BoxFolder getRootFolder(String folderId) {
        if (boxConnection != null) {
            BoxFolder rootFolder = new BoxFolder(boxConnection, folderId);
            return rootFolder;
        }
        return null;
    }

    public void importData(HashMap ds) {

        for (Object obj: getFiles()) {
            BoxFile boxFile = (BoxFile) obj;
            try {
                createMetadata(boxFile, BoxConfig.BOX_TEMPLATE_NAME, ds);
            } catch(BoxAPIException e) {
                System.out.println(e);
                System.out.print(e.getResponse());
            }
        }
    }

    public void createMetadata(BoxFile boxFile, String templateName, HashMap ds) throws BoxAPIException {
        Metadata metaData = new Metadata();
        String fileName = boxFile.getInfo().getName();
        ArrayList <Element> elements = (ArrayList)ds.get(fileName);

        if (elements == null) {
            throw new BoxAPIException("Mapping for file: " + fileName + " not found in dataset");
        }

        for (Element e: elements) {

            String fieldType = e.getFieldType();
            String value = e.getValue().toString();
            if(fieldType.equals("Date")) {
                try {
                    DateFormat dtfBefore = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                    DateFormat dtfAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dt = dtfBefore.parse(value);
                    value = dtfAfter.format(dt).toString();
                    value = value.replace(" ", "T") + "Z";
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
//            String sValue = null;
//            Double dValue = null;
//            if (fieldType == "Number") {
//                dValue = (Double) e.getValue();
//                //metaData.add("/" + e.getFieldName(), dValue);
//            } else if(fieldType == "Date") {
//                String dateValue = (String) e.getValue();
//
//            } else {
//                sValue = e.getValue().toString();
//                metaData.add("/" + e.getFieldName(), sValue);
//            }


    metaData.add("/" + e.getFieldName(), value);
        }

        boxFile.createMetadata(templateName, metaData);
    }

    public void importTemplate(String folderId, HashMap ds) {
        BoxFolder folder = getRootFolder(folderId);
        if (folder != null) {
            listFolder(folder, 0);
            importData(ds);
        }
    }

    public void getUserInfo() {
        BoxAPIConnection boxConnection = getBoxConnection();
        BoxUser user = BoxUser.getCurrentUser(boxConnection);
        BoxUser.Info info = user.getInfo();
    }
}
