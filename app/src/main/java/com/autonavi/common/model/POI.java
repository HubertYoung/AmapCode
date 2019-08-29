package com.autonavi.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public interface POI extends Serializable {
    <T extends POI> T as(Class<T> cls);

    POI clone();

    String getAdCode();

    String getAddr();

    String getCityCode();

    String getCityName();

    int getDistance();

    String getEndPoiExtension();

    ArrayList<GeoPoint> getEntranceList();

    ArrayList<GeoPoint> getExitList();

    int getIconId();

    String getIconURL();

    String getId();

    String getIndoorFloorNoName();

    String getIndustry();

    String getName();

    String getPhone();

    String getPid();

    HashMap<String, Serializable> getPoiExtra();

    GeoPoint getPoint();

    String getTransparent();

    String getType();

    void setAdCode(String str);

    void setAddr(String str);

    void setCityCode(String str);

    void setCityName(String str);

    void setDistance(int i);

    void setEndPoiExtension(String str);

    void setEntranceList(ArrayList<GeoPoint> arrayList);

    void setExitList(ArrayList<GeoPoint> arrayList);

    void setIconId(int i);

    void setIconURL(String str);

    void setId(String str);

    void setIndustry(String str);

    void setInoorFloorNoName(String str);

    void setName(String str);

    void setPhone(String str);

    void setPid(String str);

    void setPoint(GeoPoint geoPoint);

    void setTransparent(String str);

    void setType(String str);
}
