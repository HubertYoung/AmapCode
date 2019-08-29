package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.RealTimeBusItemDao;
import com.autonavi.map.db.RideHistoryDao;
import com.autonavi.map.db.RouteHistoryDao;
import com.autonavi.map.db.RunHistoryDao;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: bux reason: default package */
/* compiled from: RouteDaoMaster */
public class bux implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        RealTimeBusItemDao.a(sQLiteDatabase);
        RideHistoryDao.a(sQLiteDatabase);
        RouteHistoryDao.a(sQLiteDatabase);
        RunHistoryDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        RealTimeBusItemDao.b(sQLiteDatabase);
        RideHistoryDao.b(sQLiteDatabase);
        RouteHistoryDao.b(sQLiteDatabase);
        RunHistoryDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(RealTimeBusItemDao.class);
        arrayList.add(RideHistoryDao.class);
        arrayList.add(RouteHistoryDao.class);
        arrayList.add(RunHistoryDao.class);
        return arrayList;
    }
}
