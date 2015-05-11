package org.cpp.gis.service.impl;

import org.cpp.gis.dao.impl.UserDaoImpl;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.entities.User;
import org.cpp.gis.factory.DaoFactory;
import org.cpp.gis.utils.Page;
import org.cpp.gis.utils.PageUtil;
import org.cpp.gis.utils.Result;

import java.util.List;

/**
 * UserService实现类.
 * Created by Rose on 2015/5/9.
 */
public class UserServiceImpl {
    private UserDaoImpl userDao = DaoFactory.getInstance().createDao("org.cpp.gis.dao.impl.UserDaoImpl");

    /**
     * 添加User.
     * @param username
     * @param password
     */
    public void addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDao.createUser(user);
    }

    /**
     * 删除user.
     * @param _id
     */
    public void deleteUserById(String _id) {
        int id = Integer.parseInt(_id);
        userDao.deleteUserById(id);
    }

    public User getUserById(String id) {
        return userDao.readById(Integer.parseInt(id));
    }

    public User getUserByName(String username) {
        return userDao.readByName(username);
    }

    /**
     * 登陆.
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {
        // 先根据username找到user，再匹配密码
        User user = userDao.readByName(username);
        if(null == user) {
            return null;
        }
        if("".equals(password) || !user.getPassword().equals(password.trim())) {
            return null;
        }

        return user;
    }

    /**
     * 获得所有的用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Result list(String pageNum, String pageSize) {
        Result result = new Result();
        List<User> list = null;
        try {
            UserDaoImpl userDao = new UserDaoImpl();
            int totalRecord = userDao.getTotalRecord();
            Page page = PageUtil.createPage(Integer.parseInt(pageSize),totalRecord,Integer.parseInt(pageNum));
            list =userDao.getUserPageDate(page.getBeginIndex(), Integer.parseInt(pageSize));
            result.setList(list);
            result.setPage(page);
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重置密码.
     * @param id
     */
    public void resetPwd(String id){
        try {
            UserDaoImpl userDao = new UserDaoImpl();
            userDao.resetPwd(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void updatePwd(User user){
        try {
            UserDaoImpl userDao = new UserDaoImpl();
            userDao.updatePwd(user);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
