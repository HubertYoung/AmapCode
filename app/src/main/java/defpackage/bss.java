package defpackage;

import android.text.TextUtils;
import com.autonavi.map.db.SavePointDao;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bss reason: default package */
/* compiled from: SavePointHelper */
public class bss {
    private static bss a;
    private SavePointDao b = ((SavePointDao) xv.b().a(SavePointDao.class));
    private List<Object> c = new ArrayList();

    private bss() {
    }

    public static bss a() {
        synchronized (bss.class) {
            if (a == null) {
                a = new bss();
            }
        }
        return a;
    }

    public static int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int r = bin.a.r("101");
        if (bin.a.Y().size() > r) {
            r = bin.a.Y().size();
        }
        return r;
    }
}
