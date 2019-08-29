package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.DrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.CustomLoadHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDisplayUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import java.io.File;

public class ImageCustomLoadTask extends ImageTask {
    private static final Logger a = Logger.getLogger((String) "ImageCustomLoadTask");

    public ImageCustomLoadTask(ImageLoadReq req) {
        super(req, new ViewWrapper(req.getTargetView(), req.cacheKey));
        viewAssistant.setViewTag(this.loadReq.getTargetView(), this.loadReq.cacheKey);
        if (!this.loadReq.isStateDrawableReq()) {
            a();
        }
    }

    private void a() {
        Drawable drawable = this.loadReq.options.getImageOnLoading();
        if (!(drawable == null || (drawable instanceof BitmapDrawable) || drawable.getConstantState() == null)) {
            drawable = drawable.getConstantState().newDrawable();
        }
        final Drawable d = drawable;
        if (this.loadReq.options.getDisplayer() != null) {
            AppUtils.runOnUiThread(new Runnable() {
                public void run() {
                    if (!ImageCustomLoadTask.this.checkImageViewReused()) {
                        ImageCustomLoadTask.this.loadReq.options.getDisplayer().display(ImageCustomLoadTask.this.loadReq.getTargetView(), d, ImageCustomLoadTask.this.loadReq.path);
                    }
                }
            });
        } else {
            ImageDisplayUtils.display(d, this.viewWrapper);
        }
    }

    public Object call() {
        if (!checkTask()) {
            if (!this.loadReq.isStateDrawableReq()) {
                a();
            }
            File cacheFile = b();
            a.p("cacheFile: " + cacheFile, new Object[0]);
            Drawable drawable = null;
            if (FileUtils.checkFile(cacheFile)) {
                a.p("cacheFile exists: " + cacheFile + ", for " + this.loadReq.cacheKey, new Object[0]);
                drawable = CustomLoadHelper.loadDrawable(cacheFile, this.options.getDrawableDecoder());
                if (drawable != null) {
                    DrawableCache.get().put(this.loadReq.cacheKey.complexCacheKey(), drawable);
                    new ImageCustomDisplayTask(drawable, this.loadReq, this.viewWrapper).runTask();
                }
            }
            if (drawable == null) {
                a.p("cacheFile not exists, for " + this.loadReq.path, new Object[0]);
                if (this.loadReq.isStateDrawableReq()) {
                    ImageTaskEngine.get().submit(new ImageCustomLocalTask(this.loadReq, this.viewWrapper));
                } else if (!PathUtils.isHttp(this.loadReq.path)) {
                    ImageTaskEngine.get().submit((ImageNetTask) new ImageCustomDjangoDownloadTask(this.loadReq, this.viewWrapper));
                }
            }
        }
        return null;
    }

    private File b() {
        return new File(ImageDiskCache.get().genPathByKey(this.loadReq.cacheKey.key));
    }
}
