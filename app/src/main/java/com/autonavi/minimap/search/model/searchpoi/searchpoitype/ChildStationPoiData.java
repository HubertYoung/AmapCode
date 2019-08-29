package com.autonavi.minimap.search.model.searchpoi.searchpoitype;

import com.autonavi.common.model.POI;

public interface ChildStationPoiData extends POI {
    String getBusAngle();

    String getBusinfoAlias();

    String getBusinfoStationids();

    String getBuslineIds();

    String getBuslineKey();

    String getBuslineName();

    void getDeepinfo();

    void getDistence();

    String getPoiId();

    String getPoiId2();

    void setBusAngle(String str);

    void setBusinfoAlias(String str);

    void setBusinfoStationids(String str);

    void setBuslineIds(String str);

    void setBuslineKey(String str);

    void setBuslineName(String str);

    void setDeepinfo(String str);

    void setDistence(String str);

    void setPoiId(String str);

    void setPoiId2(String str);
}
