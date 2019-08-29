package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.VehiclesLocalDao;

@MultipleImpl(xy.class)
/* renamed from: cpt reason: default package */
/* compiled from: BaseMapDbOpenHelperImpl */
public class cpt implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        AMapLog.e("BaseMapDbManagerImpl", sb.toString());
        if (i <= 30) {
            VehiclesLocalDao.a(sQLiteDatabase);
        }
    }
}
