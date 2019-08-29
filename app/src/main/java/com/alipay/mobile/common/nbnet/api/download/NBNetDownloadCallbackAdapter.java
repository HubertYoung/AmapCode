package com.alipay.mobile.common.nbnet.api.download;

import java.io.File;

public class NBNetDownloadCallbackAdapter implements NBNetDownloadCallback {
    public static NBNetDownloadCallback nbNetDownloadCallback;

    public static final NBNetDownloadCallback getDefault() {
        if (nbNetDownloadCallback != null) {
            return nbNetDownloadCallback;
        }
        synchronized (NBNetDownloadCallbackAdapter.class) {
            if (nbNetDownloadCallback != null) {
                return nbNetDownloadCallback;
            }
            NBNetDownloadCallbackAdapter nBNetDownloadCallbackAdapter = new NBNetDownloadCallbackAdapter();
            nbNetDownloadCallback = nBNetDownloadCallbackAdapter;
            return nBNetDownloadCallbackAdapter;
        }
    }

    public void onCancled(NBNetDownloadRequest request) {
    }

    public void onDownloadStart(NBNetDownloadRequest request) {
    }

    public void onDownloadProgress(NBNetDownloadRequest request, int progress, long received, long total) {
    }

    public void onDownloadProgress(NBNetDownloadRequest request, int progress, long received, long total, File cacheFile) {
    }

    public void onDownloadError(NBNetDownloadRequest request, NBNetDownloadResponse response) {
    }

    public void onDownloadFinished(NBNetDownloadRequest request, NBNetDownloadResponse response) {
    }
}
