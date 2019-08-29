package com.autonavi.bundle.routecommon.entity;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepClassMemberNames;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMemberNames
@KeepName
@KeepImplementations
public interface IBusRouteResult extends IRouteResultData {
    int getAlternative();

    byte[] getBaseData();

    String getBaseDataForFavorite(int i);

    String getBsid();

    BusPath getBusPathWithIndex(int i);

    BusPaths getBusPathsResult();

    ArrayList<axp> getBusResultFootErrorData();

    String getBusUserMethod();

    ExtBusPath getExtBusPath(int i);

    ArrayList<ExtBusPath> getExtBusPathList();

    BusPath getFocusBusPath();

    int getFocusBusPathIndex();

    int getFocusExBusPathIndex();

    ExtBusPath getFocusExtBusPath();

    int getFocusStationIndex();

    long getReqTime();

    boolean hasRealTimeBusLine();

    boolean isExistOutageBus();

    boolean isExtBusResult();

    boolean isRidePath();

    boolean parse(JSONObject jSONObject, int i) throws JSONException, Exception;

    void setBaseData(byte[] bArr);

    void setBusPathsData(POI poi, POI poi2, BusPaths busPaths, String str);

    void setExtBusResultFlag(boolean z);

    void setFocusBusPathIndex(int i);

    void setFocusExtBusPath(int i);

    void setFocusStationIndex(int i);

    void setReqTime(long j);
}
