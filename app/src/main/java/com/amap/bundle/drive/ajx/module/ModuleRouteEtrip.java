package com.amap.bundle.drive.ajx.module;

import com.alipay.sdk.util.h;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.drive.etrip.model.EtripResultData;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.IPageHost;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.navigation.NavigationRequestHolder;
import com.autonavi.minimap.navigation.param.AutoErouteRequest;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@AjxModule("route_etrip")
@Keep
public final class ModuleRouteEtrip extends AbstractModule {
    private static final String END_POI = "end_poi";
    public static final String MODULE_NAME = "route_etrip";
    public static final String MY_LOCATION_DES = "我的位置";
    private static final String START_POI = "start_poi";
    private static final String TAG = "ModuleRouteEtrip";
    /* access modifiers changed from: private */
    public POI mEndPoi;
    /* access modifiers changed from: private */
    public ns mEtripResult;
    private int mGpsAngle;
    private JsFunctionCallback mJsExChangeStartEndPoiCallBack;
    /* access modifiers changed from: private */
    public IRouteEtripModuleListener mModuleListener;
    /* access modifiers changed from: private */
    public POI mStartPoi;

    public interface IRouteEtripModuleListener {
        boolean startEtripResultPage(String str);
    }

    @AjxMethod("clearRouteDataCache")
    public final void clearRouteDataCache() {
    }

    public final void release() {
    }

    public ModuleRouteEtrip(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("fetchRouteData")
    public final void fetchRouteData(String str, final JsFunctionCallback jsFunctionCallback) {
        AnonymousClass1 r0 = new AosResponseCallback<AosByteResponse>() {
            public void onSuccess(AosByteResponse aosByteResponse) {
                if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                    aho.a(new Runnable() {
                        public void run() {
                            jsFunctionCallback.callback(new JSONObject().toString());
                        }
                    });
                    return;
                }
                try {
                    nu nuVar = new nu();
                    nuVar.parser((byte[]) aosByteResponse.getResult());
                    ModuleRouteEtrip.this.mEtripResult = nuVar.a();
                    final String b = nuVar.b();
                    aho.a(new Runnable() {
                        public void run() {
                            if (ModuleRouteEtrip.this.mEtripResult != null && "1".equals(ModuleRouteEtrip.this.mEtripResult.b)) {
                                bax bax = (bax) a.a.a(bax.class);
                                if (!(bax == null || ModuleRouteEtrip.this.mStartPoi == null || ModuleRouteEtrip.this.mEndPoi == null)) {
                                    EtripResultData etripResultData = new EtripResultData();
                                    etripResultData.setFromPOI(ModuleRouteEtrip.this.mStartPoi);
                                    etripResultData.setToPOI(ModuleRouteEtrip.this.mEndPoi);
                                    bax.a(etripResultData, RouteType.ETRIP);
                                }
                            }
                            jsFunctionCallback.callback(b);
                        }
                    });
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                aho.a(new Runnable() {
                    public void run() {
                        jsFunctionCallback.callback(new JSONObject().toString());
                    }
                });
            }
        };
        String replaceAll = str.replaceAll("\\\\", "");
        if (!replaceAll.startsWith("{") && !replaceAll.endsWith(h.d)) {
            replaceAll = replaceAll.substring(1, replaceAll.length() - 1);
        }
        AutoErouteRequest autoErouteRequest = new AutoErouteRequest();
        autoErouteRequest.b = replaceAll;
        NavigationRequestHolder.getInstance().sendAutoEroute(autoErouteRequest, r0);
    }

    @AjxMethod("openRouteDetails")
    public final void openRouteDetails(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String e = agd.e(jSONObject, "dataKey");
            int parseInt = Integer.parseInt(agd.e(jSONObject, "type"));
            int routeType = getRouteType(parseInt);
            bax bax = (bax) a.a.a(bax.class);
            if (bax != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("etrip_result_type", parseInt);
                switch (routeType) {
                    case 0:
                        pageBundle.putString("original_route", this.mEtripResult.f.get(e).b);
                        pageBundle.putObject("bundle_key_poi_start", this.mStartPoi);
                        pageBundle.putObject("bundle_key_poi_end", this.mEndPoi);
                        pageBundle.putInt("key_type", routeType);
                        pageBundle.putObject("bundle_key_source", Boolean.TRUE);
                        bax.c(pageBundle);
                        return;
                    case 1:
                    case 2:
                    case 3:
                        pageBundle.putInt("key_type", routeType);
                        pageBundle.putString("original_route", this.mEtripResult.f.get(e).b);
                        pageBundle.putObject("bundle_key_poi_start", this.mStartPoi);
                        pageBundle.putObject("bundle_key_poi_end", this.mEndPoi);
                        bax.c(pageBundle);
                        return;
                    case 5:
                        return;
                    case 6:
                        pageBundle.putInt("key_type", routeType);
                        pageBundle.putObject("bundle_key_poi_start", this.mStartPoi);
                        pageBundle.putObject("bundle_key_poi_end", this.mEndPoi);
                        bax.c(pageBundle);
                        return;
                    default:
                        throw new RuntimeException("Unsupported Server Type!!");
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @AjxMethod("registerStartEndChangeCallback")
    public final void registerStartEndChangeCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsExChangeStartEndPoiCallBack = jsFunctionCallback;
    }

    @AjxMethod(invokeMode = "sync", value = "getStartEndPoint")
    public final String getStartEndPoint() {
        JSONObject jSONObject = new JSONObject();
        if (!(this.mStartPoi == null || this.mEndPoi == null)) {
            setStartEndPoiData(this.mStartPoi, this.mEndPoi);
            try {
                jSONObject.put(START_POI, bnx.b(this.mStartPoi));
                jSONObject.put(END_POI, bnx.b(this.mEndPoi));
                new StringBuilder("getStartEndPont: strparam").append(jSONObject.toString());
                return jSONObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    @AjxMethod(invokeMode = "sync", value = "getRequesJson")
    public final String getRequesJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.getJSONObject(START_POI);
            JSONObject jSONObject3 = jSONObject.getJSONObject(END_POI);
            JSONArray f = agd.f(jSONObject, "etripTypes");
            if (f == null || f.length() <= 0 || jSONObject2 == null || jSONObject3 == null) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < f.length(); i++) {
                stringBuffer.append(f.optString(i));
            }
            if (jSONObject2 == null || jSONObject3 == null) {
                return "";
            }
            return nv.a(this.mGpsAngle, stringBuffer.toString(), bnx.a(jSONObject2.toString()), bnx.a(jSONObject3.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    @AjxMethod("requestRoute")
    public final void requestRoute(final String str) {
        aho.a(new Runnable() {
            public void run() {
                bid pageContext = AMapPageUtil.getPageContext();
                IPageHost iPageHost = (pageContext == null || pageContext.getActivity() == null) ? null : (IPageHost) pageContext.getActivity();
                if ((iPageHost == null || !iPageHost.isHostPaused()) && ModuleRouteEtrip.this.mModuleListener != null) {
                    ModuleRouteEtrip.this.mModuleListener.startEtripResultPage(str);
                }
            }
        });
    }

    public final void exChangeStartEndPoi(POI poi, POI poi2) {
        updateMyLocation(poi, poi2);
        if (bnx.a(poi) && bnx.a(poi2)) {
            setStartEndPoiData(poi, poi2);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(START_POI, bnx.b(poi));
                jSONObject.put(END_POI, bnx.b(poi2));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.mJsExChangeStartEndPoiCallBack != null) {
                this.mJsExChangeStartEndPoiCallBack.callback(jSONObject.toString());
            }
        }
    }

    private void setStartEndPoiData(POI poi, POI poi2) {
        if (!(poi == null || poi.getName() == null || !poi.getName().equals("我的位置"))) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition != null) {
                poi.setPoint(latestPosition);
            } else {
                poi.setPoint(null);
            }
        }
        if (!(poi2 == null || poi2.getName() == null || !poi2.getName().equals("我的位置"))) {
            GeoPoint latestPosition2 = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition2 != null) {
                poi2.setPoint(latestPosition2);
            } else {
                poi.setPoint(null);
            }
        }
        if (poi != null && poi2 != null) {
            this.mStartPoi = poi;
            this.mEndPoi = poi2;
        }
    }

    public final void requestRouteResult(POI poi, POI poi2) {
        updateMyLocation(poi, poi2);
        if (bnx.a(poi) && bnx.a(poi2)) {
            if (this.mEndPoi == null || this.mStartPoi == null) {
                exChangeStartEndPoi(poi, poi2);
            } else if (!bnx.a(this.mEndPoi, poi2) || !bnx.a(this.mStartPoi, poi)) {
                exChangeStartEndPoi(poi, poi2);
            }
            setStartEndPoiData(poi, poi2);
        }
    }

    private int getRouteType(int i) {
        switch (i) {
            case 1:
                return RouteType.CAR.getValue();
            case 2:
                return RouteType.TAXI.getValue();
            case 3:
                return RouteType.BUS.getValue();
            case 4:
                return RouteType.BUS.getValue();
            case 5:
                return RouteType.ONFOOT.getValue();
            case 6:
                return RouteType.RIDE.getValue();
            default:
                throw new RuntimeException("Unsupported Server Type!!");
        }
    }

    public final void setManagerListener(IRouteEtripModuleListener iRouteEtripModuleListener) {
        this.mModuleListener = iRouteEtripModuleListener;
    }

    public final void setCompassAngle(int i) {
        this.mGpsAngle = i;
    }

    private void updateMyLocation(POI poi, POI poi2) {
        if (poi != null && "我的位置".equals(poi.getName())) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition != null) {
                poi.setPoint(latestPosition);
            }
        }
        if (poi2 != null && "我的位置".equals(poi2.getName())) {
            GeoPoint latestPosition2 = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition2 != null) {
                poi2.setPoint(latestPosition2);
            }
        }
    }

    public final void resetStartEndPoint() {
        this.mStartPoi = null;
        this.mEndPoi = null;
    }

    public final POI getStartPoi() {
        return this.mStartPoi;
    }

    public final POI getEndPoi() {
        return this.mEndPoi;
    }
}
