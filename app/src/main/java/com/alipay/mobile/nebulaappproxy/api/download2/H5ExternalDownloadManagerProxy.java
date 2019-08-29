package com.alipay.mobile.nebulaappproxy.api.download2;

import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.download.H5ExternalDownloadManager;

public class H5ExternalDownloadManagerProxy implements H5ExternalDownloadManager {
    private H5ExternalDownloadManager a;

    private H5ExternalDownloadManager a() {
        if (this.a == null) {
            this.a = new H5AppDownloadManagerV2();
        }
        return this.a;
    }

    public void addDownload(H5DownloadRequest h5DownloadRequest, H5DownloadCallback h5DownloadCallback) {
        a().addDownload(h5DownloadRequest, h5DownloadCallback);
    }

    public void cancel(String url) {
        a().cancel(url);
    }

    public boolean isDownloading(String url) {
        return a().isDownloading(url);
    }
}
