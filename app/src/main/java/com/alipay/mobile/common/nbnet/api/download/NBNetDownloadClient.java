package com.alipay.mobile.common.nbnet.api.download;

import java.util.concurrent.Future;

public interface NBNetDownloadClient {
    void cancelDownload(NBNetDownloadRequest nBNetDownloadRequest);

    Future<NBNetDownloadResponse> requestDownload(NBNetDownloadRequest nBNetDownloadRequest);

    Future<NBNetDownloadResponse> requestDownload(NBNetDownloadRequest nBNetDownloadRequest, NBNetDownloadCallback nBNetDownloadCallback);

    Future<NBNetDownloadResponse> requestDownload(String str, String str2, NBNetDownloadCallback nBNetDownloadCallback);
}
