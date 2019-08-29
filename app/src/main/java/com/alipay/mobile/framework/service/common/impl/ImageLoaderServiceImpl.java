package com.alipay.mobile.framework.service.common.impl;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.cache.disk.CacheException;
import com.alipay.mobile.common.cache.disk.lru.DefaultLruDiskCache;
import com.alipay.mobile.common.cache.mem.lru.ImageCache;
import com.alipay.mobile.common.image.ImageCacheListener;
import com.alipay.mobile.common.image.ImageLoader;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.ImageLoaderListener;
import com.alipay.mobile.framework.service.common.ImageLoaderService;

public class ImageLoaderServiceImpl extends ImageLoaderService {
    private ImageLoader a = new ImageLoader(new HttpManager(LauncherApplicationAgent.getInstance().getApplicationContext()), DefaultLruDiskCache.getInstance(), ImageCache.getInstance());
    private ImageStrategy b = new DefaultImageStrategy();

    public interface ImageStrategy {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        boolean loadAssetImage(String str, ImageLoaderListener imageLoaderListener);

        String preferImageUrl(String str, int i, int i2);
    }

    public ImageLoaderServiceImpl() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void startLoad(String owner, String group, String imagePath, ImageLoaderListener listener, int width, int height) {
        startLoad(owner, group, imagePath, listener, width, height, null);
    }

    public Bitmap loadFromCache(String owner, String group, String imagePath, int width, int height) {
        String url = this.b.preferImageUrl(imagePath, width, height);
        Bitmap bitmap = ImageCache.getInstance().get(owner, url);
        if (bitmap != null && !bitmap.isRecycled()) {
            return bitmap;
        }
        DefaultLruDiskCache.getInstance().open();
        try {
            byte[] data = DefaultLruDiskCache.getInstance().get(owner, url);
            if (data != null) {
                Bitmap bitmap2 = this.a.createBitmap(data, width, height);
                if (bitmap2 != null) {
                    ImageCache.getInstance().put(owner, group, url, bitmap2);
                    DefaultLruDiskCache.getInstance().close();
                    return bitmap2;
                }
                DefaultLruDiskCache.getInstance().remove(url);
            }
        } catch (CacheException e) {
            LoggerFactory.getTraceLogger().error((String) "ImageLoader", "[" + e.getCode() + "]" + e.getMsg());
        } finally {
            DefaultLruDiskCache.getInstance().close();
        }
        return null;
    }

    public void startLoad(String owner, String group, final String imagePath, final ImageLoaderListener listener, int width, int height, ImageCacheListener imageCacheListener) {
        if (TextUtils.isEmpty(imagePath)) {
            LoggerFactory.getTraceLogger().error((String) "ImageLoaderService", (String) "imagePath is null");
        } else if (!this.b.loadAssetImage(imagePath, listener)) {
            this.a.startLoad(owner, group, this.b.preferImageUrl(imagePath, width, height), new ImageLoaderListener() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void onProgressUpdate(String path, double percent) {
                    listener.onProgressUpdate(imagePath, percent);
                }

                public void onPreLoad(String path) {
                    listener.onPreLoad(imagePath);
                }

                public void onPostLoad(String path, Bitmap bitmap) {
                    listener.onPostLoad(imagePath, bitmap);
                }

                public void onFailed(String path, int code, String msg) {
                    listener.onFailed(imagePath, code, msg);
                }

                public void onCancelled(String path) {
                    listener.onCancelled(imagePath);
                }
            }, width, height, imageCacheListener);
        }
    }

    public void cancel(String path, ImageLoaderListener listener) {
        this.a.cancel(path, listener);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
    }
}
