package com.alipay.zoloz.toyger.widget;

/* compiled from: CircleUploadPattern */
final class a implements Runnable {
    final /* synthetic */ CircleUploadPattern a;

    a(CircleUploadPattern circleUploadPattern) {
        this.a = circleUploadPattern;
    }

    public final void run() {
        if (this.a.mContext != null) {
            this.a.intervalAction();
            this.a.mIsShowProcess = false;
        }
    }
}
