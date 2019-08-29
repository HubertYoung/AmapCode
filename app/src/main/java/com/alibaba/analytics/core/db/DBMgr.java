package com.alibaba.analytics.core.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.alibaba.analytics.core.Constants.Database;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.Ingore;
import com.alibaba.analytics.core.db.annotation.TableName;
import com.alibaba.analytics.utils.Logger;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.File;
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

    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0127, code lost:
        r12 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0129, code lost:
        r12 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0127 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:29:0x0080] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:75:0x0119=Splitter:B:75:0x0119, B:93:0x014a=Splitter:B:93:0x014a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.List<? extends com.alibaba.analytics.core.db.Entity> find(java.lang.Class<? extends com.alibaba.analytics.core.db.Entity> r12, java.lang.String r13, java.lang.String r14, int r15) {
        /*
            r11 = this;
            monitor-enter(r11)
            java.util.List r0 = java.util.Collections.EMPTY_LIST     // Catch:{ all -> 0x0155 }
            if (r12 != 0) goto L_0x0007
            monitor-exit(r11)
            return r0
        L_0x0007:
            java.lang.String r1 = r11.getTablename(r12)     // Catch:{ all -> 0x0155 }
            android.database.sqlite.SQLiteDatabase r2 = r11.checkTableAvailable(r12, r1)     // Catch:{ all -> 0x0155 }
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x0020
            java.lang.String r12 = "DBMgr"
            java.lang.Object[] r13 = new java.lang.Object[r4]     // Catch:{ all -> 0x0155 }
            java.lang.String r14 = "db is null"
            r13[r3] = r14     // Catch:{ all -> 0x0155 }
            com.alibaba.analytics.utils.Logger.d(r12, r13)     // Catch:{ all -> 0x0155 }
            monitor-exit(r11)
            return r0
        L_0x0020:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0155 }
            java.lang.String r6 = "SELECT * FROM "
            r5.<init>(r6)     // Catch:{ all -> 0x0155 }
            r5.append(r1)     // Catch:{ all -> 0x0155 }
            boolean r1 = android.text.TextUtils.isEmpty(r13)     // Catch:{ all -> 0x0155 }
            if (r1 == 0) goto L_0x0033
            java.lang.String r13 = ""
            goto L_0x003d
        L_0x0033:
            java.lang.String r1 = " WHERE "
            java.lang.String r13 = java.lang.String.valueOf(r13)     // Catch:{ all -> 0x0155 }
            java.lang.String r13 = r1.concat(r13)     // Catch:{ all -> 0x0155 }
        L_0x003d:
            r5.append(r13)     // Catch:{ all -> 0x0155 }
            boolean r13 = android.text.TextUtils.isEmpty(r14)     // Catch:{ all -> 0x0155 }
            if (r13 == 0) goto L_0x0049
            java.lang.String r13 = ""
            goto L_0x0053
        L_0x0049:
            java.lang.String r13 = " ORDER BY "
            java.lang.String r14 = java.lang.String.valueOf(r14)     // Catch:{ all -> 0x0155 }
            java.lang.String r13 = r13.concat(r14)     // Catch:{ all -> 0x0155 }
        L_0x0053:
            r5.append(r13)     // Catch:{ all -> 0x0155 }
            if (r15 > 0) goto L_0x005b
            java.lang.String r13 = ""
            goto L_0x0065
        L_0x005b:
            java.lang.String r13 = " LIMIT "
            java.lang.String r14 = java.lang.String.valueOf(r15)     // Catch:{ all -> 0x0155 }
            java.lang.String r13 = r13.concat(r14)     // Catch:{ all -> 0x0155 }
        L_0x0065:
            r5.append(r13)     // Catch:{ all -> 0x0155 }
            java.lang.String r13 = r5.toString()     // Catch:{ all -> 0x0155 }
            java.lang.String r14 = "DBMgr"
            r15 = 2
            java.lang.Object[] r1 = new java.lang.Object[r15]     // Catch:{ all -> 0x0155 }
            java.lang.String r5 = "sql"
            r1[r3] = r5     // Catch:{ all -> 0x0155 }
            r1[r4] = r13     // Catch:{ all -> 0x0155 }
            com.alibaba.analytics.utils.Logger.d(r14, r1)     // Catch:{ all -> 0x0155 }
            r14 = 0
            android.database.Cursor r13 = r2.rawQuery(r13, r14)     // Catch:{ Throwable -> 0x012f }
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0129, all -> 0x0127 }
            r14.<init>()     // Catch:{ Throwable -> 0x0129, all -> 0x0127 }
            java.util.List r0 = r11.getAllFields(r12)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
        L_0x0089:
            if (r13 == 0) goto L_0x0119
            boolean r1 = r13.moveToNext()     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            if (r1 == 0) goto L_0x0119
            java.lang.Object r1 = r12.newInstance()     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            com.alibaba.analytics.core.db.Entity r1 = (com.alibaba.analytics.core.db.Entity) r1     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            r5 = 0
        L_0x0098:
            int r6 = r0.size()     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            if (r5 >= r6) goto L_0x0114
            java.lang.Object r6 = r0.get(r5)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            java.lang.reflect.Field r6 = (java.lang.reflect.Field) r6     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            java.lang.Class r7 = r6.getType()     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            java.lang.String r8 = r11.getColumnName(r6)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            int r9 = r13.getColumnIndex(r8)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            r10 = -1
            if (r9 == r10) goto L_0x0104
            java.lang.Class<java.lang.Long> r8 = java.lang.Long.class
            if (r7 == r8) goto L_0x00e5
            java.lang.Class r8 = java.lang.Long.TYPE     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            if (r7 != r8) goto L_0x00bc
            goto L_0x00e5
        L_0x00bc:
            java.lang.Class<java.lang.Integer> r8 = java.lang.Integer.class
            if (r7 == r8) goto L_0x00dc
            java.lang.Class r8 = java.lang.Integer.TYPE     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            if (r7 != r8) goto L_0x00c5
            goto L_0x00dc
        L_0x00c5:
            java.lang.Class r8 = java.lang.Double.TYPE     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            if (r7 == r8) goto L_0x00d3
            java.lang.Class<java.lang.Double> r8 = java.lang.Double.class
            if (r7 != r8) goto L_0x00ce
            goto L_0x00d3
        L_0x00ce:
            java.lang.String r7 = r13.getString(r9)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            goto L_0x00ed
        L_0x00d3:
            double r7 = r13.getDouble(r9)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            java.lang.Double r7 = java.lang.Double.valueOf(r7)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            goto L_0x00ed
        L_0x00dc:
            int r7 = r13.getInt(r9)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            goto L_0x00ed
        L_0x00e5:
            long r7 = r13.getLong(r9)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
        L_0x00ed:
            r6.set(r1, r7)     // Catch:{ Exception -> 0x00f1 }
            goto L_0x0111
        L_0x00f1:
            r8 = move-exception
            boolean r8 = r8 instanceof java.lang.IllegalArgumentException     // Catch:{ Throwable -> 0x0111, all -> 0x0127 }
            if (r8 == 0) goto L_0x0111
            boolean r8 = r7 instanceof java.lang.String     // Catch:{ Throwable -> 0x0111, all -> 0x0127 }
            if (r8 == 0) goto L_0x0111
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Throwable -> 0x0111, all -> 0x0127 }
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Throwable -> 0x0111, all -> 0x0127 }
            r6.set(r1, r7)     // Catch:{ Throwable -> 0x0111, all -> 0x0127 }
            goto L_0x0111
        L_0x0104:
            java.lang.String r6 = "DBMgr"
            java.lang.Object[] r7 = new java.lang.Object[r15]     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            java.lang.String r9 = "can not get field"
            r7[r3] = r9     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            r7[r4] = r8     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            com.alibaba.analytics.utils.Logger.w(r6, r7)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
        L_0x0111:
            int r5 = r5 + 1
            goto L_0x0098
        L_0x0114:
            r14.add(r1)     // Catch:{ Throwable -> 0x0124, all -> 0x0127 }
            goto L_0x0089
        L_0x0119:
            com.alibaba.analytics.core.db.SqliteHelper r12 = r11.mHelper     // Catch:{ all -> 0x0155 }
            r12.closeCursor(r13)     // Catch:{ all -> 0x0155 }
            com.alibaba.analytics.core.db.SqliteHelper r12 = r11.mHelper     // Catch:{ all -> 0x0155 }
            r12.closeWritableDatabase(r2)     // Catch:{ all -> 0x0155 }
            goto L_0x0148
        L_0x0124:
            r12 = move-exception
            r0 = r14
            goto L_0x012a
        L_0x0127:
            r12 = move-exception
            goto L_0x014a
        L_0x0129:
            r12 = move-exception
        L_0x012a:
            r14 = r13
            goto L_0x0130
        L_0x012c:
            r12 = move-exception
            r13 = r14
            goto L_0x014a
        L_0x012f:
            r12 = move-exception
        L_0x0130:
            java.lang.String r13 = "DBMgr"
            java.lang.Object[] r15 = new java.lang.Object[r15]     // Catch:{ all -> 0x012c }
            java.lang.String r1 = "[get]"
            r15[r3] = r1     // Catch:{ all -> 0x012c }
            r15[r4] = r12     // Catch:{ all -> 0x012c }
            com.alibaba.analytics.utils.Logger.w(r13, r15)     // Catch:{ all -> 0x012c }
            com.alibaba.analytics.core.db.SqliteHelper r12 = r11.mHelper     // Catch:{ all -> 0x0155 }
            r12.closeCursor(r14)     // Catch:{ all -> 0x0155 }
            com.alibaba.analytics.core.db.SqliteHelper r12 = r11.mHelper     // Catch:{ all -> 0x0155 }
            r12.closeWritableDatabase(r2)     // Catch:{ all -> 0x0155 }
            r14 = r0
        L_0x0148:
            monitor-exit(r11)
            return r14
        L_0x014a:
            com.alibaba.analytics.core.db.SqliteHelper r14 = r11.mHelper     // Catch:{ all -> 0x0155 }
            r14.closeCursor(r13)     // Catch:{ all -> 0x0155 }
            com.alibaba.analytics.core.db.SqliteHelper r13 = r11.mHelper     // Catch:{ all -> 0x0155 }
            r13.closeWritableDatabase(r2)     // Catch:{ all -> 0x0155 }
            throw r12     // Catch:{ all -> 0x0155 }
        L_0x0155:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.db.DBMgr.find(java.lang.Class, java.lang.String, java.lang.String, int):java.util.List");
    }

    public void insert(Entity entity) {
        if (entity != null) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(entity);
            insert((List<? extends Entity>) arrayList);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:16|17|(8:20|(6:23|24|25|(2:27|73)(2:28|74)|33|21)|71|34|(2:36|(1:38))(1:39)|40|41|18)|42|43|44|45|46|47|48|49) */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:50|51|52|53|54|55|56|57) */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:58|59|60|61|62|63|64|65) */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0102, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x00da */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x00dd */
    /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x00e8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x00eb */
    /* JADX WARNING: Missing exception handler attribute for start block: B:58:0x00f1 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:60:0x00f4 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:62:0x00f7 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:62:0x00f7=Splitter:B:62:0x00f7, B:55:0x00eb=Splitter:B:55:0x00eb, B:46:0x00dd=Splitter:B:46:0x00dd} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void insert(java.util.List<? extends com.alibaba.analytics.core.db.Entity> r15) {
        /*
            r14 = this;
            monitor-enter(r14)
            if (r15 == 0) goto L_0x0101
            int r0 = r15.size()     // Catch:{ all -> 0x00fe }
            if (r0 != 0) goto L_0x000b
            goto L_0x0101
        L_0x000b:
            r0 = 0
            java.lang.Object r1 = r15.get(r0)     // Catch:{ all -> 0x00fe }
            com.alibaba.analytics.core.db.Entity r1 = (com.alibaba.analytics.core.db.Entity) r1     // Catch:{ all -> 0x00fe }
            java.lang.Class r1 = r1.getClass()     // Catch:{ all -> 0x00fe }
            java.lang.String r1 = r14.getTablename(r1)     // Catch:{ all -> 0x00fe }
            java.lang.Object r2 = r15.get(r0)     // Catch:{ all -> 0x00fe }
            com.alibaba.analytics.core.db.Entity r2 = (com.alibaba.analytics.core.db.Entity) r2     // Catch:{ all -> 0x00fe }
            java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x00fe }
            android.database.sqlite.SQLiteDatabase r2 = r14.checkTableAvailable(r2, r1)     // Catch:{ all -> 0x00fe }
            r3 = 1
            if (r2 != 0) goto L_0x0038
            java.lang.String r15 = "DBMgr"
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x00fe }
            java.lang.String r2 = "can not get available db"
            r1[r0] = r2     // Catch:{ all -> 0x00fe }
            com.alibaba.analytics.utils.Logger.w(r15, r1)     // Catch:{ all -> 0x00fe }
            monitor-exit(r14)
            return
        L_0x0038:
            r4 = 2
            if (r15 == 0) goto L_0x0050
            java.lang.String r5 = ""
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ all -> 0x00fe }
            java.lang.String r7 = "entities.size"
            r6[r0] = r7     // Catch:{ all -> 0x00fe }
            int r7 = r15.size()     // Catch:{ all -> 0x00fe }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x00fe }
            r6[r3] = r7     // Catch:{ all -> 0x00fe }
            com.alibaba.analytics.utils.Logger.d(r5, r6)     // Catch:{ all -> 0x00fe }
        L_0x0050:
            java.lang.Object r5 = r15.get(r0)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            com.alibaba.analytics.core.db.Entity r5 = (com.alibaba.analytics.core.db.Entity) r5     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            java.lang.Class r5 = r5.getClass()     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            java.util.List r5 = r14.getAllFields(r5)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            android.content.ContentValues r6 = new android.content.ContentValues     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            r6.<init>()     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            r2.beginTransaction()     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            r7 = 0
        L_0x0067:
            int r8 = r15.size()     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            if (r7 >= r8) goto L_0x00d7
            java.lang.Object r8 = r15.get(r7)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            com.alibaba.analytics.core.db.Entity r8 = (com.alibaba.analytics.core.db.Entity) r8     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            r9 = 0
        L_0x0074:
            int r10 = r5.size()     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            if (r9 >= r10) goto L_0x00a9
            java.lang.Object r10 = r5.get(r9)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            java.lang.reflect.Field r10 = (java.lang.reflect.Field) r10     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            java.lang.String r11 = r14.getColumnName(r10)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            java.lang.Object r10 = r10.get(r8)     // Catch:{ Exception -> 0x0098 }
            if (r10 == 0) goto L_0x0092
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ Exception -> 0x0098 }
            r6.put(r11, r10)     // Catch:{ Exception -> 0x0098 }
            goto L_0x00a6
        L_0x0092:
            java.lang.String r10 = ""
            r6.put(r11, r10)     // Catch:{ Exception -> 0x0098 }
            goto L_0x00a6
        L_0x0098:
            r10 = move-exception
            java.lang.String r11 = "DBMgr"
            java.lang.Object[] r12 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            java.lang.String r13 = "get field failed"
            r12[r0] = r13     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            r12[r3] = r10     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            com.alibaba.analytics.utils.Logger.w(r11, r12)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
        L_0x00a6:
            int r9 = r9 + 1
            goto L_0x0074
        L_0x00a9:
            long r9 = r8._id     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            r11 = -1
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 != 0) goto L_0x00c2
            java.lang.String r9 = "_id"
            r6.remove(r9)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            r9 = 0
            long r9 = r2.insert(r1, r9, r6)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            int r11 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r11 == 0) goto L_0x00d1
            r8._id = r9     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            goto L_0x00d1
        L_0x00c2:
            java.lang.String r9 = "_id=?"
            java.lang.String[] r10 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            long r11 = r8._id     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            java.lang.String r8 = java.lang.String.valueOf(r11)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            r10[r0] = r8     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            r2.update(r1, r6, r9, r10)     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
        L_0x00d1:
            r6.clear()     // Catch:{ Throwable -> 0x00f1, all -> 0x00e4 }
            int r7 = r7 + 1
            goto L_0x0067
        L_0x00d7:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00da }
        L_0x00da:
            r2.endTransaction()     // Catch:{ Exception -> 0x00dd }
        L_0x00dd:
            com.alibaba.analytics.core.db.SqliteHelper r15 = r14.mHelper     // Catch:{ all -> 0x00fe }
            r15.closeWritableDatabase(r2)     // Catch:{ all -> 0x00fe }
            monitor-exit(r14)
            return
        L_0x00e4:
            r15 = move-exception
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00e8 }
        L_0x00e8:
            r2.endTransaction()     // Catch:{ Exception -> 0x00eb }
        L_0x00eb:
            com.alibaba.analytics.core.db.SqliteHelper r0 = r14.mHelper     // Catch:{ all -> 0x00fe }
            r0.closeWritableDatabase(r2)     // Catch:{ all -> 0x00fe }
            throw r15     // Catch:{ all -> 0x00fe }
        L_0x00f1:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00f4 }
        L_0x00f4:
            r2.endTransaction()     // Catch:{ Exception -> 0x00f7 }
        L_0x00f7:
            com.alibaba.analytics.core.db.SqliteHelper r15 = r14.mHelper     // Catch:{ all -> 0x00fe }
            r15.closeWritableDatabase(r2)     // Catch:{ all -> 0x00fe }
            monitor-exit(r14)
            return
        L_0x00fe:
            r15 = move-exception
            monitor-exit(r14)
            throw r15
        L_0x0101:
            monitor-exit(r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.db.DBMgr.insert(java.util.List):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(12:14|15|(6:18|(1:20)|21|(4:23|(1:25)(1:26)|27|73)(2:28|72)|29|16)|71|30|(2:32|(1:34)(1:35))|36|37|38|39|40|41) */
    /* JADX WARNING: Can't wrap try/catch for region: R(17:13|14|15|(6:18|(1:20)|21|(4:23|(1:25)(1:26)|27|73)(2:28|72)|29|16)|71|30|(2:32|(1:34)(1:35))|36|37|38|39|40|41|42|55|56|57) */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0201, code lost:
        return 0;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:38:0x01c5 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x01c8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x01e4 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x01e7 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:60:0x01f3 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:62:0x01f6 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:62:0x01f6=Splitter:B:62:0x01f6, B:53:0x01e7=Splitter:B:53:0x01e7, B:40:0x01c8=Splitter:B:40:0x01c8} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int delete(java.util.List<? extends com.alibaba.analytics.core.db.Entity> r20) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            monitor-enter(r19)
            r3 = 0
            if (r2 == 0) goto L_0x0200
            int r4 = r20.size()     // Catch:{ all -> 0x01fc }
            if (r4 != 0) goto L_0x0010
            goto L_0x0200
        L_0x0010:
            java.lang.Object r4 = r2.get(r3)     // Catch:{ all -> 0x01fc }
            com.alibaba.analytics.core.db.Entity r4 = (com.alibaba.analytics.core.db.Entity) r4     // Catch:{ all -> 0x01fc }
            java.lang.Class r4 = r4.getClass()     // Catch:{ all -> 0x01fc }
            java.lang.String r4 = r1.getTablename(r4)     // Catch:{ all -> 0x01fc }
            java.lang.Object r5 = r2.get(r3)     // Catch:{ all -> 0x01fc }
            com.alibaba.analytics.core.db.Entity r5 = (com.alibaba.analytics.core.db.Entity) r5     // Catch:{ all -> 0x01fc }
            java.lang.Class r5 = r5.getClass()     // Catch:{ all -> 0x01fc }
            android.database.sqlite.SQLiteDatabase r5 = r1.checkTableAvailable(r5, r4)     // Catch:{ all -> 0x01fc }
            r6 = 1
            if (r5 != 0) goto L_0x003c
            java.lang.String r2 = "DBMgr"
            java.lang.Object[] r4 = new java.lang.Object[r6]     // Catch:{ all -> 0x01fc }
            java.lang.String r5 = "db is null"
            r4[r3] = r5     // Catch:{ all -> 0x01fc }
            com.alibaba.analytics.utils.Logger.d(r2, r4)     // Catch:{ all -> 0x01fc }
            monitor-exit(r19)
            return r3
        L_0x003c:
            r7 = 2
            r5.beginTransaction()     // Catch:{ Throwable -> 0x01d1 }
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch:{ Throwable -> 0x01d1 }
            r8.<init>()     // Catch:{ Throwable -> 0x01d1 }
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ Throwable -> 0x01d1 }
            r9.<init>()     // Catch:{ Throwable -> 0x01d1 }
            r10 = 0
        L_0x004b:
            int r11 = r20.size()     // Catch:{ Throwable -> 0x01d1 }
            r16 = 3
            r12 = 8
            if (r10 >= r11) goto L_0x0126
            int r11 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            if (r11 <= 0) goto L_0x0060
            java.lang.String r11 = " OR "
            r8.append(r11)     // Catch:{ Throwable -> 0x01d1 }
        L_0x0060:
            java.lang.String r11 = "_id=?"
            r8.append(r11)     // Catch:{ Throwable -> 0x01d1 }
            java.lang.Object r11 = r2.get(r10)     // Catch:{ Throwable -> 0x01d1 }
            com.alibaba.analytics.core.db.Entity r11 = (com.alibaba.analytics.core.db.Entity) r11     // Catch:{ Throwable -> 0x01d1 }
            long r13 = r11._id     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = java.lang.String.valueOf(r13)     // Catch:{ Throwable -> 0x01d1 }
            r9.add(r11)     // Catch:{ Throwable -> 0x01d1 }
            int r11 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            r13 = 20
            if (r11 != r13) goto L_0x011f
            int r11 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String[] r11 = new java.lang.String[r11]     // Catch:{ Throwable -> 0x01d1 }
            r9.toArray(r11)     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r13 = r8.toString()     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r14 = "DBMgr"
            java.lang.Object[] r15 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r17 = "whereClause"
            r15[r3] = r17     // Catch:{ Throwable -> 0x01d1 }
            r15[r6] = r13     // Catch:{ Throwable -> 0x01d1 }
            com.alibaba.analytics.utils.Logger.d(r14, r15)     // Catch:{ Throwable -> 0x01d1 }
            int r11 = r5.delete(r4, r13, r11)     // Catch:{ Throwable -> 0x01d1 }
            long r13 = (long) r11     // Catch:{ Throwable -> 0x01d1 }
            int r11 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            r18 = r8
            long r7 = (long) r11     // Catch:{ Throwable -> 0x01d1 }
            int r7 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r7 != 0) goto L_0x00dd
            java.lang.String r7 = "DBMgr"
            java.lang.Object[] r8 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = "delete success. DbName"
            r8[r3] = r11     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = r1.mDbName     // Catch:{ Throwable -> 0x01d1 }
            r8[r6] = r11     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = "tableName"
            r12 = 2
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            r8[r16] = r4     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = "whereArgs size"
            r12 = 4
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            int r11 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ Throwable -> 0x01d1 }
            r12 = 5
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = "ret size"
            r12 = 6
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            java.lang.Long r11 = java.lang.Long.valueOf(r13)     // Catch:{ Throwable -> 0x01d1 }
            r12 = 7
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            com.alibaba.analytics.utils.Logger.d(r7, r8)     // Catch:{ Throwable -> 0x01d1 }
            goto L_0x0112
        L_0x00dd:
            java.lang.String r7 = "DBMgr"
            java.lang.Object[] r8 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = "delete fail. DbName"
            r8[r3] = r11     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = r1.mDbName     // Catch:{ Throwable -> 0x01d1 }
            r8[r6] = r11     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = "tableName"
            r12 = 2
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            r8[r16] = r4     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = "whereArgs size"
            r12 = 4
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            int r11 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ Throwable -> 0x01d1 }
            r12 = 5
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = "ret size"
            r12 = 6
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            java.lang.Long r11 = java.lang.Long.valueOf(r13)     // Catch:{ Throwable -> 0x01d1 }
            r12 = 7
            r8[r12] = r11     // Catch:{ Throwable -> 0x01d1 }
            com.alibaba.analytics.utils.Logger.d(r7, r8)     // Catch:{ Throwable -> 0x01d1 }
        L_0x0112:
            r7 = r18
            int r8 = r7.length()     // Catch:{ Throwable -> 0x01d1 }
            r7.delete(r3, r8)     // Catch:{ Throwable -> 0x01d1 }
            r9.clear()     // Catch:{ Throwable -> 0x01d1 }
            goto L_0x0120
        L_0x011f:
            r7 = r8
        L_0x0120:
            int r10 = r10 + 1
            r8 = r7
            r7 = 2
            goto L_0x004b
        L_0x0126:
            r7 = r8
            int r8 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            if (r8 <= 0) goto L_0x01c2
            int r8 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String[] r8 = new java.lang.String[r8]     // Catch:{ Throwable -> 0x01d1 }
            r9.toArray(r8)     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r10 = "DBMgr"
            r11 = 2
            java.lang.Object[] r13 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r11 = "whereClause"
            r13[r3] = r11     // Catch:{ Throwable -> 0x01d1 }
            r13[r6] = r7     // Catch:{ Throwable -> 0x01d1 }
            com.alibaba.analytics.utils.Logger.d(r10, r13)     // Catch:{ Throwable -> 0x01d1 }
            int r7 = r5.delete(r4, r7, r8)     // Catch:{ Throwable -> 0x01d1 }
            long r7 = (long) r7     // Catch:{ Throwable -> 0x01d1 }
            int r10 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            long r10 = (long) r10     // Catch:{ Throwable -> 0x01d1 }
            int r10 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r10 != 0) goto L_0x018d
            java.lang.String r10 = "DBMgr"
            java.lang.Object[] r11 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r12 = "delete success. DbName"
            r11[r3] = r12     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r12 = r1.mDbName     // Catch:{ Throwable -> 0x01d1 }
            r11[r6] = r12     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r12 = "tableName"
            r13 = 2
            r11[r13] = r12     // Catch:{ Throwable -> 0x01d1 }
            r11[r16] = r4     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r4 = "whereArgs size"
            r12 = 4
            r11[r12] = r4     // Catch:{ Throwable -> 0x01d1 }
            int r4 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x01d1 }
            r9 = 5
            r11[r9] = r4     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r4 = "ret size"
            r9 = 6
            r11[r9] = r4     // Catch:{ Throwable -> 0x01d1 }
            java.lang.Long r4 = java.lang.Long.valueOf(r7)     // Catch:{ Throwable -> 0x01d1 }
            r7 = 7
            r11[r7] = r4     // Catch:{ Throwable -> 0x01d1 }
            com.alibaba.analytics.utils.Logger.d(r10, r11)     // Catch:{ Throwable -> 0x01d1 }
            goto L_0x01c2
        L_0x018d:
            java.lang.String r10 = "DBMgr"
            java.lang.Object[] r11 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r12 = "delete fail. DbName"
            r11[r3] = r12     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r12 = r1.mDbName     // Catch:{ Throwable -> 0x01d1 }
            r11[r6] = r12     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r12 = "tableName"
            r13 = 2
            r11[r13] = r12     // Catch:{ Throwable -> 0x01d1 }
            r11[r16] = r4     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r4 = "whereArgs size"
            r12 = 4
            r11[r12] = r4     // Catch:{ Throwable -> 0x01d1 }
            int r4 = r9.size()     // Catch:{ Throwable -> 0x01d1 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x01d1 }
            r9 = 5
            r11[r9] = r4     // Catch:{ Throwable -> 0x01d1 }
            java.lang.String r4 = "ret size"
            r9 = 6
            r11[r9] = r4     // Catch:{ Throwable -> 0x01d1 }
            java.lang.Long r4 = java.lang.Long.valueOf(r7)     // Catch:{ Throwable -> 0x01d1 }
            r7 = 7
            r11[r7] = r4     // Catch:{ Throwable -> 0x01d1 }
            com.alibaba.analytics.utils.Logger.d(r10, r11)     // Catch:{ Throwable -> 0x01d1 }
        L_0x01c2:
            r5.setTransactionSuccessful()     // Catch:{ Throwable -> 0x01c5 }
        L_0x01c5:
            r5.endTransaction()     // Catch:{ Throwable -> 0x01c8 }
        L_0x01c8:
            com.alibaba.analytics.core.db.SqliteHelper r3 = r1.mHelper     // Catch:{ all -> 0x01fc }
        L_0x01ca:
            r3.closeWritableDatabase(r5)     // Catch:{ all -> 0x01fc }
            goto L_0x01ea
        L_0x01ce:
            r0 = move-exception
            r2 = r0
            goto L_0x01f0
        L_0x01d1:
            r0 = move-exception
            r4 = r0
            java.lang.String r7 = "DBMgr"
            r8 = 2
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x01ce }
            java.lang.String r9 = "db delete error"
            r8[r3] = r9     // Catch:{ all -> 0x01ce }
            r8[r6] = r4     // Catch:{ all -> 0x01ce }
            com.alibaba.analytics.utils.Logger.w(r7, r8)     // Catch:{ all -> 0x01ce }
            r5.setTransactionSuccessful()     // Catch:{ Throwable -> 0x01e4 }
        L_0x01e4:
            r5.endTransaction()     // Catch:{ Throwable -> 0x01e7 }
        L_0x01e7:
            com.alibaba.analytics.core.db.SqliteHelper r3 = r1.mHelper     // Catch:{ all -> 0x01fc }
            goto L_0x01ca
        L_0x01ea:
            int r2 = r20.size()     // Catch:{ all -> 0x01fc }
            monitor-exit(r19)
            return r2
        L_0x01f0:
            r5.setTransactionSuccessful()     // Catch:{ Throwable -> 0x01f3 }
        L_0x01f3:
            r5.endTransaction()     // Catch:{ Throwable -> 0x01f6 }
        L_0x01f6:
            com.alibaba.analytics.core.db.SqliteHelper r3 = r1.mHelper     // Catch:{ all -> 0x01fc }
            r3.closeWritableDatabase(r5)     // Catch:{ all -> 0x01fc }
            throw r2     // Catch:{ all -> 0x01fc }
        L_0x01fc:
            r0 = move-exception
            r2 = r0
            monitor-exit(r19)
            throw r2
        L_0x0200:
            monitor-exit(r19)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.db.DBMgr.delete(java.util.List):int");
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

    /* JADX WARNING: Can't wrap try/catch for region: R(11:11|12|(6:15|(6:18|19|20|58|24|16)|56|25|26|13)|27|28|29|30|31|32|33|34) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:38|39|40|41|42) */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:43|44|45|46|47|48|49|50) */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0081, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r8.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b5, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r2.setTransactionSuccessful();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r2.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r12.mHelper.closeWritableDatabase(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c1, code lost:
        throw r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d3, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x00ab */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00ae */
    /* JADX WARNING: Missing exception handler attribute for start block: B:38:0x00b9 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x00bc */
    /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x00c2 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x00c5 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x00c8 */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b5 A[ExcHandler: all (r13v3 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:11:0x0038] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:47:0x00c8=Splitter:B:47:0x00c8, B:40:0x00bc=Splitter:B:40:0x00bc, B:31:0x00ae=Splitter:B:31:0x00ae} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void update(java.util.List<? extends com.alibaba.analytics.core.db.Entity> r13) {
        /*
            r12 = this;
            monitor-enter(r12)
            if (r13 == 0) goto L_0x00d2
            int r0 = r13.size()     // Catch:{ all -> 0x00cf }
            if (r0 != 0) goto L_0x000b
            goto L_0x00d2
        L_0x000b:
            r0 = 0
            java.lang.Object r1 = r13.get(r0)     // Catch:{ all -> 0x00cf }
            com.alibaba.analytics.core.db.Entity r1 = (com.alibaba.analytics.core.db.Entity) r1     // Catch:{ all -> 0x00cf }
            java.lang.Class r1 = r1.getClass()     // Catch:{ all -> 0x00cf }
            java.lang.String r1 = r12.getTablename(r1)     // Catch:{ all -> 0x00cf }
            java.lang.Object r2 = r13.get(r0)     // Catch:{ all -> 0x00cf }
            com.alibaba.analytics.core.db.Entity r2 = (com.alibaba.analytics.core.db.Entity) r2     // Catch:{ all -> 0x00cf }
            java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x00cf }
            android.database.sqlite.SQLiteDatabase r2 = r12.checkTableAvailable(r2, r1)     // Catch:{ all -> 0x00cf }
            r3 = 1
            if (r2 != 0) goto L_0x0038
            java.lang.String r13 = "DBMgr"
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x00cf }
            java.lang.String r2 = "[update] db is null"
            r1[r0] = r2     // Catch:{ all -> 0x00cf }
            com.alibaba.analytics.utils.Logger.w(r13, r1)     // Catch:{ all -> 0x00cf }
            monitor-exit(r12)
            return
        L_0x0038:
            r2.beginTransaction()     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            java.lang.Object r4 = r13.get(r0)     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            com.alibaba.analytics.core.db.Entity r4 = (com.alibaba.analytics.core.db.Entity) r4     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            java.lang.Class r4 = r4.getClass()     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            java.util.List r4 = r12.getAllFields(r4)     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            r5 = 0
        L_0x004a:
            int r6 = r13.size()     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            if (r5 >= r6) goto L_0x00a8
            android.content.ContentValues r6 = new android.content.ContentValues     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            r6.<init>()     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            r7 = 0
        L_0x0056:
            int r8 = r4.size()     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            if (r7 >= r8) goto L_0x0088
            java.lang.Object r8 = r4.get(r7)     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            java.lang.reflect.Field r8 = (java.lang.reflect.Field) r8     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            r8.setAccessible(r3)     // Catch:{ Exception -> 0x0081, all -> 0x00b5 }
            java.lang.String r9 = r12.getColumnName(r8)     // Catch:{ Exception -> 0x0081, all -> 0x00b5 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0081, all -> 0x00b5 }
            r10.<init>()     // Catch:{ Exception -> 0x0081, all -> 0x00b5 }
            java.lang.Object r11 = r13.get(r5)     // Catch:{ Exception -> 0x0081, all -> 0x00b5 }
            java.lang.Object r8 = r8.get(r11)     // Catch:{ Exception -> 0x0081, all -> 0x00b5 }
            r10.append(r8)     // Catch:{ Exception -> 0x0081, all -> 0x00b5 }
            java.lang.String r8 = r10.toString()     // Catch:{ Exception -> 0x0081, all -> 0x00b5 }
            r6.put(r9, r8)     // Catch:{ Exception -> 0x0081, all -> 0x00b5 }
            goto L_0x0085
        L_0x0081:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
        L_0x0085:
            int r7 = r7 + 1
            goto L_0x0056
        L_0x0088:
            java.lang.String r7 = "_id=?"
            java.lang.String[] r8 = new java.lang.String[r3]     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            r9.<init>()     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            java.lang.Object r10 = r13.get(r5)     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            com.alibaba.analytics.core.db.Entity r10 = (com.alibaba.analytics.core.db.Entity) r10     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            long r10 = r10._id     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            r9.append(r10)     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            r8[r0] = r9     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            r2.update(r1, r6, r7, r8)     // Catch:{ Exception -> 0x00c2, all -> 0x00b5 }
            int r5 = r5 + 1
            goto L_0x004a
        L_0x00a8:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00ab }
        L_0x00ab:
            r2.endTransaction()     // Catch:{ Exception -> 0x00ae }
        L_0x00ae:
            com.alibaba.analytics.core.db.SqliteHelper r13 = r12.mHelper     // Catch:{ all -> 0x00cf }
            r13.closeWritableDatabase(r2)     // Catch:{ all -> 0x00cf }
            monitor-exit(r12)
            return
        L_0x00b5:
            r13 = move-exception
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00b9 }
        L_0x00b9:
            r2.endTransaction()     // Catch:{ Exception -> 0x00bc }
        L_0x00bc:
            com.alibaba.analytics.core.db.SqliteHelper r0 = r12.mHelper     // Catch:{ all -> 0x00cf }
            r0.closeWritableDatabase(r2)     // Catch:{ all -> 0x00cf }
            throw r13     // Catch:{ all -> 0x00cf }
        L_0x00c2:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00c5 }
        L_0x00c5:
            r2.endTransaction()     // Catch:{ Exception -> 0x00c8 }
        L_0x00c8:
            com.alibaba.analytics.core.db.SqliteHelper r13 = r12.mHelper     // Catch:{ all -> 0x00cf }
            r13.closeWritableDatabase(r2)     // Catch:{ all -> 0x00cf }
            monitor-exit(r12)
            return
        L_0x00cf:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        L_0x00d2:
            monitor-exit(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.db.DBMgr.update(java.util.List):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:11|12|(5:15|(4:18|(3:22|23|59)|27|16)|58|28|13)|29|30|31|32|33|34|35|36) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:40|41|42|43|44) */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:45|46|47|48|49|50|51|52) */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a8, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r8.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00bf, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r2.setTransactionSuccessful();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r2.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r13.mHelper.closeWritableDatabase(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00cb, code lost:
        throw r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00dd, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00b5 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00b8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x00c3 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x00c6 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x00cc */
    /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x00cf */
    /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x00d2 */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00bf A[ExcHandler: all (r14v3 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:11:0x0038] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:49:0x00d2=Splitter:B:49:0x00d2, B:42:0x00c6=Splitter:B:42:0x00c6, B:33:0x00b8=Splitter:B:33:0x00b8} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void updateLogPriority(java.util.List<? extends com.alibaba.analytics.core.db.Entity> r14) {
        /*
            r13 = this;
            monitor-enter(r13)
            if (r14 == 0) goto L_0x00dc
            int r0 = r14.size()     // Catch:{ all -> 0x00d9 }
            if (r0 != 0) goto L_0x000b
            goto L_0x00dc
        L_0x000b:
            r0 = 0
            java.lang.Object r1 = r14.get(r0)     // Catch:{ all -> 0x00d9 }
            com.alibaba.analytics.core.db.Entity r1 = (com.alibaba.analytics.core.db.Entity) r1     // Catch:{ all -> 0x00d9 }
            java.lang.Class r1 = r1.getClass()     // Catch:{ all -> 0x00d9 }
            java.lang.String r1 = r13.getTablename(r1)     // Catch:{ all -> 0x00d9 }
            java.lang.Object r2 = r14.get(r0)     // Catch:{ all -> 0x00d9 }
            com.alibaba.analytics.core.db.Entity r2 = (com.alibaba.analytics.core.db.Entity) r2     // Catch:{ all -> 0x00d9 }
            java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x00d9 }
            android.database.sqlite.SQLiteDatabase r2 = r13.checkTableAvailable(r2, r1)     // Catch:{ all -> 0x00d9 }
            r3 = 1
            if (r2 != 0) goto L_0x0038
            java.lang.String r14 = "DBMgr"
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x00d9 }
            java.lang.String r2 = "[update] db is null"
            r1[r0] = r2     // Catch:{ all -> 0x00d9 }
            com.alibaba.analytics.utils.Logger.w(r14, r1)     // Catch:{ all -> 0x00d9 }
            monitor-exit(r13)
            return
        L_0x0038:
            r2.beginTransaction()     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            java.lang.Object r4 = r14.get(r0)     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            com.alibaba.analytics.core.db.Entity r4 = (com.alibaba.analytics.core.db.Entity) r4     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            java.lang.Class r4 = r4.getClass()     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            java.util.List r4 = r13.getAllFields(r4)     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            r5 = 0
        L_0x004a:
            int r6 = r14.size()     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            if (r5 >= r6) goto L_0x00b2
            android.content.ContentValues r6 = new android.content.ContentValues     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            r6.<init>()     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            r7 = 0
        L_0x0056:
            int r8 = r4.size()     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            if (r7 >= r8) goto L_0x00af
            java.lang.Object r8 = r4.get(r7)     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            java.lang.reflect.Field r8 = (java.lang.reflect.Field) r8     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            java.lang.String r9 = r13.getColumnName(r8)     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            if (r9 == 0) goto L_0x00ac
            java.lang.String r10 = "priority"
            boolean r10 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
            if (r10 == 0) goto L_0x00ac
            r8.setAccessible(r3)     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            r10.<init>()     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            java.lang.Object r11 = r14.get(r5)     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            java.lang.Object r8 = r8.get(r11)     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            r10.append(r8)     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            java.lang.String r8 = r10.toString()     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            r6.put(r9, r8)     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            java.lang.String r8 = "_id=?"
            java.lang.String[] r9 = new java.lang.String[r3]     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            r10.<init>()     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            java.lang.Object r11 = r14.get(r5)     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            com.alibaba.analytics.core.db.Entity r11 = (com.alibaba.analytics.core.db.Entity) r11     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            long r11 = r11._id     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            r10.append(r11)     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            r9[r0] = r10     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            r2.update(r1, r6, r8, r9)     // Catch:{ Exception -> 0x00a8, all -> 0x00bf }
            goto L_0x00af
        L_0x00a8:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ Exception -> 0x00cc, all -> 0x00bf }
        L_0x00ac:
            int r7 = r7 + 1
            goto L_0x0056
        L_0x00af:
            int r5 = r5 + 1
            goto L_0x004a
        L_0x00b2:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00b5 }
        L_0x00b5:
            r2.endTransaction()     // Catch:{ Exception -> 0x00b8 }
        L_0x00b8:
            com.alibaba.analytics.core.db.SqliteHelper r14 = r13.mHelper     // Catch:{ all -> 0x00d9 }
            r14.closeWritableDatabase(r2)     // Catch:{ all -> 0x00d9 }
            monitor-exit(r13)
            return
        L_0x00bf:
            r14 = move-exception
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00c3 }
        L_0x00c3:
            r2.endTransaction()     // Catch:{ Exception -> 0x00c6 }
        L_0x00c6:
            com.alibaba.analytics.core.db.SqliteHelper r0 = r13.mHelper     // Catch:{ all -> 0x00d9 }
            r0.closeWritableDatabase(r2)     // Catch:{ all -> 0x00d9 }
            throw r14     // Catch:{ all -> 0x00d9 }
        L_0x00cc:
            r2.setTransactionSuccessful()     // Catch:{ Exception -> 0x00cf }
        L_0x00cf:
            r2.endTransaction()     // Catch:{ Exception -> 0x00d2 }
        L_0x00d2:
            com.alibaba.analytics.core.db.SqliteHelper r14 = r13.mHelper     // Catch:{ all -> 0x00d9 }
            r14.closeWritableDatabase(r2)     // Catch:{ all -> 0x00d9 }
            monitor-exit(r13)
            return
        L_0x00d9:
            r14 = move-exception
            monitor-exit(r13)
            throw r14
        L_0x00dc:
            monitor-exit(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.db.DBMgr.updateLogPriority(java.util.List):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x003b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void execSQL(java.lang.Class<? extends com.alibaba.analytics.core.db.Entity> r3, java.lang.String r4) {
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
            com.alibaba.analytics.core.db.SqliteHelper r4 = r2.mHelper     // Catch:{ all -> 0x0037 }
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
            com.alibaba.analytics.utils.Logger.w(r0, r4, r1)     // Catch:{ all -> 0x001f }
            com.alibaba.analytics.core.db.SqliteHelper r4 = r2.mHelper     // Catch:{ all -> 0x0037 }
            r4.closeWritableDatabase(r3)     // Catch:{ all -> 0x0037 }
            monitor-exit(r2)
            return
        L_0x0031:
            com.alibaba.analytics.core.db.SqliteHelper r0 = r2.mHelper     // Catch:{ all -> 0x0037 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.db.DBMgr.execSQL(java.lang.Class, java.lang.String):void");
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0053, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int delete(java.lang.Class<? extends com.alibaba.analytics.core.db.Entity> r6, java.lang.String r7, java.lang.String[] r8) {
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
            com.alibaba.analytics.utils.Logger.d(r0, r1)     // Catch:{ all -> 0x0054 }
            if (r6 == 0) goto L_0x0051
            java.lang.String r0 = r5.getTablename(r6)     // Catch:{ all -> 0x0054 }
            android.database.sqlite.SQLiteDatabase r0 = r5.checkTableAvailable(r6, r0)     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x002d
            monitor-exit(r5)
            return r3
        L_0x002d:
            java.lang.String r6 = r5.getTablename(r6)     // Catch:{ Throwable -> 0x003d }
            int r6 = r0.delete(r6, r7, r8)     // Catch:{ Throwable -> 0x003d }
            com.alibaba.analytics.core.db.SqliteHelper r7 = r5.mHelper     // Catch:{ all -> 0x0054 }
            r7.closeWritableDatabase(r0)     // Catch:{ all -> 0x0054 }
            goto L_0x0052
        L_0x003b:
            r6 = move-exception
            goto L_0x004b
        L_0x003d:
            r6 = move-exception
            java.lang.String r7 = "DBMgr"
            java.lang.Object[] r8 = new java.lang.Object[r3]     // Catch:{ all -> 0x003b }
            com.alibaba.analytics.utils.Logger.w(r7, r6, r8)     // Catch:{ all -> 0x003b }
            com.alibaba.analytics.core.db.SqliteHelper r6 = r5.mHelper     // Catch:{ all -> 0x0054 }
            r6.closeWritableDatabase(r0)     // Catch:{ all -> 0x0054 }
            goto L_0x0051
        L_0x004b:
            com.alibaba.analytics.core.db.SqliteHelper r7 = r5.mHelper     // Catch:{ all -> 0x0054 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.db.DBMgr.delete(java.lang.Class, java.lang.String, java.lang.String[]):int");
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
        if (!(cls == null || bool.booleanValue() || writableDatabase == null)) {
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
                    Logger.d((String) TAG, "has not create talbe:", str);
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
                Logger.w((String) TAG, "update db error...", e);
            }
            sb3.delete(0, sb4.length());
            Logger.d((String) TAG, null, "excute sql:", sb4);
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
        Logger.d((String) TAG, "excute sql:", sb3);
        try {
            sQLiteDatabase.execSQL(sb3);
        } catch (Exception e) {
            Logger.w((String) TAG, "create db error", e);
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
            Logger.d((String) TAG, "db is null");
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
            com.alibaba.analytics.core.db.SqliteHelper r0 = r2.mHelper     // Catch:{ Exception -> 0x001a }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Exception -> 0x001a }
            if (r0 == 0) goto L_0x0016
            r1 = 0
            r0.delete(r3, r1, r1)     // Catch:{ Exception -> 0x001a }
            com.alibaba.analytics.core.db.SqliteHelper r3 = r2.mHelper     // Catch:{ Exception -> 0x001a }
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
            com.alibaba.analytics.utils.Logger.e(r0, r3, r1)     // Catch:{ all -> 0x0018 }
            monitor-exit(r2)
            return
        L_0x0025:
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.db.DBMgr.clear(java.lang.String):void");
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

    public double getDbFileSize() {
        File databasePath = Variables.getInstance().getContext().getDatabasePath(Database.DATABASE_NAME);
        if (databasePath != null) {
            return (((double) databasePath.length()) / 1024.0d) / 1024.0d;
        }
        return 0.0d;
    }
}
