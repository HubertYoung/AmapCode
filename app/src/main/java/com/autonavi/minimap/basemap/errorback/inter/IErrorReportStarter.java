package com.autonavi.minimap.basemap.errorback.inter;

import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IErrorReportStarter extends bie {
    public static final String POI_TYPE_DETAIL_NORMAL = "normalpoi";
    public static final String POI_TYPE_INVALID_POI = "invalidpoi";
    public static final String POI_TYPE_NON_EXIST = "nonexistpoi";
    public static final int REPORT_TYPE_ADD_LOCATION = 1;
    public static final int REPORT_TYPE_INFO_ERROR = 2;
    public static final int REPORT_TYPE_NOT_EXIST = 3;
    public static final int REQUEST_CODE_NAVI_END_PAY_FOR = 10001;
    public static final int ROUTE_FEEDBACK_ACTION_BUS = 0;
    public static final int ROUTE_FEEDBACK_ACTION_BUS_LINE = 1;
    public static final int ROUTE_FEEDBACK_ACTION_FOOT = 2;
    public static final int ROUTE_FEEDBACK_ACTION_NAVI_BUS = 3;
    public static final int ROUTE_FEEDBACK_ACTION_NAVI_FOOT = 4;
    public static final int ROUTE_FEEDBACK_ACTION_NAVI_FOOT_END = 5;

    void doFastReportError(String str);

    void doReportError(MapManager mapManager, coi coi);

    String getNameBySourcePage(int i);

    void startAddIndoorPoi(bid bid, POI poi);

    void startAddPOIFromXYSelectPoint(POI poi);

    void startAddPoi(bid bid, int i);

    void startAddPoi(POI poi);

    void startAddPoi(POI poi, JSONObject jSONObject);

    void startAddPoiFastReport(bid bid, POI poi);

    void startAddPoiFeedback(bid bid, POI poi);

    void startAddPoiFromSearch(bid bid, String str);

    void startAddPoiFromSearch(POI poi);

    void startAddPoiWhenLocation(bid bid, POI poi, PageBundle pageBundle);

    void startFeedback(bid bid);

    void startFeedback(PageBundle pageBundle);

    void startFeedbackReport();

    void startIndoorError(bid bid, POI poi);

    void startLocationError(POI poi);

    void startNormalFeedbackPage(bid bid, String str);

    void startOfflineMapError(bid bid);

    void startPOIError(bid bid, POI poi);

    void startPOIError(bid bid, POI poi, JSONObject jSONObject);

    void startPoiDetailFeedback(bid bid, POI poi, int i, JSONObject jSONObject);

    void startStationError(bid bid, POI poi);

    void startStationError(bid bid, POI poi, String str);

    void startVoiceSearch();
}
