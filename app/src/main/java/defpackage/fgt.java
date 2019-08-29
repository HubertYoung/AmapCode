package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.accs.common.Constants;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.util.HashMap;
import mtopsdk.common.util.TBSdkLog;

/* renamed from: fgt reason: default package */
/* compiled from: LocalInnerSignImpl */
public final class fgt extends fgq {
    String c;
    String d;

    public final String a(String str, String str2, String str3, int i) {
        return null;
    }

    public final String a(a aVar) {
        return this.c;
    }

    public final String a(HashMap<String, String> hashMap, String str, String str2) {
        String str3;
        String str4;
        Throwable th;
        String str5;
        fgt fgt = this;
        HashMap<String, String> hashMap2 = hashMap;
        String str6 = str;
        String c2 = c();
        String str7 = null;
        if (str6 == null) {
            hashMap2.put("SG_ERROR_CODE", "AppKey is null");
            StringBuilder sb = new StringBuilder();
            sb.append(c2);
            sb.append(" [getMtopApiSign] AppKey is null.");
            TBSdkLog.d("mtopsdk.LocalInnerSignImpl", sb.toString());
            return null;
        }
        try {
            if (hashMap.size() <= 0) {
                str4 = c2;
            } else {
                String str8 = hashMap2.get("utdid");
                String str9 = hashMap2.get(Oauth2AccessToken.KEY_UID);
                String str10 = hashMap2.get("reqbiz-ext");
                String str11 = hashMap2.get("data");
                String str12 = hashMap2.get(LogItem.MM_C15_K4_TIME);
                String str13 = hashMap2.get(MtopJSParam.API);
                String str14 = hashMap2.get("v");
                String str15 = hashMap2.get(Constants.KEY_SID);
                String str16 = hashMap2.get("ttid");
                String str17 = hashMap2.get("deviceId");
                String str18 = hashMap2.get("lat");
                str4 = c2;
                try {
                    String str19 = hashMap2.get(CameraControllerManager.MY_POILOCATION_LNG);
                    try {
                        String str20 = hashMap2.get("extdata");
                        String str21 = hashMap2.get("x-features");
                        StringBuilder sb2 = new StringBuilder(64);
                        sb2.append(fgx.a(str8));
                        sb2.append("&");
                        sb2.append(fgx.a(str9));
                        sb2.append("&");
                        sb2.append(fgx.a(str10));
                        sb2.append("&");
                        sb2.append(str6);
                        sb2.append("&");
                        sb2.append(fgx.b(str11));
                        sb2.append("&");
                        sb2.append(str12);
                        sb2.append("&");
                        sb2.append(str13);
                        sb2.append("&");
                        sb2.append(str14);
                        sb2.append("&");
                        sb2.append(fgx.a(str15));
                        sb2.append("&");
                        sb2.append(fgx.a(str16));
                        sb2.append("&");
                        sb2.append(fgx.a(str17));
                        sb2.append("&");
                        sb2.append(fgx.a(str18));
                        sb2.append("&");
                        sb2.append(fgx.a(str19));
                        sb2.append("&");
                        String str22 = str20;
                        if (fdd.a(str22)) {
                            try {
                                sb2.append(str22);
                                sb2.append("&");
                            } catch (Exception e) {
                                th = e;
                            }
                        }
                        sb2.append(str21);
                        str7 = sb2.toString();
                        fgt = this;
                    } catch (Exception e2) {
                        e = e2;
                        th = e;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str4);
                        sb3.append(" [getMtopApiSign] ISecureSignatureComponent signRequest error");
                        TBSdkLog.b((String) "mtopsdk.LocalInnerSignImpl", sb3.toString(), th);
                        str3 = null;
                        return str3;
                    }
                } catch (Exception e3) {
                    e = e3;
                    th = e;
                    StringBuilder sb32 = new StringBuilder();
                    sb32.append(str4);
                    sb32.append(" [getMtopApiSign] ISecureSignatureComponent signRequest error");
                    TBSdkLog.b((String) "mtopsdk.LocalInnerSignImpl", sb32.toString(), th);
                    str3 = null;
                    return str3;
                }
            }
            String c3 = c();
            if (fdd.b(str7)) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(c3);
                sb4.append(" [getCommonHmacSha1Sign] baseStr is null.appKey=");
                sb4.append(str6);
                TBSdkLog.d("mtopsdk.LocalInnerSignImpl", sb4.toString());
            } else {
                if (str6 != null) {
                    if (str6.equals(fgt.c)) {
                        str5 = fgw.a(str7, fgt.d);
                        str3 = str5;
                        return str3;
                    }
                }
                StringBuilder sb5 = new StringBuilder(64);
                sb5.append(c3);
                sb5.append(" [getCommonHmacSha1Sign] request appKey mismatches global appKey.requestAppKey=");
                sb5.append(str6);
                sb5.append(",globalAppKey=");
                sb5.append(fgt.c);
                TBSdkLog.d("mtopsdk.LocalInnerSignImpl", sb5.toString());
            }
            str5 = null;
            str3 = str5;
        } catch (Exception e4) {
            e = e4;
            str4 = c2;
            th = e;
            StringBuilder sb322 = new StringBuilder();
            sb322.append(str4);
            sb322.append(" [getMtopApiSign] ISecureSignatureComponent signRequest error");
            TBSdkLog.b((String) "mtopsdk.LocalInnerSignImpl", sb322.toString(), th);
            str3 = null;
            return str3;
        }
        return str3;
    }
}
