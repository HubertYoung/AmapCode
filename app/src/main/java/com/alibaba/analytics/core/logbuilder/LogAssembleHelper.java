package com.alibaba.analytics.core.logbuilder;

import android.content.Context;
import com.alibaba.analytics.utils.ReflectUtils;

public class LogAssembleHelper {
    private static String mUMID = null;
    private static boolean mUMIDGetSwitch = true;

    public static String getSecurityToken(Context context) {
        if (context != null && mUMIDGetSwitch) {
            try {
                Class<?> cls = Class.forName("com.taobao.dp.DeviceSecuritySDK");
                if (cls != null) {
                    Object invokeStaticMethod = ReflectUtils.invokeStaticMethod((Class) cls, (String) "getInstance", new Object[]{context}, Context.class);
                    if (invokeStaticMethod != null) {
                        Object invokeMethod = ReflectUtils.invokeMethod(invokeStaticMethod, "getSecurityToken");
                        if (invokeMethod != null) {
                            mUMID = (String) invokeMethod;
                        }
                        return (String) invokeMethod;
                    }
                } else {
                    mUMIDGetSwitch = false;
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }
}
