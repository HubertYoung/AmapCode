package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.model.Msgbox;
import com.autonavi.map.msgbox.db.MessageCategoryDao;
import com.autonavi.map.msgbox.db.MsgboxDao;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.HashMap;
import java.util.Map;

@MultipleImpl(xx.class)
/* renamed from: buf reason: default package */
/* compiled from: MsgBoxDaoSession */
public class buf implements xx {
    private DaoConfig a;
    private MessageCategoryDao b;
    private DaoConfig c;
    private MsgboxDao d;

    public final Map<Class, AbstractDao> a(IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map, xt xtVar) {
        this.a = map.get(MessageCategoryDao.class).clone();
        this.a.initIdentityScope(identityScopeType);
        this.b = new MessageCategoryDao(this.a, xtVar);
        this.c = map.get(MsgboxDao.class).clone();
        this.c.initIdentityScope(identityScopeType);
        this.d = new MsgboxDao(this.c, xtVar);
        HashMap hashMap = new HashMap();
        hashMap.put(btb.class, this.b);
        hashMap.put(Msgbox.class, this.d);
        return hashMap;
    }

    public final AbstractDao a(Class cls) {
        if (cls.equals(MessageCategoryDao.class)) {
            return this.b;
        }
        if (cls.equals(MsgboxDao.class)) {
            return this.d;
        }
        return null;
    }
}
