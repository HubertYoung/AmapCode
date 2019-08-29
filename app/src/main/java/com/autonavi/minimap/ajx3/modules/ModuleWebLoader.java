package com.autonavi.minimap.ajx3.modules;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.upgrade.Ajx3ActionLogUtil;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.BundleUpgradeCallback;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.Type;

@AjxModule("ajx.webloader")
public class ModuleWebLoader extends AbstractModule implements BundleUpgradeCallback {
    public static final String MODULE_NAME = "ajx.webloader";
    private JsFunctionCallback mCallback = null;
    private String mFileName = "";

    public ModuleWebLoader(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("download")
    public void download(String str, JsFunctionCallback jsFunctionCallback) {
        this.mFileName = str;
        if (TextUtils.isEmpty(str)) {
            jsFunctionCallback.callback(Boolean.FALSE, Boolean.FALSE);
            return;
        }
        this.mCallback = jsFunctionCallback;
        String bundleName = AjxFileInfo.getBundleName(str);
        Ajx3ActionLogUtil.actionLogAjxWeb(12, 12, "begin show loading", true, Ajx3UpgradeManager.getInstance().mStatId, Ajx3ActionLogUtil.generateWebAjxLoadingAndroidExt(AjxFileInfo.isFileExists(Ajx3Path.getAjxUrl(this.mFileName))));
        Ajx3UpgradeManager.getInstance().checkUpgradeViaBundleName(bundleName, this);
    }

    @AjxMethod("cancel")
    public void cancel() {
        Ajx3UpgradeManager.getInstance().cancelUpgrade(Type.web);
        this.mFileName = "";
    }

    public void onSuccess() {
        if (this.mCallback != null) {
            boolean isFileExists = AjxFileInfo.isFileExists(Ajx3Path.getAjxUrl(this.mFileName));
            this.mCallback.callback(Boolean.valueOf(isFileExists), Boolean.valueOf(isFileExists));
            Ajx3ActionLogUtil.actionLogAjxWeb(14, 14, "loading finish", true, Ajx3UpgradeManager.getInstance().mStatId, Ajx3ActionLogUtil.generateWebAjxLoadingFinishAndroidExt(isFileExists, true));
        }
    }

    public void onFailed() {
        if (this.mCallback != null) {
            boolean isFileExists = AjxFileInfo.isFileExists(Ajx3Path.getAjxUrl(this.mFileName));
            this.mCallback.callback(Boolean.FALSE, Boolean.valueOf(isFileExists));
            Ajx3ActionLogUtil.actionLogAjxWeb(14, 14, "loading finish", true, Ajx3UpgradeManager.getInstance().mStatId, Ajx3ActionLogUtil.generateWebAjxLoadingFinishAndroidExt(isFileExists, false));
        }
    }

    public void onFailed(String str) {
        if (this.mCallback != null) {
            boolean isFileExists = AjxFileInfo.isFileExists(Ajx3Path.getAjxUrl(this.mFileName));
            this.mCallback.callback(Boolean.FALSE, Boolean.valueOf(isFileExists));
            Ajx3ActionLogUtil.actionLogAjxWeb(14, 14, "loading finish".concat(String.valueOf(str)), true, Ajx3UpgradeManager.getInstance().mStatId, Ajx3ActionLogUtil.generateWebAjxLoadingFinishAndroidExt(isFileExists, false));
        }
    }
}
