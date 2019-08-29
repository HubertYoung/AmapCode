package com.autonavi.minimap.ajx3.modules;

import com.autonavi.common.Callback;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.scheme.Ajx3RouterManager;
import com.autonavi.minimap.ajx3.upgrade.Ajx3ActionLogUtil;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.BundleUpgradeCallback;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.Type;

@AjxModule("schemeloader")
public class ModuleSchemeLoader extends AbstractModule implements BundleUpgradeCallback {
    public static final String MODULE_NAME = "schemeloader";
    private JsFunctionCallback mCallback;
    private Callback mResultCallback;

    public void onFailed() {
    }

    public ModuleSchemeLoader(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setResultCallback(Callback callback) {
        this.mResultCallback = callback;
    }

    @AjxMethod("download")
    public void download(String str, JsFunctionCallback jsFunctionCallback) {
        this.mCallback = jsFunctionCallback;
        Ajx3UpgradeManager.getInstance().checkUpgradeViaScheme(str, this);
    }

    @AjxMethod("cancel")
    public void cancel() {
        Ajx3ActionLogUtil.actionLogAjxWeb(19, 0, "Cancel Remote Scheme Loading", false, Ajx3RouterManager.getInstance().getLogStatId(), Ajx3ActionLogUtil.generateSchemeAjxCheckRequestAndroidExt());
        Ajx3UpgradeManager.getInstance().cancelUpgrade(Type.scheme);
        Ajx3ActionLogUtil.actionLogAjxWeb(20, 0, "Finish Remote Scheme Loading", false, Ajx3RouterManager.getInstance().getLogStatId(), Ajx3ActionLogUtil.generateSchemeAjxCheckRequestAndroidExt());
        if (this.mResultCallback != null) {
            this.mResultCallback.callback(Boolean.FALSE);
            this.mResultCallback = null;
        }
    }

    public void onSuccess() {
        this.mCallback.callback(Boolean.TRUE, "success");
        Ajx3ActionLogUtil.actionLogAjxWeb(20, 0, "Finish Remote Scheme Loading", false, Ajx3RouterManager.getInstance().getLogStatId(), Ajx3ActionLogUtil.generateSchemeAjxCheckRequestAndroidExt());
        if (this.mResultCallback != null) {
            this.mResultCallback.callback(Boolean.TRUE);
            this.mResultCallback = null;
        }
    }

    public void onFailed(String str) {
        this.mCallback.callback(Boolean.FALSE, str);
        Ajx3ActionLogUtil.actionLogAjxWeb(20, 0, "Finish Remote Scheme Loading", false, Ajx3RouterManager.getInstance().getLogStatId(), Ajx3ActionLogUtil.generateSchemeAjxCheckRequestAndroidExt());
        if (this.mResultCallback != null) {
            this.mResultCallback.callback(Boolean.FALSE);
            this.mResultCallback = null;
        }
    }
}
