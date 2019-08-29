package org.android.agoo.message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.autonavi.common.SuperId;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.MsgDO;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageService {
    private static Context a;
    private static Map<String, Integer> c;
    private volatile SQLiteOpenHelper b = null;

    static class MessageDBHelper extends SQLiteOpenHelper {
        public MessageDBHelper(Context context) {
            super(context, "message_accs_db", null, 3);
        }

        public SQLiteDatabase getWritableDatabase() {
            if (!AdapterUtilityImpl.checkIsWritable(super.getWritableDatabase().getPath(), 102400)) {
                return null;
            }
            return super.getWritableDatabase();
        }

        private static String a() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("create table accs_message");
            stringBuffer.append("(");
            stringBuffer.append("id text UNIQUE not null,");
            stringBuffer.append("state text,");
            stringBuffer.append("message text,");
            stringBuffer.append("create_time date");
            stringBuffer.append(");");
            return stringBuffer.toString();
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.execSQL("delete from message where create_time< date('now','-7 day') and state=1");
                } catch (Throwable th) {
                    ALog.e("MessageService", "MessageService onUpgrade is error", th, new Object[0]);
                    return;
                }
            }
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS accs_message");
            sQLiteDatabase.execSQL(a());
            return;
            throw th;
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            if (sQLiteDatabase != null) {
                try {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("create table message");
                    stringBuffer.append("(");
                    stringBuffer.append("id text UNIQUE not null,");
                    stringBuffer.append("state integer,");
                    stringBuffer.append("body_code integer,");
                    stringBuffer.append("report long,");
                    stringBuffer.append("target_time long,");
                    stringBuffer.append("interval integer,");
                    stringBuffer.append("type text,");
                    stringBuffer.append("message text,");
                    stringBuffer.append("notify integer,");
                    stringBuffer.append("create_time date");
                    stringBuffer.append(");");
                    sQLiteDatabase.execSQL(stringBuffer.toString());
                    sQLiteDatabase.execSQL("CREATE INDEX id_index ON message(id)");
                    sQLiteDatabase.execSQL("CREATE INDEX body_code_index ON message(body_code)");
                    sQLiteDatabase.execSQL(a());
                } catch (Throwable th) {
                    ALog.e("MessageService", "messagedbhelper create", th, new Object[0]);
                }
            }
        }
    }

    public final void a(Context context) {
        c = new HashMap();
        a = context;
        this.b = new MessageDBHelper(context);
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0086 A[Catch:{ all -> 0x007b }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00ce  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r8, java.lang.String r9) {
        /*
            r7 = this;
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.I
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            r1 = 0
            if (r0 == 0) goto L_0x002c
            java.lang.String r0 = "MessageService"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "updateAccsMessage sqlite3--->["
            r2.<init>(r3)
            r2.append(r8)
            java.lang.String r3 = ",state="
            r2.append(r3)
            r2.append(r9)
            java.lang.String r3 = "]"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r1]
            com.taobao.accs.utl.ALog.i(r0, r2, r3)
        L_0x002c:
            r0 = 0
            boolean r2 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x007d }
            if (r2 != 0) goto L_0x007a
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x007d }
            if (r2 == 0) goto L_0x003a
            goto L_0x007a
        L_0x003a:
            android.database.sqlite.SQLiteOpenHelper r2 = r7.b     // Catch:{ Throwable -> 0x007d }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x007d }
            if (r2 != 0) goto L_0x0048
            if (r2 == 0) goto L_0x0047
            r2.close()
        L_0x0047:
            return
        L_0x0048:
            java.lang.String r0 = "1"
            boolean r0 = android.text.TextUtils.equals(r9, r0)     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
            r3 = 2
            r4 = 1
            if (r0 == 0) goto L_0x0063
            java.lang.String r0 = "UPDATE accs_message set state = ? where id = ? and state = ?"
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
            r5[r1] = r9     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
            r5[r4] = r8     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
            java.lang.String r8 = "0"
            r5[r3] = r8     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
            r2.execSQL(r0, r5)     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
            goto L_0x006e
        L_0x0063:
            java.lang.String r0 = "UPDATE accs_message set state = ? where id = ?"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
            r3[r1] = r9     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
            r3[r4] = r8     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
            r2.execSQL(r0, r3)     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
        L_0x006e:
            if (r2 == 0) goto L_0x00cb
            r2.close()
            return
        L_0x0074:
            r8 = move-exception
            r0 = r2
            goto L_0x00cc
        L_0x0077:
            r8 = move-exception
            r0 = r2
            goto L_0x007e
        L_0x007a:
            return
        L_0x007b:
            r8 = move-exception
            goto L_0x00cc
        L_0x007d:
            r8 = move-exception
        L_0x007e:
            com.taobao.accs.utl.ALog$Level r9 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ all -> 0x007b }
            boolean r9 = com.taobao.accs.utl.ALog.isPrintLog(r9)     // Catch:{ all -> 0x007b }
            if (r9 == 0) goto L_0x00ac
            java.lang.String r9 = "MessageService"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x007b }
            java.lang.String r3 = "updateAccsMessage error,e--->["
            r2.<init>(r3)     // Catch:{ all -> 0x007b }
            r2.append(r8)     // Catch:{ all -> 0x007b }
            java.lang.String r3 = "],ex="
            r2.append(r3)     // Catch:{ all -> 0x007b }
            java.lang.StackTraceElement[] r3 = r8.getStackTrace()     // Catch:{ all -> 0x007b }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x007b }
            r2.append(r3)     // Catch:{ all -> 0x007b }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x007b }
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x007b }
            com.taobao.accs.utl.ALog.e(r9, r2, r1)     // Catch:{ all -> 0x007b }
        L_0x00ac:
            com.taobao.accs.utl.UTMini r1 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ all -> 0x007b }
            r2 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r3 = "accs.add_agoo_message"
            android.content.Context r9 = a     // Catch:{ all -> 0x007b }
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r9)     // Catch:{ all -> 0x007b }
            java.lang.String r5 = "updateAccsMessageFailed"
            java.lang.String r6 = r8.toString()     // Catch:{ all -> 0x007b }
            r1.commitEvent(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x007b }
            if (r0 == 0) goto L_0x00cb
            r0.close()
            return
        L_0x00cb:
            return
        L_0x00cc:
            if (r0 == 0) goto L_0x00d1
            r0.close()
        L_0x00d1:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.a(java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00aa A[Catch:{ all -> 0x00ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00f7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r11, java.lang.String r12, java.lang.String r13) {
        /*
            r10 = this;
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.I
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            r1 = 0
            if (r0 == 0) goto L_0x0033
            java.lang.String r0 = "MessageService"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "addAccsMessage sqlite3--->["
            r2.<init>(r3)
            r2.append(r11)
            java.lang.String r3 = ",message="
            r2.append(r3)
            r2.append(r12)
            java.lang.String r3 = ",state="
            r2.append(r3)
            r2.append(r13)
            java.lang.String r3 = "]"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r1]
            com.taobao.accs.utl.ALog.i(r0, r2, r3)
        L_0x0033:
            r0 = 0
            boolean r2 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x00a0, all -> 0x009d }
            if (r2 != 0) goto L_0x009c
            boolean r2 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Throwable -> 0x00a0, all -> 0x009d }
            if (r2 == 0) goto L_0x0041
            goto L_0x009c
        L_0x0041:
            android.database.sqlite.SQLiteOpenHelper r2 = r10.b     // Catch:{ Throwable -> 0x00a0, all -> 0x009d }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x00a0, all -> 0x009d }
            if (r2 != 0) goto L_0x004f
            if (r2 == 0) goto L_0x004e
            r2.close()
        L_0x004e:
            return
        L_0x004f:
            java.lang.String r3 = "select count(1) from accs_message where id = ?"
            r4 = 1
            java.lang.String[] r5 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x0098, all -> 0x0094 }
            r5[r1] = r11     // Catch:{ Throwable -> 0x0098, all -> 0x0094 }
            android.database.Cursor r3 = r2.rawQuery(r3, r5)     // Catch:{ Throwable -> 0x0098, all -> 0x0094 }
            if (r3 == 0) goto L_0x007a
            boolean r0 = r3.moveToFirst()     // Catch:{ Throwable -> 0x0078, all -> 0x0076 }
            if (r0 == 0) goto L_0x007a
            int r0 = r3.getInt(r1)     // Catch:{ Throwable -> 0x0078, all -> 0x0076 }
            if (r0 <= 0) goto L_0x007a
            r3.close()     // Catch:{ Throwable -> 0x0078, all -> 0x0076 }
            if (r3 == 0) goto L_0x0070
            r3.close()
        L_0x0070:
            if (r2 == 0) goto L_0x0075
            r2.close()
        L_0x0075:
            return
        L_0x0076:
            r11 = move-exception
            goto L_0x0096
        L_0x0078:
            r11 = move-exception
            goto L_0x009a
        L_0x007a:
            java.lang.String r0 = "INSERT INTO accs_message VALUES(?,?,?,date('now'))"
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0078, all -> 0x0076 }
            r5[r1] = r11     // Catch:{ Throwable -> 0x0078, all -> 0x0076 }
            r5[r4] = r13     // Catch:{ Throwable -> 0x0078, all -> 0x0076 }
            r11 = 2
            r5[r11] = r12     // Catch:{ Throwable -> 0x0078, all -> 0x0076 }
            r2.execSQL(r0, r5)     // Catch:{ Throwable -> 0x0078, all -> 0x0076 }
            if (r3 == 0) goto L_0x008e
            r3.close()
        L_0x008e:
            if (r2 == 0) goto L_0x00ee
            r2.close()
            return
        L_0x0094:
            r11 = move-exception
            r3 = r0
        L_0x0096:
            r0 = r2
            goto L_0x00f0
        L_0x0098:
            r11 = move-exception
            r3 = r0
        L_0x009a:
            r0 = r2
            goto L_0x00a2
        L_0x009c:
            return
        L_0x009d:
            r11 = move-exception
            r3 = r0
            goto L_0x00f0
        L_0x00a0:
            r11 = move-exception
            r3 = r0
        L_0x00a2:
            com.taobao.accs.utl.ALog$Level r12 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ all -> 0x00ef }
            boolean r12 = com.taobao.accs.utl.ALog.isPrintLog(r12)     // Catch:{ all -> 0x00ef }
            if (r12 == 0) goto L_0x00cb
            java.lang.String r12 = "MessageService"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ef }
            java.lang.String r2 = "addAccsMessage error,e--->["
            r13.<init>(r2)     // Catch:{ all -> 0x00ef }
            r13.append(r11)     // Catch:{ all -> 0x00ef }
            java.lang.String r2 = "],ex="
            r13.append(r2)     // Catch:{ all -> 0x00ef }
            java.lang.String r2 = a(r11)     // Catch:{ all -> 0x00ef }
            r13.append(r2)     // Catch:{ all -> 0x00ef }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x00ef }
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00ef }
            com.taobao.accs.utl.ALog.e(r12, r13, r1)     // Catch:{ all -> 0x00ef }
        L_0x00cb:
            com.taobao.accs.utl.UTMini r4 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ all -> 0x00ef }
            r5 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r6 = "accs.add_agoo_message"
            android.content.Context r12 = a     // Catch:{ all -> 0x00ef }
            java.lang.String r7 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r12)     // Catch:{ all -> 0x00ef }
            java.lang.String r8 = "addAccsMessageFailed"
            java.lang.String r9 = r11.toString()     // Catch:{ all -> 0x00ef }
            r4.commitEvent(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x00ef }
            if (r3 == 0) goto L_0x00e8
            r3.close()
        L_0x00e8:
            if (r0 == 0) goto L_0x00ee
            r0.close()
            return
        L_0x00ee:
            return
        L_0x00ef:
            r11 = move-exception
        L_0x00f0:
            if (r3 == 0) goto L_0x00f5
            r3.close()
        L_0x00f5:
            if (r0 == 0) goto L_0x00fa
            r0.close()
        L_0x00fa:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    private static String a(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                StringBuilder sb = new StringBuilder();
                sb.append(stackTraceElement.toString());
                sb.append("\n");
                stringBuffer.append(sb.toString());
            }
        }
        return stringBuffer.toString();
    }

    public final void a(String str, String str2, String str3, int i) {
        b(str, str2, str3, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x014c A[Catch:{ all -> 0x0141 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0180 A[SYNTHETIC, Splitter:B:52:0x0180] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x019b A[SYNTHETIC, Splitter:B:60:0x019b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.lang.String r10, java.lang.String r11, java.lang.String r12, int r13) {
        /*
            r9 = this;
            java.lang.String r0 = "MessageService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "add sqlite3--->["
            r1.<init>(r2)
            r1.append(r10)
            java.lang.String r2 = "]"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.taobao.accs.utl.ALog.d(r0, r1, r3)
            r0 = 0
            boolean r1 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x0143 }
            r3 = -1
            if (r1 == 0) goto L_0x0027
            java.lang.String r11 = ""
            r1 = -1
            goto L_0x002b
        L_0x0027:
            int r1 = r11.hashCode()     // Catch:{ Throwable -> 0x0143 }
        L_0x002b:
            boolean r4 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Throwable -> 0x0143 }
            if (r4 == 0) goto L_0x0033
            java.lang.String r12 = ""
        L_0x0033:
            java.util.Map<java.lang.String, java.lang.Integer> r4 = c     // Catch:{ Throwable -> 0x0143 }
            boolean r4 = r4.containsKey(r10)     // Catch:{ Throwable -> 0x0143 }
            if (r4 != 0) goto L_0x006f
            java.util.Map<java.lang.String, java.lang.Integer> r4 = c     // Catch:{ Throwable -> 0x0143 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x0143 }
            r4.put(r10, r5)     // Catch:{ Throwable -> 0x0143 }
            com.taobao.accs.utl.ALog$Level r4 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0143 }
            boolean r4 = com.taobao.accs.utl.ALog.isPrintLog(r4)     // Catch:{ Throwable -> 0x0143 }
            if (r4 == 0) goto L_0x006f
            java.lang.String r4 = "MessageService"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = "addMessage,messageId="
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0143 }
            r5.append(r10)     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = ",messageStores＝"
            r5.append(r6)     // Catch:{ Throwable -> 0x0143 }
            java.util.Map<java.lang.String, java.lang.Integer> r6 = c     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0143 }
            r5.append(r6)     // Catch:{ Throwable -> 0x0143 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0143 }
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0143 }
            com.taobao.accs.utl.ALog.i(r4, r5, r6)     // Catch:{ Throwable -> 0x0143 }
        L_0x006f:
            android.database.sqlite.SQLiteOpenHelper r4 = r9.b     // Catch:{ Throwable -> 0x0143 }
            android.database.sqlite.SQLiteDatabase r4 = r4.getWritableDatabase()     // Catch:{ Throwable -> 0x0143 }
            if (r4 != 0) goto L_0x00ba
            if (r4 == 0) goto L_0x00b9
            r4.close()     // Catch:{ Throwable -> 0x007d }
            goto L_0x00b9
        L_0x007d:
            r10 = move-exception
            com.taobao.accs.utl.ALog$Level r11 = com.taobao.accs.utl.ALog.Level.E
            boolean r11 = com.taobao.accs.utl.ALog.isPrintLog(r11)
            if (r11 == 0) goto L_0x00a0
            java.lang.String r11 = "MessageService"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "addMessage,db.close(),error,e--->["
            r12.<init>(r13)
            r12.append(r10)
            java.lang.String r13 = "]"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            java.lang.Object[] r13 = new java.lang.Object[r2]
            com.taobao.accs.utl.ALog.e(r11, r12, r13)
        L_0x00a0:
            com.taobao.accs.utl.UTMini r0 = com.taobao.accs.utl.UTMini.getInstance()
            r1 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r2 = "accs.add_agoo_message"
            android.content.Context r11 = a
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r11)
            java.lang.String r4 = "addMessageDBcloseFailed"
            java.lang.String r5 = r10.toString()
            r0.commitEvent(r1, r2, r3, r4, r5)
            return
        L_0x00b9:
            return
        L_0x00ba:
            java.lang.String r0 = "INSERT INTO message VALUES(?,?,?,?,?,?,?,?,?,date('now'))"
            r5 = 9
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r5[r2] = r10     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r10 = 1
            java.lang.Integer r6 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r5[r10] = r6     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r10 = 2
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r5[r10] = r1     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r10 = 3
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r5[r10] = r1     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r10 = 4
            r6 = -1
            java.lang.Long r1 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r5[r10] = r1     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r10 = 5
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r5[r10] = r1     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r10 = 6
            r5[r10] = r12     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r10 = 7
            r5[r10] = r11     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r10 = 8
            java.lang.Integer r11 = java.lang.Integer.valueOf(r13)     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r5[r10] = r11     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r4.execSQL(r0, r5)     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            if (r4 == 0) goto L_0x013a
            r4.close()     // Catch:{ Throwable -> 0x00fe }
            goto L_0x013a
        L_0x00fe:
            r10 = move-exception
            com.taobao.accs.utl.ALog$Level r11 = com.taobao.accs.utl.ALog.Level.E
            boolean r11 = com.taobao.accs.utl.ALog.isPrintLog(r11)
            if (r11 == 0) goto L_0x0121
            java.lang.String r11 = "MessageService"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "addMessage,db.close(),error,e--->["
            r12.<init>(r13)
        L_0x0110:
            r12.append(r10)
            java.lang.String r13 = "]"
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            java.lang.Object[] r13 = new java.lang.Object[r2]
            com.taobao.accs.utl.ALog.e(r11, r12, r13)
        L_0x0121:
            com.taobao.accs.utl.UTMini r0 = com.taobao.accs.utl.UTMini.getInstance()
            r1 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r2 = "accs.add_agoo_message"
            android.content.Context r11 = a
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r11)
            java.lang.String r4 = "addMessageDBcloseFailed"
            java.lang.String r5 = r10.toString()
            r0.commitEvent(r1, r2, r3, r4, r5)
            return
        L_0x013a:
            return
        L_0x013b:
            r10 = move-exception
            r0 = r4
            goto L_0x0199
        L_0x013e:
            r10 = move-exception
            r0 = r4
            goto L_0x0144
        L_0x0141:
            r10 = move-exception
            goto L_0x0199
        L_0x0143:
            r10 = move-exception
        L_0x0144:
            com.taobao.accs.utl.ALog$Level r11 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ all -> 0x0141 }
            boolean r11 = com.taobao.accs.utl.ALog.isPrintLog(r11)     // Catch:{ all -> 0x0141 }
            if (r11 == 0) goto L_0x0166
            java.lang.String r11 = "MessageService"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0141 }
            java.lang.String r13 = "addMessage error,e--->["
            r12.<init>(r13)     // Catch:{ all -> 0x0141 }
            r12.append(r10)     // Catch:{ all -> 0x0141 }
            java.lang.String r13 = "]"
            r12.append(r13)     // Catch:{ all -> 0x0141 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0141 }
            java.lang.Object[] r13 = new java.lang.Object[r2]     // Catch:{ all -> 0x0141 }
            com.taobao.accs.utl.ALog.e(r11, r12, r13)     // Catch:{ all -> 0x0141 }
        L_0x0166:
            com.taobao.accs.utl.UTMini r3 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ all -> 0x0141 }
            r4 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r5 = "accs.add_agoo_message"
            android.content.Context r11 = a     // Catch:{ all -> 0x0141 }
            java.lang.String r6 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r11)     // Catch:{ all -> 0x0141 }
            java.lang.String r7 = "addMessageFailed"
            java.lang.String r8 = r10.toString()     // Catch:{ all -> 0x0141 }
            r3.commitEvent(r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0141 }
            if (r0 == 0) goto L_0x0198
            r0.close()     // Catch:{ Throwable -> 0x0184 }
            goto L_0x0198
        L_0x0184:
            r10 = move-exception
            com.taobao.accs.utl.ALog$Level r11 = com.taobao.accs.utl.ALog.Level.E
            boolean r11 = com.taobao.accs.utl.ALog.isPrintLog(r11)
            if (r11 == 0) goto L_0x0121
            java.lang.String r11 = "MessageService"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "addMessage,db.close(),error,e--->["
            r12.<init>(r13)
            goto L_0x0110
        L_0x0198:
            return
        L_0x0199:
            if (r0 == 0) goto L_0x01da
            r0.close()     // Catch:{ Throwable -> 0x019f }
            goto L_0x01da
        L_0x019f:
            r11 = move-exception
            com.taobao.accs.utl.ALog$Level r12 = com.taobao.accs.utl.ALog.Level.E
            boolean r12 = com.taobao.accs.utl.ALog.isPrintLog(r12)
            if (r12 == 0) goto L_0x01c2
            java.lang.String r12 = "MessageService"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r0 = "addMessage,db.close(),error,e--->["
            r13.<init>(r0)
            r13.append(r11)
            java.lang.String r0 = "]"
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            java.lang.Object[] r0 = new java.lang.Object[r2]
            com.taobao.accs.utl.ALog.e(r12, r13, r0)
        L_0x01c2:
            com.taobao.accs.utl.UTMini r1 = com.taobao.accs.utl.UTMini.getInstance()
            r2 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r3 = "accs.add_agoo_message"
            android.content.Context r12 = a
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r12)
            java.lang.String r5 = "addMessageDBcloseFailed"
            java.lang.String r6 = r11.toString()
            r1.commitEvent(r2, r3, r4, r5, r6)
        L_0x01da:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.b(java.lang.String, java.lang.String, java.lang.String, int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x003a A[SYNTHETIC, Splitter:B:28:0x003a] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0043 A[SYNTHETIC, Splitter:B:35:0x0043] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r6 = this;
            r0 = 0
            android.database.sqlite.SQLiteOpenHelper r1 = r6.b     // Catch:{ Throwable -> 0x002a, all -> 0x0025 }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Throwable -> 0x002a, all -> 0x0025 }
            if (r1 != 0) goto L_0x0011
            if (r1 == 0) goto L_0x0010
            r1.close()     // Catch:{ Throwable -> 0x000f }
            goto L_0x0010
        L_0x000f:
            return
        L_0x0010:
            return
        L_0x0011:
            java.lang.String r0 = "delete from message where create_time< date('now','-7 day') and state=1"
            r1.execSQL(r0)     // Catch:{ Throwable -> 0x0023 }
            java.lang.String r0 = "delete from accs_message where create_time< date('now','-1 day') "
            r1.execSQL(r0)     // Catch:{ Throwable -> 0x0023 }
            if (r1 == 0) goto L_0x0022
            r1.close()     // Catch:{ Throwable -> 0x0021 }
            goto L_0x0022
        L_0x0021:
            return
        L_0x0022:
            return
        L_0x0023:
            r0 = move-exception
            goto L_0x002e
        L_0x0025:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x0041
        L_0x002a:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x002e:
            java.lang.String r2 = "MessageService"
            java.lang.String r3 = "deleteCacheMessage sql Throwable"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0040 }
            com.taobao.accs.utl.ALog.e(r2, r3, r0, r4)     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x003f
            r1.close()     // Catch:{ Throwable -> 0x003e }
            goto L_0x003f
        L_0x003e:
            return
        L_0x003f:
            return
        L_0x0040:
            r0 = move-exception
        L_0x0041:
            if (r1 == 0) goto L_0x0046
            r1.close()     // Catch:{ Throwable -> 0x0046 }
        L_0x0046:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.a():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0108, code lost:
        if (com.taobao.accs.utl.ALog.isPrintLog(com.taobao.accs.utl.ALog.Level.E) == false) goto L_0x0164;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x010a, code lost:
        com.taobao.accs.utl.ALog.e("MessageService", "getUnReportMsg close cursor or db, e: ".concat(java.lang.String.valueOf(r0)), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0121, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0122, code lost:
        r4 = null;
        r0 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0125, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0126, code lost:
        r4 = null;
        r0 = r3;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0161, code lost:
        if (com.taobao.accs.utl.ALog.isPrintLog(com.taobao.accs.utl.ALog.Level.E) == false) goto L_0x0164;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0121 A[ExcHandler: all (r3v9 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:12:0x002b] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x013c A[Catch:{ all -> 0x0165 }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x014f A[SYNTHETIC, Splitter:B:69:0x014f] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0157 A[Catch:{ Throwable -> 0x0153 }] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0168 A[SYNTHETIC, Splitter:B:80:0x0168] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0170 A[Catch:{ Throwable -> 0x016c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.ArrayList<org.android.agoo.common.MsgDO> b() {
        /*
            r15 = this;
            r0 = 0
            r1 = 0
            android.database.sqlite.SQLiteOpenHelper r2 = r15.b     // Catch:{ Throwable -> 0x012f, all -> 0x012a }
            android.database.sqlite.SQLiteDatabase r2 = r2.getReadableDatabase()     // Catch:{ Throwable -> 0x012f, all -> 0x012a }
            if (r2 != 0) goto L_0x002b
            if (r2 == 0) goto L_0x002a
            r2.close()     // Catch:{ Throwable -> 0x0010 }
            goto L_0x002a
        L_0x0010:
            r2 = move-exception
            com.taobao.accs.utl.ALog$Level r3 = com.taobao.accs.utl.ALog.Level.E
            boolean r3 = com.taobao.accs.utl.ALog.isPrintLog(r3)
            if (r3 == 0) goto L_0x002a
            java.lang.String r3 = "MessageService"
            java.lang.String r4 = "getUnReportMsg close cursor or db, e: "
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r2 = r4.concat(r2)
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.taobao.accs.utl.ALog.e(r3, r2, r1)
        L_0x002a:
            return r0
        L_0x002b:
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0125, all -> 0x0121 }
            r3.<init>()     // Catch:{ Throwable -> 0x0125, all -> 0x0121 }
            java.lang.String r4 = "select * from accs_message where state = ? or state = ? or state = ?"
            java.lang.String r5 = "0"
            java.lang.String r6 = "2"
            java.lang.String r7 = "3"
            java.lang.String[] r5 = new java.lang.String[]{r5, r6, r7}     // Catch:{ Throwable -> 0x011c, all -> 0x0121 }
            android.database.Cursor r4 = r2.rawQuery(r4, r5)     // Catch:{ Throwable -> 0x011c, all -> 0x0121 }
            if (r4 == 0) goto L_0x00f3
            java.lang.String r5 = "id"
            int r5 = r4.getColumnIndex(r5)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r6 = "state"
            int r6 = r4.getColumnIndex(r6)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r7 = "message"
            int r7 = r4.getColumnIndex(r7)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r8 = "create_time"
            int r8 = r4.getColumnIndex(r8)     // Catch:{ Throwable -> 0x00f1 }
        L_0x005b:
            boolean r9 = r4.moveToNext()     // Catch:{ Throwable -> 0x00f1 }
            if (r9 == 0) goto L_0x00f3
            java.lang.String r9 = r4.getString(r7)     // Catch:{ Throwable -> 0x00f1 }
            boolean r9 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x00f1 }
            if (r9 != 0) goto L_0x00f3
            java.lang.String r9 = r4.getString(r6)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r10 = r4.getString(r7)     // Catch:{ Throwable -> 0x00f1 }
            com.taobao.accs.utl.ALog$Level r11 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x00f1 }
            boolean r11 = com.taobao.accs.utl.ALog.isPrintLog(r11)     // Catch:{ Throwable -> 0x00f1 }
            if (r11 == 0) goto L_0x00b1
            java.lang.String r11 = "MessageService"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r13 = "state: "
            r12.<init>(r13)     // Catch:{ Throwable -> 0x00f1 }
            r12.append(r9)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r13 = " ,cursor.message:"
            r12.append(r13)     // Catch:{ Throwable -> 0x00f1 }
            r12.append(r10)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r13 = " ,cursor.id:"
            r12.append(r13)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r13 = r4.getString(r5)     // Catch:{ Throwable -> 0x00f1 }
            r12.append(r13)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r13 = " ,cursor.time:"
            r12.append(r13)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r13 = r4.getString(r8)     // Catch:{ Throwable -> 0x00f1 }
            r12.append(r13)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r12 = r12.toString()     // Catch:{ Throwable -> 0x00f1 }
            java.lang.Object[] r13 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00f1 }
            com.taobao.accs.utl.ALog.i(r11, r12, r13)     // Catch:{ Throwable -> 0x00f1 }
        L_0x00b1:
            java.lang.String r11 = "0"
            boolean r11 = android.text.TextUtils.equals(r11, r9)     // Catch:{ Throwable -> 0x00f1 }
            if (r11 == 0) goto L_0x00bc
            java.lang.String r9 = "4"
            goto L_0x00d3
        L_0x00bc:
            java.lang.String r11 = "2"
            boolean r11 = android.text.TextUtils.equals(r11, r9)     // Catch:{ Throwable -> 0x00f1 }
            if (r11 == 0) goto L_0x00c7
            java.lang.String r9 = "8"
            goto L_0x00d3
        L_0x00c7:
            java.lang.String r11 = "3"
            boolean r9 = android.text.TextUtils.equals(r11, r9)     // Catch:{ Throwable -> 0x00f1 }
            if (r9 == 0) goto L_0x00d2
            java.lang.String r9 = "9"
            goto L_0x00d3
        L_0x00d2:
            r9 = r0
        L_0x00d3:
            org.android.agoo.common.MsgDO r11 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x00f1 }
            r11.<init>()     // Catch:{ Throwable -> 0x00f1 }
            boolean r11 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x00f1 }
            if (r11 != 0) goto L_0x005b
            boolean r11 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x00f1 }
            if (r11 != 0) goto L_0x005b
            org.android.agoo.common.MsgDO r9 = b(r10, r9)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r10 = "cache"
            r9.e = r10     // Catch:{ Throwable -> 0x00f1 }
            r3.add(r9)     // Catch:{ Throwable -> 0x00f1 }
            goto L_0x005b
        L_0x00f1:
            r0 = move-exception
            goto L_0x0134
        L_0x00f3:
            if (r4 == 0) goto L_0x00fb
            r4.close()     // Catch:{ Throwable -> 0x00f9 }
            goto L_0x00fb
        L_0x00f9:
            r0 = move-exception
            goto L_0x0102
        L_0x00fb:
            if (r2 == 0) goto L_0x0164
            r2.close()     // Catch:{ Throwable -> 0x00f9 }
            goto L_0x0164
        L_0x0102:
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.E
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)
            if (r2 == 0) goto L_0x0164
        L_0x010a:
            java.lang.String r2 = "MessageService"
            java.lang.String r4 = "getUnReportMsg close cursor or db, e: "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r4.concat(r0)
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.taobao.accs.utl.ALog.e(r2, r0, r1)
            goto L_0x0164
        L_0x011c:
            r4 = move-exception
            r14 = r4
            r4 = r0
            r0 = r14
            goto L_0x0134
        L_0x0121:
            r3 = move-exception
            r4 = r0
            r0 = r3
            goto L_0x0166
        L_0x0125:
            r3 = move-exception
            r4 = r0
            r0 = r3
            r3 = r4
            goto L_0x0134
        L_0x012a:
            r2 = move-exception
            r4 = r0
            r0 = r2
            r2 = r4
            goto L_0x0166
        L_0x012f:
            r2 = move-exception
            r3 = r0
            r4 = r3
            r0 = r2
            r2 = r4
        L_0x0134:
            com.taobao.accs.utl.ALog$Level r5 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ all -> 0x0165 }
            boolean r5 = com.taobao.accs.utl.ALog.isPrintLog(r5)     // Catch:{ all -> 0x0165 }
            if (r5 == 0) goto L_0x014d
            java.lang.String r5 = "MessageService"
            java.lang.String r6 = "getUnReportMsg, e: "
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0165 }
            java.lang.String r0 = r6.concat(r0)     // Catch:{ all -> 0x0165 }
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch:{ all -> 0x0165 }
            com.taobao.accs.utl.ALog.e(r5, r0, r6)     // Catch:{ all -> 0x0165 }
        L_0x014d:
            if (r4 == 0) goto L_0x0155
            r4.close()     // Catch:{ Throwable -> 0x0153 }
            goto L_0x0155
        L_0x0153:
            r0 = move-exception
            goto L_0x015b
        L_0x0155:
            if (r2 == 0) goto L_0x0164
            r2.close()     // Catch:{ Throwable -> 0x0153 }
            goto L_0x0164
        L_0x015b:
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.E
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)
            if (r2 == 0) goto L_0x0164
            goto L_0x010a
        L_0x0164:
            return r3
        L_0x0165:
            r0 = move-exception
        L_0x0166:
            if (r4 == 0) goto L_0x016e
            r4.close()     // Catch:{ Throwable -> 0x016c }
            goto L_0x016e
        L_0x016c:
            r2 = move-exception
            goto L_0x0174
        L_0x016e:
            if (r2 == 0) goto L_0x018d
            r2.close()     // Catch:{ Throwable -> 0x016c }
            goto L_0x018d
        L_0x0174:
            com.taobao.accs.utl.ALog$Level r3 = com.taobao.accs.utl.ALog.Level.E
            boolean r3 = com.taobao.accs.utl.ALog.isPrintLog(r3)
            if (r3 == 0) goto L_0x018d
            java.lang.String r3 = "MessageService"
            java.lang.String r4 = "getUnReportMsg close cursor or db, e: "
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r2 = r4.concat(r2)
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.taobao.accs.utl.ALog.e(r3, r2, r1)
        L_0x018d:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.b():java.util.ArrayList");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x005b, code lost:
        if (r4 != null) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005d, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0063, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0065, code lost:
        r9 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0066, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0079, code lost:
        if (r4 == null) goto L_0x007c;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0065 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0002] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0069 A[SYNTHETIC, Splitter:B:42:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x006e A[Catch:{ Throwable -> 0x0071 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0076 A[SYNTHETIC, Splitter:B:52:0x0076] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
            java.util.Map<java.lang.String, java.lang.Integer> r2 = c     // Catch:{ Throwable -> 0x0072, all -> 0x0065 }
            boolean r2 = r2.containsKey(r9)     // Catch:{ Throwable -> 0x0072, all -> 0x0065 }
            r3 = 1
            if (r2 == 0) goto L_0x0026
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ Throwable -> 0x0072, all -> 0x0065 }
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ Throwable -> 0x0072, all -> 0x0065 }
            if (r2 == 0) goto L_0x0024
            java.lang.String r2 = "MessageService"
            java.lang.String r4 = "hasMessageDuplicate,msgid="
            java.lang.String r5 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x0072, all -> 0x0065 }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ Throwable -> 0x0072, all -> 0x0065 }
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0072, all -> 0x0065 }
            com.taobao.accs.utl.ALog.e(r2, r4, r5)     // Catch:{ Throwable -> 0x0072, all -> 0x0065 }
        L_0x0024:
            r2 = 1
            goto L_0x0027
        L_0x0026:
            r2 = 0
        L_0x0027:
            android.database.sqlite.SQLiteOpenHelper r4 = r8.b     // Catch:{ Throwable -> 0x0063, all -> 0x0065 }
            android.database.sqlite.SQLiteDatabase r4 = r4.getReadableDatabase()     // Catch:{ Throwable -> 0x0063, all -> 0x0065 }
            if (r4 != 0) goto L_0x0035
            if (r4 == 0) goto L_0x0034
            r4.close()     // Catch:{ Throwable -> 0x0034 }
        L_0x0034:
            return r2
        L_0x0035:
            java.lang.String r5 = "select count(1) from message where id = ?"
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x0074, all -> 0x0061 }
            r6[r1] = r9     // Catch:{ Throwable -> 0x0074, all -> 0x0061 }
            android.database.Cursor r9 = r4.rawQuery(r5, r6)     // Catch:{ Throwable -> 0x0074, all -> 0x0061 }
            if (r9 == 0) goto L_0x0056
            boolean r0 = r9.moveToFirst()     // Catch:{ Throwable -> 0x0054, all -> 0x004f }
            if (r0 == 0) goto L_0x0056
            int r0 = r9.getInt(r1)     // Catch:{ Throwable -> 0x0054, all -> 0x004f }
            if (r0 <= 0) goto L_0x0056
            r2 = 1
            goto L_0x0056
        L_0x004f:
            r0 = move-exception
            r7 = r0
            r0 = r9
            r9 = r7
            goto L_0x0067
        L_0x0054:
            r0 = r9
            goto L_0x0074
        L_0x0056:
            if (r9 == 0) goto L_0x005b
            r9.close()     // Catch:{ Throwable -> 0x007c }
        L_0x005b:
            if (r4 == 0) goto L_0x007c
        L_0x005d:
            r4.close()     // Catch:{ Throwable -> 0x007c }
            goto L_0x007c
        L_0x0061:
            r9 = move-exception
            goto L_0x0067
        L_0x0063:
            r4 = r0
            goto L_0x0074
        L_0x0065:
            r9 = move-exception
            r4 = r0
        L_0x0067:
            if (r0 == 0) goto L_0x006c
            r0.close()     // Catch:{ Throwable -> 0x0071 }
        L_0x006c:
            if (r4 == 0) goto L_0x0071
            r4.close()     // Catch:{ Throwable -> 0x0071 }
        L_0x0071:
            throw r9
        L_0x0072:
            r4 = r0
            r2 = 0
        L_0x0074:
            if (r0 == 0) goto L_0x0079
            r0.close()     // Catch:{ Throwable -> 0x007c }
        L_0x0079:
            if (r4 == 0) goto L_0x007c
            goto L_0x005d
        L_0x007c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.a(java.lang.String):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x006d, code lost:
        if (r4 != null) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x006f, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0075, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0077, code lost:
        r8 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0078, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x008b, code lost:
        if (r4 == null) goto L_0x008e;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0077 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0002] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x007b A[SYNTHETIC, Splitter:B:44:0x007b] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0080 A[Catch:{ Throwable -> 0x0083 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0088 A[SYNTHETIC, Splitter:B:54:0x0088] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(java.lang.String r8, int r9) {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            java.util.Map<java.lang.String, java.lang.Integer> r2 = c     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            boolean r2 = r2.containsKey(r8)     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            r3 = 1
            if (r2 == 0) goto L_0x0032
            java.util.Map<java.lang.String, java.lang.Integer> r2 = c     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            boolean r2 = r2.containsValue(r4)     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            if (r2 == 0) goto L_0x0032
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            if (r2 == 0) goto L_0x0030
            java.lang.String r2 = "MessageService"
            java.lang.String r4 = "addMessage,messageStores hasMessageDuplicate,msgid="
            java.lang.String r5 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
            com.taobao.accs.utl.ALog.i(r2, r4, r5)     // Catch:{ Throwable -> 0x0084, all -> 0x0077 }
        L_0x0030:
            r2 = 1
            goto L_0x0033
        L_0x0032:
            r2 = 0
        L_0x0033:
            android.database.sqlite.SQLiteOpenHelper r4 = r7.b     // Catch:{ Throwable -> 0x0075, all -> 0x0077 }
            android.database.sqlite.SQLiteDatabase r4 = r4.getReadableDatabase()     // Catch:{ Throwable -> 0x0075, all -> 0x0077 }
            if (r4 != 0) goto L_0x0041
            if (r4 == 0) goto L_0x0040
            r4.close()     // Catch:{ Throwable -> 0x0040 }
        L_0x0040:
            return r2
        L_0x0041:
            java.lang.String r5 = "select count(1) from message where id = ? and body_code=? create_time< date('now','-1 day')"
            r6 = 2
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ Throwable -> 0x0086, all -> 0x0073 }
            r6[r1] = r8     // Catch:{ Throwable -> 0x0086, all -> 0x0073 }
            java.lang.String r8 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x0086, all -> 0x0073 }
            r6[r3] = r8     // Catch:{ Throwable -> 0x0086, all -> 0x0073 }
            android.database.Cursor r8 = r4.rawQuery(r5, r6)     // Catch:{ Throwable -> 0x0086, all -> 0x0073 }
            if (r8 == 0) goto L_0x0068
            boolean r9 = r8.moveToFirst()     // Catch:{ Throwable -> 0x0066, all -> 0x0062 }
            if (r9 == 0) goto L_0x0068
            int r9 = r8.getInt(r1)     // Catch:{ Throwable -> 0x0066, all -> 0x0062 }
            if (r9 <= 0) goto L_0x0068
            r2 = 1
            goto L_0x0068
        L_0x0062:
            r9 = move-exception
            r0 = r8
            r8 = r9
            goto L_0x0079
        L_0x0066:
            r0 = r8
            goto L_0x0086
        L_0x0068:
            if (r8 == 0) goto L_0x006d
            r8.close()     // Catch:{ Throwable -> 0x008e }
        L_0x006d:
            if (r4 == 0) goto L_0x008e
        L_0x006f:
            r4.close()     // Catch:{ Throwable -> 0x008e }
            goto L_0x008e
        L_0x0073:
            r8 = move-exception
            goto L_0x0079
        L_0x0075:
            r4 = r0
            goto L_0x0086
        L_0x0077:
            r8 = move-exception
            r4 = r0
        L_0x0079:
            if (r0 == 0) goto L_0x007e
            r0.close()     // Catch:{ Throwable -> 0x0083 }
        L_0x007e:
            if (r4 == 0) goto L_0x0083
            r4.close()     // Catch:{ Throwable -> 0x0083 }
        L_0x0083:
            throw r8
        L_0x0084:
            r4 = r0
            r2 = 0
        L_0x0086:
            if (r0 == 0) goto L_0x008b
            r0.close()     // Catch:{ Throwable -> 0x008e }
        L_0x008b:
            if (r4 == 0) goto L_0x008e
            goto L_0x006f
        L_0x008e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.a(java.lang.String, int):boolean");
    }

    private static MsgDO b(String str, String str2) {
        boolean z;
        String str3 = str;
        if (ALog.isPrintLog(Level.I)) {
            StringBuilder sb = new StringBuilder("msgRecevie,message--->[");
            sb.append(str3);
            sb.append("],utdid=");
            sb.append(AdapterUtilityImpl.getDeviceId(a));
            ALog.i("MessageService", sb.toString(), new Object[0]);
        }
        if (TextUtils.isEmpty(str)) {
            UTMini.getInstance().commitEvent(66002, "accs.dealMessage", AdapterUtilityImpl.getDeviceId(a), "message==null");
            if (ALog.isPrintLog(Level.I)) {
                StringBuilder sb2 = new StringBuilder("handleMessage message==null,utdid=");
                sb2.append(AdapterUtilityImpl.getDeviceId(a));
                ALog.i("MessageService", sb2.toString(), new Object[0]);
            }
            return null;
        }
        MsgDO msgDO = new MsgDO();
        try {
            JSONArray jSONArray = new JSONArray(str3);
            int length = jSONArray.length();
            new Bundle();
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();
            StringBuilder sb5 = new StringBuilder();
            String str4 = null;
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject != null) {
                    String string = jSONObject.getString(SuperId.BIT_1_MAIN_VOICE_ASSISTANT);
                    String string2 = jSONObject.getString("i");
                    String string3 = jSONObject.getString(SuperId.BIT_1_RQBXY);
                    long j = jSONObject.getLong("f");
                    sb3.append(string2);
                    if (!jSONObject.isNull(ProcessInfo.ALIAS_EXT)) {
                        str4 = jSONObject.getString(ProcessInfo.ALIAS_EXT);
                    }
                    int i2 = length - 1;
                    if (i < i2) {
                        sb3.append(",");
                    }
                    msgDO.a = string2;
                    msgDO.b = str4;
                    msgDO.e = "accs";
                    msgDO.f = "cache";
                    if (TextUtils.isEmpty(string3)) {
                        msgDO.d = "11";
                    } else if (TextUtils.isEmpty(string)) {
                        msgDO.d = "12";
                    } else if (j == -1) {
                        msgDO.d = "13";
                    } else if (!a(a, string)) {
                        ALog.d("MessageService", "ondata checkpackage is del,pack=".concat(String.valueOf(string)), new Object[0]);
                        UTMini.getInstance().commitEvent(66002, "accs.dealMessage", AdapterUtilityImpl.getDeviceId(a), "deletePack", string);
                        sb5.append(string);
                        sb4.append(string2);
                        msgDO.c = string;
                        if (i < i2) {
                            sb5.append(",");
                            sb4.append(",");
                        }
                    } else {
                        String string4 = a(j, msgDO).getString("encrypted");
                        if (!a.getPackageName().equals(string)) {
                            z = true;
                        } else if (TextUtils.equals(Integer.toString(0), string4) || TextUtils.equals(Integer.toString(4), string4)) {
                            z = false;
                        } else {
                            msgDO.d = "15";
                            ALog.e("MessageService", "error encrypted: ".concat(String.valueOf(string4)), new Object[0]);
                        }
                        msgDO.i = z;
                        if (!TextUtils.isEmpty(str2)) {
                            msgDO.l = str2;
                        }
                    }
                }
                String str5 = str2;
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            if (ALog.isPrintLog(Level.E)) {
                ALog.e("MessageService", "createMsg is error,e: ".concat(String.valueOf(th2)), new Object[0]);
            }
        }
        return msgDO;
    }

    private static boolean a(Context context, String str) {
        try {
            if (context.getPackageManager().getApplicationInfo(str, 0) != null) {
                return true;
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    private static Bundle a(long j, MsgDO msgDO) {
        Bundle bundle = new Bundle();
        try {
            char[] charArray = Long.toBinaryString(j).toCharArray();
            if (charArray != null && 8 <= charArray.length) {
                if (8 <= charArray.length) {
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(charArray[1]);
                    sb2.append(charArray[2]);
                    sb2.append(charArray[3]);
                    sb2.append(charArray[4]);
                    sb.append(Integer.parseInt(sb2.toString(), 2));
                    bundle.putString("encrypted", sb.toString());
                    if (charArray[6] == '1') {
                        bundle.putString("report", "1");
                        msgDO.j = "1";
                    }
                    if (charArray[7] == '1') {
                        bundle.putString("notify", "1");
                    }
                }
                if (9 <= charArray.length && charArray[8] == '1') {
                    bundle.putString("has_test", "1");
                }
                if (10 <= charArray.length && charArray[9] == '1') {
                    bundle.putString("duplicate", "1");
                }
                if (11 <= charArray.length && charArray[10] == '1') {
                    bundle.putInt("popup", 1);
                }
            }
        } catch (Throwable unused) {
        }
        return bundle;
    }
}
