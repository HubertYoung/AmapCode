package com.alipay.mobile.common.transport.multimedia;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

public class DjgHttpManager extends HttpManager {
    public DjgHttpManager(Context context) {
        super(context);
    }

    public HttpResponse execute(HttpUriRequest request) {
        a(request);
        try {
            return ((DjgHttpUrlResponse) super.execute(createRequest(request)).get()).getHttpResponse();
        } catch (InterruptedException e) {
            InterruptedIOException rethrow = new InterruptedIOException();
            rethrow.initCause(e);
            throw rethrow;
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            }
            IOException rethrow2 = new IOException();
            rethrow2.initCause(cause);
            throw rethrow2;
        }
    }

    private static void a(HttpUriRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        } else if (request.getRequestLine() == null) {
            throw new IllegalArgumentException("request#getRequestLine is null");
        } else if (TextUtils.isEmpty(request.getRequestLine().getUri())) {
            throw new IllegalArgumentException("request#uri is empty");
        }
    }

    @Deprecated
    public Future<Response> execute(DjgHttpUrlRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        } else if (TextUtils.isEmpty(request.getUrl())) {
            throw new IllegalArgumentException("request#url is null");
        } else {
            if (request.getHeaders() == null) {
                request.setHeaders(new ArrayList());
            }
            LogCatUtil.printInfo("HttpManager", "execute url=[" + request.getUrl() + "]");
            return super.execute(request);
        }
    }

    public HttpWorker generateWorker(HttpUrlRequest rpcHttpRequest) {
        return new DjgHttpWorker(this, rpcHttpRequest);
    }

    public void close() {
        getHttpClient().getConnectionManager().shutdown();
    }

    public AndroidHttpClient getHttpClient() {
        return this.coreHttpManager.getDjgHttpClient();
    }

    /* access modifiers changed from: protected */
    public DjgHttpUrlRequest createRequest(HttpUriRequest request) {
        return new DjgHttpUrlRequest(request);
    }
}
