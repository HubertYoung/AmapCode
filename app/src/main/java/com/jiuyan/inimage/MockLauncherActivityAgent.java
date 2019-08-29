package com.jiuyan.inimage;

import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.LauncherActivityAgent;
import com.jiuyan.inimage.util.q;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class MockLauncherActivityAgent extends LauncherActivityAgent {
    public MockLauncherActivityAgent() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x00b1 A[SYNTHETIC, Splitter:B:13:0x00b1] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x010f A[SYNTHETIC, Splitter:B:32:0x010f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String loadConfig(java.lang.String r10) {
        /*
            r9 = 2
            r8 = 1
            r7 = 0
            java.lang.String[] r0 = new java.lang.String[r9]
            java.lang.String r1 = "mingtian "
            r0[r7] = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "loadConfig "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r10)
            java.lang.String r1 = r1.toString()
            r0[r8] = r1
            com.jiuyan.inimage.util.q.a(r0)
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            r0 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r0]
            r2 = 0
            r1 = 2
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ IOException -> 0x0127, all -> 0x010b }
            r4 = 0
            java.lang.String r5 = "mingtian "
            r1[r4] = r5     // Catch:{ IOException -> 0x0127, all -> 0x010b }
            r4 = 1
            java.lang.String r5 = "loadConfig start"
            r1[r4] = r5     // Catch:{ IOException -> 0x0127, all -> 0x010b }
            com.jiuyan.inimage.util.q.a(r1)     // Catch:{ IOException -> 0x0127, all -> 0x010b }
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0127, all -> 0x010b }
            r1.<init>(r10)     // Catch:{ IOException -> 0x0127, all -> 0x010b }
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ IOException -> 0x0086 }
            r4 = 0
            java.lang.String r5 = "mingtian "
            r2[r4] = r5     // Catch:{ IOException -> 0x0086 }
            r4 = 1
            java.lang.String r5 = "loadConfig start 1"
            r2[r4] = r5     // Catch:{ IOException -> 0x0086 }
            com.jiuyan.inimage.util.q.a(r2)     // Catch:{ IOException -> 0x0086 }
        L_0x0050:
            int r2 = r1.read(r0)     // Catch:{ IOException -> 0x0086 }
            r4 = -1
            if (r2 == r4) goto L_0x00c6
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ IOException -> 0x0086 }
            r4 = 0
            java.lang.String r5 = "mingtian "
            r2[r4] = r5     // Catch:{ IOException -> 0x0086 }
            r4 = 1
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0086 }
            r5.<init>()     // Catch:{ IOException -> 0x0086 }
            java.lang.String r6 = "loadConfig start xx "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x0086 }
            java.lang.String r6 = new java.lang.String     // Catch:{ IOException -> 0x0086 }
            r6.<init>(r0)     // Catch:{ IOException -> 0x0086 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x0086 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0086 }
            r2[r4] = r5     // Catch:{ IOException -> 0x0086 }
            com.jiuyan.inimage.util.q.a(r2)     // Catch:{ IOException -> 0x0086 }
            java.lang.String r2 = new java.lang.String     // Catch:{ IOException -> 0x0086 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0086 }
            r3.append(r2)     // Catch:{ IOException -> 0x0086 }
            goto L_0x0050
        L_0x0086:
            r0 = move-exception
        L_0x0087:
            r0.printStackTrace()     // Catch:{ all -> 0x0125 }
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ all -> 0x0125 }
            r4 = 0
            java.lang.String r5 = "mingtian "
            r2[r4] = r5     // Catch:{ all -> 0x0125 }
            r4 = 1
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0125 }
            r5.<init>()     // Catch:{ all -> 0x0125 }
            java.lang.String r6 = "loadConfig exp "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0125 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0125 }
            java.lang.StringBuilder r0 = r5.append(r0)     // Catch:{ all -> 0x0125 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0125 }
            r2[r4] = r0     // Catch:{ all -> 0x0125 }
            com.jiuyan.inimage.util.q.a(r2)     // Catch:{ all -> 0x0125 }
            if (r1 == 0) goto L_0x00b4
            r1.close()     // Catch:{ IOException -> 0x0106 }
        L_0x00b4:
            java.lang.String[] r0 = new java.lang.String[r9]
            java.lang.String r1 = "mingtian "
            r0[r7] = r1
            java.lang.String r1 = "loadConfig finally "
            r0[r8] = r1
            com.jiuyan.inimage.util.q.a(r0)
        L_0x00c1:
            java.lang.String r0 = r3.toString()
            return r0
        L_0x00c6:
            r0 = 2
            java.lang.String[] r0 = new java.lang.String[r0]     // Catch:{ IOException -> 0x0086 }
            r2 = 0
            java.lang.String r4 = "mingtian "
            r0[r2] = r4     // Catch:{ IOException -> 0x0086 }
            r2 = 1
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0086 }
            r4.<init>()     // Catch:{ IOException -> 0x0086 }
            java.lang.String r5 = "loadConfig "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0086 }
            java.lang.String r5 = r3.toString()     // Catch:{ IOException -> 0x0086 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0086 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0086 }
            r0[r2] = r4     // Catch:{ IOException -> 0x0086 }
            com.jiuyan.inimage.util.q.a(r0)     // Catch:{ IOException -> 0x0086 }
            r1.close()     // Catch:{ IOException -> 0x0086 }
            if (r1 == 0) goto L_0x00f3
            r1.close()     // Catch:{ IOException -> 0x0101 }
        L_0x00f3:
            java.lang.String[] r0 = new java.lang.String[r9]
            java.lang.String r1 = "mingtian "
            r0[r7] = r1
            java.lang.String r1 = "loadConfig finally "
            r0[r8] = r1
            com.jiuyan.inimage.util.q.a(r0)
            goto L_0x00c1
        L_0x0101:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00f3
        L_0x0106:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00b4
        L_0x010b:
            r0 = move-exception
            r1 = r2
        L_0x010d:
            if (r1 == 0) goto L_0x0112
            r1.close()     // Catch:{ IOException -> 0x0120 }
        L_0x0112:
            java.lang.String[] r1 = new java.lang.String[r9]
            java.lang.String r2 = "mingtian "
            r1[r7] = r2
            java.lang.String r2 = "loadConfig finally "
            r1[r8] = r2
            com.jiuyan.inimage.util.q.a(r1)
            throw r0
        L_0x0120:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0112
        L_0x0125:
            r0 = move-exception
            goto L_0x010d
        L_0x0127:
            r0 = move-exception
            r1 = r2
            goto L_0x0087
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jiuyan.inimage.MockLauncherActivityAgent.loadConfig(java.lang.String):java.lang.String");
    }

    public void preInit(Activity activity) {
        super.preInit(activity);
    }

    public void postInit(Activity activity) {
        super.postInit(activity);
        z zVar = new z();
        String loadConfig = loadConfig(Environment.getExternalStorageDirectory() + File.separator + "inali_config.txt");
        q.a("mingtian ", "loadConfig start to config " + loadConfig);
        if (!TextUtils.isEmpty(loadConfig)) {
            for (String split : loadConfig.split(",")) {
                String[] split2 = split.split(":");
                q.a("mingtian ", "loadConfig parse " + split2[0] + ", " + split2[1]);
                if (split2[0].equals("func_magic")) {
                    zVar.a = Boolean.valueOf(split2[1]).booleanValue();
                } else if (split2[0].equals("func_paster")) {
                    zVar.e = Boolean.valueOf(split2[1]).booleanValue();
                } else if (split2[0].equals("func_crop")) {
                    zVar.b = Boolean.valueOf(split2[1]).booleanValue();
                } else if (split2[0].equals("func_rotate")) {
                    zVar.c = Boolean.valueOf(split2[1]).booleanValue();
                } else if (split2[0].equals("func_text")) {
                    zVar.d = Boolean.valueOf(split2[1]).booleanValue();
                } else if (split2[0].equals("config_text_back")) {
                    zVar.f = String.valueOf(split2[1]);
                } else if (split2[0].equals("config_text_text")) {
                    zVar.g = String.valueOf(split2[1]);
                }
            }
        }
        new Handler().postDelayed(new w(this, activity, zVar), TimeUnit.SECONDS.toMillis(1));
    }
}
