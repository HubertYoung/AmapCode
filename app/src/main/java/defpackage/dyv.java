package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.bus.realtimebus.model.BusEndPointData;
import com.autonavi.minimap.route.bus.realtimebus.model.RTBConfigData;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.util.List;

/* renamed from: dyv reason: default package */
/* compiled from: RealTimeBusSpUtil */
public class dyv {
    private static final String a = "dyv";

    public static synchronized void a(RTBConfigData rTBConfigData) {
        synchronized (dyv.class) {
            MapSharePreference mapSharePreference = new MapSharePreference((String) "realtimebus_main_sp");
            if (rTBConfigData != null) {
                mapSharePreference.putStringValue("rtb_theme_src", JSON.toJSONString(rTBConfigData));
            }
        }
    }

    private static synchronized RTBConfigData h() {
        RTBConfigData rTBConfigData;
        synchronized (dyv.class) {
            String stringValue = new MapSharePreference((String) "realtimebus_main_sp").getStringValue("rtb_theme_src", "");
            rTBConfigData = null;
            if (!TextUtils.isEmpty(stringValue)) {
                rTBConfigData = (RTBConfigData) JSON.parseObject(stringValue, RTBConfigData.class);
            }
        }
        return rTBConfigData;
    }

    private static synchronized RTBConfigData d(String str) {
        RTBConfigData rTBConfigData;
        synchronized (dyv.class) {
            rTBConfigData = null;
            if (!TextUtils.isEmpty(str)) {
                rTBConfigData = (RTBConfigData) JSON.parseObject(str, RTBConfigData.class);
            }
        }
        return rTBConfigData;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00ab, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.autonavi.minimap.route.bus.realtimebus.model.RTBConfigData a() {
        /*
            java.lang.Class<dyv> r0 = defpackage.dyv.class
            monitor-enter(r0)
            lt r1 = defpackage.lt.a()     // Catch:{ all -> 0x00ac }
            java.lang.String r2 = "rtb_theme_src"
            org.json.JSONObject r3 = r1.b     // Catch:{ all -> 0x00ac }
            r4 = 0
            if (r3 == 0) goto L_0x0015
            org.json.JSONObject r1 = r1.b     // Catch:{ all -> 0x00ac }
            java.lang.Object r1 = r1.opt(r2)     // Catch:{ all -> 0x00ac }
            goto L_0x0016
        L_0x0015:
            r1 = r4
        L_0x0016:
            if (r1 == 0) goto L_0x00aa
            boolean r2 = r1 instanceof org.json.JSONObject     // Catch:{ all -> 0x00ac }
            if (r2 == 0) goto L_0x00aa
            org.json.JSONObject r1 = (org.json.JSONObject) r1     // Catch:{ all -> 0x00ac }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00ac }
            com.autonavi.minimap.route.bus.realtimebus.model.RTBConfigData r1 = d(r1)     // Catch:{ all -> 0x00ac }
            if (r1 != 0) goto L_0x002a
            r2 = r4
            goto L_0x002e
        L_0x002a:
            java.lang.String r2 = r1.getVersion()     // Catch:{ all -> 0x00ac }
        L_0x002e:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x00ac }
            if (r3 == 0) goto L_0x0036
            monitor-exit(r0)
            return r4
        L_0x0036:
            com.autonavi.minimap.route.bus.realtimebus.model.RTBConfigData r3 = h()     // Catch:{ all -> 0x00ac }
            if (r3 == 0) goto L_0x00a2
            java.lang.String r4 = r3.getVersion()     // Catch:{ all -> 0x00ac }
            boolean r2 = android.text.TextUtils.equals(r2, r4)     // Catch:{ all -> 0x00ac }
            if (r2 != 0) goto L_0x00a0
            java.lang.String r2 = r3.getImgAbsolutePath()     // Catch:{ all -> 0x00ac }
            b(r2)     // Catch:{ all -> 0x00ac }
            com.autonavi.minimap.route.bus.realtimebus.model.RTBZipFileData r2 = r3.getRtbZipFileData()     // Catch:{ all -> 0x00ac }
            if (r2 == 0) goto L_0x0098
            com.autonavi.minimap.route.bus.realtimebus.model.RTBZipFileData r2 = r3.getRtbZipFileData()     // Catch:{ all -> 0x00ac }
            java.util.List r2 = r2.getPaths()     // Catch:{ all -> 0x00ac }
            if (r2 == 0) goto L_0x0098
            int r3 = r2.size()     // Catch:{ all -> 0x00ac }
            if (r3 != 0) goto L_0x0064
            goto L_0x0098
        L_0x0064:
            int r3 = r2.size()     // Catch:{ all -> 0x00ac }
            r4 = 0
            r5 = 0
        L_0x006a:
            if (r4 >= r3) goto L_0x0088
            java.lang.Object r6 = r2.get(r4)     // Catch:{ all -> 0x00ac }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x00ac }
            java.io.File r7 = new java.io.File     // Catch:{ all -> 0x00ac }
            r7.<init>(r6)     // Catch:{ all -> 0x00ac }
            boolean r6 = r7.exists()     // Catch:{ all -> 0x00ac }
            if (r6 == 0) goto L_0x0085
            boolean r6 = r7.delete()     // Catch:{ all -> 0x00ac }
            if (r6 == 0) goto L_0x0085
            int r5 = r5 + 1
        L_0x0085:
            int r4 = r4 + 1
            goto L_0x006a
        L_0x0088:
            java.lang.String r2 = "zyl"
            java.lang.String r3 = "delAllFiles--->"
            java.lang.String r4 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x00ac }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ all -> 0x00ac }
            defpackage.eao.b(r2, r3)     // Catch:{ all -> 0x00ac }
        L_0x0098:
            r1.createPath()     // Catch:{ all -> 0x00ac }
            a(r1)     // Catch:{ all -> 0x00ac }
            monitor-exit(r0)
            return r1
        L_0x00a0:
            monitor-exit(r0)
            return r3
        L_0x00a2:
            r1.createPath()     // Catch:{ all -> 0x00ac }
            a(r1)     // Catch:{ all -> 0x00ac }
            monitor-exit(r0)
            return r1
        L_0x00aa:
            monitor-exit(r0)
            return r4
        L_0x00ac:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dyv.a():com.autonavi.minimap.route.bus.realtimebus.model.RTBConfigData");
    }

    public static synchronized String a(String str, String str2) {
        String str3;
        synchronized (dyv.class) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(str2);
                String sb2 = sb.toString();
                String b = b();
                str3 = null;
                if (!TextUtils.isEmpty(b)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(b);
                    sb3.append(File.separator);
                    sb3.append(sb2);
                    str3 = sb3.toString();
                }
            }
        }
        return str3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0052, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String b() {
        /*
            java.lang.Class<dyv> r0 = defpackage.dyv.class
            monitor-enter(r0)
            java.lang.String r1 = android.os.Environment.getExternalStorageState()     // Catch:{ all -> 0x0053 }
            java.lang.String r2 = "mounted"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0053 }
            r2 = 0
            if (r1 == 0) goto L_0x0051
            java.io.File r1 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ all -> 0x0053 }
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x0053 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0053 }
            java.lang.String r5 = "autonavi"
            r4.<init>(r5)     // Catch:{ all -> 0x0053 }
            java.lang.String r5 = java.io.File.separator     // Catch:{ all -> 0x0053 }
            r4.append(r5)     // Catch:{ all -> 0x0053 }
            java.lang.String r5 = "iconconf"
            r4.append(r5)     // Catch:{ all -> 0x0053 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0053 }
            r3.<init>(r1, r4)     // Catch:{ all -> 0x0053 }
            boolean r1 = r3.exists()     // Catch:{ all -> 0x0053 }
            if (r1 != 0) goto L_0x003c
            boolean r1 = r3.mkdir()     // Catch:{ all -> 0x0053 }
            if (r1 != 0) goto L_0x003c
            monitor-exit(r0)
            return r2
        L_0x003c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0053 }
            r1.<init>()     // Catch:{ all -> 0x0053 }
            java.lang.String r2 = r3.getAbsolutePath()     // Catch:{ all -> 0x0053 }
            r1.append(r2)     // Catch:{ all -> 0x0053 }
            java.lang.String r2 = java.io.File.separator     // Catch:{ all -> 0x0053 }
            r1.append(r2)     // Catch:{ all -> 0x0053 }
            java.lang.String r2 = r1.toString()     // Catch:{ all -> 0x0053 }
        L_0x0051:
            monitor-exit(r0)
            return r2
        L_0x0053:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dyv.b():java.lang.String");
    }

    public static BusEndPointData c() {
        return e("realtimebus_end_point_data");
    }

    private static synchronized BusEndPointData e(String str) {
        BusEndPointData busEndPointData;
        synchronized (dyv.class) {
            busEndPointData = null;
            String stringValue = new MapSharePreference((String) "realtimebus_main_sp").getStringValue(str, "");
            String str2 = a;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(", ");
            sb.append(stringValue);
            eao.b(str2, sb.toString());
            if (!TextUtils.isEmpty(stringValue)) {
                busEndPointData = (BusEndPointData) JSON.parseObject(stringValue, BusEndPointData.class);
                if (!(busEndPointData == null || busEndPointData.getTimeStamp() == 0)) {
                    if (Math.abs(System.currentTimeMillis() - busEndPointData.getTimeStamp()) / 1000 >= 7200) {
                        busEndPointData.setBeyond2Hours(true);
                    } else {
                        busEndPointData.setBeyond2Hours(false);
                    }
                    String str3 = a;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(busEndPointData.isBeyond2Hours());
                    eao.b(str3, sb2.toString());
                }
            }
        }
        return busEndPointData;
    }

    public static void a(BusEndPointData busEndPointData) {
        b(busEndPointData);
    }

    private static synchronized void b(BusEndPointData busEndPointData) {
        synchronized (dyv.class) {
            if (busEndPointData != null) {
                new MapSharePreference((String) "realtimebus_main_sp").putStringValue("realtimebus_end_point_data", JSON.toJSONString(busEndPointData));
            }
        }
    }

    public static POI d() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(com2.a());
            if (b != null) {
                return b.c();
            }
        }
        return null;
    }

    public static POI e() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(com2.a());
            if (b != null) {
                return b.d();
            }
        }
        return null;
    }

    public static boolean a(String str) {
        return !TextUtils.isEmpty(str) && "2".equals(str);
    }

    public static boolean a(List<String> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (new File(list.get(i2)).exists()) {
                i++;
            }
        }
        if (i == size) {
            return true;
        }
        return false;
    }

    public static boolean b(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (file.exists()) {
            z = file.delete();
        }
        eao.b("zyl", "delOneFile--->".concat(String.valueOf(z)));
        return z;
    }

    public static btd f() {
        AMapPageUtil.getAppContext();
        List loadAll = bso.a().a.loadAll();
        if (loadAll != null && loadAll.size() > 0) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            int size = loadAll.size();
            btd btd = null;
            float f = -1.0f;
            for (int i = 0; i < size; i++) {
                btd btd2 = (btd) loadAll.get(i);
                float a2 = cfe.a(latestPosition, new GeoPoint(btd2.station_lon.doubleValue(), btd2.station_lat.doubleValue()));
                if (f == -1.0f || a2 < f) {
                    btd = btd2;
                    f = a2;
                }
            }
            if (btd == null || f >= 1000.0f) {
                return null;
            }
            return btd;
        }
        return null;
    }

    public static NinePatchDrawable a(Bitmap bitmap) {
        if (bitmap != null && NinePatch.isNinePatchChunk(bitmap.getNinePatchChunk())) {
            return new NinePatchDrawable(bitmap, bitmap.getNinePatchChunk(), new Rect(), null);
        }
        return null;
    }

    public static Bitmap c(String str) {
        if (!TextUtils.isEmpty(str)) {
            return BitmapFactory.decodeFile(str);
        }
        return null;
    }

    public static String[] g() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition == null) {
            return null;
        }
        return new String[]{String.valueOf(latestPosition.getLongitude()), String.valueOf(latestPosition.getLatitude())};
    }
}
