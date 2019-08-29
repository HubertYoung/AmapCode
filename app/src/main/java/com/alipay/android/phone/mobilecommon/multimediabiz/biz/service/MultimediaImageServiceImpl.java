package com.alipay.android.phone.mobilecommon.multimediabiz.biz.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageAssist;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APCacheBitmapReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageCacheQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUpRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APThumbnailBitmapReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CacheImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.mark.AddMarkRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMGifDrawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions.Builder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.GifDrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.QueryCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.APImageManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.APImageWorker;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadEngine;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.AdjusterHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.adapter.ImageRequestAdapter;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MultimediaImageServiceImpl extends MultimediaImageService {
    private static final String TAG = "MultimediaImageServiceImpl";
    private Context mContext;
    private APImageWorker mImageWorker;
    private int mLoadHeight = 240;
    private int mLoadWidth = 240;
    private final int mUpHeight = 1280;
    private final int mUpWidth = 1280;

    class MMBitmap {
        private Bitmap val;

        MMBitmap() {
        }

        public void setVal(Bitmap bitmap) {
            this.val = bitmap;
        }

        public Bitmap getVal() {
            return this.val;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getMicroApplicationContext().getApplicationContext();
        if (this.mContext != null) {
            DisplayMetrics metrics = this.mContext.getResources().getDisplayMetrics();
            int w = metrics.widthPixels;
            int h = metrics.heightPixels;
            if ((w > 0 && w <= 16000) || (h > 0 && h <= 16000)) {
                this.mLoadWidth = Math.min(w, h) / 4;
            }
            this.mLoadHeight = this.mLoadWidth;
        }
        ConfigManager.getInstance().updateConfig(true);
        MultimediaImageAssist.setupImageService(this);
        AdjusterHelper.init();
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    /* access modifiers changed from: private */
    public APImageWorker getImageWorker() {
        if (this.mImageWorker == null) {
            synchronized (this) {
                if (this.mImageWorker == null) {
                    this.mImageWorker = APImageWorker.getInstance(this.mContext);
                }
            }
        }
        return this.mImageWorker;
    }

    public void cancelLoad(String taskId) {
        getImageWorker().cancelLoad(taskId);
    }

    public void cancelUp(String taskId) {
        getImageWorker().cancelUpLoad(taskId);
    }

    public APMultimediaTaskModel getLoadTaskStatus(String taskId) {
        return getImageWorker().getTaskStatus(taskId);
    }

    public APMultimediaTaskModel getUpTaskStatus(String taskId) {
        return getImageWorker().getTaskStatus(taskId);
    }

    public void unregisteLoadCallBack(String taskId, APImageDownLoadCallback callBack) {
        APImageManager.getInstance(this.mContext).unregistLoadCallback(taskId, callBack);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage) {
        return loadImage(path, imageView, defaultImage, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, ImageWorkerPlugin plugin) {
        return loadImage(path, imageView, defaultImage, plugin, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, APImageDownLoadCallback callback) {
        return loadImage(path, callback, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, APImageDownLoadCallback callback, ImageWorkerPlugin plugin) {
        return loadImage(path, callback, plugin, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, int width, int height) {
        return loadImage(path, imageView, (Drawable) null, (APImageDownLoadCallback) null, width, height, (ImageWorkerPlugin) null);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, int width, int height) {
        return loadImage(path, imageView, defaultImage, (APImageDownLoadCallback) null, width, height, (ImageWorkerPlugin) null);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, int defaultResId, int width, int height) {
        throw new UnsupportedOperationException("no more supported, please use 'loadImage(String path, ImageView imageView, DisplayImageOptions options, APImageDownLoadCallback callback, String business)'");
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, boolean isIdle, int width, int height) {
        return loadImage(path, imageView, defaultImage, isIdle, width, height, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, int width, int height, ImageWorkerPlugin plugin) {
        return loadImage(path, imageView, defaultImage, width, height, plugin, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, APImageDownLoadCallback callback, int width, int height) {
        return loadImage(path, callback, width, height, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, APImageDownLoadCallback callback, int width, int height, ImageWorkerPlugin plugin) {
        return loadImage(path, callback, width, height, plugin, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, APImageDownLoadCallback callback, int width, int height, ImageWorkerPlugin plugin) {
        return loadImage(path, imageView, defaultImage, callback, width, height, plugin, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, APImageDownLoadCallback callback, int width, int height, ImageWorkerPlugin plugin, Size size) {
        return loadImage(path, imageView, defaultImage, callback, width, height, plugin, size, APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(APImageLoadRequest req) {
        return loadImage(req, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(byte[] data, ImageView imageView, Drawable defaultImage, int width, int height, APImageDownLoadCallback callback, ImageWorkerPlugin plugin) {
        return loadImage(data, imageView, defaultImage, width, height, callback, plugin, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, DisplayImageOptions options, APImageDownLoadCallback callback) {
        return loadImage(path, imageView, options, callback, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadImageWithMark(String path, ImageView imageView, DisplayImageOptions options, APImageDownLoadCallback callback) {
        return loadImageWithMark(path, imageView, options, callback, APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadOriginalImage(String path, ImageView imageView, Drawable defaultImage, APImageDownLoadCallback callback) {
        return loadOriginalImage(path, imageView, defaultImage, callback, APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel loadOriginalImage(String path, ImageView imageView, Drawable defaultImage, int width, int height, APImageDownLoadCallback callback) {
        return loadOriginalImage(path, imageView, defaultImage, width, height, callback, APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel uploadImage(String filePath) {
        return uploadImage(filePath, (APImageUploadCallback) null, (APImageUploadOption) null);
    }

    public APMultimediaTaskModel uploadImage(String filePath, boolean isSync) {
        return uploadImage(filePath, isSync, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel uploadImage(String filePath, APImageUploadCallback cb) {
        return uploadImage(filePath, cb, (APImageUploadOption) null);
    }

    public APMultimediaTaskModel uploadImage(String filePath, APImageUploadCallback cb, APImageUploadOption option) {
        return uploadImage(filePath, cb, option, APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel uploadImage(String filePath, APImageUploadOption option) {
        return uploadImage(filePath, (APImageUploadCallback) null, option);
    }

    public APMultimediaTaskModel uploadImage(byte[] fileData) {
        return uploadImage(fileData, (APImageUploadCallback) null);
    }

    public APMultimediaTaskModel uploadImage(byte[] fileData, APImageUploadCallback cb) {
        return uploadImage(fileData, cb, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel uploadOriginalImage(String filePath) {
        return uploadOriginalImage(filePath, (APImageUploadCallback) null);
    }

    public APMultimediaTaskModel uploadOriginalImage(String filePath, boolean isSync, APImageUploadCallback cb) {
        return uploadOriginalImage(filePath, isSync, cb, APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel uploadOriginalImage(String filePath, APImageUploadCallback cb) {
        return uploadOriginalImage(filePath, cb, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel uploadImage(APImageUpRequest req) {
        return uploadImage(req, (String) APConstants.DEFAULT_BUSINESS);
    }

    public int[] calculateCutImageRect(int width, int height, int maxLen, String path) {
        return FalconFacade.get().calculateCutImageRect(width, height, maxLen, path);
    }

    public int[] calculateCutImageRect(int width, int height, int maxLen, float scale, String path) {
        return FalconFacade.get().calculateCutImageRect(width, height, maxLen, scale, path);
    }

    public int[] calculateDesWidthHeight(String path) {
        return ImageUtils.calculateDesWidthHeight(path);
    }

    public void optimizeView(AbsListView view, OnScrollListener listener) {
        optimizeView(view, false, true, listener);
    }

    public void optimizeView(AbsListView view, boolean pauseOnScroll, boolean pauseOnFling, OnScrollListener listener) {
        getImageWorker().optimizeView(view, pauseOnScroll, pauseOnFling, listener);
    }

    public void optimizeView(ViewPager view, boolean pauseOnScroll, OnPageChangeListener listener) {
        getImageWorker().optimizeView(view, pauseOnScroll, listener);
    }

    public void optimizeView(RecyclerView view, boolean pauseOnScroll, RecyclerView.OnScrollListener listener) {
        getImageWorker().optimizeView(view, pauseOnScroll, listener);
    }

    public String getOriginalImagePath(String url) {
        return getImageWorker().getOriginalImagePath(url);
    }

    public boolean checkInNetTask(String key) {
        return getImageWorker().checkInNetTask(key);
    }

    public Size getDjangoNearestImageSize(Size size) {
        return PathUtils.getDjangoNearestImageSize(size);
    }

    public <T extends APImageQuery> APImageQueryResult<?> queryImageFor(T query) {
        if (APImageQuery.isSuperSize(query.width)) {
            query.width = Integer.valueOf(16000);
        }
        if (APImageQuery.isSuperSize(query.height)) {
            query.height = Integer.valueOf(16000);
        }
        if (query instanceof APImageCacheQuery) {
            APImageCacheQuery cacheQuery = (APImageCacheQuery) query;
            if (cacheQuery.width == -1 && cacheQuery.height == -1 && CutScaleType.SCALE_AUTO_LIMIT.equals(cacheQuery.cutScaleType)) {
                cacheQuery.width = this.mLoadWidth;
                cacheQuery.height = this.mLoadHeight;
            }
        } else if ((query instanceof APGifQuery) && !(query.width.intValue() == Integer.MAX_VALUE && query.height.intValue() == Integer.MAX_VALUE)) {
            query.width = Integer.valueOf(Integer.MAX_VALUE);
            query.height = Integer.valueOf(Integer.MAX_VALUE);
        }
        return getImageWorker().queryImageFor(query);
    }

    public Bitmap zoomBitmap(Bitmap src, int targetWidth, int targetHeight) {
        return ImageUtils.zoomBitmap(src, targetWidth, targetHeight);
    }

    public Bitmap loadCacheBitmap(final APCacheBitmapReq req) {
        long start = System.currentTimeMillis();
        Bitmap bitmap = null;
        if (!(req instanceof APThumbnailBitmapReq)) {
            if (!(req == null || req.cutScaleType == null || !CutScaleType.SCALE_AUTO_LIMIT.equals(req.cutScaleType))) {
                req.width = Integer.valueOf(this.mLoadWidth);
                req.height = Integer.valueOf(this.mLoadHeight);
            }
            bitmap = getImageWorker().loadCacheBitmap(req);
        } else if (QueryCacheManager.getInstance().getConf().loadThumbImgTimeoutSwitch()) {
            final MMBitmap tmp = new MMBitmap();
            try {
                TaskScheduleManager.get().commonExecutor().submit(new Callable<Boolean>() {
                    public Boolean call() {
                        tmp.setVal(MultimediaImageServiceImpl.this.getImageWorker().loadCacheBitmap((APThumbnailBitmapReq) req));
                        return Boolean.valueOf(true);
                    }
                }).get((long) QueryCacheManager.getInstance().getConf().queryTimeout, TimeUnit.MILLISECONDS);
                bitmap = tmp.getVal();
            } catch (Exception e) {
                Logger.E((String) TAG, (Throwable) e, "loadCacheBitmap in main thread exp: " + (System.currentTimeMillis() - start), new Object[0]);
            }
        } else {
            bitmap = getImageWorker().loadCacheBitmap((APThumbnailBitmapReq) req);
        }
        Logger.TIME("loadCacheBitmap costTime: " + (System.currentTimeMillis() - start) + ", " + req, System.currentTimeMillis() - start, new Object[0]);
        return bitmap;
    }

    public int deleteCache(String path) {
        return getImageWorker().deleteCache(path);
    }

    public void loadCustomImage(String pathOrId, View view, DisplayImageOptions options, APImageDownLoadCallback callback) {
        loadCustomImage(pathOrId, view, options, callback, APConstants.DEFAULT_BUSINESS);
    }

    public void loadCustomImage(Map<String, Integer> localPaths, DisplayImageOptions options, APImageDownLoadCallback callback, String business) {
        getImageWorker().loadCustomImage(localPaths, new Builder().cloneFrom(options).business(business).build(), callback);
    }

    public AddMarkRsp addMark(String path, DisplayImageOptions options) {
        return getImageWorker().addMark(path, options);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, String business) {
        return getImageWorker().loadImageAction(path, imageView, new Builder().imageScaleType(CutScaleType.SCALE_AUTO_LIMIT).width(Integer.valueOf(this.mLoadWidth)).height(Integer.valueOf(this.mLoadHeight)).showImageOnLoading(defaultImage).business(business).build(), (APImageDownLoadCallback) null);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, ImageWorkerPlugin plugin, String business) {
        return getImageWorker().loadImageAction(path, imageView, new Builder().imageScaleType(CutScaleType.SCALE_AUTO_LIMIT).width(Integer.valueOf(this.mLoadWidth)).height(Integer.valueOf(this.mLoadHeight)).showImageOnLoading(defaultImage).setProcessor(plugin).business(business).build(), (APImageDownLoadCallback) null);
    }

    public APMultimediaTaskModel loadImage(String path, APImageDownLoadCallback callback, String business) {
        return getImageWorker().loadImageAction(path, (ImageView) null, new Builder().imageScaleType(CutScaleType.SCALE_AUTO_LIMIT).width(Integer.valueOf(this.mLoadWidth)).height(Integer.valueOf(this.mLoadHeight)).business(business).build(), callback);
    }

    public APMultimediaTaskModel loadImage(String path, APImageDownLoadCallback callback, ImageWorkerPlugin plugin, String business) {
        return getImageWorker().loadImageAction(path, (ImageView) null, new Builder().imageScaleType(CutScaleType.SCALE_AUTO_LIMIT).width(Integer.valueOf(this.mLoadWidth)).height(Integer.valueOf(this.mLoadHeight)).setProcessor(plugin).business(business).build(), callback);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, int width, int height, String business) {
        return loadImage(path, imageView, (Drawable) null, (APImageDownLoadCallback) null, width, height, (ImageWorkerPlugin) null, business);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, int width, int height, String business) {
        return loadImage(path, imageView, defaultImage, (APImageDownLoadCallback) null, width, height, (ImageWorkerPlugin) null, business);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, boolean isIdle, int width, int height, String business) {
        return getImageWorker().loadImageAction(0, path, imageView, defaultImage, width, height, null, null, null, null, null, business, -1);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, int width, int height, ImageWorkerPlugin plugin, String business) {
        return loadImage(path, imageView, defaultImage, (APImageDownLoadCallback) null, width, height, plugin, business);
    }

    public boolean saveImageCache(Bitmap bitmap, String path, CacheImageOptions options, String businessId) {
        if (options != null && (options.width == null || options.height == null)) {
            options.width = Integer.valueOf(this.mLoadWidth);
            options.height = Integer.valueOf(this.mLoadHeight);
            options.cutScaleType = CutScaleType.SCALE_AUTO_LIMIT;
        }
        return getImageWorker().saveImageCache(bitmap, path, options, businessId);
    }

    public String buildUrl(String id, DisplayImageOptions options, Bundle extraConfig) {
        if (TextUtils.isEmpty(id)) {
            throw new IllegalArgumentException("id is empty or null!!");
        }
        if (options != null && (options.width == null || options.height == null)) {
            options = new Builder().cloneFrom(options).width(Integer.valueOf(0)).height(Integer.valueOf(0)).build();
        }
        return ImageRequestAdapter.buildUrl(new ImageLoadReq((ImageLoadEngine) null, id, (View) null, options, (APImageDownLoadCallback) null), extraConfig);
    }

    public String saveIntoCache(byte[] bytes, String business) {
        return ImageDiskCache.get().saveIntoCache(bytes, business);
    }

    public APMultimediaTaskModel loadImage(String path, APImageDownLoadCallback callback, int width, int height, String business) {
        return loadImage(path, (ImageView) null, (Drawable) null, callback, width, height, (ImageWorkerPlugin) null, business);
    }

    public APMultimediaTaskModel loadImage(String path, APImageDownLoadCallback callback, int width, int height, ImageWorkerPlugin plugin, String business) {
        return loadImage(path, (ImageView) null, (Drawable) null, callback, width, height, plugin, business);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, APImageDownLoadCallback callback, int width, int height, ImageWorkerPlugin plugin, String business) {
        return loadImage(path, imageView, defaultImage, callback, width, height, plugin, null, business);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, Drawable defaultImage, APImageDownLoadCallback callback, int width, int height, ImageWorkerPlugin plugin, Size size, String business) {
        return getImageWorker().loadImageAction(0, path, imageView, defaultImage, width, height, callback, plugin, null, size, null, business, -1);
    }

    public APMultimediaTaskModel loadImage(APImageLoadRequest req, String business) {
        if (req == null) {
            return null;
        }
        return getImageWorker().loadImageAction(req, business);
    }

    public APMultimediaTaskModel loadImage(byte[] data, ImageView imageView, Drawable defaultImage, int width, int height, APImageDownLoadCallback callback, ImageWorkerPlugin plugin, String business) {
        return getImageWorker().loadDataImage(data, imageView, defaultImage, width, height, callback, plugin, null, null, business);
    }

    public APMultimediaTaskModel loadOriginalImage(String path, ImageView imageView, Drawable defaultImage, APImageDownLoadCallback callback, String business) {
        return getImageWorker().loadImageAction(3, path, imageView, defaultImage, Integer.MAX_VALUE, Integer.MAX_VALUE, callback, null, null, null, null, business, -1);
    }

    public APMultimediaTaskModel loadOriginalImage(String path, ImageView imageView, Drawable defaultImage, int width, int height, APImageDownLoadCallback callback, String business) {
        return getImageWorker().loadImageAction(3, path, imageView, defaultImage, width, height, callback, null, null, null, null, business, -1);
    }

    public APMultimediaTaskModel loadImage(String path, ImageView imageView, DisplayImageOptions options, APImageDownLoadCallback callback, String business) {
        Builder builder = new Builder().cloneFrom(options).business(business).quality(options.getQuality()).priority(options.getPriority()).progressive(options.isProgressive()).timeout(options.getTimeout()).setBundle(options.bundle);
        if (CutScaleType.NONE.equals(options.getCutScaleType())) {
            builder.width(Integer.valueOf(Integer.MAX_VALUE)).height(Integer.valueOf(Integer.MAX_VALUE));
        } else if (options.width == null || options.height == null) {
            builder.width(Integer.valueOf(this.mLoadWidth)).height(Integer.valueOf(this.mLoadHeight)).imageScaleType(CutScaleType.SCALE_AUTO_LIMIT);
        }
        if (!APConstants.DEFAULT_BUSINESS.equals(options.getBizType())) {
            builder.setBizType(options.getBizType());
        }
        return getImageWorker().loadImageAction(path, imageView, builder.build(), callback);
    }

    public APMultimediaTaskModel loadImageWithMark(String path, ImageView imageView, DisplayImageOptions options, APImageDownLoadCallback callback, String business) {
        return getImageWorker().loadImageWithMark(path, imageView, new Builder().cloneFrom(options).business(business).quality(options.getQuality()).priority(options.getPriority()).progressive(options.isProgressive()).timeout(options.getTimeout()).build(), callback);
    }

    public void loadCustomImage(String pathOrId, View view, DisplayImageOptions options, APImageDownLoadCallback callback, String business) {
        getImageWorker().loadCustomImage(pathOrId, view, new Builder().cloneFrom(options).business(business).build(), callback);
    }

    public void registerCommonMemBusiness(String business) {
        getImageWorker().registerCommonMemBusiness(business);
    }

    public Drawable getResDrawable(String path, DisplayImageOptions options) {
        return getImageWorker().getResDrawable(path, options);
    }

    public APMultimediaTaskModel uploadImage(String filePath, String business) {
        return uploadImage(filePath, false, business);
    }

    public APMultimediaTaskModel uploadImage(String filePath, boolean isSync, String business) {
        return getImageWorker().uploadImage(isSync, filePath, 1280, 1280, null, null, business);
    }

    public APMultimediaTaskModel uploadImage(String filePath, APImageUploadCallback cb, String business) {
        return uploadImage(filePath, cb, null, business);
    }

    public APMultimediaTaskModel uploadImage(String filePath, APImageUploadCallback cb, APImageUploadOption option, String business) {
        return getImageWorker().uploadImage(false, filePath, -1, -1, cb, option, business);
    }

    public APMultimediaTaskModel uploadImage(String filePath, APImageUploadOption option, String business) {
        return uploadImage(filePath, null, option, business);
    }

    public APMultimediaTaskModel uploadImage(byte[] fileData, String business) {
        return uploadImage(fileData, (APImageUploadCallback) null, business);
    }

    public APMultimediaTaskModel uploadImage(byte[] fileData, APImageUploadCallback cb, String business) {
        return getImageWorker().uploadImage(false, fileData, cb, business);
    }

    public APMultimediaTaskModel uploadOriginalImage(String filePath, String business) {
        return uploadOriginalImage(filePath, (APImageUploadCallback) null, business);
    }

    public APMultimediaTaskModel uploadOriginalImage(String filePath, boolean isSync, APImageUploadCallback cb, String business) {
        return getImageWorker().uploadOriginalImage(isSync, filePath, cb, business);
    }

    public APMultimediaTaskModel uploadOriginalImage(String filePath, APImageUploadCallback cb, String business) {
        return getImageWorker().uploadOriginalImage(false, filePath, cb, business);
    }

    public APMultimediaTaskModel uploadImage(APImageUpRequest req, String business) {
        if (req == null) {
            return null;
        }
        return getImageWorker().uploadImage(req, business);
    }

    public APMultimediaTaskModel loadGifImage(APGifLoadRequest req, String business) {
        Logger.D(TAG, "loadGifImage start ********** path=" + req.path + ";req=" + req, new Object[0]);
        if (!(req.width == Integer.MAX_VALUE && req.height == Integer.MAX_VALUE)) {
            req.width = Integer.MAX_VALUE;
            req.height = Integer.MAX_VALUE;
        }
        return getImageWorker().loadImageAction((APImageLoadRequest) req, business, req.gifController, req.loadStateListener);
    }

    public APMGifDrawable queryGifMem(APGifQuery req, String business) {
        if (!(req.width.intValue() == Integer.MAX_VALUE && req.height.intValue() == Integer.MAX_VALUE)) {
            req.width = Integer.valueOf(Integer.MAX_VALUE);
            req.height = Integer.valueOf(Integer.MAX_VALUE);
        }
        return GifDrawableCache.getInstance().get(req.getQueryKey());
    }
}
