package com.autonavi.inter.impl;

import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYPopupWindow.OnBackPressedListener;
import java.util.HashMap;
import java.util.Map;
import proguard.annotation.KeepName;

@KeepName
public final class UITEMPLATE_MainMapInvokePriority_DATA extends HashMap<String, Map<Class<?>, Float>> {
    public UITEMPLATE_MainMapInvokePriority_DATA() {
        put("onBackPressed", new HashMap());
        ((Map) get("onBackPressed")).put(OnBackPressedListener.class, Float.valueOf(3.0f));
    }
}
