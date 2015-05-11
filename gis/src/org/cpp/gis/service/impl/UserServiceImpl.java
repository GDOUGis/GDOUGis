package org.cpp.gis.service.impl;

import org.cpp.gis.dao.impl.UserDaoImpl;
import org.cpp.gis.entities.User;
import org.cpp.gis.factory.DaoFactory;

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
}
