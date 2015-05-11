package org.cpp.gis.dao.impl;

import com.mapinfo.dp.QueryParams;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.entities.User;
import org.cpp.gis.utils.JDBCUtil;
import sun.awt.windows.ThemeReader;

import java.util.List;

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
            qr.update(sql, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(Integer id, User user) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "UPDATE tb_user SET username = ?, password = ? WHERE id = ?";
            Object[] params = {user.getUsername(), user.getPassword(), id};
            qr.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User readById(Integer id) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_user WHERE id = ?";
            return (User) qr.query(sql, id, new BeanHandler(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User readByName(String username) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_user WHERE username = ?";
            return (User) qr.query(sql, username, new BeanHandler(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getTotalRecord() {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT count(*) FROM tb_user";
            long l = (Long) qr.query(sql, new ScalarHandler());
            return (int) l;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUserPageDate(int beginIndex, int pageSize){
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "select * from tb_user limit ?,?";
            Object[] params = {beginIndex, pageSize};
            return (List<User>) qr.query(sql, params, new BeanListHandler(User.class));
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    /**
     * 重置密码.
     */
    public void resetPwd(String id){
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "update tb_user set password = '123456' where id=?";
            qr.update(sql,id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改密码;
     * @param user
     */
    public void updatePwd(User user){
        try{
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "update tb_user set password = ? where id=?";
            Object params[] = {user.getPassword(),user.getId()};
            qr.update(sql,params);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
