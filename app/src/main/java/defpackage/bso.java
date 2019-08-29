package defpackage;

import android.content.ContentValues;
import android.text.TextUtils;
import com.autonavi.map.db.RealTimeBusItemDao;
import com.autonavi.map.db.RealTimeBusItemDao.Properties;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.Collections;
import java.util.List;

/* renamed from: bso reason: default package */
/* compiled from: RealBusSearchHelper */
public final class bso {
    private static bso c;
    public RealTimeBusItemDao a = ((RealTimeBusItemDao) xv.b().a(RealTimeBusItemDao.class));
    public a b;

    /* renamed from: bso$a */
    /* compiled from: RealBusSearchHelper */
    public interface a {
        void a();
    }

    private bso() {
    }

    public static synchronized bso a() {
        bso bso;
        synchronized (bso.class) {
            try {
                if (c == null) {
                    c = new bso();
                }
                bso = c;
            }
        }
        return bso;
    }

    public final Boolean a(btd btd) {
        if (a(btd.bus_id, btd.station_id)) {
            return Boolean.FALSE;
        }
        if (this.a.insertOrReplace(btd) == 0) {
            return Boolean.FALSE;
        }
        c();
        return Boolean.TRUE;
    }

    public final boolean b(btd btd) {
        if (btd == null) {
            return false;
        }
        if (!a(btd.bus_id, btd.station_id)) {
            return this.a.insertOrReplace(btd) != 0;
        }
        ContentValues contentValues = new ContentValues();
        if (btd.adcode == null) {
            btd.adcode = "";
        }
        if (btd.station_id == null) {
            btd.station_id = "";
        }
        if (btd.station_name == null) {
            btd.station_name = "";
        }
        if (btd.station_lat == null) {
            btd.station_lat = Double.valueOf(0.0d);
        }
        if (btd.station_lon == null) {
            btd.station_lon = Double.valueOf(0.0d);
        }
        if (btd.bus_areacode == null) {
            btd.bus_areacode = "";
        }
        if (btd.poiid1 == null) {
            btd.poiid1 = "";
        }
        if (btd.bus_id == null) {
            btd.bus_id = "";
        }
        if (btd.bus_name == null) {
            btd.bus_name = "";
        }
        if (btd.bus_describe == null) {
            btd.bus_describe = "";
        }
        if (btd.alert_time == null) {
            btd.alert_time = "";
        }
        if (btd.alert_day == null) {
            btd.alert_day = "";
        }
        if (btd.is_push == null) {
            btd.is_push = "";
        }
        contentValues.put(Properties.a.columnName, btd.adcode);
        contentValues.put(Properties.b.columnName, btd.station_id);
        contentValues.put(Properties.c.columnName, btd.station_name);
        contentValues.put(Properties.d.columnName, btd.station_lat);
        contentValues.put(Properties.e.columnName, btd.station_lon);
        contentValues.put(Properties.f.columnName, btd.bus_areacode);
        contentValues.put(Properties.g.columnName, btd.poiid1);
        contentValues.put(Properties.h.columnName, btd.bus_id);
        contentValues.put(Properties.i.columnName, btd.bus_name);
        contentValues.put(Properties.j.columnName, btd.bus_describe);
        contentValues.put(Properties.k.columnName, btd.alert_time);
        contentValues.put(Properties.l.columnName, btd.alert_day);
        contentValues.put(Properties.m.columnName, btd.is_push);
        return this.a.getDatabase().update(RealTimeBusItemDao.TABLENAME, contentValues, "BUS_ID = ? AND STATION_ID = ?", new String[]{btd.bus_id, btd.station_id}) > 0;
    }

    public final boolean a(String str, String str2) {
        List list = this.a.queryBuilder().where(Properties.h.eq(str), Properties.b.eq(str2)).build().list();
        if (list == null || list.size() <= 0) {
            return false;
        }
        return true;
    }

    public final btd b(String str, String str2) {
        List list = this.a.queryBuilder().where(Properties.h.eq(str), Properties.b.eq(str2)).build().list();
        if (list == null || list.size() <= 0) {
            return null;
        }
        return (btd) list.get(0);
    }

    public final List<btd> a(String str) {
        Query build = this.a.queryBuilder().where(Properties.g.eq(str), new WhereCondition[0]).build();
        if (build == null || build.list() == null || build.list().size() <= 0) {
            return null;
        }
        return build.list();
    }

    public final void b(String str) {
        if (str != null) {
            QueryBuilder queryBuilder = this.a.queryBuilder();
            queryBuilder.where(Properties.b.eq(str), new WhereCondition[0]);
            queryBuilder.buildDelete().executeDeleteWithoutDetachingEntities();
            c();
        }
    }

    public final List<btd> c(String str) {
        QueryBuilder queryBuilder = this.a.queryBuilder();
        if (!TextUtils.isEmpty(str)) {
            queryBuilder.where(Properties.a.eq(str), new WhereCondition[0]);
        }
        List<btd> list = queryBuilder.list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            return list;
        }
        Collections.reverse(list);
        return list;
    }

    public final int b() {
        return (int) this.a.count();
    }

    private void c() {
        if (this.b != null) {
            this.b.a();
        }
    }
}
