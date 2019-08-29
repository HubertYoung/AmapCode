package com.alipay.sdk.tid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.alipay.sdk.encrypt.b;
import java.lang.ref.WeakReference;

public final class a extends SQLiteOpenHelper {
    private static final String a = "msp.db";
    private static final int b = 1;
    private WeakReference<Context> c;

    public a(Context context) {
        super(context, a, null, 1);
        this.c = new WeakReference<>(context);
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists tb_tid (name text primary key, tid text, key_tid text, dt datetime);");
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("drop table if exists tb_tid");
        onCreate(sQLiteDatabase);
    }

    public final void a(String str, String str2, String str3, String str4) {
        SQLiteDatabase sQLiteDatabase;
        SQLiteDatabase sQLiteDatabase2 = null;
        try {
            sQLiteDatabase = getWritableDatabase();
            try {
                if (a(sQLiteDatabase, str, str2)) {
                    a(sQLiteDatabase, str, str2, str3, str4);
                } else {
                    String a2 = b.a(1, str3, com.alipay.sdk.util.a.c((Context) this.c.get()));
                    String c2 = c(str, str2);
                    sQLiteDatabase.execSQL("insert into tb_tid (name, tid, key_tid, dt) values (?, ?, ?, datetime('now', 'localtime'))", new Object[]{c2, a2, str4});
                    Cursor rawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", null);
                    if (rawQuery.getCount() <= 14) {
                        rawQuery.close();
                    } else {
                        int count = rawQuery.getCount() - 14;
                        String[] strArr = new String[count];
                        if (rawQuery.moveToFirst()) {
                            int i = 0;
                            do {
                                strArr[i] = rawQuery.getString(0);
                                i++;
                                if (!rawQuery.moveToNext()) {
                                    break;
                                }
                            } while (count > i);
                        }
                        rawQuery.close();
                        for (int i2 = 0; i2 < count; i2++) {
                            if (!TextUtils.isEmpty(strArr[i2])) {
                                a(sQLiteDatabase, strArr[i2]);
                            }
                        }
                    }
                }
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
            } catch (Exception unused) {
                sQLiteDatabase2 = sQLiteDatabase;
                if (sQLiteDatabase2 != null && sQLiteDatabase2.isOpen()) {
                    sQLiteDatabase2.close();
                }
            } catch (Throwable th) {
                th = th;
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
            sQLiteDatabase2.close();
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
            sQLiteDatabase.close();
            throw th;
        }
    }

    private void d(String str, String str2) {
        SQLiteDatabase sQLiteDatabase;
        SQLiteDatabase sQLiteDatabase2 = null;
        try {
            sQLiteDatabase = getWritableDatabase();
            try {
                a(sQLiteDatabase, str, str2, "", "");
                a(sQLiteDatabase, c(str, str2));
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
            } catch (Exception unused) {
                sQLiteDatabase2 = sQLiteDatabase;
                if (sQLiteDatabase2 != null && sQLiteDatabase2.isOpen()) {
                    sQLiteDatabase2.close();
                }
            } catch (Throwable th) {
                th = th;
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
            sQLiteDatabase2.close();
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
            sQLiteDatabase.close();
            throw th;
        }
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARNING: type inference failed for: r5v6, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r1v3, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r5v10 */
    /* JADX WARNING: type inference failed for: r5v12, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r6v7, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        if (r2.isOpen() != false) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        r2.close();
        r1 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0059, code lost:
        if (r2.isOpen() != false) goto L_0x002e;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], ?[OBJECT, ARRAY]]
      uses: [java.lang.CharSequence, java.lang.String, ?[int, boolean, OBJECT, ARRAY, byte, short, char], android.database.Cursor]
      mth insns count: 50
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = "select tid from tb_tid where name=?"
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r4.getReadableDatabase()     // Catch:{ Exception -> 0x004c, all -> 0x0039 }
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x0037, all -> 0x0035 }
            java.lang.String r5 = c(r5, r6)     // Catch:{ Exception -> 0x0037, all -> 0x0035 }
            r6 = 0
            r3[r6] = r5     // Catch:{ Exception -> 0x0037, all -> 0x0035 }
            android.database.Cursor r5 = r2.rawQuery(r0, r3)     // Catch:{ Exception -> 0x0037, all -> 0x0035 }
            boolean r0 = r5.moveToFirst()     // Catch:{ Exception -> 0x004e, all -> 0x0032 }
            if (r0 == 0) goto L_0x0021
            java.lang.String r6 = r5.getString(r6)     // Catch:{ Exception -> 0x004e, all -> 0x0032 }
            r1 = r6
        L_0x0021:
            if (r5 == 0) goto L_0x0026
            r5.close()
        L_0x0026:
            if (r2 == 0) goto L_0x005c
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L_0x005c
        L_0x002e:
            r2.close()
            goto L_0x005c
        L_0x0032:
            r6 = move-exception
            r1 = r5
            goto L_0x003b
        L_0x0035:
            r6 = move-exception
            goto L_0x003b
        L_0x0037:
            r5 = r1
            goto L_0x004e
        L_0x0039:
            r6 = move-exception
            r2 = r1
        L_0x003b:
            if (r1 == 0) goto L_0x0040
            r1.close()
        L_0x0040:
            if (r2 == 0) goto L_0x004b
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L_0x004b
            r2.close()
        L_0x004b:
            throw r6
        L_0x004c:
            r5 = r1
            r2 = r5
        L_0x004e:
            if (r5 == 0) goto L_0x0053
            r5.close()
        L_0x0053:
            if (r2 == 0) goto L_0x005c
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L_0x005c
            goto L_0x002e
        L_0x005c:
            boolean r5 = android.text.TextUtils.isEmpty(r1)
            if (r5 != 0) goto L_0x0073
            java.lang.ref.WeakReference<android.content.Context> r5 = r4.c
            java.lang.Object r5 = r5.get()
            android.content.Context r5 = (android.content.Context) r5
            java.lang.String r5 = com.alipay.sdk.util.a.c(r5)
            r6 = 2
            java.lang.String r1 = com.alipay.sdk.encrypt.b.a(r6, r1, r5)
        L_0x0073:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.a(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        if (r4.isOpen() != false) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0044, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006f, code lost:
        if (r4.isOpen() != false) goto L_0x0044;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x006b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long e(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            java.lang.String r0 = "select dt from tb_tid where name=?"
            r1 = 0
            r2 = 0
            android.database.sqlite.SQLiteDatabase r4 = r6.getReadableDatabase()     // Catch:{ Exception -> 0x0063, all -> 0x004f }
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ Exception -> 0x0064, all -> 0x004c }
            java.lang.String r7 = c(r7, r8)     // Catch:{ Exception -> 0x0064, all -> 0x004c }
            r8 = 0
            r5[r8] = r7     // Catch:{ Exception -> 0x0064, all -> 0x004c }
            android.database.Cursor r7 = r4.rawQuery(r0, r5)     // Catch:{ Exception -> 0x0064, all -> 0x004c }
            boolean r0 = r7.moveToFirst()     // Catch:{ Exception -> 0x004a, all -> 0x0048 }
            if (r0 == 0) goto L_0x0037
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x004a, all -> 0x0048 }
            java.lang.String r1 = "yyyy-MM-dd HH:mm:ss"
            java.util.Locale r5 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x004a, all -> 0x0048 }
            r0.<init>(r1, r5)     // Catch:{ Exception -> 0x004a, all -> 0x0048 }
            java.lang.String r8 = r7.getString(r8)     // Catch:{ Exception -> 0x004a, all -> 0x0048 }
            java.util.Date r8 = r0.parse(r8)     // Catch:{ Exception -> 0x004a, all -> 0x0048 }
            long r0 = r8.getTime()     // Catch:{ Exception -> 0x004a, all -> 0x0048 }
            r2 = r0
        L_0x0037:
            if (r7 == 0) goto L_0x003c
            r7.close()
        L_0x003c:
            if (r4 == 0) goto L_0x0072
            boolean r7 = r4.isOpen()
            if (r7 == 0) goto L_0x0072
        L_0x0044:
            r4.close()
            goto L_0x0072
        L_0x0048:
            r8 = move-exception
            goto L_0x0052
        L_0x004a:
            r1 = r7
            goto L_0x0064
        L_0x004c:
            r8 = move-exception
            r7 = r1
            goto L_0x0052
        L_0x004f:
            r8 = move-exception
            r7 = r1
            r4 = r7
        L_0x0052:
            if (r7 == 0) goto L_0x0057
            r7.close()
        L_0x0057:
            if (r4 == 0) goto L_0x0062
            boolean r7 = r4.isOpen()
            if (r7 == 0) goto L_0x0062
            r4.close()
        L_0x0062:
            throw r8
        L_0x0063:
            r4 = r1
        L_0x0064:
            if (r1 == 0) goto L_0x0069
            r1.close()
        L_0x0069:
            if (r4 == 0) goto L_0x0072
            boolean r7 = r4.isOpen()
            if (r7 == 0) goto L_0x0072
            goto L_0x0044
        L_0x0072:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.e(java.lang.String, java.lang.String):long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0042, code lost:
        if (r2.isOpen() != false) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006c, code lost:
        if (r2.isOpen() != false) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x006e, code lost:
        r2.close();
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0068  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.String> a() {
        /*
            r6 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r6.getReadableDatabase()     // Catch:{ Exception -> 0x0060, all -> 0x004c }
            java.lang.String r3 = "select tid from tb_tid"
            android.database.Cursor r3 = r2.rawQuery(r3, r1)     // Catch:{ Exception -> 0x0061, all -> 0x0049 }
        L_0x0011:
            boolean r1 = r3.moveToNext()     // Catch:{ Exception -> 0x0047, all -> 0x0045 }
            if (r1 == 0) goto L_0x0037
            r1 = 0
            java.lang.String r1 = r3.getString(r1)     // Catch:{ Exception -> 0x0047, all -> 0x0045 }
            boolean r4 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0047, all -> 0x0045 }
            if (r4 != 0) goto L_0x0011
            java.lang.ref.WeakReference<android.content.Context> r4 = r6.c     // Catch:{ Exception -> 0x0047, all -> 0x0045 }
            java.lang.Object r4 = r4.get()     // Catch:{ Exception -> 0x0047, all -> 0x0045 }
            android.content.Context r4 = (android.content.Context) r4     // Catch:{ Exception -> 0x0047, all -> 0x0045 }
            java.lang.String r4 = com.alipay.sdk.util.a.c(r4)     // Catch:{ Exception -> 0x0047, all -> 0x0045 }
            r5 = 2
            java.lang.String r1 = com.alipay.sdk.encrypt.b.a(r5, r1, r4)     // Catch:{ Exception -> 0x0047, all -> 0x0045 }
            r0.add(r1)     // Catch:{ Exception -> 0x0047, all -> 0x0045 }
            goto L_0x0011
        L_0x0037:
            if (r3 == 0) goto L_0x003c
            r3.close()
        L_0x003c:
            if (r2 == 0) goto L_0x0071
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x0071
            goto L_0x006e
        L_0x0045:
            r0 = move-exception
            goto L_0x004f
        L_0x0047:
            r1 = r3
            goto L_0x0061
        L_0x0049:
            r0 = move-exception
            r3 = r1
            goto L_0x004f
        L_0x004c:
            r0 = move-exception
            r2 = r1
            r3 = r2
        L_0x004f:
            if (r3 == 0) goto L_0x0054
            r3.close()
        L_0x0054:
            if (r2 == 0) goto L_0x005f
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x005f
            r2.close()
        L_0x005f:
            throw r0
        L_0x0060:
            r2 = r1
        L_0x0061:
            if (r1 == 0) goto L_0x0066
            r1.close()
        L_0x0066:
            if (r2 == 0) goto L_0x0071
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L_0x0071
        L_0x006e:
            r2.close()
        L_0x0071:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.a():java.util.List");
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r5v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r1v2, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r5v7, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r6v6, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        if (r2.isOpen() != false) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        r2.close();
        r1 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0059, code lost:
        if (r2.isOpen() != false) goto L_0x002e;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], ?[OBJECT, ARRAY]]
      uses: [java.lang.String, ?[int, boolean, OBJECT, ARRAY, byte, short, char], android.database.Cursor]
      mth insns count: 42
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0055  */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String b(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = "select key_tid from tb_tid where name=?"
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r4.getReadableDatabase()     // Catch:{ Exception -> 0x004c, all -> 0x0039 }
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x0037, all -> 0x0035 }
            java.lang.String r5 = c(r5, r6)     // Catch:{ Exception -> 0x0037, all -> 0x0035 }
            r6 = 0
            r3[r6] = r5     // Catch:{ Exception -> 0x0037, all -> 0x0035 }
            android.database.Cursor r5 = r2.rawQuery(r0, r3)     // Catch:{ Exception -> 0x0037, all -> 0x0035 }
            boolean r0 = r5.moveToFirst()     // Catch:{ Exception -> 0x004e, all -> 0x0032 }
            if (r0 == 0) goto L_0x0021
            java.lang.String r6 = r5.getString(r6)     // Catch:{ Exception -> 0x004e, all -> 0x0032 }
            r1 = r6
        L_0x0021:
            if (r5 == 0) goto L_0x0026
            r5.close()
        L_0x0026:
            if (r2 == 0) goto L_0x005c
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L_0x005c
        L_0x002e:
            r2.close()
            goto L_0x005c
        L_0x0032:
            r6 = move-exception
            r1 = r5
            goto L_0x003b
        L_0x0035:
            r6 = move-exception
            goto L_0x003b
        L_0x0037:
            r5 = r1
            goto L_0x004e
        L_0x0039:
            r6 = move-exception
            r2 = r1
        L_0x003b:
            if (r1 == 0) goto L_0x0040
            r1.close()
        L_0x0040:
            if (r2 == 0) goto L_0x004b
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L_0x004b
            r2.close()
        L_0x004b:
            throw r6
        L_0x004c:
            r5 = r1
            r2 = r5
        L_0x004e:
            if (r5 == 0) goto L_0x0053
            r5.close()
        L_0x0053:
            if (r2 == 0) goto L_0x005c
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L_0x005c
            goto L_0x002e
        L_0x005c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.b(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0037 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0038 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(android.database.sqlite.SQLiteDatabase r5, java.lang.String r6, java.lang.String r7) {
        /*
            java.lang.String r0 = "select count(*) from tb_tid where name=?"
            r1 = 1
            r2 = 0
            r3 = 0
            java.lang.String[] r4 = new java.lang.String[r1]     // Catch:{ Exception -> 0x002e, all -> 0x0027 }
            java.lang.String r6 = c(r6, r7)     // Catch:{ Exception -> 0x002e, all -> 0x0027 }
            r4[r2] = r6     // Catch:{ Exception -> 0x002e, all -> 0x0027 }
            android.database.Cursor r5 = r5.rawQuery(r0, r4)     // Catch:{ Exception -> 0x002e, all -> 0x0027 }
            boolean r6 = r5.moveToFirst()     // Catch:{ Exception -> 0x002f, all -> 0x0024 }
            if (r6 == 0) goto L_0x001d
            int r6 = r5.getInt(r2)     // Catch:{ Exception -> 0x002f, all -> 0x0024 }
            goto L_0x001e
        L_0x001d:
            r6 = 0
        L_0x001e:
            if (r5 == 0) goto L_0x0035
            r5.close()
            goto L_0x0035
        L_0x0024:
            r6 = move-exception
            r3 = r5
            goto L_0x0028
        L_0x0027:
            r6 = move-exception
        L_0x0028:
            if (r3 == 0) goto L_0x002d
            r3.close()
        L_0x002d:
            throw r6
        L_0x002e:
            r5 = r3
        L_0x002f:
            if (r5 == 0) goto L_0x0034
            r5.close()
        L_0x0034:
            r6 = 0
        L_0x0035:
            if (r6 <= 0) goto L_0x0038
            return r1
        L_0x0038:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.a(android.database.sqlite.SQLiteDatabase, java.lang.String, java.lang.String):boolean");
    }

    static String c(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    private void b(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) {
        String a2 = b.a(1, str3, com.alipay.sdk.util.a.c((Context) this.c.get()));
        String c2 = c(str, str2);
        sQLiteDatabase.execSQL("insert into tb_tid (name, tid, key_tid, dt) values (?, ?, ?, datetime('now', 'localtime'))", new Object[]{c2, a2, str4});
        Cursor rawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", null);
        if (rawQuery.getCount() <= 14) {
            rawQuery.close();
            return;
        }
        int count = rawQuery.getCount() - 14;
        String[] strArr = new String[count];
        if (rawQuery.moveToFirst()) {
            int i = 0;
            do {
                strArr[i] = rawQuery.getString(0);
                i++;
                if (!rawQuery.moveToNext()) {
                    break;
                }
            } while (count > i);
        }
        rawQuery.close();
        for (int i2 = 0; i2 < count; i2++) {
            if (!TextUtils.isEmpty(strArr[i2])) {
                a(sQLiteDatabase, strArr[i2]);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) {
        sQLiteDatabase.execSQL("update tb_tid set tid=?, key_tid=?, dt=datetime('now', 'localtime') where name=?", new Object[]{b.a(1, str3, com.alipay.sdk.util.a.c((Context) this.c.get())), str4, c(str, str2)});
    }

    static void a(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            sQLiteDatabase.delete("tb_tid", "name=?", new String[]{str});
        } catch (Exception unused) {
        }
    }

    private static void a(SQLiteDatabase sQLiteDatabase) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", null);
        if (rawQuery.getCount() <= 14) {
            rawQuery.close();
            return;
        }
        int count = rawQuery.getCount() - 14;
        String[] strArr = new String[count];
        if (rawQuery.moveToFirst()) {
            int i = 0;
            do {
                strArr[i] = rawQuery.getString(0);
                i++;
                if (!rawQuery.moveToNext()) {
                    break;
                }
            } while (count > i);
        }
        rawQuery.close();
        for (int i2 = 0; i2 < count; i2++) {
            if (!TextUtils.isEmpty(strArr[i2])) {
                a(sQLiteDatabase, strArr[i2]);
            }
        }
    }
}
