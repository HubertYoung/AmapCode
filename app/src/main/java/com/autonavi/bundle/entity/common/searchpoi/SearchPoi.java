package com.autonavi.bundle.entity.common.searchpoi;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.util.ArrayList;
import java.util.List;

public interface SearchPoi extends ISearchPoiData {
    String getCostTime();

    String getDeepinfo();

    int getHeatMapFlag();

    DynamicRenderData getIDynamicRenderInfo();

    boolean getIsShowName();

    boolean getIsTopList();

    String getLabel();

    int getNeedArriveTimeCost();

    int getPicStatus();

    aum getRecommendMode();

    List<SearchRecommendPoi> getRecommonPoiInfos();

    int getReferenceRltFlag();

    ArrayList<ArrayList<GeoPoint>> getRegions();

    boolean getRichInfoFlag();

    int getRoutePlanning();

    String getShowEnvironmentalMap();

    String getShowSketchingMap();

    String getSketchDuration();

    String getSketchUrl();

    String getSubType();

    String getSuperAddress();

    Double[] getViewRegions();

    String getVoiceDriveDistance();

    void setCostTime(String str);

    void setDeepinfo(String str);

    void setHeatMapFlag(int i);

    void setIDynamicRenderInfo(DynamicRenderData dynamicRenderData);

    void setIsShowName(boolean z);

    void setIsTopList(boolean z);

    void setLabel(String str);

    void setNeedArriveTimeCost(int i);

    void setPicStatus(int i);

    void setRecommendMode(aum aum);

    void setRecommonPoiInfos(List<SearchRecommendPoi> list);

    void setReferenceRltFlag(int i);

    void setRegions(ArrayList<ArrayList<GeoPoint>> arrayList);

    void setRichInfoFlag(boolean z);

    void setRoutePlanning(int i);

    void setShowEnvironmentalMap(String str);

    void setShowSketchingMap(String str);

    void setSketchDuration(String str);

    void setSketchUrl(String str);

    void setSubType(String str);

    void setSuperAddress(String str);

    void setViewRegions(Double[] dArr);

    void setVoiceDriveDistance(String str);
}
