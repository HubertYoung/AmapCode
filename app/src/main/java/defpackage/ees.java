package defpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import com.amap.bundle.datamodel.poi.POIBase;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.HealthPointStatus;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.a;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: ees reason: default package */
/* compiled from: RideDataSaveUtil */
public final class ees {
    public static String a() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("rideshot");
        sb2.append(System.currentTimeMillis());
        sb.append(agy.a(sb2.toString()));
        sb.append(".png");
        return sb.toString();
    }

    public static RideTraceHistory a(TraceStatistics traceStatistics, long j, long j2) {
        HealthPoint[] healthPointArr;
        RideTraceHistory rideTraceHistory = new RideTraceHistory();
        rideTraceHistory.e = traceStatistics.average_speed;
        rideTraceHistory.d = traceStatistics.calorie;
        rideTraceHistory.b = (int) traceStatistics.trace_time;
        rideTraceHistory.c = traceStatistics.trace_length;
        rideTraceHistory.g = j;
        rideTraceHistory.h = j2;
        if (rideTraceHistory.b == 0) {
            long j3 = (j2 - j) / 1000;
            if (j3 > 0) {
                rideTraceHistory.b = (int) j3;
            }
        }
        rideTraceHistory.f = traceStatistics.max_speed;
        ArrayList<a> arrayList = new ArrayList<>();
        if (traceStatistics.gps_array != null) {
            for (HealthPoint healthPoint : traceStatistics.gps_array) {
                a aVar = new a();
                POIBase pOIBase = new POIBase();
                pOIBase.setPoint(new GeoPoint(healthPoint.longitude, healthPoint.latitude));
                aVar.a = pOIBase;
                aVar.b = healthPoint.status == HealthPointStatus.HPS_PAUSE ? 1 : 0;
                aVar.c = (int) healthPoint.speed;
                arrayList.add(aVar);
            }
        }
        b bVar = new b();
        bVar.e = arrayList;
        bVar.a = null;
        bVar.b = null;
        bVar.c = null;
        bVar.d = false;
        rideTraceHistory.j = bVar;
        rideTraceHistory.i = a();
        rideTraceHistory.k = RideType.SHARE_RIDE_TYPE;
        StringBuilder sb = new StringBuilder();
        sb.append(rideTraceHistory.g);
        sb.append(Token.SEPARATOR);
        sb.append(rideTraceHistory.h);
        rideTraceHistory.a = agy.a(sb.toString());
        return rideTraceHistory;
    }

    public static void a(@Nullable final RideTraceHistory rideTraceHistory) {
        eao.f("tylorvan", "saveRideHistory");
        if (rideTraceHistory != null && rideTraceHistory.k != null) {
            ahm.a(new Runnable() {
                public final void run() {
                    bte bte = new bte();
                    bte.e = Double.valueOf(rideTraceHistory.e);
                    bte.l = Double.valueOf(rideTraceHistory.f);
                    bte.d = Integer.valueOf(rideTraceHistory.d);
                    bte.b = Integer.valueOf(rideTraceHistory.b);
                    bte.c = Integer.valueOf(rideTraceHistory.c);
                    bte.h = rideTraceHistory.i;
                    bte.f = Long.valueOf(rideTraceHistory.g);
                    bte.g = Long.valueOf(rideTraceHistory.h);
                    bte.i = RideTraceHistory.a(rideTraceHistory.j);
                    StringBuilder sb = new StringBuilder();
                    sb.append(System.currentTimeMillis());
                    bte.a = agy.a(sb.toString());
                    bte.j = rideTraceHistory.k.getValue();
                    if (RideType.SHARE_RIDE_TYPE.equals(rideTraceHistory.k)) {
                        eab eab = new eab();
                        eab.d = rideTraceHistory.i;
                        eab.b = ehs.b("share_bike_cp_source");
                        eab.a = ehs.b("share_bike_order_id");
                        eab.c = String.valueOf(bte.a);
                        if (!TextUtils.isEmpty(eab.a) && eaa.a().b(eab.a) == null) {
                            eaa a2 = eaa.a();
                            if (a2.a != null) {
                                a2.a.insertOrReplace(eab);
                            }
                        } else {
                            return;
                        }
                    }
                    StringBuilder sb2 = new StringBuilder("save history: ");
                    sb2.append(bte.h);
                    eao.a((String) "tao----", sb2.toString());
                    AMapPageUtil.getAppContext();
                    bsp a3 = bsp.a();
                    if (a3.a != null) {
                        try {
                            a3.a.delete(bte);
                            a3.a.insertOrReplace(bte);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public static RideTraceHistory a(bte bte) {
        if (bte == null) {
            return null;
        }
        RideTraceHistory rideTraceHistory = new RideTraceHistory();
        if (bte.a != null) {
            rideTraceHistory.a = bte.a;
        }
        if (bte.e != null) {
            rideTraceHistory.e = bte.e.doubleValue();
        }
        if (bte.l != null) {
            rideTraceHistory.f = bte.l.doubleValue();
        }
        if (bte.d != null) {
            rideTraceHistory.d = bte.d.intValue();
        }
        if (bte.b != null) {
            rideTraceHistory.b = bte.b.intValue();
        }
        if (bte.c != null) {
            rideTraceHistory.c = bte.c.intValue();
        }
        if (bte.h != null) {
            rideTraceHistory.i = bte.h;
        }
        if (bte.f != null) {
            rideTraceHistory.g = bte.f.longValue();
        }
        if (bte.g != null) {
            rideTraceHistory.h = bte.g.longValue();
        }
        rideTraceHistory.j = RideTraceHistory.a(bte.i);
        if (bte.j != null && bte.j.intValue() == 0) {
            rideTraceHistory.k = RideType.RIDE_TYPE;
        }
        if (bte.j != null && bte.j.intValue() == 1) {
            rideTraceHistory.k = RideType.DEST_TYPE;
        }
        if (bte.j != null && bte.j.intValue() == 2) {
            rideTraceHistory.k = RideType.SHARE_RIDE_TYPE;
        }
        return rideTraceHistory;
    }

    public static String b(RideTraceHistory rideTraceHistory) {
        if (rideTraceHistory == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            b bVar = rideTraceHistory.j;
            if (bVar != null) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(DictionaryKeys.CTRLXY_X, bVar.a.getPoint().x);
                jSONObject3.put(DictionaryKeys.CTRLXY_Y, bVar.a.getPoint().y);
                jSONObject2.put("startPoint", jSONObject3);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put(DictionaryKeys.CTRLXY_X, bVar.b.getPoint().x);
                jSONObject4.put(DictionaryKeys.CTRLXY_Y, bVar.b.getPoint().y);
                jSONObject2.put("endPoint", jSONObject4);
                if (rideTraceHistory.j.c != null) {
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject5.put(DictionaryKeys.CTRLXY_X, bVar.c.getPoint().x);
                    jSONObject5.put(DictionaryKeys.CTRLXY_Y, bVar.c.getPoint().y);
                    jSONObject2.put("exitPoint", jSONObject5);
                }
                JSONArray jSONArray = new JSONArray();
                ArrayList<a> arrayList = bVar.e;
                if (arrayList != null && !arrayList.isEmpty()) {
                    Iterator<a> it = arrayList.iterator();
                    while (it.hasNext()) {
                        a next = it.next();
                        JSONObject jSONObject6 = new JSONObject();
                        jSONObject6.put(DictionaryKeys.CTRLXY_X, next.a());
                        jSONObject6.put(DictionaryKeys.CTRLXY_Y, next.b());
                        jSONArray.put(jSONObject6);
                    }
                }
                jSONObject2.put("trackPoints", jSONArray);
            }
            String str = rideTraceHistory.l;
            String str2 = rideTraceHistory.m;
            if (!(bVar == null || bVar.a == null)) {
                str = bVar.a.getName();
            }
            if (!(bVar == null || bVar.b == null)) {
                str2 = bVar.b.getName();
            }
            jSONObject2.put("startName", str);
            jSONObject2.put("endName", str2);
            jSONObject2.put("startTime", rideTraceHistory.g);
            jSONObject2.put(AppInitCallback.SP_KEY_endTime, rideTraceHistory.h);
            jSONObject2.put("drivenTime", rideTraceHistory.b);
            jSONObject2.put("distance", rideTraceHistory.c);
            jSONObject2.put("calorie", rideTraceHistory.d);
            jSONObject2.put("averageSpeed", rideTraceHistory.e);
            jSONObject2.put("maxSpeed", rideTraceHistory.f);
            jSONObject2.put("imagePath", rideTraceHistory.i);
            jSONObject.put("traceInfo", jSONObject2);
            jSONObject.put("isFromHistory", true);
            jSONObject.put("returnType", 1);
            String a = edn.a();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("trackStorageFolder", a);
            }
            StringBuilder sb = new StringBuilder("ride history to rideEndPage json str = ");
            sb.append(jSONObject.toString());
            eao.b("songping:", sb.toString());
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<RideTraceHistory> b() {
        ArrayList arrayList;
        AMapPageUtil.getAppContext();
        List<bte> b = bsp.a().b();
        if (b != null) {
            arrayList = new ArrayList();
            for (bte a : b) {
                arrayList.add(a(a));
            }
            Collections.sort(arrayList, new Comparator<RideTraceHistory>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return eax.a(((RideTraceHistory) obj2).h, ((RideTraceHistory) obj).h);
                }
            });
        } else {
            arrayList = null;
        }
        if (arrayList != null) {
            arrayList.size();
        }
        return arrayList;
    }
}
