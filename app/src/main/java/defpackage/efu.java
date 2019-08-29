package defpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.run.beans.RunTraceHistory;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.RunType;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.a;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: efu reason: default package */
/* compiled from: RunDataSavaUtil */
public final class efu {
    public static void a(@Nullable final RunTraceHistory runTraceHistory) {
        if (runTraceHistory.j != null) {
            ahm.a(new Runnable() {
                public final void run() {
                    btg btg = new btg();
                    btg.e = Double.valueOf(runTraceHistory.e);
                    btg.d = Integer.valueOf(runTraceHistory.d);
                    btg.b = Integer.valueOf(runTraceHistory.b);
                    btg.c = Integer.valueOf(runTraceHistory.c);
                    btg.h = runTraceHistory.h;
                    btg.f = Long.valueOf(runTraceHistory.f);
                    btg.g = Long.valueOf(runTraceHistory.g);
                    btg.i = RunTraceHistory.a(runTraceHistory.i);
                    StringBuilder sb = new StringBuilder();
                    sb.append(System.currentTimeMillis());
                    btg.a = agy.a(sb.toString());
                    btg.j = runTraceHistory.j.getValue();
                    AMapPageUtil.getAppContext();
                    bsq a2 = bsq.a();
                    if (a2.a != null) {
                        try {
                            a2.a.delete(btg);
                            a2.a.insertOrReplace(btg);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public static List<RunTraceHistory> a() {
        AMapPageUtil.getAppContext();
        List<btg> b = bsq.a().b();
        if (b == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (btg next : b) {
            RunTraceHistory runTraceHistory = new RunTraceHistory();
            if (next.a != null) {
                runTraceHistory.a = next.a;
            }
            if (next.e != null) {
                runTraceHistory.e = next.e.doubleValue();
            }
            if (next.d != null) {
                runTraceHistory.d = next.d.intValue();
            }
            if (next.b != null) {
                runTraceHistory.b = next.b.intValue();
            }
            if (next.c != null) {
                runTraceHistory.c = next.c.intValue();
            }
            if (next.h != null) {
                runTraceHistory.h = next.h;
            }
            if (next.f != null) {
                runTraceHistory.f = next.f.longValue();
            }
            if (next.g != null) {
                runTraceHistory.g = next.g.longValue();
            }
            runTraceHistory.i = RunTraceHistory.a(next.i);
            if (next.j != null && next.j.intValue() == 0) {
                runTraceHistory.j = RunType.RUN_TYPE;
            }
            if (next.j != null && next.j.intValue() == 1) {
                runTraceHistory.j = RunType.FOOT_TYPE;
            }
            if (next.l != null) {
                runTraceHistory.k = next.l.intValue();
            }
            if (next.m != null) {
                runTraceHistory.l = next.m.intValue();
            }
            if (next.n != null) {
                runTraceHistory.m = next.n.intValue();
            }
            if (next.o != null) {
                runTraceHistory.n = next.o.intValue();
            }
            if (next.q != null) {
                runTraceHistory.o = next.q;
            }
            if (next.p != null) {
                runTraceHistory.p = next.p.intValue();
            }
            arrayList.add(runTraceHistory);
        }
        return arrayList;
    }

    public static void a(String str) {
        try {
            RunTraceHistory runTraceHistory = new RunTraceHistory();
            JSONObject jSONObject = new JSONObject(new JSONObject(str).optString("trackInfo"));
            runTraceHistory.e = jSONObject.optDouble("averageSpeed");
            runTraceHistory.d = jSONObject.optInt("calorie");
            runTraceHistory.f = jSONObject.optLong("startTime");
            runTraceHistory.g = jSONObject.optLong(AppInitCallback.SP_KEY_endTime);
            int optInt = jSONObject.optInt("drivenTime");
            if (optInt == 0) {
                optInt = ((int) (runTraceHistory.g - runTraceHistory.f)) / 1000;
            }
            runTraceHistory.b = optInt;
            runTraceHistory.c = jSONObject.optInt("distance");
            String optString = jSONObject.optString("startName");
            String optString2 = jSONObject.optString("endName");
            b bVar = new b();
            bVar.a = POIFactory.createPOI();
            JSONObject optJSONObject = jSONObject.optJSONObject("startPoint");
            bVar.a.getPoint().x = optJSONObject.getInt(DictionaryKeys.CTRLXY_X);
            bVar.a.getPoint().y = optJSONObject.getInt(DictionaryKeys.CTRLXY_Y);
            bVar.a.setName(optString);
            bVar.b = POIFactory.createPOI();
            JSONObject optJSONObject2 = jSONObject.optJSONObject("endPoint");
            bVar.b.getPoint().x = optJSONObject2.getInt(DictionaryKeys.CTRLXY_X);
            bVar.b.getPoint().y = optJSONObject2.getInt(DictionaryKeys.CTRLXY_Y);
            bVar.b.setName(optString2);
            bVar.c = POIFactory.createPOI();
            JSONObject optJSONObject3 = jSONObject.optJSONObject("exitPoint");
            GeoPoint point = bVar.c.getPoint();
            point.x = optJSONObject3.getInt(DictionaryKeys.CTRLXY_X);
            point.y = optJSONObject3.getInt(DictionaryKeys.CTRLXY_Y);
            if (point.x > 0 && point.y > 0) {
                bVar.d = true;
            }
            bVar.e = new ArrayList<>();
            JSONArray jSONArray = jSONObject.getJSONArray("trackPoints");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                a aVar = new a();
                GeoPoint geoPoint = new GeoPoint();
                geoPoint.x = jSONObject2.optInt(DictionaryKeys.CTRLXY_X);
                geoPoint.y = jSONObject2.optInt(DictionaryKeys.CTRLXY_Y);
                aVar.a = POIFactory.createPOI();
                aVar.a.setPoint(geoPoint);
                bVar.e.add(aVar);
            }
            runTraceHistory.i = bVar;
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            runTraceHistory.a = agy.a(sb.toString());
            runTraceHistory.j = RunType.FOOT_TYPE;
            runTraceHistory.h = jSONObject.optString("imagePath");
            a(runTraceHistory);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String b(RunTraceHistory runTraceHistory) {
        if (runTraceHistory == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            b bVar = runTraceHistory.i;
            if (bVar != null) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(DictionaryKeys.CTRLXY_X, bVar.a.getPoint().x);
                jSONObject3.put(DictionaryKeys.CTRLXY_Y, bVar.a.getPoint().y);
                jSONObject2.put("startPoint", jSONObject3);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put(DictionaryKeys.CTRLXY_X, bVar.b.getPoint().x);
                jSONObject4.put(DictionaryKeys.CTRLXY_Y, bVar.b.getPoint().y);
                jSONObject2.put("endPoint", jSONObject4);
                if (runTraceHistory.i.c != null) {
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
                        int i = 0;
                        jSONObject6.put(DictionaryKeys.CTRLXY_X, (next.a == null || next.a.getPoint() == null) ? 0 : next.a.getPoint().x);
                        if (!(next.a == null || next.a.getPoint() == null)) {
                            i = next.a.getPoint().y;
                        }
                        jSONObject6.put(DictionaryKeys.CTRLXY_Y, i);
                        jSONArray.put(jSONObject6);
                    }
                }
                jSONObject2.put("trackPoints", jSONArray);
            }
            String str = "";
            String str2 = "";
            if (!(bVar == null || bVar.a == null)) {
                str = bVar.a.getName();
            }
            if (!(bVar == null || bVar.b == null)) {
                str2 = bVar.b.getName();
            }
            jSONObject2.put("startName", str);
            jSONObject2.put("endName", str2);
            jSONObject2.put("startTime", runTraceHistory.f);
            jSONObject2.put(AppInitCallback.SP_KEY_endTime, runTraceHistory.g);
            jSONObject2.put("drivenTime", runTraceHistory.b);
            jSONObject2.put("distance", runTraceHistory.c);
            jSONObject2.put("calorie", runTraceHistory.d);
            jSONObject2.put("averageSpeed", runTraceHistory.e);
            jSONObject2.put("imagePath", runTraceHistory.h);
            jSONObject2.put("his_distance", runTraceHistory.k);
            jSONObject2.put("ranking", runTraceHistory.l);
            jSONObject2.put("percent", runTraceHistory.m);
            jSONObject2.put("achievementImg", runTraceHistory.o);
            jSONObject2.put("isExistAch", runTraceHistory.n);
            jSONObject2.put("rising", runTraceHistory.p);
            jSONObject.put("traceInfo", jSONObject2);
            jSONObject.put("isFromHistory", true);
            jSONObject.put("returnType", 1);
            String a = edn.a();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("trackStorageFolder", a);
            }
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
