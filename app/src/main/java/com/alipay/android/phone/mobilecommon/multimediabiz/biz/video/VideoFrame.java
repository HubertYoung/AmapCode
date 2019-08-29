package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video;

import android.graphics.Bitmap;

public class VideoFrame {
    public Bitmap bitmap;
    public long position;

    public VideoFrame(long position2, Bitmap bitmap2) {
        this.position = position2;
        this.bitmap = bitmap2;
    }

    public void update(long position2, Bitmap bitmap2) {
        this.position = position2;
        this.bitmap = bitmap2;
    }
}
