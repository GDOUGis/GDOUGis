package org.cpp.gis.junit;

import org.cpp.gis.dao.impl.UserDaoImpl;
import org.cpp.gis.entities.User;
import org.junit.Test;


/**
 * UserDaoImpl测试单元.
 * Created by Rose on 2015/5/9.
 */
public class TestUserDaoImpl {

    private UserDaoImpl userDao = null;

    /*@Before
    public void init(){
        userDao = DaoFactory.getInstance().createDao("org.cpp.gis.dao.impl.UserDaoImpl");
    }*/

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("Rose");
        user.setPassword("123456");
        userDao.createUser(user);
    }

    @Test
    public void testDeleteUserById() {
        userDao.deleteUserById(2);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("Rose");
        user.setPassword("123456");
        userDao.updateUser(1, user);
    }

    @Test
    public void testReadById() {
        User user = userDao.readById(1);
        System.out.println(user);
    }
}
