package com.alipay.mobile.tinyappcommon;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.config.TinyAppConfig;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import java.util.List;
import java.util.Set;

/* compiled from: TinyAppMixActionServiceImpl */
public class c implements TinyAppMixActionService {
    private static final String a = c.class.getSimpleName();

    /* compiled from: TinyAppMixActionServiceImpl */
    private static class a {
        public static c a = new c(0);
    }

    /* synthetic */ c(byte b) {
        this();
    }

    private c() {
    }

    public static TinyAppMixActionService a() {
        return a.a;
    }

    public boolean allowedShowShareMenu(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        List<String> blackList = TinyAppConfig.getInstance().getShareMenuBlacklist();
        if (blackList == null || blackList.isEmpty()) {
            return true;
        }
        if (blackList.contains("all")) {
            H5Log.d(a, "allowedShowShareMenu...all closed");
            return false;
        }
        for (String blackAppId : blackList) {
            if (appId.equals(blackAppId)) {
                H5Log.d(a, "allowedShowShareMenu...hit blacklist");
                return false;
            }
        }
        return true;
    }

    public boolean allowedShowAboutMenu(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        List<String> blackList = TinyAppConfig.getInstance().getAboutMenuBlackList();
        if (blackList == null || blackList.isEmpty()) {
            return true;
        }
        if (blackList.contains("all")) {
            H5Log.d(a, "allowedShowAboutMenu...all closed");
            return false;
        }
        for (String blackAppId : blackList) {
            if (appId.equals(blackAppId)) {
                H5Log.d(a, "allowedShowAboutMenu...hit blacklist");
                return false;
            }
        }
        return true;
    }

    public boolean allowedShowFavoriteMenu(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        List<String> blackList = TinyAppConfig.getInstance().getFavoriteMenuBlacklist();
        if (blackList == null || blackList.isEmpty()) {
            return true;
        }
        if (blackList.contains("all")) {
            H5Log.d(a, "allowedShowFavoriteMenu...all closed");
            return false;
        }
        for (String blackAppId : blackList) {
            if (appId.equals(blackAppId)) {
                H5Log.d(a, "allowedShowFavoriteMenu...hit blacklist");
                return false;
            }
        }
        return true;
    }

    public boolean allowedShowAddToDesktopMenu(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        List<String> blackList = TinyAppConfig.getInstance().getAddToDesktopMenuBlacklist();
        if (blackList == null || blackList.isEmpty()) {
            return true;
        }
        if (blackList.contains("all")) {
            H5Log.d(a, "allowedShowAddToDesktopMenu...all closed");
            return false;
        }
        for (String blackAppId : blackList) {
            if (appId.equals(blackAppId)) {
                H5Log.d(a, "allowedShowAddToDesktopMenu...hit blacklist");
                return false;
            }
        }
        return true;
    }

    public JSONArray getRecentUserTinyAppList() {
        WalletTinyappUtils.getMultiProcessService();
        return null;
    }

    public List<String> getSupportedInternalApiList() {
        return TinyAppConfig.getInstance().getSupportedInternalApiList();
    }

    public void startApp(String sourceAppId, String targetAppId, JSONObject params, boolean closeCurrentApp) {
        WalletTinyappUtils.getMultiProcessService().a(sourceAppId, targetAppId, params, closeCurrentApp);
    }

    public boolean shouldLongLickShowPanel() {
        return TinyAppConfig.getInstance().shouldLongClickShowPanel();
    }

    public void initLoadStorage() {
        try {
            if (LiteProcessApi.isLiteProcess()) {
                com.alipay.mobile.tinyappcommon.a.c.a(5, null);
            }
        } catch (Throwable e) {
            H5Log.e(a, "initLoadStorage...e=" + e);
        }
    }

    public void putString(String appId, String key, String value) {
        if (LiteProcessApi.isLiteProcess()) {
            Bundle bundle = new Bundle();
            bundle.putString("appId", appId);
            bundle.putString(key, value);
            com.alipay.mobile.tinyappcommon.a.c.a(6, bundle);
        }
    }

    public boolean canOpenMiniService(String appId, String serviceId, String version, String mode) {
        return false;
    }

    public boolean isUseNativeShareSwitch() {
        return TinyAppConfig.getInstance().isUseNativeShareSwitch();
    }

    public boolean shouldInterceptWebviewOpenAppId(String currentAppId, String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        JSONObject jsonObject = TinyAppConfig.getInstance().getWebviewOpenAppIdList();
        if (jsonObject == null || jsonObject.isEmpty()) {
            return true;
        }
        String appIds = jsonObject.getString(currentAppId);
        if (TextUtils.isEmpty(appIds)) {
            return true;
        }
        if (appIds.contains("all") || appIds.contains(appId)) {
            return false;
        }
        return true;
    }

    public boolean supportRemoteDebugMode() {
        return TinyAppConfig.getInstance().supportRemoteDebugMode();
    }

    public boolean isUseRpcMergeForQrCodeShare() {
        return TinyAppConfig.getInstance().isUseRpcMergeForQrCodeShare();
    }

    public boolean isTinyAppFavorite(H5Page h5Page) {
        return false;
    }

    public boolean isEnableTemplateApp() {
        return TinyAppConfig.getInstance().isEnableTemplateApp();
    }

    public int getTemplateAppWaitTime() {
        return TinyAppConfig.getInstance().getTemplateAppWaitTime();
    }

    public boolean isEmbedWebViewShowProgress() {
        return TinyAppConfig.getInstance().isWebViewShowProgress();
    }

    public JSONArray getCookiePartWhiteList() {
        return TinyAppConfig.getInstance().getCookiePartWhiteList();
    }

    public boolean allowedUseTlsWhiteList(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return false;
        }
        List<String> whiteList = TinyAppConfig.getInstance().getUseTlsWhiteList();
        if (whiteList == null || whiteList.isEmpty()) {
            return true;
        }
        try {
            if (whiteList.contains("all")) {
                H5Log.d(a, appId + " allow use http/ws connect");
                return true;
            }
            for (String equals : whiteList) {
                if (TextUtils.equals(equals, appId)) {
                    H5Log.d(a, appId + " allow use http/ws connect");
                    return true;
                }
            }
            return false;
        } catch (Throwable e) {
            H5Log.e(a, e);
        }
    }

    public Set<String> getUseWholePkgList() {
        return TinyAppConfig.getInstance().getUseWholePackageAppIdSet();
    }
}
