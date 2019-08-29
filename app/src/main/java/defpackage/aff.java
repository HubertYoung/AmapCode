package defpackage;

import com.amap.bundle.statistics.db.LogContentDao;
import java.util.List;

/* renamed from: aff reason: default package */
/* compiled from: LogContentDataHelper */
public final class aff {
    private static aff b;
    public LogContentDao a = ((LogContentDao) xv.b().a(LogContentDao.class));

    private aff() {
    }

    public static synchronized aff a() {
        aff aff;
        synchronized (aff.class) {
            try {
                if (b == null) {
                    b = new aff();
                }
                aff = b;
            }
        }
        return aff;
    }

    public final void a(List<afg> list) {
        this.a.deleteInTx((Iterable<T>) list);
    }
}
