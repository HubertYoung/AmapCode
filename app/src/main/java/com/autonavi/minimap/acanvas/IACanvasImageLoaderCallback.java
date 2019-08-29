package com.autonavi.minimap.acanvas;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public interface IACanvasImageLoaderCallback {
    void onFailed();

    void onLoaded(@NonNull ACanvasImage aCanvasImage, @NonNull Bitmap bitmap);
}
