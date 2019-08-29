package com.alipay.mobile.common.transport.http;

import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.concurrent.ZFutureTask;
import com.alipay.mobile.common.transport.h5.H5HttpWorker;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class HttpTask extends ZFutureTask<Response> {
    private final HttpWorker a;

    public HttpTask(HttpWorker httpWorker, int taskType) {
        super(httpWorker, taskType);
        this.a = httpWorker;
    }

    public void run() {
        super.run();
    }

    public void done() {
        this.mTaskState = 4;
        this.mObservable.notifyObservers(this);
        Request request = this.a.getOriginRequest();
        TransportCallback callback = request.getCallback();
        if (callback == null) {
            super.done();
            return;
        }
        try {
            Response response = (Response) get();
            if (isCancelled() || request.isCanceled()) {
                cancelAll(request, callback);
            } else if (response != null) {
                callback.onPostExecute(request, response);
            }
        } catch (InterruptedException e) {
            callback.onFailed(request, 7, String.valueOf(e));
        } catch (ExecutionException e2) {
            LogCatUtil.error(HttpWorker.TAG, "====done ExecutionException====" + e2.toString(), e2);
        } catch (CancellationException e3) {
            request.cancel("CancellationException");
            callback.onCancelled(request);
        } catch (Throwable t) {
            throw new RuntimeException("An error occured while executing http request", t);
        }
    }

    /* access modifiers changed from: protected */
    public void cancelAll(Request request, TransportCallback callback) {
        request.cancel();
        if (!isCancelled() || !isDone()) {
            cancel(false);
        }
        callback.onCancelled(request);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        this.a.getOriginRequest().cancel();
        return super.cancel(mayInterruptIfRunning);
    }

    public void fail(Throwable t) {
        setException(t);
        Request request = this.a.getOriginRequest();
        TransportCallback callback = request.getCallback();
        if (callback != null) {
            callback.onFailed(request, 7, t.getMessage());
        }
    }

    public String getOperationType() {
        return this.a.getOperationType();
    }

    public String getUrl() {
        return this.a.getOriginRequest().getUrl();
    }

    public boolean isH5Task() {
        return this.a instanceof H5HttpWorker;
    }
}
