package com.amap.location.offline.b.b;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: StatisticsApTable */
public class e {
    public static String a = "id";
    public static String b = "originid";
    public static String c = "frequency";
    public static String d = "time";
    private static final String e;

    static {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS AP ( ");
        sb.append(a);
        sb.append(" LONG PRIMARY KEY, ");
        sb.append(b);
        sb.append(" LONG, ");
        sb.append(c);
        sb.append(" INTEGER DEFAULT 0, ");
        sb.append(d);
        sb.append(" LONG DEFAULT 0);");
        e = sb.toString();
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(e);
    }
}
