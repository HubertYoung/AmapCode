package defpackage;

import com.autonavi.map.db.RunHistoryDao;
import com.autonavi.map.db.RunHistoryDao.Properties;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

/* renamed from: bsq reason: default package */
/* compiled from: RunHistoryDBHelper */
public final class bsq {
    private static bsq b;
    public RunHistoryDao a = ((RunHistoryDao) xv.b().a(RunHistoryDao.class));

    private bsq() {
    }

    public static synchronized bsq a() {
        bsq bsq;
        synchronized (bsq.class) {
            try {
                if (b == null) {
                    b = new bsq();
                }
                bsq = b;
            }
        }
        return bsq;
    }

    public final List<btg> b() {
        if (this.a == null) {
            return null;
        }
        try {
            QueryBuilder queryBuilder = this.a.queryBuilder();
            queryBuilder.where(Properties.k.eq(Integer.valueOf(0)), new WhereCondition[0]).orderDesc(Properties.f);
            return queryBuilder.list();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public final btg a(long j) {
        if (this.a != null) {
            try {
                QueryBuilder queryBuilder = this.a.queryBuilder();
                queryBuilder.where(queryBuilder.and(Properties.f.eq(Long.valueOf(j)), Properties.k.eq(Integer.valueOf(0)), new WhereCondition[0]), new WhereCondition[0]);
                List list = queryBuilder.list();
                if (list != null && list.size() > 0) {
                    return (btg) list.get(list.size() - 1);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public final List<btg> b(long j) {
        if (this.a != null) {
            try {
                QueryBuilder queryBuilder = this.a.queryBuilder();
                queryBuilder.where(queryBuilder.and(Properties.f.gt(Long.valueOf(j)), Properties.k.eq(Integer.valueOf(0)), new WhereCondition[0]), new WhereCondition[0]);
                return queryBuilder.list();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public final void a(btg btg) {
        if (btg != null) {
            try {
                this.a.update(btg);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
