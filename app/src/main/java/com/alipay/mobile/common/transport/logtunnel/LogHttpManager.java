package com.alipay.mobile.common.transport.logtunnel;

import android.content.Context;
import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.multimedia.DjgHttpManager;
import com.alipay.mobile.common.transport.multimedia.DjgHttpUrlRequest;
import org.apache.http.client.methods.HttpUriRequest;

public class LogHttpManager extends DjgHttpManager {
    public LogHttpManager(Context context) {
        super(context);
    }

    public AndroidHttpClient getHttpClient() {
        return this.coreHttpManager.getLogHttpClient();
    }

    public HttpWorker generateWorker(HttpUrlRequest rpcHttpRequest) {
        return new LogHttpWorker(this, rpcHttpRequest);
    }

    public void close() {
        getHttpClient().getConnectionManager().shutdown();
    }

    /* access modifiers changed from: protected */
    public DjgHttpUrlRequest createRequest(HttpUriRequest request) {
        return new LogHttpUrlRequest(request);
    }
}
