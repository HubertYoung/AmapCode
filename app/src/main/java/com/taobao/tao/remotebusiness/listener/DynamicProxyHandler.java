package com.taobao.tao.remotebusiness.listener;

import com.taobao.tao.remotebusiness.MtopBusiness;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class DynamicProxyHandler implements InvocationHandler {
    private MtopCacheListenerImpl cacheListener;
    private MtopFinishListenerImpl finishListener;
    private few listener;
    private MtopBusiness mtopBusiness;
    private MtopProgressListenerImpl progressListener;

    public DynamicProxyHandler(MtopBusiness mtopBusiness2, few few) {
        this.finishListener = new MtopFinishListenerImpl(mtopBusiness2, few);
        this.mtopBusiness = mtopBusiness2;
        this.listener = few;
    }

    private MtopProgressListenerImpl getProgressListener() {
        if (this.progressListener == null) {
            this.progressListener = new MtopProgressListenerImpl(this.mtopBusiness, this.listener);
        }
        return this.progressListener;
    }

    private MtopCacheListenerImpl getCacheListener() {
        if (this.cacheListener == null) {
            this.cacheListener = new MtopCacheListenerImpl(this.mtopBusiness, this.listener);
        }
        return this.cacheListener;
    }

    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        if (method.getName().equals("onFinished")) {
            return method.invoke(this.finishListener, objArr);
        }
        if (method.getName().equals("onDataReceived") || method.getName().equals("onHeader")) {
            return method.invoke(getProgressListener(), objArr);
        }
        if (method.getName().equals("onCached")) {
            return method.invoke(getCacheListener(), objArr);
        }
        return null;
    }
}
