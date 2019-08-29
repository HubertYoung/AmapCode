package com.alipay.mobile.nebula.appcenter.download;

public interface H5ExternalDownloadManager {
    void addDownload(H5DownloadRequest h5DownloadRequest, H5DownloadCallback h5DownloadCallback);

    void cancel(String str);

    boolean isDownloading(String str);
}
