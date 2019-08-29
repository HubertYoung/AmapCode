package defpackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: ewm reason: default package */
/* compiled from: HVTDataBaseOpenHelper */
public final class ewm extends SQLiteOpenHelper {
    private static String a = "hvt_analytics";
    private static int b = 1;
    private static ewm c;

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    private ewm(Context context) {
        super(context, a, null, b);
    }

    public static synchronized ewm a(Context context) {
        ewm ewm;
        synchronized (ewm.class) {
            try {
                if (c == null) {
                    c = new ewm(context.getApplicationContext());
                }
                ewm = c;
            }
        }
        return ewm;
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS hvtInfo (id integer primary key autoincrement, type varchar(64), info text)");
    }
}
