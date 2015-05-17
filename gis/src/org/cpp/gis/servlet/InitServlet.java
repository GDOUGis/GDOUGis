package org.cpp.gis.servlet;

import com.mapinfo.mapj.MapJ;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seven on 2015/4/30.
 */
public class InitServlet extends HttpServlet {

    private String m_mapPath = null;
    private String m_fileToLoad = null;
    private String mapxtremeurl = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String strParam = null;
        //获取资源路径
        URL url = this.getClass().getResource("/../..");
        //获取地图跟目录
        System.out.println(this);
        strParam = getInitParameter("mapPath");
        if (strParam != null) {
            //也可以直接在web.xml 配置绝对路径 那么下面这一句就可以不要了
            strParam = url.getPath().substring(1).replaceAll("%20", " ")+strParam;
            m_mapPath = strParam;
        }
        //获取地图gst文件
        strParam = getInitParameter("fileToLoad");
        if (strParam != null) {
            //也可以直接在web.xml 配置绝对路径 那么下面这一句就可以不要了
            strParam = url.getPath().substring(1).replaceAll("%20", " ")+strParam;
            m_fileToLoad = strParam;
        }
        //获取地图服务器路径.
        strParam = getInitParameter("mapxtremeURL");
        if (strParam != null && strParam.length() > 0) {
            mapxtremeurl = strParam;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doInit(request,response);

    }

    /**
     * 初始化工作.
     * @param request
     * @param response
     */
    private void doInit(HttpServletRequest request, HttpServletResponse response) {

        try{
            //1.加载地图
            MapJ mapJ = new MapJ();
            mapJ.loadGeoset(m_fileToLoad, m_mapPath, null);
            //2.初始化地图信息放到session
            mapJ.setZoom(2140);
            request.getSession().setAttribute("mapj", mapJ);
            List<String> layerNames = new ArrayList<String>();
            for(int i=0;i<mapJ.getLayers().size();i++){
                layerNames.add(mapJ.getLayers().get(i).getName());
            }
            request.getSession().setAttribute("layerNames",layerNames);
            //3.重定向跳转到gisMap.jsp
            response.sendRedirect(request.getContextPath()+"/gisMap.jsp");
            //request.getRequestDispatcher("/gisMap.jsp").forward(request,response);
        }catch (Exception e ){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
