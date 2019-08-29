package com.amap.bundle.drive.ajx.module;

import android.support.annotation.Nullable;
import com.amap.bundle.drive.ajx.inter.IEventDetailDialog;
import com.amap.bundle.drive.ajx.inter.JsCommandCallback;
import com.amap.bundle.drive.ajx.inter.OnCalRouteFromData;
import com.amap.bundle.drive.ajx.inter.OnObtainFocusCallBack;
import com.amap.bundle.drive.ajx.inter.OnRouteStateChangeListener;
import com.amap.bundle.drive.ajx.inter.OnTripPoiChangeListener;
import com.amap.bundle.drive.ajx.inter.RouteEventActionInterface;
import com.amap.bundle.drive.ajx.inter.SearchAlongCallback;
import com.amap.bundle.drive.ajx.inter.TruckTopHeightCallBack;
import com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager;
import com.amap.bundle.drive.result.motorresult.result.tip.AjxMotorTipsManager;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("route_drive_result")
@Keep
@KeepClassMembers
public class ModuleRouteDriveResult extends AbstractModule {
    public static final String AJX_JS_INFO_RUNTIME_ROUTE_TYPE = "RouteType";
    public static final String CAR_EVENT_DETAIL = "path://amap_bundle_drive/src/car/result_page/tips_detail/CarTipsDetailPage.page.js";
    public static final String CAR_MOCK_NAVI = "path://amap_bundle_drive/src/car/navi_page/CarNaviSimulatePage.page.js";
    public static final String CAR_NAVI = "path://amap_bundle_drive/src/car/navi_page/CarNaviPage.page.js";
    public static final String CAR_NAVI_PRE_LOAD = "path://amap_bundle_drive/src/car/result_page/carNaviPage_preload.js";
    public static final String CAR_PREVIEW = "path://amap_bundle_drive/src/car/preview_page/CarPreviewPage.page.js";
    private static final int HIDE_PAGE_SEARCH_ALONG = 0;
    public static final String MODULE_NAME = "route_drive_result";
    public static final String MOTOR_ADD = "path://amap_bundle_motorbike/src/navi_page/component/guide/views/MotorBikeSetNumber.page.js";
    public static final String MOTOR_EVENT_DETAIL = "path://amap_bundle_motorbike/src/result_page/tips_detail/MotorBikeTipsDetailPage.page.js";
    public static final String MOTOR_MOCK_NAVI = "path://amap_bundle_motorbike/src/navi_page/MotorBikeNaviSimulatePage.page.js";
    public static final String MOTOR_NAVI = "path://amap_bundle_motorbike/src/navi_page/MotorBikeNaviPage.page.js";
    public static final String MOTOR_PREVIEW = "path://amap_bundle_motorbike/src/preview_page/MotorBikePreviewPage.page.js";
    public static final String PAGE_SOURCE_TYPE_COMMON = "source_common";
    public static final String PAGE_SOURCE_TYPE_ETRIP = "source_etrip";
    public static final String PAGE_SOURCE_TYPE_FAVORITE = "source_save";
    private static final int START_PAGE_SEARCH_ALONG = 1;
    private static final String TAG = "ModuleRouteDriveResult";
    public static final String URL_CAR_ROUTE = "path://amap_bundle_drive/src/car/result_page/CarResultPage.page.js";
    public static final String URL_MOTOR_ROUTE = "path://amap_bundle_motorbike/src/result_page/MotorBikeResultPage.page.js";
    private JsFunctionCallback mJsSearchAlongViewOnclickCallback = null;
    private ModuleRouteDriveResultImpl mModuleRouteDriveResultImpl = new ModuleRouteDriveResultImpl();
    private RouteType mRouteType;
    private TruckTopHeightCallBack mTruckTopHeightCallBack;

    public ModuleRouteDriveResult(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.mRouteType = (RouteType) iAjxContext.getRunParam(AJX_JS_INFO_RUNTIME_ROUTE_TYPE);
    }

    @AjxMethod("registerErrorReportOnClickCallback")
    public void registerErrorReportOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerErrorReportOnClickCallback(jsFunctionCallback);
    }

    public void setSearchAlongViewOnclickCallback() {
        if (this.mJsSearchAlongViewOnclickCallback != null) {
            this.mJsSearchAlongViewOnclickCallback.callback(new JSONObject().toString());
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getHistoryClickTime")
    public String getHistoryClickTime() {
        return this.mModuleRouteDriveResultImpl.getHistoryClickTime();
    }

    @AjxMethod("registerJsCalcRouteCallback")
    public void registerJsCalcRouteCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerJsCalcRouteCallback(jsFunctionCallback);
    }

    @AjxMethod("registerMitVuiRouteResultEventCallback")
    public void registerMitVuiRouteResultEventCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerMitVuiRouteResultEventCallback(jsFunctionCallback);
    }

    @AjxMethod("registerAlongSearchOnClickCallback")
    public void registerAlongSearchOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerAlongSearchOnClickCallback(jsFunctionCallback);
    }

    @AjxMethod("registerWeatherAreaRequestCallBack")
    public void registerWeatherAreaRequestCallBack(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerWeatherAreaRequestCallBack(jsFunctionCallback);
    }

    @AjxMethod("registerDriveRadarPageEventCallback")
    public void registerDriveRadarPageEventCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerDriveRadarPageEventCallback(jsFunctionCallback);
    }

    @AjxMethod("registerSurfaceAreaSizeChangeCallBack")
    public void registerSurfaceAreaSizeChangeCallBack(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerSurfaceAreaSizeChangeCallBack(jsFunctionCallback);
    }

    @AjxMethod("registerViaCityOnClickCallback")
    public void registerViaCityOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerViaCityOnClickCallback(jsFunctionCallback);
    }

    @AjxMethod("registerViaroadOnClickCallback")
    public void registerViaroadOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerViaroadOnClickCallback(jsFunctionCallback);
    }

    @AjxMethod("registerServiceAreaOnClickCallback")
    public void registerServiceAreaOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerServiceAreaOnClickCallback(jsFunctionCallback);
    }

    @AjxMethod("registerClearSearchResultOnClickCallback")
    public void registerClearSearchResultOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerClearSearchResultOnClickCallback(jsFunctionCallback);
    }

    @AjxMethod("registerWeatherAreaOnClickCallback")
    public void registerWeatherAreaOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerWeatherAreaOnClickCallback(jsFunctionCallback);
    }

    @AjxMethod("registerSupplySwitchOnClickCallback")
    public void registerSupplySwitchOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerSupplySwitchOnClickCallback(jsFunctionCallback);
    }

    @AjxMethod("registerInspectionStationOnClickCallback")
    public void registerInspectionStationOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerInspectionStationOnClickCallback(jsFunctionCallback);
    }

    @AjxMethod("registerAccessNaviCallback")
    public void registerAccessNaviCallback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerAccessNaviCallback(jsFunctionCallback);
    }

    @AjxMethod("calcRouteFromData")
    public void calcRouteFromData() {
        this.mModuleRouteDriveResultImpl.calcRouteFromData();
    }

    @AjxMethod("registerVUILayerListener")
    public void registerVUILayerListener(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerVUILayerListener(jsFunctionCallback);
    }

    @AjxMethod(invokeMode = "sync", value = "getWeatherSwitchState")
    public int getWeatherSwitchState() {
        return this.mModuleRouteDriveResultImpl.getWeatherSwitchState();
    }

    @AjxMethod(invokeMode = "sync", value = "ajxBackPress")
    public void ajxBackPress() {
        this.mModuleRouteDriveResultImpl.ajxBackPress();
    }

    @AjxMethod("setEndPoi")
    public void setEndPoi(String str) {
        this.mModuleRouteDriveResultImpl.setEndPoi(str);
    }

    @AjxMethod("setMidPoi")
    public void setMidPoi(String str) {
        this.mModuleRouteDriveResultImpl.setMidPoi(str);
    }

    @AjxMethod("jump")
    public void jump(String str, String str2) {
        this.mModuleRouteDriveResultImpl.jump(str, str2);
    }

    @AjxMethod(invokeMode = "sync", value = "getRequestRouteParam")
    public String getRequestRouteParam() {
        return this.mModuleRouteDriveResultImpl.getRequestRouteParam(this.mRouteType);
    }

    @AjxMethod("calcRouteStateChange")
    public void calcRouteStateChange(int i, String str) {
        if (bno.a) {
            tq.b("NaviMonitor", TAG, "calcRouteStateChange json=".concat(String.valueOf(str)));
        }
        this.mModuleRouteDriveResultImpl.calcRouteStateChange(i, str);
    }

    @AjxMethod("onReportErrorClick")
    public void onReportErrorClick() {
        this.mModuleRouteDriveResultImpl.onReportErrorClick();
    }

    @AjxMethod("updateRouteFocusIndex")
    public void updateRouteFocusIndex(String str) {
        this.mModuleRouteDriveResultImpl.updateRouteFocusIndex(str);
    }

    @AjxMethod("regeoPOI")
    public void regeoPOI(String str) {
        this.mModuleRouteDriveResultImpl.regeoPOI(str);
    }

    @AjxMethod("setHeaderViewAlpha")
    public void setHeaderViewAlpha() {
        this.mModuleRouteDriveResultImpl.setHeaderViewAlpha();
    }

    @AjxMethod("setAroundSearchResult")
    @Deprecated
    public void setAroundSearchResult(int i) {
        this.mModuleRouteDriveResultImpl.setAroundSearchResult(i);
    }

    @AjxMethod("onShareClick")
    public void onShareClick() {
        this.mModuleRouteDriveResultImpl.onShareClick();
    }

    @AjxMethod("onMapEventClick")
    public void onMapEventClick(String str) {
        this.mModuleRouteDriveResultImpl.onMapEventClick(str);
    }

    @AjxMethod("onObtainMapLayerClick")
    public void onObtainMapLayerClick(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.onObtainMapLayerClick(jsFunctionCallback);
    }

    @AjxMethod("addFavoriteRoute")
    public void addFavoriteRoute(int i, String str, JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.addFavoriteRoute(i, str, jsFunctionCallback);
    }

    @AjxMethod("removeFavoriteRoute")
    @Deprecated
    public void removeFavoriteRoute(int i, String str, JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.removeFavoriteRoute(i, str, jsFunctionCallback);
    }

    @AjxMethod("isFavoriteRoute")
    public void isFavoriteRoute(int i, String str, JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.isFavoriteRoute(i, str, jsFunctionCallback);
    }

    @AjxMethod("setSearchRouteInNeMode")
    public void setSearchRouteInNeMode(boolean z) {
        this.mModuleRouteDriveResultImpl.setSearchRouteInNeMode(z);
    }

    @AjxMethod("registerResultPageMapStateback")
    public void registerResultPageMapStateback(JsFunctionCallback jsFunctionCallback) {
        this.mModuleRouteDriveResultImpl.registerResultPageMapStateback(jsFunctionCallback);
    }

    @AjxMethod("setEventDetailDialogHeight")
    public void setEventDetailDialogHeight(String str) {
        this.mModuleRouteDriveResultImpl.setEventDetailDialogHeight(str);
    }

    @AjxMethod("clearAllTips")
    public void clearAllTips() {
        this.mModuleRouteDriveResultImpl.clearAllTips();
    }

    @AjxMethod(invokeMode = "sync", value = "getTopHeight")
    public String getTopHeight() {
        return String.valueOf(Float.valueOf(this.mTruckTopHeightCallBack != null ? DimensionUtils.pixelToStandardUnit(getNativeContext(), (float) this.mTruckTopHeightCallBack.getTopHeight()) : 0.0f).intValue());
    }

    @AjxMethod(invokeMode = "sync", value = "isClickedETDEntrance")
    public boolean isClickedETDEntrance() {
        return DriveUtil.isClickedETDEntrance();
    }

    @AjxMethod(invokeMode = "sync", value = "setClickedETDEntrance")
    public void setClickedETDEntrance() {
        DriveUtil.clickedETDEntrance();
    }

    public void requestCarRoute(String str) {
        this.mModuleRouteDriveResultImpl.requestCarRoute(str);
    }

    public void requestJsurfaceAreaSizeChange(int i, int i2) {
        this.mModuleRouteDriveResultImpl.requestJsurfaceAreaSizeChange(i, i2);
    }

    public void requestJsSuspendClickEvent(int i, boolean z) {
        this.mModuleRouteDriveResultImpl.requestJsSuspendClickEvent(i, z);
    }

    public void triggerAccessNaviCallback() {
        this.mModuleRouteDriveResultImpl.triggerAccessNaviCallback();
    }

    public void requestJsWeatherAreaRequest() {
        this.mModuleRouteDriveResultImpl.requestJsWeatherAreaRequest();
    }

    public void bindTipsManager(AjxTipsManager ajxTipsManager) {
        this.mModuleRouteDriveResultImpl.bindTipsManager(ajxTipsManager);
    }

    public void bindTipsManager(AjxMotorTipsManager ajxMotorTipsManager) {
        this.mModuleRouteDriveResultImpl.bindTipsManager(ajxMotorTipsManager);
    }

    public void dispatchVUILayerEvent(boolean z) {
        this.mModuleRouteDriveResultImpl.dispatchVUILayerEvent(z);
    }

    public void addSwitchSceneListener(mq mqVar) {
        this.mModuleRouteDriveResultImpl.addSwitchSceneListener(mqVar);
    }

    public void addRouteEventActionInterface(RouteEventActionInterface routeEventActionInterface) {
        this.mModuleRouteDriveResultImpl.addRouteEventActionInterface(routeEventActionInterface);
    }

    public void removeRouteEventInterface(RouteEventActionInterface routeEventActionInterface) {
        this.mModuleRouteDriveResultImpl.removeRouteEventInterface(routeEventActionInterface);
    }

    public void removeSwitchSceneListener(mq mqVar) {
        this.mModuleRouteDriveResultImpl.removeSwitchSceneListener(mqVar);
    }

    public void setGpsButtonActionInterface(mr mrVar) {
        this.mModuleRouteDriveResultImpl.setGpsButtonActionInterface(mrVar);
    }

    public void setJsCommandCallback(JsCommandCallback jsCommandCallback) {
        this.mModuleRouteDriveResultImpl.setJsCommandCallback(jsCommandCallback);
    }

    public void setOnCalRouteFromData(OnCalRouteFromData onCalRouteFromData) {
        this.mModuleRouteDriveResultImpl.setOnCalRouteFromData(onCalRouteFromData);
    }

    public void setOnObtainFocusCallBack(OnObtainFocusCallBack onObtainFocusCallBack) {
        this.mModuleRouteDriveResultImpl.setOnObtainFocusCallBack(onObtainFocusCallBack);
    }

    public void setOnRouteStateChangeListener(OnRouteStateChangeListener onRouteStateChangeListener) {
        this.mModuleRouteDriveResultImpl.setOnRouteStateChangeListener(onRouteStateChangeListener);
    }

    public void setOnTripPoiChangeListener(OnTripPoiChangeListener onTripPoiChangeListener) {
        this.mModuleRouteDriveResultImpl.setOnTripPoiChangeListener(onTripPoiChangeListener);
    }

    public void setSearchAlongCallback(SearchAlongCallback searchAlongCallback) {
        this.mModuleRouteDriveResultImpl.setSearchAlongCallback(searchAlongCallback);
    }

    public void setWeatherSwitchState(boolean z) {
        this.mModuleRouteDriveResultImpl.setWeatherSwitchState(z);
    }

    public void requestServiceAreaShowHide(boolean z) {
        this.mModuleRouteDriveResultImpl.requestServiceAreaShowHide(z);
    }

    public void requestSupplyShowHide(boolean z) {
        this.mModuleRouteDriveResultImpl.requestSupplyShowHide(z);
    }

    public void setIEventDetailDialog(IEventDetailDialog iEventDetailDialog) {
        this.mModuleRouteDriveResultImpl.setIEventDetailDialog(iEventDetailDialog);
    }

    public int getFocusIndex() {
        return this.mModuleRouteDriveResultImpl.getFocusIndex();
    }

    public void startDriveRadarPage() {
        this.mModuleRouteDriveResultImpl.requestJsDriveRadarEvent(new JSONObject().toString());
    }

    public void showSearchAlongView() {
        try {
            new JSONObject().put("type", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        alongSearchActionCallback(1);
    }

    public void hideSearchAlongView() {
        try {
            new JSONObject().put("type", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        alongSearchActionCallback(0);
    }

    private void alongSearchActionCallback(int i) {
        if (this.mModuleRouteDriveResultImpl != null) {
            this.mModuleRouteDriveResultImpl.alongSearchActionCallback(i);
        }
    }

    public void clearSearchAlongResult() {
        requestJsSuspendClickEvent(1006, true);
    }

    public void callbackErrorReportClick(boolean z) {
        this.mModuleRouteDriveResultImpl.callbackErrorReportClick(z);
    }

    public void setCarResultMapState() {
        this.mModuleRouteDriveResultImpl.setCarResultMapState();
    }

    public void requestMitVuiRouteResult(String str) {
        this.mModuleRouteDriveResultImpl.requestMitVuiRouteResult(str);
    }

    public void setTruckTopHeightCallback(TruckTopHeightCallBack truckTopHeightCallBack) {
        this.mTruckTopHeightCallBack = truckTopHeightCallBack;
    }

    public void updateFromPage(@Nullable String str) {
        this.mModuleRouteDriveResultImpl.updateFromPage(str);
    }
}
