package com.alipay.mobile.nebula.appcenter.api;

import android.webkit.WebResourceResponse;

public interface H5ContentProvider {
    public static final String H5_BRIDGE = "https://a.alipayobjects.com/bridgeapi/1.0/jsready.js";
    public static final String PAGE_ERROR = "https://alipay.com/h5container/h5_page_error.html";
    public static final String REDIRECT_LINK = "https://alipay.com/h5container/redirect_link.html";
    public static final String SECURITY_LINK = "https://alipay.com/h5container/security_link.html";
    public static final String UN_SAFE = "https://alipay.com/h5container/un_safe.html";
    public static final String WHITE_LINK = "https://alipay.com/h5container/white_link.html";

    public interface ResponseListen {
        void onGetResponse(WebResourceResponse webResourceResponse);
    }

    WebResourceResponse getContent(String str);

    WebResourceResponse getContent(String str, boolean z);

    void getContent(String str, ResponseListen responseListen);

    void getContentOnUi(String str, ResponseListen responseListen);

    String getFallbackUrl(String str);

    byte[] getLocalResource(String str);

    void mapContent(String str, String str2);

    void releaseContent();

    void setEnableFallbackUrl(boolean z);

    void setFallbackCache(String str, byte[] bArr);
}
