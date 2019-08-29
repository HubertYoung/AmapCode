package com.alipay.mobile.common.patch.dir.tar;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class Octal {
    public Octal() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static long parseOctal(byte[] header, int offset, int length) {
        long result = 0;
        boolean stillPadding = true;
        int end = offset + length;
        int i = offset;
        while (i < end && header[i] != 0) {
            if (header[i] == 32 || header[i] == 48) {
                if (!stillPadding) {
                    if (header[i] == 32) {
                        break;
                    }
                } else {
                    continue;
                    i++;
                }
            }
            stillPadding = false;
            result = (result << 3) + ((long) (header[i] - 48));
            i++;
        }
        return result;
    }

    public static int getOctalBytes(long value, byte[] buf, int offset, int length) {
        int idx = length - 1;
        buf[offset + idx] = 0;
        int idx2 = idx - 1;
        buf[offset + idx2] = 32;
        int idx3 = idx2 - 1;
        if (value == 0) {
            buf[offset + idx3] = 48;
            idx3--;
        } else {
            long val = value;
            while (idx3 >= 0 && val > 0) {
                buf[offset + idx3] = (byte) (((byte) ((int) (7 & val))) + 48);
                val >>= 3;
                idx3--;
            }
        }
        while (idx3 >= 0) {
            buf[offset + idx3] = 32;
            idx3--;
        }
        return offset + length;
    }

    public static int getCheckSumOctalBytes(long value, byte[] buf, int offset, int length) {
        getOctalBytes(value, buf, offset, length);
        buf[(offset + length) - 1] = 32;
        buf[(offset + length) - 2] = 0;
        return offset + length;
    }

    public static int getLongOctalBytes(long value, byte[] buf, int offset, int length) {
        byte[] temp = new byte[(length + 1)];
        getOctalBytes(value, temp, 0, length + 1);
        System.arraycopy(temp, 0, buf, offset, length);
        return offset + length;
    }
}
