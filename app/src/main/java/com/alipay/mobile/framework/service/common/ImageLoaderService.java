package com.alipay.mobile.framework.service.common;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.image.ImageCacheListener;
import com.alipay.mobile.framework.service.CommonService;

public abstract class ImageLoaderService extends CommonService {
    public abstract void cancel(String str, ImageLoaderListener imageLoaderListener);

    public abstract Bitmap loadFromCache(String str, String str2, String str3, int i, int i2);

    public abstract void startLoad(String str, String str2, String str3, ImageLoaderListener imageLoaderListener, int i, int i2);

    public abstract void startLoad(String str, String str2, String str3, ImageLoaderListener imageLoaderListener, int i, int i2, ImageCacheListener imageCacheListener);

    public ImageLoaderService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
