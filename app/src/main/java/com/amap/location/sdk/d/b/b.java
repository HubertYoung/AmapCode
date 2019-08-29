package com.amap.location.sdk.d.b;

import com.amap.location.common.d.a;
import com.amap.location.common.f.i;

/* compiled from: AosEncryptUtil */
public class b {
    public static String a(String str) {
        Object obj;
        try {
            obj = i.a("com.autonavi.jni.server.aos.ServerkeyNative", "amapEncode", new Object[]{str}, new Class[]{String.class});
        } catch (Exception e) {
            a.a((Throwable) e);
            obj = null;
        }
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }

    public static byte[] a(byte[] bArr) {
        Object obj;
        if (bArr == null) {
            return null;
        }
        try {
            obj = i.a("com.autonavi.jni.server.aos.ServerkeyNative", "amapEncodeBinary", new Object[]{bArr}, new Class[]{byte[].class});
        } catch (Exception e) {
            a.a((Throwable) e);
            obj = null;
        }
        if (obj == null) {
            return null;
        }
        return (byte[]) obj;
    }
}
