package com.huawei.android.pushselfshow.richpush.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.a.b;

public class RichMediaProvider extends ContentProvider {
    private static final UriMatcher b;
    b a = null;

    public static class a {
        public static final Uri a = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/support_porvider");
        public static final Uri b = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/insert_bmp");
        public static final Uri c = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/update_bmp");
        public static final Uri d = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/query_bmp");
        public static final Uri e = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/insert_msg");
        public static final Uri f = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/query_msg");
        public static final Uri g = Uri.parse("content://com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider/delete_msg");
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        b = uriMatcher;
        uriMatcher.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "support_porvider", 1);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "insert_bmp", 2);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "update_bmp", 3);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "query_bmp", 4);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "insert_msg", 5);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "query_msg", 6);
        b.addURI("com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider", "delete_msg", 7);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        r12 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0061, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0062, code lost:
        r0 = r1;
        r9 = r13;
        r13 = r12;
        r12 = r9;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:9:0x002c, B:24:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0038 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:9:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x009a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.net.Uri a(android.database.sqlite.SQLiteDatabase r11, java.lang.String r12, android.content.ContentValues r13, android.net.Uri r14) {
        /*
            r10 = this;
            java.lang.String r0 = "PushSelfShowLog_RichMediaProvider"
            java.lang.String r1 = "enter insertToDb, table is:"
            java.lang.String r2 = java.lang.String.valueOf(r12)
            java.lang.String r1 = r1.concat(r2)
            com.huawei.android.pushagent.a.a.c.a(r0, r1)
            r0 = 0
            if (r11 != 0) goto L_0x001a
            java.lang.String r11 = "PushSelfShowLog_RichMediaProvider"
            java.lang.String r12 = "db is null"
            com.huawei.android.pushagent.a.a.c.d(r11, r12)
            return r0
        L_0x001a:
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r11
            r2 = r12
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0074 }
            if (r1 != 0) goto L_0x003f
            java.lang.String r12 = "PushSelfShowLog_RichMediaProvider"
            java.lang.String r13 = "cursor is null"
            com.huawei.android.pushagent.a.a.c.d(r12, r13)     // Catch:{ Exception -> 0x003b, all -> 0x0038 }
            if (r1 == 0) goto L_0x0034
            r1.close()
        L_0x0034:
            r11.close()
            return r0
        L_0x0038:
            r12 = move-exception
            goto L_0x0098
        L_0x003b:
            r12 = move-exception
            r13 = r0
            r0 = r1
            goto L_0x0076
        L_0x003f:
            int r2 = r1.getCount()     // Catch:{ Exception -> 0x003b, all -> 0x0038 }
            r3 = 1000(0x3e8, float:1.401E-42)
            if (r2 >= r3) goto L_0x0067
            long r12 = r11.insert(r12, r0, r13)     // Catch:{ Exception -> 0x003b, all -> 0x0038 }
            r2 = 0
            int r2 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0067
            android.net.Uri r12 = android.content.ContentUris.withAppendedId(r14, r12)     // Catch:{ Exception -> 0x003b, all -> 0x0038 }
            android.content.Context r13 = r10.getContext()     // Catch:{ Exception -> 0x0061, all -> 0x0038 }
            android.content.ContentResolver r13 = r13.getContentResolver()     // Catch:{ Exception -> 0x0061, all -> 0x0038 }
            r13.notifyChange(r12, r0)     // Catch:{ Exception -> 0x0061, all -> 0x0038 }
            goto L_0x0068
        L_0x0061:
            r13 = move-exception
            r0 = r1
            r9 = r13
            r13 = r12
            r12 = r9
            goto L_0x0076
        L_0x0067:
            r12 = r0
        L_0x0068:
            if (r1 == 0) goto L_0x006d
            r1.close()
        L_0x006d:
            r11.close()
            goto L_0x0088
        L_0x0071:
            r12 = move-exception
            r1 = r0
            goto L_0x0098
        L_0x0074:
            r12 = move-exception
            r13 = r0
        L_0x0076:
            java.lang.String r14 = "PushSelfShowLog_RichMediaProvider"
            java.lang.String r1 = r12.toString()     // Catch:{ all -> 0x0071 }
            com.huawei.android.pushagent.a.a.c.c(r14, r1, r12)     // Catch:{ all -> 0x0071 }
            if (r0 == 0) goto L_0x0084
            r0.close()
        L_0x0084:
            r11.close()
            r12 = r13
        L_0x0088:
            java.lang.String r11 = "PushSelfShowLog_RichMediaProvider"
            java.lang.String r13 = "resultUri is:"
            java.lang.String r14 = java.lang.String.valueOf(r12)
            java.lang.String r13 = r13.concat(r14)
            com.huawei.android.pushagent.a.a.c.a(r11, r13)
            return r12
        L_0x0098:
            if (r1 == 0) goto L_0x009d
            r1.close()
        L_0x009d:
            r11.close()
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a(android.database.sqlite.SQLiteDatabase, java.lang.String, android.content.ContentValues, android.net.Uri):android.net.Uri");
    }

    private boolean a(String str) {
        if (str == null || str.length() == 0 || !str.contains("'")) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" can be reject, should check sql");
        c.d("PushSelfShowLog_RichMediaProvider", sb.toString());
        return true;
    }

    private boolean a(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return false;
        }
        for (String a2 : strArr) {
            if (a(a2)) {
                return true;
            }
        }
        return false;
    }

    public int delete(Uri uri, String str, String[] strArr) {
        int i;
        String str2;
        String str3;
        int match = b.match(uri);
        StringBuilder sb = new StringBuilder("uri is:");
        sb.append(uri);
        sb.append(",match result: ");
        sb.append(match);
        c.a("PushSelfShowLog_RichMediaProvider", sb.toString());
        if (this.a == null) {
            str2 = "PushSelfShowLog_RichMediaProvider";
            str3 = "dbHelper is null";
        } else if (match != 7) {
            str2 = "PushSelfShowLog_RichMediaProvider";
            str3 = "uri not match!";
        } else {
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            if (writableDatabase == null) {
                str2 = "PushSelfShowLog_RichMediaProvider";
                str3 = "db is null";
            } else {
                try {
                    i = writableDatabase.delete("pushmsg", "_id = ?", strArr);
                    try {
                        getContext().getContentResolver().notifyChange(uri, null);
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Exception e2) {
                    e = e2;
                    i = 0;
                    try {
                        c.c("PushSelfShowLog_RichMediaProvider", e.toString(), e);
                        writableDatabase.close();
                        return i;
                    } catch (Throwable th) {
                        writableDatabase.close();
                        throw th;
                    }
                }
                writableDatabase.close();
                return i;
            }
        }
        c.d(str2, str3);
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase writableDatabase;
        String str;
        String str2;
        String str3;
        int match = b.match(uri);
        StringBuilder sb = new StringBuilder("uri is:");
        sb.append(uri);
        sb.append(",match result: ");
        sb.append(match);
        c.a("PushSelfShowLog_RichMediaProvider", sb.toString());
        if (this.a == null) {
            str2 = "PushSelfShowLog_RichMediaProvider";
            str3 = "dbHelper is null";
        } else {
            if (match == 2) {
                writableDatabase = this.a.getWritableDatabase();
                str = "notify";
            } else if (match != 5) {
                str2 = "PushSelfShowLog_RichMediaProvider";
                str3 = "uri not match!";
            } else {
                writableDatabase = this.a.getWritableDatabase();
                str = "pushmsg";
            }
            return a(writableDatabase, str, contentValues, uri);
        }
        c.d(str2, str3);
        return null;
    }

    public boolean onCreate() {
        c.a("PushSelfShowLog_RichMediaProvider", "onCreate");
        this.a = b.a(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String str3;
        String sb;
        Uri uri2 = uri;
        String str4 = str;
        if (a(str4) || a(strArr)) {
            str3 = "PushSelfShowLog_RichMediaProvider";
            StringBuilder sb2 = new StringBuilder("in query selection:");
            sb2.append(str4);
            sb2.append(" or projection is invalied");
            sb = sb2.toString();
        } else {
            int match = b.match(uri2);
            StringBuilder sb3 = new StringBuilder("uri is:");
            sb3.append(uri2);
            sb3.append(",match result: ");
            sb3.append(match);
            c.a("PushSelfShowLog_RichMediaProvider", sb3.toString());
            if (this.a == null) {
                str3 = "PushSelfShowLog_RichMediaProvider";
                sb = "dbHelper is null";
            } else {
                SQLiteDatabase readableDatabase = this.a.getReadableDatabase();
                if (readableDatabase == null) {
                    str3 = "PushSelfShowLog_RichMediaProvider";
                    sb = "db is null";
                } else if (match == 1) {
                    MatrixCursor matrixCursor = new MatrixCursor(new String[]{"isSupport"});
                    matrixCursor.addRow(new Integer[]{Integer.valueOf(1)});
                    return matrixCursor;
                } else if (match == 4) {
                    try {
                        return readableDatabase.query("notify", new String[]{"bmp"}, "url = ?", strArr2, null, null, str2, null);
                    } catch (Exception e) {
                        Exception exc = e;
                        c.c("PushSelfShowLog_RichMediaProvider", exc.toString(), exc);
                        return null;
                    }
                } else if (match != 6) {
                    c.d("PushSelfShowLog_RichMediaProvider", "uri not match!");
                    return null;
                } else {
                    try {
                        return readableDatabase.rawQuery("SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp  FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url and pushmsg.url = ? order by pushmsg._id desc limit 1000;", strArr2);
                    } catch (Exception e2) {
                        c.c("PushSelfShowLog_RichMediaProvider", e2.toString(), e2);
                        return null;
                    }
                }
            }
        }
        c.d(str3, sb);
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int i;
        String str2;
        String str3;
        int match = b.match(uri);
        StringBuilder sb = new StringBuilder("uri is:");
        sb.append(uri);
        sb.append(",match result: ");
        sb.append(match);
        c.a("PushSelfShowLog_RichMediaProvider", sb.toString());
        if (this.a == null) {
            str2 = "PushSelfShowLog_RichMediaProvider";
            str3 = "dbHelper is null";
        } else if (match != 3) {
            str2 = "PushSelfShowLog_RichMediaProvider";
            str3 = "uri not match!";
        } else {
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            if (writableDatabase == null) {
                str2 = "PushSelfShowLog_RichMediaProvider";
                str3 = "db is null";
            } else {
                try {
                    i = writableDatabase.update("notify", contentValues, "url = ?", strArr);
                    try {
                        getContext().getContentResolver().notifyChange(uri, null);
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Exception e2) {
                    e = e2;
                    i = 0;
                    try {
                        c.c("PushSelfShowLog_RichMediaProvider", e.toString(), e);
                        writableDatabase.close();
                        return i;
                    } catch (Throwable th) {
                        writableDatabase.close();
                        throw th;
                    }
                }
                writableDatabase.close();
                return i;
            }
        }
        c.d(str2, str3);
        return 0;
    }
}
