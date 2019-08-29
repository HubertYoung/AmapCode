package defpackage;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.bundle.banner.manager.BannerManager$2;
import com.autonavi.bundle.banner.net.BannerResult;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.banner.BannerRequestHolder;
import com.autonavi.minimap.banner.param.BannerListRequest;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ash reason: default package */
/* compiled from: BannerManager */
public final class ash {

    /* renamed from: ash$a */
    /* compiled from: BannerManager */
    public interface a {
        void a(LinkedList<BannerItem> linkedList, long j);
    }

    public static boolean a(String str, int i) {
        Object obj;
        int i2;
        String string = AMapAppGlobal.getApplication().getSharedPreferences("BANNER_DATA".concat(String.valueOf(str)), 0).getString("latest_data", "");
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObject = new JSONObject(string);
            i2 = jSONObject.optInt(H5SensorPlugin.PARAM_INTERVAL);
            try {
                obj = jSONObject.optString("token", "");
                try {
                    JSONArray optJSONArray = jSONObject.optJSONArray("items");
                    if (optJSONArray != null) {
                        int length = optJSONArray.length();
                        for (int i3 = 0; i3 < length; i3++) {
                            JSONObject optJSONObject = optJSONArray.optJSONObject(i3);
                            if (optJSONObject != null) {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("action", optJSONObject.optString("action"));
                                jSONObject2.put("id", optJSONObject.optString("id"));
                                jSONObject2.put("image", optJSONObject.optString("image"));
                                jSONObject2.put("type", optJSONObject.optString("type"));
                                jSONObject2.put("btitle", optJSONObject.optString("btitle"));
                                jSONObject2.put("title", optJSONObject.optString("title"));
                                jSONObject2.put(Subscribe.THREAD_BACKGROUND, optJSONObject.optString(Subscribe.THREAD_BACKGROUND));
                                jSONObject2.put("font", optJSONObject.optString("font"));
                                jSONObject2.put("height", optJSONObject.optString("height"));
                                jSONObject2.put(H5Param.MENU_ICON, optJSONObject.optString(H5Param.MENU_ICON));
                                jSONObject2.put("mIsHide", optJSONObject.optString("mIsHide"));
                                if (i == i3) {
                                    jSONObject2.put("mIsHide", true);
                                }
                                jSONArray.put(jSONObject2);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e = e;
                    AMapLog.e("banner", String.valueOf(e));
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put(H5SensorPlugin.PARAM_INTERVAL, i2);
                    jSONObject3.put("items", jSONArray);
                    jSONObject3.put("token", obj);
                    AMapAppGlobal.getApplication().getSharedPreferences("BANNER_DATA".concat(String.valueOf(str)), 0).edit().putString("latest_data", jSONObject3.toString()).apply();
                    return true;
                }
            } catch (JSONException e2) {
                e = e2;
                obj = null;
                AMapLog.e("banner", String.valueOf(e));
                JSONObject jSONObject32 = new JSONObject();
                jSONObject32.put(H5SensorPlugin.PARAM_INTERVAL, i2);
                jSONObject32.put("items", jSONArray);
                jSONObject32.put("token", obj);
                AMapAppGlobal.getApplication().getSharedPreferences("BANNER_DATA".concat(String.valueOf(str)), 0).edit().putString("latest_data", jSONObject32.toString()).apply();
                return true;
            }
        } catch (JSONException e3) {
            e = e3;
            obj = null;
            i2 = 0;
            AMapLog.e("banner", String.valueOf(e));
            JSONObject jSONObject322 = new JSONObject();
            jSONObject322.put(H5SensorPlugin.PARAM_INTERVAL, i2);
            jSONObject322.put("items", jSONArray);
            jSONObject322.put("token", obj);
            AMapAppGlobal.getApplication().getSharedPreferences("BANNER_DATA".concat(String.valueOf(str)), 0).edit().putString("latest_data", jSONObject322.toString()).apply();
            return true;
        }
        JSONObject jSONObject3222 = new JSONObject();
        try {
            jSONObject3222.put(H5SensorPlugin.PARAM_INTERVAL, i2);
            jSONObject3222.put("items", jSONArray);
            jSONObject3222.put("token", obj);
            AMapAppGlobal.getApplication().getSharedPreferences("BANNER_DATA".concat(String.valueOf(str)), 0).edit().putString("latest_data", jSONObject3222.toString()).apply();
        } catch (JSONException e4) {
            AMapLog.e("banner", String.valueOf(e4));
        }
        return true;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:12|13|(3:15|16|17)|18|19|(3:21|22|23)|24|26|27|28|38) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x00a4 */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00ae  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.bundle.banner.data.BannerItem b(java.lang.String r4, int r5) {
        /*
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "BANNER_DATA"
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r4 = r1.concat(r4)
            r1 = 0
            android.content.SharedPreferences r4 = r0.getSharedPreferences(r4, r1)
            java.lang.String r0 = "latest_data"
            java.lang.String r2 = ""
            java.lang.String r4 = r4.getString(r0, r2)
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            r2 = 0
            if (r0 != 0) goto L_0x00d1
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00c7 }
            r0.<init>(r4)     // Catch:{ JSONException -> 0x00c7 }
            java.lang.String r4 = "interval"
            r0.optInt(r4)     // Catch:{ JSONException -> 0x00c7 }
            java.lang.String r4 = "token"
            java.lang.String r3 = ""
            r0.optString(r4, r3)     // Catch:{ JSONException -> 0x00c7 }
            java.lang.String r4 = "items"
            org.json.JSONArray r4 = r0.optJSONArray(r4)     // Catch:{ JSONException -> 0x00c7 }
            if (r4 == 0) goto L_0x00d1
            int r0 = r4.length()     // Catch:{ JSONException -> 0x00c7 }
            if (r5 < r0) goto L_0x0043
            goto L_0x00d1
        L_0x0043:
            org.json.JSONObject r4 = r4.optJSONObject(r5)     // Catch:{ JSONException -> 0x00c7 }
            if (r4 == 0) goto L_0x00d1
            com.autonavi.bundle.banner.data.BannerItem r5 = new com.autonavi.bundle.banner.data.BannerItem     // Catch:{ JSONException -> 0x00c7 }
            r5.<init>()     // Catch:{ JSONException -> 0x00c7 }
            java.lang.String r0 = "action"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            r5.action = r0     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r0 = "id"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            r5.id = r0     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r0 = "image"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            r5.imageURL = r0     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r0 = "type"
            int r0 = r4.optInt(r0)     // Catch:{ JSONException -> 0x00c4 }
            r5.type = r0     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r0 = "btitle"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            r5.bannerTitle = r0     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r0 = "title"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            r5.title = r0     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r0 = "background"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            r5.background = r0     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r0 = "font"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            r5.font = r0     // Catch:{ JSONException -> 0x00c4 }
            r5.height = r1     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r0 = "height"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            if (r0 == 0) goto L_0x00a4
            java.lang.String r0 = "height"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ Exception -> 0x00a4 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x00a4 }
            r5.height = r0     // Catch:{ Exception -> 0x00a4 }
        L_0x00a4:
            r5.mIsHide = r1     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r0 = "mIsHide"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            if (r0 == 0) goto L_0x00ba
            java.lang.String r0 = "mIsHide"
            java.lang.String r0 = r4.optString(r0)     // Catch:{ Exception -> 0x00ba }
            boolean r0 = java.lang.Boolean.parseBoolean(r0)     // Catch:{ Exception -> 0x00ba }
            r5.mIsHide = r0     // Catch:{ Exception -> 0x00ba }
        L_0x00ba:
            java.lang.String r0 = "icon"
            java.lang.String r4 = r4.optString(r0)     // Catch:{ JSONException -> 0x00c4 }
            r5.icon = r4     // Catch:{ JSONException -> 0x00c4 }
            r2 = r5
            goto L_0x00d1
        L_0x00c4:
            r4 = move-exception
            r2 = r5
            goto L_0x00c8
        L_0x00c7:
            r4 = move-exception
        L_0x00c8:
            java.lang.String r5 = "banner"
            java.lang.String r4 = java.lang.String.valueOf(r4)
            com.amap.bundle.logs.AMapLog.e(r5, r4)
        L_0x00d1:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ash.b(java.lang.String, int):com.autonavi.bundle.banner.data.BannerItem");
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x012a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x012b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.lang.String r17, boolean r18, boolean r19, defpackage.ash.a r20) {
        /*
            r1 = r17
            r3 = r20
            r4 = 0
            if (r19 == 0) goto L_0x0009
            r5 = 0
            goto L_0x000b
        L_0x0009:
            r5 = r18
        L_0x000b:
            if (r19 != 0) goto L_0x0127
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r7 = "BANNER_DATA"
            java.lang.String r8 = java.lang.String.valueOf(r17)
            java.lang.String r7 = r7.concat(r8)
            android.content.SharedPreferences r2 = r2.getSharedPreferences(r7, r4)
            java.lang.String r7 = "latest_data"
            java.lang.String r8 = ""
            java.lang.String r7 = r2.getString(r7, r8)
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 != 0) goto L_0x0127
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0118 }
            r9.<init>(r7)     // Catch:{ JSONException -> 0x0118 }
            java.lang.String r7 = "interval"
            int r7 = r9.optInt(r7)     // Catch:{ JSONException -> 0x0118 }
            java.lang.String r10 = "token"
            java.lang.String r11 = ""
            java.lang.String r10 = r9.optString(r10, r11)     // Catch:{ JSONException -> 0x0118 }
            java.lang.String r11 = "items"
            org.json.JSONArray r9 = r9.optJSONArray(r11)     // Catch:{ JSONException -> 0x0114 }
            if (r9 == 0) goto L_0x00fc
            java.util.LinkedList r11 = new java.util.LinkedList     // Catch:{ JSONException -> 0x0114 }
            r11.<init>()     // Catch:{ JSONException -> 0x0114 }
            int r12 = r9.length()     // Catch:{ JSONException -> 0x0114 }
            r13 = 0
            r14 = 1
        L_0x0053:
            if (r13 >= r12) goto L_0x00fe
            org.json.JSONObject r15 = r9.optJSONObject(r13)     // Catch:{ JSONException -> 0x0114 }
            if (r15 == 0) goto L_0x00f8
            com.autonavi.bundle.banner.data.BannerItem r6 = new com.autonavi.bundle.banner.data.BannerItem     // Catch:{ JSONException -> 0x0114 }
            r6.<init>()     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "action"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.action = r8     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "id"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.id = r8     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "msg_id"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.msg_id = r8     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "tag"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.tag = r8     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "image"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.imageURL = r8     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "type"
            int r8 = r15.optInt(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.type = r8     // Catch:{ JSONException -> 0x0114 }
            int r8 = r6.type     // Catch:{ JSONException -> 0x0114 }
            if (r8 > 0) goto L_0x0095
            r14 = 0
        L_0x0095:
            java.lang.String r8 = "btitle"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.bannerTitle = r8     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "title"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.title = r8     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "background"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.background = r8     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "font"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.font = r8     // Catch:{ JSONException -> 0x0114 }
            r6.height = r4     // Catch:{ JSONException -> 0x0114 }
            r6.mIsHide = r4     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "mIsHide"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            if (r8 == 0) goto L_0x00d1
            java.lang.String r8 = "mIsHide"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ Exception -> 0x00d1 }
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r8)     // Catch:{ Exception -> 0x00d1 }
            boolean r8 = r8.booleanValue()     // Catch:{ Exception -> 0x00d1 }
            r6.mIsHide = r8     // Catch:{ Exception -> 0x00d1 }
        L_0x00d1:
            java.lang.String r8 = "height"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            if (r8 == 0) goto L_0x00e5
            java.lang.String r8 = "height"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ Exception -> 0x00e5 }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ Exception -> 0x00e5 }
            r6.height = r8     // Catch:{ Exception -> 0x00e5 }
        L_0x00e5:
            java.lang.String r8 = "icon"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.icon = r8     // Catch:{ JSONException -> 0x0114 }
            java.lang.String r8 = "impression"
            java.lang.String r8 = r15.optString(r8)     // Catch:{ JSONException -> 0x0114 }
            r6.impression = r8     // Catch:{ JSONException -> 0x0114 }
            r11.add(r6)     // Catch:{ JSONException -> 0x0114 }
        L_0x00f8:
            int r13 = r13 + 1
            goto L_0x0053
        L_0x00fc:
            r11 = 0
            r14 = 1
        L_0x00fe:
            if (r14 == 0) goto L_0x0105
            long r6 = (long) r7     // Catch:{ JSONException -> 0x0114 }
            r3.a(r11, r6)     // Catch:{ JSONException -> 0x0114 }
            goto L_0x0128
        L_0x0105:
            android.content.SharedPreferences$Editor r2 = r2.edit()     // Catch:{ JSONException -> 0x0118 }
            java.lang.String r4 = "latest_data"
            java.lang.String r6 = ""
            r2.putString(r4, r6)     // Catch:{ JSONException -> 0x0118 }
            r2.apply()     // Catch:{ JSONException -> 0x0118 }
            goto L_0x0127
        L_0x0114:
            r0 = move-exception
            r2 = r0
            r6 = r10
            goto L_0x011b
        L_0x0118:
            r0 = move-exception
            r2 = r0
            r6 = 0
        L_0x011b:
            java.lang.String r4 = "banner"
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r7 = 1
            com.amap.bundle.logs.AMapLog.e(r4, r2, r7)
            r10 = r6
            goto L_0x0128
        L_0x0127:
            r10 = 0
        L_0x0128:
            if (r5 == 0) goto L_0x012b
            return
        L_0x012b:
            com.autonavi.bundle.banner.manager.BannerManager$1 r2 = new com.autonavi.bundle.banner.manager.BannerManager$1
            r2.<init>(r10, r1, r3)
            a(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ash.a(java.lang.String, boolean, boolean, ash$a):void");
    }

    public static void a(String str, Callback<BannerResult> callback) {
        BannerListRequest a2 = a(str);
        if (a2 != null) {
            BannerRequestHolder.getInstance().sendList(a2, new BannerManager$2(callback));
        } else {
            callback.error(null, false);
        }
    }

    private static BannerListRequest a(String str) {
        int i;
        try {
            i = Integer.parseInt(str);
        } catch (Throwable unused) {
            i = -1;
        }
        if (i < 0) {
            return null;
        }
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5) != null ? LocationInstrument.getInstance().getLatestPosition() : null;
        if (latestPosition == null) {
            return null;
        }
        DPoint a2 = cfg.a((long) latestPosition.x, (long) latestPosition.y);
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        defpackage.kx.a[] b = kx.b(AMapAppGlobal.getApplication());
        for (int i2 = 0; i2 <= 0; i2++) {
            defpackage.kx.a aVar = b[i2];
            String str2 = "";
            switch (aVar.c) {
                case 0:
                    str2 = "";
                    break;
                case 1:
                    str2 = "cu";
                    break;
                case 2:
                    str2 = LogItem.MM_C43_K4_CAMERA_TIME;
                    break;
                case 3:
                    str2 = "cm";
                    break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(",");
            stringBuffer.append(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(aVar.a);
            sb2.append(",");
            stringBuffer2.append(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(aVar.b);
            sb3.append(",");
            stringBuffer3.append(sb3.toString());
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer2.deleteCharAt(stringBuffer2.length() - 1);
        stringBuffer3.deleteCharAt(stringBuffer3.length() - 1);
        String valueOf = String.valueOf(a2.x);
        String valueOf2 = String.valueOf(a2.y);
        BannerListRequest bannerListRequest = new BannerListRequest();
        bannerListRequest.b = valueOf;
        bannerListRequest.c = valueOf2;
        bannerListRequest.g = "";
        bannerListRequest.d = stringBuffer.toString();
        bannerListRequest.i = stringBuffer2.toString();
        bannerListRequest.h = i;
        bannerListRequest.j = stringBuffer3.toString();
        if (TextUtils.isEmpty(str) || !str.equals("9")) {
            bannerListRequest.o = -1;
        } else {
            bannerListRequest.o = 1;
        }
        return bannerListRequest;
    }
}
