package org.cpp.gis.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.utils.JDBCUtil;

import java.util.List;

/**
 * 修改名Dao实现类.
 * Created by Rose on 2015/5/7.
 */
public class ModifyDaoImpl {

    public void create(Modify modify) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "INSERT INTO tb_modify(name, description, people, identification," +
                    "college, phone, feature_id) values(?,?,?,?,?,?,?)";
            Object[] params = {modify.getName(), modify.getDescription(), modify.getPeople(),
                modify.getIdentification(), modify.getCollege(), modify.getPhone(), modify.getFeatuere_id()};
            qr.update(sql,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据名字获取修改信息.
     * @param modifyName
     * @return
     */
    public Modify getByName(String modifyName) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_modify WHERE name = ?";
            Modify modify = (Modify) qr.query(sql, new BeanHandler(Modify.class));
            return modify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新修改名次数
     * @param i
     */
    public void updateTimes(int i) {

    }

    /**
     * 获取所有
     * @return
     */
    public int getTotalRecord() {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "select count(*) from tb_modify";
            long l = (Long) qr.query(sql, new ScalarHandler());
            return (int) l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取分页数据.
     * @param beginIndex
     * @param pageSize
     * @return
     */
    public List<Modify> getAllModifyPageData(int beginIndex, int pageSize) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "select * from tb_modify limit ?,?";
            Object[] params = {beginIndex, pageSize};

            return (List<Modify>) qr.query(sql, params, new BeanListHandler(Modify.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
