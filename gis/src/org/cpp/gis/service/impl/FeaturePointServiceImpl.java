package org.cpp.gis.service.impl;

import org.cpp.gis.dao.FeaturePointDao;
import org.cpp.gis.entities.FeaturePoint;
import org.cpp.gis.factory.DaoFactory;
import org.cpp.gis.service.FeaturePointService;

import java.util.List;

/**
 * 特征点Service实现类.
 * Created by Rose on 2015/5/3.
 */
public class FeaturePointServiceImpl implements FeaturePointService{

    private FeaturePointDao fqDao = DaoFactory.getInstance().createDao("org.cpp.gis.dao.impl.FeaturePointDaoImpl");

    public void addFeaturePoint(Integer Id, String name) {
        FeaturePoint fq = new FeaturePoint();
        fq.setId(Id);
        fq.setName(name);
        fqDao.create(fq);
    }

    @Override
    public void updateFeaturePointName(Integer Id, String name) {

    }

    @Override
    public void updateFeaturePointAlias(Integer Id, String name) {

    }

    @Override
    public FeaturePoint getById(Integer Id) {
        return fqDao.readById(Id);
    }

    @Override
    public FeaturePoint getByName(String name) {
        return null;
    }

    @Override
    public List<FeaturePoint> getAll() {
        return null;
    }
}
