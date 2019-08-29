package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.drivecommon.map.db.NaviHistoryDao;
import com.amap.bundle.drivecommon.map.db.RdCameraCityInfoDao;
import com.amap.bundle.drivecommon.map.db.RdCameraPaymentItemDao;
import com.autonavi.annotation.MultipleImpl;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: sk reason: default package */
/* compiled from: DriveDaoMaster */
public class sk implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        NaviHistoryDao.a(sQLiteDatabase);
        RdCameraCityInfoDao.a(sQLiteDatabase);
        RdCameraPaymentItemDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        NaviHistoryDao.b(sQLiteDatabase);
        RdCameraCityInfoDao.b(sQLiteDatabase);
        RdCameraPaymentItemDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(NaviHistoryDao.class);
        arrayList.add(RdCameraCityInfoDao.class);
        arrayList.add(RdCameraPaymentItemDao.class);
        return arrayList;
    }
}
