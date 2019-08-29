package com.alipay.mobile.h5container.service;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.webkit.WebResourceResponse;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.wallet.H5ExternalService;
import com.alipay.mobile.nebula.webview.APWebView;

public abstract class RnService extends H5ExternalService {
    public static final String RN_BIZ_TYPE_PREFIX = "rn_";

    public abstract boolean addRnView(H5Page h5Page, FragmentManager fragmentManager);

    public abstract APWebView createWebView(Context context);

    public abstract WebResourceResponse getResponse(String str);

    public abstract void init();

    public abstract boolean removeRnView(H5Page h5Page, FragmentManager fragmentManager);

    public static boolean isRnBiz(String bizType) {
        return bizType != null && bizType.startsWith(RN_BIZ_TYPE_PREFIX);
    }
}
