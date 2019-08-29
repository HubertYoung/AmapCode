package com.alipay.mobile.common.nbnet.biz.log;

import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.nbnet.biz.download.DownloadEngine;
import java.util.Map;

public interface FrameworkMonitor {
    void a(int i, int i2, NBNetUploadRequest nBNetUploadRequest, NBNetUploadResponse nBNetUploadResponse);

    void a(int i, String str, Map<String, String> map);

    void a(NBNetContext nBNetContext);

    void a(DownloadEngine downloadEngine, NBNetDownloadRequest nBNetDownloadRequest);

    boolean a(String str);
}
