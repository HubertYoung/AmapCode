package com.autonavi.minimap.acanvas;

import android.content.Context;
import android.support.annotation.NonNull;

public interface IACanvasImageLoader {
    void load(@NonNull Context context, @NonNull ACanvasImage aCanvasImage, boolean z, @NonNull IACanvasImageLoaderCallback iACanvasImageLoaderCallback);
}
