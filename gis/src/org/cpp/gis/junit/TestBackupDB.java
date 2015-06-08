package org.cpp.gis.junit;

import org.cpp.gis.utils.BackupDBUtil;

import java.io.File;

/**
 * Created by Administrator on 2015/6/8.
 */
public class TestBackupDB {
    public static void main(String[] args) {
        String user = "root";
        String pw = "love100200";
        String dbName = "db_gdou_gis";
//        String savePath = "E:\\backup";

        String filePath = "E:\\backup";
        File file = new File(filePath);

        if(!file.exists()) {
            file.mkdir();
        }

        filePath = file.getPath();

        BackupDBUtil.backup(user, pw, dbName, filePath);
    }
}
