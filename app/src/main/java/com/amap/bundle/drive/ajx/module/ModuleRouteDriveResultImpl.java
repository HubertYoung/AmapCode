package com.amap.bundle.drive.ajx.module;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.amap.bundle.drive.ajx.inter.IEventDetailDialog;
import com.amap.bundle.drive.ajx.inter.JsCommandCallback;
import com.amap.bundle.drive.ajx.inter.OnCalRouteFromData;
import com.amap.bundle.drive.ajx.inter.OnObtainFocusCallBack;
import com.amap.bundle.drive.ajx.inter.OnRouteStateChangeListener;
import com.amap.bundle.drive.ajx.inter.OnTripPoiChangeListener;
import com.amap.bundle.drive.ajx.inter.RouteEventActionInterface;
import com.amap.bundle.drive.ajx.inter.SearchAlongCallback;
import com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager;
import com.amap.bundle.drive.result.motorresult.result.tip.AjxMotorTipsManager;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModuleRouteDriveResultImpl {
    public static final int ANIMATION_CLOSE = 0;
    public static final int ANIMATION_OPEN = 1;
    private static final int DETAIL_STATE = 0;
    private static final int MAP_STATE = 1;
    private static final String TAG = "ModuleRouteDriveResultImpl";
    private int focusIndex = 0;
    private JsFunctionCallback mClearSearchResultOnClickCallback;
    private List<mq> mDriveSwitchSceneCallback = new ArrayList();
    private String mFromPage;
    private IEventDetailDialog mIEventDetailDialog = null;
    private JsFunctionCallback mInspectionStationOnClickCallback;
    private JsFunctionCallback mJsAccessNaviCallback;
    private JsFunctionCallback mJsAlongSearchOnclickCallBack;
    private JsFunctionCallback mJsCalcRouteCallBack;
    /* access modifiers changed from: private */
    public JsCommandCallback mJsCommandCallback;
    private JsFunctionCallback mJsDriveRadarPageEvent;
    private JsFunctionCallback mJsMitVuiRouteResultEventCallback = null;
    private JsFunctionCallback mJsOnClickErrorReportClickListener = null;
    private JsFunctionCallback mJsResultPageMapStateEventCallBack = null;
    private JsFunctionCallback mJsSupplyShowHideCallBack;
    private JsFunctionCallback mJsSurfaceAreaSizeChangeCallback;
    private JsFunctionCallback mJsWeatherAreaRequestCallBack;
    private AjxMotorTipsManager mMotorTipsManager;
    private OnCalRouteFromData mOnCalRouteFromData;
    private OnObtainFocusCallBack mOnObtainFocusCallBack;
    private List<RouteEventActionInterface> mRouteEventActionInterfaceList = new ArrayList();
    private OnRouteStateChangeListener mRouteStateChangeListener;
    private SearchAlongCallback mSearchAlongCallback;
    private JsFunctionCallback mServiceAreaOnClickCallback;
    private String mSmartTipsHeightCache;
    private AjxTipsManager mTipsManager;
    private OnTripPoiChangeListener mTripPoiChangeListener;
    private JsFunctionCallback mVUILayerListner;
    private JsFunctionCallback mViaCityOnClickCallback;
    private JsFunctionCallback mViaRoadOnClickCallback;
    private JsFunctionCallback mWeatherAreaOnClickCallback;
    private int weatherSwitchState = 0;

    public void setGpsButtonActionInterface(mr mrVar) {
    }

    public void registerClearSearchResultOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mClearSearchResultOnClickCallback = jsFunctionCallback;
    }

    public void registerDriveRadarPageEventCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsDriveRadarPageEvent = jsFunctionCallback;
    }

    public void registerJsCalcRouteCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsCalcRouteCallBack = jsFunctionCallback;
    }

    public void registerAlongSearchOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsAlongSearchOnclickCallBack = jsFunctionCallback;
    }

    public void registerServiceAreaOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mServiceAreaOnClickCallback = jsFunctionCallback;
    }

    public void requestServiceAreaShowHide(boolean z) {
        if (this.mServiceAreaOnClickCallback != null) {
            this.mServiceAreaOnClickCallback.callback(buildMotorSuspendClickString(z));
        }
    }

    public String buildMotorSuspendClickString(boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("is_click", z);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public void registerSurfaceAreaSizeChangeCallBack(JsFunctionCallback jsFunctionCallback) {
        this.mJsSurfaceAreaSizeChangeCallback = jsFunctionCallback;
        if (!TextUtils.isEmpty(this.mSmartTipsHeightCache)) {
            jsFunctionCallback.callback(this.mSmartTipsHeightCache);
            this.mSmartTipsHeightCache = null;
        }
    }

    public void registerViaCityOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mViaCityOnClickCallback = jsFunctionCallback;
    }

    public void registerViaroadOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mViaRoadOnClickCallback = jsFunctionCallback;
    }

    public void registerWeatherAreaRequestCallBack(JsFunctionCallback jsFunctionCallback) {
        this.mJsWeatherAreaRequestCallBack = jsFunctionCallback;
    }

    public void registerWeatherAreaOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mWeatherAreaOnClickCallback = jsFunctionCallback;
    }

    public void registerInspectionStationOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mInspectionStationOnClickCallback = jsFunctionCallback;
    }

    public void registerSupplySwitchOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsSupplyShowHideCallBack = jsFunctionCallback;
    }

    public void requestSupplyShowHide(boolean z) {
        if (this.mJsSupplyShowHideCallBack != null) {
            this.mJsSupplyShowHideCallBack.callback(buildMotorSuspendClickString(z));
        }
    }

    public void ajxBackPress() {
        aho.a(new Runnable() {
            public void run() {
                if (ModuleRouteDriveResultImpl.this.mJsCommandCallback != null) {
                    ModuleRouteDriveResultImpl.this.mJsCommandCallback.callback(6, "");
                }
            }
        });
    }

    public void calcRouteFromData() {
        if (this.mOnCalRouteFromData != null) {
            this.mOnCalRouteFromData.onCalRouteFromData();
        }
    }

    public String getHistoryClickTime() {
        long j = td.a().b;
        String str = "";
        if (j != -1) {
            str = String.valueOf(j);
        }
        td.a().a(0);
        td.b((String) "重置埋点状态");
        return str;
    }

    public int getWeatherSwitchState() {
        return this.weatherSwitchState;
    }

    public void setEndPoi(String str) {
        int i;
        updateFromPage("planresult_passpoint");
        POI a = bnx.a(str);
        try {
            i = new JSONObject(str).optInt(Key.SOURCE_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
            i = 0;
        }
        AMapLog.d(TAG, "setEndPoi--sourceType=".concat(String.valueOf(i)));
        if (this.mTripPoiChangeListener != null) {
            this.mTripPoiChangeListener.onEndPOIChanged(a, i);
        }
    }

    public void setMidPoi(String str) {
        updateFromPage("planresult_passpoint");
        try {
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                arrayList.add(bnx.a(((JSONObject) jSONArray.get(i)).toString()));
            }
            if (this.mTripPoiChangeListener != null) {
                this.mTripPoiChangeListener.onMidPOIChanged(arrayList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void jump(String str, String str2) {
        if (this.mDriveSwitchSceneCallback != null) {
            for (mq a : this.mDriveSwitchSceneCallback) {
                a.a(str, str2);
            }
        }
    }

    public String getRequestRouteParam(RouteType routeType) {
        int i;
        if (this.mTripPoiChangeListener == null) {
            return "";
        }
        ta taVar = new ta(this.mTripPoiChangeListener.getStartPOI(), this.mTripPoiChangeListener.getEndPOI(), this.mTripPoiChangeListener.getMidPOIs(), CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN);
        if (!TextUtils.isEmpty(this.mFromPage)) {
            taVar.C = this.mFromPage;
        }
        if (routeType == null) {
            throw new RuntimeException("规划类型为null");
        }
        if (routeType == RouteType.CAR || routeType == RouteType.MOTOR) {
            i = 0;
        } else if (routeType == RouteType.TRUCK) {
            i = 1;
        } else {
            throw new RuntimeException("不能识别的规划类型");
        }
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject(JSON.toJSONString(ou.a(i, taVar)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jSONObject == null) {
            return "";
        }
        return jSONObject.toString();
    }

    public void calcRouteStateChange(int i, String str) {
        Object obj;
        switch (i) {
            case 0:
                updateFromPage(null);
                try {
                    pi piVar = new pi();
                    if (this.mRouteStateChangeListener != null) {
                        piVar.a = RouteType.MOTOR.getValue();
                        this.mRouteStateChangeListener.onRouteStateChanged(0, piVar.a(str));
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case 1:
                if (this.mRouteStateChangeListener != null) {
                    try {
                        if (TextUtils.isEmpty(str)) {
                            obj = new JSONObject();
                        } else {
                            obj = new JSONObject(str);
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        obj = null;
                    }
                    this.mRouteStateChangeListener.onRouteStateChanged(1, obj);
                    return;
                }
                break;
            case 2:
                if (this.mRouteStateChangeListener != null) {
                    this.mRouteStateChangeListener.onRouteStateChanged(2, new Object());
                    return;
                }
                break;
            case 3:
                if (this.mRouteStateChangeListener != null) {
                    this.mRouteStateChangeListener.onRouteStateChanged(4, new Object());
                    break;
                }
                break;
        }
    }

    public void onReportErrorClick() {
        if (this.mJsCommandCallback != null) {
            this.mJsCommandCallback.callback(1004, new String[0]);
        }
    }

    public void updateRouteFocusIndex(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.focusIndex = Integer.parseInt(str);
            if (this.mRouteStateChangeListener != null) {
                this.mRouteStateChangeListener.onRouteFocusIndexChanged(this.focusIndex);
            }
        }
    }

    public void regeoPOI(String str) {
        if (this.mJsCommandCallback != null) {
            this.mJsCommandCallback.callback(26, str);
        }
    }

    public void setHeaderViewAlpha() {
        if (this.mJsCommandCallback != null) {
            this.mJsCommandCallback.callback(25, "");
        }
    }

    public void setAroundSearchResult(int i) {
        if (this.mSearchAlongCallback != null) {
            this.mSearchAlongCallback.callback(i > 0);
        }
    }

    public void onMapEventClick(String str) {
        for (RouteEventActionInterface next : this.mRouteEventActionInterfaceList) {
            if (next != null) {
                next.showIncidentDetail(str);
            }
        }
    }

    public void onObtainMapLayerClick(JsFunctionCallback jsFunctionCallback) {
        this.mOnObtainFocusCallBack.onObtainFocus();
    }

    public void onShareClick() {
        if (this.mJsCommandCallback != null) {
            this.mJsCommandCallback.callback(1003, new String[0]);
        }
    }

    public void addFavoriteRoute(int i, String str, JsFunctionCallback jsFunctionCallback) {
        String str2 = null;
        cos a = rv.a(str, this.mTripPoiChangeListener != null ? this.mTripPoiChangeListener.getRegoPOI() : null);
        if (a != null) {
            com com2 = (com) ank.a(com.class);
            if (com2 != null) {
                coq a2 = com2.a(com2.a());
                if (a2 != null) {
                    cor a3 = a2.a(a);
                    if (a3 != null) {
                        str2 = a3.f();
                    }
                }
            }
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("favId", str2);
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(jSONObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void isFavoriteRoute(int i, String str, JsFunctionCallback jsFunctionCallback) {
        String str2 = null;
        cos a = rv.a(str, this.mTripPoiChangeListener != null ? this.mTripPoiChangeListener.getRegoPOI() : null);
        if (a != null) {
            com com2 = (com) ank.a(com.class);
            if (com2 != null) {
                coq a2 = com2.a(com2.a());
                if (a2 != null) {
                    cor b = a2.b(a);
                    if (b != null) {
                        str2 = b.f();
                    }
                }
            }
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("favId", str2);
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(jSONObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setSearchRouteInNeMode(boolean z) {
        DriveSpUtil.setSearchRouteInNeMode(AMapAppGlobal.getApplication(), z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeFavoriteRoute(int r4, java.lang.String r5, com.autonavi.minimap.ajx3.core.JsFunctionCallback r6) {
        /*
            r3 = this;
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x000c }
            r4.<init>(r5)     // Catch:{ JSONException -> 0x000c }
            java.lang.String r5 = "favId"
            java.lang.String r4 = r4.getString(r5)     // Catch:{ JSONException -> 0x000c }
            goto L_0x0012
        L_0x000c:
            r4 = move-exception
            r5 = 0
            r4.printStackTrace()
            r4 = r5
        L_0x0012:
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            r0 = 1
            r1 = 0
            if (r5 != 0) goto L_0x0033
            java.lang.Class<com> r5 = defpackage.com.class
            java.lang.Object r5 = defpackage.ank.a(r5)
            com r5 = (defpackage.com) r5
            if (r5 == 0) goto L_0x0033
            java.lang.String r2 = r5.a()
            coq r5 = r5.a(r2)
            if (r5 == 0) goto L_0x0033
            r5.a(r4, r0)
            r4 = 1
            goto L_0x0034
        L_0x0033:
            r4 = 0
        L_0x0034:
            if (r6 == 0) goto L_0x0041
            java.lang.Object[] r5 = new java.lang.Object[r0]
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r5[r1] = r4
            r6.callback(r5)
        L_0x0041:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.ajx.module.ModuleRouteDriveResultImpl.removeFavoriteRoute(int, java.lang.String, com.autonavi.minimap.ajx3.core.JsFunctionCallback):void");
    }

    public void requestCarRoute(String str) {
        if (this.mJsCalcRouteCallBack != null) {
            this.mJsCalcRouteCallBack.callback(str);
        }
    }

    public void requestJsDriveRadarEvent(String str) {
        if (this.mJsDriveRadarPageEvent != null) {
            this.mJsDriveRadarPageEvent.callback(str);
        }
    }

    public void requestJsurfaceAreaSizeChange(int i, int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action_id", i);
            jSONObject.put("height_change", i2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.mJsSurfaceAreaSizeChangeCallback != null) {
            this.mJsSurfaceAreaSizeChangeCallback.callback(jSONObject.toString());
            return;
        }
        this.mSmartTipsHeightCache = jSONObject.toString();
    }

    public void requestJsWeatherAreaRequest() {
        if (this.mJsWeatherAreaRequestCallBack != null) {
            this.mJsWeatherAreaRequestCallBack.callback(new Object[0]);
        }
    }

    public void requestJsSuspendClickEvent(int i, boolean z) {
        String buildSuspendClickString = buildSuspendClickString(i, z);
        switch (i) {
            case 1002:
                if (this.mViaRoadOnClickCallback != null) {
                    this.mViaRoadOnClickCallback.callback(buildSuspendClickString);
                    return;
                }
                break;
            case 1003:
                if (this.mViaCityOnClickCallback != null) {
                    this.mViaCityOnClickCallback.callback(buildSuspendClickString);
                    return;
                }
                break;
            case 1005:
                if (this.mServiceAreaOnClickCallback != null) {
                    this.mServiceAreaOnClickCallback.callback(buildSuspendClickString);
                    return;
                }
                break;
            case 1006:
                if (this.mClearSearchResultOnClickCallback != null) {
                    this.mClearSearchResultOnClickCallback.callback(buildSuspendClickString);
                    return;
                }
                break;
            case 1008:
                if (this.mWeatherAreaOnClickCallback != null) {
                    this.mWeatherAreaOnClickCallback.callback(buildSuspendClickString);
                    return;
                }
                break;
            case 1010:
                if (this.mInspectionStationOnClickCallback != null) {
                    this.mInspectionStationOnClickCallback.callback(buildSuspendClickString);
                    break;
                }
                break;
        }
    }

    public void triggerAccessNaviCallback() {
        if (this.mJsAccessNaviCallback != null) {
            this.mJsAccessNaviCallback.callback(new Object[0]);
        }
    }

    public void setEventDetailDialogHeight(String str) {
        if (this.mIEventDetailDialog != null) {
            this.mIEventDetailDialog.setEventDetailDialogHeight(str);
        }
    }

    public void clearAllTips() {
        if (this.mTipsManager != null) {
            this.mTipsManager.m();
            return;
        }
        if (this.mMotorTipsManager != null) {
            this.mMotorTipsManager.j();
        }
    }

    public void bindTipsManager(AjxTipsManager ajxTipsManager) {
        this.mTipsManager = ajxTipsManager;
    }

    public void bindTipsManager(AjxMotorTipsManager ajxMotorTipsManager) {
        this.mMotorTipsManager = ajxMotorTipsManager;
    }

    public void addRouteEventActionInterface(RouteEventActionInterface routeEventActionInterface) {
        this.mRouteEventActionInterfaceList.add(routeEventActionInterface);
    }

    public void addSwitchSceneListener(mq mqVar) {
        this.mDriveSwitchSceneCallback.add(mqVar);
    }

    public void removeRouteEventInterface(RouteEventActionInterface routeEventActionInterface) {
        this.mRouteEventActionInterfaceList.remove(routeEventActionInterface);
    }

    public void removeSwitchSceneListener(mq mqVar) {
        if (mqVar != null && this.mDriveSwitchSceneCallback.contains(mqVar)) {
            this.mDriveSwitchSceneCallback.remove(mqVar);
        }
    }

    public void setJsCommandCallback(JsCommandCallback jsCommandCallback) {
        this.mJsCommandCallback = jsCommandCallback;
    }

    public void setOnObtainFocusCallBack(OnObtainFocusCallBack onObtainFocusCallBack) {
        this.mOnObtainFocusCallBack = onObtainFocusCallBack;
    }

    public void setOnRouteStateChangeListener(OnRouteStateChangeListener onRouteStateChangeListener) {
        this.mRouteStateChangeListener = onRouteStateChangeListener;
    }

    public void setOnTripPoiChangeListener(OnTripPoiChangeListener onTripPoiChangeListener) {
        this.mTripPoiChangeListener = onTripPoiChangeListener;
    }

    public void setSearchAlongCallback(SearchAlongCallback searchAlongCallback) {
        this.mSearchAlongCallback = searchAlongCallback;
    }

    public void setIEventDetailDialog(IEventDetailDialog iEventDetailDialog) {
        this.mIEventDetailDialog = iEventDetailDialog;
    }

    public void setWeatherSwitchState(boolean z) {
        this.weatherSwitchState = z ? 1 : 0;
    }

    public int getFocusIndex() {
        return this.focusIndex;
    }

    public String buildSuspendClickString(int i, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("event_id", i);
            jSONObject.put("is_click", z);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public void alongSearchActionCallback(int i) {
        if (this.mJsAlongSearchOnclickCallBack != null) {
            this.mJsAlongSearchOnclickCallBack.callback(Integer.valueOf(i));
        }
    }

    public void setOnCalRouteFromData(OnCalRouteFromData onCalRouteFromData) {
        this.mOnCalRouteFromData = onCalRouteFromData;
    }

    public void registerErrorReportOnClickCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsOnClickErrorReportClickListener = jsFunctionCallback;
    }

    public void callbackErrorReportClick(boolean z) {
        if (this.mJsOnClickErrorReportClickListener != null) {
            this.mJsOnClickErrorReportClickListener.callback(buildSuspendClickString(-1, z));
        }
    }

    public void registerResultPageMapStateback(JsFunctionCallback jsFunctionCallback) {
        this.mJsResultPageMapStateEventCallBack = jsFunctionCallback;
    }

    public void setCarResultMapState() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("state", 1);
            jSONObject.put("animation", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.mJsResultPageMapStateEventCallBack != null) {
            this.mJsResultPageMapStateEventCallBack.callback(jSONObject.toString());
        }
    }

    public void registerMitVuiRouteResultEventCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsMitVuiRouteResultEventCallback = jsFunctionCallback;
    }

    public void requestMitVuiRouteResult(String str) {
        if (this.mJsMitVuiRouteResultEventCallback != null) {
            this.mJsMitVuiRouteResultEventCallback.callback(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public void registerAccessNaviCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsAccessNaviCallback = jsFunctionCallback;
    }

    /* access modifiers changed from: 0000 */
    public void registerVUILayerListener(JsFunctionCallback jsFunctionCallback) {
        this.mVUILayerListner = jsFunctionCallback;
    }

    public void dispatchVUILayerEvent(boolean z) {
        if (this.mVUILayerListner != null) {
            this.mVUILayerListner.callback(Boolean.valueOf(z));
        }
    }

    public void updateFromPage(@Nullable String str) {
        this.mFromPage = str;
    }
}
