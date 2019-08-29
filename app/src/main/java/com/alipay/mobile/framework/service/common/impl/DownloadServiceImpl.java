package com.alipay.mobile.framework.service.common.impl;

import android.os.Bundle;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadManager;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.DownloadService;
import java.util.ArrayList;
import java.util.concurrent.Future;
import org.apache.http.Header;

public class DownloadServiceImpl extends DownloadService {
    private final DownloadManager a = new DownloadManager(LauncherApplicationAgent.getInstance().getApplicationContext());

    public DownloadServiceImpl() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    @Deprecated
    public Future<?> addDownload(String url, String path, ArrayList<Header> headers, TransportCallback callback) {
        return this.a.addDownload(url, path, headers, callback);
    }

    public Future<?> addDownload(DownloadRequest downloadRequest) {
        return this.a.addDownload(downloadRequest);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
        this.a.close();
    }
}
