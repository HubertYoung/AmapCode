package com.alipay.deviceid.module.rpc.mrpc.core;

import android.os.Looper;
import com.alipay.deviceid.module.rpc.mrpc.annotation.OperationType;
import com.alipay.deviceid.module.rpc.mrpc.annotation.ResetCookie;
import com.alipay.deviceid.module.x.ar;
import com.alipay.deviceid.module.x.as;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class z {
    private static final ThreadLocal<Object> a = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, Object>> b = new ThreadLocal<>();
    private byte c = 0;
    private AtomicInteger d;
    private x e;

    public z(x xVar) {
        this.e = xVar;
        this.d = new AtomicInteger();
    }

    public final Object a(Method method, Object[] objArr) {
        if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalThreadStateException("can't in main thread call rpc .");
        }
        OperationType operationType = (OperationType) method.getAnnotation(OperationType.class);
        boolean z = method.getAnnotation(ResetCookie.class) != null;
        Type genericReturnType = method.getGenericReturnType();
        method.getAnnotations();
        a.set(null);
        b.set(null);
        if (operationType == null) {
            throw new IllegalStateException("OperationType must be set.");
        }
        String value = operationType.value();
        int incrementAndGet = this.d.incrementAndGet();
        try {
            if (this.c == 0) {
                as asVar = new as(incrementAndGet, value, objArr);
                if (b.get() != null) {
                    asVar.a(b.get());
                }
                j jVar = new j(this.e.a(), method, incrementAndGet, value, asVar.a(), z);
                b.set(null);
                Object a2 = new ar(genericReturnType, (byte[]) jVar.a()).a();
                if (genericReturnType != Void.TYPE) {
                    a.set(a2);
                }
            }
            return a.get();
        } catch (RpcException e2) {
            e2.setOperationType(value);
            throw e2;
        }
    }
}
