package com.alipay.mobile.h5container.service;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.alipay.mobile.nebula.wallet.H5ExternalService;
import com.alipay.mobile.nebula.webview.APWebView;

public abstract class UcService extends H5ExternalService {
    public abstract APWebView createWebView(Context context, boolean z);

    public abstract WebResourceResponse getResponse(String str);

    public abstract String getUcVersion();

    public abstract boolean init(boolean z);

    @Deprecated
    public abstract String initUC7zSo();
}
