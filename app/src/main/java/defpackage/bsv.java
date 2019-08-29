package defpackage;

import com.autonavi.map.db.SaveTagDao;

/* renamed from: bsv reason: default package */
/* compiled from: SaveTagHelper */
public class bsv {
    private static bsv b;
    public SaveTagDao a = ((SaveTagDao) xv.b().a(SaveTagDao.class));

    public static bsv a() {
        synchronized (bsv.class) {
            try {
                if (b == null) {
                    b = new bsv();
                }
            }
        }
        return b;
    }

    private bsv() {
    }
}
