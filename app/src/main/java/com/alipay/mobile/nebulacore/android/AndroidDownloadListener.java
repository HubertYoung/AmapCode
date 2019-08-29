package com.alipay.mobile.nebulacore.android;

import android.webkit.DownloadListener;
import com.alipay.mobile.nebula.webview.APDownloadListener;

class AndroidDownloadListener implements DownloadListener {
    private APDownloadListener a;

    AndroidDownloadListener(APDownloadListener listener) {
        this.a = listener;
    }

    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        if (this.a != null) {
            this.a.onDownloadStart(url, userAgent, contentDisposition, mimetype, contentLength);
        }
    }
}
