package com.alibaba.analytics.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class PhoneInfoUtils {
    private static final String STORAGE_KEY = "UTCommon";
    private static final Random s_random = new Random();

    public static final String getUniqueID() {
        int nextInt = s_random.nextInt();
        int nextInt2 = s_random.nextInt();
        byte[] bytes = IntUtils.getBytes((int) (System.currentTimeMillis() / 1000));
        byte[] bytes2 = IntUtils.getBytes((int) System.nanoTime());
        byte[] bytes3 = IntUtils.getBytes(nextInt);
        byte[] bytes4 = IntUtils.getBytes(nextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return Base64.encodeToString(bArr, 2);
    }

    public static String getImei(Context context) {
        String str = null;
        if (context != null) {
            try {
                String string = context.getSharedPreferences(STORAGE_KEY, 0).getString("_ie", "");
                if (!StringUtils.isEmpty(string)) {
                    String str2 = new String(Base64.decode(string.getBytes(), 2), "UTF-8");
                    if (!StringUtils.isEmpty(str2)) {
                        return str2;
                    }
                }
            } catch (Exception unused) {
            }
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getDeviceId();
                }
            } catch (Exception unused2) {
            }
        }
        if (StringUtils.isEmpty(str)) {
            str = getUniqueID();
        }
        if (context != null) {
            try {
                Editor edit = context.getSharedPreferences(STORAGE_KEY, 0).edit();
                edit.putString("_ie", new String(Base64.encode(str.getBytes("UTF-8"), 2)));
                edit.commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String getImsi(Context context) {
        String str = null;
        if (context != null) {
            try {
                String string = context.getSharedPreferences(STORAGE_KEY, 0).getString("_is", "");
                if (!StringUtils.isEmpty(string)) {
                    String str2 = new String(Base64.decode(string.getBytes(), 2), "UTF-8");
                    if (!StringUtils.isEmpty(str2)) {
                        return str2;
                    }
                }
            } catch (Exception unused) {
            }
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getSubscriberId();
                }
            } catch (Exception unused2) {
            }
        }
        if (StringUtils.isEmpty(str)) {
            str = getUniqueID();
        }
        if (context != null) {
            try {
                Editor edit = context.getSharedPreferences(STORAGE_KEY, 0).edit();
                edit.putString("_is", new String(Base64.encode(str.getBytes("UTF-8"), 2)));
                edit.commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String getImeiBySystem(Context context) {
        String str = null;
        if (context == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                str = telephonyManager.getDeviceId();
            }
        } catch (Throwable unused) {
        }
        return str;
    }

    public static String getImsiBySystem(Context context) {
        String str = null;
        if (context == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                str = telephonyManager.getSubscriberId();
            }
        } catch (Throwable unused) {
        }
        return str;
    }
}
