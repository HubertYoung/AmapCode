package defpackage;

import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.lang.ref.WeakReference;

/* renamed from: bnp reason: default package */
/* compiled from: DoubleClickUtil */
public final class bnp {
    private static long a = 0;
    private static int b = -1;
    private static WeakReference<bid> c;

    public static boolean a(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - a;
        if (b == i && 0 < j && j < 800) {
            return true;
        }
        a = currentTimeMillis;
        b = i;
        return false;
    }

    public static boolean a() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - a;
        if ((c == null || c.get() == null || c.get() == AMapPageUtil.getPageContext()) && 0 < j && j < 800) {
            return true;
        }
        c = new WeakReference<>(AMapPageUtil.getPageContext());
        a = currentTimeMillis;
        return false;
    }
}
