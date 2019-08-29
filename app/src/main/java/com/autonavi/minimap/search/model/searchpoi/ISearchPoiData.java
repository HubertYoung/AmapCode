package com.autonavi.minimap.search.model.searchpoi;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.AutoNaviPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData;
import com.autonavi.minimap.search.templete.model.ITemplate;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public interface ISearchPoiData extends POI, ITemplate<PoiLayoutTemplate> {
    String getChildType();

    int getDisplayIconNameState();

    GeoPoint getDisplayPoint();

    String getFnona();

    String getIconSrcName();

    IndoorPoiData getIndoorPoiInfo();

    int getLabelType();

    String getMarkerBGOnline();

    int getMarkerBGRes();

    String getParent();

    AutoNaviPoiData getPoiAutoNaviInfo();

    ChildrenPoiData getPoiChildrenInfo();

    int getRecommendFlag();

    String getShortName();

    int getStatus();

    String getTowardsAngle();

    int getTraveDistance();

    int getTravelTime();

    void setChildType(String str);

    void setDisplayIconNameState(int i);

    void setDisplayPoint(GeoPoint geoPoint);

    void setFnona(String str);

    void setIconSrcName(String str);

    void setIndoorPoiInfo(IndoorPoiData indoorPoiData);

    void setLabelType(int i);

    void setMarkerBGOnline(String str);

    void setMarkerBGRes(int i);

    void setParent(String str);

    void setPoiAutoNaviInfo(AutoNaviPoiData autoNaviPoiData);

    void setPoiChildrenInfo(ChildrenPoiData childrenPoiData);

    void setRecommendFlag(int i);

    void setShortName(String str);

    void setStatus(int i);

    void setTowardsAngle(String str);

    void setTraveDistance(int i);

    void setTravelTime(int i);
}
