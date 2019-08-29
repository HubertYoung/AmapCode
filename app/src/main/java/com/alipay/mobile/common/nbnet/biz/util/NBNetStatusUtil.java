package com.alipay.mobile.common.nbnet.biz.util;

import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class NBNetStatusUtil {
    private static final Map<Class, Integer> a;

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        a = linkedHashMap;
        linkedHashMap.put(SSLPeerUnverifiedException.class, Integer.valueOf(-13));
        a.put(SSLHandshakeException.class, Integer.valueOf(-13));
        a.put(SSLException.class, Integer.valueOf(-13));
        a.put(SocketTimeoutException.class, Integer.valueOf(-6));
        a.put(ConnectException.class, Integer.valueOf(-5));
        a.put(SocketException.class, Integer.valueOf(-5));
        a.put(UnknownHostException.class, Integer.valueOf(-16));
        a.put(IOException.class, Integer.valueOf(-5));
        a.put(EOFException.class, Integer.valueOf(-20));
        Class errnoClass = NBNetCommonUtil.a();
        if (errnoClass != null) {
            a.put(errnoClass, Integer.valueOf(-5));
        }
    }

    public static final int a(Throwable orginThrowable) {
        if (orginThrowable == null) {
            return -1;
        }
        Throwable rootCause = MiscUtils.getRootCause(orginThrowable);
        if (rootCause instanceof NBNetException) {
            return ((NBNetException) orginThrowable).getErrorCode();
        }
        for (Entry entry : a.entrySet()) {
            if (((Class) entry.getKey()).isAssignableFrom(rootCause.getClass())) {
                return ((Integer) entry.getValue()).intValue();
            }
        }
        if (rootCause != null && rootCause.getClass().getSimpleName().equalsIgnoreCase("ErrnoException")) {
            return -5;
        }
        if (orginThrowable instanceof NBNetException) {
            return ((NBNetException) orginThrowable).getErrorCode();
        }
        return -1;
    }
}
