package com.alipay.mobile.common.nbnet.api.upload;

public interface NBNetUploadCallback {
    void onUploadError(NBNetUploadRequest nBNetUploadRequest, int i, String str);

    void onUploadFinished(NBNetUploadRequest nBNetUploadRequest, NBNetUploadResponse nBNetUploadResponse);

    void onUploadProgress(NBNetUploadRequest nBNetUploadRequest, int i, int i2, int i3);

    void onUploadStart(NBNetUploadRequest nBNetUploadRequest);
}
