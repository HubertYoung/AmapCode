package defpackage;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Value;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam.SendType;
import com.autonavi.minimap.bundle.share.entity.ShareParam.b;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import com.tencent.connect.common.Constants;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dcx reason: default package */
/* compiled from: ShareAction */
public class dcx extends vz {
    protected boolean a = false;
    private dcz b;

    /* JADX WARNING: Removed duplicated region for block: B:85:0x0344 A[Catch:{ Exception -> 0x036a }] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x035a A[Catch:{ Exception -> 0x036a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(org.json.JSONObject r25, defpackage.wa r26) {
        /*
            r24 = this;
            r1 = r25
            com.amap.bundle.jsadapter.JsAdapter r2 = r24.a()
            if (r2 != 0) goto L_0x0009
            return
        L_0x0009:
            java.lang.String r2 = "content"
            org.json.JSONArray r2 = r1.optJSONArray(r2)
            java.lang.String r3 = "urlType"
            int r3 = r1.optInt(r3)
            java.lang.String r4 = "useCustomUrl"
            int r4 = r1.optInt(r4)
            java.lang.String r5 = "caller"
            java.lang.String r5 = r1.optString(r5)
            java.lang.String r6 = "loadDirectly"
            int r6 = r1.optInt(r6)
            java.lang.String r7 = "hideLinkCopyBtn"
            java.lang.String r8 = "0"
            java.lang.String r12 = r1.optString(r7, r8)
            java.lang.String r7 = "hideMoreBtn"
            java.lang.String r8 = "0"
            java.lang.String r13 = r1.optString(r7, r8)
            java.lang.String r7 = "callbackcase"
            r8 = 0
            int r15 = r1.optInt(r7, r8)
            java.lang.String r7 = "appfrom"
            java.lang.String r9 = ""
            java.lang.String r7 = r1.optString(r7, r9)
            r9 = 1
            if (r3 != r9) goto L_0x004b
            r3 = 1
            goto L_0x004c
        L_0x004b:
            r3 = 0
        L_0x004c:
            if (r2 == 0) goto L_0x01a7
            int r10 = r2.length()
            if (r10 <= 0) goto L_0x01a7
            java.util.HashMap r10 = new java.util.HashMap
            r10.<init>()
            dct r14 = new dct
            r14.<init>(r8)
            r1 = 0
        L_0x005f:
            int r11 = r2.length()
            if (r1 >= r11) goto L_0x0197
            org.json.JSONObject r11 = r2.optJSONObject(r1)
            if (r11 == 0) goto L_0x017a
            dcq r9 = new dcq
            r9.<init>()
            java.lang.String r8 = "type"
            java.lang.String r8 = r11.optString(r8)
            r16 = r2
            java.lang.String r2 = "title"
            java.lang.String r2 = r11.optString(r2)
            r17 = r15
            java.lang.String r15 = "message"
            java.lang.String r15 = r11.optString(r15)
            r18 = r13
            java.lang.String r13 = "url"
            java.lang.String r13 = r11.optString(r13)
            r19 = r12
            java.lang.String r12 = "imgUrl"
            java.lang.String r12 = r11.optString(r12)
            r20 = r1
            java.lang.String r1 = "shareType"
            r21 = r14
            java.lang.String r14 = "0"
            java.lang.String r1 = r11.optString(r1, r14)
            java.lang.String r14 = "isWord"
            r22 = r10
            r10 = 0
            int r14 = r11.optInt(r14, r10)
            r10 = 1
            if (r14 != r10) goto L_0x00e0
            dcs r10 = new dcs
            r10.<init>()
            r23 = r8
            java.lang.String r8 = "shortMessage"
            java.lang.String r8 = r11.optString(r8)
            r10.a = r8
            java.lang.String r8 = "backgroundColor"
            java.lang.String r8 = r11.optString(r8)
            r10.b = r8
            java.lang.String r8 = "buttonColor"
            java.lang.String r8 = r11.optString(r8)
            r10.c = r8
            java.lang.String r8 = "fontColor"
            java.lang.String r8 = r11.optString(r8)
            r10.d = r8
            java.lang.String r8 = "activityId"
            java.lang.String r8 = r11.optString(r8)
            r10.e = r8
            r9.sharePassphraseInfo = r10
            goto L_0x00e2
        L_0x00e0:
            r23 = r8
        L_0x00e2:
            r9.isWord = r14
            r9.title = r2
            r9.content = r15
            r9.url = r13
            r9.useCustomUrl = r4
            r9.needToShortUrl = r3
            r9.fromSource = r7
            r9.imgUrl = r12
            r9.caller = r5
            r9.loadDirectly = r6
            r9.picOrWord = r1
            java.lang.String r1 = "weibo"
            r2 = r23
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0113
            r1 = 5
            r9.shareType = r1
            java.lang.String r1 = "weibo"
            r8 = r22
            r8.put(r1, r9)
            r1 = r21
            r10 = 1
            r1.f = r10
            goto L_0x0186
        L_0x0113:
            r1 = r21
            r8 = r22
            r10 = 1
            java.lang.String r11 = "weixin"
            boolean r11 = r11.equals(r2)
            if (r11 == 0) goto L_0x012b
            r2 = 3
            r9.shareType = r2
            java.lang.String r2 = "weixin"
            r8.put(r2, r9)
            r1.d = r10
            goto L_0x0186
        L_0x012b:
            java.lang.String r11 = "pengyou"
            boolean r11 = r11.equals(r2)
            if (r11 == 0) goto L_0x013e
            r2 = 4
            r9.shareType = r2
            java.lang.String r2 = "pengyou"
            r8.put(r2, r9)
            r1.e = r10
            goto L_0x0186
        L_0x013e:
            java.lang.String r11 = "qq"
            boolean r11 = r11.equals(r2)
            if (r11 == 0) goto L_0x0152
            r2 = 8
            r9.shareType = r2
            java.lang.String r2 = "qq"
            r8.put(r2, r9)
            r1.i = r10
            goto L_0x0186
        L_0x0152:
            java.lang.String r11 = "qzone"
            boolean r11 = r11.equals(r2)
            if (r11 == 0) goto L_0x0166
            r2 = 9
            r9.shareType = r2
            java.lang.String r2 = "qzone"
            r8.put(r2, r9)
            r1.j = r10
            goto L_0x0186
        L_0x0166:
            java.lang.String r11 = "dingding"
            boolean r2 = r11.equals(r2)
            if (r2 == 0) goto L_0x0186
            r2 = 11
            r9.shareType = r2
            java.lang.String r2 = "dingding"
            r8.put(r2, r9)
            r1.l = r10
            goto L_0x0186
        L_0x017a:
            r20 = r1
            r16 = r2
            r8 = r10
            r19 = r12
            r18 = r13
            r1 = r14
            r17 = r15
        L_0x0186:
            int r2 = r20 + 1
            r14 = r1
            r1 = r2
            r10 = r8
            r2 = r16
            r15 = r17
            r13 = r18
            r12 = r19
            r8 = 0
            r9 = 1
            goto L_0x005f
        L_0x0197:
            r8 = r10
            r19 = r12
            r18 = r13
            r1 = r14
            r17 = r15
            r9 = r24
            r11 = r26
            r9.a(r10, r11, r12, r13, r14, r15)
            return
        L_0x01a7:
            java.lang.String r2 = "type"
            java.lang.String r2 = r1.optString(r2)     // Catch:{ Exception -> 0x036a }
            java.lang.String r3 = "message"
            java.lang.String r3 = r1.optString(r3)     // Catch:{ Exception -> 0x036a }
            java.lang.String r4 = "needShortUrl"
            boolean r4 = r1.optBoolean(r4)     // Catch:{ Exception -> 0x036a }
            r5 = 0
            if (r4 == 0) goto L_0x01c2
            java.lang.String r5 = "poiInfo"
            org.json.JSONObject r5 = r1.optJSONObject(r5)     // Catch:{ Exception -> 0x036a }
        L_0x01c2:
            com.autonavi.common.model.POI r1 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()     // Catch:{ Exception -> 0x036a }
            if (r5 == 0) goto L_0x01d0
            java.lang.String r1 = r5.toString()     // Catch:{ Exception -> 0x036a }
            com.autonavi.common.model.POI r1 = defpackage.kv.a(r1)     // Catch:{ Exception -> 0x036a }
        L_0x01d0:
            java.lang.String r5 = "weibo"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x036a }
            if (r2 == 0) goto L_0x0367
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x036a }
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ Exception -> 0x036a }
            dct r5 = new dct     // Catch:{ Exception -> 0x036a }
            r6 = 0
            r5.<init>(r6)     // Catch:{ Exception -> 0x036a }
            r6 = 1
            r5.f = r6     // Catch:{ Exception -> 0x036a }
            r5.m = r6     // Catch:{ Exception -> 0x036a }
            com.autonavi.minimap.bundle.share.entity.ShareParam$f r6 = new com.autonavi.minimap.bundle.share.entity.ShareParam$f     // Catch:{ Exception -> 0x036a }
            r6.<init>()     // Catch:{ Exception -> 0x036a }
            java.lang.String r7 = ""
            java.lang.String r8 = r1.getId()     // Catch:{ Exception -> 0x036a }
            if (r8 == 0) goto L_0x0261
            java.lang.String r8 = r1.getId()     // Catch:{ Exception -> 0x036a }
            int r8 = r8.length()     // Catch:{ Exception -> 0x036a }
            if (r8 <= 0) goto L_0x0261
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r8 = "?p="
            r2.<init>(r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r8 = r1.getId()     // Catch:{ UnsupportedEncodingException -> 0x025a }
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r8 = ","
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            com.autonavi.common.model.GeoPoint r8 = r1.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x025a }
            double r8 = r8.getLatitude()     // Catch:{ UnsupportedEncodingException -> 0x025a }
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r8 = ","
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            com.autonavi.common.model.GeoPoint r8 = r1.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x025a }
            double r8 = r8.getLongitude()     // Catch:{ UnsupportedEncodingException -> 0x025a }
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r8 = ","
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r8 = r1.getName()     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r9 = "UTF-8"
            java.lang.String r8 = java.net.URLEncoder.encode(r8, r9)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r8 = ","
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r1 = r1.getAddr()     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r8 = "UTF-8"
            java.lang.String r1 = java.net.URLEncoder.encode(r1, r8)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            r2.append(r1)     // Catch:{ UnsupportedEncodingException -> 0x025a }
            java.lang.String r1 = r2.toString()     // Catch:{ UnsupportedEncodingException -> 0x025a }
            goto L_0x032b
        L_0x025a:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ Exception -> 0x036a }
            goto L_0x02fd
        L_0x0261:
            java.lang.String r8 = r1.getAddr()     // Catch:{ Exception -> 0x036a }
            if (r8 == 0) goto L_0x02b7
            java.lang.String r8 = r1.getName()     // Catch:{ Exception -> 0x036a }
            int r9 = com.autonavi.minimap.R.string.LocationMe     // Catch:{ Exception -> 0x036a }
            java.lang.String r2 = r2.getString(r9)     // Catch:{ Exception -> 0x036a }
            boolean r2 = r8.equals(r2)     // Catch:{ Exception -> 0x036a }
            if (r2 == 0) goto L_0x02b7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            java.lang.String r8 = "?q="
            r2.<init>(r8)     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            com.autonavi.common.model.GeoPoint r8 = r1.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            double r8 = r8.getLatitude()     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            java.lang.String r8 = ","
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            com.autonavi.common.model.GeoPoint r8 = r1.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            double r8 = r8.getLongitude()     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            java.lang.String r8 = ","
            r2.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            java.lang.String r1 = r1.getAddr()     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            java.lang.String r8 = "UTF-8"
            java.lang.String r1 = java.net.URLEncoder.encode(r1, r8)     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            r2.append(r1)     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            java.lang.String r1 = r2.toString()     // Catch:{ UnsupportedEncodingException -> 0x02b1 }
            goto L_0x032b
        L_0x02b1:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ Exception -> 0x036a }
            goto L_0x02fd
        L_0x02b7:
            java.lang.String r2 = r1.getName()     // Catch:{ Exception -> 0x036a }
            if (r2 == 0) goto L_0x02ff
            int r8 = r2.length()     // Catch:{ Exception -> 0x036a }
            if (r8 <= 0) goto L_0x02ff
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            java.lang.String r9 = "?q="
            r8.<init>(r9)     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            com.autonavi.common.model.GeoPoint r9 = r1.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            double r9 = r9.getLatitude()     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            r8.append(r9)     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            java.lang.String r9 = ","
            r8.append(r9)     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            com.autonavi.common.model.GeoPoint r1 = r1.getPoint()     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            double r9 = r1.getLongitude()     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            r8.append(r9)     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            java.lang.String r1 = ","
            r8.append(r1)     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            java.lang.String r1 = "UTF-8"
            java.lang.String r1 = java.net.URLEncoder.encode(r2, r1)     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            r8.append(r1)     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            java.lang.String r1 = r8.toString()     // Catch:{ UnsupportedEncodingException -> 0x02f8 }
            goto L_0x032b
        L_0x02f8:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ Exception -> 0x036a }
        L_0x02fd:
            r1 = r7
            goto L_0x032b
        L_0x02ff:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x036a }
            java.lang.String r7 = "?q="
            r2.<init>(r7)     // Catch:{ Exception -> 0x036a }
            com.autonavi.common.model.GeoPoint r7 = r1.getPoint()     // Catch:{ Exception -> 0x036a }
            double r7 = r7.getLatitude()     // Catch:{ Exception -> 0x036a }
            r2.append(r7)     // Catch:{ Exception -> 0x036a }
            java.lang.String r7 = ","
            r2.append(r7)     // Catch:{ Exception -> 0x036a }
            com.autonavi.common.model.GeoPoint r1 = r1.getPoint()     // Catch:{ Exception -> 0x036a }
            double r7 = r1.getLongitude()     // Catch:{ Exception -> 0x036a }
            r2.append(r7)     // Catch:{ Exception -> 0x036a }
            java.lang.String r1 = ","
            r2.append(r1)     // Catch:{ Exception -> 0x036a }
            java.lang.String r7 = r2.toString()     // Catch:{ Exception -> 0x036a }
            goto L_0x02fd
        L_0x032b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x036a }
            r2.<init>()     // Catch:{ Exception -> 0x036a }
            com.amap.bundle.blutils.app.ConfigerHelper r7 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()     // Catch:{ Exception -> 0x036a }
            java.lang.String r7 = r7.getShareMsgUrl()     // Catch:{ Exception -> 0x036a }
            r2.append(r7)     // Catch:{ Exception -> 0x036a }
            r2.append(r1)     // Catch:{ Exception -> 0x036a }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x036a }
            if (r4 != 0) goto L_0x0346
            java.lang.String r1 = ""
        L_0x0346:
            r6.a = r3     // Catch:{ Exception -> 0x036a }
            r6.b = r1     // Catch:{ Exception -> 0x036a }
            r6.c = r4     // Catch:{ Exception -> 0x036a }
            esb r1 = defpackage.esb.a.a     // Catch:{ Exception -> 0x036a }
            java.lang.Class<dcb> r2 = defpackage.dcb.class
            esc r1 = r1.a(r2)     // Catch:{ Exception -> 0x036a }
            dcb r1 = (defpackage.dcb) r1     // Catch:{ Exception -> 0x036a }
            if (r1 == 0) goto L_0x0367
            dcx$3 r2 = new dcx$3     // Catch:{ Exception -> 0x036a }
            r3 = r24
            r2.<init>(r6)     // Catch:{ Exception -> 0x0365 }
            r1.a(r5, r2)     // Catch:{ Exception -> 0x0365 }
            goto L_0x0369
        L_0x0365:
            r0 = move-exception
            goto L_0x036d
        L_0x0367:
            r3 = r24
        L_0x0369:
            return
        L_0x036a:
            r0 = move-exception
            r3 = r24
        L_0x036d:
            r1 = r0
            defpackage.kf.a(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dcx.a(org.json.JSONObject, wa):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0094  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.util.HashMap<java.lang.String, defpackage.dcq> r13, defpackage.wa r14, java.lang.String r15, java.lang.String r16, defpackage.dct r17, int r18) {
        /*
            r12 = this;
            r9 = r12
            r6 = r13
            r5 = r17
            android.content.Context r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            boolean r1 = defpackage.aaw.c(r1)
            if (r1 != 0) goto L_0x001c
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r2 = com.autonavi.minimap.R.string.network_error_msg
            java.lang.String r1 = r1.getString(r2)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r1)
            return
        L_0x001c:
            int r1 = r6.size()
            int[] r1 = new int[r1]
            int r2 = r6.size()
            dcq[] r2 = new defpackage.dcq[r2]
            java.util.Collection r3 = r6.values()
            java.util.Iterator r3 = r3.iterator()
            r4 = 0
            r7 = 0
        L_0x0032:
            boolean r8 = r3.hasNext()
            r10 = 1
            if (r8 == 0) goto L_0x0047
            java.lang.Object r8 = r3.next()
            dcq r8 = (defpackage.dcq) r8
            int r11 = r8.shareType
            r1[r7] = r11
            r2[r7] = r8
            int r7 = r7 + r10
            goto L_0x0032
        L_0x0047:
            int r1 = r1.length
            if (r1 != r10) goto L_0x004f
            r1 = r2[r4]
            int r1 = r1.loadDirectly
            goto L_0x0050
        L_0x004f:
            r1 = 0
        L_0x0050:
            if (r1 != 0) goto L_0x0067
            java.lang.String r2 = "1"
            r3 = r15
            boolean r2 = r3.contentEquals(r2)
            r2 = r2 ^ r10
            java.lang.String r3 = "1"
            r7 = r16
            boolean r3 = r7.contentEquals(r3)
            if (r3 == 0) goto L_0x0065
            goto L_0x0068
        L_0x0065:
            r3 = 1
            goto L_0x0069
        L_0x0067:
            r2 = 0
        L_0x0068:
            r3 = 0
        L_0x0069:
            if (r2 == 0) goto L_0x006d
            r5.g = r10
        L_0x006d:
            if (r3 == 0) goto L_0x0071
            r5.h = r10
        L_0x0071:
            if (r1 != r10) goto L_0x0075
            r1 = 1
            goto L_0x0076
        L_0x0075:
            r1 = 0
        L_0x0076:
            r5.m = r1
            int r1 = r6.size()
            if (r1 != 0) goto L_0x0094
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r1 = r1.getApplicationContext()
            android.content.res.Resources r1 = r1.getResources()
            int r2 = com.autonavi.minimap.R.string.share_content_is_empty
            java.lang.String r1 = r1.getString(r2)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r1)
            return
        L_0x0094:
            java.lang.String r1 = "weixin"
            java.lang.Object r1 = r6.get(r1)
            r3 = r1
            dcq r3 = (defpackage.dcq) r3
            java.lang.String r1 = "pengyou"
            java.lang.Object r1 = r6.get(r1)
            r7 = r1
            dcq r7 = (defpackage.dcq) r7
            if (r3 == 0) goto L_0x00c4
            java.lang.String r1 = r3.picOrWord
            java.lang.String r2 = "0"
            boolean r1 = r1.contentEquals(r2)
            if (r1 != 0) goto L_0x00bc
            java.lang.String r1 = r3.picOrWord
            java.lang.String r2 = "1"
            boolean r1 = r1.contentEquals(r2)
            if (r1 == 0) goto L_0x00c4
        L_0x00bc:
            java.lang.String r1 = r3.imgUrl
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x00e2
        L_0x00c4:
            if (r7 == 0) goto L_0x012d
            java.lang.String r1 = r7.picOrWord
            java.lang.String r2 = "0"
            boolean r1 = r1.contentEquals(r2)
            if (r1 != 0) goto L_0x00da
            java.lang.String r1 = r7.picOrWord
            java.lang.String r2 = "1"
            boolean r1 = r1.contentEquals(r2)
            if (r1 == 0) goto L_0x012d
        L_0x00da:
            java.lang.String r1 = r7.imgUrl
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x012d
        L_0x00e2:
            java.lang.String r1 = "获取分享内容中.."
            dcz r2 = r9.b     // Catch:{ Exception -> 0x0119 }
            if (r2 != 0) goto L_0x0104
            dcz r2 = new dcz     // Catch:{ Exception -> 0x0119 }
            android.app.Activity r8 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()     // Catch:{ Exception -> 0x0119 }
            java.lang.String r11 = ""
            r2.<init>(r8, r1, r11)     // Catch:{ Exception -> 0x0119 }
            r9.b = r2     // Catch:{ Exception -> 0x0119 }
            dcz r2 = r9.b     // Catch:{ Exception -> 0x0119 }
            r2.setCancelable(r10)     // Catch:{ Exception -> 0x0119 }
            dcz r2 = r9.b     // Catch:{ Exception -> 0x0119 }
            dcx$4 r8 = new dcx$4     // Catch:{ Exception -> 0x0119 }
            r8.<init>()     // Catch:{ Exception -> 0x0119 }
            r2.setOnCancelListener(r8)     // Catch:{ Exception -> 0x0119 }
        L_0x0104:
            r9.a = r4     // Catch:{ Exception -> 0x0119 }
            dcz r2 = r9.b     // Catch:{ Exception -> 0x0119 }
            r2.a(r1)     // Catch:{ Exception -> 0x0119 }
            dcz r1 = r9.b     // Catch:{ Exception -> 0x0119 }
            boolean r1 = r1.isShowing()     // Catch:{ Exception -> 0x0119 }
            if (r1 != 0) goto L_0x011e
            dcz r1 = r9.b     // Catch:{ Exception -> 0x0119 }
            r1.show()     // Catch:{ Exception -> 0x0119 }
            goto L_0x011e
        L_0x0119:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L_0x011e:
            dcx$1 r10 = new dcx$1
            r1 = r10
            r2 = r9
            r4 = r7
            r7 = r14
            r8 = r18
            r1.<init>(r3, r4, r5, r6, r7, r8)
            defpackage.ahm.a(r10)
            return
        L_0x012d:
            r7 = 0
            r8 = 0
            r1 = r9
            r2 = r5
            r3 = r6
            r4 = r14
            r5 = r18
            r6 = r7
            r7 = r8
            r1.a(r2, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dcx.a(java.util.HashMap, wa, java.lang.String, java.lang.String, dct, int):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a3 A[Catch:{ Exception -> 0x00b2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ae  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap b(defpackage.dcq r6) throws java.lang.Exception {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x00b4
            java.lang.String r1 = r6.imgUrl
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x00b4
            java.lang.String r1 = "1"
            java.lang.String r2 = r6.picOrWord     // Catch:{ Exception -> 0x00b2 }
            boolean r1 = r1.contentEquals(r2)     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r2 = r6.imgUrl     // Catch:{ Exception -> 0x00b2 }
            r3 = r1 ^ 1
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x00b2 }
            if (r4 == 0) goto L_0x0020
        L_0x001d:
            r2 = r0
            goto L_0x0099
        L_0x0020:
            java.lang.String r4 = "file"
            boolean r4 = r2.startsWith(r4)     // Catch:{ Exception -> 0x00b2 }
            r5 = 2
            if (r4 != 0) goto L_0x005b
            java.lang.String r4 = "/"
            boolean r4 = r2.startsWith(r4)     // Catch:{ Exception -> 0x00b2 }
            if (r4 == 0) goto L_0x0032
            goto L_0x005b
        L_0x0032:
            if (r3 == 0) goto L_0x004d
            android.graphics.BitmapFactory$Options r3 = new android.graphics.BitmapFactory$Options     // Catch:{ Exception -> 0x00b2 }
            r3.<init>()     // Catch:{ Exception -> 0x00b2 }
            r3.inSampleSize = r5     // Catch:{ Exception -> 0x00b2 }
            android.graphics.Bitmap$Config r4 = android.graphics.Bitmap.Config.ARGB_4444     // Catch:{ Exception -> 0x00b2 }
            r3.inPreferredConfig = r4     // Catch:{ Exception -> 0x00b2 }
            java.net.URL r4 = new java.net.URL     // Catch:{ Exception -> 0x00b2 }
            r4.<init>(r2)     // Catch:{ Exception -> 0x00b2 }
            java.io.InputStream r2 = r4.openStream()     // Catch:{ Exception -> 0x00b2 }
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r2, r0, r3)     // Catch:{ Exception -> 0x00b2 }
            goto L_0x0099
        L_0x004d:
            java.net.URL r3 = new java.net.URL     // Catch:{ Exception -> 0x00b2 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x00b2 }
            java.io.InputStream r2 = r3.openStream()     // Catch:{ Exception -> 0x00b2 }
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r2, r0, r0)     // Catch:{ Exception -> 0x00b2 }
            goto L_0x0099
        L_0x005b:
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x00b2 }
            if (r4 != 0) goto L_0x006e
            java.lang.String r4 = "file://"
            boolean r4 = r2.startsWith(r4)     // Catch:{ Exception -> 0x00b2 }
            if (r4 == 0) goto L_0x006e
            r4 = 6
            java.lang.String r2 = r2.substring(r4)     // Catch:{ Exception -> 0x00b2 }
        L_0x006e:
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x00b2 }
            r4.<init>(r2)     // Catch:{ Exception -> 0x00b2 }
            boolean r2 = r4.exists()     // Catch:{ Exception -> 0x00b2 }
            if (r2 != 0) goto L_0x007a
            goto L_0x001d
        L_0x007a:
            if (r3 == 0) goto L_0x0091
            android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options     // Catch:{ Exception -> 0x00b2 }
            r2.<init>()     // Catch:{ Exception -> 0x00b2 }
            r2.inSampleSize = r5     // Catch:{ Exception -> 0x00b2 }
            android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.ARGB_4444     // Catch:{ Exception -> 0x00b2 }
            r2.inPreferredConfig = r3     // Catch:{ Exception -> 0x00b2 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00b2 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x00b2 }
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r3, r0, r2)     // Catch:{ Exception -> 0x00b2 }
            goto L_0x0099
        L_0x0091:
            java.lang.String r2 = r4.getPath()     // Catch:{ Exception -> 0x00b2 }
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeFile(r2)     // Catch:{ Exception -> 0x00b2 }
        L_0x0099:
            java.lang.String r3 = "0"
            java.lang.String r6 = r6.picOrWord     // Catch:{ Exception -> 0x00b2 }
            boolean r6 = r3.contentEquals(r6)     // Catch:{ Exception -> 0x00b2 }
            if (r6 == 0) goto L_0x00ae
            r6 = 150(0x96, float:2.1E-43)
            r0 = 1
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createScaledBitmap(r2, r6, r6, r0)     // Catch:{ Exception -> 0x00b2 }
            r2.recycle()     // Catch:{ Exception -> 0x00b2 }
            goto L_0x00b4
        L_0x00ae:
            if (r1 == 0) goto L_0x00b4
            r0 = r2
            goto L_0x00b4
        L_0x00b2:
            r6 = move-exception
            throw r6
        L_0x00b4:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dcx.b(dcq):android.graphics.Bitmap");
    }

    /* access modifiers changed from: private */
    public void a(dct dct, HashMap<String, dcq> hashMap, wa waVar, int i, Bitmap bitmap, Bitmap bitmap2) {
        HashMap<String, dcq> hashMap2 = hashMap;
        final dcq dcq = hashMap2.get("weibo");
        final dcq dcq2 = hashMap2.get("weixin");
        final dcq dcq3 = hashMap2.get("pengyou");
        final dcq dcq4 = hashMap2.get("qq");
        final dcq dcq5 = hashMap2.get(Constants.SOURCE_QZONE);
        final dcq dcq6 = hashMap2.get("dingding");
        dcb dcb = (dcb) a.a.a(dcb.class);
        if (dcb != null) {
            final int i2 = i;
            final wa waVar2 = waVar;
            final dcb dcb2 = dcb;
            final Bitmap bitmap3 = bitmap;
            final Bitmap bitmap4 = bitmap2;
            AnonymousClass2 r2 = new dcd() {
                public final void onFinish(int i2, int i3) {
                    if (i2 == 0) {
                        String str = "";
                        switch (i2) {
                            case 3:
                                str = "weixin";
                                break;
                            case 4:
                                str = "pengyou";
                                break;
                            case 5:
                                str = "weibo";
                                break;
                            case 8:
                                str = "qq";
                                break;
                            case 9:
                                str = Constants.SOURCE_QZONE;
                                break;
                            case 11:
                                str = "dingding";
                                break;
                        }
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("_action", waVar2.b);
                            jSONObject.put("type", str);
                            if (i3 == 0) {
                                jSONObject.put("result", Value.OK);
                            } else {
                                jSONObject.put("result", UploadDataResult.FAIL_MSG);
                            }
                        } catch (JSONException e2) {
                            kf.a((Throwable) e2);
                        }
                        dcx.this.a().callJs(waVar2.a, jSONObject.toString());
                    }
                }

                public final ShareParam getShareDataByType(int i2) {
                    switch (i2) {
                        case 3:
                            if (dcq2 == null) {
                                return null;
                            }
                            e eVar = new e(0);
                            eVar.k = dcq2.isWord;
                            if (dcq2.isWord == 1) {
                                eVar.f = dcq2.title;
                                eVar.a = dcq2.content;
                                eVar.j = dcq2.sharePassphraseInfo;
                            } else if (dcq2.picOrWord.contentEquals("0")) {
                                eVar.f = dcq2.title;
                                eVar.a = dcq2.content;
                                if (!TextUtils.isEmpty(dcq2.imgUrl)) {
                                    eVar.g = bitmap3;
                                } else {
                                    eVar.g = dcq2.imgBitmap;
                                }
                                eVar.b = dcb2.a(dcq2);
                                eVar.c = dcq2.needToShortUrl;
                                eVar.d = dcq2.fromSource;
                                eVar.e = 0;
                            } else if (dcq2.picOrWord.contentEquals("1")) {
                                if (!TextUtils.isEmpty(dcq2.imgUrl)) {
                                    eVar.g = bitmap3;
                                }
                                eVar.e = 4;
                            } else if (!dcq2.picOrWord.contentEquals("2")) {
                                return null;
                            } else {
                                eVar.a = dcq2.content;
                                eVar.e = 1;
                            }
                            return eVar;
                        case 4:
                            if (dcq3 == null) {
                                return null;
                            }
                            e eVar2 = new e(1);
                            eVar2.k = dcq3.isWord;
                            if (dcq3.isWord == 1) {
                                eVar2.f = dcq3.title;
                                eVar2.a = dcq3.content;
                                eVar2.j = dcq3.sharePassphraseInfo;
                            } else if (dcq3.picOrWord.contentEquals("0")) {
                                eVar2.f = dcq3.title;
                                eVar2.a = dcq3.content;
                                if (!TextUtils.isEmpty(dcq3.imgUrl)) {
                                    eVar2.g = bitmap4;
                                } else {
                                    eVar2.g = dcq3.imgBitmap;
                                }
                                eVar2.b = dcb2.a(dcq3);
                                eVar2.c = dcq3.needToShortUrl;
                                eVar2.d = dcq3.fromSource;
                                eVar2.e = 0;
                            } else if (dcq3.picOrWord.contentEquals("1")) {
                                if (!TextUtils.isEmpty(dcq3.imgUrl)) {
                                    eVar2.g = bitmap4;
                                }
                                eVar2.e = 4;
                            } else if (!dcq3.picOrWord.contentEquals("2")) {
                                return null;
                            } else {
                                eVar2.a = dcq3.content;
                                eVar2.e = 1;
                            }
                            return eVar2;
                        case 5:
                            if (dcq == null) {
                                return null;
                            }
                            f fVar = new f();
                            fVar.a = dcq.content;
                            fVar.h = dcq.imgUrl;
                            fVar.g = dcq.title;
                            fVar.b = dcb2.a(dcq);
                            fVar.c = dcq.needToShortUrl;
                            fVar.d = dcq.fromSource;
                            return fVar;
                        case 8:
                            if (dcq4 == null) {
                                return null;
                            }
                            b bVar = new b(0);
                            bVar.e = dcq4.title;
                            bVar.a = dcq4.content;
                            bVar.f = dcq4.imgUrl;
                            bVar.b = dcb2.a(dcq4);
                            bVar.c = dcq4.needToShortUrl;
                            bVar.d = dcq4.fromSource;
                            return bVar;
                        case 9:
                            if (dcq5 == null) {
                                return null;
                            }
                            b bVar2 = new b(1);
                            bVar2.e = dcq5.title;
                            bVar2.a = dcq5.content;
                            bVar2.f = dcq5.imgUrl;
                            bVar2.b = dcb2.a(dcq5);
                            bVar2.c = dcq5.needToShortUrl;
                            bVar2.d = dcq5.fromSource;
                            return bVar2;
                        case 11:
                            if (dcq6 == null) {
                                return null;
                            }
                            DingDingParam dingDingParam = new DingDingParam();
                            dingDingParam.f = dcq6.title;
                            dingDingParam.a = dcq6.content;
                            dingDingParam.g = dcq6.imgBitmap;
                            dingDingParam.h = dcq6.imgUrl;
                            dingDingParam.b = dcb2.a(dcq6);
                            dingDingParam.c = dcq6.needToShortUrl;
                            dingDingParam.d = dcq6.fromSource;
                            if ("1".equals(dcq6.picOrWord) && !TextUtils.isEmpty(dcq6.imgUrl)) {
                                if (dcq6.imgUrl.startsWith("file")) {
                                    dingDingParam.e = SendType.LocalImage;
                                    dingDingParam.i = dcq6.imgUrl;
                                    dingDingParam.h = "";
                                } else {
                                    dingDingParam.e = SendType.OnlineImage;
                                    dingDingParam.h = dcq6.imgUrl;
                                    dingDingParam.i = "";
                                }
                            }
                            return dingDingParam;
                        default:
                            return null;
                    }
                }

                public final void onEntrySelected(int i2) {
                    String str = "";
                    if (i2 == 1) {
                        switch (i2) {
                            case 3:
                                str = "weixin";
                                break;
                            case 4:
                                str = "pengyou";
                                break;
                            case 5:
                                str = "weibo";
                                break;
                            case 8:
                                str = "qq";
                                break;
                            case 9:
                                str = Constants.SOURCE_QZONE;
                                break;
                            case 11:
                                str = "dingding";
                                break;
                        }
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("_action", waVar2.b);
                            jSONObject.put("type", str);
                        } catch (JSONException e2) {
                            kf.a((Throwable) e2);
                        }
                        dcx.this.a().callJs(waVar2.a, jSONObject.toString());
                    }
                }
            };
            dcb.a(dct, (dcd) r2);
        }
    }

    static /* synthetic */ void a(dcx dcx) {
        if (dcx.b != null) {
            dcx.b.dismiss();
            dcx.b = null;
        }
    }
}
