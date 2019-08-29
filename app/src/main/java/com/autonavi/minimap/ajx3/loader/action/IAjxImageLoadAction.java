package com.autonavi.minimap.ajx3.loader.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import pl.droidsonroids.gif.GifDrawable;

public interface IAjxImageLoadAction {
    Bitmap loadBitmap(@NonNull Context context, @NonNull PictureParams pictureParams);

    GifDrawable loadGif(@NonNull Context context, @NonNull PictureParams pictureParams);

    void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback);

    void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext);

    byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams);

    float[] readImageSize(@NonNull Context context, @NonNull PictureParams pictureParams);
}
