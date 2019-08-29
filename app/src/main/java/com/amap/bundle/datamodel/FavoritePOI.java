package com.amap.bundle.datamodel;

import com.autonavi.common.model.POI;

public interface FavoritePOI extends POI {
    String getChildType();

    String getClassification();

    String getCommonName();

    Long getCreateTime();

    String getCustomName();

    String getFnona();

    String getNewType();

    String getParent();

    String getPoiJson();

    String getSndtFlNona();

    String getTag();

    String getTempCityName();

    String getTopTime();

    String getTowardsAngle();

    String getUserId();

    boolean isSaved();

    void setChildType(String str);

    void setClassification(String str);

    void setCommonName(String str);

    void setCreateTime(Long l);

    void setCustomName(String str);

    void setFnona(String str);

    void setNewType(String str);

    void setParent(String str);

    void setPoiJson(String str);

    void setSaved(boolean z);

    void setSndtFlNona(String str);

    void setTag(String str);

    void setTempCityName(String str);

    void setTopTime(String str);

    void setTowardsAngle(String str);

    void setUserId(String str);
}
