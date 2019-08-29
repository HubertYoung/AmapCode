package com.autonavi.minimap.ajx3.util;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class EncodingUtils {
    public static final String ASCII = "ASCII";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String US_ASCII = "US-ASCII";
    public static final String UTF_16 = "UTF-16";
    public static final String UTF_8 = "UTF-8";

    public static String getString(InputStream inputStream, String str) {
        BufferedReader bufferedReader;
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(inputStream, str);
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
            } catch (IOException e) {
                e = e;
                bufferedReader = null;
                try {
                    StringBuilder sb = new StringBuilder("EncodingUtils::getString #error!!! ");
                    sb.append(Log.getStackTraceString(e));
                    LogHelper.e(sb.toString());
                    CloseableUtils.close(inputStreamReader);
                    CloseableUtils.close(bufferedReader);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    CloseableUtils.close(inputStreamReader);
                    CloseableUtils.close(bufferedReader);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                CloseableUtils.close(inputStreamReader);
                CloseableUtils.close(bufferedReader);
                throw th;
            }
            try {
                StringBuilder sb2 = new StringBuilder("");
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb2.append(readLine);
                        sb2.append("\n");
                    } else {
                        String sb3 = sb2.toString();
                        CloseableUtils.close(inputStreamReader);
                        CloseableUtils.close(bufferedReader);
                        return sb3;
                    }
                }
            } catch (IOException e2) {
                e = e2;
                StringBuilder sb4 = new StringBuilder("EncodingUtils::getString #error!!! ");
                sb4.append(Log.getStackTraceString(e));
                LogHelper.e(sb4.toString());
                CloseableUtils.close(inputStreamReader);
                CloseableUtils.close(bufferedReader);
                return null;
            }
        } catch (IOException e3) {
            e = e3;
            bufferedReader = null;
            inputStreamReader = null;
            StringBuilder sb42 = new StringBuilder("EncodingUtils::getString #error!!! ");
            sb42.append(Log.getStackTraceString(e));
            LogHelper.e(sb42.toString());
            CloseableUtils.close(inputStreamReader);
            CloseableUtils.close(bufferedReader);
            return null;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            inputStreamReader = null;
            CloseableUtils.close(inputStreamReader);
            CloseableUtils.close(bufferedReader);
            throw th;
        }
    }

    public static String getString(byte[] bArr, String str) {
        if (bArr == null) {
            return null;
        }
        return getString(bArr, 0, bArr.length, str);
    }

    public static String getString(byte[] bArr, int i, int i2, String str) {
        if (bArr == null || str == null || str.length() == 0) {
            return null;
        }
        try {
            return new String(bArr, i, i2, str);
        } catch (UnsupportedEncodingException unused) {
            return new String(bArr, i, i2);
        }
    }
}
