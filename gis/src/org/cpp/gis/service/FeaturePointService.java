package org.cpp.gis.service;

import org.cpp.gis.entities.FeaturePoint;

import java.util.List;

/**
 * 特征点Service接口.
 * Created by Rose on 2015/5/3.
 */
public interface FeaturePointService {

    public void addFeaturePoint(FeaturePoint featurePoint);

    public void updateFeaturePointName(Integer Id, String name);

    public void updateFeaturePointAlias(Integer Id, String name);

    public FeaturePoint getById(Integer Id);

    public FeaturePoint getByName(String name);

    public List<FeaturePoint> getAll();

    String[] getAliasById(String id);

    void addAlias(String id, String alias);

    String[] getAliasByName(String name);
}
