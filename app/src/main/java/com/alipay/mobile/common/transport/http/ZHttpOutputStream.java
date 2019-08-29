package com.alipay.mobile.common.transport.http;

import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ZHttpOutputStream extends FilterOutputStream {
    /* access modifiers changed from: private */
    public HttpWorker a;
    private int b = -1;
    private TimeoutMonitor c;
    private ScheduledFuture<?> d;

    class TimeoutMonitor implements Runnable {
        long executeTime = -1;

        public TimeoutMonitor() {
        }

        public void run() {
            if (System.currentTimeMillis() >= this.executeTime) {
                try {
                    HttpWorker httpWorker = ZHttpOutputStream.this.a;
                    if (httpWorker != null) {
                        HttpUriRequest targetHttpUriRequest = httpWorker.getTargetHttpUriRequest();
                        if (targetHttpUriRequest != null) {
                            targetHttpUriRequest.abort();
                            LogCatUtil.warn((String) "ZHttpOutputStream", (String) "Timeout, initiative abort request ");
                        }
                    }
                } catch (Throwable e) {
                    LogCatUtil.warn("ZHttpOutputStream", "Timeout abort request fail.", e);
                }
            }
        }

        public void setExecuteTime(long executeTime2) {
            this.executeTime = executeTime2;
        }
    }

    public ZHttpOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void write(byte[] b2) {
        super.write(b2, 0, b2.length);
    }

    public void write(byte[] buffer, int offset, int length) {
        a();
        super.write(buffer, offset, length);
        b();
    }

    public void setHttpWorker(HttpWorker httpWorker) {
        this.a = httpWorker;
    }

    public ScheduledFuture<?> getTimeoutScheduler() {
        return this.d;
    }

    private void a() {
        if (this.a != null) {
            int timeOut = c();
            if (timeOut > 0) {
                TimeoutMonitor timeoutMonitor = d();
                timeoutMonitor.setExecuteTime(System.currentTimeMillis() + ((long) timeOut));
                this.d = NetworkAsyncTaskExecutor.schedule((Runnable) timeoutMonitor, (long) (timeOut + 300), TimeUnit.MILLISECONDS);
            }
        }
    }

    private void b() {
        try {
            if (this.d == null || this.d.isCancelled()) {
                return;
            }
            if (this.d.isDone()) {
                throw new SocketTimeoutException("write timeout");
            }
            this.d.cancel(true);
            this.d = null;
        } catch (Throwable e) {
            LogCatUtil.warn("ZHttpOutputStream", "cancel fail", e);
        }
    }

    private int c() {
        if (this.b != -1) {
            return this.b;
        }
        if (!TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.HTTP_WRITE_TIMEOUT_SWITCH, "T")) {
            this.b = 0;
            return this.b;
        }
        if (this.a != null) {
            try {
                HttpParams params = this.a.getTargetHttpUriRequest().getParams();
                if (params != null) {
                    this.b = HttpConnectionParams.getSoTimeout(params);
                }
            } catch (Throwable th) {
                this.b = 0;
                return this.b;
            }
        }
        return this.b;
    }

    private TimeoutMonitor d() {
        if (this.c != null) {
            return this.c;
        }
        TimeoutMonitor timeoutMonitor = new TimeoutMonitor();
        this.c = timeoutMonitor;
        return timeoutMonitor;
    }
}
