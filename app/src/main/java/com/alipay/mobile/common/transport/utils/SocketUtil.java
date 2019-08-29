package com.alipay.mobile.common.transport.utils;

import android.os.Build.VERSION;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import java.io.FileDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;

public class SocketUtil {
    private static Class a;
    private static Class b;
    private static Class c;
    private static Object d;
    private static Method e;
    private static Method f;
    private static Method g;
    private static Method h;
    private static Method i;
    private static Integer j;
    private static Integer k;

    class SndTimeoutInitModel {
        FileDescriptor fileDescriptor = null;
        boolean result = false;
        Integer so_sndtimeo = null;
        Integer sol_socket = null;

        SndTimeoutInitModel() {
        }

        public static SndTimeoutInitModel getInstance(Socket socket) {
            SndTimeoutInitModel sndTimeoutInitModel = new SndTimeoutInitModel();
            if (socket == null) {
                LogCatUtil.warn((String) "SocketUtil", (String) "socket is null");
            } else if (socket.isClosed()) {
                LogCatUtil.warn((String) "SocketUtil", (String) "socket is closed");
            } else {
                sndTimeoutInitModel.sol_socket = SocketUtil.h();
                if (sndTimeoutInitModel.sol_socket == null) {
                    LogCatUtil.warn((String) "SocketUtil", (String) "sol_socket is closed");
                } else {
                    sndTimeoutInitModel.so_sndtimeo = SocketUtil.i();
                    if (sndTimeoutInitModel.so_sndtimeo == null) {
                        LogCatUtil.warn((String) "SocketUtil", (String) "so_sndtimeo is closed");
                    } else {
                        Method tmpGetFileDescriptorMethod = SocketUtil.a((Class) socket.getClass());
                        if (tmpGetFileDescriptorMethod == null) {
                            LogCatUtil.warn((String) "SocketUtil", (String) "getGetFileDescriptorMethod return null");
                        } else {
                            try {
                                sndTimeoutInitModel.fileDescriptor = (FileDescriptor) tmpGetFileDescriptorMethod.invoke(socket, new Object[0]);
                                if (sndTimeoutInitModel.fileDescriptor == null) {
                                    LogCatUtil.warn((String) "SocketUtil", (String) "fileDescriptor is null");
                                } else {
                                    sndTimeoutInitModel.result = true;
                                }
                            } catch (Throwable e) {
                                LogCatUtil.warn("SocketUtil", "Invoke getFileDescriptor method fail", e);
                            }
                        }
                    }
                }
            }
            return sndTimeoutInitModel;
        }
    }

    public static final boolean setSndTimeOut(Socket socket, long timeoutMs) {
        try {
            if (TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.SET_SND_TIMEOUT_SWITCH, "T")) {
                return doSetSndTimeOut(socket, timeoutMs);
            }
            LogCatUtil.info("SocketUtil", "setSndTimeOut. SET_SND_TIMEOUT_SWITCH off");
            return false;
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "setSndTimeOut fail", e2);
            return false;
        }
    }

    public static final Integer getSndTimeOut(Socket socket) {
        try {
            if (TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.SET_SND_TIMEOUT_SWITCH, "T")) {
                return a(socket);
            }
            LogCatUtil.info("SocketUtil", "getSndTimeOut. SET_SND_TIMEOUT_SWITCH off");
            return null;
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "getSndTimeOut fail", e2);
            return null;
        }
    }

    private static final Integer a(Socket socket) {
        SndTimeoutInitModel sndTimeoutInitModel = SndTimeoutInitModel.getInstance(socket);
        if (!sndTimeoutInitModel.result) {
            return null;
        }
        return a(sndTimeoutInitModel.fileDescriptor, sndTimeoutInitModel.sol_socket.intValue(), sndTimeoutInitModel.so_sndtimeo.intValue());
    }

    protected static boolean doSetSndTimeOut(Socket socket, long timeoutMs) {
        SndTimeoutInitModel sndTimeoutInitModel = SndTimeoutInitModel.getInstance(socket);
        if (!sndTimeoutInitModel.result) {
            return false;
        }
        return a(sndTimeoutInitModel.fileDescriptor, sndTimeoutInitModel.sol_socket.intValue(), sndTimeoutInitModel.so_sndtimeo.intValue(), timeoutMs);
    }

    private static final Integer a(FileDescriptor fd, int level, int option) {
        Integer num = null;
        Method tmpGetSockoptTimevalMethod = d();
        if (tmpGetSockoptTimevalMethod == null) {
            return num;
        }
        Method tmpToMillisMethod = j();
        if (tmpToMillisMethod == null) {
            return num;
        }
        try {
            Long timeval = (Long) tmpToMillisMethod.invoke(tmpGetSockoptTimevalMethod.invoke(b(), new Object[]{fd, Integer.valueOf(level), Integer.valueOf(option)}), new Object[0]);
            LogCatUtil.info("SocketUtil", "getSockoptTimeval is " + timeval);
            return Integer.valueOf(timeval.intValue());
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "getSockoptTimeval fail", e2);
            return num;
        }
    }

    private static final boolean a(FileDescriptor fd, int level, int option, long timeoutMs) {
        Method tmpSetsockoptTimevalMethod = c();
        if (tmpSetsockoptTimevalMethod == null) {
            return false;
        }
        Object structTimevalObj = a(timeoutMs);
        if (structTimevalObj == null) {
            return false;
        }
        try {
            tmpSetsockoptTimevalMethod.invoke(b(), new Object[]{fd, Integer.valueOf(level), Integer.valueOf(option), structTimevalObj});
            return true;
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "SetsockoptTimeval fail", e2);
            return false;
        }
    }

    private static Object a(long timeoutMs) {
        Object obj = null;
        Method tmpFromMillisMethod = f();
        if (tmpFromMillisMethod == null) {
            return obj;
        }
        try {
            return tmpFromMillisMethod.invoke(e(), new Object[]{Long.valueOf(timeoutMs)});
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "invoke fromMillis fail.", e2);
            return obj;
        }
    }

    private static final Class a() {
        if (a != null) {
            return a;
        }
        try {
            Class<?> cls = Class.forName("libcore.io.Libcore");
            a = cls;
            if (cls == null) {
                LogCatUtil.warn((String) "SocketUtil", (String) "libcoreClass loaded , but be null");
            }
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "getLibcoreClass exception.", e2);
        }
        return null;
    }

    private static final Object b() {
        if (d != null) {
            return d;
        }
        Class tmpLibcoreClass = a();
        if (tmpLibcoreClass == null) {
            return null;
        }
        try {
            Field osField = tmpLibcoreClass.getDeclaredField("os");
            osField.setAccessible(true);
            Object obj = osField.get(tmpLibcoreClass);
            d = obj;
            if (obj == null) {
                LogCatUtil.warn((String) "SocketUtil", (String) "os get finish , but be null");
            }
            return d;
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "getOsField fail.", e2);
            return null;
        }
    }

    private static final Method c() {
        if (e != null) {
            return e;
        }
        Object osObject = b();
        if (osObject == null) {
            return null;
        }
        Class tmpStructTimevalClass = e();
        if (tmpStructTimevalClass == null) {
            return null;
        }
        try {
            Method method = osObject.getClass().getMethod("setsockoptTimeval", new Class[]{FileDescriptor.class, Integer.TYPE, Integer.TYPE, tmpStructTimevalClass});
            e = method;
            method.setAccessible(true);
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "get setsockoptTimeval method fail.", e2);
        }
        return e;
    }

    private static final Method d() {
        if (f != null) {
            return f;
        }
        Object osObject = b();
        if (osObject == null) {
            return null;
        }
        try {
            Method method = osObject.getClass().getMethod("getsockoptTimeval", new Class[]{FileDescriptor.class, Integer.TYPE, Integer.TYPE});
            f = method;
            method.setAccessible(true);
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "get getSockoptTimeval method fail.", e2);
        }
        return f;
    }

    private static final Class e() {
        if (b != null) {
            return b;
        }
        if (VERSION.SDK_INT < 21) {
            try {
                b = Class.forName("libcore.io.StructTimeval");
            } catch (Throwable e2) {
                LogCatUtil.warn("SocketUtil", "Classload libcore.io.StructTimeval fail. SDK_INT:" + VERSION.SDK_INT, e2);
            }
        } else {
            try {
                b = Class.forName("android.system.StructTimeval");
            } catch (Throwable e3) {
                LogCatUtil.warn("SocketUtil", "Classload android.system.StructTimeval fail. SDK_INT:" + VERSION.SDK_INT, e3);
            }
        }
        return b;
    }

    private static final Method f() {
        Method method = null;
        if (g != null) {
            return g;
        }
        Class tmpStructTimevalClass = e();
        if (tmpStructTimevalClass == null) {
            return method;
        }
        try {
            Method declaredMethod = tmpStructTimevalClass.getDeclaredMethod("fromMillis", new Class[]{Long.TYPE});
            g = declaredMethod;
            declaredMethod.setAccessible(true);
            return g;
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "getDeclaredMethod fromMillis fail", e2);
            return method;
        }
    }

    private static final Class g() {
        if (c != null) {
            return c;
        }
        try {
            if (VERSION.SDK_INT < 21) {
                c = Class.forName("libcore.io.OsConstants");
            } else {
                c = Class.forName("android.system.OsConstants");
            }
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "Class load OsConstants fail. sdk: " + VERSION.SDK_INT, e2);
        }
        return c;
    }

    /* access modifiers changed from: private */
    public static final Integer h() {
        if (j != null) {
            return j;
        }
        Class tmpOsConstantsClass = g();
        if (tmpOsConstantsClass == null) {
            return Integer.valueOf(-1);
        }
        try {
            Field solSocketField = tmpOsConstantsClass.getDeclaredField("SOL_SOCKET");
            solSocketField.setAccessible(true);
            j = (Integer) solSocketField.get(tmpOsConstantsClass);
            LogCatUtil.info("SocketUtil", "Get SOL_SOCKET is " + j.intValue());
            return j;
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "Get SOL_SOCKET fail.", e2);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static final Integer i() {
        if (k != null) {
            return k;
        }
        Class tmpOsConstantsClass = g();
        if (tmpOsConstantsClass == null) {
            return Integer.valueOf(-1);
        }
        try {
            Field solSocketField = tmpOsConstantsClass.getDeclaredField("SO_SNDTIMEO");
            solSocketField.setAccessible(true);
            k = Integer.valueOf(((Integer) solSocketField.get(tmpOsConstantsClass)).intValue());
            LogCatUtil.info("SocketUtil", "Get SO_SNDTIMEO is " + k.intValue());
            return k;
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "Get SO_SNDTIMEO fail.", e2);
            return null;
        }
    }

    private static final Method j() {
        Method method = null;
        if (h != null) {
            return h;
        }
        Class tmpStructTimevalClass = e();
        if (tmpStructTimevalClass == null) {
            return method;
        }
        try {
            Method method2 = tmpStructTimevalClass.getMethod("toMillis", new Class[0]);
            h = method2;
            method2.setAccessible(true);
            return h;
        } catch (Throwable e2) {
            LogCatUtil.warn("SocketUtil", "Get toMillis method fail.", e2);
            return method;
        }
    }

    /* access modifiers changed from: private */
    public static final Method a(Class socketClass) {
        if (i != null) {
            return i;
        }
        if (socketClass == null) {
            LogCatUtil.warn((String) "SocketUtil", (String) "Illegal argument class is null ");
            return null;
        } else if (!Socket.class.isAssignableFrom(socketClass)) {
            LogCatUtil.warn((String) "SocketUtil", "Illegal argument class: " + socketClass.getName());
            return null;
        } else {
            try {
                Method declaredMethod = socketClass.getDeclaredMethod("getFileDescriptor$", new Class[0]);
                i = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (Throwable e2) {
                LogCatUtil.warn("SocketUtil", "Get getFileDescriptor$ method fail", e2);
            }
            return i;
        }
    }
}
