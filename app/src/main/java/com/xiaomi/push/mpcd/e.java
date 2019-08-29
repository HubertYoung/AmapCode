package com.xiaomi.push.mpcd;

import com.xiaomi.channel.commonutils.android.c;
import com.xiaomi.channel.commonutils.string.a;

public class e {
    private static void a(byte[] bArr) {
        if (bArr.length >= 2) {
            bArr[0] = 99;
            bArr[1] = 100;
        }
    }

    public static byte[] a(String str, byte[] bArr) {
        byte[] a = a.a(str);
        try {
            a(a);
            return c.a(a, bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] b(String str, byte[] bArr) {
        byte[] a = a.a(str);
        try {
            a(a);
            return c.b(a, bArr);
        } catch (Exception unused) {
            return null;
        }
    }
}
