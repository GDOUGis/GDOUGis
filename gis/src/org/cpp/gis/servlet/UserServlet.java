package org.cpp.gis.servlet;

import org.cpp.gis.entities.Modify;
import org.cpp.gis.entities.User;
import org.cpp.gis.service.impl.ModifyServiceImpl;
import org.cpp.gis.service.impl.UserServiceImpl;
import org.cpp.gis.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Administrator on 2015/5/9.
 */
public class UserServlet extends HttpServlet {

    private UserServiceImpl userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if((method != null) && (method.equals("login"))) {
            login(req, resp);
        }else if((method != null) && (method.equals("list"))){
            list(req,resp);
        }else if((method != null) && (method.equals("delete"))){
            delete(req, resp);
        }else if((method != null) && (method.equals("add"))){
            add(req, resp);
        }else if((method != null) && (method.equals("logout"))){
            logout(req, resp);
        }else if((method != null) && (method.equals("reset"))){
            reset(req, resp);
        }else if((method != null) && (method.equals("updatePwd"))){
            updatePwd(req, resp);
        }
    }

    /**
     * 修改密码.
     * @param req
     * @param resp
     */
    private void updatePwd(HttpServletRequest req, HttpServletResponse resp){
        try{
            User user = (User)req.getSession().getAttribute("user");
            String password = req.getParameter("password");
            user.setPassword(password);
            userService.updatePwd(user);
            req.setAttribute("message","修改成功!");
            req.getRequestDispatcher("/servlet/ForwardServlet?resource=updatePwdUI").forward(req,resp);
        }catch (Exception e){
            e.printStackTrace();
            try {
                req.getRequestDispatcher("/message.jsp").forward(req,resp);
            } catch (ServletException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 重置密码
     * @param req
     * @param resp
     */
    private void reset(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id  = req.getParameter("id");
            userService.resetPwd(id);
            req.getRequestDispatcher("/servlet/UserServlet?method=list").forward(req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     * @param req
     * @param resp
     */
    private void logout(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getSession().removeAttribute("user");
            PrintWriter out = resp.getWriter();
            out.println("<script language='javascript'>window.parent.location='/admin.jsp'</script>");
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    /**
     * 添加.
     * @param req
     * @param resp
     */
    private void add(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String username = req.getParameter("username");
            String passowrd = req.getParameter("password");
            User u = userService.getUserByName(username);
            if(u!=null){

            }else{
                userService.addUser(username,passowrd);
            }
            req.getRequestDispatcher("/servlet/UserServlet?method=list").forward(req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 删除
     * @param req
     * @param resp
     */
    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id  = req.getParameter("id");
            if(id!=null){
                userService.deleteUserById(id);
            }
            req.getRequestDispatcher("/servlet/UserServlet?method=list").forward(req,resp);
        }catch (Exception e){
            e.printStackTrace();
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
                req.setAttribute("message","账号或密码错误");
                req.getRequestDispatcher("/message.jsp").forward(req,resp);
            } else {
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/views/admin_index.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("login ==>");
    }

    /**
     * 列出所有用户.
     * @param req
     * @param resp
     */
    private void list(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String pageNum = req.getParameter("pageNum");
            String pageSize = req.getParameter("pageSize");
            if(pageNum==null){
                pageNum = "1";
            }
            if(pageSize==null){
                pageSize = "10";
            }
            Result result = userService.list(pageNum,pageSize);
            // 将分页结果存到request域.
            req.setAttribute("result", result);
            req.getRequestDispatcher("/WEB-INF/views/listUser.jsp").forward(req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
