package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper.AmapEngineErrors;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteManager;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteManager.CalcRouteParam;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteManager.CalcRouteParam.POI;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteManager.OnShowRouteCallback;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ShowRouteActionProcessor extends BaseActionProcessor {
    public static final int BUS_COMFORTABLE = 4;
    public static final int BUS_DEFAULT = 0;
    public static final int BUS_LEASECHANGE = 2;
    public static final int BUS_LEASEWALK = 3;
    public static final int BUS_NO_SUBWAY = 5;
    public static final int BUS_SAVEMONEY = 1;
    private static final int DEFAULT_POI_TYPE = 1;
    private static final int DRIVING_AVOID_CONGESTION = 4;
    private static final int DRIVING_DEFAULT = 0;
    private static final int DRIVING_MULTI_STRATEGY = 5;
    private static final int DRIVING_NO_EXPRESSWAYS = 3;
    private static final int DRIVING_NO_HIGH_AVOID_CONGESTION_SAVE_MONEY = 9;
    private static final int DRIVING_NO_HIGH_WAY = 6;
    private static final int DRIVING_NO_HIGH_WAY_SAVE_MONEY = 7;
    private static final int DRIVING_SAVE_MONEY = 1;
    private static final int DRIVING_SAVE_MONEY_AVOID_CONGESTION = 8;
    private static final int DRIVING_SHORT_DISTANCE = 2;
    private static List<String> SEARCH_TYPES = null;
    public static final String SEARCH_TYPE_BUS = "bus";
    public static final String SEARCH_TYPE_DRIVE = "drive";
    public static final String SEARCH_TYPE_RIDE = "ride";
    public static final String SEARCH_TYPE_WALK = "walk";
    private MiniAppShowRouteManager mMiniAppShowRouteManager;

    public static class ShowRouteParam implements Serializable {
        public String city;
        public String destinationCity;
        public double endLat;
        public double endLng;
        public int mode;
        public String searchType;
        public double startLat;
        public double startLng;
        public List<LatLng> throughPoints;

        public static class LatLng {
            public double lat;
            public double lng;

            public String toString() {
                StringBuilder sb = new StringBuilder("LatLng{lat=");
                sb.append(this.lat);
                sb.append(", lng=");
                sb.append(this.lng);
                sb.append('}');
                return sb.toString();
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ShowRouteParam{searchType='");
            sb.append(this.searchType);
            sb.append('\'');
            sb.append(", startLat=");
            sb.append(this.startLat);
            sb.append(", startLng=");
            sb.append(this.startLng);
            sb.append(", endLat=");
            sb.append(this.endLat);
            sb.append(", endLng=");
            sb.append(this.endLng);
            sb.append(", throughPoints=");
            sb.append(this.throughPoints);
            sb.append(", mode=");
            sb.append(this.mode);
            sb.append(", city='");
            sb.append(this.city);
            sb.append('\'');
            sb.append(", destinationCity='");
            sb.append(this.destinationCity);
            sb.append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        SEARCH_TYPES = arrayList;
        arrayList.add(SEARCH_TYPE_WALK);
        SEARCH_TYPES.add(SEARCH_TYPE_BUS);
        SEARCH_TYPES.add("drive");
        SEARCH_TYPES.add(SEARCH_TYPE_RIDE);
    }

    public ShowRouteActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView, MiniAppShowRouteManager miniAppShowRouteManager) {
        super("showRoute", weakReference, weakReference2, adapterTextureMapView);
        this.mMiniAppShowRouteManager = miniAppShowRouteManager;
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        ShowRouteParam showRouteParam = (ShowRouteParam) jSONObject.toJavaObject(ShowRouteParam.class);
        if (!checkShowRouteParam(showRouteParam)) {
            if (showRouteParam != null) {
                showRouteParam.searchType = SEARCH_TYPES.get(0);
            }
            AMapLog.error("infoservice.miniapp", AMapH5EmbedMapView.TAG, "showRouteParam ".concat(String.valueOf(showRouteParam)));
        }
        CalcRouteParam calcRouteParam = new CalcRouteParam();
        calcRouteParam.calcType = 0;
        calcRouteParam.startPOI = new POI();
        calcRouteParam.startPOI.lat = showRouteParam.startLat;
        calcRouteParam.startPOI.lon = showRouteParam.startLng;
        calcRouteParam.startPOI.type = 1;
        calcRouteParam.endPOI = new POI();
        calcRouteParam.endPOI.lat = showRouteParam.endLat;
        calcRouteParam.endPOI.lon = showRouteParam.endLng;
        calcRouteParam.endPOI.type = 1;
        if (showRouteParam.throughPoints != null && showRouteParam.throughPoints.size() > 0) {
            calcRouteParam.midPOI = new ArrayList(showRouteParam.throughPoints.size());
            for (LatLng next : showRouteParam.throughPoints) {
                POI poi = new POI();
                poi.lat = next.lat;
                poi.lon = next.lng;
                poi.type = 1;
                calcRouteParam.midPOI.add(poi);
                if (calcRouteParam.midPOI.size() >= 3) {
                    break;
                }
            }
        }
        calcRouteParam.routeMode = transformRouteMode(showRouteParam.searchType);
        calcRouteParam.strategy = transformStrategy(showRouteParam);
        calcRouteParam.type = transformType(showRouteParam);
        calcRouteParam.constrainCode = transformConstrainCode(showRouteParam);
        if (showRouteParam == null || showRouteParam.searchType == null || !showRouteParam.searchType.equals(SEARCH_TYPE_BUS)) {
            commonCalcRoute(h5BridgeContext, calcRouteParam);
        } else {
            this.mMiniAppShowRouteManager.calcBusRoute(h5BridgeContext, calcRouteParam);
        }
    }

    private void commonCalcRoute(final H5BridgeContext h5BridgeContext, CalcRouteParam calcRouteParam) {
        this.mMiniAppShowRouteManager.calcRoute(calcRouteParam, new OnShowRouteCallback() {
            public void onSucceed(int i, int i2) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put((String) "success", (Object) Boolean.TRUE);
                jSONObject.put((String) "distance", (Object) Integer.valueOf(i));
                jSONObject.put((String) "duration", (Object) Integer.valueOf(i2));
                h5BridgeContext.sendBridgeResult(jSONObject);
            }

            public void onFail(int i) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put((String) "success", (Object) Boolean.FALSE);
                jSONObject.put((String) "error", (Object) Integer.valueOf(AmapEngineErrors.valueOf(i).value()));
                h5BridgeContext.sendBridgeResult(jSONObject);
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int transformRouteMode(java.lang.String r7) {
        /*
            r6 = this;
            int r0 = r7.hashCode()
            r1 = 97920(0x17e80, float:1.37215E-40)
            r2 = 3
            r3 = 1
            r4 = 2
            r5 = 0
            if (r0 == r1) goto L_0x003c
            r1 = 3500280(0x3568f8, float:4.904937E-39)
            if (r0 == r1) goto L_0x0032
            r1 = 3641801(0x3791c9, float:5.10325E-39)
            if (r0 == r1) goto L_0x0027
            r1 = 95852938(0x5b6998a, float:1.7171599E-35)
            if (r0 == r1) goto L_0x001d
            goto L_0x0046
        L_0x001d:
            java.lang.String r0 = "drive"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0046
            r7 = 0
            goto L_0x0047
        L_0x0027:
            java.lang.String r0 = "walk"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0046
            r7 = 2
            goto L_0x0047
        L_0x0032:
            java.lang.String r0 = "ride"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0046
            r7 = 1
            goto L_0x0047
        L_0x003c:
            java.lang.String r0 = "bus"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0046
            r7 = 3
            goto L_0x0047
        L_0x0046:
            r7 = -1
        L_0x0047:
            switch(r7) {
                case 0: goto L_0x0050;
                case 1: goto L_0x004f;
                case 2: goto L_0x004d;
                case 3: goto L_0x004b;
                default: goto L_0x004a;
            }
        L_0x004a:
            goto L_0x0050
        L_0x004b:
            r5 = 3
            goto L_0x0050
        L_0x004d:
            r5 = 2
            goto L_0x0050
        L_0x004f:
            r5 = 1
        L_0x0050:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor.transformRouteMode(java.lang.String):int");
    }

    private int transformConstrainCode(ShowRouteParam showRouteParam) {
        int i = showRouteParam.mode;
        if (!(i == 3 || i == 9)) {
            switch (i) {
                case 6:
                case 7:
                    break;
                default:
                    return 0;
            }
        }
        return 1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0034  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int transformStrategy(com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor.ShowRouteParam r7) {
        /*
            r6 = this;
            java.lang.String r0 = r7.searchType
            int r7 = r7.mode
            int r1 = r0.hashCode()
            r2 = 97920(0x17e80, float:1.37215E-40)
            r3 = 1
            r4 = 0
            r5 = -1
            if (r1 == r2) goto L_0x0020
            r2 = 95852938(0x5b6998a, float:1.7171599E-35)
            if (r1 == r2) goto L_0x0016
            goto L_0x002a
        L_0x0016:
            java.lang.String r1 = "drive"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002a
            r0 = 0
            goto L_0x002b
        L_0x0020:
            java.lang.String r1 = "bus"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002a
            r0 = 1
            goto L_0x002b
        L_0x002a:
            r0 = -1
        L_0x002b:
            switch(r0) {
                case 0: goto L_0x0034;
                case 1: goto L_0x002f;
                default: goto L_0x002e;
            }
        L_0x002e:
            return r4
        L_0x002f:
            switch(r7) {
                case 0: goto L_0x0033;
                case 1: goto L_0x0033;
                case 2: goto L_0x0033;
                case 3: goto L_0x0033;
                case 4: goto L_0x0033;
                case 5: goto L_0x0033;
                default: goto L_0x0032;
            }
        L_0x0032:
            return r5
        L_0x0033:
            return r7
        L_0x0034:
            switch(r7) {
                case 0: goto L_0x003e;
                case 1: goto L_0x003e;
                case 2: goto L_0x003e;
                case 3: goto L_0x003e;
                case 4: goto L_0x003e;
                case 5: goto L_0x003e;
                case 6: goto L_0x003c;
                case 7: goto L_0x003b;
                case 8: goto L_0x0038;
                case 9: goto L_0x0038;
                default: goto L_0x0037;
            }
        L_0x0037:
            return r5
        L_0x0038:
            r7 = 12
            return r7
        L_0x003b:
            return r3
        L_0x003c:
            r7 = 2
            return r7
        L_0x003e:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor.transformStrategy(com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor$ShowRouteParam):int");
    }

    private int transformType(ShowRouteParam showRouteParam) {
        int i = showRouteParam.mode;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return i;
            default:
                return 0;
        }
    }

    private boolean checkShowRouteParam(ShowRouteParam showRouteParam) {
        if (SEARCH_TYPES.contains(showRouteParam.searchType)) {
            return true;
        }
        AMapLog.error("infoservice.miniapp", AMapH5EmbedMapView.TAG, "showRouteParam illegal searchType,  ".concat(String.valueOf(showRouteParam)));
        return false;
    }
}
