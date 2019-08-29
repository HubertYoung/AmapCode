package com.alipay.mobile.common.transport.interceptors;

import com.alipay.mobile.common.transport.http.HttpException;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransportInterceptorManager {
    private static TransportInterceptorManager a;
    private List<TransportInterceptor> b;

    public static final TransportInterceptorManager getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (TransportInterceptorManager.class) {
            try {
                if (a != null) {
                    TransportInterceptorManager transportInterceptorManager = a;
                    return transportInterceptorManager;
                }
                a = new TransportInterceptorManager();
                return a;
            }
        }
    }

    public synchronized void addInterceptor(TransportInterceptor transportInterceptor) {
        if (transportInterceptor == null) {
            LogCatUtil.warn((String) "TransportInterceptorManager", (String) "addInterceptor.   Illegal argument, transportInterceptor maybe null");
        } else if (a().contains(transportInterceptor)) {
            LogCatUtil.warn((String) "TransportInterceptorManager", (String) "addInterceptor.   Illegal argument, transportInterceptor already exists.");
        } else {
            a().add(transportInterceptor);
        }
    }

    public synchronized void removeInterceptor(TransportInterceptor transportInterceptor) {
        if (!(this.b == null || this.b.isEmpty() || transportInterceptor == null)) {
            a().remove(transportInterceptor);
        }
    }

    public synchronized void onPreTransportInterceptor(String url, Map<String, String> extMap) {
        if (this.b != null && !this.b.isEmpty()) {
            for (TransportInterceptor transportInterceptor : this.b) {
                long startTime = System.currentTimeMillis();
                try {
                    transportInterceptor.preRequestInterceptor(url, extMap);
                    LogCatUtil.warn("TransportInterceptorManager", "preRequestInterceptor finish. obj = [" + transportInterceptor.getClass().getName() + "], cost time: " + (System.currentTimeMillis() + startTime));
                } catch (Throwable e) {
                    LogCatUtil.warn("TransportInterceptorManager", "onPreTransportInterceptor. interceptor=" + transportInterceptor.getClass().getName() + ", exception message: ", e);
                    if (e instanceof HttpException) {
                        throw ((HttpException) e);
                    }
                    LogCatUtil.warn((String) "TransportInterceptorManager", "preRequestInterceptor finish. obj = [" + transportInterceptor.getClass().getName() + "], cost time: " + (System.currentTimeMillis() + startTime));
                } finally {
                    LogCatUtil.warn((String) "TransportInterceptorManager", "preRequestInterceptor finish. obj = [" + transportInterceptor.getClass().getName() + "], cost time: " + (System.currentTimeMillis() + startTime));
                }
            }
        }
    }

    private List<TransportInterceptor> a() {
        if (this.b != null) {
            return this.b;
        }
        synchronized (this) {
            if (this.b != null) {
                List<TransportInterceptor> list = this.b;
                return list;
            }
            ArrayList arrayList = new ArrayList(1);
            this.b = arrayList;
            return arrayList;
        }
    }
}
