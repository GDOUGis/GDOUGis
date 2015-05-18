package org.cpp.gis.servlet;

import net.sf.json.JSONObject;
import org.cpp.gis.entities.FeaturePoint;
import org.cpp.gis.service.FeaturePointService;
import org.cpp.gis.service.impl.FeaturePointServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2015/5/12.
 */
public class FeaturePointServlet extends HttpServlet {

    private FeaturePointService featurePointService = new FeaturePointServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String method = req.getParameter("method");
            if((method != null) && method.equals("getFeaturePoint")) {
                getFeaturePoint(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据特征点的名字查找出特征点.
     * @param req
     * @param resp
     */
    private void getFeaturePoint(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("==> getFeaturePoint");
        PrintWriter writer = null;
        try {
//            String currentName = new String(req.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
            String currentName = req.getParameter("currentName");
            currentName = URLDecoder.decode(currentName,"UTF-8");
            System.out.println("currentName:" + currentName);
            FeaturePoint featurePoint = featurePointService.getByName(currentName);
            resp.setContentType("text/html;charset=utf-8");
            JSONObject jsonObject = JSONObject.fromObject(featurePoint);
            writer = resp.getWriter();
            System.out.println(featurePoint.toString());
            writer.write(jsonObject.toString());
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        System.out.println("getFeaturePoint ==>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
