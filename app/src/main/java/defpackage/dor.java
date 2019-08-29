package defpackage;

import com.autonavi.minimap.life.db.CouponDao;
import com.autonavi.minimap.life.db.CouponDao.Properties;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

/* renamed from: dor reason: default package */
/* compiled from: CouponDataHelper */
public final class dor {
    private static dor b;
    public CouponDao a;
    private xt c;

    private dor() {
    }

    public static synchronized dor a() {
        dor dor;
        synchronized (dor.class) {
            try {
                if (b == null) {
                    dor dor2 = new dor();
                    b = dor2;
                    dor2.c = xv.b();
                    dor dor3 = b;
                    dor3.a = (CouponDao) dor3.c.a(CouponDao.class);
                }
                dor = b;
            }
        }
        return dor;
    }

    private avx b(String str) {
        List list = this.a.queryBuilder().where(Properties.a.eq(str), new WhereCondition[0]).build().list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (avx) list.get(0);
    }

    public final Boolean a(String str) {
        if (b(str) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
