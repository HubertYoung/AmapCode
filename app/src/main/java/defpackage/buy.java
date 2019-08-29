package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.RealTimeBusItemDao;
import com.autonavi.map.db.RideHistoryDao;
import com.autonavi.map.db.RouteHistoryDao;
import com.autonavi.map.db.RunHistoryDao;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: buy reason: default package */
/* compiled from: RouteDaoSession */
public class buy implements xx {
    private DaoConfig a;
    private RealTimeBusItemDao b;
    private DaoConfig c;
    private RideHistoryDao d;
    private DaoConfig e;
    private RouteHistoryDao f;
    private DaoConfig g;
    private RunHistoryDao h;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(RealTimeBusItemDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new RealTimeBusItemDao(this.a, xtVar);
        this.c = map.get(RideHistoryDao.class).clone();
        this.c.initIdentityScope(identityScopeType);
        this.d = new RideHistoryDao(this.c, xtVar);
        this.e = map.get(RouteHistoryDao.class).clone();
        this.e.initIdentityScope(identityScopeType);
        this.f = new RouteHistoryDao(this.e, xtVar);
        this.g = map.get(RunHistoryDao.class).clone();
        this.g.initIdentityScope(identityScopeType);
        this.h = new RunHistoryDao(this.g, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(btd.class, this.b);
        hashMap.put(bte.class, this.d);
        hashMap.put(btf.class, this.f);
        hashMap.put(btg.class, this.h);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(RealTimeBusItemDao.class)) {
            return this.b;
        }
        if (cls.equals(RideHistoryDao.class)) {
            return this.d;
        }
        if (cls.equals(RouteHistoryDao.class)) {
            return this.f;
        }
        if (cls.equals(RunHistoryDao.class)) {
            return this.h;
        }
        return null;
    }
}
