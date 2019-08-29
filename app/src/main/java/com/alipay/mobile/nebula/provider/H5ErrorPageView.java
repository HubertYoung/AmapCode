package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.webview.APWebView;

public interface H5ErrorPageView {
    boolean enableShowErrorPage();

    void errorPageCallback(H5Page h5Page, APWebView aPWebView, String str, int i, String str2, String str3);
}
