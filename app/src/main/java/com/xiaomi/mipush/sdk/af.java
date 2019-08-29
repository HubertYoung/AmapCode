package com.xiaomi.mipush.sdk;

import com.xiaomi.xmpush.thrift.a;

/* synthetic */ class af {
    static final /* synthetic */ int[] a = new int[a.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(20:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|(3:19|20|22)) */
    /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|22) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
    static {
        /*
            com.xiaomi.xmpush.thrift.a[] r0 = com.xiaomi.xmpush.thrift.a.values()
            int r0 = r0.length
            int[] r0 = new int[r0]
            a = r0
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.Registration     // Catch:{ NoSuchFieldError -> 0x0014 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.UnRegistration     // Catch:{ NoSuchFieldError -> 0x001f }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.Subscription     // Catch:{ NoSuchFieldError -> 0x002a }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.UnSubscription     // Catch:{ NoSuchFieldError -> 0x0035 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.SendMessage     // Catch:{ NoSuchFieldError -> 0x0040 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
            r2 = 5
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.AckMessage     // Catch:{ NoSuchFieldError -> 0x004b }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
            r2 = 6
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.SetConfig     // Catch:{ NoSuchFieldError -> 0x0056 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
            r2 = 7
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
        L_0x0056:
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.ReportFeedback     // Catch:{ NoSuchFieldError -> 0x0062 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
            r2 = 8
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
        L_0x0062:
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006e }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.Notification     // Catch:{ NoSuchFieldError -> 0x006e }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006e }
            r2 = 9
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006e }
        L_0x006e:
            int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x007a }
            com.xiaomi.xmpush.thrift.a r1 = com.xiaomi.xmpush.thrift.a.Command     // Catch:{ NoSuchFieldError -> 0x007a }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007a }
            r2 = 10
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007a }
        L_0x007a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.af.<clinit>():void");
    }
}
