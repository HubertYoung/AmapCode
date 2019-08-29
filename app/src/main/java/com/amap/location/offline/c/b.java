package com.amap.location.offline.c;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: EventTable */
public class b {
    public static String a = "id";
    public static String b = "frequency";
    private static final String c;

    static {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ACL ( ");
        sb.append(a);
        sb.append(" TEXT PRIMARY KEY, ");
        sb.append(b);
        sb.append(" INTEGER DEFAULT 0);");
        c = sb.toString();
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(c);
    }
}
