package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.Nullable;

/* renamed from: xi reason: default package */
/* compiled from: LotusPoolUtils */
public class xi {
    private static final String a = "xi";
    private static long b = (xf.b((String) "LOTUSPOOL_APP_LAUNCH_INTERVAL", TimeUnit.HOURS.toSeconds(2)) * 1000);
    private static long c = (xf.b((String) "LOTUSPOOL_LAUNCH_INTERVAL", -1) * 1000);

    public static boolean a(int i) {
        return 1 == i ? System.currentTimeMillis() - xf.d() > b : 4 == i && c >= 0 && System.currentTimeMillis() - xf.e() > c;
    }

    public static boolean a(Command command) {
        return command.h == 1 && wz.b(command);
    }

    public static boolean a(long j, Command command) {
        if (System.currentTimeMillis() < j + (command.j * 1000)) {
            return false;
        }
        if (command.h == 3 || command.h == 4 || command.h == 5 || command.h == 6) {
            return b(command.c("network"));
        }
        return true;
    }

    public static boolean b(int i) {
        int b2 = aaw.b(AMapAppGlobal.getApplication());
        return (b2 == 0 && (i & 1) > 0) || (b2 == 4 && (i & 4) > 0) || ((b2 == 1 || b2 == 2 || b2 == 3) && (i & 2) > 0);
    }

    @Nullable
    public static String a(String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith("$")) {
            return str;
        }
        String internalSDCardPath = FileUtil.getInternalSDCardPath(AMapAppGlobal.getApplication());
        String internalRefreshSDCardPath = FileUtil.getInternalRefreshSDCardPath(AMapAppGlobal.getApplication());
        String externalSDCardPath = FileUtil.getExternalSDCardPath(AMapAppGlobal.getApplication());
        String externalRefreshSDCardPath = FileUtil.getExternalRefreshSDCardPath(AMapAppGlobal.getApplication());
        String appSDCardFileDir = FileUtil.getAppSDCardFileDir();
        String absolutePath = AMapAppGlobal.getApplication().getCacheDir().getAbsolutePath();
        String absolutePath2 = AMapAppGlobal.getApplication().getFilesDir().getAbsolutePath();
        if (!TextUtils.isEmpty(internalSDCardPath) && str.startsWith("$INTERNAL_SDCARD_PATH")) {
            str = str.replace("$INTERNAL_SDCARD_PATH", internalSDCardPath);
        } else if (!TextUtils.isEmpty(internalRefreshSDCardPath) && str.startsWith("$INTERNAL_REFRESH_SDCARD_PATH")) {
            str = str.replace("$INTERNAL_REFRESH_SDCARD_PATH", internalRefreshSDCardPath);
        } else if (!TextUtils.isEmpty(externalSDCardPath) && str.startsWith("$EXTERNAL_SDCARD_PATH")) {
            str = str.replace("$EXTERNAL_SDCARD_PATH", externalSDCardPath);
        } else if (!TextUtils.isEmpty(externalRefreshSDCardPath) && str.startsWith("$EXTERNAL_REFRESH_SDCARD_PATH")) {
            str = str.replace("$EXTERNAL_REFRESH_SDCARD_PATH", externalRefreshSDCardPath);
        } else if (!TextUtils.isEmpty(appSDCardFileDir) && str.startsWith("$APP_SDCARD_FILE_DIR")) {
            str = str.replace("$APP_SDCARD_FILE_DIR", appSDCardFileDir);
        } else if (!TextUtils.isEmpty(absolutePath) && str.startsWith("$CACHE_DIR")) {
            str = str.replace("$CACHE_DIR", absolutePath);
        } else if (!TextUtils.isEmpty(absolutePath2) && str.startsWith("$FILE_DIR")) {
            str = str.replace("$FILE_DIR", absolutePath2);
        }
        return str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x005d A[Catch:{ all -> 0x006e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r7) {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0057, all -> 0x0054 }
            r1.<init>(r7)     // Catch:{ Throwable -> 0x0057, all -> 0x0054 }
            java.lang.String r7 = "MD5"
            java.security.MessageDigest r7 = java.security.MessageDigest.getInstance(r7)     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r2 = "A23C58620BB90539E56EDDE7BE50FB44"
            java.lang.String r3 = "UTF-8"
            byte[] r2 = r2.getBytes(r3)     // Catch:{ Throwable -> 0x0052 }
            r7.update(r2)     // Catch:{ Throwable -> 0x0052 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ Throwable -> 0x0052 }
        L_0x001b:
            int r3 = r1.read(r2)     // Catch:{ Throwable -> 0x0052 }
            r4 = -1
            r5 = 0
            if (r3 == r4) goto L_0x0027
            r7.update(r2, r5, r3)     // Catch:{ Throwable -> 0x0052 }
            goto L_0x001b
        L_0x0027:
            byte[] r7 = r7.digest()     // Catch:{ Throwable -> 0x0052 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0052 }
            r2.<init>()     // Catch:{ Throwable -> 0x0052 }
            int r3 = r7.length     // Catch:{ Throwable -> 0x0052 }
        L_0x0031:
            if (r5 >= r3) goto L_0x004a
            byte r4 = r7[r5]     // Catch:{ Throwable -> 0x0052 }
            r4 = r4 & 255(0xff, float:3.57E-43)
            r6 = 16
            if (r4 >= r6) goto L_0x0040
            java.lang.String r6 = "0"
            r2.append(r6)     // Catch:{ Throwable -> 0x0052 }
        L_0x0040:
            java.lang.String r4 = java.lang.Integer.toHexString(r4)     // Catch:{ Throwable -> 0x0052 }
            r2.append(r4)     // Catch:{ Throwable -> 0x0052 }
            int r5 = r5 + 1
            goto L_0x0031
        L_0x004a:
            java.lang.String r7 = r2.toString()     // Catch:{ Throwable -> 0x0052 }
            defpackage.ahe.a(r1)
            return r7
        L_0x0052:
            r7 = move-exception
            goto L_0x0059
        L_0x0054:
            r7 = move-exception
            r1 = r0
            goto L_0x006f
        L_0x0057:
            r7 = move-exception
            r1 = r0
        L_0x0059:
            boolean r2 = defpackage.bno.a     // Catch:{ all -> 0x006e }
            if (r2 == 0) goto L_0x006a
            java.lang.String r2 = "T1"
            java.lang.String r3 = a     // Catch:{ all -> 0x006e }
            java.lang.String r4 = "getFileMD5 exception"
            java.lang.String r7 = android.util.Log.getStackTraceString(r7)     // Catch:{ all -> 0x006e }
            com.amap.bundle.logs.AMapLog.logNormalNative(r2, r3, r4, r7)     // Catch:{ all -> 0x006e }
        L_0x006a:
            defpackage.ahe.a(r1)
            return r0
        L_0x006e:
            r7 = move-exception
        L_0x006f:
            defpackage.ahe.a(r1)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xi.a(java.io.File):java.lang.String");
    }

    public static boolean b(String str) {
        return !TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str);
    }
}
