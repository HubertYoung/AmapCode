package defpackage;

import com.autonavi.minimap.route.common.db.ShareBikeOrderDao;
import com.autonavi.minimap.route.common.db.ShareBikeOrderDao.Properties;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

/* renamed from: eaa reason: default package */
/* compiled from: ShareBikeDbHelper */
public final class eaa {
    private static eaa b;
    public ShareBikeOrderDao a = ((ShareBikeOrderDao) xv.b().a(eab.class));

    private eaa() {
    }

    public static synchronized eaa a() {
        eaa eaa;
        synchronized (eaa.class) {
            try {
                if (b == null) {
                    b = new eaa();
                }
                eaa = b;
            }
        }
        return eaa;
    }

    public final eab a(String str) {
        if (this.a == null) {
            return null;
        }
        try {
            QueryBuilder queryBuilder = this.a.queryBuilder();
            queryBuilder.where(Properties.d.eq(str), new WhereCondition[0]);
            List list = queryBuilder.list();
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (eab) list.get(list.size() - 1);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public final eab b(String str) {
        if (this.a == null) {
            return null;
        }
        try {
            QueryBuilder queryBuilder = this.a.queryBuilder();
            queryBuilder.where(Properties.a.eq(str), new WhereCondition[0]);
            List list = queryBuilder.list();
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (eab) list.get(list.size() - 1);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
