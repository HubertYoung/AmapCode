package defpackage;

import com.amap.bundle.statistics.db.LogContentDao;
import com.autonavi.annotation.MultipleImpl;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: afd reason: default package */
/* compiled from: LogDaoSession */
public class afd implements xx {
    private DaoConfig a;
    private LogContentDao b;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(LogContentDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new LogContentDao(this.a, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(LogContentDao.class, this.b);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(LogContentDao.class)) {
            return this.b;
        }
        return null;
    }
}
