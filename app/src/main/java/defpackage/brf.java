package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.carowner.db.CarOwnerInfoDao;
import com.autonavi.map.carowner.db.CarOwnerInformationDao;
import com.autonavi.map.carowner.db.CarOwnerReminderMsgDao;
import com.autonavi.map.db.VehiclesDao;
import com.autonavi.map.db.VehiclesReminderMsgDao;
import com.autonavi.map.db.model.CarOwnerInformation;
import com.autonavi.map.db.model.Vehicles;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: brf reason: default package */
/* compiled from: CarOwnerDaoSession */
public class brf implements xx {
    private DaoConfig a;
    private CarOwnerInfoDao b;
    private DaoConfig c;
    private CarOwnerInformationDao d;
    private DaoConfig e;
    private CarOwnerReminderMsgDao f;
    private DaoConfig g;
    private VehiclesDao h;
    private DaoConfig i;
    private VehiclesReminderMsgDao j;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(CarOwnerInfoDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new CarOwnerInfoDao(this.a, xtVar);
        this.c = map.get(CarOwnerInformationDao.class).clone();
        this.c.initIdentityScope(identityScopeType);
        this.d = new CarOwnerInformationDao(this.c, xtVar);
        this.e = map.get(CarOwnerReminderMsgDao.class).clone();
        this.e.initIdentityScope(identityScopeType);
        this.f = new CarOwnerReminderMsgDao(this.e, xtVar);
        this.g = map.get(VehiclesDao.class).clone();
        this.g.initIdentityScope(identityScopeType);
        this.h = new VehiclesDao(this.g, xtVar);
        this.i = map.get(VehiclesReminderMsgDao.class).clone();
        this.i.initIdentityScope(identityScopeType);
        this.j = new VehiclesReminderMsgDao(this.i, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(bsy.class, this.b);
        hashMap.put(CarOwnerInformation.class, this.d);
        hashMap.put(bsz.class, this.f);
        hashMap.put(Vehicles.class, this.h);
        hashMap.put(btl.class, this.j);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(CarOwnerInfoDao.class)) {
            return this.b;
        }
        if (cls.equals(CarOwnerInformationDao.class)) {
            return this.d;
        }
        if (cls.equals(CarOwnerReminderMsgDao.class)) {
            return this.f;
        }
        if (cls.equals(VehiclesDao.class)) {
            return this.h;
        }
        if (cls.equals(VehiclesReminderMsgDao.class)) {
            return this.j;
        }
        return null;
    }
}
