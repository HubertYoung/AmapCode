package defpackage;

import com.autonavi.map.db.H5WebStorageDao;
import com.autonavi.map.db.H5WebStorageDao.Properties;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

/* renamed from: bsn reason: default package */
/* compiled from: H5WebStorageDataHelper */
public final class bsn {
    private static bsn b;
    public H5WebStorageDao a;
    private xt c;

    private bsn() {
    }

    public static synchronized bsn a() {
        bsn bsn;
        synchronized (bsn.class) {
            try {
                if (b == null) {
                    bsn bsn2 = new bsn();
                    b = bsn2;
                    bsn2.c = xv.b();
                    bsn bsn3 = b;
                    bsn3.a = (H5WebStorageDao) bsn3.c.a(H5WebStorageDao.class);
                }
                bsn = b;
            }
        }
        return bsn;
    }

    public final List<bta> a(String str) {
        Query build = this.a.queryBuilder().where(Properties.a.eq(str), new WhereCondition[0]).build();
        if (build != null) {
            return build.list();
        }
        return null;
    }
}
