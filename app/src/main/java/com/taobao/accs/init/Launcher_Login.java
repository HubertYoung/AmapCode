package com.taobao.accs.init;

import android.app.Application;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import java.io.Serializable;
import java.util.HashMap;

public class Launcher_Login implements Serializable {
    public void init(final Application application, final HashMap<String, Object> hashMap) {
        ALog.i("Launcher_Login", "login", new Object[0]);
        ThreadPoolExecutorFactory.execute(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:26:0x0058 A[Catch:{ Throwable -> 0x0093 }] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r7 = this;
                    r0 = 0
                    r1 = 1
                    r2 = 0
                    java.util.HashMap r3 = r5     // Catch:{ Throwable -> 0x0048 }
                    java.lang.String r4 = "envIndex"
                    java.lang.Object r3 = r3.get(r4)     // Catch:{ Throwable -> 0x0048 }
                    java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ Throwable -> 0x0048 }
                    int r3 = r3.intValue()     // Catch:{ Throwable -> 0x0048 }
                    java.util.HashMap r4 = r5     // Catch:{ Throwable -> 0x0048 }
                    java.lang.String r5 = "onlineAppKey"
                    java.lang.Object r4 = r4.get(r5)     // Catch:{ Throwable -> 0x0048 }
                    java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x0048 }
                    if (r3 != r1) goto L_0x002c
                    java.util.HashMap r0 = r5     // Catch:{ Throwable -> 0x0028 }
                    java.lang.String r3 = "preAppKey"
                    java.lang.Object r0 = r0.get(r3)     // Catch:{ Throwable -> 0x0028 }
                    java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0028 }
                    goto L_0x0052
                L_0x0028:
                    r0 = move-exception
                    r3 = r0
                    r0 = r4
                    goto L_0x0049
                L_0x002c:
                    r0 = 2
                    if (r3 != r0) goto L_0x0031
                    r0 = 1
                    goto L_0x0032
                L_0x0031:
                    r0 = 0
                L_0x0032:
                    r5 = 3
                    if (r3 != r5) goto L_0x0037
                    r3 = 1
                    goto L_0x0038
                L_0x0037:
                    r3 = 0
                L_0x0038:
                    r0 = r0 | r3
                    if (r0 == 0) goto L_0x0046
                    java.util.HashMap r0 = r5     // Catch:{ Throwable -> 0x0028 }
                    java.lang.String r3 = "dailyAppkey"
                    java.lang.Object r0 = r0.get(r3)     // Catch:{ Throwable -> 0x0028 }
                    java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0028 }
                    goto L_0x0052
                L_0x0046:
                    r0 = r4
                    goto L_0x0052
                L_0x0048:
                    r3 = move-exception
                L_0x0049:
                    java.lang.String r4 = "Launcher_Login"
                    java.lang.String r5 = "login get param error"
                    java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0093 }
                    com.taobao.accs.utl.ALog.e(r4, r5, r3, r6)     // Catch:{ Throwable -> 0x0093 }
                L_0x0052:
                    boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0093 }
                    if (r3 == 0) goto L_0x0063
                    java.lang.String r0 = "Launcher_Login"
                    java.lang.String r3 = "login get appkey null"
                    java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0093 }
                    com.taobao.accs.utl.ALog.e(r0, r3, r4)     // Catch:{ Throwable -> 0x0093 }
                    java.lang.String r0 = "21646297"
                L_0x0063:
                    com.taobao.accs.init.Launcher_InitAccs.mForceBindUser = r1     // Catch:{ Throwable -> 0x0093 }
                    java.util.HashMap r1 = r5     // Catch:{ Throwable -> 0x0093 }
                    java.lang.String r3 = "userId"
                    java.lang.Object r1 = r1.get(r3)     // Catch:{ Throwable -> 0x0093 }
                    java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x0093 }
                    com.taobao.accs.init.Launcher_InitAccs.mUserId = r1     // Catch:{ Throwable -> 0x0093 }
                    java.util.HashMap r1 = r5     // Catch:{ Throwable -> 0x0093 }
                    java.lang.String r3 = "sid"
                    java.lang.Object r1 = r1.get(r3)     // Catch:{ Throwable -> 0x0093 }
                    java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x0093 }
                    com.taobao.accs.init.Launcher_InitAccs.mSid = r1     // Catch:{ Throwable -> 0x0093 }
                    android.app.Application r1 = r4     // Catch:{ Throwable -> 0x0093 }
                    android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x0093 }
                    java.util.HashMap r3 = r5     // Catch:{ Throwable -> 0x0093 }
                    java.lang.String r4 = "ttid"
                    java.lang.Object r3 = r3.get(r4)     // Catch:{ Throwable -> 0x0093 }
                    java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x0093 }
                    com.taobao.accs.IAppReceiver r4 = com.taobao.accs.init.Launcher_InitAccs.mAppReceiver     // Catch:{ Throwable -> 0x0093 }
                    com.taobao.accs.ACCSManager.bindApp(r1, r0, r3, r4)     // Catch:{ Throwable -> 0x0093 }
                    return
                L_0x0093:
                    r0 = move-exception
                    java.lang.String r1 = "Launcher_Login"
                    java.lang.String r3 = "login"
                    java.lang.Object[] r2 = new java.lang.Object[r2]
                    com.taobao.accs.utl.ALog.e(r1, r3, r0, r2)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.init.Launcher_Login.AnonymousClass1.run():void");
            }
        });
    }
}
