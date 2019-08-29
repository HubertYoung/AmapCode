package com.alipay.mobile.nebula.provider;

import android.webkit.WebResourceResponse;
import com.alipay.mobile.nebula.webview.APWebResourceRequest;

public interface H5ServiceWorkerControllerProvider {
    WebResourceResponse shouldInterceptRequest4ServiceWorker(APWebResourceRequest aPWebResourceRequest);
}
