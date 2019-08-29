package com.alipay.zoloz.toyger.workspace;

import com.alipay.zoloz.toyger.bean.ToygerFrame;

/* compiled from: ToygerWorkspace */
final class h implements Runnable {
    final /* synthetic */ ToygerFrame a;
    final /* synthetic */ ToygerWorkspace b;

    h(ToygerWorkspace toygerWorkspace, ToygerFrame toygerFrame) {
        this.b = toygerWorkspace;
        this.a = toygerFrame;
    }

    public final void run() {
        if (this.b.mToygerCirclePattern != null) {
            this.b.mToygerCirclePattern.getAlgorithmInfoPattern().showInfo(this.b.faceAttrToAlgAttr(this.a.tgFaceAttr));
        }
    }
}
