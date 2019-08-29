package com.alipay.mobile.common.transport.utils;

import java.lang.reflect.Method;
import javax.net.ssl.SSLSocket;

public class SSLSocketUtil {
    private static Class<?> a;
    private static Method b;
    private static Method c;

    public static final void enableTlsExtensions(SSLSocket socket, String uriHost) {
        Class localOpenSslSocketClass = c();
        if (localOpenSslSocketClass != null && localOpenSslSocketClass.isInstance(socket)) {
            try {
                Method methodSetUseSessionTickets = b();
                if (methodSetUseSessionTickets != null) {
                    methodSetUseSessionTickets.invoke(socket, new Object[]{Boolean.valueOf(true)});
                }
                Method methodSetHostname = a();
                if (methodSetHostname != null) {
                    methodSetHostname.invoke(socket, new Object[]{uriHost});
                }
            } catch (Throwable e) {
                LogCatUtil.warn((String) "SSLSocketUtil", "This isn't Android 2.3 or better. getMethodSetHostname exception:" + e.toString());
            }
        }
    }

    private static final Method a() {
        if (c != null) {
            return c;
        }
        try {
            c = c().getMethod("setHostname", new Class[]{String.class});
        } catch (NoSuchMethodException e) {
            LogCatUtil.warn((String) "SSLSocketUtil", "This isn't Android 2.3 or better. getMethodSetHostname exception:" + e.toString());
        }
        return c;
    }

    private static final Method b() {
        if (b != null) {
            return b;
        }
        try {
            b = c().getMethod("setUseSessionTickets", new Class[]{Boolean.TYPE});
        } catch (NoSuchMethodException e) {
            LogCatUtil.warn((String) "SSLSocketUtil", "This isn't Android 2.3 or better. getMethodSetUseSessionTickets exception:" + e.toString());
        }
        return b;
    }

    private static final Class<?> c() {
        if (a != null) {
            return a;
        }
        synchronized (SSLSocketUtil.class) {
            if (a != null) {
                Class<?> cls = a;
                return cls;
            }
            try {
                a = Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
            } catch (ClassNotFoundException e) {
                try {
                    a = Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
                } catch (Exception e2) {
                    LogCatUtil.warn((String) "SSLSocketUtil", "This isn't an Android runtime, exception:" + e2.toString());
                }
            }
            return a;
        }
    }
}
