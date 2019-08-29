package com.squareup.leakcanary;

public class DisplayLeakService extends AbstractAnalysisResultService {
    /* access modifiers changed from: protected */
    public void afterDefaultHandling(HeapDump heapDump, AnalysisResult analysisResult, String str) {
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00f2 */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00f7 A[SYNTHETIC, Splitter:B:44:0x00f7] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00fd A[SYNTHETIC, Splitter:B:49:0x00fd] */
    @android.annotation.TargetApi(11)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onHeapAnalyzed(com.squareup.leakcanary.HeapDump r12, com.squareup.leakcanary.AnalysisResult r13) {
        /*
            r11 = this;
            r0 = 1
            java.lang.String r1 = com.squareup.leakcanary.LeakCanary.leakInfo(r11, r12, r13, r0)
            int r2 = r1.length()
            r3 = 4000(0xfa0, float:5.605E-42)
            if (r2 < r3) goto L_0x0012
            java.lang.String r2 = "\n"
            r1.split(r2)
        L_0x0012:
            java.lang.Exception r2 = r13.failure
            if (r2 != 0) goto L_0x0022
            boolean r2 = r13.leakFound
            if (r2 == 0) goto L_0x001e
            boolean r2 = r13.excludedLeak
            if (r2 == 0) goto L_0x0022
        L_0x001e:
            r11.afterDefaultHandling(r12, r13, r1)
            return
        L_0x0022:
            android.content.res.Resources r2 = r11.getResources()
            int r3 = com.squareup.leakcanary.R.integer.__leak_canary_max_stored_leaks
            int r2 = r2.getInteger(r3)
            java.io.File r3 = com.squareup.leakcanary.internal.LeakCanaryInternals.findNextAvailableHprofFile(r2)
            if (r3 != 0) goto L_0x0045
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "Leak result dropped because we already store "
            r0.<init>(r3)
            r0.append(r2)
            java.lang.String r2 = " leak traces."
            r0.append(r2)
            r11.afterDefaultHandling(r12, r13, r1)
            return
        L_0x0045:
            com.squareup.leakcanary.HeapDump r12 = r12.renameFile(r3)
            java.io.File r2 = com.squareup.leakcanary.internal.LeakCanaryInternals.leakResultFile(r3)
            r3 = 0
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00f2 }
            r4.<init>(r2)     // Catch:{ IOException -> 0x00f2 }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x00ed, all -> 0x00eb }
            r2.<init>(r4)     // Catch:{ IOException -> 0x00ed, all -> 0x00eb }
            r2.writeObject(r12)     // Catch:{ IOException -> 0x00ed, all -> 0x00eb }
            r2.writeObject(r13)     // Catch:{ IOException -> 0x00ed, all -> 0x00eb }
            r4.close()     // Catch:{ IOException -> 0x0061 }
        L_0x0061:
            java.lang.String r2 = r12.referenceKey
            android.app.PendingIntent r2 = com.squareup.leakcanary.internal.DisplayLeakActivity.createPendingIntent(r11, r2)
            java.lang.Exception r3 = r13.failure
            if (r3 != 0) goto L_0x007d
            int r3 = com.squareup.leakcanary.R.string.__leak_canary_class_has_leaked
            java.lang.Object[] r4 = new java.lang.Object[r0]
            r5 = 0
            java.lang.String r6 = r13.className
            java.lang.String r6 = com.squareup.leakcanary.internal.LeakCanaryInternals.classSimpleName(r6)
            r4[r5] = r6
            java.lang.String r3 = r11.getString(r3, r4)
            goto L_0x0083
        L_0x007d:
            int r3 = com.squareup.leakcanary.R.string.__leak_canary_analysis_failed
            java.lang.String r3 = r11.getString(r3)
        L_0x0083:
            int r4 = com.squareup.leakcanary.R.string.__leak_canary_notification_message
            java.lang.String r4 = r11.getString(r4)
            java.lang.String r5 = "notification"
            java.lang.Object r5 = r11.getSystemService(r5)
            android.app.NotificationManager r5 = (android.app.NotificationManager) r5
            int r6 = android.os.Build.VERSION.SDK_INT
            r7 = 11
            r8 = 16
            if (r6 >= r7) goto L_0x00b1
            android.app.Notification r0 = new android.app.Notification
            r0.<init>()
            int r6 = com.squareup.leakcanary.R.drawable.__leak_canary_notification
            r0.icon = r6
            long r6 = java.lang.System.currentTimeMillis()
            r0.when = r6
            int r6 = r0.flags
            r6 = r6 | r8
            r0.flags = r6
            r0.setLatestEventInfo(r11, r3, r4, r2)
            goto L_0x00e1
        L_0x00b1:
            android.app.Notification$Builder r6 = new android.app.Notification$Builder
            r6.<init>(r11)
            int r7 = com.squareup.leakcanary.R.drawable.__leak_canary_notification
            android.app.Notification$Builder r6 = r6.setSmallIcon(r7)
            long r9 = java.lang.System.currentTimeMillis()
            android.app.Notification$Builder r6 = r6.setWhen(r9)
            android.app.Notification$Builder r3 = r6.setContentTitle(r3)
            android.app.Notification$Builder r3 = r3.setContentText(r4)
            android.app.Notification$Builder r0 = r3.setAutoCancel(r0)
            android.app.Notification$Builder r0 = r0.setContentIntent(r2)
            int r2 = android.os.Build.VERSION.SDK_INT
            if (r2 >= r8) goto L_0x00dd
            android.app.Notification r0 = r0.getNotification()
            goto L_0x00e1
        L_0x00dd:
            android.app.Notification r0 = r0.build()
        L_0x00e1:
            r2 = -558907665(0xffffffffdeafbeef, float:-6.331911E18)
            r5.notify(r2, r0)
            r11.afterDefaultHandling(r12, r13, r1)
            return
        L_0x00eb:
            r12 = move-exception
            goto L_0x00fb
        L_0x00ed:
            r3 = r4
            goto L_0x00f2
        L_0x00ef:
            r12 = move-exception
            r4 = r3
            goto L_0x00fb
        L_0x00f2:
            r11.afterDefaultHandling(r12, r13, r1)     // Catch:{ all -> 0x00ef }
            if (r3 == 0) goto L_0x00fa
            r3.close()     // Catch:{ IOException -> 0x00fa }
        L_0x00fa:
            return
        L_0x00fb:
            if (r4 == 0) goto L_0x0100
            r4.close()     // Catch:{ IOException -> 0x0100 }
        L_0x0100:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.leakcanary.DisplayLeakService.onHeapAnalyzed(com.squareup.leakcanary.HeapDump, com.squareup.leakcanary.AnalysisResult):void");
    }
}
