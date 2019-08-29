package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.DrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq.StateDrawableParam;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.CustomLoadHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDisplayUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import java.io.File;

public class ImageCustomLocalTask extends ImageTask {
    public ImageCustomLocalTask(ImageLoadReq req, ViewWrapper wrapper) {
        super(req, wrapper);
        a();
    }

    private void a() {
        Drawable drawable = this.loadReq.options.getImageOnLoading();
        if (!(drawable == null || (drawable instanceof BitmapDrawable) || drawable.getConstantState() == null)) {
            drawable = drawable.getConstantState().newDrawable();
        }
        if (drawable != null) {
            final Drawable d = drawable;
            if (this.loadReq.options.getDisplayer() != null) {
                AppUtils.runOnUiThread(new Runnable() {
                    public void run() {
                        if (!ImageCustomLocalTask.this.checkImageViewReused()) {
                            ImageCustomLocalTask.this.loadReq.options.getDisplayer().display(ImageCustomLocalTask.this.loadReq.getTargetView(), d, ImageCustomLocalTask.this.loadReq.path);
                        }
                    }
                });
            } else {
                ImageDisplayUtils.display(d, this.viewWrapper);
            }
        }
    }

    public Object call() {
        if (!checkTask() && this.loadReq.isStateDrawableReq()) {
            StateDrawableParam params = this.loadReq.getStateDrawableParam();
            StateListDrawable stateDrawable = null;
            for (String key : params.localPaths.keySet()) {
                if (stateDrawable == null) {
                    stateDrawable = new StateListDrawable();
                }
                File file = new File(key);
                int state = params.localPaths.get(key).intValue();
                int[] iArr = {state};
                stateDrawable.addState(iArr, CustomLoadHelper.loadDrawable(file, this.options.getDrawableDecoder()));
            }
            if (stateDrawable != null) {
                DrawableCache.get().put(this.loadReq.cacheKey.complexCacheKey(), stateDrawable);
            }
            new ImageCustomDisplayTask(stateDrawable, this.loadReq, this.viewWrapper).runTask();
        }
        return null;
    }
}
