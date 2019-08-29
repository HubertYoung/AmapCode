package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.H5WebStorageDao;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: cfy reason: default package */
/* compiled from: WebViewDaoSession */
public class cfy implements xx {
    private DaoConfig a;
    private H5WebStorageDao b;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(H5WebStorageDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new H5WebStorageDao(this.a, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(bta.class, this.b);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(H5WebStorageDao.class)) {
            return this.b;
        }
        return null;
    }
}
