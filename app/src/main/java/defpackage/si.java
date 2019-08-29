package defpackage;

import com.amap.bundle.drivecommon.map.db.NaviHistoryDao;

/* renamed from: si reason: default package */
/* compiled from: NaviHistoryDBHelper */
public final class si {
    private static si b;
    public NaviHistoryDao a = ((NaviHistoryDao) xv.b().a(NaviHistoryDao.class));

    private si() {
    }

    public static synchronized si a() {
        si siVar;
        synchronized (si.class) {
            try {
                if (b == null) {
                    b = new si();
                }
                siVar = b;
            }
        }
        return siVar;
    }

    public final void b() {
        if (this.a != null) {
            this.a.queryBuilder().buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }
}
