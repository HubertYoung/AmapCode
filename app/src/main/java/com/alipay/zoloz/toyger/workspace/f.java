package com.alipay.zoloz.toyger.workspace;

import com.alipay.mobile.security.faceauth.InvokeType;

/* compiled from: ToygerWorkspace */
final class f implements Runnable {
    final /* synthetic */ ToygerWorkspace a;

    f(ToygerWorkspace toygerWorkspace) {
        this.a = toygerWorkspace;
    }

    public final void run() {
        try {
            if (this.a.mUploadManager != null) {
                this.a.mUploadManager.uploadBehaviourLog(InvokeType.LIVENESS_FAILED);
            }
        } catch (Exception e) {
        }
    }
}
