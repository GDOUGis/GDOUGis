package org.cpp.gis.dao;

import org.cpp.gis.entities.FeaturePoint;

import java.util.List;

/**
 * 特征点Dao接口.
 * Created by Rose on 2015/5/3.
 */
public interface FeaturePointDao {

    /**
     * 添加一条特征点记录.
     * @author Rose.
     * @param featurePoint
     */
    public void create(FeaturePoint featurePoint);

    /**
     * 更新特征点的名称.
     * @param Id
     * @param name
     */
    public void updateName(Integer Id, String name);

    /**
     * 更新备用名.
     * @author Rose.
     * @param Id
     * @param alias
     */
    public void updateAlias(Integer Id, String alias);

    /**
     * 读取一条数据根据Id.
     * @author Rose.
     * @param Id
     * @return
     */
    public FeaturePoint readById(Integer Id);

    /**
     * 读取一条数据根据名称.
     * @author Rose.
     * @param name
     * @return
     */
    public FeaturePoint readByName(String name);

    /**
     * 读取全部特征点.
     * @author Rose.
     * @return
     */
    public List<FeaturePoint> readAll();
}
