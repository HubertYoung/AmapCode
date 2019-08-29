package com.autonavi.indoor.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import java.security.MessageDigest;

public class PackageHelper {
    static String mApplicationName = "";
    static String mPackageName = null;
    static String mVersion = "";

    public static String getApplicationVersion(Context context) {
        try {
            if (!"".equals(mVersion)) {
                return mVersion;
            }
            mVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return mVersion;
        } catch (Exception e) {
            if (L.isLogging) {
                L.d((Throwable) e);
            }
        }
    }

    public static String getApplicationName(Context context) {
        try {
            if (!"".equals(mApplicationName)) {
                return mApplicationName;
            }
            PackageManager packageManager = context.getPackageManager();
            mApplicationName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0));
            return mApplicationName;
        } catch (Exception e) {
            if (L.isLogging) {
                L.d((Throwable) e);
            }
        }
    }

    public static String getPackageName(Context context) {
        try {
            if (mPackageName != null && !"".equals(mPackageName)) {
                return mPackageName;
            }
            mPackageName = context.getApplicationContext().getPackageName();
            return mPackageName;
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
    }

    public static String getSHA1(Context context) {
        String str = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            if (packageInfo.signatures.length == 0) {
                return str;
            }
            int i = 0;
            Signature signature = packageInfo.signatures[0];
            MessageDigest instance = MessageDigest.getInstance("SHA1");
            instance.update(signature.toByteArray());
            byte[] digest = instance.digest();
            while (i < digest.length) {
                if (str.length() > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(":");
                    str = sb.toString();
                }
                StringBuilder sb2 = new StringBuilder("00");
                sb2.append(Integer.toString(digest[i] & 255, 16));
                String sb3 = sb2.toString();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(sb3.substring(sb3.length() - 2));
                i++;
                str = sb4.toString();
            }
            if (L.isLogging) {
                L.d("MY KEY HASH:".concat(String.valueOf(str)));
            }
            return str.toUpperCase();
        } catch (Exception e) {
            if (L.isLogging) {
                L.d((Throwable) e);
            }
            return str;
        }
    }
}
