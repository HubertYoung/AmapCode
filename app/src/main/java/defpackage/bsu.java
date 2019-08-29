package defpackage;

import com.autonavi.map.db.SaveSyncActionDao;

/* renamed from: bsu reason: default package */
/* compiled from: SaveSyncHelper */
public class bsu {
    private static bsu b;
    public SaveSyncActionDao a = ((SaveSyncActionDao) xv.b().a(SaveSyncActionDao.class));

    private bsu() {
    }

    public static bsu a() {
        synchronized (bsu.class) {
            try {
                if (b == null) {
                    b = new bsu();
                }
            }
        }
        return b;
    }
}
