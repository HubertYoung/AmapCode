package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.statistics.db.LogContentDao;
import com.autonavi.annotation.MultipleImpl;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: afc reason: default package */
/* compiled from: LogDaoMaster */
public class afc implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        LogContentDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        LogContentDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(LogContentDao.class);
        return arrayList;
    }
}
