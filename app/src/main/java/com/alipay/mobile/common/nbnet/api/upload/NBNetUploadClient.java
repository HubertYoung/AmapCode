package com.alipay.mobile.common.nbnet.api.upload;

import java.util.concurrent.FutureTask;

public interface NBNetUploadClient {
    FutureTask<NBNetUploadResponse> addUploadTask(NBNetUploadRequest nBNetUploadRequest);
}
