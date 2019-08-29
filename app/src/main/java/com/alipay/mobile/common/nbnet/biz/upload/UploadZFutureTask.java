package com.alipay.mobile.common.nbnet.biz.upload;

import com.alipay.mobile.common.nbnet.api.NBNetActionController;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.transport.concurrent.ZFutureTask;
import java.util.concurrent.Callable;

public class UploadZFutureTask extends ZFutureTask<NBNetUploadResponse> {
    NBNetActionController a;

    public UploadZFutureTask(Callable<NBNetUploadResponse> callable) {
        super(callable, 5);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        boolean cancel = super.cancel(mayInterruptIfRunning);
        if (this.a != null) {
            this.a.cancel();
        }
        return cancel;
    }

    public final void a(NBNetActionController nbNetActionController) {
        this.a = nbNetActionController;
    }
}
