package com.autonavi.minimap.acanvas;

import android.content.Context;
import android.graphics.Bitmap;

public interface IACanvasBridge {
    void addFpsUpdater(String str, float f, IACanvasFpsUpdater iACanvasFpsUpdater);

    void bindContext2D(String str, ACanvasView aCanvasView);

    void bindImageTexture(String str, int i, Bitmap bitmap);

    void createContext2D(String str, int i, int i2, float f);

    void destroyContext2D(String str);

    void loadImage(Context context, String str, boolean z, IACanvasImageLoaderCallback iACanvasImageLoaderCallback);

    float measureText(String str, String str2, String str3);

    void release();

    void releaseImageTexture(String str, String str2);

    void renderCommand(String str, String str2);

    void setCanvasScale(String str, float f);

    void setCanvasSize(String str, int i, int i2);
}
