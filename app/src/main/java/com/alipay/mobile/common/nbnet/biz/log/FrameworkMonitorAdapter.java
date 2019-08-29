package com.alipay.mobile.common.nbnet.biz.log;

import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.nbnet.biz.download.DownloadEngine;
import java.util.Map;

public class FrameworkMonitorAdapter implements FrameworkMonitor {
    public void a(NBNetContext nbNetContext) {
    }

    public void a(int errCode, String errMsg, Map<String, String> extMap) {
    }

    public boolean a(String traficCheckUrl) {
        return false;
    }

    public void a(DownloadEngine downloadEngine, NBNetDownloadRequest originalDownloadRequest) {
    }

    public void a(int totalSendedSize, int totalReceivedSize, NBNetUploadRequest nbNetUploadRequest, NBNetUploadResponse uploadResponse) {
    }
}
