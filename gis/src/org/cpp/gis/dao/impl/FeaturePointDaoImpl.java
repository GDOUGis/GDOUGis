package org.cpp.gis.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.cpp.gis.dao.FeaturePointDao;
import org.cpp.gis.entities.FeaturePoint;
import org.cpp.gis.utils.JDBCUtil;

import java.util.List;

/**
 * 特征点Dao实现类.
 * Created by Rose on 2015/5/3.
 */
public class FeaturePointDaoImpl implements FeaturePointDao{

    @Override
    public void create(FeaturePoint featurePoint) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "INSERT INTO tb_features(id, name, description, prepareName) VALUES(?, ?, ?, ?)";
            Object params[] = {featurePoint.getId(), featurePoint.getName(), featurePoint.getDescription(),
                featurePoint.getPrepareName()};
            qr.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateName(Integer Id, String name) {

    }

    @Override
    public void updateAlias(Integer id, String alias) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "UPDATE tb_features SET prepareName = ? WHERE id = ? ";
            Object[] params = {alias, id};
            qr.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public FeaturePoint readById(Integer Id) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_features WHERE id = ?";
            Object params[] = {Id};
            FeaturePoint fq = (FeaturePoint) qr.query(sql, params, new BeanHandler(FeaturePoint.class));
            return  fq;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FeaturePoint readByName(String name) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_features WHERE name = ?";
            Object params[] = {name};
            FeaturePoint fq = (FeaturePoint) qr.query(sql, params, new BeanHandler(FeaturePoint.class));
            return  fq;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FeaturePoint> readAll() {
        return null;
    }

    @Override
    public FeaturePoint readAliasById(Integer id) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT alias FROM tb_features WHERE id = ?";
            return (FeaturePoint) qr.query(sql, id, new BeanHandler(FeaturePoint.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取特征点的总记录数.
     * @return
     */
    @Override
    public int getTotalRecord() {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT COUNT(*) FROM tb_features";
            long l = (Long) qr.query(sql, new ScalarHandler());
            return (int) l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
