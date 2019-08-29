package defpackage;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.accs.common.Constants;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.common.MtopNetworkProp;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.features.MtopFeatureManager;
import mtopsdk.mtop.global.SDKUtils;
import mtopsdk.mtop.intf.Mtop;
import mtopsdk.xstate.network.NetworkStateReceiver;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ffo reason: default package */
/* compiled from: ProductProtocolParamBuilderImpl */
public final class ffo implements ffl {
    private ffd a = null;

    public final Map<String, String> a(fdf fdf) {
        fdf fdf2 = fdf;
        long currentTimeMillis = System.currentTimeMillis();
        Mtop mtop = fdf2.a;
        String str = mtop.b;
        this.a = mtop.c;
        fgr fgr = this.a.l;
        if (fgr == null) {
            String str2 = fdf2.h;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" ISign of mtopConfig in mtopInstance is null");
            TBSdkLog.d("mtopsdk.ProductProtocolParamBuilderImpl", str2, sb.toString());
            return null;
        }
        MtopRequest mtopRequest = fdf2.b;
        MtopNetworkProp mtopNetworkProp = fdf2.d;
        HashMap hashMap = new HashMap();
        hashMap.put("data", mtopRequest.getData());
        if (fdd.a(mtopNetworkProp.reqBizExt)) {
            hashMap.put("reqbiz-ext", mtopNetworkProp.reqBizExt);
        }
        long a2 = MtopFeatureManager.a(mtop);
        if (mtopNetworkProp.reqSource > 0) {
            a2 |= MtopFeatureManager.a(11);
        }
        hashMap.put("x-features", String.valueOf(a2));
        String a3 = fgy.a((String) "lat");
        if (fdd.a(a3)) {
            String a4 = fgy.a((String) CameraControllerManager.MY_POILOCATION_LNG);
            if (fdd.a(a4)) {
                hashMap.put("lat", a3);
                hashMap.put(CameraControllerManager.MY_POILOCATION_LNG, a4);
            }
        }
        hashMap.put("pv", "1.0");
        hashMap.put("utdid", Mtop.d());
        hashMap.put(Oauth2AccessToken.KEY_UID, fdd.a(mtopNetworkProp.reqUserId) ? mtopNetworkProp.reqUserId : mtop.d(mtopNetworkProp.userInfo));
        if (fdd.b(mtopNetworkProp.reqAppKey)) {
            mtopNetworkProp.reqAppKey = this.a.j;
            mtopNetworkProp.authCode = this.a.h;
        }
        String str3 = mtopNetworkProp.reqAppKey;
        String str4 = mtopNetworkProp.authCode;
        hashMap.put("appKey", str3);
        String valueOf = String.valueOf(SDKUtils.getCorrectionTime());
        hashMap.put(LogItem.MM_C15_K4_TIME, valueOf);
        hashMap.put(MtopJSParam.API, mtopRequest.getApiName().toLowerCase(Locale.US));
        hashMap.put("v", mtopRequest.getVersion().toLowerCase(Locale.US));
        hashMap.put(Constants.KEY_SID, mtop.c(mtopNetworkProp.userInfo));
        hashMap.put("ttid", mtopNetworkProp.ttid);
        long currentTimeMillis2 = System.currentTimeMillis();
        String a5 = fgr.a(hashMap, str3, str4);
        fdf2.g.g = System.currentTimeMillis() - currentTimeMillis2;
        if (fdd.b(a5)) {
            StringBuilder sb2 = new StringBuilder(128);
            sb2.append("apiKey=");
            sb2.append(mtopRequest.getKey());
            sb2.append(" call getMtopApiSign failed.[appKey=");
            sb2.append(str3);
            sb2.append(", authCode=");
            sb2.append(str4);
            sb2.append("]");
            TBSdkLog.d("mtopsdk.ProductProtocolParamBuilderImpl", fdf2.h, sb2.toString());
            return hashMap;
        }
        hashMap.put("sign", a5);
        if (mtopNetworkProp.wuaFlag >= 0) {
            long currentTimeMillis3 = System.currentTimeMillis();
            String a6 = fgr.a(valueOf, str3, str4, mtopNetworkProp.wuaFlag);
            fdf2.g.h = System.currentTimeMillis() - currentTimeMillis3;
            hashMap.put("wua", a6);
            if (fdd.b(a6) && TBSdkLog.a(LogEnable.ErrorEnable)) {
                String str5 = fdf2.h;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(mtopRequest.getKey());
                sb3.append(" call getSecurityBodyDataEx for wua failed.");
                TBSdkLog.d("mtopsdk.ProductProtocolParamBuilderImpl", str5, sb3.toString());
            }
        }
        long currentTimeMillis4 = System.currentTimeMillis();
        String a7 = fgr.a(valueOf, str3, str4, 8);
        fdf2.g.i = System.currentTimeMillis() - currentTimeMillis4;
        hashMap.put("x-mini-wua", a7);
        if (fdd.b(a7)) {
            String str6 = fdf2.h;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(mtopRequest.getKey());
            sb4.append(" call getSecurityBodyDataEx for mini_wua failed.");
            TBSdkLog.d("mtopsdk.ProductProtocolParamBuilderImpl", str6, sb4.toString());
        }
        MtopNetworkProp mtopNetworkProp2 = fdf2.d;
        hashMap.put("netType", fgy.a((String) "netType"));
        hashMap.put("nq", fgy.a((String) "nq"));
        hashMap.put("umt", fgy.a(fdf2.a.b, "umt"));
        String str7 = this.a.o;
        if (fdd.a(str7)) {
            hashMap.put("x-app-ver", str7);
        }
        String str8 = this.a.r;
        if (fdd.a(str8)) {
            hashMap.put("x-orange-q", str8);
        }
        String a8 = fgy.a((String) com.ali.auth.third.core.model.Constants.UA);
        if (a8 != null) {
            hashMap.put(MtopJSParam.USER_AGENT, a8);
        }
        hashMap.put("x-c-traceid", mtopNetworkProp2.clientTraceId);
        hashMap.put("f-refer", "mtop");
        if (mtopNetworkProp2.netParam > 0) {
            JSONObject jSONObject = new JSONObject();
            if ((mtopNetworkProp2.netParam & 1) != 0) {
                String str9 = NetworkStateReceiver.c;
                if (!TextUtils.isEmpty(str9)) {
                    try {
                        jSONObject.put("SSID", str9);
                    } catch (JSONException e) {
                        TBSdkLog.a((String) "mtopsdk.ProductProtocolParamBuilderImpl", (String) "set wifi ssid error.", (Throwable) e);
                    }
                }
            }
            if ((mtopNetworkProp2.netParam & 2) != 0) {
                String str10 = NetworkStateReceiver.d;
                if (!TextUtils.isEmpty(str10)) {
                    try {
                        jSONObject.put("BSSID", str10);
                    } catch (JSONException e2) {
                        TBSdkLog.a((String) "mtopsdk.ProductProtocolParamBuilderImpl", (String) "set wifi bssid error.", (Throwable) e2);
                    }
                }
            }
            if (jSONObject.length() > 0) {
                hashMap.put("x-netinfo", jSONObject.toString());
            }
        }
        fdf2.g.f = System.currentTimeMillis() - currentTimeMillis;
        return hashMap;
    }
}
