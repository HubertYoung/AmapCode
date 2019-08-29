package com.alipay.mobile.common.nbnet.biz.download;

import android.text.TextUtils;
import android.util.SparseArray;
import com.alipay.android.phone.mobilesdk.socketcraft.api.DefaultWebSocketClient;
import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse;
import com.alipay.mobile.common.nbnet.biz.GlobalNBNetContext;
import com.alipay.mobile.common.nbnet.biz.constants.NBNetConfigureItem;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public final class NBNetDownloadClientImpl implements NBNetDownloadClient {
    /* access modifiers changed from: private */
    public static final String a = NBNetDownloadClientImpl.class.getSimpleName();
    private static NBNetDownloadClientImpl c = new NBNetDownloadClientImpl();
    private final SparseArray<DownloadJob> b = new SparseArray<>();

    class ResponseFutureCallable implements Callable<NBNetDownloadResponse> {
        ResponseFutureCallable() {
        }

        public /* bridge */ /* synthetic */ Object call() {
            return null;
        }
    }

    class ResponseFutureTask extends FutureTask<NBNetDownloadResponse> {
        public ResponseFutureTask(Callable<NBNetDownloadResponse> callable) {
            super(callable);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public final void set(NBNetDownloadResponse nbNetDownloadResponse) {
            super.set(nbNetDownloadResponse);
        }
    }

    class TaskTimeOutRunnable implements Runnable {
        private DownloadJob b;
        private NBNetDownloadRequest c;

        TaskTimeOutRunnable(DownloadJob downloadJob, NBNetDownloadRequest request) {
            this.b = downloadJob;
            this.c = request;
        }

        public void run() {
            try {
                this.b.a(this.c, true);
            } catch (Throwable e) {
                NBNetLogCat.a(NBNetDownloadClientImpl.a, "TaskTimeOutRunnable#run error.", e);
            }
        }
    }

    private NBNetDownloadClientImpl() {
    }

    public static NBNetDownloadClientImpl a() {
        return c;
    }

    public final Future<NBNetDownloadResponse> requestDownload(String fileId, String savePath, NBNetDownloadCallback callback) {
        NBNetDownloadRequest request = new NBNetDownloadRequest();
        request.setFileId(fileId);
        request.setSavePath(savePath);
        return requestDownload(request, callback);
    }

    public final Future<NBNetDownloadResponse> requestDownload(NBNetDownloadRequest request, NBNetDownloadCallback callback) {
        try {
            return a(request, callback);
        } catch (Throwable t) {
            NBNetLogCat.b(a, "requestDownload fail", t);
            return a(t);
        }
    }

    public final Future<NBNetDownloadResponse> requestDownload(NBNetDownloadRequest request) {
        return requestDownload(request, null);
    }

    private synchronized Future<NBNetDownloadResponse> a(NBNetDownloadRequest request, NBNetDownloadCallback callback) {
        Future future;
        if (request != null) {
            if (!TextUtils.isEmpty(request.getFileId()) && !TextUtils.isEmpty(request.getSavePath())) {
                File targetFile = new File(request.getSavePath());
                if (targetFile.getParentFile().exists() || targetFile.getParentFile().mkdirs()) {
                    int requestId = request.getRequestId();
                    NBNetLogCat.c(a, "requestDownload:" + request.toString());
                    DownloadJob downloadJob = this.b.get(requestId);
                    if (downloadJob != null) {
                        future = downloadJob.a(request, callback);
                        NBNetLogCat.a(a, "reuseJob requestId: " + requestId);
                    } else {
                        downloadJob = new DownloadJob(request, callback);
                        GlobalNBNetContext.a();
                        future = GlobalNBNetContext.c().a(downloadJob);
                        downloadJob.a(future);
                        this.b.append(requestId, downloadJob);
                    }
                    a(request, downloadJob);
                } else {
                    throw new NBNetException(String.format("Create 'savePathDir' fail, pathDir: %s", new Object[]{targetFile.getParentFile().toString()}), -18);
                }
            }
        }
        throw new NBNetException((String) "Download request or fileId or savePath is empty. ", -24);
        return future;
    }

    private void a(NBNetDownloadRequest request, DownloadJob downloadJob) {
        if (request.getReqTimeOut() <= 0) {
            int totalTimeout = TransportConfigureManager.getInstance().getIntValue(NBNetConfigureItem.DL_TOTAL_TIME_OUT);
            if (totalTimeout > 1) {
                request.setReqTimeOut(totalTimeout);
            } else {
                return;
            }
        }
        if (request.getReqTimeOut() > 0 && request.getReqTimeOut() < 4000) {
            NBNetLogCat.d(a, "registeScheduleTaskTimeout.  Current request timeout:" + request.getReqTimeOut() + ", but minimum request timeout: 4000");
            request.setReqTimeOut(DefaultWebSocketClient.MIN_CONNECTION_TIMEOUT);
        }
        if (request.getReqTimeOut() > 0) {
            NBNetLogCat.a(a, String.format("registeScheduleTaskTimeout. request total timeout: %s", new Object[]{Integer.valueOf(request.getReqTimeOut())}));
        }
        NetworkAsyncTaskExecutor.schedule((Runnable) new TaskTimeOutRunnable(downloadJob, request), (long) request.getReqTimeOut(), TimeUnit.MILLISECONDS);
    }

    public final synchronized void cancelDownload(NBNetDownloadRequest request) {
        NBNetLogCat.c(a, "cancelDownload:" + request);
        if (request != null) {
            try {
                DownloadJob downloadJob = this.b.get(request.getRequestId());
                if (downloadJob != null) {
                    downloadJob.a(request);
                }
            } catch (Throwable t) {
                NBNetLogCat.b(a, t);
            }
        }
        return;
    }

    public final synchronized void a(NBNetDownloadRequest request) {
        this.b.remove(request.getRequestId());
        NBNetLogCat.c(a, "finishDownload:" + request + ",remained jobs count:" + this.b.size());
    }

    private Future<NBNetDownloadResponse> a(Throwable throwable) {
        NBNetDownloadResponse nbNetDownloadResponse = new NBNetDownloadResponse();
        nbNetDownloadResponse.setException(throwable);
        if (throwable instanceof NBNetException) {
            NBNetException nbNetException = (NBNetException) throwable;
            nbNetDownloadResponse.setErrorMsg(nbNetException.getMessage());
            nbNetDownloadResponse.setErrorCode(nbNetException.getErrorCode());
        } else {
            nbNetDownloadResponse.setErrorMsg("The program comes back from the different dimension space");
            nbNetDownloadResponse.setErrorCode(-1);
        }
        ResponseFutureTask responseFutureTask = new ResponseFutureTask(new ResponseFutureCallable());
        responseFutureTask.set(nbNetDownloadResponse);
        return responseFutureTask;
    }
}
