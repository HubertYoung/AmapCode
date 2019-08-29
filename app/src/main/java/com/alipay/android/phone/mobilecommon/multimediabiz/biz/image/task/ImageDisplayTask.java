package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.ReusableBitmapDrawable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDisplayUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ResourcesHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import java.util.concurrent.Future;

public class ImageDisplayTask extends ImageTask {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "ImageDisplayTask");
    private Bitmap b;
    /* access modifiers changed from: private */
    public boolean c;
    protected Drawable drawable;

    public ImageDisplayTask(Drawable drawable2, ImageLoadReq req, ViewWrapper wrapper) {
        super(req, wrapper);
        this.drawable = drawable2;
    }

    public ImageDisplayTask(Bitmap bitmap, ImageLoadReq req, boolean reusableBitmap, ViewWrapper wrapper) {
        super(req, wrapper);
        this.b = bitmap;
        this.c = reusableBitmap;
    }

    public ImageDisplayTask(Bitmap bitmap, ImageLoadReq req) {
        this(bitmap, req, true, null);
        viewAssistant.setViewTag(req.getTargetImageView(), req.cacheKey);
    }

    public ImageDisplayTask(Bitmap bitmap, ImageLoadReq req, ViewWrapper viewWrapper) {
        this(bitmap, req, true, viewWrapper);
    }

    public ImageDisplayTask(ImageLoadReq req) {
        super(req, null);
    }

    public Object call() {
        a.p("ImageDisplayTask source: " + this.loadReq.cacheKey + ";bitmap=" + this.b, new Object[0]);
        if (!this.loadReq.options.isSetNullDefaultDrawable() && this.b == null && this.drawable == null && this.loadReq.options.getImageOnLoading() == null) {
            a.d("skip setNullDrawable, source: " + this.loadReq.cacheKey, new Object[0]);
        } else if (ImageUtils.checkBitmap(this.b)) {
            if (PathUtils.isResFile(this.loadReq.source)) {
                b(ResourcesHelper.makeResDrawable(this.loadReq.options.getContext(), this.b, this.loadReq.source));
            } else {
                b(this.b);
            }
        } else if (this.drawable != null) {
            a(this.drawable);
        } else {
            b(this.loadReq.options.getImageOnLoading());
            notifyError(RETCODE.PARAM_ERROR, "param err", null);
        }
        return null;
    }

    private void a(Drawable drawable2) {
        b(drawable2);
        notifySuccess();
    }

    private void b(final Drawable drawable2) {
        if (this.options.getDisplayer() != null) {
            AppUtils.runOnUiThread(new Runnable() {
                public void run() {
                    if (!ImageDisplayTask.this.checkImageViewReused()) {
                        ImageDisplayTask.this.options.getDisplayer().display(ImageDisplayTask.this.loadReq.getTargetView(), drawable2, ImageDisplayTask.this.loadReq.source);
                    }
                }
            });
        } else {
            ImageDisplayUtils.display(drawable2, this.viewWrapper);
        }
    }

    private Bitmap a(Bitmap origin) {
        if (!ImageUtils.checkBitmap(this.b)) {
            return null;
        }
        long start = System.currentTimeMillis();
        float scale = (float) Math.sqrt((double) (9.437184E7f / ((float) origin.getByteCount())));
        int height = origin.getHeight();
        int width = origin.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        a.d("origin byteCount=" + origin.getByteCount() + ",newBM byteCount=" + newBM.getByteCount() + ",cost=" + (System.currentTimeMillis() - start), new Object[0]);
        return newBM;
    }

    private void b(Bitmap bitmap) {
        a.p("display..bitmap=" + bitmap, new Object[0]);
        if (VERSION.SDK_INT >= 24 && bitmap.getByteCount() > 94371840) {
            a.d("system version above 7.0 and bitmap too large...", new Object[0]);
            if (ConfigManager.getInstance().getCommonConfigItem().enableBitmapBytesCountCheck == 1) {
                try {
                    bitmap = a(bitmap);
                } catch (Throwable th) {
                    a.d("scale bitmap exception.", new Object[0]);
                    return;
                }
            } else {
                return;
            }
        }
        if (checkImageViewReused()) {
            a.p("checkImageViewReused " + bitmap + ";key=" + this.loadReq.cacheKey, new Object[0]);
            notifyReuse();
            return;
        }
        if (this.options.getDisplayer() != null) {
            c(bitmap);
        } else {
            ImageDisplayUtils.display(bitmap, this.viewWrapper, this.c);
        }
        notifySuccess();
    }

    private void c(final Bitmap bitmap) {
        AppUtils.runOnUiThread(new Runnable() {
            public void run() {
                BitmapDrawable d;
                if (ImageDisplayTask.this.checkImageViewReused()) {
                    ImageDisplayTask.a.p("displayer bitmap checkImageViewReused return !", new Object[0]);
                    return;
                }
                if (ImageDisplayTask.this.c) {
                    d = new ReusableBitmapDrawable(AppUtils.getResources(), bitmap);
                } else {
                    d = new BitmapDrawable(AppUtils.getResources(), bitmap);
                }
                ImageDisplayTask.this.options.getDisplayer().display(ImageDisplayTask.this.viewWrapper.getTargetView(), d, ImageDisplayTask.this.loadReq.source);
            }
        });
    }

    public Future runTask(boolean sync, ImageTaskEngine engine) {
        if (sync || this.options.isSyncLoading()) {
            call();
            return null;
        } else if (engine != null) {
            return engine.submit(this);
        } else {
            return null;
        }
    }

    public Future runTask() {
        return runTask(false, ImageTaskEngine.get());
    }

    public void syncRunTask() {
        a.p("syncRunTask start", new Object[0]);
        runTask(true, ImageTaskEngine.get());
    }
}
