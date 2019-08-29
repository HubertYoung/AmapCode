package com.autonavi.minimap.route.bus.inter;

import android.net.Uri;
import android.os.Bundle;
import com.autonavi.bundle.routecommon.model.IRouteBusLineResult;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.bus.model.Bus;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public interface IBusLineResult extends IRouteBusLineResult {
    public static final int SHOW_NUM_ONCE = 10;

    void addBusLine(Bus bus, boolean z);

    void addBusLineArray(List<Bus> list, boolean z);

    void addBusLineArray(Bus[] busArr, boolean z);

    void addStationArray(ArrayList<POI> arrayList, boolean z);

    long getAdCode();

    Bus getBusLine(int i);

    Bus[] getBusLineArray(int i);

    ArrayList<Bus> getBuslines();

    String getCityCode();

    int getCurPoiPage();

    String getCurrentCity();

    int getFocusBusLineIndex();

    int getFocusChildIndex();

    int getFocusStationIndex();

    POI getFocusedPoi();

    int getFocusedPoiIndex();

    String getLineID();

    String getMsgId();

    ArrayList<POI> getPoiList(int i);

    int getResultType();

    String getSearchKeyword();

    int getTotalPoiPage();

    int getTotalPoiSize();

    boolean hasBuslineData();

    boolean hasStationData();

    boolean isReset();

    Bundle parse(Uri uri);

    boolean parse(JSONObject jSONObject);

    void resetAll();

    void setAdCode(long j);

    void setCityCode(String str);

    void setCurPoiPage(int i);

    void setFocusBusLineIndex(int i);

    void setFocusChildIndex(int i);

    void setFocusStationIndex(int i);

    void setFocusedPoiIndex(int i);

    void setReset(boolean z);

    void setSearchKeyword(String str);

    void setSearchPage(int i);

    void setTotalPoiSize(int i);
}
