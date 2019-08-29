package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.carowner.db.CarOwnerInfoDao;
import com.autonavi.map.carowner.db.CarOwnerInformationDao;
import com.autonavi.map.carowner.db.CarOwnerReminderMsgDao;
import com.autonavi.map.db.VehiclesDao;
import com.autonavi.map.db.VehiclesReminderMsgDao;
import de.greenrobot.dao.AbstractDao;
import java.util.ArrayList;
import java.util.List;

@MultipleImpl(xw.class)
/* renamed from: bre reason: default package */
/* compiled from: CarOwnerDaoMaster */
public class bre implements xw {
    public final void a(SQLiteDatabase sQLiteDatabase) {
        CarOwnerInfoDao.a(sQLiteDatabase);
        CarOwnerInformationDao.a(sQLiteDatabase);
        CarOwnerReminderMsgDao.a(sQLiteDatabase);
        VehiclesDao.a(sQLiteDatabase);
        VehiclesReminderMsgDao.a(sQLiteDatabase);
    }

    public final void b(SQLiteDatabase sQLiteDatabase) {
        CarOwnerInfoDao.b(sQLiteDatabase);
        CarOwnerInformationDao.b(sQLiteDatabase);
        CarOwnerReminderMsgDao.b(sQLiteDatabase);
        VehiclesDao.b(sQLiteDatabase);
        VehiclesReminderMsgDao.b(sQLiteDatabase);
    }

    public final List<Class<? extends AbstractDao<?, ?>>> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(CarOwnerInfoDao.class);
        arrayList.add(CarOwnerInformationDao.class);
        arrayList.add(CarOwnerReminderMsgDao.class);
        arrayList.add(VehiclesDao.class);
        arrayList.add(VehiclesReminderMsgDao.class);
        return arrayList;
    }
}
