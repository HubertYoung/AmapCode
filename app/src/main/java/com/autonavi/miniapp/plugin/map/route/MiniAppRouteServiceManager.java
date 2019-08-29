package com.autonavi.miniapp.plugin.map.route;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.jni.ae.model.Coord2D;
import com.autonavi.jni.ae.routeplan.MiniAppRouteService;
import com.autonavi.jni.ae.routeplan.interfaces.ICalcRouteListener;
import com.autonavi.jni.ae.routeplan.model.RoutePlanPOIInfo;
import com.autonavi.jni.ae.routeplan.model.RoutePlanWayPoint;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper.AmapEngineErrors;
import java.util.ArrayList;

public class MiniAppRouteServiceManager {
    /* access modifiers changed from: private */
    public static String TAG = "MiniAppRouteServiceManager";
    private static int id;
    /* access modifiers changed from: private */
    public SparseArray<H5BridgeContext> idCallbackMap = new SparseArray<>();
    private MiniAppRouteService mMiniAppRouteService;
    /* access modifiers changed from: private */
    public Handler mainHandler = new Handler(Looper.getMainLooper());

    public MiniAppRouteServiceManager() {
        init();
    }

    private void init() {
        this.mMiniAppRouteService = new MiniAppRouteService();
        this.mMiniAppRouteService.init(0, aaf.b(ConfigerHelper.AOS_URL_KEY));
        this.mMiniAppRouteService.setCalcRouteListener(new ICalcRouteListener() {
            public void onRouteSucceed(final int i, final String str) {
                AnonymousClass1 r0 = new Runnable() {
                    public void run() {
                        String access$000 = MiniAppRouteServiceManager.TAG;
                        StringBuilder sb = new StringBuilder("onRouteSucceed, requestId: ");
                        sb.append(i);
                        sb.append(", pathResult: ");
                        sb.append(str);
                        AMapLog.debug("infoservice.miniapp", access$000, sb.toString());
                        H5BridgeContext h5BridgeContext = (H5BridgeContext) MiniAppRouteServiceManager.this.idCallbackMap.get(i);
                        if (h5BridgeContext != null) {
                            JSONObject parseObject = JSON.parseObject(str);
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put((String) "success", (Object) Boolean.TRUE);
                            jSONObject.put((String) "data", (Object) parseObject);
                            h5BridgeContext.sendBridgeResult(jSONObject);
                            MiniAppRouteServiceManager.this.idCallbackMap.remove(i);
                        }
                    }
                };
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    MiniAppRouteServiceManager.this.mainHandler.post(r0);
                } else {
                    r0.run();
                }
            }

            public void onRouteError(final int i, final int i2) {
                AnonymousClass2 r0 = new Runnable() {
                    public void run() {
                        String access$000 = MiniAppRouteServiceManager.TAG;
                        StringBuilder sb = new StringBuilder("onRouteError, requestId: ");
                        sb.append(i);
                        sb.append(", error: ");
                        sb.append(i2);
                        AMapLog.debug("infoservice.miniapp", access$000, sb.toString());
                        if (MiniAppRouteServiceManager.this.idCallbackMap != null && MiniAppRouteServiceManager.this.idCallbackMap.size() != 0) {
                            AmapEngineErrors valueOf = AmapEngineErrors.valueOf(i2);
                            H5BridgeContext h5BridgeContext = (H5BridgeContext) MiniAppRouteServiceManager.this.idCallbackMap.get(i);
                            if (h5BridgeContext != null) {
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put((String) "success", (Object) Boolean.FALSE);
                                jSONObject.put((String) "error", (Object) Integer.valueOf(valueOf.value()));
                                h5BridgeContext.sendBridgeResult(jSONObject);
                                MiniAppRouteServiceManager.this.idCallbackMap.remove(i);
                            }
                        }
                    }
                };
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    MiniAppRouteServiceManager.this.mainHandler.post(r0);
                } else {
                    r0.run();
                }
            }
        });
    }

    public void calcRoute(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        if (jSONObject != null) {
            int i = id + 1;
            id = i;
            this.idCallbackMap.append(i, h5BridgeContext);
            this.mMiniAppRouteService.calcRoute(i, parseMiniAppData(jSONObject));
        }
    }

    public void abortCalcRoute() {
        for (int i = 0; i < this.idCallbackMap.size(); i++) {
            this.mMiniAppRouteService.abortCalcRoute(this.idCallbackMap.keyAt(i));
        }
        this.idCallbackMap.clear();
    }

    private RoutePlanWayPoint parseMiniAppData(JSONObject jSONObject) {
        RoutePlanWayPoint routePlanWayPoint = new RoutePlanWayPoint();
        ArrayList<RoutePlanPOIInfo> arrayList = new ArrayList<>();
        ArrayList<RoutePlanPOIInfo> arrayList2 = new ArrayList<>();
        RoutePlanPOIInfo routePlanPOIInfo = new RoutePlanPOIInfo();
        routePlanPOIInfo.realPos = new Coord2D(jSONObject.getDouble("startLng").doubleValue(), jSONObject.getDouble("startLat").doubleValue());
        routePlanPOIInfo.floor = jSONObject.getIntValue("fromFloor");
        routePlanPOIInfo.poiID = jSONObject.getString("fromPOIID");
        routePlanPOIInfo.parentID = jSONObject.getString("fromParentID");
        if (jSONObject.getIntValue("fromIndoor") == 1) {
            routePlanPOIInfo.parentRel = "201";
        }
        if (!TextUtils.isEmpty(routePlanPOIInfo.poiID)) {
            routePlanPOIInfo.type = 2;
        } else {
            routePlanPOIInfo.type = 1;
        }
        arrayList.add(routePlanPOIInfo);
        routePlanWayPoint.start = arrayList;
        RoutePlanPOIInfo routePlanPOIInfo2 = new RoutePlanPOIInfo();
        routePlanPOIInfo2.type = 1;
        routePlanPOIInfo2.realPos = new Coord2D(jSONObject.getDouble("endLng").doubleValue(), jSONObject.getDouble("endLat").doubleValue());
        routePlanPOIInfo2.floor = jSONObject.getIntValue("toFloor");
        routePlanPOIInfo2.poiID = jSONObject.getString("toPOIID");
        routePlanPOIInfo2.parentID = jSONObject.getString("toParentID");
        if (jSONObject.getIntValue("toIndoor") == 1) {
            routePlanPOIInfo2.parentRel = "201";
        }
        if (!TextUtils.isEmpty(routePlanPOIInfo2.poiID)) {
            routePlanPOIInfo2.type = 2;
        } else {
            routePlanPOIInfo2.type = 1;
        }
        arrayList2.add(routePlanPOIInfo2);
        routePlanWayPoint.end = arrayList2;
        return routePlanWayPoint;
    }
}
