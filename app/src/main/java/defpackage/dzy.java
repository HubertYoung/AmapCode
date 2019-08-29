package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.minimap.route.common.db.ShareBikeOrderDao;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: dzy reason: default package */
/* compiled from: RouteDaoSessionImpl */
public class dzy implements xx {
    private DaoConfig a;
    private ShareBikeOrderDao b;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(ShareBikeOrderDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new ShareBikeOrderDao(this.a, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(eab.class, this.b);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(eab.class)) {
            return this.b;
        }
        return null;
    }
}
