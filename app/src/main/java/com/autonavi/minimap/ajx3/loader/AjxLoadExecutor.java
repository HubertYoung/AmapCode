package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import pl.droidsonroids.gif.GifDrawable;

public interface AjxLoadExecutor {
    Bitmap doLoadBitmap(@NonNull Context context, @NonNull Uri uri, int i);

    GifDrawable doLoadGif(@NonNull Context context, @NonNull Uri uri, int i);

    void doLoadImage(@NonNull Context context, @NonNull Uri uri, int i, @NonNull ImageCallback imageCallback);
}
