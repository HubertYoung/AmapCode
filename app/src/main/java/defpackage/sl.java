package defpackage;

import com.amap.bundle.drivecommon.map.db.NaviHistoryDao;
import com.amap.bundle.drivecommon.map.db.RdCameraCityInfoDao;
import com.amap.bundle.drivecommon.map.db.RdCameraPaymentItemDao;
import com.amap.bundle.drivecommon.map.db.model.RdCameraCityInfo;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.autonavi.annotation.MultipleImpl;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: sl reason: default package */
/* compiled from: DriveDaoSession */
public class sl implements xx {
    private DaoConfig a;
    private NaviHistoryDao b;
    private DaoConfig c;
    private RdCameraCityInfoDao d;
    private DaoConfig e;
    private RdCameraPaymentItemDao f;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(NaviHistoryDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new NaviHistoryDao(this.a, xtVar);
        this.c = map.get(RdCameraCityInfoDao.class).clone();
        this.c.initIdentityScope(identityScopeType);
        this.d = new RdCameraCityInfoDao(this.c, xtVar);
        this.e = map.get(RdCameraPaymentItemDao.class).clone();
        this.e.initIdentityScope(identityScopeType);
        this.f = new RdCameraPaymentItemDao(this.e, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(sj.class, this.b);
        hashMap.put(RdCameraCityInfo.class, this.d);
        hashMap.put(RdCameraPaymentItem.class, this.f);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(NaviHistoryDao.class)) {
            return this.b;
        }
        if (cls.equals(RdCameraCityInfoDao.class)) {
            return this.d;
        }
        if (cls.equals(RdCameraPaymentItemDao.class)) {
            return this.f;
        }
        return null;
    }
}
