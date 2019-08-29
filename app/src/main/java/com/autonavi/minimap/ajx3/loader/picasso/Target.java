package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import pl.droidsonroids.gif.GifDrawable;

public interface Target {
    void onBitmapFailed(Drawable drawable);

    void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom);

    void onGifDrawableLoaded(GifDrawable gifDrawable, LoadedFrom loadedFrom);

    void onPrepareLoad(Drawable drawable);
}
