package com.autonavi.bundle.routecommon.ajx;

import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.bundle.sharebike.ajx.ModuleBicycle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.map.db.helper.RouteHistoryDBHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("route_common")
public class ModuleRoute extends AbstractModule {
    private static final String KEY_AIRTICKET = "airticket";
    private static final String KEY_BUS = "bus";
    private static final String KEY_CAR = "car";
    private static final String KEY_COACH = "coach";
    private static final String KEY_ETRIP = "etrip";
    private static final String KEY_FOOT = "foot";
    private static final String KEY_MOTOR = "motor";
    private static final String KEY_RIDE = "ride";
    private static final String KEY_TRAIN = "train";
    private static final String KEY_TRUCK = "truck";
    private static final String RUN_RECOMMEND_NEW = "runrecommendnew";
    private static final String SHARE_BIKE_NEW = "sharebikenew";

    @AjxMethod("showAuthorizationStatusDeniedAlert")
    public void showAuthorizationStatusDeniedAlert(String str) {
    }

    public ModuleRoute(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("getRouteHistory")
    public String getRouteHistory(String str, JsFunctionCallback jsFunctionCallback) {
        return routeHistory(getRouteTypeFromKey(str), jsFunctionCallback);
    }

    @AjxMethod("clearRouteHistory")
    public void clearRouteHistory(final String str, final JsFunctionCallback jsFunctionCallback) {
        ahl.a(new a<String>() {
            public final void onError(Throwable th) {
            }

            public final /* synthetic */ void onFinished(Object obj) {
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(new Object[0]);
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                RouteType access$000 = ModuleRoute.this.getRouteTypeFromKey(str);
                if (access$000 == RouteType.COACH || access$000 == RouteType.AIRTICKET) {
                    RouteHistoryDBHelper.getInstance(AMapPageUtil.getAppContext()).clearRouteHistory(access$000.getValue());
                } else {
                    bin.a.c(chk.b(access$000), "", 1);
                }
                return "";
            }
        }, ahn.b());
    }

    @AjxMethod("getDistance")
    public void getDistance(String str, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.isEmpty(str)) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(Integer.valueOf(-1));
            }
            return;
        }
        float f = -1.0f;
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (!(jSONArray.length() < 2 || jSONArray.getJSONObject(0) == null || jSONArray.getJSONObject(1) == null)) {
                f = cfe.a(new GeoPoint(jSONArray.getJSONObject(0).optInt(DictionaryKeys.CTRLXY_X), jSONArray.getJSONObject(0).optInt(DictionaryKeys.CTRLXY_Y)), new GeoPoint(jSONArray.getJSONObject(1).optInt(DictionaryKeys.CTRLXY_X), jSONArray.getJSONObject(1).optInt(DictionaryKeys.CTRLXY_Y)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
        String sb2 = sb.toString();
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Float.valueOf(f), sb2);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getSyncDistance")
    public float getSyncDistance(String str) {
        float f = -1.0f;
        if (TextUtils.isEmpty(str)) {
            return -1.0f;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (!(jSONArray.length() < 2 || jSONArray.getJSONObject(0) == null || jSONArray.getJSONObject(1) == null)) {
                f = cfe.a(new GeoPoint(jSONArray.getJSONObject(0).optInt(DictionaryKeys.CTRLXY_X), jSONArray.getJSONObject(0).optInt(DictionaryKeys.CTRLXY_Y)), new GeoPoint(jSONArray.getJSONObject(1).optInt(DictionaryKeys.CTRLXY_X), jSONArray.getJSONObject(1).optInt(DictionaryKeys.CTRLXY_Y)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return f;
    }

    @AjxMethod("hasShareBike")
    public void hasShareBike(final JsFunctionCallback jsFunctionCallback) {
        ahl.b(new a<String>() {
            public final /* synthetic */ void onFinished(Object obj) {
                String str = (String) obj;
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                lx lxVar = lt.a().c.w;
                boolean booleanValue = (lxVar == null || lxVar.b == null) ? false : lxVar.b.booleanValue();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW, booleanValue);
                return jSONObject.toString();
            }
        });
    }

    @AjxMethod("hasAgroup")
    public void hasAgroup(final JsFunctionCallback jsFunctionCallback) {
        ahl.a(new a<String>() {
            public final /* synthetic */ void onFinished(Object obj) {
                String str = (String) obj;
                eao.a("----- ModuleRoute ----- hasAgroup json ----- ".concat(String.valueOf(str)));
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                cuh cuh = (cuh) a.a.a(cuh.class);
                boolean c = cuh != null ? cuh.c().c() : false;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW, c);
                return jSONObject.toString();
            }
        }, ahn.b());
    }

    @AjxMethod("showRedPoint")
    public void showRedPoint(final JsFunctionCallback jsFunctionCallback) {
        ahl.a(new a<String>() {
            public final void onError(Throwable th) {
            }

            public final /* synthetic */ void onFinished(Object obj) {
                String str = (String) obj;
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("recommendRoute", mapSharePreference.getBooleanValue(ModuleRoute.RUN_RECOMMEND_NEW, true));
                jSONObject.put(ModuleBicycle.MODULE_NAME, mapSharePreference.getBooleanValue(ModuleRoute.SHARE_BIKE_NEW, true));
                return jSONObject.toString();
            }
        }, ahn.b());
    }

    @AjxMethod("notifyRedPointStatus")
    public void notifyRedPointStatus(final String str) {
        ahl.a(new a<String>() {
            public final void onError(Throwable th) {
            }

            public final /* bridge */ /* synthetic */ void onFinished(Object obj) {
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                if (ModuleBicycle.MODULE_NAME.equals(str)) {
                    mapSharePreference.putBooleanValue(ModuleRoute.SHARE_BIKE_NEW, false);
                } else if ("recommendRoute".equals(str)) {
                    mapSharePreference.putBooleanValue(ModuleRoute.RUN_RECOMMEND_NEW, false);
                }
                return "";
            }
        }, ahn.b());
    }

    @AjxMethod("cancelVibrate")
    public void cancelVibrate() {
        ebs a = ebs.a(getNativeContext());
        if (a.a != null) {
            a.a.cancel();
        }
    }

    /* access modifiers changed from: private */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.autonavi.bundle.routecommon.model.RouteType getRouteTypeFromKey(java.lang.String r3) {
        /*
            r2 = this;
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            int r1 = r3.hashCode()
            switch(r1) {
                case 97920: goto L_0x006a;
                case 98260: goto L_0x0060;
                case 3148910: goto L_0x0056;
                case 3500280: goto L_0x004b;
                case 94831770: goto L_0x0041;
                case 96844298: goto L_0x0037;
                case 104085621: goto L_0x002c;
                case 110621192: goto L_0x0021;
                case 110640223: goto L_0x0016;
                case 599278806: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x0074
        L_0x000b:
            java.lang.String r1 = "airticket"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 8
            goto L_0x0075
        L_0x0016:
            java.lang.String r1 = "truck"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 6
            goto L_0x0075
        L_0x0021:
            java.lang.String r1 = "train"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 4
            goto L_0x0075
        L_0x002c:
            java.lang.String r1 = "motor"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 9
            goto L_0x0075
        L_0x0037:
            java.lang.String r1 = "etrip"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 7
            goto L_0x0075
        L_0x0041:
            java.lang.String r1 = "coach"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 5
            goto L_0x0075
        L_0x004b:
            java.lang.String r1 = "ride"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 3
            goto L_0x0075
        L_0x0056:
            java.lang.String r1 = "foot"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 2
            goto L_0x0075
        L_0x0060:
            java.lang.String r1 = "car"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 0
            goto L_0x0075
        L_0x006a:
            java.lang.String r1 = "bus"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0074
            r3 = 1
            goto L_0x0075
        L_0x0074:
            r3 = -1
        L_0x0075:
            switch(r3) {
                case 0: goto L_0x0094;
                case 1: goto L_0x0091;
                case 2: goto L_0x008e;
                case 3: goto L_0x008b;
                case 4: goto L_0x0088;
                case 5: goto L_0x0085;
                case 6: goto L_0x0082;
                case 7: goto L_0x007f;
                case 8: goto L_0x007c;
                case 9: goto L_0x0079;
                default: goto L_0x0078;
            }
        L_0x0078:
            goto L_0x0096
        L_0x0079:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.MOTOR
            goto L_0x0096
        L_0x007c:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.AIRTICKET
            goto L_0x0096
        L_0x007f:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            goto L_0x0096
        L_0x0082:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK
            goto L_0x0096
        L_0x0085:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.COACH
            goto L_0x0096
        L_0x0088:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.TRAIN
            goto L_0x0096
        L_0x008b:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.RIDE
            goto L_0x0096
        L_0x008e:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT
            goto L_0x0096
        L_0x0091:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.BUS
            goto L_0x0096
        L_0x0094:
            com.autonavi.bundle.routecommon.model.RouteType r0 = com.autonavi.bundle.routecommon.model.RouteType.CAR
        L_0x0096:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommon.ajx.ModuleRoute.getRouteTypeFromKey(java.lang.String):com.autonavi.bundle.routecommon.model.RouteType");
    }

    private String routeHistory(final RouteType routeType, final JsFunctionCallback jsFunctionCallback) {
        ahl.a(new a<String>() {
            public final void onError(Throwable th) {
            }

            public final /* synthetic */ void onFinished(Object obj) {
                String str = (String) obj;
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                String str;
                int i = 0;
                if (routeType == RouteType.COACH || routeType == RouteType.AIRTICKET) {
                    List<btf> historyList = RouteHistoryDBHelper.getInstance(AMapPageUtil.getAppContext()).getHistoryList(routeType.getValue());
                    if (historyList == null || historyList.size() <= 0) {
                        return "";
                    }
                    JSONArray jSONArray = new JSONArray();
                    while (i < historyList.size()) {
                        jSONArray.put(ModuleRoute.this.parseRouteHistoryToJson(historyList.get(i)));
                        i++;
                    }
                    str = jSONArray.toString();
                } else {
                    List<chk> a2 = chk.a(routeType);
                    if (a2.size() <= 0) {
                        return "";
                    }
                    ModuleRoute.this.removeDuplicate(a2);
                    JSONArray jSONArray2 = new JSONArray();
                    while (i < a2.size()) {
                        jSONArray2.put(ModuleRoute.this.parseSyncableRouteHistoryToJson(a2.get(i)));
                        i++;
                    }
                    str = jSONArray2.toString();
                }
                return str;
            }
        }, ahn.b());
        return "";
    }

    /* access modifiers changed from: private */
    public void removeDuplicate(List<chk> list) {
        int size = list.size() - 1;
        while (size >= 0) {
            chk chk = list.get(size);
            if (chk != null) {
                int i = size - 1;
                while (i >= 0) {
                    if (i != size) {
                        chk chk2 = list.get(i);
                        if (isSamePOI(chk.d(), chk2.d())) {
                            if (chk.f() == null || chk.f().size() <= 0) {
                                boolean isSamePOI = isSamePOI(chk.e(), chk2.e());
                                if ((chk2.f() == null || chk2.f().size() == 0) && isSamePOI) {
                                    list.remove(i);
                                    i++;
                                    size--;
                                }
                            } else if (chk2.f() != null && chk2.f().size() > 0 && chk2.f().size() == chk.f().size()) {
                                ArrayList<POI> f = chk.f();
                                ArrayList<POI> f2 = chk2.f();
                                int i2 = 0;
                                int i3 = 0;
                                while (i2 < f.size() && isSamePOI(f.get(i2), f2.get(i2))) {
                                    i3++;
                                    i2++;
                                }
                                boolean isSamePOI2 = isSamePOI(chk.e(), chk2.e());
                                if (i3 == f.size() && isSamePOI2) {
                                    list.remove(i);
                                    i++;
                                    size--;
                                }
                            }
                        }
                    }
                    i--;
                }
            }
            size--;
        }
    }

    private boolean isMyLocation(String str) {
        return getNativeContext().getString(R.string.my_location).equals(str) || getNativeContext().getString(R.string.map_selected_location).equals(str) || "已选择的位置".equals(str);
    }

    private boolean isSamePOI(POI poi, POI poi2) {
        return poi.getName().equals(poi2.getName());
    }

    /* access modifiers changed from: private */
    public JSONObject parseSyncableRouteHistoryToJson(chk chk) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("start_poi", bnx.b(chk.d()));
            jSONObject.put("end_poi", bnx.b(chk.e()));
            ArrayList<POI> f = chk.f();
            if (f != null && f.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < f.size(); i++) {
                    jSONArray.put(bnx.b(f.get(i)));
                }
                jSONObject.put("middle_pois", jSONArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    /* access modifiers changed from: private */
    public JSONObject parseRouteHistoryToJson(btf btf) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (btf.i != null && btf.m == null) {
                btf.m = btf.a(btf.i);
            }
            jSONObject.put("start_poi", bnx.b(btf.m));
            if (btf.k != null && btf.n == null) {
                btf.n = btf.a(btf.k);
            }
            jSONObject.put("end_poi", bnx.b(btf.n));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    @AjxMethod("startEmulation")
    public void startEmulation(String str) {
        boolean c = a.c();
        StringBuilder sb = new StringBuilder("startEmulation param:");
        sb.append(str);
        sb.append(", isDebug:");
        sb.append(c);
        eao.a((String) "emulation", sb.toString());
        if (c) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    String optString = new JSONObject(str).optString("type");
                    if ("ride".equals(optString)) {
                        a.a(TrackType.RIDE);
                    } else if (KEY_FOOT.equals(optString)) {
                        a.a(TrackType.WALK);
                    } else if ("bus".equals(optString)) {
                        a.a(TrackType.BUS);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @AjxMethod("stopEmulation")
    public void stopEmulation(String str) {
        eao.a((String) "emulation", "stopEmulation param:".concat(String.valueOf(str)));
        a.b();
    }

    @AjxMethod("getRouteServiceCloudStatus")
    public void getRouteServiceCloudStatus(final String str, final JsFunctionCallback jsFunctionCallback) {
        awz.a();
        AnonymousClass7 r0 = new lp() {
            public final void onConfigCallBack(int i) {
            }

            public final void onConfigResultCallBack(int i, String str) {
                jsFunctionCallback.callback(str);
                awz.a();
                String str2 = str;
                if (!TextUtils.isEmpty(str2)) {
                    String a2 = awz.a(str2);
                    if (TextUtils.isEmpty(a2)) {
                        lo.a().b(a2, this);
                    }
                }
            }
        };
        if (!TextUtils.isEmpty(str)) {
            String a = awz.a(str);
            if (!TextUtils.isEmpty(a)) {
                lo.a().a(a, (lp) r0);
            }
        }
    }
}
