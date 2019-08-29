package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.H5WebStorageDao;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: cfx reason: default package */
/* compiled from: WebViewDaoMaster */
public class cfx implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        H5WebStorageDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        H5WebStorageDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(H5WebStorageDao.class);
        return arrayList;
    }
}
