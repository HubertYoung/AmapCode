package com.alibaba.analytics.utils;

import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ZipDictUtils {
    private static final int MAX_SIZE_DICT = 32768;
    private static final int MAX_SIZE_HEAD = 1024;
    private static final int MAX_SIZE_LOG = 256;
    private static int mDictIndex;
    private static int mDictLength;
    private static HashMap<String, String> mZipDictMap = new HashMap<>();

    public static synchronized byte[] getHeadBytes(String str) throws IOException {
        byte[] bytes;
        synchronized (ZipDictUtils.class) {
            try {
                bytes = getBytes(str, true);
            }
        }
        return bytes;
    }

    public static synchronized byte[] getBytes(String str) throws IOException {
        byte[] bytes;
        synchronized (ZipDictUtils.class) {
            try {
                bytes = getBytes(str, false);
            }
        }
        return bytes;
    }

    private static synchronized byte[] getBytes(String str, boolean z) throws IOException {
        byte[] byteArray;
        int i;
        synchronized (ZipDictUtils.class) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (checkEnableKey(str, z)) {
                try {
                    i = Integer.parseInt(mZipDictMap.get(str));
                } catch (Exception unused) {
                    i = -1;
                }
                if (i >= 0) {
                    byteArrayOutputStream.write(getLengthBytes(1, i));
                } else {
                    put(str);
                    byteArrayOutputStream.write(getLengthBytes(2, str.getBytes().length));
                    byteArrayOutputStream.write(str.getBytes());
                }
            } else if (TextUtils.isEmpty(str)) {
                byteArrayOutputStream.write(getLengthBytes(3, 0));
            } else {
                byteArrayOutputStream.write(getLengthBytes(3, str.getBytes().length));
                byteArrayOutputStream.write(str.getBytes());
            }
            byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArray;
    }

    private static synchronized boolean checkEnableKey(String str, boolean z) {
        synchronized (ZipDictUtils.class) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (z) {
                if (str.length() > 1024) {
                    return false;
                }
            } else if (str.length() > 256) {
                return false;
            }
            if (mDictLength + str.length() > 32768) {
                return false;
            }
            return true;
        }
    }

    private static synchronized void put(String str) {
        synchronized (ZipDictUtils.class) {
            HashMap<String, String> hashMap = mZipDictMap;
            StringBuilder sb = new StringBuilder();
            sb.append(mDictIndex);
            hashMap.put(str, sb.toString());
            mDictLength += str.length();
            mDictIndex++;
        }
    }

    public static synchronized void clear() {
        synchronized (ZipDictUtils.class) {
            mZipDictMap.clear();
            mDictLength = 0;
            mDictIndex = 0;
        }
    }

    public static byte[] getLengthBytes(int i, int i2) {
        int i3 = 1 << (8 - i);
        byte b = (byte) i3;
        int i4 = i3 - 1;
        if (i2 < i4) {
            return ByteUtils.intToBytes1(i2 | b);
        }
        byte[] bArr = new byte[4];
        bArr[0] = (byte) ((b | i4) & 255);
        int i5 = i2 - i4;
        int i6 = 1;
        while (i5 >= 128) {
            bArr[i6] = (byte) ((128 | (i5 % 128)) & 255);
            i5 /= 128;
            i6++;
        }
        bArr[i6] = (byte) (i5 & 127);
        return ByteUtils.subBytes(bArr, 0, i6 + 1);
    }

    public static byte[] getLengthBytes(int i) {
        return getLengthBytes(0, i);
    }
}
