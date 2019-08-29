package com.autonavi.miniapp.plugin.modules;

import com.alipay.android.nebulaapp.MiniAppUtil;
import com.autonavi.miniapp.biz.manager.MiniAppManager;
import com.autonavi.miniapp.plugin.router.MiniAppRouter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.KeepName;

@AjxModule("bigPear")
@KeepName
public class ModuleMiniApp extends AbstractModule {
    public static final String MODULE_NAME = "bigPear";
    private static final String TAG = "ModuleMiniApp";

    public ModuleMiniApp(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "recentBigPears")
    public String recentMiniApps() {
        return MiniAppManager.getInstance().recentMiniApps();
    }

    @AjxMethod(invokeMode = "sync", value = "isBigPearEnable")
    public boolean isMiniAppEnable() {
        return MiniAppUtil.isMiniAppEnable();
    }

    @AjxMethod(invokeMode = "sync", value = "isSupportScheme")
    public boolean isSupportScheme(String str) {
        return MiniAppUtil.isSupportScheme(str);
    }

    @AjxMethod("processScheme")
    public void processScheme(String str) {
        MiniAppRouter.processScheme(str);
    }

    @AjxMethod("getBigPearCloudConfig")
    public void getMiniAppCloudConfig(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(MiniAppUtil.getMiniAppCloudConfig());
        }
    }
}
