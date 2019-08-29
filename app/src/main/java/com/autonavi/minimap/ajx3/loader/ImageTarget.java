package com.autonavi.minimap.ajx3.loader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.Target;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import pl.droidsonroids.gif.GifDrawable;

public class ImageTarget implements Target {
    private static final Map<ImageCallback, ImageTarget> sMap = new ConcurrentHashMap();
    private ImageCallback mImageCallback;

    public static ImageTarget getInstance(@NonNull ImageCallback imageCallback) {
        ImageTarget imageTarget = sMap.get(imageCallback);
        if (imageTarget != null) {
            return imageTarget;
        }
        ImageTarget imageTarget2 = new ImageTarget(imageCallback);
        sMap.put(imageCallback, imageTarget2);
        return imageTarget2;
    }

    private ImageTarget(ImageCallback imageCallback) {
        this.mImageCallback = imageCallback;
    }

    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
        sMap.remove(this.mImageCallback);
        this.mImageCallback.onBitmapLoaded(bitmap);
    }

    public void onGifDrawableLoaded(GifDrawable gifDrawable, LoadedFrom loadedFrom) {
        sMap.remove(this.mImageCallback);
        this.mImageCallback.onGifLoaded(gifDrawable);
    }

    public void onBitmapFailed(Drawable drawable) {
        sMap.remove(this.mImageCallback);
        this.mImageCallback.onBitmapFailed(drawable);
    }

    public void onPrepareLoad(Drawable drawable) {
        this.mImageCallback.onPrepareLoad(drawable);
    }
}
