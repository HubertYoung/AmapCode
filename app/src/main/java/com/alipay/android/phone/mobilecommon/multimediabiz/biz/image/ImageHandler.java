package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.ImageCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpDjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APMultimediaTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CacheUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.Callable;

public abstract class ImageHandler<V> implements Callable<V> {
    ImageLoadEngine a = ImageLoadEngine.getInstance();
    BitmapCacheLoader b = null;
    CutScaleType c = CutScaleType.KEEP_RATIO;
    private DjangoClient d;
    private FalconFacade e;
    public String mCloudId;
    protected Context mContext;
    protected MultimediaFileService mFileService;
    public String mGifFId;
    protected ImageCacheManager mImageCacheManager;
    public String mLocalId;

    public ImageHandler(Context context) {
        this.mContext = context;
        this.b = this.a.getCacheLoader();
        this.e = FalconFacade.get();
        this.mImageCacheManager = APImageWorker.getInstance(context).getImageCacheManager();
        this.mFileService = AppUtils.getFileService();
    }

    public String formatCacheKey(ImageWorkerPlugin plugin, String path, int width, int height, CutScaleType cutScaleType) {
        return CacheUtils.makeImageCacheKey(plugin, path, width, height, cutScaleType, null);
    }

    public ByteArrayOutputStream compressImage(File imageFile, int quality, int width, int height) {
        return this.e.compressImage(imageFile, quality, width, height);
    }

    public ByteArrayOutputStream compressImage(byte[] imageData, int quality, int width, int height) {
        return this.e.compressImage(imageData, quality, width, height);
    }

    /* access modifiers changed from: protected */
    public APImageManager getImageManager() {
        return APImageManager.getInstance(this.mContext);
    }

    /* access modifiers changed from: protected */
    public synchronized DjangoClient getDjangoClient() {
        if (this.d == null) {
            ConnectionManager conMgr = new HttpConnectionManager();
            conMgr.setAppKey("aliwallet");
            this.d = new HttpDjangoClient(this.mContext, conMgr);
        }
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void putNetTaskTag(String key, String value) {
        APImageManager.getInstance(this.mContext).putLoadlingTaskTag(key, value);
    }

    public void removeNetTaskTag(String key) {
        APImageManager.getInstance(this.mContext).removeLoadingTaskTag(key);
    }

    /* access modifiers changed from: protected */
    public void removeUploadCallBack(String taskId) {
        APImageManager.getInstance(this.mContext).unregistUploadCallback(taskId);
    }

    /* access modifiers changed from: protected */
    public void removeTaskModel(String taskId) {
        APMultimediaTaskManager.getInstance(this.mContext).removeTaskRecord(taskId);
    }
}
