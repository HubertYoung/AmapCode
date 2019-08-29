package com.taobao.tao.remotebusiness.listener;

import com.taobao.tao.remotebusiness.IRemoteCacheListener;
import com.taobao.tao.remotebusiness.IRemoteProcessListener;
import com.taobao.tao.remotebusiness.MtopBusiness;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class MtopListenerProxyFactory {
    public static few getMtopListenerProxy(MtopBusiness mtopBusiness, few few) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(b.class);
        if (few instanceof IRemoteProcessListener) {
            arrayList.add(d.class);
            arrayList.add(c.class);
        }
        if ((few instanceof IRemoteCacheListener) || mtopBusiness.mtopProp.useCache) {
            arrayList.add(a.class);
        }
        return (few) Proxy.newProxyInstance(few.class.getClassLoader(), (Class[]) arrayList.toArray(new Class[arrayList.size()]), new DynamicProxyHandler(mtopBusiness, few));
    }
}
