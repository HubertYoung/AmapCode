package com.alipay.zoloz.toyger.util;

import android.os.Build;
import android.os.Build.VERSION;
import com.alipay.android.phone.a.a.a;
import com.alipay.mobile.security.bio.utils.BioLog;
import java.lang.reflect.Field;

public class EnvCheck {
    private static final int ANDROID_VERSION_4_3 = 18;

    private boolean isLowOS() {
        if (VERSION.SDK == null || Integer.parseInt(VERSION.SDK) >= 18) {
            return false;
        }
        return true;
    }

    public EnvErrorType check() {
        EnvErrorType envErrorType = EnvErrorType.ENV_ERROR_INVALID;
        if (isLowOS()) {
            return EnvErrorType.ENV_ERROR_LOW_OS;
        }
        if (!a.a.equals(getCamera())) {
            return envErrorType;
        }
        if (!"armeabi-v7a".equals(Build.CPU_ABI)) {
            return EnvErrorType.ENV_ERROR_UNSUPPORTED_CPU;
        }
        if (com.alipay.zoloz.hardware.camera.a.a.b() == -1) {
            return EnvErrorType.ENV_ERROR_NO_FRONT_CAMERA;
        }
        return envErrorType;
    }

    public static String getCamera() {
        try {
            Field field = a.class.getField("a");
            field.setAccessible(true);
            return (String) field.get(a.class);
        } catch (NoSuchFieldException e) {
            BioLog.w((Throwable) e);
            r0 = a.a;
            return a.a;
        } catch (IllegalAccessException e2) {
            BioLog.w((Throwable) e2);
            r0 = a.a;
            return a.a;
        }
    }
}
