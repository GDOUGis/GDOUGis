package org.cpp.gis.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.cpp.gis.entities.User;
import org.cpp.gis.utils.JDBCUtil;

/**
 * Created by Administrator on 2015/5/9.
 */
public class UserDaoImpl {

    public void createUser(User user) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "INSERT INTO tb_user(username, password) values(?, ?)";
            Object[] params = {user.getUsername(), user.getPassword()};
            qr.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUserById(Integer id) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "DELETE FROM tb_user WHERE id = ?";
            qr.update(sql,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(Integer id ,User user) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "UPDATE tb_user SET username = ?, password = ? WHERE id = ?";
            Object[] params = {user.getUsername(), user.getPassword(), id};
            qr.update(sql,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User readById(Integer id) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_user WHERE id = ?";
            return (User) qr.query(sql,id, new BeanHandler(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User readByName(String username) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_user WHERE username = ?";
            return (User) qr.query(sql,username, new BeanHandler(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
