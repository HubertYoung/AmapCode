package com.alipay.mobile.common.nbnet.biz.upload;

import com.alipay.mobile.common.nbnet.api.NBNetActionController;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class NBNetUploadActionController implements NBNetActionController {
    private List<Future> a = new ArrayList(2);
    private NBNetUploadWorker b;
    private boolean c = false;

    public void cancel() {
        if (!this.c) {
            this.c = true;
            a();
            if (this.b != null) {
                this.b.cancel();
            }
        }
    }

    public void stop() {
        if (!this.c) {
            this.c = true;
            a();
            if (this.b != null) {
                this.b.stop();
            }
        }
    }

    public boolean isStop() {
        return this.c;
    }

    public final void a(NBNetUploadWorker nbNetUploadWorker) {
        this.b = nbNetUploadWorker;
    }

    public final void a(Future futureTask) {
        this.a.add(futureTask);
    }

    private void a() {
        Future[] futures;
        if (!this.a.isEmpty()) {
            this.a.clear();
            for (Future futureTask : (Future[]) this.a.toArray(new Future[this.a.size()])) {
                if (futureTask != null && !futureTask.isDone() && !futureTask.isCancelled()) {
                    try {
                        futureTask.cancel(true);
                    } catch (Throwable th) {
                    }
                }
            }
            NBNetLogCat.a((String) "NBNetUploadActionController", (String) "cancelFutures finish.");
        }
    }
}
