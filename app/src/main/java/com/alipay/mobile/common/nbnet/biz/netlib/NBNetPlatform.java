package com.alipay.mobile.common.nbnet.biz.netlib;

import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import java.lang.reflect.Method;
import java.net.Socket;
import javax.net.ssl.SSLSocket;

public class NBNetPlatform {
    static Method a = null;
    static Class b = null;
    static Class<?> c = null;
    static Method d;
    static Method e;

    public static final void a(SSLSocket socket, String uriHost) {
        try {
            Class tmpSslSocketUtilClass = b();
            if (tmpSslSocketUtilClass != null) {
                c().invoke(tmpSslSocketUtilClass, new Object[]{socket, uriHost});
            }
        } catch (Throwable e2) {
            NBNetLogCat.a((String) "NBNetPlatform", e2);
        }
    }

    public static void a(Socket socket) {
        if (a()) {
            try {
                d.invoke(c, new Object[]{socket});
            } catch (Throwable e2) {
                NBNetLogCat.d("NBNetPlatform", "tagSocket exception: " + e2.toString());
            }
        }
    }

    public static void b(Socket socket) {
        if (a()) {
            try {
                d.invoke(c, new Object[]{socket});
            } catch (Throwable e2) {
                NBNetLogCat.d("NBNetPlatform", "untagSocket exception: " + e2.toString());
            }
        }
    }

    private static final boolean a() {
        if (c != null) {
            return true;
        }
        try {
            Class<?> cls = Class.forName("android.net.TrafficStats");
            c = cls;
            d = cls.getMethod("tagSocket", new Class[]{Socket.class});
            e = c.getMethod("untagSocket", new Class[]{Socket.class});
            return true;
        } catch (Throwable e2) {
            NBNetLogCat.d("NBNetPlatform", "initTrafficStats exception: " + e2.toString());
            c = null;
            d = null;
            e = null;
            return false;
        }
    }

    private static final Class b() {
        if (b != null) {
            return b;
        }
        synchronized (NBNetPlatform.class) {
            if (b != null) {
                Class cls = b;
                return cls;
            }
            try {
                b = Class.forName("com.alipay.mobile.common.transport.utils.SSLSocketUtil");
            } catch (ClassNotFoundException e2) {
                NBNetLogCat.a((String) "NBNetPlatform", (Throwable) e2);
            }
            return b;
        }
    }

    private static final Method c() {
        if (a != null) {
            return a;
        }
        synchronized (NBNetPlatform.class) {
            if (a != null) {
                Method method = a;
                return method;
            }
            Class sslSocketUtilClass = b();
            if (sslSocketUtilClass == null) {
                return null;
            }
            try {
                a = sslSocketUtilClass.getDeclaredMethod("enableTlsExtensions", new Class[]{SSLSocket.class, String.class});
            } catch (NoSuchMethodException e2) {
                NBNetLogCat.a((String) "NBNetPlatform", (Throwable) e2);
            }
            Method method2 = a;
            return method2;
        }
    }
}
