package com.alipay.zoloz.toyger.workspace;

import com.alipay.mobile.security.faceauth.model.DetectTimerTask.TimerListener;

/* compiled from: ToygerWorkspace */
final class m implements TimerListener {
    final /* synthetic */ ToygerWorkspace a;

    m(ToygerWorkspace toygerWorkspace) {
        this.a = toygerWorkspace;
    }

    public final void countdown(int i) {
        if (this.a.mDetectTimerTask != null && this.a.mDetectTimerTask.isTimeOut()) {
            this.a.mWorkspaceHandler.post(new n(this));
        } else if (!(this.a.mDetectTimerTask == null || this.a.mCurrentToygerFrame == null || this.a.isFirstTime)) {
            this.a.mWorkspaceHandler.post(new o(this));
        }
        if (this.a.isFirstTime) {
            this.a.isFirstTime = false;
        }
        if (this.a.mWorkspaceHandler != null) {
            this.a.mWorkspaceHandler.post(new p(this, i));
        }
    }
}
