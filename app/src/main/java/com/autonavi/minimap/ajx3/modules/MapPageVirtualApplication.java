package com.autonavi.minimap.ajx3.modules;

import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.HashSet;
import java.util.Iterator;

public class MapPageVirtualApplication extends esh {
    static HashSet<JsFunctionCallback> callbacks = new HashSet<>();

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public static void addListener(JsFunctionCallback jsFunctionCallback) {
        callbacks.add(jsFunctionCallback);
    }

    public static void removeListener(JsFunctionCallback jsFunctionCallback) {
        callbacks.remove(jsFunctionCallback);
    }

    private void notify(String str) {
        if (callbacks.size() > 0) {
            Iterator<JsFunctionCallback> it = callbacks.iterator();
            while (it.hasNext()) {
                it.next().callback(str);
            }
        }
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        if (AMapPageUtil.isHomePage()) {
            notify(isColdBoot() ? "coldBoot" : "warmBoot");
        }
    }

    public void vAppEnterForeground() {
        super.vAppEnterForeground();
        if (AMapPageUtil.isHomePage()) {
            notify("enterForeground");
        }
    }

    public void vAppEnterBackground() {
        super.vAppEnterBackground();
        if (AMapPageUtil.isHomePage()) {
            notify("enterBackground");
        }
    }
}
