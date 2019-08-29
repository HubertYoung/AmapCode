package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.life.db.CouponDao.Properties;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/* renamed from: avy reason: default package */
/* compiled from: CouponDaoServiceImpl */
public final class avy implements avt {
    public final boolean a(String str) {
        return dor.a().a(str).booleanValue();
    }

    public final boolean a(avx avx) {
        Boolean bool;
        if (dor.a().a.insertOrReplace(avx) != 0) {
            bool = Boolean.TRUE;
        } else {
            bool = Boolean.FALSE;
        }
        return bool.booleanValue();
    }

    public final boolean b(String str) {
        dor a = dor.a();
        if (!TextUtils.isEmpty(str)) {
            QueryBuilder queryBuilder = a.a.queryBuilder();
            queryBuilder.where(Properties.a.eq(str), new WhereCondition[0]);
            queryBuilder.buildDelete().executeDeleteWithoutDetachingEntities();
        }
        if (!a.a(str).booleanValue()) {
            return true;
        }
        return false;
    }
}
