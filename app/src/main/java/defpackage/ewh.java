package defpackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: ewh reason: default package */
/* compiled from: DataBaseOpenHelper */
public final class ewh extends SQLiteOpenHelper {
    private static String a = "hmt_analytics";
    private static int b = 1;
    private static Context c;

    /* renamed from: ewh$a */
    /* compiled from: DataBaseOpenHelper */
    static class a {
        /* access modifiers changed from: private */
        public static final ewh a = new ewh(0);
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    /* synthetic */ ewh(byte b2) {
        this();
    }

    private ewh() {
        super(c, a, null, b);
    }

    public static synchronized ewh a(Context context) {
        ewh a2;
        synchronized (ewh.class) {
            try {
                c = context.getApplicationContext();
                a2 = a.a;
            }
        }
        return a2;
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS hmtInfo (id integer primary key autoincrement, type varchar(64), info text)");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS reqInfo (id integer primary key autoincrement, type varchar(64), info text)");
    }
}
