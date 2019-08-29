package com.alipay.mobile.nebulauc.impl;

import com.alipay.mobile.nebula.webview.APDownloadListener;
import com.uc.webview.export.DownloadListener;

class UCDownloadListener implements DownloadListener {
    private APDownloadListener mListener;

    UCDownloadListener(APDownloadListener listener) {
        this.mListener = listener;
    }

    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        if (this.mListener != null) {
            this.mListener.onDownloadStart(url, userAgent, contentDisposition, mimetype, contentLength);
        }
    }
}
