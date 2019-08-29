package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.TipItemDao;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: bsi reason: default package */
/* compiled from: SearchDaoMaster */
public class bsi implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        TipItemDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        TipItemDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(TipItemDao.class);
        return arrayList;
    }
}
