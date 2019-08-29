package com.alipay.mobile.common.transport.download;

import com.alipay.mobile.common.transport.http.HttpException;

public class DownloadIOException extends HttpException {
    public DownloadIOException(int errCode, String downloadUrl, String filePath, String msg) {
        super(Integer.valueOf(errCode), downloadUrl + "， " + filePath + "， " + msg);
        this.mCode = errCode;
        this.mMsg = downloadUrl + "， " + filePath + "， " + msg;
    }
}
