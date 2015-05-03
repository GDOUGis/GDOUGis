package org.cpp.gis.entities;

/**
 * 特征点实体
 * Created by Rose on 2015/5/3.
 */
public class FeaturePoint {
    private Integer id;
    private String name;
    private Double x;
    private Double y;

    public FeaturePoint() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public FeaturePoint(Integer id, String name, Double x, Double y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
