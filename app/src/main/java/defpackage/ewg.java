package defpackage;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: ewg reason: default package */
/* compiled from: DataBaseManager */
public final class ewg {
    private static SQLiteOpenHelper a;
    private static SQLiteDatabase b;

    public static synchronized void a(SQLiteOpenHelper sQLiteOpenHelper) {
        synchronized (ewg.class) {
            a = sQLiteOpenHelper;
        }
    }

    public static synchronized SQLiteDatabase a() {
        SQLiteDatabase sQLiteDatabase;
        synchronized (ewg.class) {
            try {
                if (b == null || !b.isOpen()) {
                    b = a.getWritableDatabase();
                }
                sQLiteDatabase = b;
            }
        }
        return sQLiteDatabase;
    }

    public static synchronized void b() {
        synchronized (ewg.class) {
            if (b != null && b.isOpen()) {
                b.close();
            }
        }
    }
}
