package org.cpp.gis.junit;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.service.impl.ModifyServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 将数据库的数据导出到excel.
 * Created by Rose on 2015/5/9.
 */
public class ExportToExcel {

    public static void main(String[] args) {
        ModifyServiceImpl  modifyService = new ModifyServiceImpl();
        WritableWorkbook wwb = null;
        try {
            // 创建可写入的excel.
            String filePath = "D:\\test.xls";
            File file = new File(filePath);
            if(!file.exists()) {
                file.createNewFile();
            }

            // 以以上fileName来创建一个WorkBook
            wwb = Workbook.createWorkbook(file);

            // 创建工作表
            WritableSheet ws = wwb.createSheet("Test sheet 1", 0);

            // 查询数据库中所有的数据
            List<Modify> list = modifyService.getModifyPageData(1, 10);
            // 要插入到的Excel表格的行号，默认从0开始
            Label labelId = new Label(0, 0, "No(id)");
            Label labelName = new Label(1, 0, "ModifyName");
            ws.addCell(labelId);
            ws.addCell(labelName);
            for(int i = 0; i < list.size(); i++) {
                Label labelId_i = new Label(0, i+1, list.get(i).getId()+"");
                Label labelName_i = new Label(1, i+1, list.get(i).getName()+"");
                ws.addCell(labelId_i);
                ws.addCell(labelName_i);
            }

            // 写进文档
            wwb.write();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭excel工作对象
            if (wwb != null) {
                try {
                    wwb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
