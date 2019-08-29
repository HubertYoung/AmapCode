package com.autonavi.minimap.ajx3.assistant;

import com.autonavi.minimap.ajx3.AjxInit;
import com.autonavi.minimap.ajx3.util.AjxDebugUtils;
import com.autonavi.wing.VirtualAllLifecycleApplication;

public class AjxAssistantApplication extends VirtualAllLifecycleApplication {
    public static String[] ITEM = {"设置", "IDE\n调试", "诊断", "返回", "扫码", "刷新"};

    public boolean isRegisterLifeCycle() {
        return false;
    }

    public void vAppEnterForeground() {
        if (AjxInit.sIsAjxEngineInited) {
            AjxDebugUtils.bindAjx3DebugService();
        }
    }

    public void vAppEnterBackground() {
        if (AjxInit.sIsAjxEngineInited) {
            AjxDebugUtils.unBindAjx3DebugService();
        }
    }

    public void vAppResume() {
        if (AjxInit.sIsAjxEngineInited) {
            AjxDebugUtils.bindAjx3DebugService();
        }
    }

    public void vAppPause() {
        super.vAppPause();
        if (AjxInit.sIsAjxEngineInited) {
            AjxDebugUtils.unBindAjx3DebugService();
        }
    }
}
