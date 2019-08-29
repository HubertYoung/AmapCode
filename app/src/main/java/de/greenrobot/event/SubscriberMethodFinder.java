package de.greenrobot.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class SubscriberMethodFinder {
    private static final Map<String, List<SubscriberMethod>> methodCache = new HashMap();
    private static final Map<Class<?>, Class<?>> skipMethodNameVerificationForClasses = new ConcurrentHashMap();

    SubscriberMethodFinder() {
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r17v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<de.greenrobot.event.SubscriberMethod> findSubscriberMethods(java.lang.Class r17, java.lang.String r18) {
        /*
            r16 = this;
            r1 = r18
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = r17.getName()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r2.<init>(r3)
            r3 = 46
            r2.append(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            java.util.Map<java.lang.String, java.util.List<de.greenrobot.event.SubscriberMethod>> r3 = methodCache
            monitor-enter(r3)
            java.util.Map<java.lang.String, java.util.List<de.greenrobot.event.SubscriberMethod>> r4 = methodCache     // Catch:{ all -> 0x0123 }
            java.lang.Object r4 = r4.get(r2)     // Catch:{ all -> 0x0123 }
            java.util.List r4 = (java.util.List) r4     // Catch:{ all -> 0x0123 }
            monitor-exit(r3)     // Catch:{ all -> 0x0123 }
            if (r4 == 0) goto L_0x002a
            return r4
        L_0x002a:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r6 = r17
        L_0x003b:
            if (r6 != 0) goto L_0x003f
            goto L_0x00f1
        L_0x003f:
            java.lang.String r7 = r6.getName()
            java.lang.String r8 = "java."
            boolean r8 = r7.startsWith(r8)
            if (r8 != 0) goto L_0x00f1
            java.lang.String r8 = "javax."
            boolean r8 = r7.startsWith(r8)
            if (r8 != 0) goto L_0x00f1
            java.lang.String r8 = "android."
            boolean r7 = r7.startsWith(r8)
            if (r7 != 0) goto L_0x00f1
            java.lang.reflect.Method[] r7 = r6.getDeclaredMethods()
            int r8 = r7.length
            r9 = 0
            r10 = 0
        L_0x0062:
            if (r10 < r8) goto L_0x0069
            java.lang.Class r6 = r6.getSuperclass()
            goto L_0x003b
        L_0x0069:
            r11 = r7[r10]
            java.lang.String r12 = r11.getName()
            boolean r13 = r12.startsWith(r1)
            if (r13 == 0) goto L_0x00ed
            java.lang.Class[] r13 = r11.getParameterTypes()
            int r14 = r13.length
            r15 = 1
            if (r14 != r15) goto L_0x00ed
            int r14 = r18.length()
            java.lang.String r14 = r12.substring(r14)
            int r15 = r14.length()
            if (r15 != 0) goto L_0x008e
            de.greenrobot.event.ThreadMode r14 = de.greenrobot.event.ThreadMode.PostThread
            goto L_0x00ae
        L_0x008e:
            java.lang.String r15 = "MainThread"
            boolean r15 = r14.equals(r15)
            if (r15 == 0) goto L_0x0099
            de.greenrobot.event.ThreadMode r14 = de.greenrobot.event.ThreadMode.MainThread
            goto L_0x00ae
        L_0x0099:
            java.lang.String r15 = "BackgroundThread"
            boolean r15 = r14.equals(r15)
            if (r15 == 0) goto L_0x00a4
            de.greenrobot.event.ThreadMode r14 = de.greenrobot.event.ThreadMode.BackgroundThread
            goto L_0x00ae
        L_0x00a4:
            java.lang.String r15 = "Async"
            boolean r14 = r14.equals(r15)
            if (r14 == 0) goto L_0x00d5
            de.greenrobot.event.ThreadMode r14 = de.greenrobot.event.ThreadMode.Async
        L_0x00ae:
            r13 = r13[r9]
            r5.setLength(r9)
            r5.append(r12)
            r12 = 62
            r5.append(r12)
            java.lang.String r12 = r13.getName()
            r5.append(r12)
            java.lang.String r12 = r5.toString()
            boolean r12 = r4.add(r12)
            if (r12 == 0) goto L_0x00ed
            de.greenrobot.event.SubscriberMethod r12 = new de.greenrobot.event.SubscriberMethod
            r12.<init>(r11, r14, r13)
            r3.add(r12)
            goto L_0x00ed
        L_0x00d5:
            java.util.Map<java.lang.Class<?>, java.lang.Class<?>> r12 = skipMethodNameVerificationForClasses
            boolean r12 = r12.containsKey(r6)
            if (r12 != 0) goto L_0x00ed
            de.greenrobot.event.EventBusException r1 = new de.greenrobot.event.EventBusException
            java.lang.String r2 = "Illegal onEvent method, check for typos: "
            java.lang.String r3 = java.lang.String.valueOf(r11)
            java.lang.String r2 = r2.concat(r3)
            r1.<init>(r2)
            throw r1
        L_0x00ed:
            int r10 = r10 + 1
            goto L_0x0062
        L_0x00f1:
            boolean r4 = r3.isEmpty()
            if (r4 == 0) goto L_0x0115
            de.greenrobot.event.EventBusException r2 = new de.greenrobot.event.EventBusException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Subscriber "
            r3.<init>(r4)
            r4 = r17
            r3.append(r4)
            java.lang.String r4 = " has no methods called "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L_0x0115:
            java.util.Map<java.lang.String, java.util.List<de.greenrobot.event.SubscriberMethod>> r1 = methodCache
            monitor-enter(r1)
            java.util.Map<java.lang.String, java.util.List<de.greenrobot.event.SubscriberMethod>> r4 = methodCache     // Catch:{ all -> 0x011f }
            r4.put(r2, r3)     // Catch:{ all -> 0x011f }
            monitor-exit(r1)     // Catch:{ all -> 0x011f }
            return r3
        L_0x011f:
            r0 = move-exception
            r2 = r0
            monitor-exit(r1)     // Catch:{ all -> 0x011f }
            throw r2
        L_0x0123:
            r0 = move-exception
            r1 = r0
            monitor-exit(r3)     // Catch:{ all -> 0x0123 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: de.greenrobot.event.SubscriberMethodFinder.findSubscriberMethods(java.lang.Class, java.lang.String):java.util.List");
    }

    static void clearCaches() {
        methodCache.clear();
    }

    static void skipMethodNameVerificationFor(Class<?> cls) {
        if (!methodCache.isEmpty()) {
            throw new IllegalStateException("This method must be called before registering anything");
        }
        skipMethodNameVerificationForClasses.put(cls, cls);
    }

    public static void clearSkipMethodNameVerifications() {
        skipMethodNameVerificationForClasses.clear();
    }
}
