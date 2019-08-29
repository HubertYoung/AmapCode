package defpackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.MessageDigest;
import tv.danmaku.ijk.media.player.misc.IMediaFormat;

/* renamed from: eug reason: default package */
/* compiled from: DatabaseSourceInfoStorage */
public final class eug extends SQLiteOpenHelper implements eui {
    private static final String[] a = {"_id", "url", "length", IMediaFormat.KEY_MIME};

    public eug(Context context) {
        super(context, "AndroidVideoCache.db", null, 1);
        etr.a(context);
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        etr.a(sQLiteDatabase);
        sQLiteDatabase.execSQL("CREATE TABLE SourceInfo (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,url TEXT NOT NULL,mime TEXT,length INTEGER);");
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        throw new IllegalStateException("Should not be called. There is no any migration");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x004e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final defpackage.etv a(java.lang.String r10) {
        /*
            r9 = this;
            defpackage.etr.a(r10)
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r9.getReadableDatabase()     // Catch:{ all -> 0x004a }
            java.lang.String r2 = "SourceInfo"
            java.lang.String[] r3 = a     // Catch:{ all -> 0x004a }
            java.lang.String r4 = "url=?"
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ all -> 0x004a }
            r6 = 0
            java.lang.String r7 = b(r10)     // Catch:{ all -> 0x004a }
            r5[r6] = r7     // Catch:{ all -> 0x004a }
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x004a }
            if (r1 == 0) goto L_0x0044
            boolean r2 = r1.moveToFirst()     // Catch:{ all -> 0x0042 }
            if (r2 != 0) goto L_0x0028
            goto L_0x0044
        L_0x0028:
            etv r0 = new etv     // Catch:{ all -> 0x0042 }
            java.lang.String r2 = "length"
            int r2 = r1.getColumnIndexOrThrow(r2)     // Catch:{ all -> 0x0042 }
            long r2 = r1.getLong(r2)     // Catch:{ all -> 0x0042 }
            java.lang.String r4 = "mime"
            int r4 = r1.getColumnIndexOrThrow(r4)     // Catch:{ all -> 0x0042 }
            java.lang.String r4 = r1.getString(r4)     // Catch:{ all -> 0x0042 }
            r0.<init>(r10, r2, r4)     // Catch:{ all -> 0x0042 }
            goto L_0x0044
        L_0x0042:
            r10 = move-exception
            goto L_0x004c
        L_0x0044:
            if (r1 == 0) goto L_0x0049
            r1.close()
        L_0x0049:
            return r0
        L_0x004a:
            r10 = move-exception
            r1 = r0
        L_0x004c:
            if (r1 == 0) goto L_0x0051
            r1.close()
        L_0x0051:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eug.a(java.lang.String):etv");
    }

    public final void a(String str, etv etv) {
        Object[] objArr = {str, etv};
        for (int i = 0; i < 2; i++) {
            if (objArr[i] == null) {
                throw new NullPointerException();
            }
        }
        boolean z = a(str) != null;
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", b(etv.a));
        contentValues.put("length", Long.valueOf(etv.b));
        contentValues.put(IMediaFormat.KEY_MIME, etv.c);
        if (z) {
            getWritableDatabase().update("SourceInfo", contentValues, "url=?", new String[]{b(str)});
        } else {
            getWritableDatabase().insert("SourceInfo", null, contentValues);
        }
    }

    private static String b(String str) {
        return (str == null || str.length() == 0) ? str : a(str.getBytes());
    }

    private static String a(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                byte b2 = b & 255;
                if (b2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(b2));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
