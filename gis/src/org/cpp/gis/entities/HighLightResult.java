package org.cpp.gis.entities;

import com.mapinfo.dp.Feature;

/**
 * Created by Seven on 2015/5/21.
 */
public class HighLightResult {

    private String layerName;
    private Feature feature;


    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}
