package com.uc.webview.export.cyclone.service;

import android.content.Context;
import com.uc.webview.export.cyclone.Constant;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCKnownException;
import com.uc.webview.export.cyclone.UCLibrary;
import com.uc.webview.export.cyclone.UCLogger;
import com.uc.webview.export.cyclone.UCService;

@Constant
/* compiled from: ProGuard */
public class UCVmsizeImpl implements UCVmsize {
    private static final String LOG_TAG = "UCVmsizeImpl";
    private static boolean mSoIsLoaded = false;
    private static UCKnownException mSoIsLoadedException;

    private static native int nativeSaveChromiumReservedSpace(long j);

    public int getServiceVersion() {
        return 0;
    }

    static {
        try {
            UCService.registerImpl(UCVmsize.class, new UCVmsizeImpl());
        } catch (Throwable th) {
            UCLogger create = UCLogger.create("w", LOG_TAG);
            if (create != null) {
                create.print("UCVmsizeImpl register exception:".concat(String.valueOf(th)), new Throwable[0]);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0051 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long saveChromiumReservedSpace(android.content.Context r10) throws java.lang.Exception {
        /*
            r9 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 0
            r3 = 21
            if (r0 < r3) goto L_0x00de
            int r0 = android.os.Build.VERSION.SDK_INT
            r3 = 27
            if (r0 <= r3) goto L_0x0010
            goto L_0x00de
        L_0x0010:
            java.lang.String r0 = "android.webkit.WebViewFactory"
            java.lang.Class r0 = java.lang.Class.forName(r0)
            if (r0 == 0) goto L_0x00dd
            java.lang.String r3 = "sAddressSpaceReserved"
            java.lang.reflect.Field r3 = r0.getDeclaredField(r3)
            if (r3 == 0) goto L_0x00dd
            boolean r4 = r3.isAccessible()
            r5 = 1
            if (r4 != 0) goto L_0x002a
            r3.setAccessible(r5)
        L_0x002a:
            r4 = 0
            boolean r6 = r3.getBoolean(r4)
            if (r6 == 0) goto L_0x00dd
            java.lang.String r1 = "sProviderLock"
            java.lang.reflect.Field r0 = r0.getDeclaredField(r1)     // Catch:{ Throwable -> 0x0048 }
            if (r0 != 0) goto L_0x003a
            goto L_0x0048
        L_0x003a:
            boolean r1 = r0.isAccessible()     // Catch:{ Throwable -> 0x0048 }
            if (r1 != 0) goto L_0x0043
            r0.setAccessible(r5)     // Catch:{ Throwable -> 0x0048 }
        L_0x0043:
            java.lang.Object r0 = r0.get(r4)     // Catch:{ Throwable -> 0x0048 }
            goto L_0x0049
        L_0x0048:
            r0 = r9
        L_0x0049:
            boolean r1 = mSoIsLoaded
            if (r1 != 0) goto L_0x0050
            loadSo(r10)
        L_0x0050:
            monitor-enter(r0)
            java.lang.Boolean r10 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x00da }
            r3.set(r4, r10)     // Catch:{ all -> 0x00da }
            java.lang.String r10 = "android.os.SystemProperties"
            java.lang.Class r10 = java.lang.Class.forName(r10)     // Catch:{ Exception -> 0x00d3 }
            if (r10 != 0) goto L_0x0066
            java.lang.RuntimeException r10 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r1 = "SystemProperties not found."
            r10.<init>(r1)     // Catch:{ Exception -> 0x00d3 }
            throw r10     // Catch:{ Exception -> 0x00d3 }
        L_0x0066:
            java.lang.String r1 = "getLong"
            r2 = 2
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x00d3 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r8 = 0
            r6[r8] = r7     // Catch:{ Exception -> 0x00d3 }
            java.lang.Class r7 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x00d3 }
            r6[r5] = r7     // Catch:{ Exception -> 0x00d3 }
            java.lang.reflect.Method r10 = r10.getDeclaredMethod(r1, r6)     // Catch:{ Exception -> 0x00d3 }
            if (r10 != 0) goto L_0x0082
            java.lang.RuntimeException r10 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r1 = "SystemProperties.getLong not found."
            r10.<init>(r1)     // Catch:{ Exception -> 0x00d3 }
            throw r10     // Catch:{ Exception -> 0x00d3 }
        L_0x0082:
            boolean r1 = r10.isAccessible()     // Catch:{ Exception -> 0x00d3 }
            if (r1 != 0) goto L_0x008b
            r10.setAccessible(r5)     // Catch:{ Exception -> 0x00d3 }
        L_0x008b:
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r2 = "persist.sys.webview.vmsize"
            r1[r8] = r2     // Catch:{ Exception -> 0x00d3 }
            r6 = 104857600(0x6400000, double:5.1806538E-316)
            java.lang.Long r2 = java.lang.Long.valueOf(r6)     // Catch:{ Exception -> 0x00d3 }
            r1[r5] = r2     // Catch:{ Exception -> 0x00d3 }
            java.lang.Object r10 = r10.invoke(r4, r1)     // Catch:{ Exception -> 0x00d3 }
            java.lang.Long r10 = (java.lang.Long) r10     // Catch:{ Exception -> 0x00d3 }
            if (r10 != 0) goto L_0x00aa
            java.lang.RuntimeException r10 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r1 = "SystemProperties.getLong invoke return null."
            r10.<init>(r1)     // Catch:{ Exception -> 0x00d3 }
            throw r10     // Catch:{ Exception -> 0x00d3 }
        L_0x00aa:
            long r1 = r10.longValue()     // Catch:{ Exception -> 0x00d3 }
            int r1 = nativeSaveChromiumReservedSpace(r1)     // Catch:{ Exception -> 0x00d3 }
            if (r1 != 0) goto L_0x00ba
            long r1 = r10.longValue()     // Catch:{ Exception -> 0x00d3 }
            monitor-exit(r0)     // Catch:{ all -> 0x00da }
            goto L_0x00dd
        L_0x00ba:
            java.lang.RuntimeException r10 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x00d3 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r5 = "Error:"
            r2.<init>(r5)     // Catch:{ Exception -> 0x00d3 }
            r2.append(r1)     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r1 = " on nativeSaveChromiumReservedSpace"
            r2.append(r1)     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x00d3 }
            r10.<init>(r1)     // Catch:{ Exception -> 0x00d3 }
            throw r10     // Catch:{ Exception -> 0x00d3 }
        L_0x00d3:
            r10 = move-exception
            java.lang.Boolean r1 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x00da }
            r3.set(r4, r1)     // Catch:{ all -> 0x00da }
            throw r10     // Catch:{ all -> 0x00da }
        L_0x00da:
            r10 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00da }
            throw r10
        L_0x00dd:
            return r1
        L_0x00de:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.cyclone.service.UCVmsizeImpl.saveChromiumReservedSpace(android.content.Context):long");
    }

    private static synchronized void loadSo(Context context) {
        synchronized (UCVmsizeImpl.class) {
            if (!mSoIsLoaded) {
                if (mSoIsLoadedException != null) {
                    throw mSoIsLoadedException;
                }
                try {
                    UCLibrary.load(context, UCCyclone.genFile(context, null, "libvmsize", ".so", 24713491, "e3d7b7107d5f402c1dde1a3930f7d7da", UCVmsizeImplConstant.genCodes(), new Object[0]).getAbsolutePath(), null);
                    mSoIsLoaded = true;
                } catch (Throwable th) {
                    UCKnownException uCKnownException = new UCKnownException(th);
                    mSoIsLoadedException = uCKnownException;
                    throw uCKnownException;
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UCVmsizeImpl.");
        sb.append(getServiceVersion());
        return sb.toString();
    }
}
