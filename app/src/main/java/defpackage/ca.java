package defpackage;

import java.util.Map;
import java.util.Random;

/* renamed from: ca reason: default package */
/* compiled from: AmdcTaskExecutor */
public class ca {
    public static Random b = new Random();
    public Map<String, Object> a;

    /* renamed from: ca$a */
    /* compiled from: AmdcTaskExecutor */
    public class a implements Runnable {
        private Map<String, Object> b;

        public a(Map<String, Object> map) {
            this.b = map;
        }

        public a() {
        }

        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r9 = this;
                r0 = 0
                r1 = 0
                java.util.Map<java.lang.String, java.lang.Object> r2 = r9.b     // Catch:{ Exception -> 0x018f }
                if (r2 != 0) goto L_0x001a
                java.lang.Class<ca> r2 = defpackage.ca.class
                monitor-enter(r2)     // Catch:{ Exception -> 0x018f }
                ca r3 = defpackage.ca.this     // Catch:{ all -> 0x0017 }
                java.util.Map r3 = r3.a     // Catch:{ all -> 0x0017 }
                ca r4 = defpackage.ca.this     // Catch:{ all -> 0x0017 }
                r4.a = null     // Catch:{ all -> 0x0017 }
                monitor-exit(r2)     // Catch:{ all -> 0x0017 }
                r2 = r3
                goto L_0x001a
            L_0x0017:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0017 }
                throw r3     // Catch:{ Exception -> 0x018f }
            L_0x001a:
                boolean r3 = anet.channel.status.NetworkStatusHelper.h()     // Catch:{ Exception -> 0x018f }
                if (r3 != 0) goto L_0x0021
                return
            L_0x0021:
                anet.channel.entity.ENV r3 = defpackage.m.d()     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "Env"
                java.lang.Object r4 = r2.get(r4)     // Catch:{ Exception -> 0x018f }
                if (r3 == r4) goto L_0x0037
                java.lang.String r2 = "awcn.AmdcThreadPoolExecutor"
                java.lang.String r3 = "task's env changed"
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x018f }
                defpackage.cl.c(r2, r3, r1, r4)     // Catch:{ Exception -> 0x018f }
                return
            L_0x0037:
                cg r3 = defpackage.bz.b()     // Catch:{ Exception -> 0x018f }
                if (r3 == 0) goto L_0x0181
                java.lang.String r4 = r3.a()     // Catch:{ Exception -> 0x018f }
                boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x018f }
                if (r4 == 0) goto L_0x0049
                goto L_0x0181
            L_0x0049:
                anet.channel.status.NetworkStatusHelper$NetworkStatus r4 = anet.channel.status.NetworkStatusHelper.a()     // Catch:{ Exception -> 0x018f }
                boolean r5 = anet.channel.status.NetworkStatusHelper.h()     // Catch:{ Exception -> 0x018f }
                if (r5 != 0) goto L_0x005e
                java.lang.String r2 = "amdc.DispatchParamBuilder"
                java.lang.String r3 = "no network, don't send amdc request"
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x018f }
                defpackage.cl.d(r2, r3, r1, r4)     // Catch:{ Exception -> 0x018f }
                goto L_0x018a
            L_0x005e:
                java.lang.String r5 = "appkey"
                java.lang.String r6 = r3.a()     // Catch:{ Exception -> 0x018f }
                r2.put(r5, r6)     // Catch:{ Exception -> 0x018f }
                java.lang.String r5 = "v"
                java.lang.String r6 = "5.0"
                r2.put(r5, r6)     // Catch:{ Exception -> 0x018f }
                java.lang.String r5 = "platform"
                java.lang.String r6 = "android"
                r2.put(r5, r6)     // Catch:{ Exception -> 0x018f }
                java.lang.String r5 = "platformVersion"
                java.lang.String r6 = android.os.Build.VERSION.RELEASE     // Catch:{ Exception -> 0x018f }
                r2.put(r5, r6)     // Catch:{ Exception -> 0x018f }
                java.lang.String r5 = defpackage.m.f()     // Catch:{ Exception -> 0x018f }
                boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x018f }
                if (r5 != 0) goto L_0x0090
                java.lang.String r5 = "sid"
                java.lang.String r6 = defpackage.m.f()     // Catch:{ Exception -> 0x018f }
                r2.put(r5, r6)     // Catch:{ Exception -> 0x018f }
            L_0x0090:
                java.lang.String r5 = defpackage.m.g()     // Catch:{ Exception -> 0x018f }
                boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x018f }
                if (r5 != 0) goto L_0x00a3
                java.lang.String r5 = "deviceId"
                java.lang.String r6 = defpackage.m.g()     // Catch:{ Exception -> 0x018f }
                r2.put(r5, r6)     // Catch:{ Exception -> 0x018f }
            L_0x00a3:
                java.lang.String r5 = "netType"
                java.lang.String r6 = r4.toString()     // Catch:{ Exception -> 0x018f }
                r2.put(r5, r6)     // Catch:{ Exception -> 0x018f }
                boolean r4 = r4.isWifi()     // Catch:{ Exception -> 0x018f }
                if (r4 == 0) goto L_0x00bb
                java.lang.String r4 = "bssid"
                java.lang.String r5 = anet.channel.status.NetworkStatusHelper.g()     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
            L_0x00bb:
                java.lang.String r4 = "carrier"
                java.lang.String r5 = anet.channel.status.NetworkStatusHelper.d()     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "mnc"
                java.lang.String r5 = anet.channel.status.NetworkStatusHelper.e()     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "lat"
                double r5 = defpackage.bz.a     // Catch:{ Exception -> 0x018f }
                java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "lng"
                double r5 = defpackage.bz.b     // Catch:{ Exception -> 0x018f }
                java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.util.Map r4 = defpackage.bz.c()     // Catch:{ Exception -> 0x018f }
                r2.putAll(r4)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "channel"
                java.lang.String r5 = defpackage.bz.c     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "appName"
                java.lang.String r5 = defpackage.bz.d     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "appVersion"
                java.lang.String r5 = defpackage.bz.e     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "stackType"
                int r5 = defpackage.ct.c()     // Catch:{ Exception -> 0x018f }
                r6 = 1
                r7 = 4
                switch(r5) {
                    case 1: goto L_0x010f;
                    case 2: goto L_0x010d;
                    case 3: goto L_0x010b;
                    default: goto L_0x010a;
                }     // Catch:{ Exception -> 0x018f }
            L_0x010a:
                goto L_0x010f
            L_0x010b:
                r7 = 1
                goto L_0x010f
            L_0x010d:
                r5 = 2
                r7 = 2
            L_0x010f:
                java.lang.String r5 = java.lang.Integer.toString(r7)     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "domain"
                java.lang.String r5 = "hosts"
                java.lang.Object r5 = r2.remove(r5)     // Catch:{ Exception -> 0x018f }
                java.util.Set r5 = (java.util.Set) r5     // Catch:{ Exception -> 0x018f }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x018f }
                r7.<init>()     // Catch:{ Exception -> 0x018f }
                java.util.Iterator r5 = r5.iterator()     // Catch:{ Exception -> 0x018f }
            L_0x0129:
                boolean r8 = r5.hasNext()     // Catch:{ Exception -> 0x018f }
                if (r8 == 0) goto L_0x013e
                java.lang.Object r8 = r5.next()     // Catch:{ Exception -> 0x018f }
                java.lang.String r8 = (java.lang.String) r8     // Catch:{ Exception -> 0x018f }
                r7.append(r8)     // Catch:{ Exception -> 0x018f }
                r8 = 32
                r7.append(r8)     // Catch:{ Exception -> 0x018f }
                goto L_0x0129
            L_0x013e:
                int r5 = r7.length()     // Catch:{ Exception -> 0x018f }
                if (r5 <= 0) goto L_0x014c
                int r5 = r7.length()     // Catch:{ Exception -> 0x018f }
                int r5 = r5 - r6
                r7.deleteCharAt(r5)     // Catch:{ Exception -> 0x018f }
            L_0x014c:
                java.lang.String r5 = r7.toString()     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "signType"
                boolean r5 = r3.b()     // Catch:{ Exception -> 0x018f }
                if (r5 == 0) goto L_0x015e
                java.lang.String r5 = "sec"
                goto L_0x0160
            L_0x015e:
                java.lang.String r5 = "noSec"
            L_0x0160:
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r4 = "t"
                long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x018f }
                java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x018f }
                r2.put(r4, r5)     // Catch:{ Exception -> 0x018f }
                java.lang.String r3 = defpackage.ce.a(r3, r2)     // Catch:{ Exception -> 0x018f }
                boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x018f }
                if (r4 == 0) goto L_0x017b
                goto L_0x018a
            L_0x017b:
                java.lang.String r4 = "sign"
                r2.put(r4, r3)     // Catch:{ Exception -> 0x018f }
                goto L_0x018b
            L_0x0181:
                java.lang.String r2 = "amdc.DispatchParamBuilder"
                java.lang.String r3 = "amdc sign is null or appkey is empty"
                java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x018f }
                defpackage.cl.d(r2, r3, r1, r4)     // Catch:{ Exception -> 0x018f }
            L_0x018a:
                r2 = r1
            L_0x018b:
                defpackage.cc.a(r2)     // Catch:{ Exception -> 0x018f }
                return
            L_0x018f:
                java.lang.String r2 = "awcn.AmdcThreadPoolExecutor"
                java.lang.String r3 = "exec amdc task failed."
                java.lang.Object[] r0 = new java.lang.Object[r0]
                defpackage.cl.e(r2, r3, r1, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.ca.a.run():void");
        }
    }

    ca() {
    }
}
