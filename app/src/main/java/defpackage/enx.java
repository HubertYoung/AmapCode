package defpackage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.amap.bundle.blutils.FileUtil;
import java.io.File;

/* renamed from: enx reason: default package */
/* compiled from: ProfileUtil */
public final class enx {
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0062 A[SYNTHETIC, Splitter:B:40:0x0062] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x006c A[SYNTHETIC, Splitter:B:45:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0078 A[SYNTHETIC, Splitter:B:52:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0082 A[SYNTHETIC, Splitter:B:57:0x0082] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r5, java.lang.String r6) {
        /*
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            r6.<init>(r5)     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            boolean r5 = r2.exists()     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            if (r5 == 0) goto L_0x0015
            r2.delete()     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
        L_0x0015:
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0053, all -> 0x004e }
            r6.<init>(r2)     // Catch:{ Exception -> 0x0053, all -> 0x004e }
            r1 = 2048(0x800, float:2.87E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0048, all -> 0x0043 }
        L_0x0023:
            int r2 = r5.read(r1)     // Catch:{ Exception -> 0x0048, all -> 0x0043 }
            r3 = -1
            if (r2 == r3) goto L_0x002e
            r6.write(r1, r0, r2)     // Catch:{ Exception -> 0x0048, all -> 0x0043 }
            goto L_0x0023
        L_0x002e:
            r6.flush()     // Catch:{ Exception -> 0x0048, all -> 0x0043 }
            r6.close()     // Catch:{ Exception -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0039:
            r5.close()     // Catch:{ Exception -> 0x003d }
            goto L_0x0041
        L_0x003d:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0041:
            r5 = 1
            return r5
        L_0x0043:
            r0 = move-exception
            r1 = r6
            r6 = r5
            r5 = r0
            goto L_0x0076
        L_0x0048:
            r1 = move-exception
            r4 = r6
            r6 = r5
            r5 = r1
            r1 = r4
            goto L_0x005d
        L_0x004e:
            r6 = move-exception
            r4 = r6
            r6 = r5
            r5 = r4
            goto L_0x0076
        L_0x0053:
            r6 = move-exception
            r4 = r6
            r6 = r5
            r5 = r4
            goto L_0x005d
        L_0x0058:
            r5 = move-exception
            r6 = r1
            goto L_0x0076
        L_0x005b:
            r5 = move-exception
            r6 = r1
        L_0x005d:
            r5.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r1 == 0) goto L_0x006a
            r1.close()     // Catch:{ Exception -> 0x0066 }
            goto L_0x006a
        L_0x0066:
            r5 = move-exception
            r5.printStackTrace()
        L_0x006a:
            if (r6 == 0) goto L_0x0074
            r6.close()     // Catch:{ Exception -> 0x0070 }
            goto L_0x0074
        L_0x0070:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0074:
            return r0
        L_0x0075:
            r5 = move-exception
        L_0x0076:
            if (r1 == 0) goto L_0x0080
            r1.close()     // Catch:{ Exception -> 0x007c }
            goto L_0x0080
        L_0x007c:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0080:
            if (r6 == 0) goto L_0x008a
            r6.close()     // Catch:{ Exception -> 0x0086 }
            goto L_0x008a
        L_0x0086:
            r6 = move-exception
            r6.printStackTrace()
        L_0x008a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.enx.a(java.lang.String, java.lang.String):boolean");
    }

    public static void a(String str) {
        cjy.a("ProfileDebug:".concat(String.valueOf(str)));
    }

    public static void b(String str) {
        cjy.c("ProfileDebug:".concat(String.valueOf(str)));
    }

    public static String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getFilesDir());
        sb.append("/libajx_v3.so");
        return sb.toString();
    }

    public static String b() {
        String str = DiskFormatter.B;
        if (akp.b()) {
            str = "A";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(akp.c());
        sb.append(str);
        return sb.toString();
    }

    public static String c() {
        String str = DiskFormatter.B;
        if (!akp.b()) {
            str = "A";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(akp.c());
        sb.append(str);
        return sb.toString();
    }

    public static void a(Context context) {
        Class<?> cls;
        a((String) "restart app");
        try {
            cls = Class.forName("com.autonavi.map.activity.SplashActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            b("SplashActivity not found.");
            cls = null;
        }
        PendingIntent activity = PendingIntent.getActivity(context, 0, new Intent(context, cls), 268435456);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager != null) {
            alarmManager.set(1, System.currentTimeMillis() + 1000, activity);
        }
        System.exit(0);
    }

    public static void c(String str) {
        File file = new File(str);
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File absolutePath : file.listFiles()) {
                    c(absolutePath.getAbsolutePath());
                }
                return;
            }
            boolean readable = file.setReadable(true);
            StringBuilder sb = new StringBuilder("update read properties [");
            sb.append(str);
            sb.append("], [");
            sb.append(readable);
            sb.append("]");
            a(sb.toString());
        }
    }
}
