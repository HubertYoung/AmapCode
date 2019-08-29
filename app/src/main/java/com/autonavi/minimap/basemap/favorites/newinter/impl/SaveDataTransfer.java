package com.autonavi.minimap.basemap.favorites.newinter.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.KeyValueStorage;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SaveDataTransfer implements con {
    public static final String a = "SaveDataTransfer";
    public String b;
    private boolean c;
    /* access modifiers changed from: private */
    public volatile boolean d;
    /* access modifiers changed from: private */
    public volatile boolean e;
    /* access modifiers changed from: private */
    public WriteLock f;
    /* access modifiers changed from: private */
    public Callback<Boolean> g;

    public interface ICustomAddressStorage extends KeyValueStorage<ICustomAddressStorage> {
        POI getCompanyPoi();

        POI getHomePoi();

        void setCompanyPoi(POI poi);

        void setHomePoi(POI poi);
    }

    public SaveDataTransfer() {
        this.b = null;
        this.c = false;
        this.d = false;
        this.e = false;
        this.f = new ReentrantReadWriteLock().writeLock();
        this.b = cpm.b().a();
    }

    public static SaveDataTransfer c() {
        return (SaveDataTransfer) ank.a(con.class);
    }

    public static POI a(String str, String str2) {
        POI poi;
        byte[] a2 = new lc(str).a();
        if (a2 == null) {
            return null;
        }
        try {
            poi = a(new JSONObject(new String(a2)), str2);
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
            poi = null;
        }
        return poi;
    }

    private static POI a(JSONObject jSONObject, String str) {
        if (str == null) {
            return null;
        }
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            if (jSONObject2 == null) {
                return null;
            }
            POI createPOI = POIFactory.createPOI("", new GeoPoint(agd.a(jSONObject2, "mx"), agd.a(jSONObject2, "my")));
            createPOI.setId(agd.e(jSONObject2, "mId"));
            createPOI.setName(agd.e(jSONObject2, GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME));
            createPOI.setAddr(agd.e(jSONObject2, "mAddr"));
            createPOI.setCityCode(agd.e(jSONObject2, "mCityCode"));
            createPOI.setCityName(agd.e(jSONObject2, "mCityName"));
            createPOI.setEndPoiExtension(agd.e(jSONObject2, "mEndPoiExtension"));
            createPOI.setTransparent(agd.e(jSONObject2, "mTransparent"));
            return createPOI;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final synchronized void a(final Context context) {
        if (!this.c) {
            this.c = true;
            new Thread(new Runnable() {
                public final void run() {
                    System.currentTimeMillis();
                    SaveDataTransfer.a(SaveDataTransfer.this);
                    SaveDataTransfer.this.f.lock();
                    SaveDataTransfer.this.d = true;
                    if (SaveDataTransfer.this.e && SaveDataTransfer.this.d) {
                        SaveDataTransfer.d();
                    }
                    SaveDataTransfer.this.f.unlock();
                    if (SaveDataTransfer.this.g != null) {
                        SaveDataTransfer.this.g.callback(Boolean.TRUE);
                    }
                    SaveDataTransfer.this.g = null;
                }
            }).start();
        }
    }

    public final void b(final Context context) {
        new Thread(new Runnable() {
            public final void run() {
                System.currentTimeMillis();
                SaveDataTransfer.a(SaveDataTransfer.this, context, 2, false);
                SaveDataTransfer.a(SaveDataTransfer.this, context, 4, false);
                SaveDataTransfer.a(SaveDataTransfer.this, context, 8, false);
                SaveDataTransfer.a(SaveDataTransfer.this, context, 1, true);
                SaveDataTransfer.a(SaveDataTransfer.this, context, 16, false);
                SaveDataTransfer.this.f.lock();
                SaveDataTransfer.this.e = true;
                if (SaveDataTransfer.this.d && SaveDataTransfer.this.e) {
                    SaveDataTransfer.d();
                }
                SaveDataTransfer.this.f.unlock();
            }
        }).start();
    }

    public final void a(Callback<Boolean> callback) {
        this.g = callback;
    }

    public final boolean b() {
        return this.d;
    }

    private static String e() {
        String absolutePath = FileUtil.getFilesDir().getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append(absolutePath);
        sb.append(absolutePath);
        return sb.toString();
    }

    private static List<btk> b(String str, String str2) {
        String trim = FileUtil.readData(str).trim();
        List<btk> list = null;
        if (TextUtils.isEmpty(trim)) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            try {
                JSONArray jSONArray = new JSONObject(trim).getJSONArray("items");
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    String e2 = agd.e(jSONArray.getJSONObject(i), "tag");
                    if (!TextUtils.isEmpty(e2)) {
                        btk btk = new btk();
                        cpm.b();
                        btk.a = cpm.a(e2);
                        btk.b = str2;
                        btk.c = "1.0.0";
                        btk.d = e2;
                        arrayList.add(btk);
                    }
                }
                return arrayList;
            } catch (JSONException e3) {
                e = e3;
                list = arrayList;
                e.printStackTrace();
                return list;
            }
        } catch (JSONException e4) {
            e = e4;
            e.printStackTrace();
            return list;
        }
    }

    private static List<btj> c(String str, String str2) {
        String trim = FileUtil.readData(str).trim();
        List<btj> list = null;
        if (TextUtils.isEmpty(trim)) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            try {
                JSONArray jSONArray = new JSONObject(trim).getJSONArray("items");
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (jSONObject != null) {
                        btj btj = new btj();
                        btj.a = str2;
                        btj.c = agd.a(jSONObject, "action_type");
                        btj.d = agd.a(jSONObject, "item_type");
                        String e2 = agd.e(jSONObject, "item_key_id");
                        cpm.b();
                        btj.b = cpm.a(e2, str2);
                        arrayList.add(btj);
                    }
                }
                return arrayList;
            } catch (JSONException e3) {
                e = e3;
                list = arrayList;
                e.printStackTrace();
                return list;
            }
        } catch (JSONException e4) {
            e = e4;
            e.printStackTrace();
            return list;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x018f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<defpackage.bth> d(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            if (r11 == 0) goto L_0x0057
            java.lang.String r2 = r9.b
            boolean r2 = r11.equals(r2)
            if (r2 == 0) goto L_0x0057
            java.lang.String r2 = android.os.Environment.getExternalStorageState()
            java.lang.String r3 = "mounted"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x004b
            java.io.File r2 = android.os.Environment.getExternalStorageDirectory()
            java.io.File r3 = new java.io.File
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r2 = r2.toString()
            r4.append(r2)
            java.lang.String r2 = "/autonavi/favor/"
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            java.lang.String r4 = "saveCookie"
            r3.<init>(r2, r4)
            boolean r2 = r3.exists()
            if (r2 == 0) goto L_0x004b
            java.util.List r2 = defpackage.cpi.b(r3)
            r3.delete()
            goto L_0x004c
        L_0x004b:
            r2 = r1
        L_0x004c:
            if (r2 == 0) goto L_0x0057
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x0057
            r0.addAll(r2)
        L_0x0057:
            boolean r2 = android.text.TextUtils.isEmpty(r10)
            if (r2 != 0) goto L_0x0079
            java.io.File r2 = new java.io.File
            r2.<init>(r10)
            boolean r10 = r2.exists()
            if (r10 != 0) goto L_0x006a
            r10 = r1
            goto L_0x006e
        L_0x006a:
            java.util.List r10 = defpackage.cpi.a(r2)
        L_0x006e:
            if (r10 == 0) goto L_0x0079
            int r2 = r10.size()
            if (r2 <= 0) goto L_0x0079
            r0.addAll(r10)
        L_0x0079:
            int r10 = r0.size()
            if (r10 <= 0) goto L_0x01d9
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            int r10 = r0.size()
            r2 = 0
            r3 = 0
        L_0x008a:
            if (r3 >= r10) goto L_0x01d9
            java.lang.Object r4 = r0.get(r3)
            le r4 = (defpackage.le) r4
            if (r4 == 0) goto L_0x01d5
            com.autonavi.common.model.POI r5 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r6 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r6 = r5.as(r6)
            com.amap.bundle.datamodel.FavoritePOI r6 = (com.amap.bundle.datamodel.FavoritePOI) r6
            com.autonavi.common.model.GeoPoint r7 = new com.autonavi.common.model.GeoPoint
            r7.<init>()
            java.lang.String r8 = "x"
            int r8 = r4.a(r8)
            r7.x = r8
            java.lang.String r8 = "y"
            int r8 = r4.a(r8)
            r7.y = r8
            r5.setPoint(r7)
            android.app.Application r7 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r8 = "poi_version"
            android.content.SharedPreferences r7 = r7.getSharedPreferences(r8, r2)
            java.lang.String r8 = "is_new"
            boolean r7 = r7.getBoolean(r8, r2)
            if (r7 == 0) goto L_0x00f9
            java.lang.String r7 = "name"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setName(r7)
            java.lang.String r7 = "addr"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setAddr(r7)
            java.lang.String r7 = "phone"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setPhone(r7)
            java.lang.String r7 = "custom_name"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r6.setCustomName(r7)
            goto L_0x011a
        L_0x00f9:
            java.lang.String r7 = "name"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setName(r7)
            java.lang.String r7 = "addr"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setAddr(r7)
            java.lang.String r7 = "phone"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setPhone(r7)
        L_0x011a:
            java.lang.String r7 = r5.getAddr()
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x012f
            java.lang.String r7 = "custom_address"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setAddr(r7)
        L_0x012f:
            java.lang.String r7 = "cityCode"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setCityCode(r7)
            java.lang.String r7 = "cityName"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setCityName(r7)
            java.lang.String r7 = "mId"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            r5.setId(r7)
            java.lang.String r7 = "cpdata"
            java.lang.String r8 = ""
            java.lang.String r7 = r4.b(r7, r8)
            defpackage.cpj.a(r7, r5)
            java.lang.String r5 = "customTag"
            java.lang.String r7 = "[]"
            java.lang.String r5 = r4.b(r5, r7)
            if (r5 == 0) goto L_0x018c
            java.lang.String r5 = r5.trim()
            boolean r7 = android.text.TextUtils.isEmpty(r5)
            if (r7 != 0) goto L_0x018c
            java.lang.Object r5 = org.xidea.el.json.JSONDecoder.decode(r5)     // Catch:{ Exception -> 0x0188 }
            java.util.ArrayList r5 = (java.util.ArrayList) r5     // Catch:{ Exception -> 0x0188 }
            if (r5 == 0) goto L_0x018c
            int r7 = r5.size()     // Catch:{ Exception -> 0x0188 }
            if (r7 <= 0) goto L_0x018c
            java.lang.Object r5 = r5.get(r2)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0188 }
            r6.setTag(r5)     // Catch:{ Exception -> 0x0188 }
            r5 = 1
            goto L_0x018d
        L_0x0188:
            r5 = move-exception
            r5.printStackTrace()
        L_0x018c:
            r5 = 0
        L_0x018d:
            if (r5 != 0) goto L_0x01bf
            java.lang.String r5 = "originTag"
            java.lang.String r7 = "[]"
            java.lang.String r4 = r4.b(r5, r7)
            if (r4 == 0) goto L_0x01bf
            java.lang.String r4 = r4.trim()
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x01bf
            java.lang.Object r4 = org.xidea.el.json.JSONDecoder.decode(r4)     // Catch:{ Exception -> 0x01bb }
            java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch:{ Exception -> 0x01bb }
            if (r4 == 0) goto L_0x01bf
            int r5 = r4.size()     // Catch:{ Exception -> 0x01bb }
            if (r5 <= 0) goto L_0x01bf
            java.lang.Object r4 = r4.get(r2)     // Catch:{ Exception -> 0x01bb }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x01bb }
            r6.setTag(r4)     // Catch:{ Exception -> 0x01bb }
            goto L_0x01bf
        L_0x01bb:
            r4 = move-exception
            r4.printStackTrace()
        L_0x01bf:
            bth r4 = new bth
            r4.<init>()
            r4.a(r6)
            r4.b = r11
            defpackage.cpm.b()
            java.lang.String r5 = defpackage.cpm.b(r6)
            r4.a = r5
            r1.add(r4)
        L_0x01d5:
            int r3 = r3 + 1
            goto L_0x008a
        L_0x01d9:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.d(java.lang.String, java.lang.String):java.util.List");
    }

    private static List<bti> e(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        try {
            String trim = FileUtil.readData(str).trim();
            if (!TextUtils.isEmpty(trim)) {
                List<bti> f2 = f(trim, str2);
                if (f2 != null && f2.size() > 0) {
                    arrayList.addAll(f2);
                }
            }
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    private static void a(File file) {
        ahd.a(file);
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x014d A[Catch:{ JSONException -> 0x0180 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<defpackage.bti> f(java.lang.String r11, java.lang.String r12) {
        /*
            r0 = 0
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0182 }
            r1.<init>()     // Catch:{ JSONException -> 0x0182 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0180 }
            r2.<init>(r11)     // Catch:{ JSONException -> 0x0180 }
            java.lang.String r11 = "items"
            org.json.JSONArray r11 = r2.getJSONArray(r11)     // Catch:{ JSONException -> 0x0180 }
            int r2 = r11.length()     // Catch:{ JSONException -> 0x0180 }
            r3 = 0
            r4 = 0
        L_0x0017:
            if (r4 >= r2) goto L_0x0187
            org.json.JSONObject r5 = r11.getJSONObject(r4)     // Catch:{ JSONException -> 0x0180 }
            if (r5 != 0) goto L_0x0022
        L_0x001f:
            r6 = r0
            goto L_0x0119
        L_0x0022:
            com.autonavi.minimap.basemap.favorites.data.RouteItem r6 = new com.autonavi.minimap.basemap.favorites.data.RouteItem     // Catch:{ JSONException -> 0x0180 }
            r6.<init>()     // Catch:{ JSONException -> 0x0180 }
            java.lang.String r7 = "route_type"
            int r7 = defpackage.agd.a(r5, r7)     // Catch:{ JSONException -> 0x0180 }
            r6.routeType = r7     // Catch:{ JSONException -> 0x0180 }
            int r7 = r6.routeType     // Catch:{ JSONException -> 0x0180 }
            switch(r7) {
                case 0: goto L_0x00cf;
                case 1: goto L_0x00a1;
                case 2: goto L_0x0081;
                case 3: goto L_0x005f;
                case 4: goto L_0x0036;
                default: goto L_0x0035;
            }
        L_0x0035:
            goto L_0x001f
        L_0x0036:
            if (r5 == 0) goto L_0x010a
            defpackage.cpk.a(r5, r6)     // Catch:{ Exception -> 0x0059 }
            esb r7 = defpackage.esb.a.a     // Catch:{ Exception -> 0x0059 }
            java.lang.Class<atb> r8 = defpackage.atb.class
            esc r7 = r7.a(r8)     // Catch:{ Exception -> 0x0059 }
            atb r7 = (defpackage.atb) r7     // Catch:{ Exception -> 0x0059 }
            if (r7 == 0) goto L_0x010a
            atd r7 = r7.e()     // Catch:{ Exception -> 0x0059 }
            com.autonavi.common.model.POI r8 = r6.fromPoi     // Catch:{ Exception -> 0x0059 }
            com.autonavi.common.model.POI r9 = r6.toPoi     // Catch:{ Exception -> 0x0059 }
            java.lang.Object r5 = r7.a(r5, r8, r9)     // Catch:{ Exception -> 0x0059 }
            r6.routeData = r5     // Catch:{ Exception -> 0x0059 }
            goto L_0x010a
        L_0x0059:
            r5 = move-exception
            defpackage.kf.a(r5)     // Catch:{ JSONException -> 0x0180 }
            goto L_0x010a
        L_0x005f:
            if (r5 == 0) goto L_0x010a
            defpackage.cpk.a(r5, r6)     // Catch:{ JSONException -> 0x0180 }
            esb r7 = defpackage.esb.a.a     // Catch:{ JSONException -> 0x0180 }
            java.lang.Class<avi> r8 = defpackage.avi.class
            esc r7 = r7.a(r8)     // Catch:{ JSONException -> 0x0180 }
            avi r7 = (defpackage.avi) r7     // Catch:{ JSONException -> 0x0180 }
            if (r7 == 0) goto L_0x010a
            avh r7 = r7.b()     // Catch:{ JSONException -> 0x0180 }
            com.autonavi.common.model.POI r8 = r6.fromPoi     // Catch:{ JSONException -> 0x0180 }
            com.autonavi.common.model.POI r9 = r6.toPoi     // Catch:{ JSONException -> 0x0180 }
            int r10 = r6.routeLength     // Catch:{ JSONException -> 0x0180 }
            r7.a(r5, r8, r9, r10)     // Catch:{ JSONException -> 0x0180 }
            goto L_0x010a
        L_0x0081:
            if (r5 == 0) goto L_0x010a
            defpackage.cpk.a(r5, r6)     // Catch:{ Exception -> 0x009c }
            esb r7 = defpackage.esb.a.a     // Catch:{ Exception -> 0x009c }
            java.lang.Class<atb> r8 = defpackage.atb.class
            esc r7 = r7.a(r8)     // Catch:{ Exception -> 0x009c }
            atb r7 = (defpackage.atb) r7     // Catch:{ Exception -> 0x009c }
            if (r7 == 0) goto L_0x010a
            atd r7 = r7.e()     // Catch:{ Exception -> 0x009c }
            r7.b(r5)     // Catch:{ Exception -> 0x009c }
            goto L_0x010a
        L_0x009c:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ JSONException -> 0x0180 }
            goto L_0x010a
        L_0x00a1:
            if (r5 == 0) goto L_0x010a
            defpackage.cpk.a(r5, r6)     // Catch:{ JSONException -> 0x0180 }
            java.lang.String r7 = r6.method     // Catch:{ JSONException -> 0x0180 }
            if (r7 == 0) goto L_0x00ba
            java.lang.String r7 = r6.method     // Catch:{ JSONException -> 0x0180 }
            int r7 = r7.length()     // Catch:{ JSONException -> 0x0180 }
            if (r7 <= 0) goto L_0x00ba
            java.lang.String r7 = r6.method     // Catch:{ JSONException -> 0x0180 }
            boolean r7 = defpackage.dhw.a(r7)     // Catch:{ JSONException -> 0x0180 }
            if (r7 != 0) goto L_0x00be
        L_0x00ba:
            java.lang.String r7 = "1"
            r6.method = r7     // Catch:{ JSONException -> 0x0180 }
        L_0x00be:
            java.lang.Class<dhz> r7 = defpackage.dhz.class
            java.lang.Object r7 = defpackage.ank.a(r7)     // Catch:{ JSONException -> 0x0180 }
            dhz r7 = (defpackage.dhz) r7     // Catch:{ JSONException -> 0x0180 }
            if (r7 == 0) goto L_0x010a
            java.lang.Object r5 = r7.a(r5, r3)     // Catch:{ JSONException -> 0x0180 }
            r6.routeData = r5     // Catch:{ JSONException -> 0x0180 }
            goto L_0x010a
        L_0x00cf:
            java.lang.String r7 = "id"
            java.lang.String r7 = defpackage.agd.e(r5, r7)     // Catch:{ JSONException -> 0x0180 }
            r6.id = r7     // Catch:{ JSONException -> 0x0180 }
            java.lang.String r7 = "route_name"
            java.lang.String r7 = defpackage.agd.e(r5, r7)     // Catch:{ JSONException -> 0x0180 }
            r6.routeName = r7     // Catch:{ JSONException -> 0x0180 }
            java.lang.String r7 = "route_len"
            int r7 = defpackage.agd.a(r5, r7)     // Catch:{ JSONException -> 0x0180 }
            r6.routeLength = r7     // Catch:{ JSONException -> 0x0180 }
            java.lang.String r7 = "route_alias"
            java.lang.String r7 = defpackage.agd.e(r5, r7)     // Catch:{ JSONException -> 0x0180 }
            r6.routeNote = r7     // Catch:{ JSONException -> 0x0180 }
            esb r7 = defpackage.esb.a.a     // Catch:{ JSONException -> 0x0180 }
            java.lang.Class<asy> r8 = defpackage.asy.class
            esc r7 = r7.a(r8)     // Catch:{ JSONException -> 0x0180 }
            asy r7 = (defpackage.asy) r7     // Catch:{ JSONException -> 0x0180 }
            if (r7 == 0) goto L_0x010a
            asz r7 = r7.c()     // Catch:{ JSONException -> 0x0180 }
            java.lang.Object r5 = r7.b(r5)     // Catch:{ JSONException -> 0x0180 }
            r6.routeData = r5     // Catch:{ JSONException -> 0x0180 }
        L_0x010a:
            java.lang.String r5 = r6.id     // Catch:{ JSONException -> 0x0180 }
            if (r5 == 0) goto L_0x0116
            java.lang.String r5 = r6.id     // Catch:{ JSONException -> 0x0180 }
            int r5 = r5.length()     // Catch:{ JSONException -> 0x0180 }
            if (r5 != 0) goto L_0x0119
        L_0x0116:
            r6.generateKeyId()     // Catch:{ JSONException -> 0x0180 }
        L_0x0119:
            if (r6 == 0) goto L_0x017c
            java.lang.Object r5 = r6.routeData     // Catch:{ JSONException -> 0x0180 }
            if (r5 == 0) goto L_0x017c
            bti r5 = new bti     // Catch:{ JSONException -> 0x0180 }
            r5.<init>()     // Catch:{ JSONException -> 0x0180 }
            int r7 = r6.routeType     // Catch:{ JSONException -> 0x0180 }
            java.lang.Object r8 = r6.routeData     // Catch:{ JSONException -> 0x0180 }
            r5.a(r7, r8)     // Catch:{ JSONException -> 0x0180 }
            int r7 = r6.startX     // Catch:{ JSONException -> 0x0180 }
            r5.d = r7     // Catch:{ JSONException -> 0x0180 }
            int r7 = r6.startY     // Catch:{ JSONException -> 0x0180 }
            r5.e = r7     // Catch:{ JSONException -> 0x0180 }
            int r7 = r6.endX     // Catch:{ JSONException -> 0x0180 }
            r5.f = r7     // Catch:{ JSONException -> 0x0180 }
            int r7 = r6.endY     // Catch:{ JSONException -> 0x0180 }
            r5.g = r7     // Catch:{ JSONException -> 0x0180 }
            com.autonavi.common.model.POI r7 = r6.fromPoi     // Catch:{ JSONException -> 0x0180 }
            r5.a(r7)     // Catch:{ JSONException -> 0x0180 }
            com.autonavi.common.model.POI r7 = r6.toPoi     // Catch:{ JSONException -> 0x0180 }
            r5.b(r7)     // Catch:{ JSONException -> 0x0180 }
            boolean r7 = r6.hasMidPoi     // Catch:{ JSONException -> 0x0180 }
            r5.p = r7     // Catch:{ JSONException -> 0x0180 }
            com.autonavi.common.model.POI r7 = r6.midPoi     // Catch:{ JSONException -> 0x0180 }
            if (r7 == 0) goto L_0x015a
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0180 }
            r7.<init>()     // Catch:{ JSONException -> 0x0180 }
            com.autonavi.common.model.POI r8 = r6.midPoi     // Catch:{ JSONException -> 0x0180 }
            r7.add(r8)     // Catch:{ JSONException -> 0x0180 }
            r5.a(r7)     // Catch:{ JSONException -> 0x0180 }
        L_0x015a:
            java.lang.String r7 = r6.method     // Catch:{ JSONException -> 0x0180 }
            r5.h = r7     // Catch:{ JSONException -> 0x0180 }
            int r7 = r6.routeLength     // Catch:{ JSONException -> 0x0180 }
            r5.l = r7     // Catch:{ JSONException -> 0x0180 }
            int r7 = r6.routeType     // Catch:{ JSONException -> 0x0180 }
            r5.c = r7     // Catch:{ JSONException -> 0x0180 }
            java.lang.String r7 = r6.routeName     // Catch:{ JSONException -> 0x0180 }
            r5.j = r7     // Catch:{ JSONException -> 0x0180 }
            java.lang.String r6 = r6.routeNote     // Catch:{ JSONException -> 0x0180 }
            r5.q = r6     // Catch:{ JSONException -> 0x0180 }
            r5.b = r12     // Catch:{ JSONException -> 0x0180 }
            defpackage.cpm.b()     // Catch:{ JSONException -> 0x0180 }
            java.lang.String r6 = defpackage.cpm.a(r5)     // Catch:{ JSONException -> 0x0180 }
            r5.a = r6     // Catch:{ JSONException -> 0x0180 }
            r1.add(r5)     // Catch:{ JSONException -> 0x0180 }
        L_0x017c:
            int r4 = r4 + 1
            goto L_0x0017
        L_0x0180:
            r11 = move-exception
            goto L_0x0184
        L_0x0182:
            r11 = move-exception
            r1 = r0
        L_0x0184:
            r11.printStackTrace()
        L_0x0187:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer.f(java.lang.String, java.lang.String):java.util.List");
    }

    public final boolean a() {
        return AMapAppGlobal.getApplication().getSharedPreferences("sync_time_file", 0).getBoolean("transfer_old_data_key", false);
    }

    static /* synthetic */ void a(SaveDataTransfer saveDataTransfer) {
        String str;
        if (!TextUtils.isEmpty(saveDataTransfer.b)) {
            String str2 = saveDataTransfer.b;
            ArrayList arrayList = new ArrayList();
            String e2 = e();
            if (e2 != null) {
                if ("public".equals(str2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(FileUtil.getFilesDir().getAbsolutePath());
                    sb.append(File.separator);
                    sb.append("saveCookie");
                    List<bth> d2 = saveDataTransfer.d(sb.toString(), str2);
                    if (d2 != null && d2.size() > 0) {
                        arrayList.addAll(d2);
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(e2);
                    sb2.append(File.separator);
                    sb2.append("saveCookie");
                    str = sb2.toString();
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(e2);
                    sb3.append(File.separator);
                    sb3.append(str2);
                    sb3.append(File.separator);
                    sb3.append("saveCookie");
                    str = sb3.toString();
                }
                List<bth> d3 = saveDataTransfer.d(str, str2);
                if (d3 != null && d3.size() > 0) {
                    arrayList.addAll(d3);
                }
                if (arrayList.size() > 0) {
                    cpf.b(str2);
                    cpf.c((List<bth>) arrayList);
                }
            }
        }
    }

    static /* synthetic */ void d() {
        a(new File(FileUtil.getFilesDir(), "saveCookie"));
        a(new File(e()));
        AMapAppGlobal.getApplication().getSharedPreferences("sync_time_file", 0).edit().putBoolean("transfer_old_data_key", true).apply();
    }

    static /* synthetic */ void a(SaveDataTransfer saveDataTransfer, Context context, int i, boolean z) {
        File[] listFiles;
        SaveDataTransfer saveDataTransfer2 = saveDataTransfer;
        ArrayList arrayList = new ArrayList();
        String e2 = e();
        if (e2 != null && new File(e2).exists()) {
            arrayList.add(e2);
            File file = new File(e2);
            if (file.exists() && file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    if (file2.exists() && file2.isDirectory()) {
                        arrayList.add(file2.getAbsolutePath());
                    }
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        int i2 = 1;
        if (arrayList.size() > 0) {
            String e3 = e();
            String[] strArr = new String[arrayList.size()];
            arrayList.toArray(strArr);
            int length = strArr.length;
            int i3 = 0;
            while (i3 < length) {
                String str = strArr[i3];
                if (!TextUtils.isEmpty(str)) {
                    String str2 = null;
                    if (str.equals(e3)) {
                        str2 = "public";
                    } else {
                        String[] split = str.split(File.separatorChar == '\\' ? "\\\\" : File.separator);
                        if (split != null && split.length > 0) {
                            str2 = split[split.length - i2];
                        }
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        if ((i & 1) > 0) {
                            if (!z || !str2.equals(saveDataTransfer2.b)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(str);
                                sb.append(File.separator);
                                sb.append("saveCookie");
                                List<bth> d2 = saveDataTransfer2.d(sb.toString(), str2);
                                if (d2 != null && d2.size() > 0) {
                                    arrayList2.addAll(d2);
                                }
                                if ("public".equals(str2)) {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(FileUtil.getFilesDir().getAbsolutePath());
                                    sb2.append(File.separator);
                                    sb2.append("saveCookie");
                                    List<bth> d3 = saveDataTransfer2.d(sb2.toString(), str2);
                                    if (d3 != null && d3.size() > 0) {
                                        arrayList2.addAll(d3);
                                    }
                                }
                            }
                        }
                        if ((i & 4) > 0) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str);
                            sb3.append(File.separator);
                            sb3.append("tags_save_cookie");
                            List<btk> b2 = b(sb3.toString(), str2);
                            if (b2 != null && b2.size() > 0) {
                                arrayList4.addAll(b2);
                            }
                        }
                        if ((i & 8) > 0) {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(str);
                            sb4.append(File.separator);
                            sb4.append("synchronization_recoder");
                            List<btj> c2 = c(sb4.toString(), str2);
                            if (c2 != null && c2.size() > 0) {
                                arrayList5.addAll(c2);
                            }
                        }
                        if ((i & 2) > 0) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str);
                            sb5.append(File.separator);
                            sb5.append("route_save_cookie");
                            List<bti> e4 = e(sb5.toString(), str2);
                            if (e4.size() > 0) {
                                arrayList3.addAll(e4);
                            }
                        }
                        if ((i & 16) > 0) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str);
                            sb6.append(File.separator);
                            sb6.append("synchronization_timer");
                            String sb7 = sb6.toString();
                            SharedPreferences sharedPreferences = context.getSharedPreferences("sync_time_file", 0);
                            String readData = FileUtil.readData(sb7);
                            if (!TextUtils.isEmpty(readData)) {
                                Editor edit = sharedPreferences.edit();
                                cpm.b();
                                edit.putString(cpm.b(str2), readData).apply();
                            }
                            i3++;
                            i2 = 1;
                        }
                    }
                }
                Context context2 = context;
                i3++;
                i2 = 1;
            }
        } else if ((i & 1) > 0) {
            StringBuilder sb8 = new StringBuilder();
            sb8.append(FileUtil.getFilesDir().getAbsolutePath());
            sb8.append(File.separator);
            sb8.append("saveCookie");
            List<bth> d4 = saveDataTransfer2.d(sb8.toString(), "public");
            if (d4 != null && d4.size() > 0) {
                arrayList2.addAll(d4);
            }
        }
        if (arrayList2.size() > 0) {
            cpf.b(cpm.b().a());
            cpf.c((List<bth>) arrayList2);
        }
        if (arrayList4.size() > 0) {
            bsv.a().a.insertOrReplaceInTx((Iterable<T>) arrayList4);
        }
        if (arrayList5.size() > 0) {
            bsu.a().a.insertOrReplaceInTx((Iterable<T>) arrayList5);
        }
    }
}
