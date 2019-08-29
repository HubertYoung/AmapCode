package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APDisplayer;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption.QUALITITY;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APCacheBitmapReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageBigQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageCacheQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageClearCacheQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageOriginalQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageSourceCutQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageThumbnailQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUpRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APThumbnailBitmapReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.BaseOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CacheImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.mark.AddMarkRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMGifDrawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APGifController;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APLoadStateListener;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions.Builder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheMonitor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.DrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.GifDrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.AutoStorageCleaner;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.ImageCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.ImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.ImgQueryCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.QueryCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DiskConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.TaskConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq.LoadOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.GifAssist;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDisplayUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.MemoryMonitor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ResourcesHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewAssistant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.gif.GifProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.listener.PauseOnPageChangeListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.listener.PauseOnScrollListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.listener.PauseRecyclerViewOnScrollListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.progressive.ProgressiveMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.AddMarkTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageAliasPathLoadTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageCustomDisplayTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageCustomLoadTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageDisplayTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageLoadTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageNetTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.logging.CLog;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APMultimediaTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.ImageCachePerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.manager.TaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.MarkUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ReflectUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.StringUtils;
import java.io.File;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class APImageWorker {
    private static APImageWorker a = null;
    private static int b = 10000;
    /* access modifiers changed from: private */
    public static final Logger c = Logger.getLogger((String) "APImageWorker");
    private ImageLoadEngine d;
    private Context e = null;
    private ImageCacheManager f;

    public static class MMQueryResult {
        APImageQueryResult<APImageOriginalQuery> a = null;

        public APImageQueryResult getValue() {
            return this.a;
        }

        public void setValue(APImageQueryResult value) {
            this.a = value;
        }
    }

    public class UserLeaveHintReceiver extends BroadcastReceiver {
        private final int b = 3;
        private Handler c = null;

        public UserLeaveHintReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (AppUtils.isDebug(AppUtils.getApplicationContext())) {
                APImageWorker.c.d("onReceive action: " + intent.getAction(), new Object[0]);
            }
            if ("com.alipay.mobile.framework.USERLEAVEHINT".equals(intent.getAction())) {
                TaskScheduleManager.get().execute(new Runnable() {
                    public void run() {
                        try {
                            CacheStatistics.get().uploadCacheInfoAsync();
                            CacheMonitor.get().doCheckInBackground();
                        } catch (Throwable t) {
                            APImageWorker.c.w("onReceive leave, error, t: " + t, new Object[0]);
                        }
                    }
                });
                TaskScheduleManager.get().execute(new Runnable() {
                    public void run() {
                        try {
                            CacheHitManager.getInstance().report();
                        } catch (Throwable t) {
                            APImageWorker.c.w("onReceive leave, report cache hit error, t: " + t, new Object[0]);
                        }
                    }
                });
            }
            if (this.c == null) {
                this.c = new Handler(TaskScheduleManager.get().commonLooper()) {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 3:
                                TaskScheduleManager.get().execute(new Runnable() {
                                    public void run() {
                                        AutoStorageCleaner.get().doClean();
                                    }
                                });
                                return;
                            default:
                                return;
                        }
                    }
                };
            }
            DiskConf diskConf = ConfigManager.getInstance().diskConf();
            if ("com.alipay.mobile.framework.USERLEAVEHINT".equalsIgnoreCase(intent.getAction())) {
                if (diskConf.autoCleanV2Switch == 1) {
                    this.c.removeMessages(3);
                    this.c.sendEmptyMessageDelayed(3, diskConf.backgroundDelayTime * 1000);
                }
            } else if ("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND".equalsIgnoreCase(intent.getAction())) {
                if (this.c.hasMessages(3)) {
                    this.c.removeMessages(3);
                }
                AutoStorageCleaner.get().interruptClean();
                MemoryMonitor.startKnockOutMemTask();
            }
        }
    }

    private APImageWorker(Context context) {
        this.e = context;
        this.d = ImageLoadEngine.getInstance();
        registerUserLeaveHintReceiver();
    }

    public static APImageWorker getInstance(Context context) {
        if (a == null) {
            synchronized (APImageWorker.class) {
                try {
                    if (a == null) {
                        a = new APImageWorker(context);
                    }
                }
            }
        }
        return a;
    }

    public ImageCacheManager getImageCacheManager() {
        if (this.f == null) {
            synchronized (this) {
                try {
                    if (this.f == null) {
                        this.f = new ImageCacheManager();
                    }
                }
            }
        }
        return this.f;
    }

    public APMultimediaTaskModel getTaskStatus(String taskId) {
        return APMultimediaTaskManager.getInstance(this.e).getTaskRecord(taskId);
    }

    public APMultimediaTaskModel cancelLoad(String taskId) {
        ImageNetTask task;
        if (TextUtils.isEmpty(taskId)) {
            c.d("cancelLoad taskId is null", new Object[0]);
            return null;
        }
        TaskConfig taskConfig = ConfigManager.getInstance().getCommonConfigItem().taskConf;
        if (taskConfig.separateImage == 0) {
            task = (ImageNetTask) TaskManager.getInstance().getTaskScheduler(TaskConstants.IMAGE_NET_TASKSERVICE).cancelTask(taskId);
        } else {
            task = (ImageNetTask) TaskManager.getInstance().getTaskScheduler(TaskConstants.IMAGE_DJG_TASKSERVICE).cancelTask(taskId);
            if (task == null) {
                if (taskConfig.urlWhiteImage == 1) {
                    task = (ImageNetTask) TaskManager.getInstance().getTaskScheduler(TaskConstants.IMAGE_WHITE_URL_TASKSERVICE).cancelTask(taskId);
                    if (task == null) {
                        task = (ImageNetTask) TaskManager.getInstance().getTaskScheduler(TaskConstants.IMAGE_BLACK_URL_TASKSERVICE).cancelTask(taskId);
                    }
                } else {
                    task = (ImageNetTask) TaskManager.getInstance().getTaskScheduler(TaskConstants.IMAGE_URL_TASKSERVICE).cancelTask(taskId);
                }
            }
        }
        if (task == null) {
            return null;
        }
        return task.loadReq.taskModel;
    }

    private Bitmap a(ImageLoadReq req) {
        Bitmap cache;
        boolean z;
        if (TextUtils.isEmpty(req.path) || !this.d.hasInitCacheLoader() || req.options.isWithImageDataInCallback() || (req.options.isDetectedGif() && GifAssist.isGif(req.cacheKey))) {
            return null;
        }
        BitmapCacheLoader loader = this.d.getCacheLoader();
        Bitmap pre = ImageDisplayUtils.getReusableBitmap(req.getTargetImageView());
        long start = System.currentTimeMillis();
        if (req.options.isUsingSourceType()) {
            String sourceGifPath = GifProcessor.getRelateCloudIdGifPath(req.path);
            cache = loader.getMemCache(TextUtils.isEmpty(sourceGifPath) ? req.cacheKey : req.makeCacheKey(sourceGifPath), pre, req.options.getBusinessId());
            if (cache == null && !TextUtils.isEmpty(sourceGifPath)) {
                cache = loader.getMemCache(req.cacheKey, pre, req.options.getBusinessId());
            }
        } else {
            cache = loader.getMemCache(req.cacheKey, pre, req.options.getBusinessId());
        }
        ImageCachePerf current = ImageCachePerf.getCurrent();
        if (cache != null) {
            z = true;
        } else {
            z = false;
        }
        current.addMemHitTime(z, System.currentTimeMillis() - start);
        if (cache == null && pre != null && pre.getHeight() * pre.getRowBytes() < BitmapCacheLoader.LARGE_CACHE_MEM_SIZE) {
            ImageDisplayUtils.fillReusableBitmap(req.getTargetImageView(), pre);
        }
        CLog.d("APImageWorker", "loadFromMemCache pre: %s, cache: %s, req: %s", pre, cache, req);
        return cache;
    }

    public APMultimediaTaskModel loadImageAction(int loadType, String path, ImageView imageView, Drawable defaultImage, int width, int height, APImageDownLoadCallback callback, ImageWorkerPlugin plugin, APDisplayer displayer, Size size, String caller, String business, int quality) {
        Builder builder = new Builder().showImageOnLoading(defaultImage).setProcessor(plugin).displayer(displayer).originalSize(size).caller(caller).business(business).width(Integer.valueOf(width)).height(Integer.valueOf(height)).quality(quality);
        if (loadType == 3) {
            builder.imageScaleType(CutScaleType.NONE);
        }
        return loadImageAction(new ImageLoadReq(this.d, path, imageView, callback, builder.build()));
    }

    public APMultimediaTaskModel loadImageAction(ImageLoadReq req) {
        APMGifDrawable gif;
        req.startTime = System.currentTimeMillis();
        CLog.d("APImageWorker", "loadImageAction req: %s", req);
        if (!preCheckArgs(req)) {
            return null;
        }
        APMultimediaTaskModel taskStatus = new APMultimediaTaskModel();
        req.taskModel = taskStatus;
        req.taskModel.setTaskId(req.taskId);
        Bitmap cache = null;
        if (req.getGifController() != null) {
            req.notifyGifState(0, true, 0);
            View view = req.getTargetView();
            if (req.options.shareGifMemCache || view == null) {
                gif = GifDrawableCache.getInstance().get(req.cacheKey.complexCacheKey());
            } else {
                gif = GifDrawableCache.getInstance().get(req.cacheKey.complexCacheKey() + view.hashCode());
            }
            GifDrawableCache.getInstance().get(req.cacheKey.complexCacheKey());
            if (gif != null) {
                if (view != null) {
                    gif.bindView(view);
                }
                req.notifyGifController(gif);
                taskStatus.loadFromMemCache = true;
                req.notifyGifState(0, false, 0);
                return taskStatus;
            }
            req.notifyGifState(0, false, -1);
        } else {
            cache = a(req);
        }
        boolean alasPathUpdate = false;
        if (ImageUtils.checkBitmap(cache)) {
            alasPathUpdate = true;
            taskStatus.loadFromMemCache = true;
            req.downloadRsp.loadFrom = 0;
            new ImageDisplayTask(cache, req).syncRunTask();
            if (ProgressiveMgr.getInstance().isExistKey(req.cacheKey.complexCacheKey())) {
                c.p("loadImageAction has progressive bitmap key=" + req.cacheKey.complexCacheKey(), new Object[0]);
            } else if (req.options.getAliasPath() == null || req.path.equals(ImageDiskCache.get().queryAliasKey(req.options.getAliasPath()))) {
                if (!req.options.isReturnStatusWhileInMem()) {
                    return null;
                }
                return taskStatus;
            }
        }
        req.aliasPathUpdate = alasPathUpdate;
        this.d.submit((ImageTask) req.options.getAliasPath() == null ? new ImageLoadTask(req) : new ImageAliasPathLoadTask(req));
        return taskStatus;
    }

    /* access modifiers changed from: protected */
    public boolean preCheckArgs(ImageLoadReq req) {
        if (!StringUtils.isEmptyOrNullStr(req.path) || (req.data != null && req.data.length > 0)) {
            return true;
        }
        if (req.getTargetImageView() != null) {
            ViewAssistant.getInstance().setViewTag(req.getTargetImageView(), req.cacheKey);
        }
        new ImageDisplayTask(req).syncRunTask();
        return false;
    }

    public APMultimediaTaskModel loadImageAction(APImageLoadRequest req, String businessId) {
        return loadImageAction(req, businessId, (APGifController) null, (APLoadStateListener) null);
    }

    public APMultimediaTaskModel loadImageAction(APImageLoadRequest req, String businessId, APGifController gifController, APLoadStateListener loadStateListener) {
        ImageLoadReq r = null;
        Builder builder = a(req, businessId);
        if (req.loadType == 0 || req.loadType == 1 || req.loadType == 4 || req.loadType == 3) {
            int priority = req.getPriority();
            if (priority == 10) {
                priority = 5;
            }
            builder.priority(priority);
            if (req.loadType == 3) {
                builder.imageScaleType(CutScaleType.NONE);
                builder.width(Integer.valueOf(Integer.MAX_VALUE));
                builder.height(Integer.valueOf(Integer.MAX_VALUE));
            }
            r = new ImageLoadReq(this.d, req.path, req.imageView, req.callback, builder.build());
        } else if (req.loadType == 2) {
            r = new ImageLoadReq(this.d, req.data, req.imageView, req.callback, builder.build());
        }
        r.mTimeout = req.getTimeout();
        r.thumbPath = req.thumbPath;
        r.imageId = req.imageId;
        if (req.baseOptions != null) {
            LoadOptions options = new LoadOptions();
            options.ignoreGifAutoStart = req.baseOptions.ignoreGifAutoStart;
            options.ignoreNetTask = req.baseOptions.ignoreNetTask;
            r.setLoadOptions(options);
            options.saveToDiskCache = req.baseOptions.saveToDiskCache;
            if (builder.baseOptions == null) {
                builder.baseOptions(req.baseOptions);
            } else {
                builder.baseOptions.saveToDiskCache = req.baseOptions.saveToDiskCache;
            }
            if (r.options.baseOptions == null) {
                r.options.baseOptions = new BaseOptions();
                r.options.baseOptions.ignoreGifAutoStart = req.baseOptions.ignoreGifAutoStart;
                r.options.baseOptions.ignoreNetTask = req.baseOptions.ignoreNetTask;
                r.options.baseOptions.forceSystemDecode = req.baseOptions.forceSystemDecode;
                r.options.baseOptions.saveToDiskCache = req.baseOptions.saveToDiskCache;
            }
        }
        r.setGifWrapper(gifController, loadStateListener);
        return loadImageAction(r);
    }

    private static Builder a(APImageLoadRequest req, String businessId) {
        return new Builder().showImageOnLoading(req.defaultDrawable).setProcessor(req.plugin).displayer(req.displayer).originalSize(req.srcSize).caller(req.caller).business(businessId).width(Integer.valueOf(req.width)).height(Integer.valueOf(req.height)).quality(req.getQuality()).md5(req.getMd5()).https(req.isHttps()).progressive(req.isProgressive()).base64Optimization(req.base64Optimization).withImageDataInCallback(req.withImageDataInCallback).imageScaleType(req.cutScaleType).setSecondaryCutScaleType(req.secondaryCutScaleType).usingSourceType(req.usingSourceType).setContext(req.getContext()).setBizType(req.getBizType()).detectedGif(req.detectedGif).shareGifMemCache(req.shareGifMemCache).enableSaliency(req.enableSaliency).expiredTime(req.getExpiredTime()).setBundle(req.bundle);
    }

    public APMultimediaTaskModel loadImageAction(String path, ImageView imageView, DisplayImageOptions options, APImageDownLoadCallback callback) {
        if (options != null && CutScaleType.NONE.equals(options.getCutScaleType()) && (options.getWidth() == null || options.getWidth().intValue() != Integer.MAX_VALUE || options.getHeight() == null || options.getHeight().intValue() != Integer.MAX_VALUE)) {
            options = new Builder().cloneFrom(options).width(Integer.valueOf(Integer.MAX_VALUE)).height(Integer.valueOf(Integer.MAX_VALUE)).build();
        }
        return loadImageAction(new ImageLoadReq(this.d, path, imageView, callback, options));
    }

    public APMultimediaTaskModel loadImageWithMark(String path, ImageView imageView, DisplayImageOptions options, APImageDownLoadCallback callback) {
        if (MarkUtil.isValidMarkOption(options)) {
            return loadImageAction(path, imageView, options, callback);
        }
        throw new RuntimeException("loadImageWithMark options is invalid");
    }

    public APMultimediaTaskModel loadDataImage(byte[] data, ImageView imageView, Drawable defaultImage, int width, int height, APImageDownLoadCallback callback, ImageWorkerPlugin plugin, APDisplayer displayer, Size size, String business) {
        return loadImageAction(new ImageLoadReq(this.d, data, imageView, callback, new Builder().setProcessor(plugin).showImageOnLoading(defaultImage).width(Integer.valueOf(width)).height(Integer.valueOf(height)).originalSize(size).displayer(displayer).business(business).build()));
    }

    public APMultimediaTaskModel cancelUpLoad(String taskId) {
        if (TextUtils.isEmpty(taskId)) {
            c.d("cancelUpLoad taskId is null", new Object[0]);
            return null;
        }
        Map futures = APMultimediaTaskManager.getInstance(this.e).getTaskFutureList(taskId);
        if (futures != null) {
            for (Future cancel : futures.keySet()) {
                cancel.cancel(true);
            }
            futures.clear();
        }
        APMultimediaTaskManager.getInstance(this.e).removeTaskFuture(taskId);
        APMultimediaTaskModel task = APMultimediaTaskManager.getInstance(this.e).getTaskRecord(taskId);
        if (task == null) {
            return task;
        }
        task.setStatus(2);
        APImageManager.getInstance(this.e).unregistUploadCallback(taskId);
        APMultimediaTaskManager.getInstance(this.e).updateTaskRecord(task);
        c.d("cancelUpLoad taskId =" + taskId, new Object[0]);
        return task;
    }

    public APMultimediaTaskModel uploadImage(APImageUpRequest request, String business) {
        ImageUpHandler upTask;
        if (request.option == null) {
            request.option = new APImageUploadOption();
            request.option.setQua(request.uploadType == 0 ? QUALITITY.ORIGINAL : QUALITITY.DEFAULT);
            request.option.setImage_x(request.width);
            request.option.setImage_y(request.height);
            request.option.setPublic = request.setPublic;
            request.option.setFileType(request.getFileType());
        }
        request.option.businessId = business;
        APMultimediaTaskModel taskStatus = new APMultimediaTaskModel();
        taskStatus.setSourcePath(request.path);
        APMultimediaTaskManager.getInstance(this.e).addTaskRecord(taskStatus);
        if (request.callback != null) {
            APImageManager.getInstance(this.e).registUploadCallback(taskStatus.getTaskId(), request.callback);
        }
        if (TextUtils.isEmpty(request.path)) {
            upTask = new ImageUpHandler(this.e, request.fileData, request.callback, request.option, taskStatus);
        } else {
            upTask = new ImageUpHandler(this.e, request.path, request.callback, request.option, taskStatus);
        }
        upTask.setTimeOut(request.getTimeout());
        Future future = this.d.submit(upTask);
        APMultimediaTaskManager.getInstance(this.e).addTaskFuture(taskStatus.getTaskId(), future);
        if (request.isSync) {
            try {
                long timeout = (long) request.getTimeout();
                if (timeout > 0) {
                    future.get(timeout, TimeUnit.SECONDS);
                } else {
                    future.get();
                }
            } catch (TimeoutException e2) {
                taskStatus.setStatus(3);
                APMultimediaTaskManager.getInstance(this.e).updateTaskRecord(taskStatus);
            } catch (InterruptedException e3) {
                taskStatus.setStatus(2);
                APMultimediaTaskManager.getInstance(this.e).updateTaskRecord(taskStatus);
            } catch (ExecutionException e4) {
                taskStatus.setStatus(3);
                APMultimediaTaskManager.getInstance(this.e).updateTaskRecord(taskStatus);
            } finally {
                APMultimediaTaskManager.getInstance(this.e).removeTaskFuture(taskStatus.getTaskId());
            }
        }
        return taskStatus;
    }

    public APMultimediaTaskModel uploadImage(boolean isSync, String filePath, int width, int height, APImageUploadCallback cb, APImageUploadOption option, String business) {
        APImageUpRequest request = new APImageUpRequest();
        request.isSync = isSync;
        request.path = filePath;
        request.width = width;
        request.height = height;
        request.callback = cb;
        request.option = option;
        return uploadImage(request, business);
    }

    public APMultimediaTaskModel uploadOriginalImage(boolean isSync, String filePath, APImageUploadCallback cb, String business) {
        APImageUpRequest request = new APImageUpRequest();
        request.isSync = isSync;
        request.uploadType = 0;
        request.path = filePath;
        request.callback = cb;
        return uploadImage(request, business);
    }

    public APMultimediaTaskModel uploadImage(boolean isSync, byte[] fileData, APImageUploadCallback cb, String business) {
        APImageUpRequest request = new APImageUpRequest();
        request.isSync = isSync;
        request.fileData = fileData;
        request.callback = cb;
        return uploadImage(request, business);
    }

    public static int[] calculateCutImageRect(int width, int height, int maxLen, String path) {
        return FalconFacade.get().calculateCutImageRect(width, height, maxLen, path);
    }

    public static int[] calculateCutImageRect(int width, int height, int maxLen, float scale, String path) {
        return FalconFacade.get().calculateCutImageRect(width, height, maxLen, scale, path);
    }

    public static int[] calculateDesWidthHeight(String path) {
        return ImageUtils.calculateDesWidthHeight(path);
    }

    public void optimizeView(AbsListView listView, boolean pauseOnScroll, boolean pauseOnFling, OnScrollListener listener) {
        if (listView != null) {
            OnScrollListener preOnScrollerListener = (OnScrollListener) ReflectUtils.getFieldValue(AbsListView.class, "mOnScrollListener", listView);
            if (listView instanceof OnScrollListener) {
                preOnScrollerListener = (OnScrollListener) listView;
                listener = preOnScrollerListener;
            }
            if (listener == null) {
                listener = preOnScrollerListener;
            } else if (!(preOnScrollerListener == null || listener == preOnScrollerListener)) {
                throw new IllegalStateException("This listView has been set OnScrollerListener. Please check whether conflicted");
            }
            try {
                listView.setOnScrollListener(new PauseOnScrollListener(this.d, pauseOnScroll, pauseOnFling, listener));
            } catch (Throwable e2) {
                c.d("optimizeView listview setOnScrollListener exp: " + e2.toString(), new Object[0]);
            }
        }
    }

    public void optimizeView(RecyclerView recyclerView, boolean pauseOnScroll, RecyclerView.OnScrollListener listener) {
        if (recyclerView != null) {
            RecyclerView.OnScrollListener preOnScrollerListener = (RecyclerView.OnScrollListener) ReflectUtils.getFieldValue(RecyclerView.class, "mScrollListener", recyclerView);
            if (listener == null) {
                listener = preOnScrollerListener;
            } else if (!(preOnScrollerListener == null || listener == preOnScrollerListener)) {
                throw new IllegalStateException("This recyclerView has been set OnScrollerListener. Please check whether conflicted");
            }
            try {
                recyclerView.setOnScrollListener(new PauseRecyclerViewOnScrollListener(this.d, pauseOnScroll, listener));
            } catch (Exception e2) {
                c.d("optimizeView recyclerView setOnScrollListener exp: " + e2.toString(), new Object[0]);
                try {
                    recyclerView.addOnScrollListener(new PauseRecyclerViewOnScrollListener(this.d, pauseOnScroll, listener));
                } catch (Exception e3) {
                    c.d("optimizeView viewPager addOnPageChangeListener exp: " + e2.toString(), new Object[0]);
                }
            }
        }
    }

    public void optimizeView(ViewPager viewPager, boolean pauseOnScroll, OnPageChangeListener listener) {
        if (viewPager != null) {
            try {
                viewPager.setOnPageChangeListener(new PauseOnPageChangeListener(this.d, pauseOnScroll, listener));
            } catch (Exception e2) {
                c.d("optimizeView viewPager setOnPageChangeListener exp: " + e2.toString(), new Object[0]);
                try {
                    viewPager.addOnPageChangeListener(new PauseOnPageChangeListener(this.d, pauseOnScroll, listener));
                } catch (Exception e3) {
                    c.d("optimizeView viewPager addOnPageChangeListener exp: " + e2.toString(), new Object[0]);
                }
            }
        }
    }

    public String getOriginalImagePath(String url) {
        String path = "";
        final APImageOriginalQuery query = new APImageOriginalQuery(url);
        query.requireImageInfo = false;
        ImgQueryCache cache = null;
        if (QueryCacheManager.getInstance().getConf().getImgOriginalCacheSwitch()) {
            cache = QueryCacheManager.getInstance().getImageQuery(query.getQueryKey());
        }
        if (cache != null) {
            if (!TextUtils.isEmpty(cache.path)) {
                path = cache.path;
            }
            return path;
        } else if (QueryCacheManager.getInstance().getConf().getOriginalImgPathSwitch()) {
            c.d("getOriginalImagePath off url: " + url, new Object[0]);
            return path;
        } else {
            APImageQueryResult result = null;
            if (QueryCacheManager.getInstance().getConf().getImageOriginalPathTimeoutSwith()) {
                final MMQueryResult tempResult = new MMQueryResult();
                try {
                    TaskScheduleManager.get().commonExecutor().submit(new Callable<Boolean>() {
                        public Boolean call() {
                            tempResult.setValue(APImageWorker.this.getImageCacheManager().queryImageFor(query));
                            return Boolean.valueOf(true);
                        }
                    }).get((long) QueryCacheManager.getInstance().getConf().queryTimeout, TimeUnit.MILLISECONDS);
                    result = tempResult.getValue();
                } catch (Exception e2) {
                    c.e(e2, "getOriginalImagePath exp url: " + url, new Object[0]);
                }
            } else {
                result = getImageCacheManager().queryImageFor(query);
            }
            if (result != null && result.success) {
                path = result.path;
            }
            QueryCacheManager.getInstance().put(query.getQueryKey(), result);
            return path;
        }
    }

    public boolean checkInNetTask(String path) {
        return APImageManager.getInstance(this.e).isUrlInNetTask(path);
    }

    public <T extends APImageQuery> APImageQueryResult<? extends APImageQuery> queryImageFor(final T query) {
        final ImageCacheManager manager = getImageCacheManager();
        query.path = PathUtils.extractPath(query.path);
        APImageQueryResult result = null;
        ImgQueryCache cache = QueryCacheManager.getInstance().getImageQuery(query.getQueryKey());
        if (cache == null || !cache.success) {
            if (QueryCacheManager.getInstance().getConf().getImagePathTimeoutSwith()) {
                final MMQueryResult tempResult = new MMQueryResult();
                try {
                    TaskScheduleManager.get().commonExecutor().submit(new Callable<Boolean>() {
                        public Boolean call() {
                            APImageQueryResult ret = null;
                            if (query instanceof APImageSourceCutQuery) {
                                ret = manager.queryImageFor((APImageSourceCutQuery) query);
                            } else if (query instanceof APImageBigQuery) {
                                ret = manager.queryImageFor((APImageBigQuery) query);
                            } else if (query instanceof APImageOriginalQuery) {
                                ret = manager.queryImageFor((APImageOriginalQuery) query);
                            } else if (query instanceof APImageClearCacheQuery) {
                                ret = manager.queryImageFor((APImageClearCacheQuery) query);
                            } else if (query instanceof APImageCacheQuery) {
                                ret = manager.queryImageFor((APImageCacheQuery) query);
                            } else if (query instanceof APImageThumbnailQuery) {
                                ret = manager.queryImageFor((APImageThumbnailQuery) query);
                            } else if (query instanceof APGifQuery) {
                                ret = manager.queryImageFor((APGifQuery) query);
                            }
                            tempResult.setValue(ret);
                            return Boolean.valueOf(true);
                        }
                    }).get((long) QueryCacheManager.getInstance().getConf().queryTimeout, TimeUnit.MILLISECONDS);
                    result = tempResult.getValue();
                } catch (Exception e2) {
                    c.e(e2, "getOriginalImagePath exp url: " + query.path, new Object[0]);
                }
            } else if (query instanceof APImageSourceCutQuery) {
                result = manager.queryImageFor((APImageSourceCutQuery) query);
            } else if (query instanceof APImageBigQuery) {
                result = manager.queryImageFor((APImageBigQuery) query);
            } else if (query instanceof APImageOriginalQuery) {
                result = manager.queryImageFor((APImageOriginalQuery) query);
            } else if (query instanceof APImageClearCacheQuery) {
                result = manager.queryImageFor((APImageClearCacheQuery) query);
            } else if (query instanceof APImageCacheQuery) {
                result = manager.queryImageFor((APImageCacheQuery) query);
            } else if (query instanceof APImageThumbnailQuery) {
                result = manager.queryImageFor((APImageThumbnailQuery) query);
            } else if (query instanceof APGifQuery) {
                result = manager.queryImageFor((APGifQuery) query);
            }
            if (result == null) {
                CacheHitManager.getInstance().imageCacheMissed();
                if (query instanceof APImageSourceCutQuery) {
                    result = new APImageQueryResult((APImageSourceCutQuery) query);
                } else if (query instanceof APImageBigQuery) {
                    result = new APImageQueryResult((APImageBigQuery) query);
                } else if (query instanceof APImageOriginalQuery) {
                    result = new APImageQueryResult((APImageOriginalQuery) query);
                } else if (query instanceof APImageClearCacheQuery) {
                    result = new APImageQueryResult((APImageClearCacheQuery) query);
                } else if (query instanceof APImageCacheQuery) {
                    result = new APImageQueryResult((APImageCacheQuery) query);
                } else if (query instanceof APImageThumbnailQuery) {
                    result = new APImageQueryResult((APImageThumbnailQuery) query);
                } else if (query instanceof APGifQuery) {
                    result = new APImageQueryResult((APGifQuery) query);
                }
            } else if (result.success) {
                QueryCacheManager.getInstance().put(query.getQueryKey(), result);
                CacheHitManager.getInstance().imageCacheHit();
            } else {
                CacheHitManager.getInstance().imageCacheMissed();
            }
            return result;
        }
        CacheHitManager.getInstance().imageCacheHit();
        if (query instanceof APImageSourceCutQuery) {
            result = new APImageQueryResult((APImageSourceCutQuery) query);
        } else if (query instanceof APImageBigQuery) {
            result = new APImageQueryResult((APImageBigQuery) query);
        } else if (query instanceof APImageOriginalQuery) {
            result = new APImageQueryResult((APImageOriginalQuery) query);
        } else if (query instanceof APImageClearCacheQuery) {
            result = new APImageQueryResult((APImageClearCacheQuery) query);
        } else if (query instanceof APImageCacheQuery) {
            result = new APImageQueryResult((APImageCacheQuery) query);
        } else if (query instanceof APImageThumbnailQuery) {
            result = new APImageQueryResult((APImageThumbnailQuery) query);
        } else if (query instanceof APGifQuery) {
            result = new APImageQueryResult((APGifQuery) query);
        }
        result.success = cache.success;
        result.path = cache.path;
        result.width = cache.width;
        result.height = cache.height;
        return result;
    }

    public Bitmap loadCacheBitmap(APCacheBitmapReq req) {
        BitmapCacheKey cacheKey;
        if (req == null) {
            return null;
        }
        a(req);
        if (req.usingSourceType) {
            cacheKey = new BitmapCacheKey(req.path, req.width.intValue(), req.height.intValue(), req.cutScaleType, req.plugin, req.getQuality(), req.imageMarkRequest, 1);
        } else {
            cacheKey = new BitmapCacheKey(req.path, req.width.intValue(), req.height.intValue(), req.cutScaleType, req.plugin, req.getQuality(), req.imageMarkRequest);
        }
        return loadCacheBitmap(cacheKey, req.loadFromDiskCache);
    }

    public Bitmap loadCacheBitmap(BitmapCacheKey cacheKey, boolean loadFromDiskCache) {
        if (cacheKey == null) {
            return null;
        }
        Bitmap bitmap = this.d.getCacheLoader().loadCacheBitmap(cacheKey, loadFromDiskCache);
        if (bitmap == null || !bitmap.isRecycled()) {
            return bitmap;
        }
        return null;
    }

    public Bitmap loadCacheBitmap(APThumbnailBitmapReq req) {
        Bitmap bitmap;
        if (req == null) {
            return null;
        }
        APImageThumbnailQuery query = new APImageThumbnailQuery(req.path);
        query.expectWidth = req.width;
        query.expectHeight = req.height;
        query.minWidth = req.minWidth;
        query.minHeight = req.minHeight;
        query.setQuality(req.getQuality());
        APImageQueryResult<APImageThumbnailQuery> queryImageFor = getImageCacheManager().queryImageFor(query);
        if (!queryImageFor.success) {
            return null;
        }
        APCacheBitmapReq bitmapReq = new APCacheBitmapReq(queryImageFor.query.path, queryImageFor.width, queryImageFor.height);
        bitmapReq.loadFromDiskCache = req.loadFromDiskCache;
        bitmapReq.setQuality(req.getQuality());
        if (queryImageFor instanceof ImageQueryResult) {
            bitmap = loadCacheBitmap(((ImageQueryResult) queryImageFor).cacheKey, req.loadFromDiskCache);
        } else {
            bitmap = loadCacheBitmap(bitmapReq);
        }
        if (!req.loadFromDiskCache) {
            return bitmap;
        }
        if (bitmap == null || bitmap.isRecycled()) {
            return ImageUtils.decodeBitmapByFalcon(new File(queryImageFor.path));
        }
        return bitmap;
    }

    private static void a(APCacheBitmapReq req) {
        if (req != null) {
            req.path = PathUtils.extractPath(req.path);
            if (req.srcSize != null) {
                req.cutScaleType = ImageUtils.calcCutScaleType(req.srcSize, 0.5f, Math.max(req.width.intValue(), req.width.intValue()));
            }
        }
    }

    public int deleteCache(String path) {
        return getImageCacheManager().deleteCache(path);
    }

    public AddMarkRsp addMark(String path, DisplayImageOptions options) {
        AddMarkRsp addMarkRsp;
        if (!MarkUtil.isValidMarkOption(options)) {
            throw new RuntimeException("addMark options is invalid");
        } else if (PathUtils.checkAftId(path) || PathUtils.checkAftId(options.getImageMarkRequest().getMarkId())) {
            AddMarkRsp addMarkRsp2 = new AddMarkRsp();
            APImageRetMsg retMsg = new APImageRetMsg();
            retMsg.setCode(RETCODE.SUC);
            retMsg.setMsg("Add mark success use aft id");
            addMarkRsp2.setRetmsg(retMsg);
            return addMarkRsp2;
        } else {
            try {
                addMarkRsp = new AddMarkTask(new ImageLoadReq(this.d, path, (View) null, options, (APImageDownLoadCallback) null)).call();
            } catch (Exception e2) {
                addMarkRsp = new AddMarkRsp();
                APImageRetMsg retMsg2 = new APImageRetMsg();
                retMsg2.setCode(RETCODE.UNKNOWN_ERROR);
                retMsg2.setMsg(e2.getMessage());
                addMarkRsp.setRetmsg(retMsg2);
            }
            return addMarkRsp;
        }
    }

    public void loadCustomImage(String pathOrId, View view, DisplayImageOptions options, APImageDownLoadCallback callback) {
        ImageLoadReq req = new ImageLoadReq(this.d, pathOrId, view, options, callback);
        req.startTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(req.source)) {
            if (view != null) {
                ViewAssistant.getInstance().setViewTag(view, req.cacheKey);
            }
            new ImageDisplayTask(req).syncRunTask();
            return;
        }
        Drawable cacheDrawable = DrawableCache.get().get(req.cacheKey.complexCacheKey());
        if (cacheDrawable != null) {
            ViewAssistant.getInstance().setViewTag(view, req.cacheKey);
            new ImageCustomDisplayTask(cacheDrawable, req, null).syncRunTask();
            return;
        }
        this.d.submit((ImageTask) new ImageCustomLoadTask(req));
    }

    public void loadCustomImage(Map<String, Integer> localPaths, DisplayImageOptions options, APImageDownLoadCallback callback) {
        ImageLoadReq req = new ImageLoadReq(this.d, localPaths, options, callback);
        req.startTime = System.currentTimeMillis();
        if (localPaths.isEmpty()) {
            new ImageDisplayTask(req).syncRunTask();
            return;
        }
        Drawable cacheDrawable = DrawableCache.get().get(req.cacheKey.complexCacheKey());
        if (cacheDrawable != null) {
            new ImageCustomDisplayTask(cacheDrawable, req, null).syncRunTask();
            return;
        }
        this.d.submit((ImageTask) new ImageCustomLoadTask(req));
    }

    public void registerCommonMemBusiness(String business) {
        CacheContext.get().registerCommonMemCache(business);
    }

    public boolean saveImageCache(Bitmap bitmap, String path, CacheImageOptions options, String businessId) {
        return getImageCacheManager().saveImageCache(bitmap, path, options, businessId);
    }

    public Drawable getResDrawable(String path, DisplayImageOptions options) {
        Context context = options.getContext();
        if (context != null) {
            return ResourcesHelper.getDrawable(context, path, new ImageLoadReq(this.d, path, (View) null, options, (APImageDownLoadCallback) null));
        }
        return null;
    }

    public void registerUserLeaveHintReceiver() {
        TaskScheduleManager.get().commonHandler().postDelayed(new Runnable() {
            public void run() {
                LocalBroadcastManager lc = LocalBroadcastManager.getInstance(AppUtils.getApplicationContext());
                UserLeaveHintReceiver receiver = new UserLeaveHintReceiver();
                IntentFilter filter = new IntentFilter();
                filter.addAction("com.alipay.mobile.framework.USERLEAVEHINT");
                filter.addAction("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND");
                if (lc != null) {
                    lc.registerReceiver(receiver, filter);
                }
            }
        }, (long) b);
    }
}
