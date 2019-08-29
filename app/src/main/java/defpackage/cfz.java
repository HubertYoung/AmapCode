package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.H5WebStorageDao;

@MultipleImpl(xy.class)
/* renamed from: cfz reason: default package */
/* compiled from: WebViewOpenHelper */
public class cfz implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        if (i <= 1) {
            H5WebStorageDao.a(sQLiteDatabase);
        }
    }
}
