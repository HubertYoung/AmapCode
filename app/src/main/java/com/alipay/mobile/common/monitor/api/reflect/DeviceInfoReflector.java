package com.alipay.mobile.common.monitor.api.reflect;

import android.content.Context;
import android.os.SystemClock;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.reflect.Method;

public class DeviceInfoReflector {
    private static Class<?> a;
    private static Method b;
    private static Method c;
    private static boolean d;
    private static boolean e;
    private static boolean f;
    private static boolean g;
    private static boolean h;

    private static void a() {
        if (a == null) {
            try {
                a = DeviceInfoReflector.class.getClassLoader().loadClass("com.alipay.mobile.common.info.DeviceInfo");
            } catch (Throwable e2) {
                if (!d) {
                    d = true;
                    LoggerFactory.getTraceLogger().error("DeviceInfoReflector", "init", e2);
                }
            }
        }
        if (b == null && a != null) {
            try {
                Method declaredMethod = a.getDeclaredMethod("createInstance", new Class[]{Context.class});
                b = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (Throwable e3) {
                if (!e) {
                    e = true;
                    LoggerFactory.getTraceLogger().error("DeviceInfoReflector", "init", e3);
                }
            }
        }
        if (c == null && a != null) {
            try {
                Method declaredMethod2 = a.getDeclaredMethod("getmDid", new Class[0]);
                c = declaredMethod2;
                declaredMethod2.setAccessible(true);
            } catch (Throwable e4) {
                if (!f) {
                    f = true;
                    LoggerFactory.getTraceLogger().error("DeviceInfoReflector", "init", e4);
                }
            }
        }
    }

    public static String getmDid(Context context) {
        String mDid = null;
        if (context != null) {
            long nowTime = h ? 0 : SystemClock.uptimeMillis();
            mDid = null;
            a();
            if (!(b == null || c == null)) {
                try {
                    Object result = c.invoke(b.invoke(null, new Object[]{context}), new Object[0]);
                    if (result != null) {
                        mDid = (String) result;
                    }
                } catch (Throwable e2) {
                    if (!g) {
                        g = true;
                        LoggerFactory.getTraceLogger().error("DeviceInfoReflector", "getmDid", e2);
                    }
                }
            }
            if (!h) {
                h = true;
                LoggerFactory.getTraceLogger().info("DeviceInfoReflector", LoggerFactory.getProcessInfo().getProcessAlias() + ", getmDid, spend: " + (SystemClock.uptimeMillis() - nowTime));
            }
        }
        return mDid;
    }
}
