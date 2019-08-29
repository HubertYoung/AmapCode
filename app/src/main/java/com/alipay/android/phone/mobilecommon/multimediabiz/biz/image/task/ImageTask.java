package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
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
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
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
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.exception.CancelException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskPoolParams;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.manager.TaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ImageTask<V> implements Callable<V> {
    private static final Logger a = Logger.getLogger((String) "ImageTask");
    protected static final Map<String, TaskPoolParams> sTaskPoolParamsMap = new ConcurrentHashMap(3);
    protected static ViewAssistant viewAssistant = ViewAssistant.getInstance();
    protected boolean cancelled = false;
    protected APImageDownLoadCallback downLoadCallback;
    protected APImageDownloadRsp downloadRsp;
    public ImageLoadReq loadReq;
    protected Context mContext;
    protected DisplayImageOptions options;
    protected ViewWrapper<View> viewWrapper;

    protected ImageTask() {
    }

    public ImageTask(ImageLoadReq loadReq2, ViewWrapper<View> wrapper) {
        this.loadReq = loadReq2;
        this.options = loadReq2.options;
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

    public void cancel() {
        this.cancelled = true;
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
        if (!req.skipDisplay) {
            new ImageDisplayTask(bitmap, req, wrapper).syncRunTask();
        }
    }

    public void display(Drawable drawable, ImageLoadReq req, ViewWrapper wrapper) {
        if (!req.skipDisplay) {
            new ImageDisplayTask(drawable, req, wrapper).syncRunTask();
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

    /* access modifiers changed from: protected */
    public boolean isCanceled() {
        return this.cancelled || Thread.interrupted();
    }

    /* access modifiers changed from: protected */
    public void notifyCancel() {
        notifyError(RETCODE.CANCEL, "load cancel, key: " + this.loadReq.cacheKey, new CancelException());
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
                    ImageTask.this.a(imageLoadReq, retcode, str, exc);
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
            try {
                req.downLoadCallback.onError(rsp, e);
            } catch (Throwable t) {
                a.e(t, "biz req: " + req + ", handle error", new Object[0]);
            }
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
        query.businessId = req.options.getBusinessId();
        query.requireImageInfo = false;
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
            rsp.taskModel = this.loadReq.taskModel;
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

    /* access modifiers changed from: protected */
    public void addNetTask(ImageMMTask task) {
        String type;
        TaskConfig taskConfig = ConfigManager.getInstance().getCommonConfigItem().taskConf;
        if (taskConfig.separateImage == 0) {
            type = TaskConstants.IMAGE_NET_TASKSERVICE;
        } else {
            type = TaskConstants.IMAGE_DJG_TASKSERVICE;
            if (task instanceof ImageUrlTask) {
                type = CommonUtils.getImageUrlTaskType(taskConfig, task.loadReq.path);
            }
        }
        a.d("addNetTask task: " + task + ", type: " + type + ", config: " + taskConfig, new Object[0]);
        TaskManager.getInstance().createTaskScheduler(type, a(taskConfig, type)).addTask(task);
    }

    private static TaskPoolParams a(TaskConfig config, String type) {
        TaskPoolParams params;
        synchronized (sTaskPoolParamsMap) {
            params = sTaskPoolParamsMap.get(type);
            if (params == null) {
                params = new TaskPoolParams();
                if (TaskConstants.IMAGE_URL_TASKSERVICE.equals(type)) {
                    params.mCoreSize = config.imgUrlImageOccurs;
                } else if (TaskConstants.IMAGE_DJG_TASKSERVICE.equals(type)) {
                    params.mCoreSize = config.imgDjgImageOccurs;
                } else if (TaskConstants.IMAGE_WHITE_URL_TASKSERVICE.equals(type)) {
                    params.mCoreSize = config.imgUrlWhiteOccurs;
                } else if (TaskConstants.IMAGE_BLACK_URL_TASKSERVICE.equals(type)) {
                    params.mCoreSize = config.imgUrlBlackOccurs;
                } else {
                    params.mCoreSize = TaskConstants.IMAGE_TASK_OCCURS;
                }
                params.mMaxOccurs = params.mCoreSize;
                sTaskPoolParamsMap.put(type, params);
            }
        }
        return params;
    }

    /* access modifiers changed from: protected */
    public boolean isGifTask(ImageLoadReq req, String path) {
        CommonUtils.loadGifSoLibOnce();
        boolean ret = req.options.isDetectedGif() && !TextUtils.isEmpty(path);
        if (!ret) {
            return ret;
        }
        if (SandboxWrapper.isContentUriPath(path)) {
            return ImageFileType.isGif(SandboxWrapper.getImageHeaderType(Uri.parse(path)));
        }
        return ImageFileType.isGif(new File(path));
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
    public String getGifCacheKey(Object view) {
        if (!SimpleConfigProvider.get().getGifConfig().checkCacheKeySwitch() || view == null || this.loadReq.options.shareGifMemCache) {
            return this.loadReq.cacheKey.complexCacheKey();
        }
        return this.loadReq.cacheKey.complexCacheKey() + view.hashCode();
    }
}
