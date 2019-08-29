package com.alipay.mobile.tinyappcommon.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import java.util.List;
import java.util.Set;

public interface TinyAppMixActionService {
    boolean allowedShowAboutMenu(String str);

    boolean allowedShowAddToDesktopMenu(String str);

    boolean allowedShowFavoriteMenu(String str);

    boolean allowedShowShareMenu(String str);

    boolean allowedUseTlsWhiteList(String str);

    boolean canOpenMiniService(String str, String str2, String str3, String str4);

    JSONArray getCookiePartWhiteList();

    JSONArray getRecentUserTinyAppList();

    List<String> getSupportedInternalApiList();

    int getTemplateAppWaitTime();

    Set<String> getUseWholePkgList();

    void initLoadStorage();

    boolean isEmbedWebViewShowProgress();

    boolean isEnableTemplateApp();

    boolean isTinyAppFavorite(H5Page h5Page);

    boolean isUseNativeShareSwitch();

    boolean isUseRpcMergeForQrCodeShare();

    void putString(String str, String str2, String str3);

    boolean shouldInterceptWebviewOpenAppId(String str, String str2);

    boolean shouldLongLickShowPanel();

    void startApp(String str, String str2, JSONObject jSONObject, boolean z);

    boolean supportRemoteDebugMode();
}
