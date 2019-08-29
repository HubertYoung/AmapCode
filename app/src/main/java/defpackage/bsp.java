package defpackage;

import com.autonavi.map.db.RideHistoryDao;
import com.autonavi.map.db.RideHistoryDao.Properties;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

/* renamed from: bsp reason: default package */
/* compiled from: RideHistoryDBHelper */
public final class bsp {
    private static bsp b;
    public RideHistoryDao a = ((RideHistoryDao) xv.b().a(RideHistoryDao.class));

    private bsp() {
    }

    public static synchronized bsp a() {
        bsp bsp;
        synchronized (bsp.class) {
            try {
                if (b == null) {
                    b = new bsp();
                }
                bsp = b;
            }
        }
        return bsp;
    }

    public final List<bte> b() {
        if (this.a != null) {
            try {
                QueryBuilder queryBuilder = this.a.queryBuilder();
                queryBuilder.where(Properties.k.eq(Integer.valueOf(0)), new WhereCondition[0]);
                return queryBuilder.list();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public final bte a(String str) {
        if (this.a != null) {
            try {
                QueryBuilder queryBuilder = this.a.queryBuilder();
                queryBuilder.where(queryBuilder.and(Properties.a.eq(str), Properties.k.eq(Integer.valueOf(0)), new WhereCondition[0]), new WhereCondition[0]);
                List list = queryBuilder.list();
                if (list != null && list.size() > 0) {
                    return (bte) list.get(list.size() - 1);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public final bte a(long j) {
        if (this.a != null) {
            try {
                QueryBuilder queryBuilder = this.a.queryBuilder();
                queryBuilder.where(queryBuilder.and(Properties.f.eq(Long.valueOf(j)), Properties.k.eq(Integer.valueOf(0)), new WhereCondition[0]), new WhereCondition[0]);
                List list = queryBuilder.list();
                if (list != null && list.size() > 0) {
                    return (bte) list.get(list.size() - 1);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public final void a(bte bte) {
        if (bte != null) {
            try {
                this.a.update(bte);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
