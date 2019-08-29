package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class cx implements ValueCallback<UpdateTask> {
    final ValueCallback a = ((ValueCallback) this.b.invokeO(10007, "downloadException"));
    final /* synthetic */ cn b;

    cx(cn cnVar) {
        this.b = cnVar;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(14:8|(1:10)(1:11)|12|13|14|15|16|18|19|20|21|22|25|26) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0071, code lost:
        r0 = "";
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0064 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void onReceiveValue(java.lang.Object r11) {
        /*
            r10 = this;
            com.uc.webview.export.utility.download.UpdateTask r11 = (com.uc.webview.export.utility.download.UpdateTask) r11
            java.lang.String r0 = "UpdateSetupTask"
            java.lang.String r1 = "EVENT_DOWNLOAD_EXCEPTION"
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            com.uc.webview.export.internal.setup.cn r0 = r10.b
            monitor-enter(r0)
            com.uc.webview.export.internal.setup.cn r1 = r10.b     // Catch:{ all -> 0x00d7 }
            r1.c = true     // Catch:{ all -> 0x00d7 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d7 }
            com.uc.webview.export.internal.setup.cn r0 = r10.b     // Catch:{ Throwable -> 0x00d6 }
            com.uc.webview.export.internal.setup.UCSetupException r0 = r0.getExtraException()     // Catch:{ Throwable -> 0x00d6 }
            if (r0 != 0) goto L_0x00a3
            java.lang.Throwable r0 = r11.getException()     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r1 = ""
            java.lang.String r2 = ""
            java.lang.String r3 = ""
            java.lang.String r4 = ""
            boolean r5 = r0 instanceof com.uc.webview.export.cyclone.UCKnownException     // Catch:{ Throwable -> 0x00d6 }
            if (r5 == 0) goto L_0x002d
            com.uc.webview.export.cyclone.UCKnownException r0 = (com.uc.webview.export.cyclone.UCKnownException) r0     // Catch:{ Throwable -> 0x00d6 }
            goto L_0x0033
        L_0x002d:
            com.uc.webview.export.cyclone.UCKnownException r5 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ Throwable -> 0x00d6 }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x00d6 }
            r0 = r5
        L_0x0033:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d6 }
            r5.<init>()     // Catch:{ Throwable -> 0x00d6 }
            r5.append(r1)     // Catch:{ Throwable -> 0x00d6 }
            int r1 = r0.errCode()     // Catch:{ Throwable -> 0x00d6 }
            r5.append(r1)     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r1 = r5.toString()     // Catch:{ Throwable -> 0x00d6 }
            java.lang.Throwable r5 = r0.getRootCause()     // Catch:{ Throwable -> 0x004f }
            java.lang.String r5 = r5.getMessage()     // Catch:{ Throwable -> 0x004f }
            r3 = r5
        L_0x004f:
            java.lang.String r5 = "httpcode:"
            int r5 = r3.indexOf(r5)     // Catch:{ Throwable -> 0x0064 }
            int r5 = r5 + 9
            java.lang.String r5 = r3.substring(r5)     // Catch:{ Throwable -> 0x0064 }
            int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ Throwable -> 0x0064 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x0064 }
            r4 = r5
        L_0x0064:
            java.lang.Throwable r0 = r0.getRootCause()     // Catch:{ Throwable -> 0x0071 }
            java.lang.Class r0 = r0.getClass()     // Catch:{ Throwable -> 0x0071 }
            java.lang.String r0 = r0.getSimpleName()     // Catch:{ Throwable -> 0x0071 }
            goto L_0x0072
        L_0x0071:
            r0 = r2
        L_0x0072:
            com.uc.webview.export.internal.setup.cn r2 = r10.b     // Catch:{ Throwable -> 0x00d6 }
            android.util.Pair r5 = new android.util.Pair     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r6 = "sdk_upd"
            com.uc.webview.export.cyclone.UCHashMap r7 = new com.uc.webview.export.cyclone.UCHashMap     // Catch:{ Throwable -> 0x00d6 }
            r7.<init>()     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r8 = "cnt"
            java.lang.String r9 = "1"
            com.uc.webview.export.cyclone.UCHashMap r7 = r7.set(r8, r9)     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r8 = "code"
            com.uc.webview.export.cyclone.UCHashMap r4 = r7.set(r8, r4)     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r7 = "err"
            com.uc.webview.export.cyclone.UCHashMap r1 = r4.set(r7, r1)     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r4 = "cls"
            com.uc.webview.export.cyclone.UCHashMap r0 = r1.set(r4, r0)     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r1 = "msg"
            com.uc.webview.export.cyclone.UCHashMap r0 = r0.set(r1, r3)     // Catch:{ Throwable -> 0x00d6 }
            r5.<init>(r6, r0)     // Catch:{ Throwable -> 0x00d6 }
            r2.callbackStat(r5)     // Catch:{ Throwable -> 0x00d6 }
        L_0x00a3:
            java.lang.Throwable r0 = r11.getException()     // Catch:{ Throwable -> 0x00d6 }
            if (r0 == 0) goto L_0x00cb
            java.lang.Throwable r0 = r11.getException()     // Catch:{ Throwable -> 0x00d6 }
            boolean r0 = r0 instanceof com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ Throwable -> 0x00d6 }
            if (r0 == 0) goto L_0x00bd
            com.uc.webview.export.internal.setup.cn r0 = r10.b     // Catch:{ Throwable -> 0x00d6 }
            java.lang.Throwable r11 = r11.getException()     // Catch:{ Throwable -> 0x00d6 }
            com.uc.webview.export.internal.setup.UCSetupException r11 = (com.uc.webview.export.internal.setup.UCSetupException) r11     // Catch:{ Throwable -> 0x00d6 }
            r0.setExtraException(r11)     // Catch:{ Throwable -> 0x00d6 }
            goto L_0x00cb
        L_0x00bd:
            com.uc.webview.export.internal.setup.cn r0 = r10.b     // Catch:{ Throwable -> 0x00d6 }
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ Throwable -> 0x00d6 }
            java.lang.Throwable r11 = r11.getException()     // Catch:{ Throwable -> 0x00d6 }
            r1.<init>(r11)     // Catch:{ Throwable -> 0x00d6 }
            r0.setExtraException(r1)     // Catch:{ Throwable -> 0x00d6 }
        L_0x00cb:
            android.webkit.ValueCallback r11 = r10.a     // Catch:{ Throwable -> 0x00d6 }
            if (r11 == 0) goto L_0x00d6
            android.webkit.ValueCallback r11 = r10.a     // Catch:{ Throwable -> 0x00d6 }
            com.uc.webview.export.internal.setup.cn r0 = r10.b     // Catch:{ Throwable -> 0x00d6 }
            r11.onReceiveValue(r0)     // Catch:{ Throwable -> 0x00d6 }
        L_0x00d6:
            return
        L_0x00d7:
            r11 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00d7 }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.cx.onReceiveValue(java.lang.Object):void");
    }
}
