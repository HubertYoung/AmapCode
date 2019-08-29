package com.alipay.mobile.common.transport.logtunnel;

import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.multimedia.DjgHttpWorker;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class LogHttpWorker extends DjgHttpWorker {
    public LogHttpWorker(HttpManager httpManager, HttpUrlRequest request) {
        super(httpManager, request);
    }

    /* access modifiers changed from: protected */
    public void addRequestHeaders() {
        printHeaderLog(getTargetHttpUriRequest().getAllHeaders());
    }

    /* access modifiers changed from: protected */
    public void copyHeaders() {
    }

    /* access modifiers changed from: protected */
    public void addCookie2Header() {
    }

    /* access modifiers changed from: protected */
    public void whenExceptionFlushUploadLog() {
        LogCatUtil.info(HttpWorker.TAG, "whenExceptionFlushUploadLog, upload log request no execute it.");
    }

    /* access modifiers changed from: protected */
    public void monitorErrorLog(Throwable e) {
        LogCatUtil.error(HttpWorker.TAG, "LogHttpWorker. Execute fail. ", e);
    }
}
