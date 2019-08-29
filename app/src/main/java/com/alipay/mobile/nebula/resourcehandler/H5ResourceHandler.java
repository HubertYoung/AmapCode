package com.alipay.mobile.nebula.resourcehandler;

import android.webkit.WebResourceResponse;

public interface H5ResourceHandler {
    WebResourceResponse shouldInterceptRequest(String str);
}
