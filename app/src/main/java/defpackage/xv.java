package defpackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.system.Os;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.inter.IMultipleServiceLoader;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: xv reason: default package */
/* compiled from: DbManager */
public class xv {
    private static xs a;
    private static xt b;
    private static xv c;
    private static a d;

    /* renamed from: xv$a */
    /* compiled from: DbManager */
    public static class a extends SQLiteOpenHelper {
        Throwable a = null;

        a(Context context, String str) {
            super(context, str, null, 57);
        }

        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            try {
                xs.a(sQLiteDatabase);
            } catch (RuntimeException e) {
                StringBuilder sb = new StringBuilder("DbOpenHelper onCreate: ");
                sb.append(String.valueOf(e));
                AMapLog.logFatalNative(AMapLog.GROUP_COMMON, "P0001", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, sb.toString());
                this.a = e;
                throw e;
            }
        }

        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            try {
                List<xy> a2 = a();
                if (a2.size() > 0) {
                    for (xy a3 : a2) {
                        a3.a(sQLiteDatabase, i, i2);
                    }
                }
            } catch (RuntimeException e) {
                this.a = e;
                throw e;
            }
        }

        public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            try {
                xs.b(sQLiteDatabase);
                xs.a(sQLiteDatabase);
                List<xy> a2 = a();
                if (a2.size() > 0) {
                    Iterator<xy> it = a2.iterator();
                    while (it.hasNext()) {
                        it.next();
                    }
                }
            } catch (RuntimeException e) {
                this.a = e;
                throw e;
            }
        }

        private static List<xy> a() {
            List<Class<? extends T>> loadServices = ((IMultipleServiceLoader) bqn.a(IMultipleServiceLoader.class)).loadServices(xy.class);
            ArrayList arrayList = new ArrayList();
            if (loadServices == null) {
                return arrayList;
            }
            for (Class newInstance : loadServices) {
                try {
                    arrayList.add((xy) newInstance.newInstance());
                } catch (Exception e) {
                    AMapLog.logErrorNative(AMapLog.GROUP_COMMON, "P0001", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, String.valueOf(e));
                    if (bno.a) {
                        throw new IllegalArgumentException(e);
                    }
                    e.printStackTrace();
                }
            }
            return arrayList;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00f0, code lost:
        if (r0 != null) goto L_0x00f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00fc, code lost:
        if (r0 == null) goto L_0x0105;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private xv() {
        /*
            r7 = this;
            r7.<init>()
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 17
            if (r0 < r1) goto L_0x0029
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r2 = r2.getApplicationContext()
            android.content.pm.ApplicationInfo r2 = r2.getApplicationInfo()
            java.lang.String r2 = r2.dataDir
            r0.append(r2)
            java.lang.String r2 = "/"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            goto L_0x0048
        L_0x0029:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "/data/data/"
            r0.<init>(r2)
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r2 = r2.getApplicationContext()
            java.lang.String r2 = r2.getPackageName()
            r0.append(r2)
            java.lang.String r2 = "/"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
        L_0x0048:
            a(r0)
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r1) goto L_0x006f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r1 = r1.getApplicationContext()
            android.content.pm.ApplicationInfo r1 = r1.getApplicationInfo()
            java.lang.String r1 = r1.dataDir
            r0.append(r1)
            java.lang.String r1 = "/databases/"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            goto L_0x008e
        L_0x006f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "/data/data/"
            r0.<init>(r1)
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r1 = r1.getApplicationContext()
            java.lang.String r1 = r1.getPackageName()
            r0.append(r1)
            java.lang.String r1 = "/databases/"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
        L_0x008e:
            a(r0)
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "aMap.db"
            java.io.File r0 = r0.getDatabasePath(r1)
            java.lang.String r0 = r0.getPath()
            a(r0)
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "aMap.db"
            java.io.File r0 = r0.getDatabasePath(r1)     // Catch:{ Throwable -> 0x0105 }
            java.lang.String r0 = r0.getPath()     // Catch:{ Throwable -> 0x0105 }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0105 }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x0105 }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x0105 }
            if (r1 != 0) goto L_0x00bc
            goto L_0x0105
        L_0x00bc:
            r1 = 16
            r2 = 0
            android.database.sqlite.SQLiteDatabase r0 = android.database.sqlite.SQLiteDatabase.openDatabase(r0, r2, r1, r2)     // Catch:{ Throwable -> 0x0105 }
            if (r0 != 0) goto L_0x00c6
            goto L_0x0105
        L_0x00c6:
            java.util.Locale r1 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00f8 }
            android.content.ContentValues r3 = new android.content.ContentValues     // Catch:{ Exception -> 0x00f8 }
            r3.<init>()     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r4 = "locale"
            r3.put(r4, r1)     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r4 = "android_metadata"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r6 = "locale!='"
            r5.<init>(r6)     // Catch:{ Exception -> 0x00f8 }
            r5.append(r1)     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r1 = "'"
            r5.append(r1)     // Catch:{ Exception -> 0x00f8 }
            java.lang.String r1 = r5.toString()     // Catch:{ Exception -> 0x00f8 }
            r0.update(r4, r3, r1, r2)     // Catch:{ Exception -> 0x00f8 }
            if (r0 == 0) goto L_0x0105
        L_0x00f2:
            r0.close()     // Catch:{ Throwable -> 0x0105 }
            goto L_0x0105
        L_0x00f6:
            r1 = move-exception
            goto L_0x00ff
        L_0x00f8:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x00f6 }
            if (r0 == 0) goto L_0x0105
            goto L_0x00f2
        L_0x00ff:
            if (r0 == 0) goto L_0x0104
            r0.close()     // Catch:{ Throwable -> 0x0105 }
        L_0x0104:
            throw r1     // Catch:{ Throwable -> 0x0105 }
        L_0x0105:
            xv$a r0 = new xv$a
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r2 = "aMap.db"
            r0.<init>(r1, r2)
            d = r0
            xs r0 = new xs     // Catch:{ RuntimeException -> 0x012a }
            xv$a r1 = d     // Catch:{ RuntimeException -> 0x012a }
            android.database.sqlite.SQLiteDatabase r1 = de.greenrobot.dao.DbUtils.getWritableDatabaseBusyWait(r1)     // Catch:{ RuntimeException -> 0x012a }
            r0.<init>(r1)     // Catch:{ RuntimeException -> 0x012a }
            a = r0     // Catch:{ RuntimeException -> 0x012a }
            xs r0 = a
            de.greenrobot.dao.identityscope.IdentityScopeType r1 = de.greenrobot.dao.identityscope.IdentityScopeType.None
            xt r0 = r0.newSession(r1)
            b = r0
            return
        L_0x012a:
            r0 = move-exception
            xv$a r1 = d
            java.lang.Throwable r1 = r1.a
            if (r1 == 0) goto L_0x0179
            xv$a r1 = d
            java.lang.Throwable r1 = r1.a
            if (r0 == r1) goto L_0x0179
            java.lang.String r1 = "T1"
            java.lang.String r2 = "P0001"
            java.lang.String r3 = "E001"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x015a }
            java.lang.String r5 = "DbManager constructor original ex: "
            r4.<init>(r5)     // Catch:{ Throwable -> 0x015a }
            java.lang.String r5 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x015a }
            r4.append(r5)     // Catch:{ Throwable -> 0x015a }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x015a }
            com.amap.bundle.logs.AMapLog.logFatalNative(r1, r2, r3, r4)     // Catch:{ Throwable -> 0x015a }
            xv$a r1 = d     // Catch:{ Throwable -> 0x015a }
            java.lang.Throwable r1 = r1.a     // Catch:{ Throwable -> 0x015a }
            r0.initCause(r1)     // Catch:{ Throwable -> 0x015a }
            goto L_0x0179
        L_0x015a:
            r1 = move-exception
            r1.printStackTrace()
            java.lang.String r2 = "T1"
            java.lang.String r3 = "P0001"
            java.lang.String r4 = "E001"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "DbManager constructor initCause ex: "
            r5.<init>(r6)
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r5.append(r1)
            java.lang.String r1 = r5.toString()
            com.amap.bundle.logs.AMapLog.logFatalNative(r2, r3, r4, r1)
        L_0x0179:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xv.<init>():void");
    }

    private static void a(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                if (!file.canWrite() || !file.canRead()) {
                    file.setWritable(true);
                    file.setReadable(true);
                    Os.chmod(file.getPath(), 504);
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("DbManager checkPermission: ");
            sb.append(e.toString());
            AMapLog.error("paas.db", "DbManager", sb.toString());
        }
    }

    public static xs a() {
        c();
        return a;
    }

    private static xv c() {
        if (c == null) {
            synchronized (xv.class) {
                if (c == null) {
                    c = new xv();
                }
            }
        }
        return c;
    }

    public static xt b() {
        c();
        return b;
    }
}
