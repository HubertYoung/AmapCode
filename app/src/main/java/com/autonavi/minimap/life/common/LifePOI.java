package com.autonavi.minimap.life.common;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.templete.model.ITemplate;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public interface LifePOI extends POI, ITemplate<PoiLayoutTemplate> {
    String getImageURL();

    String getMinCost();

    String getSceneLevel();

    void setImageURL(String str);

    void setMinCost(String str);

    void setSceneLevel(String str);
}
