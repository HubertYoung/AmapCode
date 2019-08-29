package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.VehiclesLocalDao;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: cps reason: default package */
/* compiled from: BaseMapDaoSessionImpl */
public class cps implements xx {
    private DaoConfig a;
    private VehiclesLocalDao b;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(VehiclesLocalDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new VehiclesLocalDao(this.a, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(bti.class, this.b);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(VehiclesLocalDao.class)) {
            return this.b;
        }
        return null;
    }
}
