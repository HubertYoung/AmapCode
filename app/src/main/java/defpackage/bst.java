package defpackage;

import android.text.TextUtils;
import com.autonavi.map.db.SaveRouteDao;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bst reason: default package */
/* compiled from: SaveRouteHelper */
public class bst {
    private static bst a;
    private SaveRouteDao b = ((SaveRouteDao) xv.b().a(SaveRouteDao.class));
    private List<Object> c = new ArrayList();

    private bst() {
    }

    public static bst a() {
        synchronized (bst.class) {
            if (a == null) {
                a = new bst();
            }
        }
        return a;
    }

    public static int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int r = bin.a.r("105");
        int r2 = bin.a.r("102");
        int r3 = r + r2 + bin.a.r("103") + bin.a.r("104");
        if (r3 == 0) {
            r3 = bin.a.X().size();
        }
        return r3;
    }
}
