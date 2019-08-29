package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.map.db.TipItemDao;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: bsj reason: default package */
/* compiled from: SearchDaoSession */
public class bsj implements xx {
    private DaoConfig a;
    private TipItemDao b;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(TipItemDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new TipItemDao(this.a, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(TipItem.class, this.b);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(TipItemDao.class)) {
            return this.b;
        }
        return null;
    }
}
