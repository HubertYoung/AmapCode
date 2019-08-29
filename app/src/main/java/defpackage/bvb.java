package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.SavePointDao;
import com.autonavi.map.db.SaveRouteDao;
import com.autonavi.map.db.SaveSyncActionDao;
import com.autonavi.map.db.SaveTagDao;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: bvb reason: default package */
/* compiled from: SaveDaoSession */
public class bvb implements xx {
    private DaoConfig a;
    private SavePointDao b;
    private DaoConfig c;
    private SaveRouteDao d;
    private DaoConfig e;
    private SaveSyncActionDao f;
    private DaoConfig g;
    private SaveTagDao h;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(SavePointDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new SavePointDao(this.a, xtVar);
        this.g = map.get(SaveTagDao.class).clone();
        this.g.initIdentityScope(identityScopeType);
        this.h = new SaveTagDao(this.g, xtVar);
        this.e = map.get(SaveSyncActionDao.class).clone();
        this.e.initIdentityScope(identityScopeType);
        this.f = new SaveSyncActionDao(this.e, xtVar);
        this.c = map.get(SaveRouteDao.class).clone();
        this.c.initIdentityScope(identityScopeType);
        this.d = new SaveRouteDao(this.c, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(bth.class, this.b);
        hashMap.put(bti.class, this.d);
        hashMap.put(btj.class, this.f);
        hashMap.put(btk.class, this.h);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(SavePointDao.class)) {
            return this.b;
        }
        if (cls.equals(SaveTagDao.class)) {
            return this.h;
        }
        if (cls.equals(SaveSyncActionDao.class)) {
            return this.f;
        }
        if (cls.equals(SaveRouteDao.class)) {
            return this.d;
        }
        return null;
    }
}
