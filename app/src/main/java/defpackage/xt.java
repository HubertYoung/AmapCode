package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.inter.IMultipleServiceLoader;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: xt reason: default package */
/* compiled from: DaoSession */
public final class xt extends AbstractDaoSession {
    private List<xx> a = null;

    public xt(SQLiteDatabase sQLiteDatabase, IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map) {
        super(sQLiteDatabase);
        HashMap hashMap = new HashMap();
        List<xx> a2 = a();
        if (a2 != null && a2.size() > 0) {
            for (xx a3 : a2) {
                hashMap.putAll(a3.a(identityScopeType, map, this));
            }
        }
        for (Entry entry : hashMap.entrySet()) {
            try {
                Class cls = (Class) entry.getValue();
                AbstractDao abstractDao = (AbstractDao) entry.getKey();
                if (bno.a) {
                    StringBuilder sb = new StringBuilder("clazz = ");
                    sb.append(cls);
                    sb.append(", abstractDao");
                    sb.append(abstractDao);
                    AMapLog.d("db--->DaoSession", sb.toString());
                }
                registerDao(cls, abstractDao);
            } catch (Exception unused) {
            }
        }
    }

    private List<xx> a() {
        if (this.a != null) {
            return this.a;
        }
        List<Class<? extends T>> loadServices = ((IMultipleServiceLoader) bqn.a(IMultipleServiceLoader.class)).loadServices(xx.class);
        if (loadServices == null) {
            return this.a;
        }
        this.a = new ArrayList();
        for (Class newInstance : loadServices) {
            try {
                this.a.add((xx) newInstance.newInstance());
            } catch (Exception e) {
                if (bno.a) {
                    throw new IllegalArgumentException(e);
                }
                e.printStackTrace();
            }
        }
        return this.a;
    }

    public final AbstractDao a(Class cls) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("getAbstractDao -> className = ");
            sb.append(cls.getName());
            AMapLog.d("db--->DaoSession", sb.toString());
        }
        AbstractDao abstractDao = null;
        List<xx> a2 = a();
        if (a2 != null && a2.size() > 0) {
            for (xx a3 : a2) {
                abstractDao = a3.a(cls);
                if (abstractDao != null) {
                    break;
                }
            }
        }
        return abstractDao;
    }
}
