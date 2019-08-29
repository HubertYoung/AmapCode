package com.tencent.connect.a;

import android.content.Context;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.utils.OpenConfig;
import java.lang.reflect.Method;
import org.eclipse.mat.hprof.IHprofParserHandler;

/* compiled from: ProGuard */
public class a {
    private static Class<?> a = null;
    private static Class<?> b = null;
    private static Method c = null;
    private static Method d = null;
    private static Method e = null;
    private static Method f = null;
    private static boolean g = false;

    public static boolean a(Context context, QQToken qQToken) {
        return OpenConfig.getInstance(context, qQToken.getAppId()).getBoolean("Common_ta_enable");
    }

    public static void b(Context context, QQToken qQToken) {
        try {
            if (a(context, qQToken)) {
                f.invoke(a, new Object[]{Boolean.TRUE});
                return;
            }
            f.invoke(a, new Object[]{Boolean.FALSE});
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void c(Context context, QQToken qQToken) {
        String concat = "Aqc".concat(String.valueOf(qQToken.getAppId()));
        try {
            a = Class.forName("com.tencent.stat.StatConfig");
            Class<?> cls = Class.forName("com.tencent.stat.StatService");
            b = cls;
            c = cls.getMethod("reportQQ", new Class[]{Context.class, String.class});
            d = b.getMethod("trackCustomEvent", new Class[]{Context.class, String.class, String[].class});
            e = b.getMethod("commitEvents", new Class[]{Context.class, Integer.TYPE});
            f = a.getMethod("setEnableStatService", new Class[]{Boolean.TYPE});
            b(context, qQToken);
            a.getMethod("setAutoExceptionCaught", new Class[]{Boolean.TYPE}).invoke(a, new Object[]{Boolean.FALSE});
            a.getMethod("setEnableSmartReporting", new Class[]{Boolean.TYPE}).invoke(a, new Object[]{Boolean.TRUE});
            a.getMethod("setSendPeriodMinutes", new Class[]{Integer.TYPE}).invoke(a, new Object[]{Integer.valueOf(1440)});
            Class<?> cls2 = Class.forName("com.tencent.stat.StatReportStrategy");
            a.getMethod("setStatSendStrategy", new Class[]{cls2}).invoke(a, new Object[]{cls2.getField("PERIOD").get(null)});
            b.getMethod("startStatService", new Class[]{Context.class, String.class, String.class}).invoke(b, new Object[]{context, concat, Class.forName("com.tencent.stat.common.StatConstants").getField(IHprofParserHandler.VERSION).get(null)});
            g = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void d(Context context, QQToken qQToken) {
        if (g) {
            b(context, qQToken);
            if (qQToken.getOpenId() != null) {
                try {
                    c.invoke(b, new Object[]{context, qQToken.getOpenId()});
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static void a(Context context, QQToken qQToken, String str, String... strArr) {
        if (g) {
            b(context, qQToken);
            try {
                d.invoke(b, new Object[]{context, str, strArr});
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
