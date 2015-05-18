package org.cpp.gis.utils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.service.impl.ModifyServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 导出excel工具类.
 * Created by Rose on 2015/5/18.
 */
public class ExcelUtil {
    public static String exportExcel(String basePath) {
        ModifyServiceImpl modifyService = new ModifyServiceImpl();
        WritableWorkbook wwb = null;
        Date date = new Date();
        try {
            // 创建可写入的excel.
            String filePath = basePath +"\\"+date.getTime()+".xls";
            System.out.println("filePath="+filePath);
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
            Label labelId = new Label(0, 0, "编号(id)");
            Label labelName = new Label(1, 0, "拟改名");
            Label labelDesc = new Label(2, 0, "描述");
            Label labelPeople = new Label(3, 0, "修改人");
            Label labelCollaege = new Label(4, 0, "所在学院");
            Label labelPhone = new Label(5, 0, "联系电话");
            Label labelFeature_id = new Label(6, 0, "特征点编号");

            ws.addCell(labelId);
            ws.addCell(labelName);
            ws.addCell(labelDesc);
            ws.addCell(labelPeople);
            ws.addCell(labelCollaege);
            ws.addCell(labelPhone);
            ws.addCell(labelFeature_id);
            for(int i = 0; i < list.size(); i++) {
                Label labelId_i = new Label(0, i+1, list.get(i).getId()+"");
                Label labelName_i = new Label(1, i+1, list.get(i).getName()+"");
                Label labelDesc_i = new Label(2, i+1, list.get(i).getDescription() + "");
                Label labelPeople_i = new Label(3, i+1, list.get(i).getPeople() + "");
                Label labelCollege_i = new Label(4, i+1, list.get(i).getCollege() + "");
                Label labelPhone_i = new Label(5, i+1, list.get(i).getPeople() + "");
                Label labelFeature_id_i = new Label(6, i+1, list.get(i).getFeature_id()+"");
                ws.addCell(labelId_i);
                ws.addCell(labelName_i);
                ws.addCell(labelDesc_i);
                ws.addCell(labelPeople_i);
                ws.addCell(labelCollege_i);
                ws.addCell(labelPhone_i);
                ws.addCell(labelFeature_id_i);
            }

            // 写进文档
            wwb.write();

            return filePath;

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
        return null;
    }
}
