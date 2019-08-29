package com.alipay.mobile.common.transport.http;

import android.content.Context;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.http.inner.CoreHttpManager;
import com.alipay.mobile.common.transport.rpc.RpcHttpWorker;
import java.util.concurrent.Future;

public class HttpManager {
    public static final String TAG = "HttpManager";
    protected CoreHttpManager coreHttpManager;

    public HttpManager(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        this.coreHttpManager = CoreHttpManager.getInstance(context);
    }

    public Future<Response> execute(Request request) {
        return this.coreHttpManager.execute(this, request);
    }

    public AndroidHttpClient getHttpClient() {
        return this.coreHttpManager.getHttpClient();
    }

    public void addDataSize(long size) {
        this.coreHttpManager.addDataSize(size);
    }

    public void addConnectTime(long time) {
        this.coreHttpManager.addConnectTime(time);
    }

    public void addSocketTime(long time) {
        this.coreHttpManager.addSocketTime(time);
    }

    public long getAverageSpeed() {
        return this.coreHttpManager.getAverageSpeed();
    }

    public long getAverageConnectTime() {
        return this.coreHttpManager.getAverageConnectTime();
    }

    public String dumpPerf() {
        return this.coreHttpManager.dumpPerf(getClass().getSimpleName());
    }

    public void close() {
        this.coreHttpManager.close();
    }

    public Context getContext() {
        return this.coreHttpManager.getContext();
    }

    public HttpWorker generateWorker(HttpUrlRequest rpcHttpRequest) {
        return new RpcHttpWorker(this, rpcHttpRequest);
    }

    public void setHttpClient(AndroidHttpClient androidHttpClient) {
        this.coreHttpManager.setHttpClient(androidHttpClient);
    }
}
