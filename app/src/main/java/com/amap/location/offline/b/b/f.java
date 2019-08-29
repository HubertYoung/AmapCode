package com.amap.location.offline.b.b;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: StatisticsClTable */
public class f {
    public static String a = "id";
    public static String b = "originid";
    public static String c = "frequency";
    public static String d = "time";
    private static final String e;

    static {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS CL ( ");
        sb.append(a);
        sb.append(" LONG PRIMARY KEY, ");
        sb.append(b);
        sb.append(" TEXT, ");
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
