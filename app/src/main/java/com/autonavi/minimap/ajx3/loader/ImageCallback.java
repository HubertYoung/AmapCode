package com.autonavi.minimap.ajx3.loader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import pl.droidsonroids.gif.GifDrawable;

public interface ImageCallback {
    void onBitmapFailed(Drawable drawable);

    void onBitmapLoaded(Bitmap bitmap);

    void onGifLoaded(GifDrawable gifDrawable);

    void onPrepareLoad(Drawable drawable);
}
