package com.alipay.mobile.common.nbnet.biz.upload;

import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadClient;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.transport.concurrent.TaskExecutorManager;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class DefaultNBNetUploadClient implements NBNetUploadClient {
    private static DefaultNBNetUploadClient a;

    private DefaultNBNetUploadClient() {
    }

    public static final DefaultNBNetUploadClient a() {
        if (a != null) {
            return a;
        }
        synchronized (DefaultNBNetUploadClient.class) {
            try {
                if (a != null) {
                    DefaultNBNetUploadClient defaultNBNetUploadClient = a;
                    return defaultNBNetUploadClient;
                }
                a = new DefaultNBNetUploadClient();
                return a;
            }
        }
    }

    public FutureTask<NBNetUploadResponse> addUploadTask(NBNetUploadRequest request) {
        NBNetUploadActionController nbNetActionController = new NBNetUploadActionController();
        request.setNBNetActionController(nbNetActionController);
        UploadZFutureTask futureTask = new UploadZFutureTask(new NBNetUploadWorker(request));
        futureTask.a(nbNetActionController);
        TaskExecutorManager.getInstance(NBNetEnvUtils.a()).execute(futureTask);
        nbNetActionController.a((Future) futureTask);
        return futureTask;
    }
}
