package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.route.common.db.ShareBikeOrderDao;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: dzx reason: default package */
/* compiled from: RouteDaoMasterImpl */
public class dzx implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        ShareBikeOrderDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        ShareBikeOrderDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(ShareBikeOrderDao.class);
        return arrayList;
    }
}
