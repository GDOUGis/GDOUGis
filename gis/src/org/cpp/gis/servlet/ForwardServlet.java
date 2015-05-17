package org.cpp.gis.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Seven on 2015/5/11.
 */
public class ForwardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
                String resource = request.getParameter("resource");

            if("admin_left".equals(resource)){
                request.getRequestDispatcher("/WEB-INF/views/admin_left.jsp").forward(request,response);
            }else if("admin_top".equals(resource)){
                request.getRequestDispatcher("/WEB-INF/views/admin_top.jsp").forward(request,response);
            }else if("admin_main".equals(resource)){
                request.getRequestDispatcher("/WEB-INF/views/admin_main.jsp").forward(request,response);
            }else if("addUser".equals(resource)){
                request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request,response);
            }else if("updatePwdUI".equals(resource)){
                request.getRequestDispatcher("/WEB-INF/views/updatePwdUI.jsp").forward(request,response);
            }else if("admin_bottom".equals(resource)){
                request.getRequestDispatcher("/WEB-INF/views/admin_bottom.jsp").forward(request,response);
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
