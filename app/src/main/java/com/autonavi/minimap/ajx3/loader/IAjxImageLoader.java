package com.autonavi.minimap.ajx3.loader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;

public interface IAjxImageLoader {
    void loadImage(@Nullable View view, @Nullable IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback);

    byte[] loadImage(@NonNull PictureParams pictureParams);

    void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback);

    String processingPath(@NonNull PictureParams pictureParams);

    float[] readImageSize(@NonNull PictureParams pictureParams);
}
