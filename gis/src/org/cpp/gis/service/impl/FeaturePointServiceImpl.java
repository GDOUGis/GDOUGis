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

    private FeaturePointDao fpDao = DaoFactory.getInstance().createDao("org.cpp.gis.dao.impl.FeaturePointDaoImpl");

    public void addFeaturePoint(Integer Id, String name) {
        FeaturePoint fq = new FeaturePoint();
        fq.setId(Id);
        fq.setName(name);
        fpDao.create(fq);
    }

    @Override
    public void updateFeaturePointName(Integer Id, String name) {

    }

    @Override
    public void updateFeaturePointAlias(Integer Id, String name) {

    }

    @Override
    public FeaturePoint getById(Integer Id) {
        return fpDao.readById(Id);
    }

    @Override
    public FeaturePoint getByName(String name) {
        return null;
    }

    @Override
    public List<FeaturePoint> getAll() {
        return null;
    }

    @Override
    public String[] getAliasById(String id) {
        FeaturePoint fp = fpDao.readById(Integer.parseInt(id));
        if(fp.getAlias() == null || "".equals(fp.getAlias())) {
            return null;
        }
        String[] results = fp.getAlias().split("#");
        return results;
    }

    @Override
    public void addAlias(String id, String alias) {
        try {
            // 先读取该特征点的别名
            FeaturePoint fp = fpDao.readAliasById(Integer.parseInt(id));
            String oldAlias = fp.getAlias();
            if(oldAlias == null || "".equals(oldAlias.trim())) {        // 该特征点一开始就没有备用名
                System.out.println("一开始是空的："+ oldAlias);
                fpDao.updateAlias(Integer.parseInt(id), alias);
            } else {
                // 若一开始就有备用名，则在新添加的一条备用名前面加个分隔符‘#’. 如：钟海楼#阳中
                String newAlias = oldAlias + "#" + alias;
                System.out.println("新的备用名：");
                fpDao.updateAlias(Integer.parseInt(id), newAlias);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getAliasByName(String name) {
        FeaturePoint fp = fpDao.readByName(name);
        if (fp.getAlias() == null || "".equals(fp.getAlias())) {
            return null;
        }
        String[] results = fp.getAlias().split("#");
        return results;
    }
}
