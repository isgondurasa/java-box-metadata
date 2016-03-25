package org.svao.sumati;

/**
 * Created by asviridov on 22/03/16.
 */

import com.box.sdk.*;

import org.svao.sumati.config.Box;

import java.util.ArrayList;


public class BoxHelper {

    private String devToken;
    private BoxAPIConnection boxConnection;
    private ArrayList<BoxFile> fileList = new ArrayList<BoxFile>();

    public BoxHelper(String _devToken) {
        setDevToken(_devToken);
    }

    public void setDevToken(String _devToken) {
        devToken = _devToken;
    }

    public String getDevToken() {
        return devToken;
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
                if (depth < Box.MAX_DEPTH) {
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

    public void getTemplateNames() {

        for (Object obj: getFiles()) {
            try {
                BoxFile f = (BoxFile) obj;
                Metadata metadata = f.getMetadata();
                String templateName = metadata.getTemplateName();
                String d = metadata.get("/metadata_templates/enterprise/schema");
                String p = metadata.get("/metadata");
                System.out.println(d + " " + p);
            } catch (BoxAPIException e) {
                System.out.println(e);
            }
        }
    }

    public void createMetadata() {
        Metadata metadata = new Metadata();
    }

    public void importTemplate(String folderId) {
        BoxFolder folder = getRootFolder(folderId);
        if (folder != null) {
            listFolder(folder, 0);
            getTemplateNames();
        }
    }

    public void getUserInfo() {
        BoxAPIConnection boxConnection = getBoxConnection();
        BoxUser user = BoxUser.getCurrentUser(boxConnection);
        BoxUser.Info info = user.getInfo();
    }
}
