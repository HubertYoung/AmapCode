package com.amap.location.offline.b.b;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: LocationApTable */
public class a {
    public static String a = "id";
    public static String b = "lat";
    public static String c = "lng";
    public static String d = "acc";
    public static String e = "conf";
    public static String f = "timestamp";
    public static String g = "frequency";
    private static final String h;

    static {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS AP ( ");
        sb.append(a);
        sb.append(" LONG PRIMARY KEY, ");
        sb.append(b);
        sb.append(" INTEGER, ");
        sb.append(c);
        sb.append(" INTEGER, ");
        sb.append(d);
        sb.append(" INTEGER, ");
        sb.append(e);
        sb.append(" INTEGER, ");
        sb.append(f);
        sb.append(" LONG, ");
        sb.append(g);
        sb.append(" INTEGER DEFAULT 0);");
        h = sb.toString();
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(h);
    }
}
