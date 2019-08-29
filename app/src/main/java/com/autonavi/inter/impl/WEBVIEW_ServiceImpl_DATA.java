package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.amap.bundle.webview.util.WebTemplateUrlRewriteDelegateImpl", "com.amap.bundle.webview.util.WebviewSchemeCheckServiceImpl"}, inters = {"com.autonavi.minimap.util.url.IWebTemplateUrlRewriteDelegate", "com.autonavi.common.utils.IWebviewSchemeCheckService"}, module = "webview")
@KeepName
public final class WEBVIEW_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public WEBVIEW_ServiceImpl_DATA() {
        put(emz.class, ajo.class);
        put(bnt.class, ajq.class);
    }
}
