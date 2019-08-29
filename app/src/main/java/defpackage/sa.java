package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.voice.VoiceDriveUtils$2;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback.a;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.widget.ConfirmDlgLifeCircle;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: sa reason: default package */
/* compiled from: VoiceDriveUtils */
public final class sa {
    private static ArrayList<Integer> a = new ArrayList<>();
    private static ArrayList<Integer> b = new ArrayList<>();
    private static a c;

    public static boolean a(double d, double d2) {
        return -180.0d <= d && d <= 180.0d && -90.0d <= d2 && d2 <= 90.0d;
    }

    public static boolean b(double d, double d2) {
        return d == -1000.0d || d2 == -1000.0d;
    }

    public static boolean d(int i) {
        return i != -1;
    }

    public static void a(POI poi, POI poi2, List<POI> list, RouteType routeType, String str, int i) {
        DriveUtil.putLastRoutingChoice(str);
        try {
            apr apr = (apr) a.a.a(apr.class);
            if (apr != null) {
                apr.b(AMapPageUtil.getPageContext());
            }
            ConfirmDlgLifeCircle.removeAll();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            AMapLog.e("IntentController", sb.toString());
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean("bundle_key_auto_route", true);
        if (poi != null) {
            pageBundle.putObject("bundle_key_poi_start", poi);
        }
        if (list != null) {
            pageBundle.putObject("bundle_key_poi_mids", list);
        }
        pageBundle.putObject("bundle_key_poi_end", poi2);
        if (routeType != null) {
            pageBundle.putObject("bundle_key_route_type", routeType);
        } else {
            pageBundle.putObject("bundle_key_route_type", RouteType.CAR);
        }
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("bundle_key_method", str);
        }
        pageBundle.putInt("bundle_key_voice_tokenId", i);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage((String) "amap.extra.route.route", pageBundle);
        }
    }

    public static void a(POI poi, ArrayList<POI> arrayList, POI poi2, String str, int i) {
        int d = dhw.d(str);
        int c2 = dhw.c(str);
        ms msVar = (ms) a.a.a(ms.class);
        if (msVar != null) {
            msVar.a(AMapAppGlobal.getTopActivity(), poi, arrayList, poi2, d, c2, i, str);
        }
    }

    public static boolean a(POI poi, String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            if (!TextUtils.isEmpty(str)) {
                poi.setName(str);
            } else {
                poi.setName("地图选定位置");
            }
            Point a2 = cfg.a(Double.valueOf(str2).doubleValue(), Double.valueOf(str3).doubleValue());
            poi.setPoint(new GeoPoint(a2.x, a2.y));
        } else if (TextUtils.isEmpty(str) || "我的位置".equals(str)) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            poi.setName("我的位置");
            poi.setPoint(latestPosition);
        } else {
            poi.setName(str);
            return true;
        }
        return false;
    }

    public static void b(POI poi, String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            Point a2 = cfg.a(Double.valueOf(str2).doubleValue(), Double.valueOf(str3).doubleValue());
            poi.setPoint(new GeoPoint(a2.x, a2.y));
            if (TextUtils.isEmpty(str)) {
                poi.setName("地图选定位置");
            }
        }
        if (!TextUtils.isEmpty(str)) {
            poi.setName(str);
            if ("我的位置".equals(str)) {
                poi.setPoint(LocationInstrument.getInstance().getLatestPosition());
            }
        }
    }

    public static void c(POI poi, String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            Point a2 = cfg.a(Double.valueOf(str2).doubleValue(), Double.valueOf(str3).doubleValue());
            poi.setPoint(new GeoPoint(a2.x, a2.y));
            if (TextUtils.isEmpty(str)) {
                poi.setName("地图选定位置");
            }
        }
        if (!TextUtils.isEmpty(str)) {
            poi.setName(str);
            if ("我的位置".equals(str)) {
                poi.setPoint(LocationInstrument.getInstance().getLatestPosition());
            }
        }
    }

    public static POI a() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b2 = com2.b(com2.a());
            if (b2 != null) {
                return b2.c();
            }
        }
        return null;
    }

    public static POI b() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b2 = com2.b(com2.a());
            if (b2 != null) {
                return b2.d();
            }
        }
        return null;
    }

    static {
        a.add(Integer.valueOf(-1));
        a.add(Integer.valueOf(1));
        a.add(Integer.valueOf(2));
        a.add(Integer.valueOf(4));
        a.add(Integer.valueOf(6));
        a.add(Integer.valueOf(8));
        a.add(Integer.valueOf(10));
        a.add(Integer.valueOf(12));
        a.add(Integer.valueOf(14));
        a.add(Integer.valueOf(16));
        a.add(Integer.valueOf(9));
        a.add(Integer.valueOf(0));
        b.add(Integer.valueOf(-1));
        b.add(Integer.valueOf(1));
        b.add(Integer.valueOf(2));
        b.add(Integer.valueOf(4));
        b.add(Integer.valueOf(6));
        b.add(Integer.valueOf(8));
        b.add(Integer.valueOf(10));
        b.add(Integer.valueOf(12));
        b.add(Integer.valueOf(14));
        b.add(Integer.valueOf(16));
        b.add(Integer.valueOf(9));
        b.add(Integer.valueOf(0));
    }

    public static boolean a(int i) {
        return a.contains(Integer.valueOf(i));
    }

    public static boolean b(int i) {
        return b.contains(Integer.valueOf(i));
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String c(int r5) {
        /*
            boolean r0 = b(r5)
            if (r0 != 0) goto L_0x0009
            java.lang.String r5 = ""
            return r5
        L_0x0009:
            r0 = -1
            if (r5 != r0) goto L_0x000f
            java.lang.String r5 = ""
            return r5
        L_0x000f:
            r0 = 8
            r1 = 0
            r2 = 1
            if (r5 != r0) goto L_0x001a
            r5 = 0
        L_0x0016:
            r0 = 0
        L_0x0017:
            r1 = 1
        L_0x0018:
            r2 = 0
            goto L_0x004d
        L_0x001a:
            r0 = 4
            if (r5 != r0) goto L_0x0020
            r5 = 0
        L_0x001e:
            r0 = 0
            goto L_0x004d
        L_0x0020:
            r0 = 2
            if (r5 != r0) goto L_0x0026
            r5 = 1
        L_0x0024:
            r0 = 0
            goto L_0x0018
        L_0x0026:
            if (r5 != r2) goto L_0x002b
            r5 = 0
            r0 = 1
            goto L_0x0018
        L_0x002b:
            r0 = 12
            if (r5 != r0) goto L_0x0033
            r5 = 0
        L_0x0030:
            r0 = 0
            r1 = 1
            goto L_0x004d
        L_0x0033:
            r0 = 10
            if (r5 != r0) goto L_0x0039
            r5 = 1
            goto L_0x0016
        L_0x0039:
            r0 = 6
            if (r5 != r0) goto L_0x003e
            r5 = 1
            goto L_0x001e
        L_0x003e:
            r0 = 14
            if (r5 != r0) goto L_0x0044
            r5 = 1
            goto L_0x0030
        L_0x0044:
            r0 = 9
            if (r5 != r0) goto L_0x004b
            r5 = 0
            r0 = 1
            goto L_0x0017
        L_0x004b:
            r5 = 0
            goto L_0x0024
        L_0x004d:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = ""
            if (r1 == 0) goto L_0x005d
            java.lang.String r1 = "2"
            r3.append(r1)
            java.lang.String r4 = "|"
        L_0x005d:
            if (r2 == 0) goto L_0x0069
            r3.append(r4)
            java.lang.String r1 = "4"
            r3.append(r1)
            java.lang.String r4 = "|"
        L_0x0069:
            if (r5 == 0) goto L_0x0075
            r3.append(r4)
            java.lang.String r5 = "8"
            r3.append(r5)
            java.lang.String r4 = "|"
        L_0x0075:
            if (r0 == 0) goto L_0x007f
            r3.append(r4)
            java.lang.String r5 = "16"
            r3.append(r5)
        L_0x007f:
            int r5 = r3.length()
            if (r5 != 0) goto L_0x0088
            java.lang.String r5 = "1"
            return r5
        L_0x0088:
            java.lang.String r5 = r3.toString()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.sa.c(int):java.lang.String");
    }

    public static void a(int i, String str) {
        if (PlaySoundUtils.getInstance().isSilent()) {
            a(i, (int) UCMPackageInfo.initUCMBuildInfo);
            return;
        }
        AudioManager audioManager = (AudioManager) AMapAppGlobal.getApplication().getSystemService("audio");
        int streamMaxVolume = audioManager.getStreamMaxVolume(3);
        int streamVolume = audioManager.getStreamVolume(3);
        try {
            int optInt = new JSONObject(str).optInt("type", -1);
            if (optInt == 0) {
                if (streamVolume < streamMaxVolume) {
                    tv.a((Context) AMapAppGlobal.getApplication()).a(true);
                    e(i);
                    return;
                }
                a(i, (int) UCMPackageInfo.deleteTempDecFiles);
            } else if (optInt != 1) {
                a(i, 10001);
            } else if (streamVolume > 1) {
                tv.a((Context) AMapAppGlobal.getApplication()).b(true);
                e(i);
            } else {
                a(i, (int) UCMPackageInfo.getVersion);
            }
        } catch (JSONException unused) {
            a(i, (int) SDKFactory.getCoreType);
        }
    }

    public static JSONArray a(final RouteType routeType, final int i, final ry ryVar) {
        ahl.a(new a<JSONArray>() {
            public final /* synthetic */ void onFinished(Object obj) {
                JSONArray jSONArray = (JSONArray) obj;
                if (ryVar != null) {
                    ryVar.a(jSONArray);
                }
            }

            public final void onError(Throwable th) {
                if (ryVar != null) {
                    ryVar.a(new JSONArray());
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                List<chk> a2 = chk.a(routeType);
                if (a2.size() <= 0) {
                    return new JSONArray();
                }
                JSONArray jSONArray = new JSONArray();
                int i = 0;
                while (i < a2.size() && i != i) {
                    jSONArray.put(sa.b(a2.get(i)));
                    i++;
                }
                return jSONArray;
            }
        }, ahn.b());
        return new JSONArray();
    }

    /* access modifiers changed from: private */
    public static JSONObject b(chk chk) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("start_poi", a(chk.d()));
            jSONObject.put("end_poi", a(chk.e()));
            ArrayList<POI> f = chk.f();
            if (f != null && f.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < f.size(); i++) {
                    jSONArray.put(a(f.get(i)));
                }
                jSONObject.put("middle_pois", jSONArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private static JSONObject a(POI poi) {
        if (poi == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId());
            jSONObject.put("name", poi.getName());
            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, poi.getPoint().getLongitude());
            jSONObject.put("lat", poi.getPoint().getLatitude());
            ArrayList<GeoPoint> entranceList = poi.getEntranceList();
            if (entranceList != null && !entranceList.isEmpty()) {
                JSONArray jSONArray = new JSONArray();
                for (GeoPoint next : entranceList) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(DictionaryKeys.CTRLXY_X, next.x);
                    jSONObject2.put(DictionaryKeys.CTRLXY_Y, next.y);
                    jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, next.getLongitude());
                    jSONObject2.put("lat", next.getLatitude());
                    jSONArray.put(jSONObject2);
                }
                jSONObject.put("entranceList", jSONArray);
            }
            return jSONObject;
        } catch (JSONException e) {
            AMapLog.e("poi", String.valueOf(e));
            return null;
        }
    }

    public static int a(String str) {
        JSONObject jSONObject;
        if (!TextUtils.isEmpty(str)) {
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return a(jSONObject);
        }
        jSONObject = null;
        return a(jSONObject);
    }

    public static int a(JSONObject jSONObject) {
        int i;
        if (jSONObject == null) {
            return -1;
        }
        if (jSONObject.has("routePlanStartNaviToken")) {
            try {
                i = jSONObject.getInt("routePlanStartNaviToken");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return i;
        }
        i = -1;
        return i;
    }

    public static void a(rz rzVar) {
        if (c != null) {
            c.cancel();
        }
        c = bib.a(new VoiceDriveUtils$2(rzVar), 6000);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x005b A[SYNTHETIC, Splitter:B:18:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x011b  */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(int r17, java.lang.String r18, defpackage.asv r19) {
        /*
            java.lang.String r0 = "1"
            r1 = 3
            java.lang.String[] r2 = new java.lang.String[r1]
            java.lang.String[] r3 = new java.lang.String[r1]
            int[] r1 = new int[r1]
            r5 = 0
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0090 }
            r7 = r18
            r6.<init>(r7)     // Catch:{ JSONException -> 0x0090 }
            java.lang.String r7 = "startPoi"
            org.json.JSONObject r7 = r6.optJSONObject(r7)     // Catch:{ JSONException -> 0x0090 }
            java.lang.String r8 = "endPoi"
            org.json.JSONObject r8 = r6.optJSONObject(r8)     // Catch:{ JSONException -> 0x0090 }
            java.lang.String r9 = "midPoi"
            org.json.JSONArray r6 = r6.optJSONArray(r9)     // Catch:{ JSONException -> 0x0090 }
            r9 = 4
            if (r7 == 0) goto L_0x003b
            java.lang.String r10 = "poiType"
            int r10 = r7.optInt(r10)     // Catch:{ JSONException -> 0x0090 }
            java.lang.String r11 = "poiName"
            java.lang.String r12 = ""
            java.lang.String r7 = r7.optString(r11, r12)     // Catch:{ JSONException -> 0x0090 }
            if (r10 != r9) goto L_0x003b
            java.lang.String r10 = "选择起点"
            goto L_0x003d
        L_0x0039:
            r8 = r5
            goto L_0x0092
        L_0x003b:
            r7 = r5
            r10 = r7
        L_0x003d:
            if (r8 == 0) goto L_0x0057
            java.lang.String r11 = "poiType"
            int r11 = r8.optInt(r11)     // Catch:{ JSONException -> 0x0054 }
            java.lang.String r12 = "poiName"
            java.lang.String r13 = ""
            java.lang.String r8 = r8.optString(r12, r13)     // Catch:{ JSONException -> 0x0054 }
            if (r11 != r9) goto L_0x0057
            java.lang.String r11 = "选择终点"
            goto L_0x0059
        L_0x0052:
            r11 = r5
            goto L_0x0094
        L_0x0054:
            r8 = r5
            r11 = r8
            goto L_0x0094
        L_0x0057:
            r8 = r5
            r11 = r8
        L_0x0059:
            if (r6 == 0) goto L_0x0094
            int r12 = r6.length()     // Catch:{ JSONException -> 0x0094 }
            if (r12 <= 0) goto L_0x0094
            r12 = 0
        L_0x0062:
            int r13 = r6.length()     // Catch:{ JSONException -> 0x0094 }
            if (r12 >= r13) goto L_0x0094
            org.json.JSONObject r13 = r6.optJSONObject(r12)     // Catch:{ JSONException -> 0x0094 }
            if (r13 == 0) goto L_0x008d
            java.lang.String r14 = "poiType"
            int r14 = r13.optInt(r14)     // Catch:{ JSONException -> 0x0094 }
            java.lang.String r15 = "poiName"
            java.lang.String r4 = ""
            java.lang.String r4 = r13.optString(r15, r4)     // Catch:{ JSONException -> 0x0094 }
            if (r14 != r9) goto L_0x0087
            r2[r12] = r4     // Catch:{ JSONException -> 0x0094 }
            java.lang.String r4 = "选择途经点"
            r3[r12] = r4     // Catch:{ JSONException -> 0x0094 }
            r1[r12] = r12     // Catch:{ JSONException -> 0x0094 }
            goto L_0x008d
        L_0x0087:
            r2[r12] = r5     // Catch:{ JSONException -> 0x0094 }
            r3[r12] = r5     // Catch:{ JSONException -> 0x0094 }
            r1[r12] = r12     // Catch:{ JSONException -> 0x0094 }
        L_0x008d:
            int r12 = r12 + 1
            goto L_0x0062
        L_0x0090:
            r7 = r5
            r8 = r7
        L_0x0092:
            r10 = r8
            r11 = r10
        L_0x0094:
            asw$a r4 = new asw$a
            bid r5 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r4.<init>(r5)
            asx$a r5 = r4.a
            r5.e = r7
            asx$a r5 = r4.a
            r5.f = r10
            asx$a r5 = r4.a
            r5.g = r8
            asx$a r5 = r4.a
            r5.h = r11
            r5 = 0
            r6 = r2[r5]
            asx$a r7 = r4.a
            r7.i = r6
            r6 = r3[r5]
            asx$a r7 = r4.a
            r7.j = r6
            r5 = r1[r5]
            asx$a r6 = r4.a
            r6.k = r5
            r5 = 1
            r6 = r2[r5]
            asx$a r7 = r4.a
            r7.l = r6
            r6 = r3[r5]
            asx$a r7 = r4.a
            r7.m = r6
            r5 = r1[r5]
            asx$a r6 = r4.a
            r6.n = r5
            r5 = 2
            r2 = r2[r5]
            asx$a r6 = r4.a
            r6.o = r2
            r2 = r3[r5]
            asx$a r3 = r4.a
            r3.p = r2
            r1 = r1[r5]
            asx$a r2 = r4.a
            r2.q = r1
            asx$a r1 = r4.a
            r1.b = r0
            asx$a r0 = r4.a
            r1 = r17
            r0.d = r1
            asx$a r0 = r4.a
            r1 = 10000(0x2710, float:1.4013E-41)
            r0.c = r1
            asx$a r0 = r4.a
            r1 = r19
            r0.r = r1
            asw r0 = new asw
            asx$a r1 = r4.a
            bid r1 = r1.a
            r0.<init>(r1)
            asx$a r1 = r4.a
            asx r2 = r0.b
            bid r3 = r1.a
            r2.c = r3
            asv r3 = r1.r
            r2.b = r3
            java.lang.String r1 = r1.a()
            r2.a = r1
            asx r1 = r0.b
            if (r1 == 0) goto L_0x012e
            esb r1 = defpackage.esb.a.a
            java.lang.Class<asu> r2 = defpackage.asu.class
            esc r1 = r1.a(r2)
            asu r1 = (defpackage.asu) r1
            if (r1 == 0) goto L_0x012e
            asx r0 = r0.b
            r1.a(r0)
        L_0x012e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.sa.a(int, java.lang.String, asv):void");
    }

    public static POI b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("startPoi")) {
                return null;
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("startPoi");
            POI createPOI = POIFactory.createPOI();
            createPOI.setId(optJSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
            a(createPOI, optJSONObject.optString("name"), optJSONObject.optString("lat"), optJSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON));
            if (optJSONObject.has("entry_lon") && optJSONObject.has("entry_lat")) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new GeoPoint(Double.parseDouble(optJSONObject.optString("entry_lon")), Double.parseDouble(optJSONObject.optString("entry_lat"))));
                createPOI.setEntranceList(arrayList);
            }
            return createPOI;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static POI c(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("endPoi")) {
                return null;
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("endPoi");
            POI createPOI = POIFactory.createPOI();
            createPOI.setId(optJSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
            a(createPOI, optJSONObject.optString("name"), optJSONObject.optString("lat"), optJSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON));
            if (optJSONObject.has("entry_lon") && optJSONObject.has("entry_lat")) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new GeoPoint(Double.parseDouble(optJSONObject.optString("entry_lon")), Double.parseDouble(optJSONObject.optString("entry_lat"))));
                createPOI.setEntranceList(arrayList);
            }
            return createPOI;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static List<Pair<Integer, POI>> d(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("midPoi")) {
                return null;
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("midPoi");
            if (optJSONArray.length() <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    POI createPOI = POIFactory.createPOI();
                    createPOI.setId(optJSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
                    a(createPOI, optJSONObject.optString("name"), optJSONObject.optString("lat"), optJSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON));
                    if (optJSONObject.has("entry_lon") && optJSONObject.has("entry_lat")) {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(new GeoPoint(Double.parseDouble(optJSONObject.optString("entry_lon")), Double.parseDouble(optJSONObject.optString("entry_lat"))));
                        createPOI.setEntranceList(arrayList2);
                    }
                    arrayList.add(new Pair(Integer.valueOf(optJSONObject.optInt("index")), createPOI));
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean c() {
        try {
            if (new JSONObject(lo.a().a((String) "voice_sdk")).optInt("requestPoiSelector", -1) == 1) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void e(int i) {
        if (i != -1) {
            aia aia = (aia) a.a.a(aia.class);
            if (aia != null) {
                aia.a(i, 10000, (Pair<String, Object>) null);
            }
        }
    }

    public static void a(int i, int i2) {
        if (i != -1) {
            aia aia = (aia) a.a.a(aia.class);
            if (aia != null) {
                aia.a(i, i2, (Pair<String, Object>) null);
            }
        }
    }

    public static void a(int i, Pair pair) {
        if (i != -1) {
            aia aia = (aia) a.a.a(aia.class);
            if (aia != null) {
                aia.a(i, 10000, pair);
            }
        }
    }
}
