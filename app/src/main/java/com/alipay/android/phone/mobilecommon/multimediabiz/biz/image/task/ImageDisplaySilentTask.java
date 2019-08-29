package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;

public class ImageDisplaySilentTask extends ImageDisplayTask {
    public ImageDisplaySilentTask(Drawable drawable, ImageLoadReq req, ViewWrapper wrapper) {
        super(drawable, req, wrapper);
    }

    public ImageDisplaySilentTask(Bitmap bitmap, ImageLoadReq req, ViewWrapper wrapper) {
        super(bitmap, req, wrapper);
    }

    /* access modifiers changed from: protected */
    public void notifyError(ImageLoadReq req, RETCODE code, String msg, Exception e) {
    }

    /* access modifiers changed from: protected */
    public void notifySuccess() {
    }
}
