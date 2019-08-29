package com.alipay.zoloz.toyger.workspace;

import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.faceauth.model.DetectTimerTask.TimerListener;
import com.alipay.zoloz.toyger.bean.ToygerFrame;

/* compiled from: ToygerWorkspace */
final class e implements TimerListener {
    final /* synthetic */ ToygerWorkspace a;
    private ToygerFrame b;

    e(ToygerWorkspace toygerWorkspace) {
        this.a = toygerWorkspace;
    }

    public final void countdown(int i) {
        if (i > 0) {
            if (!(this.b == this.a.mCurrentToygerFrame || this.a.mCurrentToygerFrame == null)) {
                this.a.recordSlice(this.a.mCurrentToygerFrame);
                this.b = this.a.mCurrentToygerFrame;
            }
            try {
                if (this.a.mSensorCollectors != null) {
                    this.a.mSensorData.add(this.a.mSensorCollectors.getData());
                }
            } catch (Throwable th) {
                BioLog.e(th);
            }
        }
    }
}
