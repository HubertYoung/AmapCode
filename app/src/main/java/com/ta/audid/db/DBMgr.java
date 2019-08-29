package com.ta.audid.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.ta.audid.db.annotation.Column;
import com.ta.audid.db.annotation.Ingore;
import com.ta.audid.db.annotation.TableName;
import com.ta.audid.utils.UtdidLogger;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DBMgr {
    private static final String TAG = "DBMgr";
    private HashMap<String, Boolean> mCheckdbMap = new HashMap<>();
    private HashMap<Class<?>, List<Field>> mClsFieldsMap = new HashMap<>();
    private HashMap<Class<?>, String> mClsTableNameMap = new HashMap<>();
    private String mDbName;
    private HashMap<Field, String> mFieldNameMap = new HashMap<>();
    private SqliteHelper mHelper;

    public DBMgr(Context context, String str) {
        this.mHelper = new SqliteHelper(context, str);
        this.mDbName = str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0115, code lost:
        r12 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0117, code lost:
        r12 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0115 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:29:0x007b] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:68:0x0107=Splitter:B:68:0x0107, B:86:0x0138=Splitter:B:86:0x0138} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.List<? extends com.ta.audid.db.Entity> find(java.lang.Class<? extends com.ta.audid.db.Entity> r12, java.lang.String r13, java.lang.String r14, int r15) {
        /*
            r11 = this;
            monitor-enter(r11)
            java.util.List r0 = java.util.Collections.EMPTY_LIST     // Catch:{ all -> 0x0143 }
            if (r12 != 0) goto L_0x0007
            monitor-exit(r11)
            return r0
        L_0x0007:
            java.lang.String r1 = r11.getTablename(r12)     // Catch:{ all -> 0x0143 }
            android.database.sqlite.SQLiteDatabase r2 = r11.checkTableAvailable(r12, r1)     // Catch:{ all -> 0x0143 }
            r3 = 0
            if (r2 != 0) goto L_0x001b
            java.lang.String r12 = "db is null"
            java.lang.Object[] r13 = new java.lang.Object[r3]     // Catch:{ all -> 0x0143 }
            com.ta.audid.utils.UtdidLogger.d(r12, r13)     // Catch:{ all -> 0x0143 }
            monitor-exit(r11)
            return r0
        L_0x001b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0143 }
            java.lang.String r5 = "SELECT * FROM "
            r4.<init>(r5)     // Catch:{ all -> 0x0143 }
            r4.append(r1)     // Catch:{ all -> 0x0143 }
            boolean r1 = android.text.TextUtils.isEmpty(r13)     // Catch:{ all -> 0x0143 }
            if (r1 == 0) goto L_0x002e
            java.lang.String r13 = ""
            goto L_0x0038
        L_0x002e:
            java.lang.String r1 = " WHERE "
            java.lang.String r13 = java.lang.String.valueOf(r13)     // Catch:{ all -> 0x0143 }
            java.lang.String r13 = r1.concat(r13)     // Catch:{ all -> 0x0143 }
        L_0x0038:
            r4.append(r13)     // Catch:{ all -> 0x0143 }
            boolean r13 = android.text.TextUtils.isEmpty(r14)     // Catch:{ all -> 0x0143 }
            if (r13 == 0) goto L_0x0044
            java.lang.String r13 = ""
            goto L_0x004e
        L_0x0044:
            java.lang.String r13 = " ORDER BY "
            java.lang.String r14 = java.lang.String.valueOf(r14)     // Catch:{ all -> 0x0143 }
            java.lang.String r13 = r13.concat(r14)     // Catch:{ all -> 0x0143 }
        L_0x004e:
            r4.append(r13)     // Catch:{ all -> 0x0143 }
            if (r15 > 0) goto L_0x0056
            java.lang.String r13 = ""
            goto L_0x0060
        L_0x0056:
            java.lang.String r13 = " LIMIT "
            java.lang.String r14 = java.lang.String.valueOf(r15)     // Catch:{ all -> 0x0143 }
            java.lang.String r13 = r13.concat(r14)     // Catch:{ all -> 0x0143 }
        L_0x0060:
            r4.append(r13)     // Catch:{ all -> 0x0143 }
            java.lang.String r13 = r4.toString()     // Catch:{ all -> 0x0143 }
            java.lang.String r14 = "DBMgr"
            r15 = 2
            java.lang.Object[] r1 = new java.lang.Object[r15]     // Catch:{ all -> 0x0143 }
            java.lang.String r4 = "sql"
            r1[r3] = r4     // Catch:{ all -> 0x0143 }
            r4 = 1
            r1[r4] = r13     // Catch:{ all -> 0x0143 }
            com.ta.audid.utils.UtdidLogger.d(r14, r1)     // Catch:{ all -> 0x0143 }
            r14 = 0
            android.database.Cursor r13 = r2.rawQuery(r13, r14)     // Catch:{ Throwable -> 0x011d }
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0117, all -> 0x0115 }
            r14.<init>()     // Catch:{ Throwable -> 0x0117, all -> 0x0115 }
            java.util.List r0 = r11.getAllFields(r12)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
        L_0x0084:
            if (r13 == 0) goto L_0x0107
            boolean r1 = r13.moveToNext()     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            if (r1 == 0) goto L_0x0107
            java.lang.Object r1 = r12.newInstance()     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            com.ta.audid.db.Entity r1 = (com.ta.audid.db.Entity) r1     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            r5 = 0
        L_0x0093:
            int r6 = r0.size()     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            if (r5 >= r6) goto L_0x0102
            java.lang.Object r6 = r0.get(r5)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            java.lang.reflect.Field r6 = (java.lang.reflect.Field) r6     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            java.lang.Class r7 = r6.getType()     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            java.lang.String r8 = r11.getColumnName(r6)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            int r9 = r13.getColumnIndex(r8)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            r10 = -1
            if (r9 == r10) goto L_0x00ec
            java.lang.Class<java.lang.Long> r8 = java.lang.Long.class
            if (r7 == r8) goto L_0x00e0
            java.lang.Class r8 = java.lang.Long.TYPE     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            if (r7 != r8) goto L_0x00b7
            goto L_0x00e0
        L_0x00b7:
            java.lang.Class<java.lang.Integer> r8 = java.lang.Integer.class
            if (r7 == r8) goto L_0x00d7
            java.lang.Class r8 = java.lang.Integer.TYPE     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            if (r7 != r8) goto L_0x00c0
            goto L_0x00d7
        L_0x00c0:
            java.lang.Class r8 = java.lang.Double.TYPE     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            if (r7 == r8) goto L_0x00ce
            java.lang.Class<java.lang.Double> r8 = java.lang.Double.class
            if (r7 != r8) goto L_0x00c9
            goto L_0x00ce
        L_0x00c9:
            java.lang.String r7 = r13.getString(r9)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            goto L_0x00e8
        L_0x00ce:
            double r7 = r13.getDouble(r9)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            java.lang.Double r7 = java.lang.Double.valueOf(r7)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            goto L_0x00e8
        L_0x00d7:
            int r7 = r13.getInt(r9)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            goto L_0x00e8
        L_0x00e0:
            long r7 = r13.getLong(r9)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
        L_0x00e8:
            r6.set(r1, r7)     // Catch:{ Exception -> 0x00ff }
            goto L_0x00ff
        L_0x00ec:
            java.lang.String r6 = "DBMgr"
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            java.lang.String r9 = "can not get field: "
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            java.lang.String r8 = r9.concat(r8)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            r7[r3] = r8     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            com.ta.audid.utils.UtdidLogger.w(r6, r7)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
        L_0x00ff:
            int r5 = r5 + 1
            goto L_0x0093
        L_0x0102:
            r14.add(r1)     // Catch:{ Throwable -> 0x0112, all -> 0x0115 }
            goto L_0x0084
        L_0x0107:
            com.ta.audid.db.SqliteHelper r12 = r11.mHelper     // Catch:{ all -> 0x0143 }
            r12.closeCursor(r13)     // Catch:{ all -> 0x0143 }
            com.ta.audid.db.SqliteHelper r12 = r11.mHelper     // Catch:{ all -> 0x0143 }
            r12.closeWritableDatabase(r2)     // Catch:{ all -> 0x0143 }
            goto L_0x0136
        L_0x0112:
            r12 = move-exception
            r0 = r14
            goto L_0x0118
        L_0x0115:
            r12 = move-exception
            goto L_0x0138
        L_0x0117:
            r12 = move-exception
        L_0x0118:
            r14 = r13
            goto L_0x011e
        L_0x011a:
            r12 = move-exception
            r13 = r14
            goto L_0x0138
        L_0x011d:
            r12 = move-exception
        L_0x011e:
            java.lang.String r13 = "DBMgr"
            java.lang.Object[] r15 = new java.lang.Object[r15]     // Catch:{ all -> 0x011a }
            java.lang.String r1 = "[get]"
            r15[r3] = r1     // Catch:{ all -> 0x011a }
            r15[r4] = r12     // Catch:{ all -> 0x011a }
            com.ta.audid.utils.UtdidLogger.w(r13, r15)     // Catch:{ all -> 0x011a }
            com.ta.audid.db.SqliteHelper r12 = r11.mHelper     // Catch:{ all -> 0x0143 }
            r12.closeCursor(r14)     // Catch:{ all -> 0x0143 }
            com.ta.audid.db.SqliteHelper r12 = r11.mHelper     // Catch:{ all -> 0x0143 }
            r12.closeWritableDatabase(r2)     // Catch:{ all -> 0x0143 }
            r14 = r0
        L_0x0136:
            monitor-exit(r11)
            return r14
        L_0x0138:
            com.ta.audid.db.SqliteHelper r14 = r11.mHelper     // Catch:{ all -> 0x0143 }
            r14.closeCursor(r13)     // Catch:{ all -> 0x0143 }
            com.ta.audid.db.SqliteHelper r13 = r11.mHelper     // Catch:{ all -> 0x0143 }
            r13.closeWritableDatabase(r2)     // Catch:{ all -> 0x0143 }
            throw r12     // Catch:{ all -> 0x0143 }
        L_0x0143:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.DBMgr.find(java.lang.Class, java.lang.String, java.lang.String, int):java.util.List");
    }

    public void insert(Entity entity) {
        if (entity != null) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(entity);
            insert((List<? extends Entity>) arrayList);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:12|13|(8:16|(6:19|20|21|(2:23|76)(2:24|77)|29|17)|74|30|(2:32|(1:34)(1:35))(1:36)|37|38|14)|39|40|41|42|43|44|45|46) */
    /* JADX WARNING: Can't wrap try/catch for region: R(12:49|50|51|52|53|54|55|56|57|58|59|60) */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:47|48|61|62|63|64|65|66|67) */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0156, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x011c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x011f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x013b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:57:0x013e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:63:0x0148 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:65:0x014b */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:65:0x014b=Splitter:B:65:0x014b, B:57:0x013e=Splitter:B:57:0x013e, B:43:0x011f=Splitter:B:43:0x011f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void insert(java.util.List<? extends com.ta.audid.db.Entity> r20) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            monitor-enter(r19)
            if (r2 == 0) goto L_0x0155
            int r3 = r20.size()     // Catch:{ all -> 0x0151 }
            if (r3 != 0) goto L_0x000f
            goto L_0x0155
        L_0x000f:
            r3 = 0
            java.lang.Object r4 = r2.get(r3)     // Catch:{ all -> 0x0151 }
            com.ta.audid.db.Entity r4 = (com.ta.audid.db.Entity) r4     // Catch:{ all -> 0x0151 }
            java.lang.Class r4 = r4.getClass()     // Catch:{ all -> 0x0151 }
            java.lang.String r4 = r1.getTablename(r4)     // Catch:{ all -> 0x0151 }
            java.lang.Object r5 = r2.get(r3)     // Catch:{ all -> 0x0151 }
            com.ta.audid.db.Entity r5 = (com.ta.audid.db.Entity) r5     // Catch:{ all -> 0x0151 }
            java.lang.Class r5 = r5.getClass()     // Catch:{ all -> 0x0151 }
            android.database.sqlite.SQLiteDatabase r5 = r1.checkTableAvailable(r5, r4)     // Catch:{ all -> 0x0151 }
            r6 = 1
            if (r5 != 0) goto L_0x003c
            java.lang.String r2 = "DBMgr"
            java.lang.Object[] r4 = new java.lang.Object[r6]     // Catch:{ all -> 0x0151 }
            java.lang.String r5 = "can not get available db"
            r4[r3] = r5     // Catch:{ all -> 0x0151 }
            com.ta.audid.utils.UtdidLogger.w(r2, r4)     // Catch:{ all -> 0x0151 }
            monitor-exit(r19)
            return
        L_0x003c:
            java.lang.Object r7 = r2.get(r3)     // Catch:{ Throwable -> 0x0129 }
            com.ta.audid.db.Entity r7 = (com.ta.audid.db.Entity) r7     // Catch:{ Throwable -> 0x0129 }
            java.lang.Class r7 = r7.getClass()     // Catch:{ Throwable -> 0x0129 }
            java.util.List r7 = r1.getAllFields(r7)     // Catch:{ Throwable -> 0x0129 }
            android.content.ContentValues r8 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0129 }
            r8.<init>()     // Catch:{ Throwable -> 0x0129 }
            r5.beginTransaction()     // Catch:{ Throwable -> 0x0129 }
            r9 = 0
        L_0x0053:
            int r10 = r20.size()     // Catch:{ Throwable -> 0x0129 }
            if (r9 >= r10) goto L_0x0119
            java.lang.Object r10 = r2.get(r9)     // Catch:{ Throwable -> 0x0129 }
            com.ta.audid.db.Entity r10 = (com.ta.audid.db.Entity) r10     // Catch:{ Throwable -> 0x0129 }
            r11 = 0
        L_0x0060:
            int r12 = r7.size()     // Catch:{ Throwable -> 0x0129 }
            r13 = 2
            if (r11 >= r12) goto L_0x0097
            java.lang.Object r12 = r7.get(r11)     // Catch:{ Throwable -> 0x0129 }
            java.lang.reflect.Field r12 = (java.lang.reflect.Field) r12     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r14 = r1.getColumnName(r12)     // Catch:{ Throwable -> 0x0129 }
            java.lang.Object r12 = r12.get(r10)     // Catch:{ Exception -> 0x0085 }
            if (r12 == 0) goto L_0x007f
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ Exception -> 0x0085 }
            r8.put(r14, r12)     // Catch:{ Exception -> 0x0085 }
            goto L_0x0094
        L_0x007f:
            java.lang.String r12 = ""
            r8.put(r14, r12)     // Catch:{ Exception -> 0x0085 }
            goto L_0x0094
        L_0x0085:
            r0 = move-exception
            r12 = r0
            java.lang.String r14 = "DBMgr"
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r15 = "get field failed"
            r13[r3] = r15     // Catch:{ Throwable -> 0x0129 }
            r13[r6] = r12     // Catch:{ Throwable -> 0x0129 }
            com.ta.audid.utils.UtdidLogger.w(r14, r13)     // Catch:{ Throwable -> 0x0129 }
        L_0x0094:
            int r11 = r11 + 1
            goto L_0x0060
        L_0x0097:
            long r11 = r10._id     // Catch:{ Throwable -> 0x0129 }
            r14 = -1
            int r11 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r11 != 0) goto L_0x00ee
            java.lang.String r11 = "_id"
            r8.remove(r11)     // Catch:{ Throwable -> 0x0129 }
            r11 = 0
            long r11 = r5.insert(r4, r11, r8)     // Catch:{ Throwable -> 0x0129 }
            int r14 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            r16 = 4
            r17 = 3
            r15 = 6
            if (r14 == 0) goto L_0x00d1
            r10._id = r11     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r11 = "DBMgr"
            java.lang.Object[] r12 = new java.lang.Object[r15]     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r14 = "mDbName"
            r12[r3] = r14     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r14 = r1.mDbName     // Catch:{ Throwable -> 0x0129 }
            r12[r6] = r14     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r14 = "tablename"
            r12[r13] = r14     // Catch:{ Throwable -> 0x0129 }
            r12[r17] = r4     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r13 = "insert:success"
            r12[r16] = r13     // Catch:{ Throwable -> 0x0129 }
            r13 = 5
            r12[r13] = r10     // Catch:{ Throwable -> 0x0129 }
            com.ta.audid.utils.UtdidLogger.d(r11, r12)     // Catch:{ Throwable -> 0x0129 }
            goto L_0x0112
        L_0x00d1:
            java.lang.String r11 = "DBMgr"
            java.lang.Object[] r12 = new java.lang.Object[r15]     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r14 = "mDbName"
            r12[r3] = r14     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r14 = r1.mDbName     // Catch:{ Throwable -> 0x0129 }
            r12[r6] = r14     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r14 = "tablename"
            r12[r13] = r14     // Catch:{ Throwable -> 0x0129 }
            r12[r17] = r4     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r13 = "insert:error"
            r12[r16] = r13     // Catch:{ Throwable -> 0x0129 }
            r13 = 5
            r12[r13] = r10     // Catch:{ Throwable -> 0x0129 }
            com.ta.audid.utils.UtdidLogger.w(r11, r12)     // Catch:{ Throwable -> 0x0129 }
            goto L_0x0112
        L_0x00ee:
            java.lang.String r11 = "_id=?"
            java.lang.String[] r12 = new java.lang.String[r6]     // Catch:{ Throwable -> 0x0129 }
            long r13 = r10._id     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r10 = java.lang.String.valueOf(r13)     // Catch:{ Throwable -> 0x0129 }
            r12[r3] = r10     // Catch:{ Throwable -> 0x0129 }
            int r10 = r5.update(r4, r8, r11, r12)     // Catch:{ Throwable -> 0x0129 }
            long r10 = (long) r10     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r12 = "DBMgr"
            java.lang.Object[] r13 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r14 = "db update :"
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r10 = r14.concat(r10)     // Catch:{ Throwable -> 0x0129 }
            r13[r3] = r10     // Catch:{ Throwable -> 0x0129 }
            com.ta.audid.utils.UtdidLogger.w(r12, r13)     // Catch:{ Throwable -> 0x0129 }
        L_0x0112:
            r8.clear()     // Catch:{ Throwable -> 0x0129 }
            int r9 = r9 + 1
            goto L_0x0053
        L_0x0119:
            r5.setTransactionSuccessful()     // Catch:{ Exception -> 0x011c }
        L_0x011c:
            r5.endTransaction()     // Catch:{ Exception -> 0x011f }
        L_0x011f:
            com.ta.audid.db.SqliteHelper r2 = r1.mHelper     // Catch:{ all -> 0x0151 }
            r2.closeWritableDatabase(r5)     // Catch:{ all -> 0x0151 }
            monitor-exit(r19)
            return
        L_0x0126:
            r0 = move-exception
            r2 = r0
            goto L_0x0145
        L_0x0129:
            r0 = move-exception
            r2 = r0
            java.lang.String r4 = "DBMgr"
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x0126 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0126 }
            r6[r3] = r2     // Catch:{ all -> 0x0126 }
            com.ta.audid.utils.UtdidLogger.d(r4, r6)     // Catch:{ all -> 0x0126 }
            r5.setTransactionSuccessful()     // Catch:{ Exception -> 0x013b }
        L_0x013b:
            r5.endTransaction()     // Catch:{ Exception -> 0x013e }
        L_0x013e:
            com.ta.audid.db.SqliteHelper r2 = r1.mHelper     // Catch:{ all -> 0x0151 }
            r2.closeWritableDatabase(r5)     // Catch:{ all -> 0x0151 }
            monitor-exit(r19)
            return
        L_0x0145:
            r5.setTransactionSuccessful()     // Catch:{ Exception -> 0x0148 }
        L_0x0148:
            r5.endTransaction()     // Catch:{ Exception -> 0x014b }
        L_0x014b:
            com.ta.audid.db.SqliteHelper r3 = r1.mHelper     // Catch:{ all -> 0x0151 }
            r3.closeWritableDatabase(r5)     // Catch:{ all -> 0x0151 }
            throw r2     // Catch:{ all -> 0x0151 }
        L_0x0151:
            r0 = move-exception
            r2 = r0
            monitor-exit(r19)
            throw r2
        L_0x0155:
            monitor-exit(r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.DBMgr.insert(java.util.List):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:13|14|(4:17|(2:19|56)(2:20|55)|21|15)|22|23|24|25|26|27) */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0104, code lost:
        return 0;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x00cc */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x00cf */
    /* JADX WARNING: Missing exception handler attribute for start block: B:36:0x00e8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:38:0x00eb */
    /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x00f7 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x00fa */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:47:0x00fa=Splitter:B:47:0x00fa, B:38:0x00eb=Splitter:B:38:0x00eb, B:26:0x00cf=Splitter:B:26:0x00cf} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int delete(java.util.List<? extends com.ta.audid.db.Entity> r13) {
        /*
            r12 = this;
            monitor-enter(r12)
            r0 = 0
            if (r13 == 0) goto L_0x0103
            int r1 = r13.size()     // Catch:{ all -> 0x0100 }
            if (r1 != 0) goto L_0x000c
            goto L_0x0103
        L_0x000c:
            java.lang.Object r1 = r13.get(r0)     // Catch:{ all -> 0x0100 }
            com.ta.audid.db.Entity r1 = (com.ta.audid.db.Entity) r1     // Catch:{ all -> 0x0100 }
            java.lang.Class r1 = r1.getClass()     // Catch:{ all -> 0x0100 }
            java.lang.String r1 = r12.getTablename(r1)     // Catch:{ all -> 0x0100 }
            java.lang.Object r2 = r13.get(r0)     // Catch:{ all -> 0x0100 }
            com.ta.audid.db.Entity r2 = (com.ta.audid.db.Entity) r2     // Catch:{ all -> 0x0100 }
            java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x0100 }
            android.database.sqlite.SQLiteDatabase r2 = r12.checkTableAvailable(r2, r1)     // Catch:{ all -> 0x0100 }
            r3 = 1
            if (r2 != 0) goto L_0x0038
            java.lang.String r13 = "DBMgr"
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x0100 }
            java.lang.String r2 = "db is null"
            r1[r0] = r2     // Catch:{ all -> 0x0100 }
            com.ta.audid.utils.UtdidLogger.d(r13, r1)     // Catch:{ all -> 0x0100 }
            monitor-exit(r12)
            return r0
        L_0x0038:
            r4 = 2
            r2.beginTransaction()     // Catch:{ Throwable -> 0x00d7 }
            r5 = 0
        L_0x003d:
            int r6 = r13.size()     // Catch:{ Throwable -> 0x00d7 }
            if (r5 >= r6) goto L_0x00c9
            java.lang.String r6 = "_id=?"
            java.lang.String[] r7 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x00d7 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d7 }
            r8.<init>()     // Catch:{ Throwable -> 0x00d7 }
            java.lang.Object r9 = r13.get(r5)     // Catch:{ Throwable -> 0x00d7 }
            com.ta.audid.db.Entity r9 = (com.ta.audid.db.Entity) r9     // Catch:{ Throwable -> 0x00d7 }
            long r9 = r9._id     // Catch:{ Throwable -> 0x00d7 }
            r8.append(r9)     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x00d7 }
            r7[r0] = r8     // Catch:{ Throwable -> 0x00d7 }
            int r6 = r2.delete(r1, r6, r7)     // Catch:{ Throwable -> 0x00d7 }
            long r6 = (long) r6     // Catch:{ Throwable -> 0x00d7 }
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            r7 = 5
            r8 = 4
            r9 = 3
            r10 = 6
            if (r6 > 0) goto L_0x0094
            java.lang.String r6 = "DBMgr"
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r11 = "db"
            r10[r0] = r11     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r11 = r12.mDbName     // Catch:{ Throwable -> 0x00d7 }
            r10[r3] = r11     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r11 = "tableName"
            r10[r4] = r11     // Catch:{ Throwable -> 0x00d7 }
            r10[r9] = r1     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r9 = " delete failed _id"
            r10[r8] = r9     // Catch:{ Throwable -> 0x00d7 }
            java.lang.Object r8 = r13.get(r5)     // Catch:{ Throwable -> 0x00d7 }
            com.ta.audid.db.Entity r8 = (com.ta.audid.db.Entity) r8     // Catch:{ Throwable -> 0x00d7 }
            long r8 = r8._id     // Catch:{ Throwable -> 0x00d7 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ Throwable -> 0x00d7 }
            r10[r7] = r8     // Catch:{ Throwable -> 0x00d7 }
            com.ta.audid.utils.UtdidLogger.w(r6, r10)     // Catch:{ Throwable -> 0x00d7 }
            goto L_0x00c5
        L_0x0094:
            java.lang.String r6 = "DBMgr"
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r11 = "db "
            r10[r0] = r11     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r11 = r12.mDbName     // Catch:{ Throwable -> 0x00d7 }
            r10[r3] = r11     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r11 = "tableName"
            r10[r4] = r11     // Catch:{ Throwable -> 0x00d7 }
            r10[r9] = r1     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r9 = "delete success _id"
            r10[r8] = r9     // Catch:{ Throwable -> 0x00d7 }
            java.lang.Object r8 = r13.get(r5)     // Catch:{ Throwable -> 0x00d7 }
            com.ta.audid.db.Entity r8 = (com.ta.audid.db.Entity) r8     // Catch:{ Throwable -> 0x00d7 }
            long r8 = r8._id     // Catch:{ Throwable -> 0x00d7 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ Throwable -> 0x00d7 }
            r10[r7] = r8     // Catch:{ Throwable -> 0x00d7 }
            com.ta.audid.utils.UtdidLogger.d(r6, r10)     // Catch:{ Throwable -> 0x00d7 }
            java.lang.Object r6 = r13.get(r5)     // Catch:{ Throwable -> 0x00d7 }
            com.ta.audid.db.Entity r6 = (com.ta.audid.db.Entity) r6     // Catch:{ Throwable -> 0x00d7 }
            r7 = -1
            r6._id = r7     // Catch:{ Throwable -> 0x00d7 }
        L_0x00c5:
            int r5 = r5 + 1
            goto L_0x003d
        L_0x00c9:
            r2.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00cc }
        L_0x00cc:
            r2.endTransaction()     // Catch:{ Throwable -> 0x00cf }
        L_0x00cf:
            com.ta.audid.db.SqliteHelper r0 = r12.mHelper     // Catch:{ all -> 0x0100 }
        L_0x00d1:
            r0.closeWritableDatabase(r2)     // Catch:{ all -> 0x0100 }
            goto L_0x00ee
        L_0x00d5:
            r13 = move-exception
            goto L_0x00f4
        L_0x00d7:
            r1 = move-exception
            java.lang.String r5 = "DBMgr"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00d5 }
            java.lang.String r6 = "db delete error:"
            r4[r0] = r6     // Catch:{ all -> 0x00d5 }
            r4[r3] = r1     // Catch:{ all -> 0x00d5 }
            com.ta.audid.utils.UtdidLogger.w(r5, r4)     // Catch:{ all -> 0x00d5 }
            r2.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00e8 }
        L_0x00e8:
            r2.endTransaction()     // Catch:{ Throwable -> 0x00eb }
        L_0x00eb:
            com.ta.audid.db.SqliteHelper r0 = r12.mHelper     // Catch:{ all -> 0x0100 }
            goto L_0x00d1
        L_0x00ee:
            int r13 = r13.size()     // Catch:{ all -> 0x0100 }
            monitor-exit(r12)
            return r13
        L_0x00f4:
            r2.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00f7 }
        L_0x00f7:
            r2.endTransaction()     // Catch:{ Throwable -> 0x00fa }
        L_0x00fa:
            com.ta.audid.db.SqliteHelper r0 = r12.mHelper     // Catch:{ all -> 0x0100 }
            r0.closeWritableDatabase(r2)     // Catch:{ all -> 0x0100 }
            throw r13     // Catch:{ all -> 0x0100 }
        L_0x0100:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        L_0x0103:
            monitor-exit(r12)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.DBMgr.delete(java.util.List):int");
    }

    public int delete(Entity entity) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(entity);
        return delete((List<? extends Entity>) arrayList);
    }

    public void update(Entity entity) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(entity);
        update((List<? extends Entity>) arrayList);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d8, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        r2.setTransactionSuccessful();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00dd, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        com.ta.audid.utils.UtdidLogger.w(TAG, "setTransactionSuccessful", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r2.setTransactionSuccessful();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0107, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        com.ta.audid.utils.UtdidLogger.w(TAG, "setTransactionSuccessful", r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        r2.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0119, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        com.ta.audid.utils.UtdidLogger.w(TAG, "endTransaction", r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0127, code lost:
        r13.mHelper.closeWritableDatabase(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x012d, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0132, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x0103 */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00d8 A[ExcHandler: all (r14v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:12:0x0039] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void update(java.util.List<? extends com.ta.audid.db.Entity> r14) {
        /*
            r13 = this;
            monitor-enter(r13)
            if (r14 == 0) goto L_0x0131
            int r0 = r14.size()     // Catch:{ all -> 0x012e }
            if (r0 != 0) goto L_0x000b
            goto L_0x0131
        L_0x000b:
            r0 = 0
            java.lang.Object r1 = r14.get(r0)     // Catch:{ all -> 0x012e }
            com.ta.audid.db.Entity r1 = (com.ta.audid.db.Entity) r1     // Catch:{ all -> 0x012e }
            java.lang.Class r1 = r1.getClass()     // Catch:{ all -> 0x012e }
            java.lang.String r1 = r13.getTablename(r1)     // Catch:{ all -> 0x012e }
            java.lang.Object r2 = r14.get(r0)     // Catch:{ all -> 0x012e }
            com.ta.audid.db.Entity r2 = (com.ta.audid.db.Entity) r2     // Catch:{ all -> 0x012e }
            java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x012e }
            android.database.sqlite.SQLiteDatabase r2 = r13.checkTableAvailable(r2, r1)     // Catch:{ all -> 0x012e }
            r3 = 1
            if (r2 != 0) goto L_0x0038
            java.lang.String r14 = "DBMgr"
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x012e }
            java.lang.String r2 = "[update] db is null"
            r1[r0] = r2     // Catch:{ all -> 0x012e }
            com.ta.audid.utils.UtdidLogger.w(r14, r1)     // Catch:{ all -> 0x012e }
            monitor-exit(r13)
            return
        L_0x0038:
            r4 = 2
            r2.beginTransaction()     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            java.lang.Object r5 = r14.get(r0)     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            com.ta.audid.db.Entity r5 = (com.ta.audid.db.Entity) r5     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            java.lang.Class r5 = r5.getClass()     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            java.util.List r5 = r13.getAllFields(r5)     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            r6 = 0
        L_0x004b:
            int r7 = r14.size()     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            if (r6 >= r7) goto L_0x00ad
            android.content.ContentValues r7 = new android.content.ContentValues     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            r7.<init>()     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            r8 = 0
        L_0x0057:
            int r9 = r5.size()     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            if (r8 >= r9) goto L_0x008d
            java.lang.Object r9 = r5.get(r8)     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            java.lang.reflect.Field r9 = (java.lang.reflect.Field) r9     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            r9.setAccessible(r3)     // Catch:{ Exception -> 0x0082, all -> 0x00d8 }
            java.lang.String r10 = r13.getColumnName(r9)     // Catch:{ Exception -> 0x0082, all -> 0x00d8 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0082, all -> 0x00d8 }
            r11.<init>()     // Catch:{ Exception -> 0x0082, all -> 0x00d8 }
            java.lang.Object r12 = r14.get(r6)     // Catch:{ Exception -> 0x0082, all -> 0x00d8 }
            java.lang.Object r9 = r9.get(r12)     // Catch:{ Exception -> 0x0082, all -> 0x00d8 }
            r11.append(r9)     // Catch:{ Exception -> 0x0082, all -> 0x00d8 }
            java.lang.String r9 = r11.toString()     // Catch:{ Exception -> 0x0082, all -> 0x00d8 }
            r7.put(r10, r9)     // Catch:{ Exception -> 0x0082, all -> 0x00d8 }
            goto L_0x008a
        L_0x0082:
            r9 = move-exception
            java.lang.String r10 = ""
            java.lang.Object[] r11 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            com.ta.audid.utils.UtdidLogger.e(r10, r9, r11)     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
        L_0x008a:
            int r8 = r8 + 1
            goto L_0x0057
        L_0x008d:
            java.lang.String r8 = "_id=?"
            java.lang.String[] r9 = new java.lang.String[r3]     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            r10.<init>()     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            java.lang.Object r11 = r14.get(r6)     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            com.ta.audid.db.Entity r11 = (com.ta.audid.db.Entity) r11     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            long r11 = r11._id     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            r10.append(r11)     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            r9[r0] = r10     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            r2.update(r1, r7, r8, r9)     // Catch:{ Exception -> 0x0103, all -> 0x00d8 }
            int r6 = r6 + 1
            goto L_0x004b
        L_0x00ad:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00b1 }
            goto L_0x00bf
        L_0x00b1:
            r14 = move-exception
            java.lang.String r1 = "DBMgr"
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x012e }
            java.lang.String r6 = "setTransactionSuccessful"
            r5[r0] = r6     // Catch:{ all -> 0x012e }
            r5[r3] = r14     // Catch:{ all -> 0x012e }
            com.ta.audid.utils.UtdidLogger.w(r1, r5)     // Catch:{ all -> 0x012e }
        L_0x00bf:
            r2.endTransaction()     // Catch:{ Exception -> 0x00c3 }
            goto L_0x00d1
        L_0x00c3:
            r14 = move-exception
            java.lang.String r1 = "DBMgr"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x012e }
            java.lang.String r5 = "endTransaction"
            r4[r0] = r5     // Catch:{ all -> 0x012e }
            r4[r3] = r14     // Catch:{ all -> 0x012e }
            com.ta.audid.utils.UtdidLogger.w(r1, r4)     // Catch:{ all -> 0x012e }
        L_0x00d1:
            com.ta.audid.db.SqliteHelper r14 = r13.mHelper     // Catch:{ all -> 0x012e }
            r14.closeWritableDatabase(r2)     // Catch:{ all -> 0x012e }
            monitor-exit(r13)
            return
        L_0x00d8:
            r14 = move-exception
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00dd }
            goto L_0x00eb
        L_0x00dd:
            r1 = move-exception
            java.lang.String r5 = "DBMgr"
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ all -> 0x012e }
            java.lang.String r7 = "setTransactionSuccessful"
            r6[r0] = r7     // Catch:{ all -> 0x012e }
            r6[r3] = r1     // Catch:{ all -> 0x012e }
            com.ta.audid.utils.UtdidLogger.w(r5, r6)     // Catch:{ all -> 0x012e }
        L_0x00eb:
            r2.endTransaction()     // Catch:{ Exception -> 0x00ef }
            goto L_0x00fd
        L_0x00ef:
            r1 = move-exception
            java.lang.String r5 = "DBMgr"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x012e }
            java.lang.String r6 = "endTransaction"
            r4[r0] = r6     // Catch:{ all -> 0x012e }
            r4[r3] = r1     // Catch:{ all -> 0x012e }
            com.ta.audid.utils.UtdidLogger.w(r5, r4)     // Catch:{ all -> 0x012e }
        L_0x00fd:
            com.ta.audid.db.SqliteHelper r0 = r13.mHelper     // Catch:{ all -> 0x012e }
            r0.closeWritableDatabase(r2)     // Catch:{ all -> 0x012e }
            throw r14     // Catch:{ all -> 0x012e }
        L_0x0103:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x0107 }
            goto L_0x0115
        L_0x0107:
            r14 = move-exception
            java.lang.String r1 = "DBMgr"
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x012e }
            java.lang.String r6 = "setTransactionSuccessful"
            r5[r0] = r6     // Catch:{ all -> 0x012e }
            r5[r3] = r14     // Catch:{ all -> 0x012e }
            com.ta.audid.utils.UtdidLogger.w(r1, r5)     // Catch:{ all -> 0x012e }
        L_0x0115:
            r2.endTransaction()     // Catch:{ Exception -> 0x0119 }
            goto L_0x0127
        L_0x0119:
            r14 = move-exception
            java.lang.String r1 = "DBMgr"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x012e }
            java.lang.String r5 = "endTransaction"
            r4[r0] = r5     // Catch:{ all -> 0x012e }
            r4[r3] = r14     // Catch:{ all -> 0x012e }
            com.ta.audid.utils.UtdidLogger.w(r1, r4)     // Catch:{ all -> 0x012e }
        L_0x0127:
            com.ta.audid.db.SqliteHelper r14 = r13.mHelper     // Catch:{ all -> 0x012e }
            r14.closeWritableDatabase(r2)     // Catch:{ all -> 0x012e }
            monitor-exit(r13)
            return
        L_0x012e:
            r14 = move-exception
            monitor-exit(r13)
            throw r14
        L_0x0131:
            monitor-exit(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.DBMgr.update(java.util.List):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e2, code lost:
        r15 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        r2.setTransactionSuccessful();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e7, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        com.ta.audid.utils.UtdidLogger.w(TAG, "setTransactionSuccessful", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x013c, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:61:0x010d */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A[ExcHandler: all (r15v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:12:0x0039] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void updateLogPriority(java.util.List<? extends com.ta.audid.db.Entity> r15) {
        /*
            r14 = this;
            monitor-enter(r14)
            if (r15 == 0) goto L_0x013b
            int r0 = r15.size()     // Catch:{ all -> 0x0138 }
            if (r0 != 0) goto L_0x000b
            goto L_0x013b
        L_0x000b:
            r0 = 0
            java.lang.Object r1 = r15.get(r0)     // Catch:{ all -> 0x0138 }
            com.ta.audid.db.Entity r1 = (com.ta.audid.db.Entity) r1     // Catch:{ all -> 0x0138 }
            java.lang.Class r1 = r1.getClass()     // Catch:{ all -> 0x0138 }
            java.lang.String r1 = r14.getTablename(r1)     // Catch:{ all -> 0x0138 }
            java.lang.Object r2 = r15.get(r0)     // Catch:{ all -> 0x0138 }
            com.ta.audid.db.Entity r2 = (com.ta.audid.db.Entity) r2     // Catch:{ all -> 0x0138 }
            java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x0138 }
            android.database.sqlite.SQLiteDatabase r2 = r14.checkTableAvailable(r2, r1)     // Catch:{ all -> 0x0138 }
            r3 = 1
            if (r2 != 0) goto L_0x0038
            java.lang.String r15 = "DBMgr"
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x0138 }
            java.lang.String r2 = "[update] db is null"
            r1[r0] = r2     // Catch:{ all -> 0x0138 }
            com.ta.audid.utils.UtdidLogger.w(r15, r1)     // Catch:{ all -> 0x0138 }
            monitor-exit(r14)
            return
        L_0x0038:
            r4 = 2
            r2.beginTransaction()     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            java.lang.Object r5 = r15.get(r0)     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            com.ta.audid.db.Entity r5 = (com.ta.audid.db.Entity) r5     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            java.lang.Class r5 = r5.getClass()     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            java.util.List r5 = r14.getAllFields(r5)     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            r6 = 0
        L_0x004b:
            int r7 = r15.size()     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            if (r6 >= r7) goto L_0x00b7
            android.content.ContentValues r7 = new android.content.ContentValues     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            r7.<init>()     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            r8 = 0
        L_0x0057:
            int r9 = r5.size()     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            if (r8 >= r9) goto L_0x00b4
            java.lang.Object r9 = r5.get(r8)     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            java.lang.reflect.Field r9 = (java.lang.reflect.Field) r9     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            java.lang.String r10 = r14.getColumnName(r9)     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            if (r10 == 0) goto L_0x00b1
            java.lang.String r11 = "priority"
            boolean r11 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            if (r11 == 0) goto L_0x00b1
            r9.setAccessible(r3)     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            r11.<init>()     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            java.lang.Object r12 = r15.get(r6)     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            java.lang.Object r9 = r9.get(r12)     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            r11.append(r9)     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            java.lang.String r9 = r11.toString()     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            r7.put(r10, r9)     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            java.lang.String r9 = "_id=?"
            java.lang.String[] r10 = new java.lang.String[r3]     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            r11.<init>()     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            java.lang.Object r12 = r15.get(r6)     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            com.ta.audid.db.Entity r12 = (com.ta.audid.db.Entity) r12     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            long r12 = r12._id     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            r11.append(r12)     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            r10[r0] = r11     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            r2.update(r1, r7, r9, r10)     // Catch:{ Exception -> 0x00a9, all -> 0x00e2 }
            goto L_0x00b4
        L_0x00a9:
            r9 = move-exception
            java.lang.String r10 = ""
            java.lang.Object[] r11 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
            com.ta.audid.utils.UtdidLogger.e(r10, r9, r11)     // Catch:{ Exception -> 0x010d, all -> 0x00e2 }
        L_0x00b1:
            int r8 = r8 + 1
            goto L_0x0057
        L_0x00b4:
            int r6 = r6 + 1
            goto L_0x004b
        L_0x00b7:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00bb }
            goto L_0x00c9
        L_0x00bb:
            r15 = move-exception
            java.lang.String r1 = "DBMgr"
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0138 }
            java.lang.String r6 = "setTransactionSuccessful"
            r5[r0] = r6     // Catch:{ all -> 0x0138 }
            r5[r3] = r15     // Catch:{ all -> 0x0138 }
            com.ta.audid.utils.UtdidLogger.w(r1, r5)     // Catch:{ all -> 0x0138 }
        L_0x00c9:
            r2.endTransaction()     // Catch:{ Exception -> 0x00cd }
            goto L_0x00db
        L_0x00cd:
            r15 = move-exception
            java.lang.String r1 = "DBMgr"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0138 }
            java.lang.String r5 = "endTransaction"
            r4[r0] = r5     // Catch:{ all -> 0x0138 }
            r4[r3] = r15     // Catch:{ all -> 0x0138 }
            com.ta.audid.utils.UtdidLogger.w(r1, r4)     // Catch:{ all -> 0x0138 }
        L_0x00db:
            com.ta.audid.db.SqliteHelper r15 = r14.mHelper     // Catch:{ all -> 0x0138 }
            r15.closeWritableDatabase(r2)     // Catch:{ all -> 0x0138 }
            monitor-exit(r14)
            return
        L_0x00e2:
            r15 = move-exception
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00e7 }
            goto L_0x00f5
        L_0x00e7:
            r1 = move-exception
            java.lang.String r5 = "DBMgr"
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ all -> 0x0138 }
            java.lang.String r7 = "setTransactionSuccessful"
            r6[r0] = r7     // Catch:{ all -> 0x0138 }
            r6[r3] = r1     // Catch:{ all -> 0x0138 }
            com.ta.audid.utils.UtdidLogger.w(r5, r6)     // Catch:{ all -> 0x0138 }
        L_0x00f5:
            r2.endTransaction()     // Catch:{ Exception -> 0x00f9 }
            goto L_0x0107
        L_0x00f9:
            r1 = move-exception
            java.lang.String r5 = "DBMgr"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0138 }
            java.lang.String r6 = "endTransaction"
            r4[r0] = r6     // Catch:{ all -> 0x0138 }
            r4[r3] = r1     // Catch:{ all -> 0x0138 }
            com.ta.audid.utils.UtdidLogger.w(r5, r4)     // Catch:{ all -> 0x0138 }
        L_0x0107:
            com.ta.audid.db.SqliteHelper r0 = r14.mHelper     // Catch:{ all -> 0x0138 }
            r0.closeWritableDatabase(r2)     // Catch:{ all -> 0x0138 }
            throw r15     // Catch:{ all -> 0x0138 }
        L_0x010d:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x0111 }
            goto L_0x011f
        L_0x0111:
            r15 = move-exception
            java.lang.String r1 = "DBMgr"
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0138 }
            java.lang.String r6 = "setTransactionSuccessful"
            r5[r0] = r6     // Catch:{ all -> 0x0138 }
            r5[r3] = r15     // Catch:{ all -> 0x0138 }
            com.ta.audid.utils.UtdidLogger.w(r1, r5)     // Catch:{ all -> 0x0138 }
        L_0x011f:
            r2.endTransaction()     // Catch:{ Exception -> 0x0123 }
            goto L_0x0131
        L_0x0123:
            r15 = move-exception
            java.lang.String r1 = "DBMgr"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0138 }
            java.lang.String r5 = "endTransaction"
            r4[r0] = r5     // Catch:{ all -> 0x0138 }
            r4[r3] = r15     // Catch:{ all -> 0x0138 }
            com.ta.audid.utils.UtdidLogger.w(r1, r4)     // Catch:{ all -> 0x0138 }
        L_0x0131:
            com.ta.audid.db.SqliteHelper r15 = r14.mHelper     // Catch:{ all -> 0x0138 }
            r15.closeWritableDatabase(r2)     // Catch:{ all -> 0x0138 }
            monitor-exit(r14)
            return
        L_0x0138:
            r15 = move-exception
            monitor-exit(r14)
            throw r15
        L_0x013b:
            monitor-exit(r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.DBMgr.updateLogPriority(java.util.List):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x003b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void execSQL(java.lang.Class<? extends com.ta.audid.db.Entity> r3, java.lang.String r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 == 0) goto L_0x003a
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0037 }
            if (r0 != 0) goto L_0x003a
            java.lang.String r0 = r2.getTablename(r3)     // Catch:{ all -> 0x0037 }
            android.database.sqlite.SQLiteDatabase r3 = r2.checkTableAvailable(r3, r0)     // Catch:{ all -> 0x0037 }
            if (r3 != 0) goto L_0x0015
            monitor-exit(r2)
            return
        L_0x0015:
            r3.execSQL(r4)     // Catch:{ Throwable -> 0x0021 }
            com.ta.audid.db.SqliteHelper r4 = r2.mHelper     // Catch:{ all -> 0x0037 }
            r4.closeWritableDatabase(r3)     // Catch:{ all -> 0x0037 }
            monitor-exit(r2)
            return
        L_0x001f:
            r4 = move-exception
            goto L_0x0031
        L_0x0021:
            r4 = move-exception
            java.lang.String r0 = "DBMgr"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x001f }
            com.ta.audid.utils.UtdidLogger.w(r0, r4, r1)     // Catch:{ all -> 0x001f }
            com.ta.audid.db.SqliteHelper r4 = r2.mHelper     // Catch:{ all -> 0x0037 }
            r4.closeWritableDatabase(r3)     // Catch:{ all -> 0x0037 }
            monitor-exit(r2)
            return
        L_0x0031:
            com.ta.audid.db.SqliteHelper r0 = r2.mHelper     // Catch:{ all -> 0x0037 }
            r0.closeWritableDatabase(r3)     // Catch:{ all -> 0x0037 }
            throw r4     // Catch:{ all -> 0x0037 }
        L_0x0037:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        L_0x003a:
            monitor-exit(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.DBMgr.execSQL(java.lang.Class, java.lang.String):void");
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0053, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int delete(java.lang.Class<? extends com.ta.audid.db.Entity> r6, java.lang.String r7, java.lang.String[] r8) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            r1 = 5
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "whereArgs"
            r3 = 0
            r1[r3] = r2     // Catch:{ all -> 0x0054 }
            r2 = 1
            r1[r2] = r8     // Catch:{ all -> 0x0054 }
            r2 = 2
            java.lang.String r4 = ""
            r1[r2] = r4     // Catch:{ all -> 0x0054 }
            r2 = 3
            java.lang.String r4 = "whereArgs"
            r1[r2] = r4     // Catch:{ all -> 0x0054 }
            r2 = 4
            r1[r2] = r8     // Catch:{ all -> 0x0054 }
            com.ta.audid.utils.UtdidLogger.d(r0, r1)     // Catch:{ all -> 0x0054 }
            if (r6 == 0) goto L_0x0051
            java.lang.String r0 = r5.getTablename(r6)     // Catch:{ all -> 0x0054 }
            android.database.sqlite.SQLiteDatabase r0 = r5.checkTableAvailable(r6, r0)     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x002d
            monitor-exit(r5)
            return r3
        L_0x002d:
            java.lang.String r6 = r5.getTablename(r6)     // Catch:{ Throwable -> 0x003d }
            int r6 = r0.delete(r6, r7, r8)     // Catch:{ Throwable -> 0x003d }
            com.ta.audid.db.SqliteHelper r7 = r5.mHelper     // Catch:{ all -> 0x0054 }
            r7.closeWritableDatabase(r0)     // Catch:{ all -> 0x0054 }
            goto L_0x0052
        L_0x003b:
            r6 = move-exception
            goto L_0x004b
        L_0x003d:
            r6 = move-exception
            java.lang.String r7 = "DBMgr"
            java.lang.Object[] r8 = new java.lang.Object[r3]     // Catch:{ all -> 0x003b }
            com.ta.audid.utils.UtdidLogger.w(r7, r6, r8)     // Catch:{ all -> 0x003b }
            com.ta.audid.db.SqliteHelper r6 = r5.mHelper     // Catch:{ all -> 0x0054 }
            r6.closeWritableDatabase(r0)     // Catch:{ all -> 0x0054 }
            goto L_0x0051
        L_0x004b:
            com.ta.audid.db.SqliteHelper r7 = r5.mHelper     // Catch:{ all -> 0x0054 }
            r7.closeWritableDatabase(r0)     // Catch:{ all -> 0x0054 }
            throw r6     // Catch:{ all -> 0x0054 }
        L_0x0051:
            r6 = 0
        L_0x0052:
            monitor-exit(r5)
            return r6
        L_0x0054:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.DBMgr.delete(java.lang.Class, java.lang.String, java.lang.String[]):int");
    }

    public String getTablename(Class<?> cls) {
        String str;
        if (cls == null) {
            return null;
        }
        if (this.mClsTableNameMap.containsKey(cls)) {
            return this.mClsTableNameMap.get(cls);
        }
        TableName tableName = (TableName) cls.getAnnotation(TableName.class);
        if (tableName == null || TextUtils.isEmpty(tableName.value())) {
            str = cls.getName().replace(".", "_");
        } else {
            str = tableName.value();
        }
        this.mClsTableNameMap.put(cls, str);
        return str;
    }

    private SQLiteDatabase checkTableAvailable(Class<? extends Entity> cls, String str) {
        Cursor cursor;
        SQLiteDatabase writableDatabase = this.mHelper.getWritableDatabase();
        Boolean bool = Boolean.TRUE;
        if (this.mCheckdbMap.get(str) == null || !this.mCheckdbMap.get(str).booleanValue()) {
            bool = Boolean.FALSE;
        }
        if (cls != null && !bool.booleanValue()) {
            List<Field> allFields = getAllFields(cls);
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder(" SELECT * FROM ");
            sb.append(str);
            sb.append(" LIMIT 0");
            String sb2 = sb.toString();
            int i = 0;
            if (allFields != null) {
                int i2 = 1;
                try {
                    cursor = writableDatabase.rawQuery(sb2, null);
                } catch (Exception unused) {
                    UtdidLogger.d((String) TAG, "has not create talbe:", str);
                    cursor = null;
                }
                if (cursor != null) {
                    i2 = 0;
                }
                while (i < allFields.size()) {
                    Field field = allFields.get(i);
                    if (!"_id".equalsIgnoreCase(getColumnName(field)) && (i2 != 0 || (cursor != null && cursor.getColumnIndex(getColumnName(field)) == -1))) {
                        arrayList.add(field);
                    }
                    i++;
                }
                this.mHelper.closeCursor(cursor);
                i = i2;
            }
            if (i != 0) {
                createTable(writableDatabase, str, arrayList);
            } else if (arrayList.size() > 0) {
                updateTable(writableDatabase, str, arrayList);
            }
            this.mCheckdbMap.put(str, Boolean.TRUE);
        }
        return writableDatabase;
    }

    private void updateTable(SQLiteDatabase sQLiteDatabase, String str, ArrayList<Field> arrayList) {
        StringBuilder sb = new StringBuilder("ALTER TABLE ");
        sb.append(str);
        sb.append(" ADD COLUMN ");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++) {
            sb3.append(sb2);
            sb3.append(getColumnName(arrayList.get(i)));
            sb3.append(Token.SEPARATOR);
            sb3.append(getSQLType(arrayList.get(i).getType()));
            String sb4 = sb3.toString();
            try {
                sQLiteDatabase.execSQL(sb4);
            } catch (Exception e) {
                UtdidLogger.w(TAG, "update db error...", e);
            }
            sb3.delete(0, sb4.length());
            UtdidLogger.d((String) TAG, null, "excute sql:", sb4);
        }
    }

    private void createTable(SQLiteDatabase sQLiteDatabase, String str, ArrayList<Field> arrayList) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb2.append(str);
        sb2.append(" (_id INTEGER PRIMARY KEY AUTOINCREMENT ,");
        sb.append(sb2.toString());
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                Class<?> type = arrayList.get(i).getType();
                sb.append(Token.SEPARATOR);
                sb.append(getColumnName(arrayList.get(i)));
                sb.append(Token.SEPARATOR);
                sb.append(getSQLType(type));
                sb.append(Token.SEPARATOR);
                sb.append(getDefaultValue(type));
            }
        }
        sb.append(" );");
        String sb3 = sb.toString();
        UtdidLogger.d((String) TAG, "excute sql:", sb3);
        try {
            sQLiteDatabase.execSQL(sb3);
        } catch (Exception e) {
            UtdidLogger.w(TAG, "create db error", e);
        }
    }

    private String getSQLType(Class<?> cls) {
        return (cls == Long.TYPE || cls == Integer.TYPE || cls == Long.class) ? "INTEGER" : "TEXT";
    }

    public synchronized int count(Class<? extends Entity> cls) {
        Cursor cursor;
        SqliteHelper sqliteHelper;
        int i = 0;
        if (cls == null) {
            return 0;
        }
        String tablename = getTablename(cls);
        SQLiteDatabase checkTableAvailable = checkTableAvailable(cls, tablename);
        if (checkTableAvailable != null) {
            Cursor cursor2 = null;
            try {
                cursor = checkTableAvailable.rawQuery("SELECT count(*) FROM ".concat(String.valueOf(tablename)), null);
                if (cursor != null) {
                    try {
                        cursor.moveToFirst();
                        i = cursor.getInt(0);
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = cursor;
                        this.mHelper.closeCursor(cursor2);
                        this.mHelper.closeWritableDatabase(checkTableAvailable);
                        throw th;
                    }
                }
                this.mHelper.closeCursor(cursor);
                sqliteHelper = this.mHelper;
            } catch (Throwable th2) {
                th = th2;
                this.mHelper.closeCursor(cursor2);
                this.mHelper.closeWritableDatabase(checkTableAvailable);
                throw th;
            }
            sqliteHelper.closeWritableDatabase(checkTableAvailable);
        } else {
            UtdidLogger.d((String) TAG, "db is null");
        }
    }

    public synchronized int count(Class<? extends Entity> cls, String str) {
        SqliteHelper sqliteHelper;
        int i = 0;
        if (cls == null) {
            return 0;
        }
        String tablename = getTablename(cls);
        SQLiteDatabase checkTableAvailable = checkTableAvailable(cls, tablename);
        if (checkTableAvailable != null) {
            Cursor cursor = null;
            try {
                StringBuilder sb = new StringBuilder("SELECT count(*) FROM ");
                sb.append(tablename);
                sb.append(TextUtils.isEmpty(str) ? "" : " WHERE ".concat(String.valueOf(str)));
                Cursor rawQuery = checkTableAvailable.rawQuery(sb.toString(), null);
                if (rawQuery != null) {
                    try {
                        rawQuery.moveToFirst();
                        i = rawQuery.getInt(0);
                    } catch (Throwable th) {
                        cursor = rawQuery;
                        th = th;
                        this.mHelper.closeCursor(cursor);
                        this.mHelper.closeWritableDatabase(checkTableAvailable);
                        throw th;
                    }
                }
                this.mHelper.closeCursor(rawQuery);
                sqliteHelper = this.mHelper;
            } catch (Throwable th2) {
                th = th2;
                UtdidLogger.d((String) TAG, th.toString());
                this.mHelper.closeCursor(cursor);
                sqliteHelper = this.mHelper;
                sqliteHelper.closeWritableDatabase(checkTableAvailable);
                return i;
            }
            sqliteHelper.closeWritableDatabase(checkTableAvailable);
        } else {
            UtdidLogger.d((String) TAG, "db is null");
        }
    }

    public synchronized void clear(Class<? extends Entity> cls) {
        if (cls != null) {
            clear(getTablename(cls));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void clear(java.lang.String r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 != 0) goto L_0x0005
            monitor-exit(r2)
            return
        L_0x0005:
            com.ta.audid.db.SqliteHelper r0 = r2.mHelper     // Catch:{ Exception -> 0x001a }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Exception -> 0x001a }
            if (r0 == 0) goto L_0x0016
            r1 = 0
            r0.delete(r3, r1, r1)     // Catch:{ Exception -> 0x001a }
            com.ta.audid.db.SqliteHelper r3 = r2.mHelper     // Catch:{ Exception -> 0x001a }
            r3.closeWritableDatabase(r0)     // Catch:{ Exception -> 0x001a }
        L_0x0016:
            monitor-exit(r2)
            return
        L_0x0018:
            r3 = move-exception
            goto L_0x0025
        L_0x001a:
            r3 = move-exception
            java.lang.String r0 = "delete db data"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0018 }
            com.ta.audid.utils.UtdidLogger.e(r0, r3, r1)     // Catch:{ all -> 0x0018 }
            monitor-exit(r2)
            return
        L_0x0025:
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.db.DBMgr.clear(java.lang.String):void");
    }

    private List<Field> getAllFields(Class cls) {
        if (this.mClsFieldsMap.containsKey(cls)) {
            return this.mClsFieldsMap.get(cls);
        }
        List<Field> emptyList = Collections.emptyList();
        if (cls != null) {
            emptyList = new ArrayList<>();
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.getAnnotation(Ingore.class) == null && !field.isSynthetic()) {
                    field.setAccessible(true);
                    emptyList.add(field);
                }
            }
            if (!(cls.getSuperclass() == null || cls.getSuperclass() == Object.class)) {
                emptyList.addAll(getAllFields(cls.getSuperclass()));
            }
            this.mClsFieldsMap.put(cls, emptyList);
        }
        return emptyList;
    }

    private String getColumnName(Field field) {
        String str;
        if (this.mFieldNameMap.containsKey(field)) {
            return this.mFieldNameMap.get(field);
        }
        Column column = (Column) field.getAnnotation(Column.class);
        if (column == null || TextUtils.isEmpty(column.value())) {
            str = field.getName();
        } else {
            str = column.value();
        }
        this.mFieldNameMap.put(field, str);
        return str;
    }

    private String getDefaultValue(Class cls) {
        return (cls == Long.TYPE || cls == Integer.TYPE || cls == Long.class) ? "default 0" : "default \"\"";
    }
}
