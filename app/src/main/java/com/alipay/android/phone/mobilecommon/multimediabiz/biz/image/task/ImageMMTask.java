package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageOriginalQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.GifDrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.ImageCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.TaskConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.APImageWorker;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadEngine;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.GifAssist;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewAssistant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableOwner;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.exception.CancelException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.manager.TaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.schedule.TaskScheduler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageMMTask<V> extends MMTask<V> {
    private static final Logger a = Logger.getLogger((String) "ImageMMTask");
    protected static ViewAssistant viewAssistant = ViewAssistant.getInstance();
    protected APImageDownLoadCallback downLoadCallback;
    protected APImageDownloadRsp downloadRsp;
    public ImageLoadReq loadReq;
    protected Context mContext;
    protected DisplayImageOptions options;
    protected ViewWrapper<View> viewWrapper;

    protected ImageMMTask() {
    }

    public ImageMMTask(ImageLoadReq loadReq2, ViewWrapper<View> wrapper) {
        this.loadReq = loadReq2;
        this.options = loadReq2.options;
        setPriority(loadReq2.options.getPriority());
        this.downLoadCallback = loadReq2.downLoadCallback;
        this.downloadRsp = loadReq2.downloadRsp;
        this.mContext = AppUtils.getApplicationContext();
        this.viewWrapper = wrapper == null ? new ViewWrapper<>(loadReq2.getTargetView(), loadReq2.cacheKey) : wrapper;
    }

    /* access modifiers changed from: protected */
    public BitmapCacheLoader getCacheLoader() {
        return ImageLoadEngine.getInstance().getCacheLoader();
    }

    /* access modifiers changed from: protected */
    public ImageCacheManager getCacheManager() {
        return APImageWorker.getInstance(AppUtils.getApplicationContext()).getImageCacheManager();
    }

    /* access modifiers changed from: protected */
    public boolean checkImageViewReused() {
        return viewAssistant.checkViewReused(this.viewWrapper);
    }

    /* access modifiers changed from: protected */
    public boolean checkImageViewReused(ImageLoadReq req) {
        if (ConfigManager.getInstance().getCommonConfigItem().useNewReuseCheck == 1) {
            return viewAssistant.checkViewReused(new ViewWrapper(req.getTargetView(), req.cacheKey));
        }
        return checkImageViewReused();
    }

    public void onMergeTask(MMTask task) {
    }

    public V taskRun() {
        return null;
    }

    public void cancel() {
        super.cancel();
    }

    public Bitmap processBitmap(Bitmap bitmap) {
        return processBitmap(this.options.getProcessor(), bitmap);
    }

    public Bitmap processBitmap(ImageWorkerPlugin processor, Bitmap bitmap) {
        if (processor == null) {
            return bitmap;
        }
        try {
            return processor.process(this.loadReq.taskModel, bitmap);
        } catch (Exception e) {
            a.e(e, "processBitmap error", new Object[0]);
            return bitmap;
        }
    }

    public void display(Bitmap bitmap, ImageLoadReq req, ViewWrapper wrapper) {
        display(bitmap, req, wrapper, true);
    }

    public void display(Bitmap bitmap, ImageLoadReq req, ViewWrapper wrapper, boolean notify) {
        if (!req.skipDisplay) {
            (notify ? new ImageDisplayTask(bitmap, req, wrapper) : new ImageDisplaySilentTask(bitmap, req, wrapper)).syncRunTask();
        }
    }

    /* access modifiers changed from: protected */
    public boolean isNeedZoom(ImageLoadReq req) {
        if (ConfigManager.getInstance().getAshmemConfSwitch()) {
            return false;
        }
        DisplayImageOptions o = req.options;
        if (!CutScaleType.KEEP_RATIO.equals(o.getCutScaleType()) || o.getWidth().intValue() == 0 || o.getHeight().intValue() == 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isNeedZoom(Bitmap bitmap, Integer preferWidth, Integer preferHeight) {
        if (ConfigManager.getInstance().getAshmemConfSwitch() || !ImageUtils.checkBitmap(bitmap) || preferWidth == null || preferWidth.intValue() <= 0 || preferHeight == null || preferHeight.intValue() <= 0) {
            return false;
        }
        if (bitmap.getWidth() > preferWidth.intValue() + 1 || bitmap.getHeight() > preferHeight.intValue() + 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public int[] getFitSize(int width, int height) {
        return getFitSize(null, width, height);
    }

    /* access modifiers changed from: protected */
    public int[] getFitSize(Size srcSize, int width, int height) {
        int[] out = ZoomHelper.getFitSize(this.mContext, srcSize, width, height);
        a.d("getFitSize: " + srcSize + ", w: " + width + ", h: " + height + ", out: " + Arrays.toString(out), new Object[0]);
        return out;
    }

    /* access modifiers changed from: protected */
    public boolean checkTask() {
        if (isCanceled()) {
            notifyCancel();
            return true;
        }
        boolean ret = waitIfPaused();
        if (!ret) {
            return ret;
        }
        notifyReuse();
        return ret;
    }

    /* access modifiers changed from: protected */
    public boolean waitIfPaused() {
        ImageLoadEngine engine = this.loadReq.loadEngine;
        AtomicBoolean pause = engine.getPause();
        if (pause.get()) {
            synchronized (engine.getPauseLock()) {
                if (pause.get()) {
                    try {
                        engine.getPauseLock().wait();
                    } catch (InterruptedException e) {
                        return true;
                    }
                }
            }
        }
        return checkImageViewReused();
    }

    public void notifyCancel() {
        notifyError(RETCODE.CANCEL, "load cancel, key: " + this.loadReq.cacheKey, new CancelException());
    }

    public void onStateChange(int state) {
        if (isCanceled()) {
            notifyCancel();
        }
    }

    /* access modifiers changed from: protected */
    public void notifyReuse() {
        notifyError(RETCODE.REUSE, "load reuse, key: " + this.loadReq.cacheKey, new CancelException());
    }

    /* access modifiers changed from: protected */
    public void notifyError(RETCODE code, String msg, Exception e) {
        notifyError(this.loadReq, code, msg, e);
    }

    /* access modifiers changed from: protected */
    public void notifyError(ImageLoadReq req, RETCODE code, String msg, Exception e) {
        if (AppUtils.inMainLooper()) {
            final ImageLoadReq imageLoadReq = req;
            final RETCODE retcode = code;
            final String str = msg;
            final Exception exc = e;
            ImageTaskEngine.get().submit((Runnable) new Runnable() {
                public void run() {
                    ImageMMTask.this.a(imageLoadReq, retcode, str, exc);
                }
            });
            return;
        }
        a(req, code, msg, e);
    }

    /* access modifiers changed from: private */
    public void a(ImageLoadReq req, RETCODE code, String msg, Exception e) {
        if (req.downLoadCallback != null) {
            APImageDownloadRsp rsp = req.downloadRsp;
            APImageRetMsg retMsg = new APImageRetMsg();
            APImageRetMsg originalRetMsg = new APImageRetMsg();
            if (CompareUtils.in(code, RETCODE.CANCEL, RETCODE.REUSE) || !checkImageViewReused(req)) {
                retMsg.setCode(code);
            } else {
                retMsg.setCode(RETCODE.REUSE);
            }
            retMsg.setMsg(msg);
            rsp.setRetmsg(retMsg);
            originalRetMsg.setCode(a(req, code));
            originalRetMsg.setMsg(msg);
            rsp.originalRetMsg = originalRetMsg;
            req.downLoadCallback.onError(rsp, e);
        }
        if (e instanceof CancelException) {
            return;
        }
        if (!RETCODE.PARAM_ERROR.equals(code) || this.options.getImageOnLoading() == null) {
            a.e(e, "notifyError code: " + code + ", msg: " + msg + ", loadReq: " + req, new Object[0]);
            long cost = System.currentTimeMillis() - req.startTime;
            Logger.TIME("ImageTask notifyError costTime: " + cost + ", " + this.loadReq.path, cost, new Object[0]);
        }
    }

    private RETCODE a(ImageLoadReq req, RETCODE code) {
        if (FileUtils.checkFile(ImageDiskCache.get().getCacheFile(req.cacheKey))) {
            return RETCODE.SUC;
        }
        if (!CutScaleType.NONE.equals(req.options.getCutScaleType())) {
            return code;
        }
        APImageOriginalQuery query = new APImageOriginalQuery(req.path);
        query.requireImageInfo = false;
        query.businessId = req.options.getBusinessId();
        if (APImageWorker.getInstance(this.mContext).getImageCacheManager().queryImageFor(query).success) {
            return RETCODE.SUC;
        }
        return code;
    }

    /* access modifiers changed from: protected */
    public void notifySuccess() {
        if (this.downLoadCallback != null) {
            APImageDownloadRsp rsp = this.downloadRsp;
            APImageRetMsg retMsg = new APImageRetMsg();
            retMsg.setMsg("load success: " + this.loadReq.source);
            retMsg.setCode(RETCODE.SUC);
            rsp.setRetmsg(retMsg);
            this.downLoadCallback.onSucc(rsp);
        }
        long coast = System.currentTimeMillis() - this.loadReq.startTime;
        if (coast > 1000) {
            Logger.TIME("ImageTask notifySuccess costTime: " + coast + ", " + this.loadReq.path, coast, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void notifySuccessWithImageData(ImageLoadReq loadReq2, byte[] imageData) {
        if (loadReq2 != null && loadReq2.downLoadCallback != null) {
            APImageDownloadRsp rsp = loadReq2.downloadRsp;
            APImageRetMsg retMsg = new APImageRetMsg();
            retMsg.setMsg("load success: " + loadReq2.source);
            retMsg.setCode(RETCODE.SUC);
            rsp.setRetmsg(retMsg);
            rsp.imageData = imageData;
            loadReq2.downLoadCallback.onSucc(rsp);
        }
    }

    public void removeTask(String taskId) {
        a().removeTask(taskId);
    }

    public void cancelTask(String taskId) {
        a().cancelTask(taskId);
    }

    private TaskScheduler a() {
        String type;
        TaskConfig taskConfig = ConfigManager.getInstance().getCommonConfigItem().taskConf;
        if (taskConfig.separateImage == 0) {
            type = TaskConstants.IMAGE_NET_TASKSERVICE;
        } else {
            type = TaskConstants.IMAGE_DJG_TASKSERVICE;
            if (this instanceof ImageUrlTask) {
                type = CommonUtils.getImageUrlTaskType(taskConfig, this.loadReq.path);
            }
        }
        return TaskManager.getInstance().getTaskScheduler(type);
    }

    public void display(Drawable drawable, ImageLoadReq req, ViewWrapper wrapper) {
        if (!req.skipDisplay) {
            new ImageDisplayTask(drawable, req, wrapper).syncRunTask();
        }
    }

    /* access modifiers changed from: protected */
    public boolean isGifTask(ImageLoadReq req, byte[] fileData) {
        return req.options.isDetectedGif() && fileData != null && ImageFileType.isGif(fileData);
    }

    /* access modifiers changed from: protected */
    public boolean isGifTask(ImageLoadReq req, File file) {
        boolean gif = file != null && req.options.isDetectedGif() && ImageFileType.isGif(file);
        if (gif) {
            GifAssist.recordGif(req.cacheKey);
        }
        return gif;
    }

    /* access modifiers changed from: protected */
    public void loadGif(File gifFile, ImageLoadReq loadReq2, ViewWrapper viewWrapper2) {
        boolean ignoreAutoCheck;
        View view = loadReq2.getTargetView();
        if (loadReq2.getLoadOptions() == null || !loadReq2.getLoadOptions().ignoreGifAutoStart) {
            ignoreAutoCheck = false;
        } else {
            ignoreAutoCheck = true;
        }
        if (view != null || (!(viewWrapper2 == null || viewWrapper2.getTargetView() == null) || ignoreAutoCheck)) {
            if (!(view != null || viewWrapper2 == null || viewWrapper2.getTargetView() == null)) {
                view = viewWrapper2.getTargetView();
            }
            String cacheKey = getGifCacheKey(view);
            GifDrawableImpl gifDrawable = (GifDrawableImpl) GifDrawableCache.getInstance().get(cacheKey);
            if (gifDrawable == null) {
                ImageInfo info = ImageInfo.getImageInfo(gifFile.getAbsolutePath());
                if (ignoreAutoCheck) {
                    gifDrawable = new GifDrawableOwner(this.mContext, gifFile.getAbsolutePath(), info.correctWidth, info.correctHeight, null);
                } else {
                    gifDrawable = new GifDrawableImpl(this.mContext, gifFile.getAbsolutePath(), info.correctWidth, info.correctHeight, null);
                }
                GifDrawableCache.getInstance().put(cacheKey, gifDrawable);
            } else {
                a.d("loadGif from memcache", new Object[0]);
            }
            gifDrawable.bindView(view);
            display((Drawable) gifDrawable, loadReq2, viewWrapper2);
            loadReq2.notifyGifController(gifDrawable);
            return;
        }
        notifySuccess();
    }

    /* access modifiers changed from: protected */
    public String getGifCacheKey(Object view) {
        if (!SimpleConfigProvider.get().getGifConfig().checkCacheKeySwitch() || view == null || this.loadReq.options.shareGifMemCache) {
            return this.loadReq.cacheKey.complexCacheKey();
        }
        return this.loadReq.cacheKey.complexCacheKey() + view.hashCode();
    }
}
