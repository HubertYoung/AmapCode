package defpackage;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.bundle.routecommute.net.CommuteNetManager$4;
import com.autonavi.bundle.routecommute.net.CommuteRequestHolder;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.jni.ae.route.RouteService;
import com.autonavi.map.db.model.Car;
import com.autonavi.minimap.navigation.NavigationRequestHolder;
import com.autonavi.minimap.navigation.param.EtaRouteRequest;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import com.autonavi.sdk.location.LocationInstrument;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bau reason: default package */
/* compiled from: CommuteNetManager */
public final class bau {
    azs a;
    private final String b = "CommuteNetManager";

    /* renamed from: bau$a */
    /* compiled from: CommuteNetManager */
    public interface a {
        void a();

        void a(Object obj, int i);
    }

    /* renamed from: bau$b */
    /* compiled from: CommuteNetManager */
    public class b {
        public boolean a = false;

        public b() {
        }
    }

    public final b a(int i, a aVar) {
        switch (i) {
            case 1:
            case 2:
            case 5:
            case 6:
                b(i, aVar);
                break;
            case 3:
            case 4:
            case 7:
            case 8:
                c(i, aVar);
                break;
            case 9:
            case 10:
                d(i, aVar);
                break;
        }
        return new b();
    }

    private void b(int i, a aVar) {
        azb.a("CommuteNetManager", "requestCommuteEtaEtdRestrictData");
        azw azw = new azw();
        a(azw);
        b(azw);
        d(azw);
        e(azw);
        a(i, azw, aVar);
    }

    private void c(int i, a aVar) {
        azb.a("CommuteNetManager", "requestCommuteEtaEtdRestrictData");
        azw azw = new azw();
        a(azw);
        c(azw);
        d(azw);
        e(azw);
        a(i, azw, aVar);
    }

    private void a(final int i, azw azw, final a aVar) {
        azb.a("CommuteNetManager", "requestCommuteEtaEtdRestrictData  param:".concat(String.valueOf(azw)));
        CommuteRequestHolder.getInstance().sendEtaRoute(azw, new dko<azv>() {
            public final /* synthetic */ void a(dkm dkm) {
                azv azv = (azv) dkm;
                azb.a("CommuteNetManager", "requestCommuteEtaEtdRestrictData  onSuccess    response:".concat(String.valueOf(azv)));
                if (aVar != null) {
                    if (azv == null || azv.a == null) {
                        aVar.a();
                    } else {
                        aVar.a(azv.a, i);
                    }
                }
            }

            public final void a(Exception exc) {
                azb.a("CommuteNetManager", "requestCommuteEtaEtdRestrictData  onError:".concat(String.valueOf(exc)));
                if (aVar != null) {
                    aVar.a();
                }
            }
        });
    }

    private void d(int i, final a aVar) {
        this.a = new azs();
        bam bam = new bam();
        a(bam);
        e(bam);
        d(bam);
        a(i, bam, (a) new a() {
            public final void a(Object obj, int i) {
                StringBuilder sb = new StringBuilder("requestCPointData  homeEtaRequestParam onSuccess  thread:");
                sb.append(Thread.currentThread());
                azb.a("ddddd", sb.toString());
                if (aVar != null && bau.this.a != null) {
                    bau.this.a.a = (azt) obj;
                    if (bau.this.a.b != null) {
                        aVar.a(bau.this.a, i);
                    }
                }
            }

            public final void a() {
                azb.a("ddddd", "requestCPointData  homeEtaRequestParam onError");
                bau.this.a = null;
                if (aVar != null) {
                    aVar.a();
                }
            }
        });
        bam bam2 = new bam();
        b(bam2);
        e(bam2);
        d(bam2);
        a(i, bam2, (a) new a() {
            public final void a(Object obj, int i) {
                StringBuilder sb = new StringBuilder("requestCPointData  companyEtaRequestParam onSuccess  thread:");
                sb.append(Thread.currentThread());
                azb.a("ddddd", sb.toString());
                if (aVar != null && bau.this.a != null) {
                    bau.this.a.b = (azt) obj;
                    if (bau.this.a.a != null) {
                        aVar.a(bau.this.a, i);
                    }
                }
            }

            public final void a() {
                azb.a("ddddd", "requestCPointData  companyEtaRequestParam onError");
                bau.this.a = null;
                if (aVar != null) {
                    aVar.a();
                }
            }
        });
    }

    private void a(int i, bam bam, a aVar) {
        EtaRouteRequest etaRouteRequest = new EtaRouteRequest();
        Map<String, String> c = c(bam);
        new StringBuilder("Request params=").append(bam.toString());
        CommuteNetManager$4 commuteNetManager$4 = new CommuteNetManager$4(this, aVar, i);
        etaRouteRequest.addReqParams(c);
        NavigationRequestHolder.getInstance().sendEtaRoute(etaRouteRequest, commuteNetManager$4);
    }

    private static void a(azw azw, GeoPoint geoPoint) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("type", 0);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(0, geoPoint.getLongitude());
            jSONArray.put(1, geoPoint.getLatitude());
            jSONObject2.put("coor", jSONArray);
            jSONObject.put("start_point", jSONObject2);
            jSONObject.put("gpsinfo", a());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        azw.o = jSONObject.toString();
    }

    private static JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            if (latestLocation != null) {
                Bundle extras = latestLocation.getExtras();
                jSONObject.put("angle", (double) ((float) extras.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_ROAD_COURSE, -1.0d)));
                jSONObject.put("angle_type", String.valueOf(extras.getInt(LocationInstrument.LOCATION_EXTRAS_KEY_COURSE_TYPE, -1)));
                jSONObject.put("speed", (double) latestLocation.getSpeed());
                jSONObject.put("precision", String.valueOf(latestLocation.getAccuracy()));
                LocInfo locInfo = LocationInstrument.getInstance().getLocInfo();
                if (locInfo != null) {
                    jSONObject.put("credibility", locInfo.courseAcc);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private static int a(GeoPoint geoPoint) {
        if (geoPoint == null) {
            return -1;
        }
        lj b2 = li.a().b(geoPoint.x, geoPoint.y);
        if (b2 == null) {
            return -1;
        }
        return b2.j;
    }

    private static void b(azw azw) {
        NaviAddress b2 = azf.b();
        if (b2 != null && b2.home != null && b2.home.getHome() != null) {
            azw.c = String.valueOf(b2.home.getHome().getPoint().getLongitude());
            azw.d = String.valueOf(b2.home.getHome().getPoint().getLatitude());
            a(azw, b2.home.getHome());
            lj b3 = li.a().b(b2.home.getHome().getPoint().x, b2.home.getHome().getPoint().y);
            if (b3 != null) {
                azw.j = b3.j;
            }
        }
    }

    private static void c(azw azw) {
        NaviAddress b2 = azf.b();
        if (b2 != null && b2.company != null && b2.company.getCompany() != null) {
            azw.c = String.valueOf(b2.company.getCompany().getPoint().getLongitude());
            azw.d = String.valueOf(b2.company.getCompany().getPoint().getLatitude());
            a(azw, b2.company.getCompany());
            lj b3 = li.a().b(b2.company.getCompany().getPoint().x, b2.company.getCompany().getPoint().y);
            if (b3 != null) {
                azw.j = b3.j;
            }
        }
    }

    private static void a(azw azw, POI poi) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", 2);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(0, poi.getPoint().getLongitude());
            jSONArray.put(1, poi.getPoint().getLatitude());
            jSONObject2.put("coor", jSONArray);
            jSONObject.put("end_point", jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("name", poi.getName());
            jSONObject3.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId());
            jSONObject3.put("type_code", poi.getType());
            jSONObject.put("poiinfo", jSONObject3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        azw.p = jSONObject.toString();
    }

    private static void d(azw azw) {
        djk djk = (djk) ank.a(djk.class);
        String str = "";
        if (djk != null) {
            str = djk.l();
        }
        if (!TextUtils.isEmpty(str)) {
            str.equals("1");
        }
        azw.e = str;
        azw.f = 0;
        azw.g = "4.0";
        Car c = c();
        if (c != null) {
            azw.k = c.plateNum;
            azw.l = c.vehiclePowerType;
            azw.r = DriveUtil.getVtype(c, 0);
        }
        azw.q = 0;
        azw.h = RouteService.getSdkVersion();
    }

    private static void e(azw azw) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("invoker", "commute");
            jSONObject.put("frompage", "homepage");
            jSONObject.put("type", 1);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("first_timestamp", b());
            jSONObject2.put(H5SensorPlugin.PARAM_INTERVAL, 900);
            jSONObject2.put(NewHtcHomeBadger.COUNT, 3);
            jSONObject.put("t_profile", jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        azw.s = jSONObject.toString();
    }

    private static String b() {
        Calendar instance = Calendar.getInstance();
        instance.add(12, a(instance.get(12)));
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        if (bno.a) {
            Date date = new Date();
            date.setTime(timeInMillis);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            azb.a("CommuteNetManager", "getEtdFirstTimeStamp    etdFirstTimeStamp:".concat(String.valueOf(simpleDateFormat.format(date))));
            StringBuilder sb = new StringBuilder("getEtdFirstTimeStamp          currentTime:");
            sb.append(simpleDateFormat.format(new Date()));
            azb.a("CommuteNetManager", sb.toString());
        }
        return String.valueOf(timeInMillis / 1000);
    }

    private static int a(int i) {
        int i2 = (i + 3) % 5;
        if (i2 != 0) {
            return 3 + (5 - i2);
        }
        return 3;
    }

    private static void a(bam bam) {
        NaviAddress b2 = azf.b();
        if (b2 != null && b2.home != null && b2.home.getHome() != null) {
            bam.c = b2.home.getHome().getPoint().getLongitude();
            bam.d = b2.home.getHome().getPoint().getLatitude();
        }
    }

    private static void b(bam bam) {
        NaviAddress b2 = azf.b();
        if (b2 != null && b2.company != null && b2.company.getCompany() != null) {
            bam.c = b2.company.getCompany().getPoint().getLongitude();
            bam.d = b2.company.getCompany().getPoint().getLatitude();
        }
    }

    @NonNull
    private static Map<String, String> c(bam bam) {
        HashMap hashMap = new HashMap();
        hashMap.put(LocationParams.PARA_FLP_AUTONAVI_LON, String.valueOf(bam.a));
        hashMap.put("lat", String.valueOf(bam.b));
        hashMap.put(DictionaryKeys.CTRLXY_X, String.valueOf(bam.c));
        hashMap.put(DictionaryKeys.CTRLXY_Y, String.valueOf(bam.d));
        hashMap.put("policy2", bam.e);
        hashMap.put("multi_routes", String.valueOf(bam.f));
        return hashMap;
    }

    private static void d(bam bam) {
        djk djk = (djk) ank.a(djk.class);
        String str = "";
        if (djk != null) {
            str = djk.l();
        }
        if (!TextUtils.isEmpty(str)) {
            str.equals("1");
        }
        bam.e = str;
    }

    private static void a(azw azw) {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition == null) {
            latestPosition = new GeoPoint();
        }
        azw.a = String.valueOf(latestPosition.getLongitude());
        azw.b = String.valueOf(latestPosition.getLatitude());
        azw.i = a(latestPosition);
        a(azw, latestPosition);
    }

    private static Car c() {
        ato ato = (ato) defpackage.esb.a.a.a(ato.class);
        if (ato != null) {
            return ato.a().b(1);
        }
        return null;
    }

    private static void e(bam bam) {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition == null) {
            latestPosition = new GeoPoint();
        }
        bam.a = latestPosition.getLongitude();
        bam.b = latestPosition.getLatitude();
    }
}
