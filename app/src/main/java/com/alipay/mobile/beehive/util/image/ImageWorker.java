package com.alipay.mobile.beehive.util.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APDisplayer;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import java.util.HashMap;
import java.util.Map;

public class ImageWorker {
    private static final String TAG = "ImageWorker";
    /* access modifiers changed from: private */
    public static Handler uiHandler = new Handler(Looper.getMainLooper());
    private int cachePeriod;
    /* access modifiers changed from: private */
    public Drawable defaultDrawable;
    private boolean fastToRecycle;
    private int height;
    private Config inPreferredConfig;
    private MultimediaImageService multimediaImageService;
    private Resources resources;
    /* access modifiers changed from: private */
    public Map<String, String> taskMap;
    private int width;

    public ImageWorker(Context context) {
        this(context, (Bitmap) null);
    }

    public ImageWorker(Context context, int defaultBitmapResId) {
        this(context, (Bitmap) null);
        if (defaultBitmapResId > 0) {
            this.defaultDrawable = this.resources.getDrawable(defaultBitmapResId);
        }
    }

    public ImageWorker(Context context, Bitmap defaultBitmap) {
        this.width = 240;
        this.height = 240;
        this.inPreferredConfig = Config.ARGB_8888;
        this.taskMap = new HashMap();
        this.resources = context.getResources();
        this.multimediaImageService = (MultimediaImageService) MicroServiceUtil.getMicroService(MultimediaImageService.class);
        if (defaultBitmap != null) {
            this.defaultDrawable = new BitmapDrawable(this.resources, defaultBitmap);
        }
    }

    public ImageWorker loadImage(String path, ImageView imageView) {
        return loadImage(path, imageView, null);
    }

    public ImageWorker loadImage(String path, ImageView imageView, ImageWorkerPlugin plugin) {
        loadImageAction(path, imageView, this.width, this.height, null, plugin);
        return this;
    }

    public ImageWorker loadImage(String path, ImageView imageView, int width2, int height2) {
        return loadImage(path, imageView, width2, height2, (ImageWorkerPlugin) null);
    }

    public ImageWorker loadImage(String path, ImageView imageView, int width2, int height2, ImageWorkerPlugin plugin) {
        loadImageAction(path, imageView, width2, height2, null, plugin);
        return this;
    }

    public ImageWorker loadImage(String path, ImageWorkerCallback callback, int width2, int height2) {
        return loadImage(path, callback, width2, height2, (ImageWorkerPlugin) null);
    }

    public ImageWorker loadImage(String path, ImageWorkerCallback callback, int width2, int height2, ImageWorkerPlugin plugin) {
        loadImageAction(path, null, width2, height2, callback, plugin);
        return this;
    }

    public void optimizeListView(AbsListView view, OnScrollListener listener) {
        optimizeListView(view, false, true, listener);
    }

    public void optimizeListView(AbsListView view, boolean pauseOnScroll, boolean pauseOnFling, OnScrollListener listener) {
        this.multimediaImageService.optimizeView(view, pauseOnScroll, pauseOnFling, listener);
    }

    private void loadImageAction(final String path, final View imageView, int width2, int height2, final ImageWorkerCallback callback, ImageWorkerPlugin plugin) {
        if (imageView != null) {
            imageView.setTag(path);
        }
        if (TextUtils.isEmpty(path)) {
            Log.d(TAG, "加载图片，path为空，设置默认icon=" + this.defaultDrawable);
            if (this.defaultDrawable != null) {
                if (imageView != null) {
                    imageView.setTag(null);
                }
                setDefaultImage(imageView);
                return;
            }
            return;
        }
        APImageDownLoadCallback apCallback = null;
        if (callback != null) {
            callback.onStart(path);
            apCallback = new APImageDownLoadCallback() {
                public final void onError(APImageDownloadRsp rep, Exception ex) {
                    ImageWorker.this.setDefaultImage(imageView);
                    callback.onFailure(path, 404, ex != null ? ex.getLocalizedMessage() : "");
                }

                public final void onProcess(String path, int process) {
                    callback.onProgress(path, ((double) process) / 100.0d);
                }

                public final void onSucc(APImageDownloadRsp rep) {
                }
            };
        }
        APImageLoadRequest req = new APImageLoadRequest();
        req.path = path;
        req.callback = apCallback;
        req.defaultDrawable = this.defaultDrawable;
        req.height = height2;
        req.width = width2;
        req.plugin = plugin;
        if (imageView instanceof ImageView) {
            req.imageView = (ImageView) imageView;
        } else {
            req.displayer = getDisplayer(path, imageView, callback);
        }
        APMultimediaTaskModel result = this.multimediaImageService.loadImage(req);
        if (result != null) {
            setDefaultImage(imageView);
            this.taskMap.put(path, result.getTaskId());
        }
    }

    private APDisplayer getDisplayer(final String path, final View imageView, final ImageWorkerCallback callback) {
        return new APDisplayer() {
            public final void display(View view, Drawable drawable, String sourcePath) {
                ImageWorker.this.success(sourcePath, (BitmapDrawable) drawable, callback);
                if (imageView != null && path.equals(imageView.getTag())) {
                    ImageWorker.this.setImageDrawable(imageView, drawable);
                }
                ImageWorker.uiHandler.post(new Runnable() {
                    public final void run() {
                        if (ImageWorker.this.taskMap.containsKey(path)) {
                            ImageWorker.this.taskMap.remove(path);
                        }
                    }
                });
            }
        };
    }

    /* access modifiers changed from: private */
    public void success(final String path, final BitmapDrawable drawable, final ImageWorkerCallback callback) {
        if (callback == null) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            uiHandler.post(new Runnable() {
                public final void run() {
                    callback.onSuccess(path, drawable);
                }
            });
        } else {
            callback.onSuccess(path, drawable);
        }
    }

    /* access modifiers changed from: private */
    public void setDefaultImage(final View imageView) {
        if (imageView != null && this.defaultDrawable != null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                uiHandler.post(new Runnable() {
                    public final void run() {
                        ImageWorker.this.setImageDrawable(imageView, ImageWorker.this.defaultDrawable);
                    }
                });
            } else {
                setImageDrawable(imageView, this.defaultDrawable);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setImageDrawable(final View imageView, final Drawable drawable) {
        if (imageView != null && drawable != null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                uiHandler.post(new Runnable() {
                    public final void run() {
                        ImageWorker.this.setBitmap(imageView, drawable);
                    }
                });
            } else {
                setBitmap(imageView, drawable);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setBitmap(View tv2, Drawable bitmap) {
        ((ImageView) tv2).setImageDrawable(bitmap);
    }

    public ImageWorker setDefaultImage(Bitmap bitmap) {
        this.defaultDrawable = new BitmapDrawable(this.resources, bitmap);
        return this;
    }

    public ImageWorker setDefaultImage(int resId) {
        if (resId > 0 && (this.resources.getDrawable(resId) instanceof BitmapDrawable)) {
            this.defaultDrawable = this.resources.getDrawable(resId);
        }
        return this;
    }

    public ImageWorker cancel(String path, int width2, int height2) {
        if (this.taskMap.containsKey(path)) {
            this.multimediaImageService.cancelLoad(this.taskMap.get(path));
            this.taskMap.remove(path);
        }
        return this;
    }

    public ImageWorker cancel(String path) {
        if (this.taskMap.containsKey(path)) {
            this.multimediaImageService.cancelLoad(this.taskMap.get(path));
            this.taskMap.remove(path);
        }
        return this;
    }

    public ImageWorker cancelAll() {
        for (String httpUrl : this.taskMap.values()) {
            this.multimediaImageService.cancelLoad(httpUrl);
        }
        this.taskMap.clear();
        return this;
    }

    public int getWidth() {
        return this.width;
    }

    public ImageWorker setWidth(int width2) {
        if (width2 > 0) {
            this.width = width2;
        }
        return this;
    }

    public int getHeight() {
        return this.height;
    }

    public ImageWorker setHeight(int height2) {
        if (height2 > 0) {
            this.height = height2;
        }
        return this;
    }

    public int getCachePeriod() {
        return this.cachePeriod;
    }

    public Config getInPreferredConfig() {
        return this.inPreferredConfig;
    }

    public ImageWorker setInPreferredConfig(Config inPreferredConfig2) {
        if (inPreferredConfig2 != null) {
            this.inPreferredConfig = inPreferredConfig2;
        }
        return this;
    }

    public ImageWorker setCachePeriod(int cachePeriod2) {
        this.cachePeriod = cachePeriod2;
        return this;
    }

    public boolean isFastToRecycle() {
        return this.fastToRecycle;
    }

    public ImageWorker setFastToRecycle(boolean fastToRecycle2) {
        this.fastToRecycle = fastToRecycle2;
        return this;
    }
}
