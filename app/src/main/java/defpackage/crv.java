package defpackage;

import android.content.Context;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.db.model.Vehicles;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@MultipleImpl(awa.class)
/* renamed from: crv reason: default package */
/* compiled from: OperationInit */
public class crv implements awa {
    Context a = null;

    public final void a() {
        this.a = AMapAppGlobal.getApplication();
        ahm.a(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:60:0x01ad, code lost:
                if (r3 != false) goto L_0x01af;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void run() {
                /*
                    r12 = this;
                    crv r0 = defpackage.crv.this     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r1 = ""
                    java.lang.Class<coy> r2 = defpackage.coy.class
                    java.lang.Object r2 = defpackage.ank.a(r2)     // Catch:{ Exception -> 0x0229 }
                    coy r2 = (defpackage.coy) r2     // Catch:{ Exception -> 0x0229 }
                    if (r2 == 0) goto L_0x0012
                    java.lang.String r1 = r2.a()     // Catch:{ Exception -> 0x0229 }
                L_0x0012:
                    java.lang.Class<com> r2 = defpackage.com.class
                    java.lang.Object r2 = defpackage.ank.a(r2)     // Catch:{ Exception -> 0x0229 }
                    com r2 = (defpackage.com) r2     // Catch:{ Exception -> 0x0229 }
                    r3 = 0
                    if (r2 == 0) goto L_0x002e
                    cop r4 = r2.b(r1)     // Catch:{ Exception -> 0x0229 }
                    coq r2 = r2.a(r1)     // Catch:{ Exception -> 0x0229 }
                    java.util.List r5 = r4.a(r1)     // Catch:{ Exception -> 0x0229 }
                    java.util.List r6 = r2.b(r1)     // Catch:{ Exception -> 0x0229 }
                    goto L_0x003a
                L_0x002e:
                    java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x0229 }
                    r5.<init>()     // Catch:{ Exception -> 0x0229 }
                    java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Exception -> 0x0229 }
                    r6.<init>()     // Catch:{ Exception -> 0x0229 }
                    r2 = r3
                    r4 = r2
                L_0x003a:
                    if (r1 != 0) goto L_0x003f
                    java.lang.String r1 = ""
                    goto L_0x0049
                L_0x003f:
                    java.lang.String r7 = "public"
                    boolean r7 = r1.equals(r7)     // Catch:{ Exception -> 0x0229 }
                    if (r7 == 0) goto L_0x0049
                    java.lang.String r1 = ""
                L_0x0049:
                    if (r5 == 0) goto L_0x005d
                    int r7 = r5.size()     // Catch:{ Exception -> 0x0229 }
                    if (r7 == 0) goto L_0x005d
                    crv$2 r7 = new crv$2     // Catch:{ Exception -> 0x0229 }
                    r7.<init>(r5, r1, r4)     // Catch:{ Exception -> 0x0229 }
                    bim r4 = defpackage.bim.aa()     // Catch:{ Exception -> 0x0229 }
                    r4.a(r7)     // Catch:{ Exception -> 0x0229 }
                L_0x005d:
                    if (r6 == 0) goto L_0x0071
                    int r4 = r6.size()     // Catch:{ Exception -> 0x0229 }
                    if (r4 == 0) goto L_0x0071
                    crv$3 r4 = new crv$3     // Catch:{ Exception -> 0x0229 }
                    r4.<init>(r6, r1, r2)     // Catch:{ Exception -> 0x0229 }
                    bim r2 = defpackage.bim.aa()     // Catch:{ Exception -> 0x0229 }
                    r2.a(r4)     // Catch:{ Exception -> 0x0229 }
                L_0x0071:
                    com.amap.bundle.mapstorage.MapSharePreference r2 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ Exception -> 0x0229 }
                    com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ Exception -> 0x0229 }
                    r2.<init>(r4)     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r4 = "FIRST_MERGE_MAP_SET"
                    r5 = 0
                    boolean r4 = r2.getBooleanValue(r4, r5)     // Catch:{ Exception -> 0x0229 }
                    bim r6 = defpackage.bim.aa()     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r7 = "201"
                    int r6 = r6.a(r7)     // Catch:{ Exception -> 0x0229 }
                    r7 = 1
                    if (r4 != 0) goto L_0x009f
                    if (r6 > 0) goto L_0x009f
                    java.lang.String r4 = "FIRST_MERGE_MAP_SET"
                    r2.putBooleanValue(r4, r7)     // Catch:{ Exception -> 0x0229 }
                    crv$4 r4 = new crv$4     // Catch:{ Exception -> 0x0229 }
                    r4.<init>()     // Catch:{ Exception -> 0x0229 }
                    bim r6 = defpackage.bim.aa()     // Catch:{ Exception -> 0x0229 }
                    r6.a(r4)     // Catch:{ Exception -> 0x0229 }
                L_0x009f:
                    java.lang.String r4 = "truck_copyed_car_history"
                    boolean r4 = r2.getBooleanValue(r4, r5)     // Catch:{ Exception -> 0x0229 }
                    if (r4 != 0) goto L_0x00c4
                    com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x0229 }
                    java.util.List r4 = defpackage.chk.a(r4)     // Catch:{ Exception -> 0x0229 }
                    int r6 = r4.size()     // Catch:{ Exception -> 0x0229 }
                    if (r6 == 0) goto L_0x00bf
                    crv$5 r6 = new crv$5     // Catch:{ Exception -> 0x0229 }
                    r6.<init>(r4, r1)     // Catch:{ Exception -> 0x0229 }
                    bim r4 = defpackage.bim.aa()     // Catch:{ Exception -> 0x0229 }
                    r4.a(r6)     // Catch:{ Exception -> 0x0229 }
                L_0x00bf:
                    java.lang.String r4 = "truck_copyed_car_history"
                    r2.putBooleanValue(r4, r7)     // Catch:{ Exception -> 0x0229 }
                L_0x00c4:
                    java.lang.String r4 = "etrip_copyed_car_or_bus_history"
                    boolean r4 = r2.getBooleanValue(r4, r5)     // Catch:{ Exception -> 0x0229 }
                    if (r4 != 0) goto L_0x00fa
                    com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ Exception -> 0x0229 }
                    java.util.List r4 = defpackage.chk.a(r4)     // Catch:{ Exception -> 0x0229 }
                    com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.BUS     // Catch:{ Exception -> 0x0229 }
                    java.util.List r6 = defpackage.chk.a(r6)     // Catch:{ Exception -> 0x0229 }
                    int r8 = r6.size()     // Catch:{ Exception -> 0x0229 }
                    int r9 = r4.size()     // Catch:{ Exception -> 0x0229 }
                    if (r8 <= r9) goto L_0x00e3
                    r4 = r6
                L_0x00e3:
                    int r6 = r4.size()     // Catch:{ Exception -> 0x0229 }
                    if (r6 == 0) goto L_0x00f5
                    crv$6 r6 = new crv$6     // Catch:{ Exception -> 0x0229 }
                    r6.<init>(r4, r1)     // Catch:{ Exception -> 0x0229 }
                    bim r0 = defpackage.bim.aa()     // Catch:{ Exception -> 0x0229 }
                    r0.a(r6)     // Catch:{ Exception -> 0x0229 }
                L_0x00f5:
                    java.lang.String r0 = "etrip_copyed_car_or_bus_history"
                    r2.putBooleanValue(r0, r7)     // Catch:{ Exception -> 0x0229 }
                L_0x00fa:
                    bim r0 = defpackage.bim.aa()     // Catch:{ Exception -> 0x0229 }
                    r0.f()     // Catch:{ Exception -> 0x0229 }
                    com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ Exception -> 0x0229 }
                    com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r1 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.user_route_method_info     // Catch:{ Exception -> 0x0229 }
                    r0.<init>(r1)     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r1 = "car_no"
                    java.lang.String r2 = ""
                    java.lang.String r1 = r0.getStringValue(r1, r2)     // Catch:{ Exception -> 0x0229 }
                    boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0229 }
                    r8 = 1000(0x3e8, double:4.94E-321)
                    if (r2 != 0) goto L_0x015c
                    java.lang.String r1 = r1.trim()     // Catch:{ Exception -> 0x0229 }
                    boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0229 }
                    if (r2 != 0) goto L_0x015c
                    boolean r2 = defpackage.crv.d()     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r4 = ""
                    if (r2 == 0) goto L_0x012e
                    java.lang.String r4 = defpackage.crv.c()     // Catch:{ Exception -> 0x0229 }
                L_0x012e:
                    com.autonavi.map.db.model.Car r2 = new com.autonavi.map.db.model.Car     // Catch:{ Exception -> 0x0229 }
                    r2.<init>()     // Catch:{ Exception -> 0x0229 }
                    r2.plateNum = r1     // Catch:{ Exception -> 0x0229 }
                    r2.vehicleType = r7     // Catch:{ Exception -> 0x0229 }
                    long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0229 }
                    long r10 = r10 / r8
                    r2.updateTime = r10     // Catch:{ Exception -> 0x0229 }
                    r2.createTime = r10     // Catch:{ Exception -> 0x0229 }
                    esb r6 = defpackage.esb.a.a     // Catch:{ Exception -> 0x0229 }
                    java.lang.Class<ato> r10 = defpackage.ato.class
                    esc r6 = r6.a(r10)     // Catch:{ Exception -> 0x0229 }
                    ato r6 = (defpackage.ato) r6     // Catch:{ Exception -> 0x0229 }
                    if (r6 == 0) goto L_0x015c
                    atm r10 = r6.a()     // Catch:{ Exception -> 0x0229 }
                    r10.a(r4, r2)     // Catch:{ Exception -> 0x0229 }
                    atm r2 = r6.a()     // Catch:{ Exception -> 0x0229 }
                    r2.b(r7, r1)     // Catch:{ Exception -> 0x0229 }
                L_0x015c:
                    android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r1 = "car_no"
                    r0.remove(r1)     // Catch:{ Exception -> 0x0229 }
                    r0.apply()     // Catch:{ Exception -> 0x0229 }
                    defpackage.crv.b()     // Catch:{ Exception -> 0x0229 }
                    defpackage.crv.e()     // Catch:{ Exception -> 0x0229 }
                    com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ Exception -> 0x0229 }
                    com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r1 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.user_route_method_info     // Catch:{ Exception -> 0x0229 }
                    r0.<init>(r1)     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r1 = "truck_car_no"
                    java.lang.String r2 = ""
                    java.lang.String r1 = r0.getStringValue(r1, r2)     // Catch:{ Exception -> 0x0229 }
                    boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0229 }
                    if (r2 != 0) goto L_0x0221
                    esb r2 = defpackage.esb.a.a     // Catch:{ Exception -> 0x0229 }
                    java.lang.Class<ato> r4 = defpackage.ato.class
                    esc r2 = r2.a(r4)     // Catch:{ Exception -> 0x0229 }
                    ato r2 = (defpackage.ato) r2     // Catch:{ Exception -> 0x0229 }
                    if (r2 == 0) goto L_0x0199
                    atm r2 = r2.a()     // Catch:{ Exception -> 0x0229 }
                    com.autonavi.map.db.model.Car r3 = r2.a(r1)     // Catch:{ Exception -> 0x0229 }
                L_0x0199:
                    if (r3 == 0) goto L_0x01af
                    int r2 = r3.vehicleType     // Catch:{ Exception -> 0x0229 }
                    if (r2 != r7) goto L_0x01af
                    java.lang.String r2 = "CAR_AVOID_LIMITED_PATHS_SWITCH"
                    boolean r2 = r0.getBooleanValue(r2, r5)     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r3 = "truck_open_car_no"
                    boolean r3 = r0.getBooleanValue(r3, r5)     // Catch:{ Exception -> 0x0229 }
                    if (r2 != 0) goto L_0x0221
                    if (r3 == 0) goto L_0x0221
                L_0x01af:
                    com.autonavi.map.db.model.Car r2 = new com.autonavi.map.db.model.Car     // Catch:{ Exception -> 0x0229 }
                    r2.<init>()     // Catch:{ Exception -> 0x0229 }
                    r2.plateNum = r1     // Catch:{ Exception -> 0x0229 }
                    r3 = 2
                    r2.vehicleType = r3     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r4 = "truck_height"
                    r5 = 0
                    float r4 = r0.getFloatValue(r4, r5)     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r4 = defpackage.crv.a(r4)     // Catch:{ Exception -> 0x0229 }
                    r2.height = r4     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r4 = "truck_load"
                    float r4 = r0.getFloatValue(r4, r5)     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r5 = "truck_avoid_load"
                    boolean r5 = r0.getBooleanValue(r5, r7)     // Catch:{ Exception -> 0x0229 }
                    r2.truckAvoidWeightLimit = r5     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r4 = defpackage.crv.a(r4)     // Catch:{ Exception -> 0x0229 }
                    r2.weight = r4     // Catch:{ Exception -> 0x0229 }
                    long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0229 }
                    long r4 = r4 / r8
                    r2.updateTime = r4     // Catch:{ Exception -> 0x0229 }
                    r2.createTime = r4     // Catch:{ Exception -> 0x0229 }
                    boolean r4 = defpackage.crv.d()     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r5 = ""
                    if (r4 == 0) goto L_0x01ef
                    java.lang.String r5 = defpackage.crv.c()     // Catch:{ Exception -> 0x0229 }
                L_0x01ef:
                    esb r4 = defpackage.esb.a.a     // Catch:{ Exception -> 0x0229 }
                    java.lang.Class<ato> r6 = defpackage.ato.class
                    esc r4 = r4.a(r6)     // Catch:{ Exception -> 0x0229 }
                    ato r4 = (defpackage.ato) r4     // Catch:{ Exception -> 0x0229 }
                    if (r4 == 0) goto L_0x020b
                    atm r6 = r4.a()     // Catch:{ Exception -> 0x0229 }
                    r6.a(r5, r2)     // Catch:{ Exception -> 0x0229 }
                    atm r2 = r4.a()     // Catch:{ Exception -> 0x0229 }
                    r2.b(r3, r1)     // Catch:{ Exception -> 0x0229 }
                L_0x020b:
                    android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r1 = "truck_car_no"
                    r0.remove(r1)     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r1 = "truck_height"
                    r0.remove(r1)     // Catch:{ Exception -> 0x0229 }
                    java.lang.String r1 = "truck_load"
                    r0.remove(r1)     // Catch:{ Exception -> 0x0229 }
                    r0.apply()     // Catch:{ Exception -> 0x0229 }
                L_0x0221:
                    bim r0 = defpackage.bim.aa()     // Catch:{ Exception -> 0x0229 }
                    r0.z()     // Catch:{ Exception -> 0x0229 }
                    return
                L_0x0229:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: defpackage.crv.AnonymousClass1.run():void");
            }
        });
    }

    static String a(float f) {
        int round = Math.round(f);
        if (((float) round) == f) {
            return String.valueOf(round);
        }
        return String.valueOf(f);
    }

    public static void e() {
        if (d()) {
            String c = c();
            List<Vehicles> list = null;
            ato ato = (ato) a.a.a(ato.class);
            if (ato != null) {
                list = ato.b().c();
            }
            if (list != null && list.size() != 0) {
                a(c, list);
                if (ato != null) {
                    ato.b().d();
                }
            }
        }
    }

    private static void a(String str, List<Vehicles> list) {
        for (int i = 0; i < list.size(); i++) {
            Vehicles vehicles = list.get(i);
            Car car = new Car();
            car.vehicleType = 1;
            car.plateNum = vehicles.vehicle_plateNum;
            car.vehicleCode = vehicles.vehicle_vehiclecode;
            car.engineNum = vehicles.vehicle_engineNum;
            car.frameNum = vehicles.vehicle_frameNum;
            car.telphone = vehicles.vehicle_telephone;
            car.validityPeriod = vehicles.vehicle_validityPeriod;
            car.ocrRequestId = vehicles.ocrRequestId;
            car.vehicleMsg = vehicles.vehicle_vehicleMsg;
            car.vehicleLogo = vehicles.vehicle_vehicleLogo;
            if (vehicles.vehicle_violationReminder == null) {
                car.violationReminder = 0;
            } else {
                car.violationReminder = vehicles.vehicle_violationReminder.intValue();
            }
            if (vehicles.vehicle_checkReminder == null) {
                car.checkReminder = 0;
            } else {
                car.checkReminder = vehicles.vehicle_checkReminder.intValue();
            }
            if (vehicles.vehicle_limitReminder == null) {
                car.limitReminder = 0;
            } else {
                car.limitReminder = vehicles.vehicle_limitReminder.intValue();
            }
            car.createTime = vehicles.getModificationTimes() / 1000;
            car.updateTime = System.currentTimeMillis() / 1000;
            ato ato = (ato) a.a.a(ato.class);
            if (ato != null) {
                ato.a().a(str, car);
            }
            if (vehicles.isOftenUsed() && ato != null) {
                ato.a().b(1, vehicles.vehicle_plateNum);
            }
        }
    }

    public static JSONObject a(bti bti, String str) {
        if (bti == null) {
            return null;
        }
        switch (bti.c) {
            case 0:
                JSONObject jSONObject = new JSONObject();
                a(jSONObject, bti, str);
                asy asy = (asy) a.a.a(asy.class);
                if (asy != null) {
                    asy.c().a(jSONObject, bti.d());
                }
                return jSONObject;
            case 1:
                JSONObject jSONObject2 = new JSONObject();
                a(jSONObject2, bti, str);
                Object d = bti.d();
                if (d == null || !dia.class.isInstance(d)) {
                    return jSONObject2;
                }
                dia dia = (dia) bti.d();
                agd.a(jSONObject2, (String) "strategy", dia.getPathStrategy());
                agd.a(jSONObject2, (String) "mPathlength", dia.getPathlength());
                agd.a(jSONObject2, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME, dia.getCostTime());
                return jSONObject2;
            case 2:
                JSONObject jSONObject3 = new JSONObject();
                a(jSONObject3, bti, str);
                atb atb = (atb) a.a.a(atb.class);
                if (atb != null) {
                    atb.e().a(bti.d(), jSONObject3);
                }
                return jSONObject3;
            case 3:
                JSONObject jSONObject4 = new JSONObject();
                a(jSONObject4, bti, str);
                return jSONObject4;
            default:
                return null;
        }
    }

    private static void a(JSONObject jSONObject, bti bti, String str) {
        agd.a(jSONObject, (String) "type", 1);
        agd.a(jSONObject, (String) "version", bti.i);
        agd.a(jSONObject, (String) "id", str);
        agd.a(jSONObject, (String) "route_type", bti.c);
        Long valueOf = Long.valueOf(0);
        if (bti.s != null) {
            valueOf = bti.s;
        }
        agd.a(jSONObject, (String) "create_time", String.valueOf(((double) valueOf.longValue()) / 1000.0d));
        if (bti.c == 1) {
            agd.a(jSONObject, (String) "method", dhw.b(bti.h));
        } else {
            agd.a(jSONObject, (String) "method", bti.h);
        }
        agd.a(jSONObject, (String) "start_x", bti.d);
        agd.a(jSONObject, (String) "start_y", bti.e);
        agd.a(jSONObject, (String) "end_x", bti.f);
        agd.a(jSONObject, (String) "end_y", bti.g);
        agd.a(jSONObject, (String) "route_name", bti.j);
        agd.a(jSONObject, (String) "route_len", bti.l);
        agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME, bti.u);
        agd.a(jSONObject, (String) "route_alias", bti.q);
        a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_FROM_POI, bti.a());
        a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_TO_POI, bti.b());
        try {
            jSONObject.put("has_mid_poi", bti.p);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (bti.p) {
            agd.a(jSONObject, (String) "mid_pois", bti.o);
        }
    }

    private static void a(JSONObject jSONObject, String str, POI poi) {
        if (poi != null && jSONObject != null) {
            JSONObject jSONObject2 = new JSONObject();
            agd.a(jSONObject2, (String) "mId", poi.getId());
            agd.a(jSONObject2, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, poi.getName());
            agd.a(jSONObject2, (String) "mAddr", poi.getAddr());
            agd.a(jSONObject2, (String) "mCityCode", poi.getCityCode());
            agd.a(jSONObject2, (String) "mCityName", poi.getCityName());
            agd.a(jSONObject2, (String) "mx", poi.getPoint().x);
            agd.a(jSONObject2, (String) "my", poi.getPoint().y);
            agd.a(jSONObject2, (String) "mType", poi.getType());
            agd.a(jSONObject2, (String) "mEndPoiExtension", poi.getEndPoiExtension());
            agd.a(jSONObject2, (String) "mTransparent", poi.getTransparent());
            try {
                jSONObject.put(str, jSONObject2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void b() {
        ato ato = (ato) a.a.a(ato.class);
        List<Vehicles> a2 = ato != null ? ato.b().a() : null;
        int i = 0;
        if (a2 != null) {
            i = a2.size();
        }
        if (i != 0) {
            a((String) "", a2);
            if (ato != null) {
                ato.b().b();
            }
        }
    }

    static String c() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }

    static boolean d() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }
}
