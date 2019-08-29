package com.xiaomi.channel.commonutils.misc;

import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;

public class e {
    static final char[] a = HexUtils.HEX_CHARS.toCharArray();

    public static String a(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder(i2 * 2);
        for (int i3 = 0; i3 < i2; i3++) {
            byte b = bArr[i + i3] & 255;
            sb.append(a[b >> 4]);
            sb.append(a[b & 15]);
        }
        return sb.toString();
    }
}
