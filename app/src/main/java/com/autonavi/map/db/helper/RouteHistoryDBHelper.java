package com.autonavi.map.db.helper;

import android.content.Context;
import com.autonavi.map.db.RouteHistoryDao;
import com.autonavi.map.db.RouteHistoryDao.Properties;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

public class RouteHistoryDBHelper {
    private static RouteHistoryDBHelper a;
    private RouteHistoryDao b = ((RouteHistoryDao) xv.b().a(RouteHistoryDao.class));

    private RouteHistoryDBHelper() {
    }

    public static synchronized RouteHistoryDBHelper getInstance(Context context) {
        RouteHistoryDBHelper routeHistoryDBHelper;
        synchronized (RouteHistoryDBHelper.class) {
            try {
                if (a == null) {
                    a = new RouteHistoryDBHelper();
                }
                routeHistoryDBHelper = a;
            }
        }
        return routeHistoryDBHelper;
    }

    public List<btf> getHistoryList(int i) {
        if (this.b == null) {
            return null;
        }
        try {
            QueryBuilder queryBuilder = this.b.queryBuilder();
            queryBuilder.where(Properties.c.eq(Integer.valueOf(i)), new WhereCondition[0]).orderDesc(Properties.l);
            return queryBuilder.list();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public void saveRouteHistory(btf btf) {
        if (this.b != null) {
            btf.l = Long.valueOf(System.currentTimeMillis());
            try {
                this.b.delete(btf);
                this.b.insertOrReplace(btf);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void deleteRouteHistory(btf btf) {
        if (btf != null) {
            try {
                this.b.delete(btf);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void clearRouteHistory(int i) {
        if (this.b != null) {
            try {
                this.b.queryBuilder().where(Properties.c.eq(Integer.valueOf(i)), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void deleteAll() {
        try {
            this.b.queryBuilder().buildDelete().executeDeleteWithoutDetachingEntities();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void saveInBatch(List<btf> list) {
        if (list != null) {
            removeDuplicate(list);
            for (btf next : list) {
                try {
                    this.b.delete(next);
                    this.b.insertOrReplace(next);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void removeDuplicate(List<btf> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int size = list.size() - 1; size > i; size--) {
                if (list.get(size).a.equalsIgnoreCase(list.get(i).a)) {
                    list.remove(size);
                }
            }
        }
    }
}
