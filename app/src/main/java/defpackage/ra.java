package defpackage;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

/* renamed from: ra reason: default package */
/* compiled from: ContextUtils */
public final class ra {
    public static boolean a(AbstractBasePage abstractBasePage) {
        return (abstractBasePage == null || !abstractBasePage.isAlive() || abstractBasePage.getActivity() == null || abstractBasePage.getContext() == null) ? false : true;
    }
}
