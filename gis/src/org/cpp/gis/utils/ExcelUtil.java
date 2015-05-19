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
            String filePath = date.getTime()+".xls";
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
//            List<Modify> list = modifyService.getModifyPageData(1, 1000);
//            if(list.isEmpty()){
//                return null;
//            }
//            // 要插入到的Excel表格的行号，默认从0开始
//            Label labelId = new Label(0, 0, "编号(id)");
//            Label labelName = new Label(1, 0, "拟改名");
//            Label labelDesc = new Label(2, 0, "描述");
//            Label labelPeople = new Label(3, 0, "修改人");
//            Label labelCollege = new Label(4, 0, "所在学院");
//            Label labelPhone = new Label(5, 0, "联系电话");
//            Label labelFeature_id = new Label(6, 0, "特征点编号");
//
//            ws.addCell(labelId);
//            ws.addCell(labelName);
//            ws.addCell(labelDesc);
//            ws.addCell(labelPeople);
//            ws.addCell(labelCollege);
//            ws.addCell(labelPhone);
//            ws.addCell(labelFeature_id);
//            for(int i = 0; i < list.size(); i++) {
//                Label labelId_i = new Label(0, i+1, list.get(i).getId()+"");
//                Label labelName_i = new Label(1, i+1, list.get(i).getName()+"");
//                System.out.println(list.get(i).getName()+"====================================");
//                Label labelDesc_i = new Label(2, i+1, list.get(i).getDescription() + "");
//                Label labelPeople_i = new Label(3, i+1, list.get(i).getPeople() + "");
//                Label labelCollege_i = new Label(4, i+1, list.get(i).getCollege() + "");
//                Label labelPhone_i = new Label(5, i+1, list.get(i).getPhone() + "");
//                Label labelFeature_id_i = new Label(6, i+1, list.get(i).getFeature_id()+"");
//                ws.addCell(labelId_i);
//                ws.addCell(labelName_i);
//                ws.addCell(labelDesc_i);
//                ws.addCell(labelPeople_i);
//                ws.addCell(labelCollege_i);
//                ws.addCell(labelPhone_i);
//                ws.addCell(labelFeature_id_i);
//            }
//
//            // 写进文档
//            wwb.write();
            // 外层循环遍历所有特征点，并将同名数据合并到一个单元格
            Result result = modifyService.getFPModifyPD("1", "1000");
            List<Modify> resultList = result.getList();                      // 这并不是FeaturePoint实体，而是Modify实体，借用了。

            // 物点列
            Label labelCurName = new Label(0, 0, "物点（现用名）");
            // 拟用名列
            Label labelPrepareName = new Label(1, 0, "拟用名");
            // 修改信息列
            Label labelModifyName = new Label(2, 0, "修改名");
            Label labelDesc = new Label(3, 0, "描述");
            Label labelPeople = new Label(4, 0, "修改人");
            Label labelCollege = new Label(5, 0, "所在学院");
            Label labelPhone = new Label(6, 0, "联系电话");

            // 添加一行数据（标题）
            ws.addCell(labelCurName);
            ws.addCell(labelPrepareName);
            ws.addCell(labelModifyName);
            ws.addCell(labelDesc);
            ws.addCell(labelPeople);
            ws.addCell(labelCollege);
            ws.addCell(labelPhone);

            int j = 0;
            int lastRow = 0;                                            // 记录上一行位置

            for(int i = 0; i < resultList.size(); i++) {
                int id = resultList.get(i).getFeature_id();             // 物点ID
                String fpName = resultList.get(i).getName();            // 物点名称（现用名）
                // 根据物点ID查找出拟用名
                String prepareName = modifyService.getPrepareNameByFPId(id);
                int tmp = 0;
                tmp += resultList.get(i).getTimes();                    // 该物点有多少次被修改，就合并多少个单元格
                // 根据物点名称查找出所有修改名信息.
                List<Modify> modifyList = modifyService.getModifyDetail(id, "1", "100").getList();   // 根据物点ID找出修改详情（修改的列表）
                int k = 0;
                for(; k < resultList.get(i).getTimes(); j++) {          // 该物点被修改多少次就循环多少次
                    Label labelName_i = new Label(2, j+1, modifyList.get(k).getName()+"");
                    Label labelDesc_i = new Label(3, j+1, modifyList.get(k).getDescription() + "");
                    Label labelPeople_i = new Label(4, j+1, modifyList.get(k).getPeople() + "");
                    Label labelCollege_i = new Label(5, j+1, modifyList.get(k).getCollege() + "");
                    Label labelPhone_i = new Label(6, j+1, modifyList.get(k).getPhone() + "");
                    ws.addCell(labelName_i);
                    ws.addCell(labelDesc_i);
                    ws.addCell(labelPeople_i);
                    ws.addCell(labelCollege_i);
                    ws.addCell(labelPhone_i);
                    k++;
                }
                if(i == 0) {
                    ws.addCell(new Label(0, lastRow + 1, fpName));
                    ws.addCell(new Label(1, lastRow + 1, prepareName));
                    ws.mergeCells(0, 1, 0, tmp);               // 合并单元格
                    ws.mergeCells(1,1, 1, tmp);               // 合并单元格
                    System.out.println("i==0: tmp =>" + tmp);
                    lastRow += tmp;
                } else {
                    ws.addCell(new Label(0, lastRow + 1, fpName));
                    ws.addCell(new Label(1, lastRow + 1, prepareName));
                    System.out.println("i!=0: lastRow =>" + lastRow + ", tmp => " + tmp);
                    ws.mergeCells(0, lastRow + 1, 0, lastRow + tmp);
                    ws.mergeCells(1, lastRow + 1, 1, lastRow + tmp);
                    lastRow += tmp;

                }
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
