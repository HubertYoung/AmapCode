package com.alipay.mobile.common.nbnet.biz;

import com.alipay.mobile.common.nbnet.api.NBNetInterceptor;
import com.alipay.mobile.common.nbnet.biz.download.DownloadFuseInterceptor;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetFuseException;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NBNetInterceptorManager {
    private static final String a = NBNetInterceptorManager.class.getSimpleName();
    private static volatile NBNetInterceptorManager b;
    private List<NBNetInterceptor> c = new CopyOnWriteArrayList();

    private NBNetInterceptorManager() {
        this.c.add(new DownloadFuseInterceptor());
    }

    public static NBNetInterceptorManager a() {
        if (b != null) {
            return b;
        }
        synchronized (NBNetInterceptorManager.class) {
            try {
                NBNetInterceptorManager tmp = new NBNetInterceptorManager();
                if (b == null) {
                    b = tmp;
                }
            }
        }
        return b;
    }

    public final void a(NBNetInterceptor interceptor) {
        if (interceptor != null) {
            this.c.add(interceptor);
        }
    }

    public final void a(String resId, long dataLen) {
        for (NBNetInterceptor interceptor : this.c) {
            try {
                interceptor.reportTransmittedTraffic(1, resId, dataLen);
            } catch (Throwable t) {
                NBNetLogCat.b(a, t);
            }
        }
    }

    public final void b(String resId, long dataLen) {
        for (NBNetInterceptor interceptor : this.c) {
            try {
                interceptor.reportReceivedTraffic(1, resId, dataLen);
            } catch (Throwable t) {
                NBNetLogCat.b(a, t);
            }
        }
    }

    public final void a(String resId) {
        for (NBNetInterceptor interceptor : this.c) {
            try {
                interceptor.intercept(1, resId);
            } catch (IOException e) {
                throw new NBNetFuseException("intercepted by " + interceptor.getClass().getName(), e);
            } catch (Throwable t) {
                NBNetLogCat.b(a, t);
            }
        }
    }
}
