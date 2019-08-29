package com.alipay.mobile.nebulacore.util;

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.Build.VERSION;
import com.alipay.mobile.nebula.util.H5Log;

public class H5ToastUtil {
    public static int checkOp(Context context, int op) {
        H5Log.d("H5ToastUtil", "isNotificationEnabled: " + isNotificationEnabled(context));
        if (VERSION.SDK_INT >= 19) {
            Object object = context.getSystemService("appops");
            try {
                return ((Integer) object.getClass().getDeclaredMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(object, new Object[]{Integer.valueOf(op), Integer.valueOf(Binder.getCallingUid()), context.getPackageName()})).intValue();
            } catch (Throwable th) {
                th.getMessage();
            }
        }
        return -1;
    }

    public static boolean isNotificationEnabled(Context context) {
        if (VERSION.SDK_INT < 19) {
            return true;
        }
        if (VERSION.SDK_INT >= 24) {
            NotificationManager mAppOps = (NotificationManager) context.getSystemService("notification");
            if (mAppOps != null) {
                return mAppOps.areNotificationsEnabled();
            }
        } else {
            AppOpsManager mAppOps2 = (AppOpsManager) context.getSystemService("appops");
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;
            try {
                Class appOpsClass = Class.forName(AppOpsManager.class.getName());
                return ((Integer) appOpsClass.getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(mAppOps2, new Object[]{Integer.valueOf(((Integer) appOpsClass.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(uid), pkg})).intValue() == 0;
            } catch (Exception e) {
                H5Log.d("H5ToastUtil", "e: " + e.getMessage());
            }
        }
        return true;
    }
}
