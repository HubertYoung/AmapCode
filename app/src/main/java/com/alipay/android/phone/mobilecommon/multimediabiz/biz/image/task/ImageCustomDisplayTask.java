package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;

public class ImageCustomDisplayTask extends ImageDisplayTask {
    public ImageCustomDisplayTask(Drawable drawable, ImageLoadReq req, ViewWrapper wrapper) {
        super(drawable, req, wrapper);
        if (drawable != null && !(drawable instanceof BitmapDrawable) && drawable.getConstantState() != null) {
            this.drawable = drawable.getConstantState().newDrawable();
        }
    }
}
