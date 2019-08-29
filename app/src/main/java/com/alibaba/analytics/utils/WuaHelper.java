package com.alibaba.analytics.utils;

import android.content.Context;
import com.alibaba.analytics.core.Variables;
import java.util.HashMap;

public class WuaHelper {
    public static String getMiniWua() {
        long j = 0;
        try {
            if (Logger.isDebug()) {
                j = System.currentTimeMillis();
            }
            Class<?> cls = Class.forName("com.alibaba.wireless.security.open.SecurityGuardManager");
            Object invoke = cls.getMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{Variables.getInstance().getContext()});
            Class<?> cls2 = Class.forName("com.alibaba.wireless.security.open.securitybody.ISecurityBodyComponent");
            Object invoke2 = cls.getMethod("getInterface", new Class[]{Class.class}).invoke(invoke, new Object[]{cls2});
            Class<?> cls3 = Class.forName("com.alibaba.wireless.security.open.securitybody.SecurityBodyDefine");
            int i = cls3.getField("OPEN_SECURITYBODY_FLAG_FORMAT_MINI").getInt(cls3);
            int i2 = cls3.getField("OPEN_SECURITYBODY_ENV_ONLINE").getInt(cls3);
            Logger.d("OPEN_SECURITYBODY_FLAG_FORMAT_MINI:".concat(String.valueOf(i)), new Object[0]);
            Logger.d("OPEN_SECURITYBODY_ENV_ONLINE:".concat(String.valueOf(i2)), new Object[0]);
            String str = (String) cls2.getMethod("getSecurityBodyDataEx", new Class[]{String.class, String.class, String.class, HashMap.class, Integer.TYPE, Integer.TYPE}).invoke(invoke2, new Object[]{null, null, null, null, Integer.valueOf(i), Integer.valueOf(i2)});
            if (Logger.isDebug()) {
                StringBuilder sb = new StringBuilder("Mini Wua: ");
                sb.append(str);
                sb.append(",waste time:");
                sb.append(System.currentTimeMillis() - j);
                Logger.d(sb.toString(), new Object[0]);
            }
            return str;
        } catch (Throwable th) {
            Logger.d((String) "", th.toString());
            return null;
        }
    }
}
