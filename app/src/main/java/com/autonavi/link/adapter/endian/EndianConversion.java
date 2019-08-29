package com.autonavi.link.adapter.endian;

import android.support.v4.view.MotionEventCompat;

public class EndianConversion {
    public static byte[] int32ToByte(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((65280 & i) >> 8), (byte) ((16711680 & i) >> 16), (byte) ((i & -16777216) >> 24)};
    }

    public static byte[] int16ToByte(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8)};
    }

    public static int bytesToInt32(byte[] bArr) {
        return ((bArr[3] & 255) << 24) | (bArr[0] & 255) | ((bArr[1] & 255) << 8) | ((bArr[2] & 255) << 16);
    }

    public static int bytesToInt16(byte[] bArr) {
        return ((bArr[1] << 8) & 65280) | (bArr[0] & 255);
    }

    public static byte[] little_intToByte(int i, int i2) {
        byte[] bArr = new byte[i2];
        if (i2 == 1) {
            bArr[0] = (byte) (i & 255);
        } else if (i2 == 2) {
            bArr[0] = (byte) (i & 255);
            bArr[1] = (byte) ((i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        } else {
            bArr[0] = (byte) (i & 255);
            bArr[1] = (byte) ((i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr[2] = (byte) ((16711680 & i) >> 16);
            bArr[3] = (byte) ((i & -16777216) >> 24);
        }
        return bArr;
    }

    public static int little_bytesToInt(byte[] bArr) {
        if (bArr.length == 1) {
            return bArr[0] & 255;
        }
        if (bArr.length == 2) {
            return ((bArr[1] << 8) & 65280) | (bArr[0] & 255);
        }
        return ((bArr[3] << 24) & -16777216) | (bArr[0] & 255) | ((bArr[1] << 8) & 65280) | ((bArr[2] << 16) & 16711680);
    }

    public static byte[] big_intToByte(int i, int i2) {
        byte[] bArr = new byte[i2];
        if (i2 == 1) {
            bArr[0] = (byte) (i & 255);
        } else if (i2 == 2) {
            bArr[0] = (byte) ((i >>> 8) & 255);
            bArr[1] = (byte) (i & 255);
        } else {
            bArr[0] = (byte) ((i >>> 24) & 255);
            bArr[1] = (byte) ((i >>> 16) & 255);
            bArr[2] = (byte) ((i >>> 8) & 255);
            bArr[3] = (byte) (i & 255);
        }
        return bArr;
    }

    public static int big_bytesToInt(byte[] bArr) {
        if (bArr.length == 1) {
            return bArr[0] & 255;
        }
        if (bArr.length == 2) {
            return (bArr[1] & 255) | ((bArr[0] & 255) << 8);
        }
        return (bArr[3] & 255) | ((((((bArr[0] & 255) << 8) | (bArr[1] & 255)) << 8) | (bArr[2] & 255)) << 8);
    }
}
