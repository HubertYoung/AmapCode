package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
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

/* renamed from: ffn reason: default package */
/* compiled from: OpenProtocolParamBuilderImpl */
public final class ffn implements ffl {
    public final Map<String, String> a(fdf fdf) {
        fdf fdf2 = fdf;
        long currentTimeMillis = System.currentTimeMillis();
        String str = fdf2.a.b;
        ffd ffd = fdf2.a.c;
        if (ffd.l == null) {
            String str2 = fdf2.h;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" [buildParams] ISign in mtopConfig of mtopInstance  is null");
            TBSdkLog.d("mtopsdk.OpenProtocolParamBuilderImpl", str2, sb.toString());
            return null;
        }
        MtopRequest mtopRequest = fdf2.b;
        MtopNetworkProp mtopNetworkProp = fdf2.d;
        Mtop mtop = fdf2.a;
        HashMap hashMap = new HashMap();
        hashMap.put(MtopJSParam.API, mtopRequest.getApiName().toLowerCase(Locale.US));
        hashMap.put("v", mtopRequest.getVersion().toLowerCase(Locale.US));
        hashMap.put("data", mtopRequest.getData());
        if (fdd.b(mtopNetworkProp.reqAppKey)) {
            mtopNetworkProp.reqAppKey = ffd.j;
            mtopNetworkProp.authCode = ffd.h;
        }
        String str3 = mtopNetworkProp.reqAppKey;
        String str4 = mtopNetworkProp.authCode;
        hashMap.put("appKey", str3);
        hashMap.put("accessToken", fgy.a(fdd.a(mtop.b, mtopNetworkProp.openAppKey), "accessToken"));
        String valueOf = String.valueOf(SDKUtils.getCorrectionTime());
        hashMap.put(LogItem.MM_C15_K4_TIME, valueOf);
        hashMap.put("utdid", Mtop.d());
        hashMap.put("pv", "1.0");
        hashMap.put("x-features", String.valueOf(MtopFeatureManager.a(mtop)));
        hashMap.put("ttid", mtopNetworkProp.ttid);
        hashMap.put(Constants.KEY_SID, mtop.c(mtopNetworkProp.userInfo));
        fgr fgr = ffd.l;
        if (mtopNetworkProp.wuaFlag >= 0) {
            long currentTimeMillis2 = System.currentTimeMillis();
            String str5 = ffd.i;
            String a = fgr.a(valueOf, str3, str5, mtopNetworkProp.wuaFlag);
            fdf2.g.h = System.currentTimeMillis() - currentTimeMillis2;
            hashMap.put("wua", a);
            if (fdd.b(a) && TBSdkLog.a(LogEnable.ErrorEnable)) {
                StringBuilder sb2 = new StringBuilder(128);
                sb2.append("apiKey=");
                sb2.append(mtopRequest.getKey());
                sb2.append(" call getSecurityBodyDataEx fail, wua is null.[appKey=");
                sb2.append(str3);
                sb2.append(", wuaAuthCode=");
                sb2.append(str5);
                sb2.append("]");
                TBSdkLog.d("mtopsdk.OpenProtocolParamBuilderImpl", fdf2.h, sb2.toString());
            }
        }
        long currentTimeMillis3 = System.currentTimeMillis();
        String a2 = fgr.a(hashMap, str3, str4);
        fdf2.g.g = System.currentTimeMillis() - currentTimeMillis3;
        if (fdd.b(a2)) {
            StringBuilder sb3 = new StringBuilder(128);
            sb3.append("apiKey=");
            sb3.append(mtopRequest.getKey());
            sb3.append(" call getMtopApiSign failed.[appKey=");
            sb3.append(str3);
            sb3.append(", authCode=");
            sb3.append(str4);
            sb3.append("]");
            TBSdkLog.d("mtopsdk.OpenProtocolParamBuilderImpl", fdf2.h, sb3.toString());
            return hashMap;
        }
        hashMap.put("sign", a2);
        String str6 = fdf2.a.c.o;
        if (fdd.a(str6)) {
            hashMap.put("x-app-ver", str6);
        }
        String a3 = fgy.a((String) com.ali.auth.third.core.model.Constants.UA);
        if (a3 != null) {
            hashMap.put(MtopJSParam.USER_AGENT, a3);
        }
        String a4 = fgy.a((String) "lat");
        if (fdd.a(a4)) {
            String a5 = fgy.a((String) CameraControllerManager.MY_POILOCATION_LNG);
            if (fdd.a(a5)) {
                hashMap.put("lat", a4);
                hashMap.put(CameraControllerManager.MY_POILOCATION_LNG, a5);
            }
        }
        fdf2.g.f = System.currentTimeMillis() - currentTimeMillis;
        return hashMap;
    }
}
