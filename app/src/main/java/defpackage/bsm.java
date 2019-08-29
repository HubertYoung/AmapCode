package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.carowner.db.CarOwnerInformationDao;
import com.autonavi.map.carowner.db.CarOwnerReminderMsgDao;
import com.autonavi.map.db.VehiclesDao;
import com.autonavi.map.db.VehiclesDao.Properties;
import com.autonavi.map.db.VehiclesReminderMsgDao;
import com.autonavi.map.db.model.Vehicles;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

/* renamed from: bsm reason: default package */
/* compiled from: CarOwnerMultiVehiclesDBHelper */
public final class bsm implements atn {
    private static bsm b;
    private a a = a.a;
    private CarOwnerInformationDao c = ((CarOwnerInformationDao) xv.b().a(CarOwnerInformationDao.class));
    private VehiclesDao d = ((VehiclesDao) xv.b().a(VehiclesDao.class));
    private CarOwnerReminderMsgDao e = ((CarOwnerReminderMsgDao) xv.b().a(CarOwnerReminderMsgDao.class));
    private VehiclesReminderMsgDao f = ((VehiclesReminderMsgDao) xv.b().a(VehiclesReminderMsgDao.class));
    private bsw g = ((bsw) ank.a(bsw.class));

    public final void a(a aVar) {
        this.a = aVar;
    }

    private bsm() {
        if (this.g != null) {
            this.g.a();
        }
    }

    public static synchronized bsm e() {
        bsm bsm;
        synchronized (bsm.class) {
            try {
                if (b == null) {
                    b = new bsm();
                }
                bsm = b;
            }
        }
        return bsm;
    }

    public final List<Vehicles> a() {
        if (this.g == null) {
            return null;
        }
        try {
            return this.g.b();
        } catch (Exception unused) {
            return null;
        }
    }

    public final void b() {
        if (this.g != null) {
            this.g.c();
        }
    }

    public final void d() {
        this.c.deleteAll();
        f();
        this.e.deleteAll();
        this.f.deleteAll();
    }

    private void f() {
        AMapLog.i("zyl", "deleteAllNetVehicles()");
        QueryBuilder queryBuilder = this.d.queryBuilder();
        queryBuilder.where(Properties.b.notEq(Integer.valueOf(-100)), new WhereCondition[0]);
        List list = queryBuilder.list();
        if (list != null && list.size() > 0) {
            this.d.deleteInTx((Iterable<T>) list);
        }
    }

    public final List<Vehicles> c() {
        if (this.a.a()) {
            QueryBuilder queryBuilder = this.d.queryBuilder();
            queryBuilder.where(Properties.b.notEq(Integer.valueOf(-1)), Properties.b.notEq(Integer.valueOf(-100)));
            return queryBuilder.orderAsc(Properties.a).list();
        } else if (this.g != null) {
            return this.g.b();
        } else {
            return null;
        }
    }
}
