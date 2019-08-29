package com.alipay.apmobilesecuritysdk.commonbiz.external;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;

public class UtdidWrapper {
    private static String TAG = "APSecuritySdk";

    public static String getUtdid(Context context) {
        TraceLogger f = LoggerFactory.f();
        try {
            return (String) Class.forName("com.ut.device.UTDevice").getMethod("getUtdid", new Class[]{Context.class}).invoke(null, new Object[]{context});
        } catch (Exception unused) {
            f.b(TAG, (String) "[*] UTDID error。");
            r8 = "";
            return "";
        }
    }
}
