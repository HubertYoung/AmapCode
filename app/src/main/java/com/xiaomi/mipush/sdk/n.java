package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.alipay.mobile.mrtc.api.constants.APCallCode;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.xiaomi.channel.commonutils.android.f;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.misc.c;
import com.xiaomi.channel.commonutils.misc.h.a;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.push.service.an;
import com.xiaomi.push.service.k;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.g;
import com.xiaomi.xmpush.thrift.l;
import com.xiaomi.xmpush.thrift.o;
import com.xiaomi.xmpush.thrift.p;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;
import com.xiaomi.xmpush.thrift.y;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class n extends a {
    private final int a = -1;
    private final int b = 3600;
    private Context c;
    private int d;

    public n(Context context, int i) {
        this.c = context;
        this.d = i;
    }

    private static Location a(Location location, Location location2) {
        return location == null ? location2 : (location2 != null && location.getTime() <= location2.getTime()) ? location2 : location;
    }

    public static void a(Context context, boolean z) {
        p b2 = b(context);
        byte[] a2 = au.a(b2);
        ai aiVar = new ai("-1", false);
        aiVar.c(r.GeoUpdateLoc.W);
        aiVar.a(a2);
        aiVar.a((Map<String, String>) new HashMap<String,String>());
        aiVar.i().put(Constants.EXTRA_KEY_INITIAL_WIFI_UPLOAD, String.valueOf(z));
        boolean b3 = k.b(context);
        if (b3) {
            aiVar.i().put(Constants.EXTRA_KEY_XMSF_GEO_IS_WORK, String.valueOf(b3));
        }
        StringBuilder sb = new StringBuilder("reportLocInfo locInfo timestamp:");
        sb.append(System.currentTimeMillis());
        sb.append(",");
        sb.append(String.valueOf(b2.c() != null ? b2.c() : "null"));
        sb.append(",");
        sb.append(String.valueOf(b2.b != null ? b2.b.toString() : null));
        sb.append(",");
        sb.append(String.valueOf(b2.a != null ? b2.a.toString() : null));
        b.c(sb.toString());
        aj.a(context).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, true, (u) null);
        g(context);
    }

    private boolean a(long j) {
        return ((float) Math.abs((System.currentTimeMillis() / 1000) - this.c.getSharedPreferences("mipush_extra", 0).getLong("last_upload_lbs_data_timestamp", -1))) > ((float) j) * 0.9f;
    }

    protected static boolean a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        return (packageManager.checkPermission("android.permission.ACCESS_COARSE_LOCATION", packageName) == 0) || (packageManager.checkPermission("android.permission.ACCESS_FINE_LOCATION", packageName) == 0);
    }

    private static p b(Context context) {
        p pVar = new p();
        if (!f.e()) {
            return pVar;
        }
        pVar.a(c(context));
        pVar.b(d(context));
        pVar.a(e(context));
        return pVar;
    }

    private boolean b() {
        if (d.e(this.c)) {
            return true;
        }
        return d.f(this.c) && a((long) Math.max(60, an.a(this.c).a(g.UploadNOWIFIGeoLocFrequency.a(), 3600)));
    }

    private static List<y> c(Context context) {
        ArrayList arrayList;
        o oVar = new o();
        try {
            List<ScanResult> scanResults = ((WifiManager) context.getSystemService("wifi")).getScanResults();
            if (!c.a(scanResults)) {
                Collections.sort(scanResults, oVar);
                arrayList = new ArrayList();
                for (int i = 0; i < Math.min(30, scanResults.size()); i++) {
                    ScanResult scanResult = scanResults.get(i);
                    if (scanResult != null) {
                        y yVar = new y();
                        yVar.a(TextUtils.isEmpty(scanResult.BSSID) ? "" : scanResult.BSSID);
                        yVar.a(scanResult.level);
                        yVar.b(scanResult.SSID);
                        arrayList.add(yVar);
                    }
                }
            } else {
                arrayList = null;
            }
            return arrayList;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static List<com.xiaomi.xmpush.thrift.c> d(Context context) {
        try {
            List neighboringCellInfo = ((TelephonyManager) context.getSystemService("phone")).getNeighboringCellInfo();
            int i = 0;
            ArrayList arrayList = null;
            while (i < neighboringCellInfo.size()) {
                NeighboringCellInfo neighboringCellInfo2 = (NeighboringCellInfo) neighboringCellInfo.get(i);
                ArrayList arrayList2 = new ArrayList();
                if (neighboringCellInfo2.getLac() > 0 || neighboringCellInfo2.getCid() > 0) {
                    com.xiaomi.xmpush.thrift.c cVar = new com.xiaomi.xmpush.thrift.c();
                    cVar.a(neighboringCellInfo2.getCid());
                    cVar.b((neighboringCellInfo2.getRssi() * 2) + APCallCode.CALL_ERROR_INNER);
                    arrayList2.add(cVar);
                }
                i++;
                arrayList = arrayList2;
            }
            return arrayList;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static l e(Context context) {
        l lVar = null;
        if (!a(context)) {
            return null;
        }
        Location f = f(context);
        if (f != null) {
            o oVar = new o();
            oVar.b(f.getLatitude());
            oVar.a(f.getLongitude());
            lVar = new l();
            lVar.a((double) f.getAccuracy());
            lVar.a(oVar);
            lVar.a(f.getProvider());
            lVar.a(new Date().getTime() - f.getTime());
        }
        return lVar;
    }

    private static Location f(Context context) {
        Location location;
        Location location2;
        Location location3;
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        try {
            location = locationManager.getLastKnownLocation("network");
        } catch (Exception unused) {
            location = null;
        }
        try {
            location2 = locationManager.getLastKnownLocation(WidgetType.GPS);
        } catch (Exception unused2) {
            location2 = null;
        }
        try {
            location3 = locationManager.getLastKnownLocation("passive");
        } catch (Exception unused3) {
            location3 = null;
        }
        return a(location3, a(location, location2));
    }

    private static void g(Context context) {
        Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
        edit.putLong("last_upload_lbs_data_timestamp", System.currentTimeMillis() / 1000);
        edit.commit();
    }

    public int a() {
        return 11;
    }

    public void run() {
        if (k.e(this.c) && an.a(this.c).a(g.UploadGeoAppLocSwitch.a(), true) && d.d(this.c) && b() && com.xiaomi.channel.commonutils.misc.f.a(this.c, "11", (long) this.d)) {
            a(this.c, false);
        }
    }
}
