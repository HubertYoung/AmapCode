package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.statistics.db.LogContentDao;
import com.autonavi.annotation.MultipleImpl;

@MultipleImpl(xy.class)
/* renamed from: afe reason: default package */
/* compiled from: LogOpenHelper */
public class afe implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        if (i <= 4) {
            LogContentDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE logcontent RENAME TO logcontent_temp");
            LogContentDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into logcontent(ID, PAGEID, BUTTONID, STEPID, TIME, SESSIONID, X, Y, PARAM) select ID, PAGEID, BUTTONID, STEPID, TIME, SESSIONID, X, Y, PARAM from logcontent_temp");
            sQLiteDatabase.execSQL("DROP TABLE logcontent_temp");
        }
    }
}
