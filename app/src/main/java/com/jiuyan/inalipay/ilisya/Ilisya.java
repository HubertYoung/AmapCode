package com.jiuyan.inalipay.ilisya;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;

public class Ilisya {
    private static Ilisya _ilisya = null;

    private static native byte[] jniCreateHttpRequestString(String str, String str2);

    private static native int jniInit(String str, String str2);

    private static native void jniSetServerTimestamp(long j);

    static {
        System.loadLibrary("ilisya");
        Init("GD{8W3N^53~;2{");
    }

    public static boolean Init(String str) {
        if (_ilisya != null) {
            return true;
        }
        if (jniInit(str, "_dcarg") != 0) {
            return false;
        }
        _ilisya = new Ilisya();
        return true;
    }

    public static Ilisya instance() {
        return _ilisya;
    }

    public String createHttpRequestString(String str, String str2) {
        return new String(jniCreateHttpRequestString(str, str2));
    }

    public void setServerTimestamp(long j) {
        jniSetServerTimestamp(j);
    }

    private Ilisya() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void main(String[] strArr) {
        Init(BehavorReporter.PROVIDE_BY_ALIPAY);
        Ilisya instance = instance();
        instance.setServerTimestamp(1478250974);
        System.out.println(instance.createHttpRequestString("_v=1.2.3&_n=android", "euid=12345&ff=124134"));
        System.out.println(instance.createHttpRequestString("_v=1.2.3&_n=android", null));
        System.out.println(instance.createHttpRequestString(null, "euid=12345"));
        System.out.println(instance.createHttpRequestString(null, null));
    }
}
