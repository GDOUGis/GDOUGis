package org.cpp.gis.servlet;

import org.cpp.gis.entities.Modify;
import org.cpp.gis.entities.User;
import org.cpp.gis.service.impl.ModifyServiceImpl;
import org.cpp.gis.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/5/9.
 */
public class UserServlet extends HttpServlet {

    private UserServiceImpl userService = new UserServiceImpl();
    private ModifyServiceImpl modifyService = new ModifyServiceImpl();

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
        System.out.println("==> login");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("un:"+username+", pw:"+password);
        try {
             /*判空*/
            if(username == null || password == null ||
                    "".equals(username.trim()) || "".equals(password.trim())) {
                return ;
            }

            /*查找是否存在该用户，存在则返回1并将user存到sessin域中，否则返回0*/
            User user = userService.login(username, password);
            if(user == null) {
            } else {
                req.getSession().setAttribute("user", user);
                List<Modify> list = modifyService.getFPModifyPD("1","20");
                // 将分页结果存到request域.
                System.out.println(list.toString());
                req.setAttribute("list", list);
                req.getRequestDispatcher("/WEB-INF/views/table.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("login ==>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
