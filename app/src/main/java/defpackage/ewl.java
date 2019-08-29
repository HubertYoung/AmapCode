package defpackage;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: ewl reason: default package */
/* compiled from: HVTDataBaseManager */
public class ewl {
    private static ewl b;
    private static SQLiteOpenHelper c;
    private AtomicInteger a = new AtomicInteger();
    private SQLiteDatabase d;

    public static synchronized void a(SQLiteOpenHelper sQLiteOpenHelper) {
        synchronized (ewl.class) {
            if (b == null) {
                b = new ewl();
                c = sQLiteOpenHelper;
            }
        }
    }

    public static synchronized ewl a() {
        ewl ewl;
        synchronized (ewl.class) {
            try {
                if (b == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ewl.class.getSimpleName());
                    sb.append(" is not initialized, call initializeInstance(..) method first.");
                    throw new IllegalStateException(sb.toString());
                }
                ewl = b;
            }
        }
        return ewl;
    }

    public final synchronized SQLiteDatabase b() {
        try {
            if (this.a.incrementAndGet() == 1) {
                this.d = c.getWritableDatabase();
            }
        }
        return this.d;
    }

    public final synchronized void c() {
        if (this.a.decrementAndGet() == 0) {
            this.d.close();
        }
    }
}
