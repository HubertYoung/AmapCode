package defpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.lotuspool.internal.model.CommandResultDao.Properties;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressFBWarnings({"NP_LOAD_OF_KNOWN_NULL_VALUE"})
/* renamed from: xe reason: default package */
/* compiled from: LotusPoolDBManager */
public class xe {
    private static final String c = "xe";
    public xd a;
    public a b;

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0065, code lost:
        if (r0 == null) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0059, code lost:
        if (r0 != null) goto L_0x005b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public xe(android.content.Context r8) {
        /*
            r7 = this;
            r7.<init>()
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r0 = r0.getApplicationContext()
            android.content.Context r0 = r0.getApplicationContext()
            java.lang.String r1 = "lotuspool.db"
            java.io.File r0 = r0.getDatabasePath(r1)     // Catch:{ Throwable -> 0x006e }
            java.lang.String r0 = r0.getPath()     // Catch:{ Throwable -> 0x006e }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x006e }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x006e }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x006e }
            if (r1 != 0) goto L_0x0025
            goto L_0x007d
        L_0x0025:
            r1 = 16
            r2 = 0
            android.database.sqlite.SQLiteDatabase r0 = android.database.sqlite.SQLiteDatabase.openDatabase(r0, r2, r1, r2)     // Catch:{ Throwable -> 0x006e }
            if (r0 != 0) goto L_0x002f
            goto L_0x007d
        L_0x002f:
            java.util.Locale r1 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x0061 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0061 }
            android.content.ContentValues r3 = new android.content.ContentValues     // Catch:{ Exception -> 0x0061 }
            r3.<init>()     // Catch:{ Exception -> 0x0061 }
            java.lang.String r4 = "locale"
            r3.put(r4, r1)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r4 = "android_metadata"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0061 }
            java.lang.String r6 = "locale!='"
            r5.<init>(r6)     // Catch:{ Exception -> 0x0061 }
            r5.append(r1)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r1 = "'"
            r5.append(r1)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r1 = r5.toString()     // Catch:{ Exception -> 0x0061 }
            r0.update(r4, r3, r1, r2)     // Catch:{ Exception -> 0x0061 }
            if (r0 == 0) goto L_0x007d
        L_0x005b:
            r0.close()     // Catch:{ Throwable -> 0x006e }
            goto L_0x007d
        L_0x005f:
            r1 = move-exception
            goto L_0x0068
        L_0x0061:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x005f }
            if (r0 == 0) goto L_0x007d
            goto L_0x005b
        L_0x0068:
            if (r0 == 0) goto L_0x006d
            r0.close()     // Catch:{ Throwable -> 0x006e }
        L_0x006d:
            throw r1     // Catch:{ Throwable -> 0x006e }
        L_0x006e:
            r0 = move-exception
            java.lang.String r1 = "T1"
            java.lang.String r2 = c
            java.lang.String r3 = "updateLocale:"
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)
            com.amap.bundle.logs.AMapLog.logFatalNative(r1, r2, r3, r0)
        L_0x007d:
            xc$a r0 = new xc$a
            java.lang.String r1 = "lotuspool.db"
            r0.<init>(r8, r1)
            r7.b = r0
            xc r8 = new xc
            xc$a r0 = r7.b
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()
            r8.<init>(r0)
            xd r8 = r8.newSession()
            r7.a = r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xe.<init>(android.content.Context):void");
    }

    /* access modifiers changed from: 0000 */
    public final void a(CommandResult commandResult) {
        if (commandResult != null) {
            this.a.insertOrReplace(commandResult);
        }
    }

    @Nullable
    public final Map<String, xg> a() {
        List<CommandResult> list = this.a.queryBuilder(CommandResult.class).orderAsc(Properties.b, Properties.f).build().list();
        xg xgVar = null;
        if (list == null || list.isEmpty()) {
            return null;
        }
        HashMap hashMap = new HashMap(list.size());
        for (CommandResult commandResult : list) {
            if (commandResult.b()) {
                if (xgVar == null || !TextUtils.equals(commandResult.a(), xgVar.a())) {
                    xgVar = new xg(commandResult.a, commandResult.b, commandResult.d, commandResult.e);
                    hashMap.put(xgVar.a(), xgVar);
                }
                xgVar.a(commandResult);
            }
        }
        return hashMap;
    }
}
