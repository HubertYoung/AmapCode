package com.alipay.apmobilesecuritysdk.proxydetect;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.storage.SettingsStorage;
import com.alipay.mobile.security.stun.StunClient;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

public class WebRTCClient {
    private static WebRTCClient a;
    /* access modifiers changed from: private */
    public static Object b = new Object();
    private final int c = 1000;
    /* access modifiers changed from: private */
    public String d = "";
    /* access modifiers changed from: private */
    public AtomicBoolean e = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public String f = null;
    /* access modifiers changed from: private */
    public int g = -1;
    private TraceLogger h = LoggerFactory.f();

    class StunWorker implements Runnable {
        public StunWorker() {
        }

        public void run() {
            Object c;
            synchronized (WebRTCClient.b) {
                try {
                    TraceLogger f = LoggerFactory.f();
                    StringBuilder sb = new StringBuilder("StunWorker(),开始时间 : ");
                    sb.append(System.currentTimeMillis());
                    f.b((String) CONST.LOG_TAG, sb.toString());
                    WebRTCClient.this.d = StunClient.a(WebRTCClient.this.f, WebRTCClient.this.g);
                    TraceLogger f2 = LoggerFactory.f();
                    StringBuilder sb2 = new StringBuilder("StunWorker(),REAL IP : ");
                    sb2.append(WebRTCClient.this.d);
                    sb2.append(", 结束时间 : ");
                    sb2.append(System.currentTimeMillis());
                    f2.b((String) CONST.LOG_TAG, sb2.toString());
                    WebRTCClient.this.e.set(false);
                    c = WebRTCClient.b;
                } catch (Exception e) {
                    try {
                        TraceLogger f3 = LoggerFactory.f();
                        StringBuilder sb3 = new StringBuilder("StunWorker(),Exception : ");
                        sb3.append(CommonUtils.getStackString(e));
                        f3.b((String) CONST.LOG_TAG, sb3.toString());
                        WebRTCClient.this.e.set(false);
                        c = WebRTCClient.b;
                    } catch (Throwable th) {
                        WebRTCClient.this.e.set(false);
                        WebRTCClient.b.notifyAll();
                        throw th;
                    }
                }
                c.notifyAll();
            }
        }
    }

    private WebRTCClient(Context context) {
        String g2 = SettingsStorage.g(context);
        try {
            if (CommonUtils.isNotBlank(g2) && g2.contains("host") && g2.contains("port")) {
                LoggerFactory.f().b((String) CONST.LOG_TAG, (String) "WebRTCClient 开始构造");
                JSONObject jSONObject = new JSONObject(g2);
                this.f = jSONObject.getString("host");
                this.g = Integer.parseInt(jSONObject.getString("port"));
                LoggerFactory.f().b((String) CONST.LOG_TAG, (String) "WebRTCClient 构造成功");
            }
        } catch (Exception unused) {
            LoggerFactory.f().b((String) CONST.LOG_TAG, (String) "WebRTCClient throw exception");
            this.f = null;
            this.g = -1;
        }
    }

    public static WebRTCClient a(Context context) {
        if (a == null) {
            synchronized (WebRTCClient.class) {
                try {
                    if (a == null) {
                        a = new WebRTCClient(context);
                    }
                }
            }
        }
        return a;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:4|5|(2:7|8)|9|10|11) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0041 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a() {
        /*
            r6 = this;
            java.util.concurrent.atomic.AtomicBoolean r0 = r6.e
            boolean r0 = r0.get()
            if (r0 == 0) goto L_0x0061
            java.lang.Object r0 = b
            monitor-enter(r0)
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x005e }
            java.lang.String r2 = "APSecuritySdk"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x005e }
            java.lang.String r4 = "getMapResult(), 开始时间 : "
            r3.<init>(r4)     // Catch:{ all -> 0x005e }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x005e }
            r3.append(r4)     // Catch:{ all -> 0x005e }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x005e }
            r1.b(r2, r3)     // Catch:{ all -> 0x005e }
            java.util.concurrent.atomic.AtomicBoolean r1 = r6.e     // Catch:{ all -> 0x005e }
            boolean r1 = r1.get()     // Catch:{ all -> 0x005e }
            if (r1 == 0) goto L_0x0041
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Exception -> 0x0041 }
            java.lang.String r2 = "APSecuritySdk"
            java.lang.String r3 = "wait"
            r1.b(r2, r3)     // Catch:{ Exception -> 0x0041 }
            java.lang.Object r1 = b     // Catch:{ Exception -> 0x0041 }
            r2 = 1000(0x3e8, double:4.94E-321)
            r1.wait(r2)     // Catch:{ Exception -> 0x0041 }
        L_0x0041:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x005e }
            java.lang.String r2 = "APSecuritySdk"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x005e }
            java.lang.String r4 = "getMapResult(), 结束时间 : "
            r3.<init>(r4)     // Catch:{ all -> 0x005e }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x005e }
            r3.append(r4)     // Catch:{ all -> 0x005e }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x005e }
            r1.b(r2, r3)     // Catch:{ all -> 0x005e }
            monitor-exit(r0)     // Catch:{ all -> 0x005e }
            goto L_0x0061
        L_0x005e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005e }
            throw r1
        L_0x0061:
            java.lang.String r0 = r6.d
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.apmobilesecuritysdk.proxydetect.WebRTCClient.a():java.lang.String");
    }

    public final void b() {
        TraceLogger traceLogger = this.h;
        StringBuilder sb = new StringBuilder("getMappedAddressAsync(),stun server = ");
        sb.append(this.f);
        sb.append(":");
        sb.append(this.g);
        traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        if (this.f != null && this.f.length() > 0 && this.g > 0 && !this.e.getAndSet(true)) {
            new Thread(new StunWorker()).start();
        }
    }
}
