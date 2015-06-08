package org.cpp.gis.utils;

import java.io.*;

/**
 * 数据库备份工具类.
 * Created by Rose on 2015/6/8.
 */
public class BackupDBUtil {

    /**
     * 备份.
     * @param user 数据库用户名
     * @param password 数据库密码
     * @param dbName 要备份的数据库
     * @param savePath 保存备份文件的路径
     */
    public static void backup(String user, String password, String dbName, String savePath) {
        try {
            Runtime runtime = Runtime.getRuntime();

            // 调用mysql的cmd
            Process child = runtime.exec("mysqldump -u" + user + " -p" + password + " --set-charset=utf8 " + dbName);

            /*
             把进程执行中的控制台输出信息写入.sql文件， 即生成了备份文件.
             注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            */
            InputStream in = child.getInputStream(); // 控制台的输出信息作为输入流.
            InputStreamReader reader = new InputStreamReader(in, "utf-8");

            String inStr;
            StringBuffer sb = new StringBuffer();
            String outStr;
            // 组合控制台输出信息字符串.
            BufferedReader br = new BufferedReader(reader);
            while((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();

            // .sql目标文件
            FileOutputStream fout = new FileOutputStream(new File(savePath + "\\" + dbName + ".sql"));
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
            writer.write(outStr);
            // 注：如果用缓冲的方式写入文件的话，会导致中文乱码，用flush()方法则可以避免.
            writer.flush();

            // 关闭资源
            in.close();
            reader.close();
            br.close();
            writer.close();
            fout.close();

            System.out.println("=============================== backup ok =================================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param user
     * @param password
     * @param dbName
     * @param path
     */
    public static void load(String user, String password, String dbName, String path) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process child = runtime.exec("mysql -u" + user + " -p" + password + " " + dbName);
            OutputStream out = child.getOutputStream();
            String inStr;
            StringBuffer sb = new StringBuffer();
            String outStr;
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(path + "\\" + dbName + ".sql"), "utf-8"));
            while((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();

            OutputStreamWriter writer = new OutputStreamWriter(out, "utf-8");
            writer.write(outStr);
            writer.flush();

            out.close();
            br.close();
            writer.close();

            System.out.println("=============================== load ok =================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
