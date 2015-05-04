package org.cpp.gis.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
            String sql = "INSERT INTO tb_features(id, name) VALUES(?,?)";
            Object params[] = {featurePoint.getId(), featurePoint.getName()};
            qr.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateName(Integer Id, String name) {

    }

    @Override
    public void updateAlias(Integer Id, String alias) {

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
        return null;
    }

    @Override
    public List<FeaturePoint> readAll() {
        return null;
    }
}
