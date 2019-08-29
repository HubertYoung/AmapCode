package com.autonavi.inter.impl;

import com.amap.bundle.webview.page.TransparentWebViewPage;
import com.amap.bundle.webview.page.WebViewPage;
import com.autonavi.annotation.helper.PageActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.common.action.webview.transparent", "amap.common.action.webview"}, module = "webview", pages = {"com.amap.bundle.webview.page.TransparentWebViewPage", "com.amap.bundle.webview.page.WebViewPage"})
@KeepName
public final class WEBVIEW_PageAction_DATA extends HashMap<String, Class<?>> {
    public WEBVIEW_PageAction_DATA() {
        put("amap.common.action.webview.transparent", TransparentWebViewPage.class);
        put("amap.common.action.webview", WebViewPage.class);
    }
}
