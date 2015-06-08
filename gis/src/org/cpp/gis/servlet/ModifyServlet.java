package org.cpp.gis.servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.cpp.gis.entities.FeaturePoint;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.service.FeaturePointService;
import org.cpp.gis.service.impl.FeaturePointServiceImpl;
import org.cpp.gis.service.impl.ModifyServiceImpl;
import org.cpp.gis.utils.BackupDBUtil;
import org.cpp.gis.utils.ExcelUtil;
import org.cpp.gis.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rose on 2015/5/9.
 */
public class ModifyServlet extends HttpServlet {
    /**
     * 分页大小.
     */
    private int pageSize = 10;
    private ModifyServiceImpl modifyService = new ModifyServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if((method != null) && (method.equals("getModifyPageData"))) {
            getModifyPageData(req, resp);
        } else if((method != null) && (method.equals("getModifiedPDGroupByFN"))) {
            getModifiedPDGroupByFN(req, resp);
        } else if((method != null) && (method.equals("loadFPModifyPD"))) {
            loadFPModifyPD(req, resp);
        } else if((method != null) && (method.equals("showModifiedFPDetail"))) {
            showModifiedFPDetail(req, resp);
        } else if((method != null) && (method.equals("exportData"))) {
            exportData(req, resp);
        } else if((method != null) && (method.equals("backup"))) {
            backup(resp, req);
        }
    }

    /**
     * 备份数据.
     */
    private void backup(HttpServletResponse resp, HttpServletRequest req) {
        try {
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

            resp.getWriter().write("1");    // 备份成功返回1
        } catch (Exception e) {
            try {
                resp.getWriter().write("0");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }


    }

    /**
     * 导出数据.
     * @param req
     * @param resp
     */
    private void exportData(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("==> exportData");
        OutputStream out = null;
        BufferedInputStream buf = null;
        try {
            // 本地生成excel
            String basePath = req.getRealPath("/excel");
            String filePath = ExcelUtil.exportExcel(basePath);
            buf = new BufferedInputStream(new FileInputStream(filePath));
            byte[] tmpBuf = new byte[1024];
            int len = 0;
            resp.reset();
            // 写出
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/x-msdownload");
            resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filePath, "utf-8"));
            out = resp.getOutputStream();
            while((len = buf.read(tmpBuf)) > 0) {
                out.write(tmpBuf, 0, len);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
               if(out!=null){
                   buf.close();
                   out.close();
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("exportData ==>");
    }

    /**
     * 显示修改过的详情.
     * @param req
     * @param resp
     */
    private void showModifiedFPDetail(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("==> showModifiedFPDetail ");
        try {
            int feature_id = Integer.parseInt(req.getParameter("feature_id"));

            String pageNum = req.getParameter("pageNum");
            String pageSize = req.getParameter("pageSize");
            if(pageNum==null){
                pageNum = "1";
            }
            if(pageSize==null){
                pageSize = "10";
            }
            Result result = modifyService.getModifyDetail(feature_id,pageNum,pageSize);
            // 将分页结果存到request域.
            req.setAttribute("result", result);
            //将特征点名放到request
            FeaturePointService fs = new FeaturePointServiceImpl();
            FeaturePoint f  = fs.getById(feature_id);
            req.setAttribute("featurePointName",f.getName());
            // 请求转发.
            req.getRequestDispatcher("/WEB-INF/views/modifyFpDetail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("showModifiedFPDetail ==>");
    }

    /**
     * 加载被修改过的特征点的分页统计数据.
     * @param req
     * @param resp
     */
    private void loadFPModifyPD(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("==> loadFPModifyPD");
        try {
            String pageNum = req.getParameter("pageNum");
            String pageSize = req.getParameter("pageSize");
            if(pageNum==null){
                pageNum = "1";
            }
            if(pageSize==null){
                pageSize = "10";
            }
            Result result = modifyService.getFPModifyPD(pageNum,pageSize);
            // 将分页结果存到request域.
            req.setAttribute("result", result);
            List list = result.getList();
           /* for(int i = 0; i<list.size(); i++) {
                System.out.println(list.get(i).getFeature_id()+":"+ list.get(i).getName() + " == > " + list.get(i).getTimes());
            }*/
            req.getRequestDispatcher("/WEB-INF/views/table.jsp").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("==> loadFPModifyPD");
    }

    /**
     * 根据特征点分组返回修改名的统计信息.
     * @param req
     * @param resp
     */
    private void getModifiedPDGroupByFN(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("==> getModifiedPDGroupByFN");
        PrintWriter out = null;
        try {
            String pageNum = req.getParameter("pageNum");
            String pageSize = req.getParameter("pageSize");
            List<Modify> list = modifyService.getModifiedPDGrounpByFN(Integer.parseInt(pageNum),
                    Integer.parseInt(pageSize));
            Map<String, Integer> map = new HashMap<String, Integer>();
            for(int i = 0; i<list.size();i++) {
                map.put(list.get(i).getName(), list.get(i).getTimes());
            }
            JSONObject jsonObject = JSONObject.fromObject(map);
            String result = jsonObject.toString();
            System.out.println(result);
            resp.setContentType("text/html;charset=utf-8");
            out = resp.getWriter();
            out.write(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
        System.out.println("getModifiedPDGroupByFN ==>");
    }

    /**
     * 获取modify的分页数据.
     * @param req
     * @param resp
     */
    private void getModifyPageData(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("==> getModifyPageData");
        PrintWriter out = null;
        resp.setContentType("text/html;charset=utf-8");
        try {
            out = resp.getWriter();
            int pageNum = Integer.parseInt(req.getParameter("pageNum"));
            List<Modify> list = modifyService.getModifyPageData(pageNum, pageSize);
            JSONArray jsonObject = JSONArray.fromObject(list);
            System.out.println(jsonObject.toString());
            out.write(jsonObject.toString());
            out.flush();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
        System.out.println("getModifyPageData ==>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
