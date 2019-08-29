package com.alipay.mobile.common.transport.h5;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.ArrayList;
import java.util.concurrent.Future;

public class H5NetworkManager extends HttpManager {
    public H5NetworkManager(Context context) {
        super(context);
    }

    public Future<?> enqueue(H5HttpUrlRequest h5HttpUrlRequest) {
        if (h5HttpUrlRequest == null) {
            throw new IllegalArgumentException("h5HttpUrlRequest is null");
        } else if (TextUtils.isEmpty(h5HttpUrlRequest.getUrl())) {
            throw new IllegalArgumentException("h5HttpUrlRequest#url is null");
        } else {
            if (h5HttpUrlRequest.getHeaders() == null) {
                h5HttpUrlRequest.setHeaders(new ArrayList());
            }
            LogCatUtil.printInfo("HttpManager", "enqueue url=[" + h5HttpUrlRequest.getUrl() + "]");
            return execute(h5HttpUrlRequest);
        }
    }

    public HttpWorker generateWorker(HttpUrlRequest rpcHttpRequest) {
        return new H5HttpWorker(this, rpcHttpRequest);
    }

    public AndroidHttpClient getHttpClient() {
        return this.coreHttpManager.getH5HttpClient();
    }

    public void close() {
        getHttpClient().close();
        setHttpClient(null);
        getHttpClient();
    }

    public void setHttpClient(AndroidHttpClient androidHttpClient) {
        this.coreHttpManager.setH5HttpClient(androidHttpClient);
    }
}
