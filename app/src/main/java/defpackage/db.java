package defpackage;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import anet.channel.monitor.NetworkSpeed;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import com.ta.utdid2.device.UTDevice;
import java.lang.reflect.Method;

/* renamed from: db reason: default package */
/* compiled from: Utils */
public class db {
    public static Context a;

    public static String a(Context context) {
        return UTDevice.getUtdid(context);
    }

    public static String b(Context context) {
        String str;
        if (context == null) {
            return "";
        }
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.processName;
        } catch (NameNotFoundException unused) {
            str = "";
        }
        return str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r5, int r6) {
        /*
            java.lang.String r0 = ""
            r1 = -108(0xffffffffffffff94, float:NaN)
            java.lang.String r2 = "activity"
            java.lang.Object r5 = r5.getSystemService(r2)     // Catch:{ Exception -> 0x0056 }
            android.app.ActivityManager r5 = (android.app.ActivityManager) r5     // Catch:{ Exception -> 0x0056 }
            java.util.List r5 = r5.getRunningAppProcesses()     // Catch:{ Exception -> 0x0056 }
            if (r5 == 0) goto L_0x002f
            int r2 = r5.size()     // Catch:{ Exception -> 0x0056 }
            if (r2 <= 0) goto L_0x002f
            java.util.Iterator r5 = r5.iterator()     // Catch:{ Exception -> 0x0056 }
        L_0x001c:
            boolean r2 = r5.hasNext()     // Catch:{ Exception -> 0x0056 }
            if (r2 == 0) goto L_0x0069
            java.lang.Object r2 = r5.next()     // Catch:{ Exception -> 0x0056 }
            android.app.ActivityManager$RunningAppProcessInfo r2 = (android.app.ActivityManager.RunningAppProcessInfo) r2     // Catch:{ Exception -> 0x0056 }
            int r3 = r2.pid     // Catch:{ Exception -> 0x0056 }
            if (r3 != r6) goto L_0x001c
            java.lang.String r5 = r2.processName     // Catch:{ Exception -> 0x0056 }
            goto L_0x006a
        L_0x002f:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0056 }
            java.lang.String r2 = "BuildVersion="
            r5.<init>(r2)     // Catch:{ Exception -> 0x0056 }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0056 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x0056 }
            r5.append(r2)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0056 }
            java.lang.String r5 = defpackage.co.a(r1, r5)     // Catch:{ Exception -> 0x0056 }
            z r2 = defpackage.x.a()     // Catch:{ Exception -> 0x0056 }
            anet.channel.statist.ExceptionStatistic r3 = new anet.channel.statist.ExceptionStatistic     // Catch:{ Exception -> 0x0056 }
            java.lang.String r4 = "rt"
            r3.<init>(r1, r5, r4)     // Catch:{ Exception -> 0x0056 }
            r2.a(r3)     // Catch:{ Exception -> 0x0056 }
            goto L_0x0069
        L_0x0056:
            r5 = move-exception
            z r2 = defpackage.x.a()
            anet.channel.statist.ExceptionStatistic r3 = new anet.channel.statist.ExceptionStatistic
            java.lang.String r5 = r5.toString()
            java.lang.String r4 = "rt"
            r3.<init>(r1, r5, r4)
            r2.a(r3)
        L_0x0069:
            r5 = r0
        L_0x006a:
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto L_0x0074
            java.lang.String r5 = a(r6)
        L_0x0074:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.db.a(android.content.Context, int):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b0, code lost:
        if (r5 != null) goto L_0x00b2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ad A[SYNTHETIC, Splitter:B:36:0x00ad] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c5 A[SYNTHETIC, Splitter:B:46:0x00c5] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00ca A[Catch:{ IOException -> 0x00ce }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(int r7) {
        /*
            java.lang.String r0 = "ps  |  grep  "
            java.lang.String r1 = java.lang.String.valueOf(r7)
            java.lang.String r0 = r0.concat(r1)
            r1 = 0
            r2 = 0
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x00a0, all -> 0x009c }
            java.lang.String r4 = "sh"
            java.lang.Process r3 = r3.exec(r4)     // Catch:{ Exception -> 0x00a0, all -> 0x009c }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00a0, all -> 0x009c }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00a0, all -> 0x009c }
            java.io.InputStream r6 = r3.getInputStream()     // Catch:{ Exception -> 0x00a0, all -> 0x009c }
            r5.<init>(r6)     // Catch:{ Exception -> 0x00a0, all -> 0x009c }
            r4.<init>(r5)     // Catch:{ Exception -> 0x00a0, all -> 0x009c }
            java.io.DataOutputStream r5 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x009a, all -> 0x0097 }
            java.io.OutputStream r6 = r3.getOutputStream()     // Catch:{ Exception -> 0x009a, all -> 0x0097 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x009a, all -> 0x0097 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a2 }
            r6.<init>()     // Catch:{ Exception -> 0x00a2 }
            r6.append(r0)     // Catch:{ Exception -> 0x00a2 }
            java.lang.String r0 = "  &\n"
            r6.append(r0)     // Catch:{ Exception -> 0x00a2 }
            java.lang.String r0 = r6.toString()     // Catch:{ Exception -> 0x00a2 }
            r5.writeBytes(r0)     // Catch:{ Exception -> 0x00a2 }
            r5.flush()     // Catch:{ Exception -> 0x00a2 }
            java.lang.String r0 = "exit\n"
            r5.writeBytes(r0)     // Catch:{ Exception -> 0x00a2 }
            r3.waitFor()     // Catch:{ Exception -> 0x00a2 }
        L_0x004c:
            java.lang.String r0 = r4.readLine()     // Catch:{ Exception -> 0x00a2 }
            if (r0 == 0) goto L_0x0093
            java.lang.String r3 = "\\s+"
            java.lang.String r6 = "  "
            java.lang.String r0 = r0.replaceAll(r3, r6)     // Catch:{ Exception -> 0x00a2 }
            java.lang.String r3 = "  "
            java.lang.String[] r0 = r0.split(r3)     // Catch:{ Exception -> 0x00a2 }
            int r3 = r0.length     // Catch:{ Exception -> 0x00a2 }
            r6 = 9
            if (r3 < r6) goto L_0x004c
            r3 = 1
            r6 = r0[r3]     // Catch:{ Exception -> 0x00a2 }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x00a2 }
            if (r6 != 0) goto L_0x004c
            r3 = r0[r3]     // Catch:{ Exception -> 0x00a2 }
            java.lang.String r3 = r3.trim()     // Catch:{ Exception -> 0x00a2 }
            java.lang.String r6 = java.lang.String.valueOf(r7)     // Catch:{ Exception -> 0x00a2 }
            boolean r3 = r3.equals(r6)     // Catch:{ Exception -> 0x00a2 }
            if (r3 == 0) goto L_0x004c
            r7 = 8
            r7 = r0[r7]     // Catch:{ Exception -> 0x00a2 }
            r4.close()     // Catch:{ IOException -> 0x0089 }
            r5.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x0092
        L_0x0089:
            java.lang.String r0 = "awcn.Utils"
            java.lang.String r3 = "getProcessNameNew "
            java.lang.Object[] r1 = new java.lang.Object[r1]
            defpackage.cl.e(r0, r3, r2, r1)
        L_0x0092:
            return r7
        L_0x0093:
            r4.close()     // Catch:{ IOException -> 0x00b6 }
            goto L_0x00b2
        L_0x0097:
            r7 = move-exception
            r5 = r2
            goto L_0x00c3
        L_0x009a:
            r5 = r2
            goto L_0x00a2
        L_0x009c:
            r7 = move-exception
            r4 = r2
            r5 = r4
            goto L_0x00c3
        L_0x00a0:
            r4 = r2
            r5 = r4
        L_0x00a2:
            java.lang.String r7 = "awcn.Utils"
            java.lang.String r0 = "getProcessNameNew "
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x00c2 }
            defpackage.cl.e(r7, r0, r2, r3)     // Catch:{ all -> 0x00c2 }
            if (r4 == 0) goto L_0x00b0
            r4.close()     // Catch:{ IOException -> 0x00b6 }
        L_0x00b0:
            if (r5 == 0) goto L_0x00bf
        L_0x00b2:
            r5.close()     // Catch:{ IOException -> 0x00b6 }
            goto L_0x00bf
        L_0x00b6:
            java.lang.String r7 = "awcn.Utils"
            java.lang.String r0 = "getProcessNameNew "
            java.lang.Object[] r1 = new java.lang.Object[r1]
            defpackage.cl.e(r7, r0, r2, r1)
        L_0x00bf:
            java.lang.String r7 = ""
            return r7
        L_0x00c2:
            r7 = move-exception
        L_0x00c3:
            if (r4 == 0) goto L_0x00c8
            r4.close()     // Catch:{ IOException -> 0x00ce }
        L_0x00c8:
            if (r5 == 0) goto L_0x00d7
            r5.close()     // Catch:{ IOException -> 0x00ce }
            goto L_0x00d7
        L_0x00ce:
            java.lang.String r0 = "awcn.Utils"
            java.lang.String r3 = "getProcessNameNew "
            java.lang.Object[] r1 = new java.lang.Object[r1]
            defpackage.cl.e(r0, r3, r2, r1)
        L_0x00d7:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.db.a(int):java.lang.String");
    }

    public static Context a() {
        if (a != null) {
            return a;
        }
        synchronized (db.class) {
            if (a != null) {
                Context context = a;
                return context;
            }
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(cls, new Object[0]);
                a = (Context) invoke.getClass().getMethod("getApplication", new Class[0]).invoke(invoke, new Object[0]);
            } catch (Exception e) {
                cl.a("awcn.Utils", "getAppContext", null, e, new Object[0]);
            }
            Context context2 = a;
            return context2;
        }
    }

    public static Object a(String str, String str2, Class<?>[] clsArr, Object... objArr) throws Exception {
        Class<?> cls = Class.forName(str);
        Method declaredMethod = cls.getDeclaredMethod(str2, clsArr);
        if (declaredMethod == null) {
            return null;
        }
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(cls, objArr);
    }

    public static float b() {
        int i;
        NetworkStatus a2 = NetworkStatusHelper.a();
        float f = (a2 == NetworkStatus.G4 || a2 == NetworkStatus.WIFI) ? 0.8f : 1.0f;
        ar a3 = ar.a();
        if (NetworkStatusHelper.a() == NetworkStatus.G2) {
            i = 1;
        } else {
            i = a3.k;
        }
        return i == NetworkSpeed.Fast.getCode() ? f * 0.75f : f;
    }
}
