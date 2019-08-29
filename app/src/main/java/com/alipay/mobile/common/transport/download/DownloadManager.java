package com.alipay.mobile.common.transport.download;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.utils.DownloadUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Future;
import org.apache.http.Header;

public class DownloadManager extends HttpManager {
    public DownloadManager(Context context) {
        super(context);
    }

    public Future<?> addDownload(String url, String path, ArrayList<Header> headers, TransportCallback callback) {
        LogCatUtil.info("DownloadManager", "addDownload url=[" + url + "]");
        if (headers == null) {
            headers = new ArrayList<>();
        }
        HttpUrlRequest request = new DownloadRequest(url, path, null, headers);
        request.setTransportCallback(callback);
        return execute(request);
    }

    public Future<?> addDownload(DownloadRequest downloadRequest) {
        if (downloadRequest == null) {
            throw new IllegalArgumentException("downloadRequest may not be null");
        } else if (TextUtils.isEmpty(downloadRequest.getUrl())) {
            throw new IllegalArgumentException("downloadRequest#url may not be null");
        } else {
            if (downloadRequest.getHeaders() == null) {
                downloadRequest.setHeaders(new ArrayList());
            }
            LogCatUtil.info("DownloadManager", "addDownload url=[" + downloadRequest.getUrl() + "]");
            return execute(downloadRequest);
        }
    }

    public HttpWorker generateWorker(HttpUrlRequest rpcHttpRequest) {
        return new DownloadWorker(this, rpcHttpRequest);
    }

    @Deprecated
    public File getCacheFile(Context context, String url, String path, ArrayList<Header> headers, TransportCallback callback) {
        return DownloadUtils.createCacheFile(context, url, path, headers, callback);
    }

    public File getCacheFile(Context context, DownloadRequest downloadRequest) {
        return DownloadUtils.createCacheFile(context, downloadRequest);
    }

    public AndroidHttpClient getHttpClient() {
        return this.coreHttpManager.getDjgHttpClient();
    }
}
