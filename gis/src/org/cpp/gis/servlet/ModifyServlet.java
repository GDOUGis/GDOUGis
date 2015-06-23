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
        } else if((method != null) && (method.equals("delete"))){
            delete(req,resp);
        }
    }


    /**
     * 删除.
     * @param request
     * @param response
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        try{
            Integer id = Integer.parseInt(request.getParameter("modifyId"));
            modifyService.deleteById(id);
            Integer feature_id = Integer.parseInt(request.getParameter("feature_id"));
            request.getRequestDispatcher("/servlet/ModifyServlet?method=showModifiedFPDetail&feature_id+"+feature_id).forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * 备份数据.
     */
    private void backup(HttpServletResponse resp, HttpServletRequest req) {
        System.out.println("==> backup");
        OutputStream out = null;
        FileInputStream inputStream = null;
        try {
            String user = "root";
            String pw = "root";
            String dbName = "db_gdou_gis";
            String filePath = "D:\\backup";
            File file = new File(filePath);
            if(!file.exists()) {
                file.mkdir();
            }
            filePath = file.getPath();
            BackupDBUtil.backup(user, pw, dbName, filePath);
            String fileName = URLEncoder.encode(dbName + ".sql", "utf-8");

            resp.setContentType("multipart/form-data");
            resp.setHeader("Content-disposition","attachment;filename="+fileName);

            inputStream = new FileInputStream(filePath + "\\" + fileName);
            out = resp.getOutputStream();

            byte bytes[]=new byte[1024 * 1024];//设置缓冲区为1MB
            int len=0;
            //读取数据。返回值为读入缓冲区的字节总数,如果到达文件末尾，则返回-1
            while((len=inputStream.read(bytes))!=-1)
            {
                //将指定 byte数组中从下标 0 开始的 len个字节写入此文件输出流,(即读了多少就写入多少)
                out.write(bytes,0,len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出数据.
     * @param req
     * @param resp
     */
    private void exportData(HttpServletRequest req, HttpServletResponse resp) {
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
    }

    /**
     * 显示修改过的详情.
     * @param req
     * @param resp
     */
    private void showModifiedFPDetail(HttpServletRequest req, HttpServletResponse resp) {
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

    }

    /**
     * 加载被修改过的特征点的分页统计数据.
     * @param req
     * @param resp
     */
    private void loadFPModifyPD(HttpServletRequest req, HttpServletResponse resp) {
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
    }

    /**
     * 根据特征点分组返回修改名的统计信息.
     * @param req
     * @param resp
     */
    private void getModifiedPDGroupByFN(HttpServletRequest req, HttpServletResponse resp) {
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
            resp.setContentType("text/html;charset=utf-8");
            out = resp.getWriter();
            out.write(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
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
