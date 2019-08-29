package com.alipay.inside.android.phone.mrpc.core.utils;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.security.model.SGParamContext;
import com.alipay.android.phone.inside.security.signature.SecureSignatureUtils;
import java.util.HashMap;

public class RpcSignUtil {
    public static final String APP_KEY_ALIPAYINSIDE = "23699722";
    public static final String APP_KEY_DEBUG = "rpc-sdk";
    public static final String APP_KEY_ONLINE = "rpc-sdk-online";
    private static final String TAG = "RpcSignUtil";

    public static class SignData {
        public static final int OPEN_ENUM_SIGN_ATLAS = 2;
        private static SignData emptySignData;
        public String sign = "";
        public int signType = 0;

        public static final SignData newEmptySignData() {
            if (emptySignData == null) {
                emptySignData = new SignData();
            }
            return emptySignData;
        }
    }

    public static SignData signature(Context context, String str, boolean z, String str2) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("INPUT", str2);
            SGParamContext sGParamContext = new SGParamContext();
            sGParamContext.a = hashMap;
            setAppKey(sGParamContext, str, z, context);
            SignData signData = new SignData();
            sGParamContext.c = 4;
            String a = SecureSignatureUtils.a(context, sGParamContext);
            signData.sign = a;
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("Get security signed string: ");
            sb.append(a);
            sb.append(", requestType: ");
            sb.append(sGParamContext.c);
            f.a((String) TAG, sb.toString());
            return signData;
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "rpc", (String) "RpcSignRequestEx", th);
            return SignData.newEmptySignData();
        }
    }

    private static void setAppKey(SGParamContext sGParamContext, String str, boolean z, Context context) {
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("RpcSignUtil::setAppKey appkey=");
        sb.append(str);
        sb.append(",isReq2Online=");
        sb.append(z);
        f.b((String) "rpc", sb.toString());
        try {
            sGParamContext.b = "23699722";
        } finally {
            if (MiscUtils.isDebugger(context)) {
                TraceLogger f2 = LoggerFactory.f();
                String str2 = TAG;
                r1 = "appKey:";
                StringBuilder sb2 = new StringBuilder(r1);
                sb2.append(sGParamContext.b);
                f2.a(str2, sb2.toString());
            }
        }
    }
}
