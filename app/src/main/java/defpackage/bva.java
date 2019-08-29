package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.SavePointDao;
import com.autonavi.map.db.SaveRouteDao;
import com.autonavi.map.db.SaveSyncActionDao;
import com.autonavi.map.db.SaveTagDao;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: bva reason: default package */
/* compiled from: SaveDaoMaster */
public class bva implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        SavePointDao.a(sQLiteDatabase);
        SaveRouteDao.a(sQLiteDatabase);
        SaveSyncActionDao.a(sQLiteDatabase);
        SaveTagDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        SavePointDao.b(sQLiteDatabase);
        SaveRouteDao.b(sQLiteDatabase);
        SaveSyncActionDao.b(sQLiteDatabase);
        SaveTagDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(SavePointDao.class);
        arrayList.add(SaveRouteDao.class);
        arrayList.add(SaveSyncActionDao.class);
        arrayList.add(SaveTagDao.class);
        return arrayList;
    }
}
