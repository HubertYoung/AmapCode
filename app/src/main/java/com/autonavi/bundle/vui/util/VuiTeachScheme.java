package com.autonavi.bundle.vui.util;

import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class VuiTeachScheme {

    @Retention(RetentionPolicy.SOURCE)
    public @interface SchemeContants {
    }

    public static int a() {
        VUIStateManager.f();
        bid pageContext = AMapPageUtil.getPageContext();
        return (!(pageContext != null && VUIStateManager.b(pageContext.getClass().getName())) && VUIStateManager.f().o) ? 1 : 0;
    }
}
