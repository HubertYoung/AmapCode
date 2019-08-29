package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.webview.APWebView;

public interface H5HttpErrorRouterProvider {
    boolean enableRoute(APWebView aPWebView, H5Page h5Page, int i, String str);
}
