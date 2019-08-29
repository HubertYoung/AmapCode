package defpackage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.util.HashMap;

/* renamed from: dlq reason: default package */
/* compiled from: MainIntentDispatcher */
public final class dlq extends BaseIntentDispatcher {
    private final String a = "https://wap.amap.com/gxd/index.html";
    private final String b = "myLocation";
    private final String c = "shorturl";
    private final String d = "rootmap";
    private final String e = "CitySelect";
    private final String f = "SpecialTopic";
    private final String g = "Subway";
    private final String h = "EasyDriving";
    private final String i = "Illegal";
    private final String j = "Favorite";
    private final String k = "Layers";
    private final String l = "ExternalURL";
    private final String m = "Cleaning";
    private final String n = "ShortCut";
    private final String o = "GoldCoin";
    private final String p = "DrivingZone";
    private final String q = "ShowLayerPanel";
    private ProgressDlg r;
    /* access modifiers changed from: private */
    public boolean s = false;
    private Activity t;

    public dlq(Activity activity) {
        this.t = activity;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002d, code lost:
        if (r2.size() > 0) goto L_0x039c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:321:0x083f, code lost:
        if (r1 == false) goto L_0x0841;
     */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x039f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x03a0  */
    /* JADX WARNING: Removed duplicated region for block: B:326:0x0846  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(android.content.Intent r27) {
        /*
            r26 = this;
            r7 = r26
            android.net.Uri r8 = r27.getData()
            r9 = 1
            if (r8 != 0) goto L_0x000a
            return r9
        L_0x000a:
            java.lang.String r10 = r8.getHost()
            r11 = 0
            if (r8 == 0) goto L_0x084b
            boolean r1 = android.text.TextUtils.isEmpty(r10)
            if (r1 != 0) goto L_0x084b
            java.lang.String r1 = r8.getHost()
            java.util.List r2 = r8.getPathSegments()
            java.lang.String r3 = "poi"
            boolean r3 = android.text.TextUtils.equals(r1, r3)
            if (r3 == 0) goto L_0x0031
            if (r2 == 0) goto L_0x039d
            int r1 = r2.size()
            if (r1 > 0) goto L_0x039c
            goto L_0x039d
        L_0x0031:
            java.lang.String r3 = "amap"
            boolean r3 = android.text.TextUtils.equals(r1, r3)
            if (r3 == 0) goto L_0x0059
            android.app.Activity r1 = r7.t
            if (r1 == 0) goto L_0x0056
            android.app.Activity r1 = r7.t
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            if (r1 == 0) goto L_0x0056
            android.app.Activity r2 = r7.t
            java.lang.String r2 = r2.getPackageName()
            android.content.Intent r1 = r1.getLaunchIntentForPackage(r2)
            if (r1 == 0) goto L_0x0056
            android.app.Activity r2 = r7.t
            r2.startActivity(r1)
        L_0x0056:
            r11 = 1
            goto L_0x039d
        L_0x0059:
            java.lang.String r3 = "drivingzone"
            boolean r3 = android.text.TextUtils.equals(r1, r3)
            if (r3 == 0) goto L_0x007d
            if (r2 == 0) goto L_0x039d
            boolean r1 = r2.isEmpty()
            if (r1 == 0) goto L_0x006b
            goto L_0x039d
        L_0x006b:
            java.lang.Object r1 = r2.get(r11)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = "list"
            boolean r1 = android.text.TextUtils.equals(r1, r2)
            if (r1 == 0) goto L_0x039c
            a()
            goto L_0x0056
        L_0x007d:
            java.lang.String r3 = "share"
            boolean r3 = android.text.TextUtils.equals(r1, r3)
            if (r3 == 0) goto L_0x0217
            if (r2 == 0) goto L_0x039c
            boolean r1 = r2.isEmpty()
            if (r1 == 0) goto L_0x008f
            goto L_0x039c
        L_0x008f:
            java.lang.Object r1 = r2.get(r11)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = "openpanel"
            boolean r1 = android.text.TextUtils.equals(r1, r2)
            if (r1 == 0) goto L_0x039c
            java.lang.String r1 = "content"
            java.lang.String r1 = r8.getQueryParameter(r1)
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0210 }
            r2.<init>(r1)     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r3 = "content"
            org.json.JSONArray r2 = r2.getJSONArray(r3)     // Catch:{ JSONException -> 0x0210 }
            int r3 = r2.length()     // Catch:{ JSONException -> 0x0210 }
            dct r5 = new dct     // Catch:{ JSONException -> 0x0210 }
            r5.<init>(r11)     // Catch:{ JSONException -> 0x0210 }
            r4 = 0
        L_0x00bd:
            if (r4 >= r3) goto L_0x017e
            org.json.JSONObject r13 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r14 = "type"
            java.lang.String r14 = r13.optString(r14)     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r15 = "title"
            java.lang.String r15 = r13.optString(r15)     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r12 = "message"
            r13.optString(r12)     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r12 = "url"
            java.lang.String r17 = r13.optString(r12)     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r12 = "imgurl"
            java.lang.String r20 = r13.optString(r12)     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r12 = "weibo"
            boolean r12 = android.text.TextUtils.equals(r14, r12)     // Catch:{ JSONException -> 0x0210 }
            if (r12 == 0) goto L_0x010a
            r5.f = r9     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r12 = "weibo"
            dcq r14 = new dcq     // Catch:{ JSONException -> 0x0210 }
            r16 = 5
            r18 = 0
            r19 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r13 = r14
            r11 = r14
            r14 = r16
            r16 = r1
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)     // Catch:{ JSONException -> 0x0210 }
            r6.put(r12, r11)     // Catch:{ JSONException -> 0x0210 }
            goto L_0x0179
        L_0x010a:
            java.lang.String r11 = "weixin"
            boolean r11 = android.text.TextUtils.equals(r14, r11)     // Catch:{ JSONException -> 0x0210 }
            if (r11 == 0) goto L_0x012f
            r5.d = r9     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r11 = "weixin"
            dcq r12 = new dcq     // Catch:{ JSONException -> 0x0210 }
            r14 = 3
            r18 = 0
            r19 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r13 = r12
            r16 = r1
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)     // Catch:{ JSONException -> 0x0210 }
            r6.put(r11, r12)     // Catch:{ JSONException -> 0x0210 }
            goto L_0x0179
        L_0x012f:
            java.lang.String r11 = "pengyou"
            boolean r11 = android.text.TextUtils.equals(r14, r11)     // Catch:{ JSONException -> 0x0210 }
            if (r11 == 0) goto L_0x0154
            r5.e = r9     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r11 = "pengyou"
            dcq r12 = new dcq     // Catch:{ JSONException -> 0x0210 }
            r14 = 4
            r18 = 0
            r19 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r13 = r12
            r16 = r1
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)     // Catch:{ JSONException -> 0x0210 }
            r6.put(r11, r12)     // Catch:{ JSONException -> 0x0210 }
            goto L_0x0179
        L_0x0154:
            java.lang.String r11 = "dingding"
            boolean r11 = android.text.TextUtils.equals(r14, r11)     // Catch:{ JSONException -> 0x0210 }
            if (r11 == 0) goto L_0x0179
            r5.l = r9     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r11 = "dingding"
            dcq r12 = new dcq     // Catch:{ JSONException -> 0x0210 }
            r14 = 11
            r18 = 0
            r19 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r13 = r12
            r16 = r1
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)     // Catch:{ JSONException -> 0x0210 }
            r6.put(r11, r12)     // Catch:{ JSONException -> 0x0210 }
        L_0x0179:
            int r4 = r4 + 1
            r11 = 0
            goto L_0x00bd
        L_0x017e:
            int r1 = r6.size()     // Catch:{ JSONException -> 0x0210 }
            if (r1 != 0) goto L_0x019b
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ JSONException -> 0x0210 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ JSONException -> 0x0210 }
            android.content.res.Resources r1 = r1.getResources()     // Catch:{ JSONException -> 0x0210 }
            int r2 = com.autonavi.minimap.R.string.share_content_is_empty     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r1 = r1.getString(r2)     // Catch:{ JSONException -> 0x0210 }
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r1)     // Catch:{ JSONException -> 0x0210 }
            goto L_0x0056
        L_0x019b:
            java.lang.String r1 = "weixin"
            java.lang.Object r1 = r6.get(r1)     // Catch:{ JSONException -> 0x0210 }
            r3 = r1
            dcq r3 = (defpackage.dcq) r3     // Catch:{ JSONException -> 0x0210 }
            java.lang.String r1 = "pengyou"
            java.lang.Object r1 = r6.get(r1)     // Catch:{ JSONException -> 0x0210 }
            r4 = r1
            dcq r4 = (defpackage.dcq) r4     // Catch:{ JSONException -> 0x0210 }
            if (r3 == 0) goto L_0x01b7
            java.lang.String r1 = r3.imgUrl     // Catch:{ JSONException -> 0x0210 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0210 }
            if (r1 == 0) goto L_0x01c1
        L_0x01b7:
            if (r4 == 0) goto L_0x020a
            java.lang.String r1 = r4.imgUrl     // Catch:{ JSONException -> 0x0210 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0210 }
            if (r1 != 0) goto L_0x020a
        L_0x01c1:
            java.lang.String r1 = "获取分享内容中.."
            com.autonavi.map.widget.ProgressDlg r2 = r7.r     // Catch:{ Exception -> 0x01f9 }
            if (r2 != 0) goto L_0x01e3
            com.autonavi.map.widget.ProgressDlg r2 = new com.autonavi.map.widget.ProgressDlg     // Catch:{ Exception -> 0x01f9 }
            android.app.Activity r11 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()     // Catch:{ Exception -> 0x01f9 }
            java.lang.String r12 = ""
            r2.<init>(r11, r1, r12)     // Catch:{ Exception -> 0x01f9 }
            r7.r = r2     // Catch:{ Exception -> 0x01f9 }
            com.autonavi.map.widget.ProgressDlg r2 = r7.r     // Catch:{ Exception -> 0x01f9 }
            r2.setCancelable(r9)     // Catch:{ Exception -> 0x01f9 }
            com.autonavi.map.widget.ProgressDlg r2 = r7.r     // Catch:{ Exception -> 0x01f9 }
            dlq$3 r11 = new dlq$3     // Catch:{ Exception -> 0x01f9 }
            r11.<init>()     // Catch:{ Exception -> 0x01f9 }
            r2.setOnCancelListener(r11)     // Catch:{ Exception -> 0x01f9 }
        L_0x01e3:
            r2 = 0
            r7.s = r2     // Catch:{ Exception -> 0x01f9 }
            com.autonavi.map.widget.ProgressDlg r2 = r7.r     // Catch:{ Exception -> 0x01f9 }
            r2.setMessage(r1)     // Catch:{ Exception -> 0x01f9 }
            com.autonavi.map.widget.ProgressDlg r1 = r7.r     // Catch:{ Exception -> 0x01f9 }
            boolean r1 = r1.isShowing()     // Catch:{ Exception -> 0x01f9 }
            if (r1 != 0) goto L_0x01fe
            com.autonavi.map.widget.ProgressDlg r1 = r7.r     // Catch:{ Exception -> 0x01f9 }
            r1.show()     // Catch:{ Exception -> 0x01f9 }
            goto L_0x01fe
        L_0x01f9:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ JSONException -> 0x0210 }
        L_0x01fe:
            dlq$7 r11 = new dlq$7     // Catch:{ JSONException -> 0x0210 }
            r1 = r11
            r2 = r7
            r1.<init>(r3, r4, r5, r6)     // Catch:{ JSONException -> 0x0210 }
            defpackage.ahm.a(r11)     // Catch:{ JSONException -> 0x0210 }
            goto L_0x0056
        L_0x020a:
            r1 = 0
            r7.a(r5, r6, r1, r1)     // Catch:{ JSONException -> 0x0210 }
            goto L_0x0056
        L_0x0210:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            goto L_0x0056
        L_0x0217:
            java.lang.String r3 = "map"
            boolean r3 = android.text.TextUtils.equals(r1, r3)
            if (r3 == 0) goto L_0x0331
            if (r2 == 0) goto L_0x039c
            int r1 = r2.size()
            if (r1 > 0) goto L_0x0229
            goto L_0x039c
        L_0x0229:
            r1 = 0
            java.lang.Object r2 = r2.get(r1)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r1 = "ShowLayerPanel"
            boolean r1 = r2.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x0250
            com.autonavi.bundle.amaphome.api.MapHomeIntentAction r1 = com.autonavi.bundle.amaphome.api.MapHomeIntentAction.OPEN_LAYER_PANEL
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            java.lang.String r3 = "key_action"
            java.lang.String r4 = "action_base_map_scheme"
            r2.putString(r3, r4)
            java.lang.String r3 = "key_scheme_feature"
            r2.putObject(r3, r1)
            a(r2)
            goto L_0x0056
        L_0x0250:
            java.lang.String r1 = "showmergedialog"
            boolean r1 = r2.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x0266
            android.app.Activity r1 = r7.t
            if (r1 == 0) goto L_0x039c
            dlq$1 r1 = new dlq$1
            r1.<init>()
            defpackage.aho.a(r1)
            goto L_0x0056
        L_0x0266:
            java.lang.String r1 = "showtraffic"
            boolean r1 = r2.equalsIgnoreCase(r1)
            r3 = -1
            if (r1 == 0) goto L_0x02a1
            java.lang.String r1 = "longitude"
            double r1 = defpackage.agt.a(r8, r1)
            java.lang.String r4 = "latitude"
            double r4 = defpackage.agt.a(r8, r4)
            java.lang.String r6 = "zoom"
            int r3 = defpackage.agt.a(r8, r6, r3)
            com.autonavi.common.PageBundle r6 = new com.autonavi.common.PageBundle
            r6.<init>()
            java.lang.String r11 = "key_action"
            java.lang.String r12 = "action_show_traffic"
            r6.putString(r11, r12)
            java.lang.String r11 = "lat"
            r6.putDouble(r11, r4)
            java.lang.String r4 = "lon"
            r6.putDouble(r4, r1)
            java.lang.String r1 = "zoom"
            r6.putInt(r1, r3)
            a(r6)
            goto L_0x0056
        L_0x02a1:
            java.lang.String r1 = "trafficevent"
            boolean r1 = r2.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x02e8
            java.lang.String r1 = "id"
            int r1 = defpackage.agt.a(r8, r1, r3)
            java.lang.String r2 = "longitude"
            double r4 = defpackage.agt.a(r8, r2)
            java.lang.String r2 = "latitude"
            double r11 = defpackage.agt.a(r8, r2)
            java.lang.String r2 = "zoom"
            int r2 = defpackage.agt.a(r8, r2, r3)
            if (r1 <= 0) goto L_0x0056
            com.autonavi.common.PageBundle r3 = new com.autonavi.common.PageBundle
            r3.<init>()
            java.lang.String r6 = "key_action"
            java.lang.String r13 = "action_traffic_event"
            r3.putString(r6, r13)
            java.lang.String r6 = "traffic_event_id"
            r3.putInt(r6, r1)
            java.lang.String r1 = "lat"
            r3.putDouble(r1, r11)
            java.lang.String r1 = "lon"
            r3.putDouble(r1, r4)
            java.lang.String r1 = "zoom"
            r3.putInt(r1, r2)
            a(r3)
            goto L_0x0056
        L_0x02e8:
            java.lang.String r1 = "showRealtimebus"
            boolean r1 = r2.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x031b
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            java.lang.String r2 = "key_action"
            java.lang.String r3 = "action_show_realtime_bus"
            r1.putString(r2, r3)
            if (r8 == 0) goto L_0x0316
            java.lang.String r2 = "show"
            java.lang.String r2 = r8.getQueryParameter(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0316
            java.lang.String r2 = "show"
            r3 = 0
            boolean r2 = r8.getBooleanQueryParameter(r2, r3)
            java.lang.String r3 = "key_schema_realtime_bus_show"
            r1.putBoolean(r3, r2)
        L_0x0316:
            a(r1)
            goto L_0x0056
        L_0x031b:
            java.lang.String r1 = "real3d"
            boolean r1 = r2.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x0056
            com.autonavi.map.core.Real3DManager.a()
            com.autonavi.common.PageBundle r1 = com.autonavi.map.core.Real3DManager.a(r8)
            if (r1 == 0) goto L_0x039c
            a(r1)
            goto L_0x0056
        L_0x0331:
            java.lang.String r2 = "auitest"
            boolean r2 = android.text.TextUtils.equals(r1, r2)
            if (r2 == 0) goto L_0x0363
            boolean r1 = com.autonavi.ae.AEUtil.IS_DEBUG
            if (r1 == 0) goto L_0x039c
            java.lang.String r1 = "page"
            java.lang.String r1 = r8.getQueryParameter(r1)
            java.lang.String r2 = "sinber"
            java.lang.String r3 = "doOpenNewAuiTestScheme page:"
            java.lang.String r4 = java.lang.String.valueOf(r1)
            java.lang.String r3 = r3.concat(r4)
            com.amap.bundle.logs.AMapLog.e(r2, r3)
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            java.lang.String r3 = "url"
            r2.putString(r3, r1)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r1 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r7.startPage(r1, r2)
            goto L_0x0056
        L_0x0363:
            java.lang.String r2 = "real3dmap"
            boolean r2 = android.text.TextUtils.equals(r1, r2)
            if (r2 == 0) goto L_0x0383
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            java.lang.String r2 = "key_action"
            java.lang.String r3 = "action_base_map_scheme"
            r1.putString(r2, r3)
            java.lang.String r2 = "key_scheme_feature"
            com.autonavi.bundle.amaphome.api.MapHomeIntentAction r3 = com.autonavi.bundle.amaphome.api.MapHomeIntentAction.CLOSED_TIPS_POINT
            r1.putObject(r2, r3)
            a(r1)
            goto L_0x0056
        L_0x0383:
            java.lang.String r2 = "ajxdebug"
            boolean r1 = android.text.TextUtils.equals(r1, r2)
            if (r1 == 0) goto L_0x039c
            boolean r1 = com.autonavi.minimap.ajx3.util.AjxDebugUtils.isRelease()
            if (r1 != 0) goto L_0x0056
            android.app.Activity r1 = r7.t
            boolean r1 = r1 instanceof com.autonavi.map.activity.NewMapActivity
            if (r1 == 0) goto L_0x0056
            com.autonavi.map.activity.NewMapActivity.a(r8)
            goto L_0x0056
        L_0x039c:
            r11 = 0
        L_0x039d:
            if (r11 == 0) goto L_0x03a0
            return r9
        L_0x03a0:
            java.lang.String r1 = "myLocation"
            boolean r1 = r10.equals(r1)
            if (r1 == 0) goto L_0x03c0
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            java.lang.String r2 = "key_action"
            java.lang.String r3 = "action_base_map_scheme"
            r1.putString(r2, r3)
            java.lang.String r2 = "key_scheme_feature"
            com.autonavi.bundle.amaphome.api.MapHomeIntentAction r3 = com.autonavi.bundle.amaphome.api.MapHomeIntentAction.MY_LOCATION
            r1.putObject(r2, r3)
            a(r1)
            goto L_0x0848
        L_0x03c0:
            java.lang.String r1 = "shorturl"
            boolean r1 = r10.endsWith(r1)
            if (r1 == 0) goto L_0x0405
            android.content.Intent r1 = new android.content.Intent
            java.lang.String r2 = "android.intent.action.VIEW"
            r1.<init>(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "http://wb.amap.com/?"
            r2.<init>(r3)
            java.lang.String r3 = r8.getQuery()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.net.Uri r2 = android.net.Uri.parse(r2)
            r1.setData(r2)
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            java.lang.String r3 = "key_action"
            java.lang.String r4 = "action_base_map_scheme"
            r2.putString(r3, r4)
            java.lang.String r3 = "key_scheme_feature"
            com.autonavi.bundle.amaphome.api.MapHomeIntentAction r4 = com.autonavi.bundle.amaphome.api.MapHomeIntentAction.SHORT_URL
            r2.putObject(r3, r4)
            java.lang.String r3 = "key_schema_short_url_intent"
            r2.putObject(r3, r1)
            a(r2)
            goto L_0x0848
        L_0x0405:
            java.lang.String r1 = "rootmap"
            boolean r1 = r10.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x0412
            r26.removeAllFragmentsWithoutRoot()
            goto L_0x0848
        L_0x0412:
            java.lang.String r1 = "openFeature"
            boolean r1 = r10.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x0849
            android.net.Uri r1 = r27.getData()
            java.lang.String r2 = "featureName"
            java.lang.String r2 = r1.getQueryParameter(r2)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L_0x0439
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r2 = com.autonavi.minimap.R.string.intent_open_fail_param_error
            java.lang.String r1 = r1.getString(r2)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r1)
            goto L_0x0843
        L_0x0439:
            java.lang.String r3 = "CitySelect"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0452
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            java.lang.String r2 = "SWITCH_CITY_FOR"
            r1.putInt(r2, r9)
            java.lang.String r2 = "amap.basemap.action.switch_city_node_page"
            r7.startPage(r2, r1)
            goto L_0x0843
        L_0x0452:
            java.lang.String r3 = "SpecialTopic"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0468
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r1 == 0) goto L_0x0843
            java.lang.String r2 = "amap.basemap.action.acticities"
            r3 = 0
            r1.startPage(r2, r3)
            goto L_0x0843
        L_0x0468:
            java.lang.String r3 = "Subway"
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto L_0x0475
            r7.a(r1)
            goto L_0x0843
        L_0x0475:
            java.lang.String r3 = "EasyDriving"
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto L_0x0490
            esb r1 = defpackage.esb.a.a
            java.lang.Class<ddq> r2 = defpackage.ddq.class
            esc r1 = r1.a(r2)
            ddq r1 = (defpackage.ddq) r1
            if (r1 == 0) goto L_0x0843
            r1.b()
            goto L_0x0843
        L_0x0490:
            java.lang.String r3 = "Illegal"
            boolean r3 = r3.equalsIgnoreCase(r2)
            if (r3 == 0) goto L_0x04e4
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 11
            if (r1 >= r2) goto L_0x04ad
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r2 = com.autonavi.minimap.R.string.intent_low_app_version
            java.lang.String r1 = r1.getString(r2)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r1)
            goto L_0x0843
        L_0x04ad:
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r1 == 0) goto L_0x0843
            boolean r1 = b()
            if (r1 == 0) goto L_0x04c8
            java.lang.Class<com.autonavi.minimap.basemap.traffic.inter.ICarILlegalUtil> r1 = com.autonavi.minimap.basemap.traffic.inter.ICarILlegalUtil.class
            java.lang.Object r1 = defpackage.ank.a(r1)
            com.autonavi.minimap.basemap.traffic.inter.ICarILlegalUtil r1 = (com.autonavi.minimap.basemap.traffic.inter.ICarILlegalUtil) r1
            if (r1 == 0) goto L_0x0843
            r1.a()
            goto L_0x0843
        L_0x04c8:
            esb r1 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r2 = com.autonavi.bundle.account.api.IAccountService.class
            esc r1 = r1.a(r2)
            com.autonavi.bundle.account.api.IAccountService r1 = (com.autonavi.bundle.account.api.IAccountService) r1
            if (r1 == 0) goto L_0x0843
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            dlq$5 r3 = new dlq$5
            r3.<init>()
            r1.a(r2, r3)
            goto L_0x0843
        L_0x04e4:
            java.lang.String r3 = "Favorite"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x04ff
            esb r1 = defpackage.esb.a.a
            java.lang.Class<auz> r2 = defpackage.auz.class
            esc r1 = r1.a(r2)
            auz r1 = (defpackage.auz) r1
            if (r1 == 0) goto L_0x0843
            r1.a()
            goto L_0x0843
        L_0x04ff:
            java.lang.String r3 = "Layers"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0517
            java.lang.Class<com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController> r1 = com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController.class
            java.lang.Object r1 = defpackage.ank.a(r1)
            com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController r1 = (com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController) r1
            if (r1 == 0) goto L_0x0843
            r2 = 0
            r1.a(r2)
            goto L_0x0843
        L_0x0517:
            java.lang.String r3 = "ExternalURL"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0543
            java.lang.String r2 = "url"
            java.lang.String r1 = r1.getQueryParameter(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0843
            java.lang.String r2 = "UTF-8"
            java.lang.String r1 = java.net.URLDecoder.decode(r1, r2)     // Catch:{ Exception -> 0x0843 }
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch:{ Exception -> 0x0843 }
            android.app.Activity r2 = r7.t     // Catch:{ Exception -> 0x0843 }
            android.content.Intent r3 = new android.content.Intent     // Catch:{ Exception -> 0x0843 }
            java.lang.String r4 = "android.intent.action.VIEW"
            r3.<init>(r4, r1)     // Catch:{ Exception -> 0x0843 }
            r2.startActivity(r3)     // Catch:{ Exception -> 0x0843 }
            goto L_0x0843
        L_0x0543:
            java.lang.String r3 = "Cleaning"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x055e
            esb r1 = defpackage.esb.a.a
            java.lang.Class<ddq> r2 = defpackage.ddq.class
            esc r1 = r1.a(r2)
            ddq r1 = (defpackage.ddq) r1
            if (r1 == 0) goto L_0x0843
            r1.c()
            goto L_0x0843
        L_0x055e:
            java.lang.String r3 = "ShortCut"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x056e
            java.lang.String r1 = "amap.basemap.action.add_navi_shortcut_page"
            r2 = 0
            r7.startPage(r1, r2)
            goto L_0x0843
        L_0x056e:
            java.lang.String r3 = "GoldCoin"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x05da
            java.lang.String r2 = "page"
            java.lang.String r1 = r1.getQueryParameter(r2)
            java.lang.String r2 = "history"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x059e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.amap.bundle.blutils.app.ConfigerHelper r2 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r2 = r2.getGoldcoinUrl()
            r1.append(r2)
            java.lang.String r2 = "record.html"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            goto L_0x05b7
        L_0x059e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.amap.bundle.blutils.app.ConfigerHelper r2 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r2 = r2.getGoldcoinUrl()
            r1.append(r2)
            java.lang.String r2 = "index.html"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
        L_0x05b7:
            aja r2 = new aja
            r2.<init>(r1)
            ajf r1 = new ajf
            r1.<init>()
            r2.b = r1
            esb r1 = defpackage.esb.a.a
            java.lang.Class<aix> r3 = defpackage.aix.class
            esc r1 = r1.a(r3)
            aix r1 = (defpackage.aix) r1
            if (r1 == 0) goto L_0x0843
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r1.a(r3, r2)
            goto L_0x0843
        L_0x05da:
            java.lang.String r3 = "PoiDetail"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x05ee
            android.app.Activity r2 = r7.t
            dlq$4 r3 = new dlq$4
            r3.<init>(r1)
            r2.runOnUiThread(r3)
            goto L_0x0843
        L_0x05ee:
            java.lang.String r3 = "VoiceSearch"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 != 0) goto L_0x0843
            java.lang.String r3 = "OpenTraffic"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0616
            com.autonavi.bundle.amaphome.api.MapHomeIntentAction r1 = com.autonavi.bundle.amaphome.api.MapHomeIntentAction.OPEN_TRAFFIC_HELP
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            java.lang.String r3 = "key_action"
            java.lang.String r4 = "action_base_map_scheme"
            r2.putString(r3, r4)
            java.lang.String r3 = "key_scheme_feature"
            r2.putObject(r3, r1)
            a(r2)
            goto L_0x0843
        L_0x0616:
            java.lang.String r3 = "Account"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0645
            esb r1 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r2 = com.autonavi.bundle.account.api.IAccountService.class
            esc r1 = r1.a(r2)
            com.autonavi.bundle.account.api.IAccountService r1 = (com.autonavi.bundle.account.api.IAccountService) r1
            if (r1 == 0) goto L_0x0843
            boolean r2 = r1.a()
            if (r2 != 0) goto L_0x063c
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r3 = 0
            r1.a(r2, r3)
            goto L_0x0843
        L_0x063c:
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r1.b(r2)
            goto L_0x0843
        L_0x0645:
            java.lang.String r3 = "User"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0690
            esb r1 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r2 = com.autonavi.bundle.account.api.IAccountService.class
            esc r1 = r1.a(r2)
            com.autonavi.bundle.account.api.IAccountService r1 = (com.autonavi.bundle.account.api.IAccountService) r1
            if (r1 == 0) goto L_0x0843
            esb r2 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r3 = com.autonavi.bundle.account.api.IAccountService.class
            esc r2 = r2.a(r3)
            com.autonavi.bundle.account.api.IAccountService r2 = (com.autonavi.bundle.account.api.IAccountService) r2
            if (r2 != 0) goto L_0x066c
            java.lang.String r2 = ""
            goto L_0x0677
        L_0x066c:
            ant r2 = r2.e()
            if (r2 != 0) goto L_0x0675
            java.lang.String r2 = ""
            goto L_0x0677
        L_0x0675:
            java.lang.String r2 = r2.a
        L_0x0677:
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0686
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r1.b(r2)
            goto L_0x0843
        L_0x0686:
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r3 = 0
            r1.a(r2, r3)
            goto L_0x0843
        L_0x0690:
            java.lang.String r3 = "Wallet"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x06a7
            java.lang.Class<cpq> r1 = defpackage.cpq.class
            java.lang.Object r1 = defpackage.ank.a(r1)
            cpq r1 = (defpackage.cpq) r1
            if (r1 == 0) goto L_0x0843
            r1.a()
            goto L_0x0843
        L_0x06a7:
            java.lang.String r3 = "Mine"
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0841
            java.lang.String r2 = "page"
            java.lang.String r2 = r1.getQueryParameter(r2)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x07bc
            java.lang.String r3 = "ToolBox"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x07bc
            java.lang.String r2 = "item"
            java.lang.String r1 = r1.getQueryParameter(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x07b9
            java.lang.String r2 = "ElectronicEye"
            boolean r2 = r1.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x06e7
            bid r1 = r26.getPageContext()
            java.lang.String r2 = "amap.drive.action.edog"
            com.autonavi.common.PageBundle r3 = new com.autonavi.common.PageBundle
            r3.<init>()
            r1.startPage(r2, r3)
            goto L_0x07b9
        L_0x06e7:
            java.lang.String r2 = "Traffic"
            boolean r2 = r1.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x06fb
            bid r1 = r26.getPageContext()
            java.lang.String r2 = "amap.drive.action.traffic.remind"
            r3 = 0
            r1.startPage(r2, r3)
            goto L_0x07b9
        L_0x06fb:
            java.lang.String r2 = "Violation"
            boolean r2 = r1.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x073a
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r1 == 0) goto L_0x07b9
            boolean r1 = b()
            if (r1 == 0) goto L_0x071e
            java.lang.Class<com.autonavi.minimap.basemap.traffic.inter.ICarILlegalUtil> r1 = com.autonavi.minimap.basemap.traffic.inter.ICarILlegalUtil.class
            java.lang.Object r1 = defpackage.ank.a(r1)
            com.autonavi.minimap.basemap.traffic.inter.ICarILlegalUtil r1 = (com.autonavi.minimap.basemap.traffic.inter.ICarILlegalUtil) r1
            if (r1 == 0) goto L_0x07b9
            r1.a()
            goto L_0x07b9
        L_0x071e:
            esb r1 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r2 = com.autonavi.bundle.account.api.IAccountService.class
            esc r1 = r1.a(r2)
            com.autonavi.bundle.account.api.IAccountService r1 = (com.autonavi.bundle.account.api.IAccountService) r1
            if (r1 == 0) goto L_0x07b9
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            dlq$6 r3 = new dlq$6
            r3.<init>()
            r1.a(r2, r3)
            goto L_0x07b9
        L_0x073a:
            java.lang.String r2 = "Subway"
            boolean r2 = r1.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x0748
            r2 = 0
            r7.a(r2)
            goto L_0x07b9
        L_0x0748:
            java.lang.String r2 = "Maincollect"
            boolean r2 = r1.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x0788
            java.lang.String r1 = "com.sh.paipai"
            boolean r2 = defpackage.ahp.a(r1)
            if (r2 == 0) goto L_0x075e
            android.app.Activity r2 = r7.t
            defpackage.ahp.b(r2, r1)
            goto L_0x07b9
        L_0x075e:
            com.amap.bundle.blutils.app.ConfigerHelper r1 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r2 = "gongjiaopaipai_url"
            java.lang.String r1 = r1.getKeyValue(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x07b9
            android.content.Intent r2 = new android.content.Intent     // Catch:{ ActivityNotFoundException -> 0x0782 }
            java.lang.String r3 = "android.intent.action.VIEW"
            r2.<init>(r3)     // Catch:{ ActivityNotFoundException -> 0x0782 }
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch:{ ActivityNotFoundException -> 0x0782 }
            r2.setData(r1)     // Catch:{ ActivityNotFoundException -> 0x0782 }
            android.app.Activity r1 = r7.t     // Catch:{ ActivityNotFoundException -> 0x0782 }
            r1.startActivity(r2)     // Catch:{ ActivityNotFoundException -> 0x0782 }
            goto L_0x07b9
        L_0x0782:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            goto L_0x07b9
        L_0x0788:
            java.lang.String r2 = "AutonaviGold"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x07d6
            java.lang.String r1 = "com.autonavi.gxdtaojin"
            boolean r2 = defpackage.ahp.a(r1)
            if (r2 == 0) goto L_0x079e
            android.app.Activity r2 = r7.t
            defpackage.ahp.b(r2, r1)
            goto L_0x07b9
        L_0x079e:
            android.content.Intent r1 = new android.content.Intent     // Catch:{ ActivityNotFoundException -> 0x07b4 }
            java.lang.String r2 = "android.intent.action.VIEW"
            r1.<init>(r2)     // Catch:{ ActivityNotFoundException -> 0x07b4 }
            java.lang.String r2 = "https://wap.amap.com/gxd/index.html"
            android.net.Uri r2 = android.net.Uri.parse(r2)     // Catch:{ ActivityNotFoundException -> 0x07b4 }
            r1.setData(r2)     // Catch:{ ActivityNotFoundException -> 0x07b4 }
            android.app.Activity r2 = r7.t     // Catch:{ ActivityNotFoundException -> 0x07b4 }
            r2.startActivity(r1)     // Catch:{ ActivityNotFoundException -> 0x07b4 }
            goto L_0x07b9
        L_0x07b4:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L_0x07b9:
            r1 = 1
            goto L_0x083f
        L_0x07bc:
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x07e6
            java.lang.String r3 = "Fortune"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x07e6
            java.lang.String r2 = "item"
            java.lang.String r1 = r1.getQueryParameter(r2)
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x07d8
        L_0x07d6:
            r1 = 0
            goto L_0x083f
        L_0x07d8:
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r2 = com.autonavi.minimap.R.string.intent_open_fail
            java.lang.String r1 = r1.getString(r2)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r1)
            goto L_0x07b9
        L_0x07e6:
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x080c
            java.lang.String r3 = "DrivingZone"
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x080c
            java.lang.String r2 = "item"
            java.lang.String r1 = r1.getQueryParameter(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x07b9
            java.lang.String r2 = "List"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x07d6
            a()
            goto L_0x07b9
        L_0x080c:
            bid r1 = r26.getPageContext()
            if (r1 != 0) goto L_0x0813
            goto L_0x07d6
        L_0x0813:
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r1 == 0) goto L_0x07b9
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle
            r2.<init>()
            java.lang.String r3 = "jsData"
            bnv r4 = new bnv
            r4.<init>()
            java.lang.String r4 = defpackage.bnv.b()
            r2.putString(r3, r4)
            esb r3 = defpackage.esb.a.a
            java.lang.Class<apr> r4 = defpackage.apr.class
            esc r3 = r3.a(r4)
            apr r3 = (defpackage.apr) r3
            if (r3 == 0) goto L_0x07b9
            r3.b(r1, r2)
            goto L_0x07b9
        L_0x083f:
            if (r1 != 0) goto L_0x0843
        L_0x0841:
            r1 = 0
            goto L_0x0844
        L_0x0843:
            r1 = 1
        L_0x0844:
            if (r1 != 0) goto L_0x0848
            r1 = 0
            return r1
        L_0x0848:
            return r9
        L_0x0849:
            r1 = 0
            return r1
        L_0x084b:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dlq.a(android.content.Intent):boolean");
    }

    private static void a() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", Ajx3Path.DRIVE_NEWS_PATH);
            pageContext.startPage(Ajx3Page.class, pageBundle);
        }
    }

    /* access modifiers changed from: private */
    public void a(dct dct, HashMap<String, dcq> hashMap, Bitmap bitmap, Bitmap bitmap2) {
        final dcq dcq = hashMap.get("weibo");
        final dcq dcq2 = hashMap.get("weixin");
        final dcq dcq3 = hashMap.get("pengyou");
        dcb dcb = (dcb) a.a.a(dcb.class);
        if (dcb != null) {
            final dcb dcb2 = dcb;
            final Bitmap bitmap3 = bitmap;
            final Bitmap bitmap4 = bitmap2;
            AnonymousClass2 r1 = new dcd() {
                public final ShareParam getShareDataByType(int i) {
                    switch (i) {
                        case 3:
                            e eVar = new e(0);
                            eVar.f = dcq2.title;
                            eVar.a = dcq2.content;
                            if (!TextUtils.isEmpty(dcq2.imgUrl)) {
                                eVar.g = bitmap3;
                            } else {
                                eVar.g = dcq2.imgBitmap;
                            }
                            eVar.b = dcb2.a(dcq2);
                            eVar.c = dcq2.needToShortUrl;
                            eVar.e = 0;
                            return eVar;
                        case 4:
                            e eVar2 = new e(1);
                            eVar2.f = dcq3.title;
                            eVar2.a = dcq3.content;
                            if (!TextUtils.isEmpty(dcq3.imgUrl)) {
                                eVar2.g = bitmap4;
                            } else {
                                eVar2.g = dcq3.imgBitmap;
                            }
                            eVar2.b = dcb2.a(dcq3);
                            eVar2.c = dcq3.needToShortUrl;
                            eVar2.e = 0;
                            return eVar2;
                        case 5:
                            f fVar = new f();
                            fVar.a = dcq.content;
                            fVar.h = dcq.imgUrl;
                            fVar.b = dcb2.a(dcq);
                            fVar.c = dcq.needToShortUrl;
                            return fVar;
                        default:
                            return null;
                    }
                }
            };
            dcb.a(dct, (dcd) r1);
        }
    }

    public static void a(PageBundle pageBundle) {
        apr apr = (apr) a.a.a(apr.class);
        if (apr != null) {
            apr.a(AMapPageUtil.getPageContext(), pageBundle);
        }
    }

    private void a(Uri uri) {
        bdk bdk = (bdk) a.a.a(bdk.class);
        if (uri == null) {
            if (bdk != null) {
                bdk.a(this.t, null);
            }
            return;
        }
        String queryParameter = uri.getQueryParameter(AutoJsonUtils.JSON_ADCODE);
        if (bdk != null) {
            bdk.a(this.t, queryParameter);
        }
    }

    private static boolean b() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    static /* synthetic */ void a(dlq dlq) {
        if (dlq.r != null) {
            dlq.r.dismiss();
            dlq.r = null;
        }
    }
}
