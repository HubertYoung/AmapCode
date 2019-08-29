package com.autonavi.minimap.life.common.net.impl;

import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.Callback.b;

public class NetTransferImpl<ResultType> implements AosResponseCallback<AosStringResponse>, a, dod {
    private static Handler a = new Handler(Looper.getMainLooper());
    private doe b;
    private AosRequest c;
    /* access modifiers changed from: private */
    public Callback<ResultType> d;
    private CompatDialog e;

    /* JADX WARNING: Removed duplicated region for block: B:19:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void onSuccess(com.amap.bundle.aosservice.response.AosResponse r4) {
        /*
            r3 = this;
            com.amap.bundle.aosservice.response.AosStringResponse r4 = (com.amap.bundle.aosservice.response.AosStringResponse) r4
            r0 = 0
            com.autonavi.common.Callback<ResultType> r1 = r3.d     // Catch:{ Exception -> 0x0057 }
            java.lang.Class r1 = r1.getClass()     // Catch:{ Exception -> 0x0057 }
            com.autonavi.common.Callback<ResultType> r2 = r3.d     // Catch:{ Exception -> 0x0057 }
            boolean r2 = r2 instanceof com.autonavi.common.Callback.PrepareCallback     // Catch:{ Exception -> 0x0057 }
            if (r2 == 0) goto L_0x003e
            java.lang.Class<com.autonavi.common.Callback$PrepareCallback> r2 = com.autonavi.common.Callback.PrepareCallback.class
            java.lang.reflect.Type r1 = org.xidea.el.impl.ReflectUtil.a(r1, r2)     // Catch:{ Exception -> 0x0057 }
            boolean r2 = r1 instanceof java.lang.Class     // Catch:{ Exception -> 0x0057 }
            if (r2 == 0) goto L_0x005b
            java.lang.Class r1 = (java.lang.Class) r1     // Catch:{ Exception -> 0x0057 }
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            boolean r1 = r1.isAssignableFrom(r2)     // Catch:{ Exception -> 0x0057 }
            if (r1 == 0) goto L_0x0031
            com.autonavi.common.Callback<ResultType> r1 = r3.d     // Catch:{ Exception -> 0x0057 }
            com.autonavi.common.Callback$PrepareCallback r1 = (com.autonavi.common.Callback.PrepareCallback) r1     // Catch:{ Exception -> 0x0057 }
            java.lang.String r4 = r4.getResponseBodyString()     // Catch:{ Exception -> 0x0057 }
            java.lang.Object r4 = r1.prepare(r4)     // Catch:{ Exception -> 0x0057 }
        L_0x002f:
            r0 = r4
            goto L_0x005b
        L_0x0031:
            com.autonavi.common.Callback<ResultType> r1 = r3.d     // Catch:{ Exception -> 0x0057 }
            com.autonavi.common.Callback$PrepareCallback r1 = (com.autonavi.common.Callback.PrepareCallback) r1     // Catch:{ Exception -> 0x0057 }
            byte[] r4 = r4.getResponseBodyData()     // Catch:{ Exception -> 0x0057 }
            java.lang.Object r4 = r1.prepare(r4)     // Catch:{ Exception -> 0x0057 }
            goto L_0x002f
        L_0x003e:
            java.lang.Class<com.autonavi.common.Callback> r2 = com.autonavi.common.Callback.class
            java.lang.reflect.Type r1 = org.xidea.el.impl.ReflectUtil.a(r1, r2)     // Catch:{ Exception -> 0x0057 }
            boolean r2 = r1 instanceof java.lang.Class     // Catch:{ Exception -> 0x0057 }
            if (r2 == 0) goto L_0x005b
            java.lang.Class r1 = (java.lang.Class) r1     // Catch:{ Exception -> 0x0057 }
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            boolean r1 = r1.isAssignableFrom(r2)     // Catch:{ Exception -> 0x0057 }
            if (r1 == 0) goto L_0x005b
            java.lang.String r4 = r4.getResponseBodyString()     // Catch:{ Exception -> 0x0057 }
            goto L_0x002f
        L_0x0057:
            r4 = move-exception
            r4.printStackTrace()
        L_0x005b:
            if (r0 == 0) goto L_0x0067
            android.os.Handler r4 = a
            com.autonavi.minimap.life.common.net.impl.NetTransferImpl$1 r1 = new com.autonavi.minimap.life.common.net.impl.NetTransferImpl$1
            r1.<init>(r0)
            r4.post(r1)
        L_0x0067:
            doe r4 = r3.b
            r4.a(r3)
            com.amap.bundle.utils.ui.CompatDialog r4 = r3.e
            if (r4 == 0) goto L_0x0075
            com.amap.bundle.utils.ui.CompatDialog r4 = r3.e
            r4.dismiss()
        L_0x0075:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.life.common.net.impl.NetTransferImpl.onSuccess(com.amap.bundle.aosservice.response.AosResponse):void");
    }

    public final AosRequest a() {
        return this.c;
    }

    public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        a.post(new Runnable() {
            public final void run() {
                NetTransferImpl.this.d.error(aosResponseException, false);
            }
        });
        this.b.a(this);
        if (this.e != null) {
            this.e.dismiss();
        }
    }

    public void cancel() {
        if (!this.c.isCanceled()) {
            yq.a();
            yq.a(this.c);
            if (this.d instanceof b) {
                ((b) this.d).onCancelled();
            }
            this.b.a(this);
            if (this.e != null) {
                this.e.dismiss();
            }
        }
    }

    public boolean isCancelled() {
        return this.c.isCanceled();
    }
}
