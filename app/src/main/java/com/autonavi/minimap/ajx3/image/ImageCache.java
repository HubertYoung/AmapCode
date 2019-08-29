package com.autonavi.minimap.ajx3.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.loader.picasso.Cache;
import com.autonavi.minimap.ajx3.loader.picasso.LruCache;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso;
import com.autonavi.minimap.ajx3.loader.picasso.Utils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.PathUtils;
import pl.droidsonroids.gif.GifDrawable;

public class ImageCache implements Cache<Image> {
    private static final String TAG = "ImageCache";
    private static volatile ImageCache sInstance;
    private Cache<Image> mCache = null;

    static class CacheImpl extends LruCache<Image> {
        public CacheImpl(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public int calculateValueBytes(Image image) {
            if (image.bitmap != null) {
                return Utils.getBitmapBytes(image.bitmap);
            }
            return 0;
        }
    }

    public static class Image {
        public Bitmap bitmap;
        public GifDrawable gif;
        public float imageSize;
        public String realUrl;
    }

    public static ImageCache getInstance(Context context) {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ImageCache.class) {
            if (context == null) {
                try {
                    throw new IllegalStateException("Ajx.sContext can not be null !!!");
                } catch (Throwable th) {
                    while (true) {
                        throw th;
                    }
                }
            } else if (sInstance == null) {
                sInstance = new ImageCache(context);
            }
        }
        return sInstance;
    }

    private ImageCache(Context context) {
        this.mCache = new CacheImpl(context);
    }

    public Image get(String str) {
        Image image = (Image) this.mCache.get(str);
        if (image != null) {
            StringBuilder sb = new StringBuilder("ImageCache: 命中 url = ");
            sb.append(str);
            sb.append(", bitmap = ");
            sb.append(image);
            LogHelper.d(sb.toString());
        } else {
            LogHelper.d("ImageCache: 未命中 url = ".concat(String.valueOf(str)));
        }
        return image;
    }

    public void set(String str, Image image) {
        StringBuilder sb = new StringBuilder("ImageCache: 保存 url = ");
        sb.append(str);
        sb.append(", bitmap = ");
        sb.append(image);
        LogHelper.d(sb.toString());
        this.mCache.set(str, image);
    }

    public void clear() {
        LogHelper.d("ImageCache: clear ImageCache !!!");
        this.mCache.clear();
    }

    public void preLoad(@NonNull IAjxContext iAjxContext, @NonNull String[] strArr, @NonNull JsFunctionCallback jsFunctionCallback) {
        if (strArr.length == 0) {
            LogHelper.loge("ImageCache: 参数不能为空!");
            return;
        }
        for (String preLoadImpl : strArr) {
            preLoadImpl(iAjxContext, preLoadImpl, jsFunctionCallback);
        }
    }

    public void preLoad(@NonNull Context context, String[] strArr) {
        if (strArr.length <= 0) {
            LogHelper.e("ImageCache: url参数不能为空!");
            return;
        }
        for (final String str : strArr) {
            LogHelper.log("ImageCache: preLoad(context) url = ".concat(String.valueOf(str)));
            Ajx.getInstance().lookupLoader(str).preLoadImage(PictureParams.make(null, str, false), new ImageCallback() {
                public void onGifLoaded(GifDrawable gifDrawable) {
                }

                public void onBitmapLoaded(Bitmap bitmap) {
                    StringBuilder sb = new StringBuilder("ImageCache: onBitmapLoaded url = ");
                    sb.append(str);
                    LogHelper.log(sb.toString());
                }

                public void onBitmapFailed(Drawable drawable) {
                    StringBuilder sb = new StringBuilder("ImageCache: onBitmapFailed url = ");
                    sb.append(str);
                    LogHelper.log(sb.toString());
                }

                public void onPrepareLoad(Drawable drawable) {
                    StringBuilder sb = new StringBuilder("ImageCache: onPrepareLoad url = ");
                    sb.append(str);
                    LogHelper.log(sb.toString());
                }
            });
        }
    }

    public void evictCache(@NonNull IAjxContext iAjxContext, @NonNull String[] strArr) {
        if (strArr.length == 0) {
            LogHelper.loge("ImageCache: 参数不能为空!");
            return;
        }
        for (String str : strArr) {
            if (str != null) {
                evictCacheImpl(iAjxContext, str);
            }
        }
    }

    public int size() {
        return this.mCache.size();
    }

    public int maxSize() {
        return this.mCache.maxSize();
    }

    public void clearKeyUri(String str) {
        this.mCache.clearKeyUri(str);
    }

    private static void preLoadImpl(@NonNull IAjxContext iAjxContext, @NonNull final String str, @NonNull final JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str)) {
            Ajx.getInstance().lookupLoader(str).preLoadImage(PictureParams.make(iAjxContext, str, false), new ImageCallback() {
                public final void onPrepareLoad(Drawable drawable) {
                }

                public final void onGifLoaded(GifDrawable gifDrawable) {
                    jsFunctionCallback.callback(str, Boolean.TRUE);
                    StringBuilder sb = new StringBuilder("ImageCache: preload gif success gif = ");
                    sb.append(gifDrawable);
                    sb.append(", url = ");
                    sb.append(str);
                    LogHelper.d(sb.toString());
                }

                public final void onBitmapLoaded(Bitmap bitmap) {
                    jsFunctionCallback.callback(str, Boolean.TRUE);
                    StringBuilder sb = new StringBuilder("ImageCache: preload bitmap success bitmap = ");
                    sb.append(bitmap);
                    sb.append(", url = ");
                    sb.append(str);
                    LogHelper.d(sb.toString());
                }

                public final void onBitmapFailed(Drawable drawable) {
                    jsFunctionCallback.callback(str, Boolean.FALSE);
                    StringBuilder sb = new StringBuilder("ImageCache: preload gif or bitmap failed errorDrawable = ");
                    sb.append(drawable);
                    sb.append(", url = ");
                    sb.append(str);
                    LogHelper.d(sb.toString());
                }
            });
        }
    }

    private void evictCacheImpl(@NonNull IAjxContext iAjxContext, @NonNull String str) {
        if (!TextUtils.isEmpty(str)) {
            String rectifyFileScheme = PathUtils.rectifyFileScheme(PathUtils.processPath(iAjxContext, str));
            LogHelper.d("ImageCache: clear cache url = ".concat(String.valueOf(rectifyFileScheme)));
            Picasso.with(iAjxContext.getNativeContext()).invalidate(rectifyFileScheme);
        }
    }
}
