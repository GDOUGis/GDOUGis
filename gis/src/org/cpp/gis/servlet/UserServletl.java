package org.cpp.gis.servlet;

import org.cpp.gis.entities.User;
import org.cpp.gis.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/5/9.
 */
public class UserServletl extends HttpServlet {

    private UserServiceImpl userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if((method != null) && (method.equals("login"))) {
            login(req, resp);
        }
    }

    /**
     * 登陆.
     * @param req
     * @param resp
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            resp.setContentType("text/html;charset=utf-8");
             /*判空*/
            if(username == null || password == null ||
                    "".equals(username.trim()) || "".equals(password.trim())) {
                return ;
            }

            /*查找是否存在该用户，存在则返回1并将user存到sessin域中，否则返回0*/
            User user = userService.getUserByName(username);
            if(user == null) {
                out.write("0");
            } else {
                req.getSession().setAttribute("user", user);
                out.write("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
