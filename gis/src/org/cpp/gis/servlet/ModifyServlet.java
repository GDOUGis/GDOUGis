package org.cpp.gis.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Rose on 2015/5/9.
 */
public class ModifyServlet extends HttpServlet {
    /**
     * 分页大小.
     */
    private int pageSize = 10;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if((method != null) && (method.equals("getModifyPageData"))) {
            getModifyPageData(req, resp);
        }
    }

    /**
     * 获取modify的分页数据.
     * @param req
     * @param resp
     */
    private void getModifyPageData(HttpServletRequest req, HttpServletResponse resp) {
        String pageNum = req.getParameter("pageNum");
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
