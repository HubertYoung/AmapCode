package com.autonavi.minimap.basemap.common.inter.impl;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.Callback;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.mapinterface.IMapRequestManager;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.auth.param.UserDeviceRequest;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.geo.GeoRequestHolder;
import com.autonavi.minimap.geo.param.ReverseCodeRequest;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.CodePointRequest;
import com.autonavi.minimap.poi.param.DetailMpsRequest;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public class MapRequestManagerImpl implements IMapRequestManager {
    MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private final String b = "LAST_TIME_DEVICE_INFO_COLLECT";
    private b c = new b();
    private final boolean d = false;

    abstract class a<T> {
        protected UserDeviceRequest b;

        /* access modifiers changed from: 0000 */
        public abstract T a();

        /* access modifiers changed from: 0000 */
        public abstract void a(String str);

        public a(UserDeviceRequest userDeviceRequest) {
            this.b = userDeviceRequest;
        }

        public final boolean b() {
            return this.b != null;
        }
    }

    public class b {
        public int a = 1440;
        public boolean b = false;

        public b() {
        }

        public final boolean a() {
            String a2 = lo.a().a((String) "amap_basemap_config");
            if (!TextUtils.isEmpty(a2)) {
                try {
                    JSONObject optJSONObject = new JSONObject(a2).optJSONObject("device_info_collect");
                    if (optJSONObject != null) {
                        this.a = optJSONObject.optInt("interval_minutes", 1440);
                        boolean z = false;
                        if (optJSONObject.optInt("alc_log_switch", 0) == 1) {
                            z = true;
                        }
                        this.b = z;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    public MapRequestManagerImpl() {
        this.c.a();
    }

    public AosRequest xyPoi(String str, GeoPoint geoPoint, Callback<List<POI>> callback) {
        DetailMpsRequest detailMpsRequest = new DetailMpsRequest();
        detailMpsRequest.k = str;
        detailMpsRequest.l = String.valueOf(geoPoint.getLongitude());
        detailMpsRequest.m = String.valueOf(geoPoint.getLatitude());
        detailMpsRequest.n = "2.16";
        detailMpsRequest.c = "0";
        detailMpsRequest.d = "true";
        detailMpsRequest.f = "301000";
        detailMpsRequest.o = "h";
        detailMpsRequest.e = "5";
        detailMpsRequest.h = AppManager.getInstance().getUserLocInfo();
        a(callback, detailMpsRequest);
        return detailMpsRequest;
    }

    public AosRequest idPoi(String str, long j, int i, Callback<List<POI>> callback) {
        return idPoi(str, null, j, i, null, callback);
    }

    public AosRequest idPoi(String str, GeoPoint geoPoint, long j, int i, SuperId superId, Callback<List<POI>> callback) {
        if (TextUtils.isEmpty(str) || callback == null) {
            return null;
        }
        DetailMpsRequest detailMpsRequest = new DetailMpsRequest();
        detailMpsRequest.b = str;
        detailMpsRequest.n = "2.16";
        detailMpsRequest.c = String.valueOf(i);
        detailMpsRequest.d = "true";
        detailMpsRequest.f = "301000";
        detailMpsRequest.e = "5";
        detailMpsRequest.h = AppManager.getInstance().getUserLocInfo();
        detailMpsRequest.i = String.valueOf(j);
        if (geoPoint != null) {
            detailMpsRequest.l = String.valueOf(geoPoint.getLongitude());
            detailMpsRequest.m = String.valueOf(geoPoint.getLatitude());
        }
        if (superId != null) {
            detailMpsRequest.o = superId.getScenceId();
        } else {
            detailMpsRequest.o = "h";
        }
        a(callback, detailMpsRequest);
        return detailMpsRequest;
    }

    private void a(final Callback<List<POI>> callback, DetailMpsRequest detailMpsRequest) {
        PoiRequestHolder.getInstance().sendDetailMps(detailMpsRequest, new AosResponseCallback<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                if (callback != null) {
                    try {
                        JSONObject aosByteResponseToJSONObject = AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
                        new cog();
                        final coh a2 = cog.a(aosByteResponseToJSONObject);
                        if (a2 != null) {
                            aho.a(new Runnable() {
                                public final void run() {
                                    callback.callback(a2.a);
                                }
                            });
                            return;
                        }
                        aho.a(new Runnable() {
                            public final void run() {
                                callback.error(new Throwable("result is null!"), false);
                            }
                        });
                    } catch (JSONException e) {
                        aho.a(new Runnable() {
                            public final void run() {
                                callback.error(e, true);
                            }
                        });
                    }
                }
            }

            public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
                if (callback != null) {
                    aho.a(new Runnable() {
                        public final void run() {
                            callback.error(aosResponseException, false);
                        }
                    });
                }
            }
        });
    }

    public AosRequest poiMark(String str, int i, String str2, String str3, String str4, final Callback<com.autonavi.map.mapinterface.IMapRequestManager.a> callback) {
        CodePointRequest codePointRequest = new CodePointRequest();
        codePointRequest.b = str;
        if (TextUtils.isEmpty(str3)) {
            codePointRequest.f = str2;
        } else {
            codePointRequest.c = str3;
        }
        codePointRequest.e = i == 1 ? "brand" : Constants.ANIMATOR_NONE;
        codePointRequest.d = str4;
        PoiRequestHolder.getInstance().sendCodePoint(codePointRequest, new AosResponseCallback<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                if (callback != null) {
                    try {
                        JSONObject aosByteResponseToJSONObject = AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
                        new com.autonavi.map.mapinterface.IMapRequestManager.b();
                        final com.autonavi.map.mapinterface.IMapRequestManager.a a2 = com.autonavi.map.mapinterface.IMapRequestManager.b.a(aosByteResponseToJSONObject);
                        aho.a(new Runnable() {
                            public final void run() {
                                callback.callback(a2);
                            }
                        });
                    } catch (JSONException e) {
                        aho.a(new Runnable() {
                            public final void run() {
                                callback.error(e, true);
                            }
                        });
                    }
                }
            }

            public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
                if (callback != null) {
                    aho.a(new Runnable() {
                        public final void run() {
                            callback.error(aosResponseException, false);
                        }
                    });
                }
            }
        });
        return codePointRequest;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0093, code lost:
        if (java.lang.Math.abs(java.lang.System.currentTimeMillis() - r6) <= ((long) ((r4.c.a * 60) * 1000))) goto L_0x0096;
     */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.amap.bundle.aosservice.request.AosRequest authDevice(java.lang.String r5, java.lang.String r6, int r7, int r8, final com.autonavi.common.Callback<com.autonavi.map.mapinterface.IMapRequestManager.d> r9) {
        /*
            r4 = this;
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r0 = com.amap.bundle.network.request.param.NetworkParam.getDeviceToken(r0)
            com.autonavi.minimap.auth.param.UserDeviceRequest r1 = new com.autonavi.minimap.auth.param.UserDeviceRequest
            r1.<init>()
            r1.c = r0
            java.lang.String r7 = java.lang.String.valueOf(r7)
            r1.h = r7
            java.lang.String r7 = java.lang.String.valueOf(r8)
            r1.i = r7
            r1.e = r6
            r1.d = r5
            esb r5 = defpackage.esb.a.a
            java.lang.Class<fhb> r6 = defpackage.fhb.class
            esc r5 = r5.a(r6)
            fhb r5 = (defpackage.fhb) r5
            if (r5 == 0) goto L_0x003a
            boolean r5 = r5.e()
            if (r5 == 0) goto L_0x0036
            java.lang.String r5 = "1"
            goto L_0x0038
        L_0x0036:
            java.lang.String r5 = "0"
        L_0x0038:
            r1.k = r5
        L_0x003a:
            java.lang.String r5 = a()
            r1.l = r5
            java.lang.String r5 = com.amap.bundle.network.request.param.NetworkParam.getdai()
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x004c
            r1.m = r5
        L_0x004c:
            java.lang.String r5 = com.amap.bundle.network.request.param.NetworkParam.getdsn()
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x0058
            r1.n = r5
        L_0x0058:
            java.lang.String r5 = com.amap.bundle.network.request.param.NetworkParam.getdcs()
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x0064
            r1.o = r5
        L_0x0064:
            r5 = 0
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$b r6 = r4.c
            int r6 = r6.a
            if (r6 == 0) goto L_0x0095
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$b r6 = r4.c
            int r6 = r6.a
            if (r6 <= 0) goto L_0x0096
            com.amap.bundle.mapstorage.MapSharePreference r6 = r4.a
            java.lang.String r7 = "LAST_TIME_DEVICE_INFO_COLLECT"
            r2 = 0
            long r6 = r6.getLongValue(r7, r2)
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 == 0) goto L_0x0095
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 - r6
            long r6 = java.lang.Math.abs(r2)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$b r8 = r4.c
            int r8 = r8.a
            int r8 = r8 * 60
            int r8 = r8 * 1000
            long r2 = (long) r8
            int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r6 <= 0) goto L_0x0096
        L_0x0095:
            r5 = 1
        L_0x0096:
            if (r5 == 0) goto L_0x009b
            java.lang.String r6 = "1"
            goto L_0x009d
        L_0x009b:
            java.lang.String r6 = "0"
        L_0x009d:
            r1.p = r6
            if (r5 == 0) goto L_0x0142
            com.amap.bundle.mapstorage.MapSharePreference r5 = r4.a
            java.lang.String r6 = "LAST_TIME_DEVICE_INFO_COLLECT"
            long r7 = java.lang.System.currentTimeMillis()
            r5.putLongValue(r6, r7)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$16 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$16
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$17 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$17
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$18 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$18
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$19 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$19
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$20 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$20
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$5 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$5
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$6 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$6
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$7 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$7
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$8 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$8
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$9 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$9
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$10 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$10
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$11 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$11
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$12 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$12
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$13 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$13
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$14 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$14
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$15 r5 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$15
            r5.<init>(r1)
            a(r5)
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$b r5 = r4.c
            boolean r5 = r5.b
            if (r5 == 0) goto L_0x0142
            java.lang.String r5 = com.alibaba.fastjson.JSON.toJSONString(r1)     // Catch:{ Exception -> 0x013e }
            java.lang.String r6 = "P0024"
            java.lang.String r7 = "E001"
            defpackage.coe.a(r6, r7, r5)     // Catch:{ Exception -> 0x013e }
            goto L_0x0142
        L_0x013e:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0142:
            com.autonavi.minimap.auth.AuthRequestHolder r5 = com.autonavi.minimap.auth.AuthRequestHolder.getInstance()
            com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$3 r6 = new com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl$3
            r6.<init>(r9)
            r5.sendUserDevice(r1, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl.authDevice(java.lang.String, java.lang.String, int, int, com.autonavi.common.Callback):com.amap.bundle.aosservice.request.AosRequest");
    }

    public AosRequest getReverseGeocodeResult(GeoPoint geoPoint, final Callback<awg> callback) {
        ReverseCodeRequest reverseCodeRequest = new ReverseCodeRequest();
        reverseCodeRequest.g = 5;
        reverseCodeRequest.h = 1;
        reverseCodeRequest.d = true;
        reverseCodeRequest.f = 1;
        reverseCodeRequest.i = 1;
        if (geoPoint != null) {
            DPoint a2 = cfg.a((long) geoPoint.x, (long) geoPoint.y);
            reverseCodeRequest.c = String.valueOf(a2.y);
            reverseCodeRequest.b = String.valueOf(a2.x);
        }
        GeoRequestHolder.getInstance().sendReverseCode(reverseCodeRequest, new AosResponseCallback<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                if (callback != null) {
                    try {
                        JSONObject aosByteResponseToJSONObject = AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
                        new awf();
                        final awg a2 = awf.a(aosByteResponseToJSONObject);
                        aho.a(new Runnable() {
                            public final void run() {
                                callback.callback(a2);
                            }
                        });
                    } catch (JSONException e) {
                        aho.a(new Runnable() {
                            public final void run() {
                                callback.error(e, true);
                            }
                        });
                    }
                }
            }

            public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
                if (callback != null) {
                    aho.a(new Runnable() {
                        public final void run() {
                            callback.error(aosResponseException, false);
                        }
                    });
                }
            }
        });
        return reverseCodeRequest;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x004e */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0052 A[SYNTHETIC, Splitter:B:31:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0058 A[SYNTHETIC, Splitter:B:36:0x0058] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a() {
        /*
            r0 = 0
            java.util.Properties r1 = new java.util.Properties     // Catch:{ Throwable -> 0x004e }
            r1.<init>()     // Catch:{ Throwable -> 0x004e }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x004e }
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x004e }
            java.io.File r4 = android.os.Environment.getRootDirectory()     // Catch:{ Throwable -> 0x004e }
            java.lang.String r5 = "build.prop"
            r3.<init>(r4, r5)     // Catch:{ Throwable -> 0x004e }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x004e }
            r1.load(r2)     // Catch:{ Throwable -> 0x0048, all -> 0x0046 }
            java.lang.String r3 = "ro.miui.ui.version.code"
            java.lang.String r3 = r1.getProperty(r3, r0)     // Catch:{ Throwable -> 0x0048, all -> 0x0046 }
            if (r3 != 0) goto L_0x003b
            java.lang.String r3 = "ro.miui.ui.version.name"
            java.lang.String r3 = r1.getProperty(r3, r0)     // Catch:{ Throwable -> 0x0048, all -> 0x0046 }
            if (r3 != 0) goto L_0x003b
            java.lang.String r3 = "ro.miui.internal.storage"
            java.lang.String r3 = r1.getProperty(r3, r0)     // Catch:{ Throwable -> 0x0048, all -> 0x0046 }
            if (r3 == 0) goto L_0x0035
            goto L_0x003b
        L_0x0035:
            java.lang.String r0 = android.os.Build.DISPLAY     // Catch:{ Throwable -> 0x0048, all -> 0x0046 }
            r2.close()     // Catch:{ Throwable -> 0x003a }
        L_0x003a:
            return r0
        L_0x003b:
            java.lang.String r3 = "ro.build.version.incremental"
            java.lang.String r0 = r1.getProperty(r3, r0)     // Catch:{ Throwable -> 0x0048, all -> 0x0046 }
            r2.close()     // Catch:{ Throwable -> 0x0045 }
        L_0x0045:
            return r0
        L_0x0046:
            r0 = move-exception
            goto L_0x0056
        L_0x0048:
            r0 = r2
            goto L_0x004e
        L_0x004a:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L_0x0056
        L_0x004e:
            java.lang.String r1 = android.os.Build.DISPLAY     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x0055
            r0.close()     // Catch:{ Throwable -> 0x0055 }
        L_0x0055:
            return r1
        L_0x0056:
            if (r2 == 0) goto L_0x005b
            r2.close()     // Catch:{ Throwable -> 0x005b }
        L_0x005b:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl.a():java.lang.String");
    }

    private static void a(a aVar) {
        boolean z;
        String str = "";
        try {
            Object a2 = aVar.a();
            if (a2 != null) {
                str = String.valueOf(a2);
            }
            z = false;
        } catch (Exception e) {
            e.printStackTrace();
            z = true;
        }
        if (z) {
            str = "err";
        }
        if (!TextUtils.isEmpty(str) && aVar.b()) {
            aVar.a(str);
        }
    }
}
