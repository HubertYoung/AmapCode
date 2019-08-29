package com.alipay.inside.android.phone.mrpc.core;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.inside.android.phone.mrpc.core.utils.MiscUtils;
import com.alipay.inside.android.phone.mrpc.core.utils.TransportEnvUtil;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpManager implements Transport {
    private static final int CORE_SIZE = 10;
    private static HttpManager HTTP_MANAGER = null;
    private static final int KEEP_ALIVE_TIME = 10;
    private static final int POOL_SIZE = 15;
    private static final int QUEUE_SIZE = 20;
    public static final String TAG = "HttpManager";
    private static final ThreadFactory THREADFACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public final Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder("HttpManager.HttpWorker #");
            sb.append(this.mCount.getAndIncrement());
            Thread thread = new Thread(runnable, sb.toString());
            thread.setPriority(4);
            return thread;
        }
    };
    private long mAllConnectTimes;
    private long mAllDataSize;
    private long mAllSocketTimes;
    Context mContext;
    private AndroidHttpClient mHttpClient;
    private ThreadPoolExecutor mParallelExecutor;
    private int mRequestTimes;

    public HttpManager(Context context) {
        this.mContext = context;
        TransportEnvUtil.setContext(this.mContext);
        init();
    }

    public static final HttpManager getInstance(Context context) {
        if (HTTP_MANAGER != null) {
            return HTTP_MANAGER;
        }
        return syncCreateInstance(context);
    }

    private static final synchronized HttpManager syncCreateInstance(Context context) {
        synchronized (HttpManager.class) {
            if (HTTP_MANAGER != null) {
                HttpManager httpManager = HTTP_MANAGER;
                return httpManager;
            }
            HttpManager httpManager2 = new HttpManager(context);
            HTTP_MANAGER = httpManager2;
            return httpManager2;
        }
    }

    private void init() {
        this.mHttpClient = AndroidHttpClient.newInstance("android");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 15, 10, TimeUnit.SECONDS, new ArrayBlockingQueue(20), THREADFACTORY, new DiscardOldestPolicy());
        this.mParallelExecutor = threadPoolExecutor;
        try {
            this.mParallelExecutor.allowCoreThreadTimeOut(true);
        } catch (Exception e) {
            LoggerFactory.f().b((String) "rpc", (Throwable) e);
        }
        try {
            CookieSyncManager.createInstance(this.mContext);
            CookieManager.getInstance().setAcceptCookie(true);
        } catch (Throwable th) {
            LoggerFactory.f().b("HttpManager", "set cookie fail!", th);
        }
    }

    public Future<Response> execute(Request request) {
        if (!(request instanceof HttpUrlRequest)) {
            throw new RuntimeException("request send error.");
        }
        if (MiscUtils.isDebugger(this.mContext)) {
            LoggerFactory.f().b((String) "HttpManager", dumpPerf());
        }
        FutureTask<Response> makeTask = makeTask(generateWorker((HttpUrlRequest) request));
        this.mParallelExecutor.execute(makeTask);
        return makeTask;
    }

    private FutureTask<Response> makeTask(final HttpWorker httpWorker) {
        return new FutureTask<Response>(httpWorker) {
            /* access modifiers changed from: protected */
            public void done() {
                HttpUrlRequest request = httpWorker.getRequest();
                TransportCallback callback = request.getCallback();
                if (callback == null) {
                    super.done();
                    return;
                }
                try {
                    Response response = (Response) get();
                    if (!isCancelled()) {
                        if (!request.isCanceled()) {
                            if (response != null) {
                                callback.onPostExecute(request, response);
                            }
                            return;
                        }
                    }
                    request.cancel();
                    if (!isCancelled() || !isDone()) {
                        cancel(false);
                    }
                    callback.onCancelled(request);
                } catch (InterruptedException e) {
                    callback.onFailed(request, 7, String.valueOf(e));
                } catch (ExecutionException e2) {
                    if (e2.getCause() == null || !(e2.getCause() instanceof HttpException)) {
                        callback.onFailed(request, 6, String.valueOf(e2));
                        return;
                    }
                    HttpException httpException = (HttpException) e2.getCause();
                    callback.onFailed(request, httpException.getCode(), httpException.getMsg());
                } catch (CancellationException unused) {
                    request.cancel();
                    callback.onCancelled(request);
                } catch (Throwable th) {
                    throw new RuntimeException("An error occured while executing http request", th);
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public HttpWorker generateWorker(HttpUrlRequest httpUrlRequest) {
        return new HttpWorker(this, httpUrlRequest);
    }

    public AndroidHttpClient getHttpClient() {
        return this.mHttpClient;
    }

    public void addDataSize(long j) {
        this.mAllDataSize += j;
    }

    public void addConnectTime(long j) {
        this.mAllConnectTimes += j;
        this.mRequestTimes++;
    }

    public void addSocketTime(long j) {
        this.mAllSocketTimes += j;
    }

    public long getAverageSpeed() {
        if (this.mAllSocketTimes == 0) {
            return 0;
        }
        return ((this.mAllDataSize * 1000) / this.mAllSocketTimes) >> 10;
    }

    public long getAverageConnectTime() {
        if (this.mRequestTimes == 0) {
            return 0;
        }
        return this.mAllConnectTimes / ((long) this.mRequestTimes);
    }

    public String dumpPerf() {
        StringBuilder sb = new StringBuilder("HttpManager");
        sb.append(hashCode());
        sb.append(": Active Task = %d, Completed Task = %d, All Task = %d,Avarage Speed = %d KB/S, Connetct Time = %d ms, All data size = %d bytes, All enqueueConnect time = %d ms, All socket time = %d ms, All request times = %d times");
        return String.format(sb.toString(), new Object[]{Integer.valueOf(this.mParallelExecutor.getActiveCount()), Long.valueOf(this.mParallelExecutor.getCompletedTaskCount()), Long.valueOf(this.mParallelExecutor.getTaskCount()), Long.valueOf(getAverageSpeed()), Long.valueOf(getAverageConnectTime()), Long.valueOf(this.mAllDataSize), Long.valueOf(this.mAllConnectTimes), Long.valueOf(this.mAllSocketTimes), Integer.valueOf(this.mRequestTimes)});
    }

    public void close() {
        if (this.mParallelExecutor != null) {
            this.mParallelExecutor.shutdown();
            this.mParallelExecutor = null;
        }
        if (this.mHttpClient != null) {
            this.mHttpClient.close();
            this.mHttpClient = null;
        }
    }
}
