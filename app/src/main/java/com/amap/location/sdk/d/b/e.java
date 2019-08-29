package com.amap.location.sdk.d.b;

import android.content.Context;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.FPS;
import com.amap.location.e.c;
import com.amap.location.e.f;
import com.amap.location.icecream.b;
import com.amap.location.sdk.BuildConfig;
import com.amap.location.sdk.b.a.d;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import com.google.gson.Gson;
import org.json.JSONObject;

/* compiled from: NetworkLocationProvider */
public class e extends c {
    private c b;
    private Context c;
    private f d;
    /* access modifiers changed from: private */
    public com.amap.location.c.a e = new com.amap.location.c.a();
    private boolean f;
    private boolean g;
    /* access modifiers changed from: private */
    public a h;
    private long i = -1;
    /* access modifiers changed from: private */
    public boolean j = false;
    private com.amap.location.e.a k = new com.amap.location.e.a() {
        public void a(AmapLoc amapLoc, FPS fps) {
            if (amapLoc == null || !amapLoc.isLocationCorrect()) {
                a a2 = e.this.h;
                if (a2 != null) {
                    a2.a(amapLoc);
                }
                e.this.a((String) "null");
                return;
            }
            boolean z = false;
            String type = amapLoc.getType();
            if (!e.this.j) {
                int locType = AmapLoc.getLocType(amapLoc);
                long time = amapLoc.getTime();
                amapLoc = e.this.e.a(amapLoc);
                if (!(amapLoc == null || amapLoc.getTime() == time)) {
                    amapLoc.setIsLast(true);
                    z = true;
                }
                if (amapLoc == null) {
                    switch (locType) {
                        case 1:
                            d.a(108);
                            break;
                        case 2:
                            d.a(100042);
                            d.a(107);
                            break;
                        case 3:
                            d.a(100048);
                            d.a(107);
                            break;
                        case 4:
                            d.a(100044);
                            break;
                    }
                    com.amap.location.common.d.a.c("netloc", "filter netloc and return null");
                    e.this.a((String) "filtered");
                    return;
                }
            }
            Location a3 = e.b(amapLoc, z);
            e.this.a(a3);
            e.this.a(type);
            if (b.a().a(1) > 0 && fps != null) {
                fps.latitude = a3.getLatitude();
                fps.longitude = a3.getLongitude();
                fps.accuracy = (double) a3.getAccuracy();
                fps.provider = (byte) AmapLoc.getLocType(amapLoc);
                try {
                    b.a().a(new Gson().toJson((Object) fps));
                    com.amap.location.common.d.a.b("netloc", "report fps to icecream");
                } catch (Throwable unused) {
                }
            }
        }
    };

    /* compiled from: NetworkLocationProvider */
    public interface a {
        void a(AmapLoc amapLoc);
    }

    public e(Context context, com.amap.location.sdk.d.a aVar, JSONObject jSONObject, boolean z) {
        super(aVar);
        this.c = context;
        this.b = new c();
        this.b.b = "ABKLWEH8H9LH09NLB5CCAGHK78BYZ89";
        this.b.a = "AmapSS_v2.0_R131029";
        this.b.c = BuildConfig.VERSION_NAME;
        this.b.d = com.amap.location.sdk.b.b.d();
        g.a(jSONObject, this.b.l);
        this.b.k = g.a(jSONObject, z);
        this.b.m = g.a(context);
        this.b.j = new h();
        this.b.h = false;
        this.b.i = true;
        this.d = new f(this.b);
    }

    public void a(int i2, boolean z) {
        if (!this.f) {
            this.d.a(this.c);
            this.f = true;
        }
        if (z) {
            this.i = SystemClock.elapsedRealtime();
        }
        this.g = true;
        this.d.a(i2, this.k, z);
    }

    public void a() {
        this.i = -1;
        this.g = false;
        this.d.b();
    }

    public void a(@NonNull JSONObject jSONObject, boolean z) {
        g.a(jSONObject, this.b.l);
        c.b a2 = g.a(jSONObject, z);
        com.amap.location.offline.c a3 = g.a(this.c);
        this.d.a(a2);
        this.d.a(a3);
    }

    public void a(boolean z) {
        this.j = z;
        ((h) this.b.j).a(z);
    }

    public void b(boolean z) {
        this.b.h = false;
    }

    public String b() {
        try {
            return this.d.c();
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
            return "";
        }
    }

    public boolean c() {
        return this.f && this.g;
    }

    public void d() {
        this.f = false;
        this.d.a();
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if (this.i >= 0) {
            this.i = -1;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(",");
            sb.append(SystemClock.elapsedRealtime() - this.i);
            String sb2 = sb.toString();
            d.a(100032, sb2.getBytes());
            com.amap.location.common.d.a.b("netloc", "first loc success:".concat(String.valueOf(sb2)));
        }
    }

    /* access modifiers changed from: private */
    public static Location b(AmapLoc amapLoc, boolean z) {
        Location location = new Location("network");
        location.setLatitude(amapLoc.getLat());
        location.setLongitude(amapLoc.getLon());
        location.setAccuracy(amapLoc.getAccuracy());
        location.setTime(System.currentTimeMillis());
        if (VERSION.SDK_INT >= 17) {
            location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        String poiid = amapLoc.getPoiid();
        String floor = amapLoc.getFloor();
        StringBuilder sb = new StringBuilder();
        sb.append(amapLoc.getRetype());
        String sb2 = sb.toString();
        String poiname = amapLoc.getPoiname();
        String desc = amapLoc.getDesc();
        String country = amapLoc.getCountry();
        String citycode = amapLoc.getCitycode();
        String adcode = amapLoc.getAdcode();
        String province = amapLoc.getProvince();
        String city = amapLoc.getCity();
        String district = amapLoc.getDistrict();
        String road = amapLoc.getRoad();
        String street = amapLoc.getStreet();
        int scenarioConfidence = amapLoc.getScenarioConfidence();
        String subType = amapLoc.getSubType();
        Location location2 = location;
        boolean isLast = amapLoc.getIsLast();
        int coord = amapLoc.getCoord();
        Bundle bundle = new Bundle();
        String str = subType;
        bundle.putString("retype", sb2);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(amapLoc.getType());
        bundle.putString("type", sb3.toString());
        bundle.putInt("locType", AmapLoc.getLocType(amapLoc));
        bundle.putString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poiid);
        bundle.putString("floor", floor);
        bundle.putString("poiname", poiname);
        bundle.putString("desc", desc);
        bundle.putString("citycode", citycode);
        bundle.putString(AutoJsonUtils.JSON_ADCODE, adcode);
        bundle.putString("country", country);
        bundle.putString("province", province);
        bundle.putString("city", city);
        bundle.putString("district", district);
        bundle.putString("road", road);
        bundle.putString("street", street);
        bundle.putFloat(LocationInstrument.OPTIMIZD_ACCURACY_KEY, amapLoc.getAccuracy());
        bundle.putInt("scenarioConfidence", scenarioConfidence);
        String str2 = str;
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, str2);
        }
        bundle.putBoolean("isLast", isLast);
        bundle.putBoolean("isFilter", z);
        if (coord == 0) {
            bundle.putString("coord", "WGS84");
        } else if (coord == 1) {
            bundle.putString("coord", "GCJ02");
        }
        if (!TextUtils.isEmpty(amapLoc.getServerTraceId())) {
            bundle.putString("serverTraceId", amapLoc.getServerTraceId());
        }
        Location location3 = location2;
        location3.setExtras(bundle);
        return location3;
    }

    public void a(a aVar) {
        this.h = aVar;
    }
}
