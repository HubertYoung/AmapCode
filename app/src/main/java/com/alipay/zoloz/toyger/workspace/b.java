package com.alipay.zoloz.toyger.workspace;

import com.alipay.mobile.security.bio.utils.ScreenUtil;

/* compiled from: ToygerCaptureFragment */
final class b implements Runnable {
    final /* synthetic */ ToygerCaptureFragment a;

    b(ToygerCaptureFragment toygerCaptureFragment) {
        this.a = toygerCaptureFragment;
    }

    public final void run() {
        if (this.a.mLight > ScreenUtil.getScreenBrightness(this.a.getContext())) {
            ScreenUtil.setScreenBrightness(this.a.getActivity(), this.a.mLight);
        }
    }
}
