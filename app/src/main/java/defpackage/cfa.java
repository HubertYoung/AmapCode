package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.UserInfoDao;
import com.autonavi.map.db.model.UserInfo;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: cfa reason: default package */
/* compiled from: UserInfoDaoSession */
public class cfa implements xx {
    private DaoConfig a;
    private UserInfoDao b;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(UserInfoDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new UserInfoDao(this.a, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(UserInfo.class, this.b);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(UserInfoDao.class)) {
            return this.b;
        }
        return null;
    }
}
