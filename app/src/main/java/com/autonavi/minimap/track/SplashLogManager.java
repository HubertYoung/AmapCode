package com.autonavi.minimap.track;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.alimama.AlimamaRequestHolder;
import com.autonavi.minimap.alimama.param.H5LogRequest;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashLogManager {
    /* access modifiers changed from: private */
    public static final String a = "SplashLogManager";

    static class SplashLogListener extends FalconAosPrepareResponseCallback<a> {
        private String a;
        private String b;
        private String c;

        public final /* bridge */ /* synthetic */ void a(Object obj) {
        }

        public SplashLogListener(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            SplashLogManager.a;
            if (this.c != null) {
                SplashLogManager.a(this.a, this.b, this.c);
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: b */
        public a a(AosByteResponse aosByteResponse) {
            a aVar = new a();
            boolean z = true;
            try {
                aVar.parser((byte[]) aosByteResponse.getResult());
                if (aVar.errorCode != 1) {
                    SplashLogManager.a;
                    new StringBuilder("send splash realtime log failed. reason=").append(aVar.errorCode);
                } else {
                    z = false;
                }
            } catch (Exception e) {
                SplashLogManager.a;
                e.printStackTrace();
            }
            if (z && this.c != null) {
                SplashLogManager.a(this.a, this.b, this.c);
            }
            return aVar;
        }
    }

    static class a extends AbstractAOSParser {
        a() {
        }

        public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
            parseHeader(bArr);
        }

        public final String getErrorDesc(int i) {
            return String.valueOf(i);
        }
    }

    public static void a(String str, String str2, String str3, String str4, String str5, String str6) {
        H5LogRequest h5LogRequest = new H5LogRequest();
        h5LogRequest.c = String.valueOf(System.currentTimeMillis());
        h5LogRequest.d = str;
        if (TextUtils.isEmpty(str2)) {
            str2 = "afp";
        }
        h5LogRequest.e = "splashscreen".concat(String.valueOf(str2));
        if (!TextUtils.isEmpty(str3)) {
            h5LogRequest.f = str3;
        }
        if (!TextUtils.isEmpty(str4)) {
            h5LogRequest.g = str4;
        }
        if (!TextUtils.isEmpty(str5)) {
            h5LogRequest.h = str5;
            h5LogRequest.i = str6;
        }
        AlimamaRequestHolder.getInstance().sendH5Log(h5LogRequest, new SplashLogListener(str, str2, str3));
    }

    public static void a(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("session_id", str);
            jSONObject.put("type", "splashscreen".concat(String.valueOf(str2)));
            jSONObject.put("status", str3);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_SPLASH_SCREEN, "B003", jSONObject);
    }
}
