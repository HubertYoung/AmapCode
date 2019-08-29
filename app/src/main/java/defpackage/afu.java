package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import com.amap.bundle.tools.datafreecheck.DataFreeLowActivity;

/* renamed from: afu reason: default package */
/* compiled from: DataFreeChecker */
public class afu {
    private static afu b;
    public boolean a;

    public static afu a(Context context) {
        if (b == null) {
            synchronized (afu.class) {
                if (b == null) {
                    b = new afu(context);
                }
            }
        }
        return b;
    }

    private afu(Context context) {
        this.a = b(context);
    }

    public static boolean b(Context context) {
        if (Environment.getDataDirectory().getUsableSpace() < 5242880) {
            return true;
        }
        return c(context);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004b A[SYNTHETIC, Splitter:B:22:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0059 A[SYNTHETIC, Splitter:B:30:0x0059] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean c(android.content.Context r4) {
        /*
            java.io.File r0 = new java.io.File
            java.io.File r4 = r4.getFilesDir()
            java.lang.String r1 = "checkNoSpace.txt"
            r0.<init>(r4, r1)
            r4 = 0
            r1 = 0
            boolean r2 = r0.exists()     // Catch:{ Throwable -> 0x003b }
            if (r2 != 0) goto L_0x0016
            r0.createNewFile()     // Catch:{ Throwable -> 0x003b }
        L_0x0016:
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x003b }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x003b }
            r3.<init>(r0, r4)     // Catch:{ Throwable -> 0x003b }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x003b }
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Throwable -> 0x0036, all -> 0x0033 }
            r2.write(r1)     // Catch:{ Throwable -> 0x0036, all -> 0x0033 }
            r2.flush()     // Catch:{ Throwable -> 0x0036, all -> 0x0033 }
            r2.close()     // Catch:{ Throwable -> 0x002e }
            goto L_0x004e
        L_0x002e:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x004e
        L_0x0033:
            r4 = move-exception
            r1 = r2
            goto L_0x0057
        L_0x0036:
            r4 = move-exception
            r1 = r2
            goto L_0x003c
        L_0x0039:
            r4 = move-exception
            goto L_0x0057
        L_0x003b:
            r4 = move-exception
        L_0x003c:
            r4.printStackTrace()     // Catch:{ all -> 0x0039 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0039 }
            java.lang.String r2 = "No space left on device"
            boolean r4 = r4.contains(r2)     // Catch:{ all -> 0x0039 }
            if (r1 == 0) goto L_0x004e
            r1.close()     // Catch:{ Throwable -> 0x002e }
        L_0x004e:
            r0.delete()     // Catch:{ Throwable -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0056:
            return r4
        L_0x0057:
            if (r1 == 0) goto L_0x0061
            r1.close()     // Catch:{ Throwable -> 0x005d }
            goto L_0x0061
        L_0x005d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0061:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.afu.c(android.content.Context):boolean");
    }

    public static boolean a(Activity activity) {
        if (!a((Context) activity).a) {
            return false;
        }
        b(activity);
        return true;
    }

    public static void b(Activity activity) {
        try {
            activity.startActivity(new Intent(activity, DataFreeLowActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
