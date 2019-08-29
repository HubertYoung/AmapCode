package com.tencent.open.a;

import android.text.format.Time;
import android.util.Log;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;

/* compiled from: ProGuard */
public final class h {
    public static final h a = new h();

    public final String a(int i) {
        if (i == 4) {
            return LogHelper.DEFAULT_LEVEL;
        }
        if (i == 8) {
            return "W";
        }
        if (i == 16) {
            return "E";
        }
        if (i == 32) {
            return "A";
        }
        switch (i) {
            case 1:
                return SecureSignatureDefine.SG_KEY_SIGN_VERSION;
            case 2:
                return "D";
            default:
                return "-";
        }
    }

    public final String a(int i, Thread thread, long j, String str, String str2, Throwable th) {
        long j2 = j % 1000;
        Time time = new Time();
        time.set(j);
        StringBuilder sb = new StringBuilder();
        sb.append(a(i));
        sb.append('/');
        sb.append(time.format("%Y-%m-%d %H:%M:%S"));
        sb.append(DjangoUtils.EXTENSION_SEPARATOR);
        if (j2 < 10) {
            sb.append("00");
        } else if (j2 < 100) {
            sb.append('0');
        }
        sb.append(j2);
        sb.append(' ');
        sb.append('[');
        if (thread == null) {
            sb.append("N/A");
        } else {
            sb.append(thread.getName());
        }
        sb.append(']');
        sb.append('[');
        sb.append(str);
        sb.append(']');
        sb.append(' ');
        sb.append(str2);
        sb.append(10);
        if (th != null) {
            sb.append("* Exception : \n");
            sb.append(Log.getStackTraceString(th));
            sb.append(10);
        }
        return sb.toString();
    }
}
