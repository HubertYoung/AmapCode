package com.autonavi.minimap.ajx3.upgrade;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;

class RollbackInfo {
    static final int ROLLBACK_INFO_TYPE_BAD_BUNDLE_INFO = -3;
    static final int ROLLBACK_INFO_TYPE_BAD_SERVER_INFO = -2;
    static final int ROLLBACK_INFO_TYPE_NEED_NOT_DO = -1;
    static final int ROLLBACK_INFO_TYPE_ROLLBACK_ALREADY = 0;
    static final int ROLLBACK_INFO_TYPE_VALID = 1;
    private static final String TAG = "Ajx3Rollback";
    String bundleName;
    String fromString;
    boolean isRestart = false;
    boolean isShowNotice = false;
    String noticeContent = "";
    String restartNotice = "数据更新中，请重启高德地图";
    String target;
    String targetFileName;

    RollbackInfo() {
    }

    /* access modifiers changed from: 0000 */
    public int isValid() {
        if (TextUtils.isEmpty(this.bundleName) || TextUtils.isEmpty(this.target) || TextUtils.isEmpty(this.fromString) || this.fromString.contains(this.target)) {
            return -2;
        }
        Ajx3BundleFileInfo bundleFileInfo = Ajx3UpgradeManager.getInstance().getBundleFileInfo(this.bundleName);
        if (bundleFileInfo == null) {
            return -3;
        }
        if (bundleFileInfo.mIsRollback) {
            return 0;
        }
        String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(this.bundleName);
        if (TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
            loadedDiffAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(this.bundleName);
        }
        if (TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
            return -3;
        }
        return this.fromString.contains(loadedDiffAjxFileVersion) ? 1 : -1;
    }

    /* access modifiers changed from: 0000 */
    public boolean isReady() {
        if ("base_version".equals(this.target)) {
            this.targetFileName = "base_version";
            return true;
        }
        Ajx3BundleFileInfo bundleFileInfo = Ajx3UpgradeManager.getInstance().getBundleFileInfo(this.bundleName);
        if (bundleFileInfo == null) {
            return false;
        }
        String patchFileNameVarVersion = bundleFileInfo.getPatchFileNameVarVersion(this.target);
        if (TextUtils.isEmpty(patchFileNameVarVersion)) {
            return false;
        }
        this.targetFileName = patchFileNameVarVersion;
        return true;
    }
}
