package com.autonavi.minimap.drive.route;

import android.content.Intent;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import java.util.ArrayList;

public interface IDriveRouteResult extends IRouteResultData {
    String genMethodStr(int i);

    Intent getArgIntent();

    CalcRouteResult getCalcRouteResult();

    CalcRouteScene getCalcRouteScene();

    String getCarPlate();

    int getFocusRouteIndex();

    int getFocusStationIndex();

    String getFromCityCode();

    int getGotoNaviDlgIndex();

    POI getMainPoi();

    int getRecommendFlag();

    GeoPoint getShareEndPos();

    ArrayList<GeoPoint> getShareMidPoses();

    GeoPoint getShareStartPos();

    int getStationsCount();

    String getToCityCode();

    boolean hasMidPos();

    boolean isLongDisResult();

    boolean isM_bNative();

    boolean isSceneResult();

    boolean isServiceAreaMode();

    boolean isSuggestOnfoot();

    boolean isViaCityMode();

    boolean isViaRoadMode();

    void setArgIntent(Intent intent);

    void setCalcRouteResult(CalcRouteResult calcRouteResult);

    void setCarPlate(String str);

    void setFocusRouteIndex(int i);

    void setFocusStationIndex(int i);

    void setGotoNaviDlgIndex(int i);

    void setM_bNative(boolean z);

    void setMainPoi(POI poi);

    void setRecommendFlag(int i);

    void setSceneResult(boolean z);

    void setServiceAreaMode(boolean z);

    void setShareEndPos(GeoPoint geoPoint);

    void setShareMidPos(ArrayList<GeoPoint> arrayList);

    void setShareStartPos(GeoPoint geoPoint);

    void setStationCount(int i);

    void setSuggestOnfoot(boolean z);

    void setViaCityMode(boolean z);

    void setViaRoadMode(boolean z);
}
