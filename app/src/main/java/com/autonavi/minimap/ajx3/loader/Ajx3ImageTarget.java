package com.autonavi.minimap.ajx3.loader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressFBWarnings({"WMI_WRONG_MAP_ITERATOR"})
public class Ajx3ImageTarget implements bkf {
    private static final Map<WeakReference<ImageCallback>, Ajx3ImageTarget> map = new ConcurrentHashMap();
    private WeakReference<ImageCallback> mImageCallback;

    public static Ajx3ImageTarget getInstance(@NonNull ImageCallback imageCallback) {
        for (WeakReference next : map.keySet()) {
            if (next != null && next.get() == imageCallback) {
                Ajx3ImageTarget ajx3ImageTarget = map.get(next);
                if (ajx3ImageTarget != null) {
                    return ajx3ImageTarget;
                }
            }
        }
        return new Ajx3ImageTarget(imageCallback);
    }

    protected Ajx3ImageTarget(ImageCallback imageCallback) {
        this.mImageCallback = new WeakReference<>(imageCallback);
        map.put(this.mImageCallback, this);
    }

    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
        map.remove(this.mImageCallback);
        ImageCallback imageCallback = (ImageCallback) this.mImageCallback.get();
        if (imageCallback != null) {
            imageCallback.onBitmapLoaded(bitmap);
        }
    }

    public void onBitmapFailed(Drawable drawable) {
        map.remove(this.mImageCallback);
        ImageCallback imageCallback = (ImageCallback) this.mImageCallback.get();
        if (imageCallback != null) {
            imageCallback.onBitmapFailed(drawable);
        }
    }

    public void onPrepareLoad(Drawable drawable) {
        ImageCallback imageCallback = (ImageCallback) this.mImageCallback.get();
        if (imageCallback != null) {
            imageCallback.onPrepareLoad(drawable);
        }
    }
}
