package com.alipay.mobile.common.nbnet.api.download;

import java.io.File;

public interface NBNetDownloadCallback {
    void onCancled(NBNetDownloadRequest nBNetDownloadRequest);

    void onDownloadError(NBNetDownloadRequest nBNetDownloadRequest, NBNetDownloadResponse nBNetDownloadResponse);

    void onDownloadFinished(NBNetDownloadRequest nBNetDownloadRequest, NBNetDownloadResponse nBNetDownloadResponse);

    @Deprecated
    void onDownloadProgress(NBNetDownloadRequest nBNetDownloadRequest, int i, long j, long j2);

    void onDownloadProgress(NBNetDownloadRequest nBNetDownloadRequest, int i, long j, long j2, File file);

    void onDownloadStart(NBNetDownloadRequest nBNetDownloadRequest);
}
