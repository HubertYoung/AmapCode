package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.VehiclesLocalDao;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: cpr reason: default package */
/* compiled from: BaseMapDaoMasterImpl */
public class cpr implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        VehiclesLocalDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        VehiclesLocalDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(VehiclesLocalDao.class);
        return arrayList;
    }
}
