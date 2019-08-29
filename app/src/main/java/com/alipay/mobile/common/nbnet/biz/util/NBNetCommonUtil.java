package com.alipay.mobile.common.nbnet.biz.util;

import android.os.Process;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.io.Closeable;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class NBNetCommonUtil {
    private static Class a;

    class NetworkThreadFactory implements ThreadFactory {
        public String a = "";
        public ThreadPoolExecutor b;

        NetworkThreadFactory(String name, ThreadPoolExecutor threadPoolExecutor) {
            this.a = name;
            this.b = threadPoolExecutor;
        }

        public Thread newThread(Runnable r) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.a);
            if (this.b != null) {
                stringBuilder.append(new StringBuilder(MetaRecord.LOG_SEPARATOR).append(this.b.getActiveCount() + 1).toString());
            }
            return new Thread(r, stringBuilder.toString());
        }
    }

    public static final boolean a(String a2, String b) {
        if (a2 == b) {
            return true;
        }
        if (a2 == null && b != null) {
            return false;
        }
        if (a2 != null && b == null) {
            return false;
        }
        if (!a2.equals(b)) {
            return false;
        }
        return true;
    }

    public static boolean a(Object a2, Object b) {
        return a2 == b || !(a2 == null || b == null || !a2.equals(b));
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                NBNetLogCat.a((String) "closeQuietly", "===>Warning: Connection [closed] . closeable=[" + closeable.getClass().getName() + "] closeable hashcode=" + String.valueOf(closeable.hashCode()));
                closeable.close();
            } catch (RuntimeException rethrown) {
                NBNetLogCat.a((String) "closeQuietly", (Throwable) rethrown);
            } catch (Throwable ignored) {
                NBNetLogCat.a((String) "closeQuietly", ignored);
            }
        }
    }

    public static int a(String scheme) {
        if ("http".equalsIgnoreCase(scheme)) {
            return 80;
        }
        if ("https".equalsIgnoreCase(scheme)) {
            return 443;
        }
        return -1;
    }

    public static final int a(int port, String protocol) {
        return port < 0 ? a(protocol) : port;
    }

    public static final int b(String contentLengthStr) {
        int contentLength = -1;
        if (TextUtils.isEmpty(contentLengthStr)) {
            return contentLength;
        }
        try {
            return Integer.parseInt(contentLengthStr);
        } catch (NumberFormatException e) {
            NBNetLogCat.a((String) "NBNetCommonUtil", (Throwable) e);
            return contentLength;
        }
    }

    public static ThreadFactory c(final String name) {
        return new ThreadFactory() {
            public final Thread newThread(Runnable runnable) {
                Process.setThreadPriority(-4);
                Thread result = new Thread(runnable, name);
                result.setDaemon(true);
                return result;
            }
        };
    }

    public static final boolean a(Throwable rootCause) {
        Throwable rootCause2 = MiscUtils.getRootCause(rootCause);
        if ((rootCause2 instanceof SSLPeerUnverifiedException) || (rootCause2 instanceof SSLHandshakeException) || (rootCause2 instanceof ConnectException) || (rootCause2 instanceof SSLException) || (rootCause2 instanceof SocketException) || (rootCause2 instanceof SocketTimeoutException) || (rootCause2 instanceof UnknownHostException) || (rootCause2 instanceof IOException)) {
            return true;
        }
        Class errnoExceptionClass = a();
        if (errnoExceptionClass != null) {
            return a(errnoExceptionClass, (Class) rootCause2.getClass());
        }
        return false;
    }

    private static boolean a(Class parenClass, Class objClass) {
        return parenClass.isAssignableFrom(objClass);
    }

    public static Class a() {
        if (a != null) {
            return a;
        }
        try {
            a = Class.forName("android.system.ErrnoException");
        } catch (Throwable e) {
            NBNetLogCat.d("NBNetCommonUtil", "getErrnoExceptionClass. forName exception: " + e.toString());
        }
        if (a != null) {
            return a;
        }
        try {
            a = Class.forName("libcore.io.ErrnoException");
        } catch (Throwable e2) {
            NBNetLogCat.d("NBNetCommonUtil", "getErrnoExceptionClass. forName exception: " + e2.toString());
        }
        return a;
    }

    public static final NetworkThreadFactory a(String name, ThreadPoolExecutor threadPoolExecutor) {
        return new NetworkThreadFactory(name, threadPoolExecutor);
    }
}
