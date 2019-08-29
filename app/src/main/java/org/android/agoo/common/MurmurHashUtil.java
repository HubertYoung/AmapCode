package org.android.agoo.common;

import android.text.TextUtils;

public final class MurmurHashUtil {
    public static final int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return (int) (Math.random() * 100.0d);
        }
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        int i = length >> 2;
        int i2 = Integer.MAX_VALUE ^ length;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = (i3 << 2) + 0;
            int i5 = ((bytes[i4 + 0] & 255) | (((((bytes[i4 + 3] << 8) | (bytes[i4 + 2] & 255)) << 8) | (bytes[i4 + 1] & 255)) << 8)) * 1540483477;
            i2 = (i2 * 1540483477) ^ ((i5 ^ (i5 >>> 24)) * 1540483477);
        }
        int i6 = length - (i << 2);
        if (i6 != 0) {
            if (i6 >= 3) {
                i2 ^= bytes[(length + 0) - 3] << 16;
            }
            if (i6 >= 2) {
                i2 ^= bytes[(length + 0) - 2] << 8;
            }
            if (i6 > 0) {
                i2 ^= bytes[(length + 0) - 1];
            }
            i2 *= 1540483477;
        }
        int i7 = ((i2 >>> 13) ^ i2) * 1540483477;
        return (int) (((long) Math.abs(i7 ^ (i7 >>> 15))) % 100);
    }
}
