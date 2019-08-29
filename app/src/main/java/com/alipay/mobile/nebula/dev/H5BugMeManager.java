package com.alipay.mobile.nebula.dev;

import com.alipay.mobile.nebula.webview.APWebView;
import java.util.HashMap;

public interface H5BugMeManager {
    boolean hasAccessToDebug(String str);

    void logServiceWorkerOnReceiveValue(HashMap<String, String> hashMap, String str);

    void onBugMeSwitched(boolean z);

    void openSettingPanel(boolean z);

    void release();

    void setDomainWhiteList(String[] strArr);

    void setUp();

    void setWebViewDebugging(String str, APWebView aPWebView);
}
