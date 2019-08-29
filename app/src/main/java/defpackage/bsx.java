package defpackage;

import com.autonavi.map.db.VehiclesLocalDao;
import com.autonavi.map.db.VehiclesLocalDao.Properties;
import com.autonavi.map.db.model.Vehicles;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

/* renamed from: bsx reason: default package */
/* compiled from: LocalDBManagerImpl */
public class bsx implements bsw {
    private VehiclesLocalDao a;

    public final void a() {
        this.a = (VehiclesLocalDao) xv.b().a(VehiclesLocalDao.class);
    }

    public final List<Vehicles> b() {
        if (this.a == null) {
            return null;
        }
        QueryBuilder queryBuilder = this.a.queryBuilder();
        queryBuilder.where(Properties.b.notEq(Integer.valueOf(-1)), new WhereCondition[0]);
        return queryBuilder.orderAsc(Properties.a).list();
    }

    public final void c() {
        if (this.a != null) {
            this.a.deleteAll();
        }
    }
}
