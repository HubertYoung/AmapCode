package com.taobao.accs.dispatch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.Utils;

public class IntentDispatch {
    public static final String TAG = "IntentDispatch";

    public static void dispatchIntent(Context context, final Intent intent, final boolean z) {
        if (context == null || intent == null) {
            ALog.e(TAG, "dispatchIntent context or intent is null", new Object[0]);
            return;
        }
        final Context applicationContext = context.getApplicationContext();
        try {
            if (Utils.isTarget26(applicationContext)) {
                ALog.i(TAG, "dispatchIntent bind service start", "intent", intent.toString());
                applicationContext.bindService(intent, new ServiceConnection() {
                    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004c, code lost:
                        if (r8 == false) goto L_0x004f;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004f, code lost:
                        return;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0030, code lost:
                        if (r8 != false) goto L_0x0032;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0032, code lost:
                        r6.unbindService(r5);
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0037, code lost:
                        return;
                     */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void onServiceConnected(android.content.ComponentName r6, android.os.IBinder r7) {
                        /*
                            r5 = this;
                            java.lang.String r0 = com.taobao.accs.dispatch.IntentDispatch.TAG
                            java.lang.String r1 = "bindService connected"
                            r2 = 2
                            java.lang.Object[] r2 = new java.lang.Object[r2]
                            java.lang.String r3 = "componentName"
                            r4 = 0
                            r2[r4] = r3
                            java.lang.String r6 = r6.toString()
                            r3 = 1
                            r2[r3] = r6
                            com.taobao.accs.utl.ALog.d(r0, r1, r2)
                            android.os.Messenger r6 = new android.os.Messenger
                            r6.<init>(r7)
                            android.os.Message r7 = new android.os.Message
                            r7.<init>()
                            android.os.Bundle r0 = r7.getData()     // Catch:{ Exception -> 0x003a }
                            java.lang.String r1 = "intent"
                            android.content.Intent r2 = r7     // Catch:{ Exception -> 0x003a }
                            r0.putParcelable(r1, r2)     // Catch:{ Exception -> 0x003a }
                            r6.send(r7)     // Catch:{ Exception -> 0x003a }
                            boolean r6 = r8
                            if (r6 == 0) goto L_0x004f
                        L_0x0032:
                            android.content.Context r6 = r6
                            r6.unbindService(r5)
                            return
                        L_0x0038:
                            r6 = move-exception
                            goto L_0x0050
                        L_0x003a:
                            r6 = move-exception
                            java.lang.String r7 = com.taobao.accs.dispatch.IntentDispatch.TAG     // Catch:{ all -> 0x0038 }
                            java.lang.String r0 = "dispatch intent with exception"
                            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x0038 }
                            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0038 }
                            r1[r4] = r6     // Catch:{ all -> 0x0038 }
                            com.taobao.accs.utl.ALog.e(r7, r0, r1)     // Catch:{ all -> 0x0038 }
                            boolean r6 = r8
                            if (r6 == 0) goto L_0x004f
                            goto L_0x0032
                        L_0x004f:
                            return
                        L_0x0050:
                            boolean r7 = r8
                            if (r7 == 0) goto L_0x0059
                            android.content.Context r7 = r6
                            r7.unbindService(r5)
                        L_0x0059:
                            throw r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.dispatch.IntentDispatch.AnonymousClass1.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
                    }

                    public final void onServiceDisconnected(ComponentName componentName) {
                        ALog.d(IntentDispatch.TAG, "bindService on disconnect", "componentName", componentName.toString());
                        applicationContext.unbindService(this);
                    }
                }, 1);
                return;
            }
            ALog.i(TAG, "dispatchIntent start service ", new Object[0]);
            applicationContext.startService(intent);
        } catch (Exception e) {
            ALog.e(TAG, "dispatchIntent method call with exception ", e.toString());
            e.printStackTrace();
        }
    }
}
