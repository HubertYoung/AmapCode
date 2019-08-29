package defpackage;

import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: cyw reason: default package */
/* compiled from: SuspendConstant */
public interface cyw {

    /* renamed from: cyw$a */
    /* compiled from: SuspendConstant */
    public static class a {
        public static final int a;

        static {
            if (AMapPageUtil.getAppContext().getResources().getDisplayMetrics().densityDpi >= 320) {
                a = 4;
            } else {
                a = 3;
            }
        }
    }
}
