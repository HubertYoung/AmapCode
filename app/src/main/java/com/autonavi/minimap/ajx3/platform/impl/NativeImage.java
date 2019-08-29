package com.autonavi.minimap.ajx3.platform.impl;

import android.content.Context;
import com.autonavi.minimap.ajx3.loader.AjxLoaderManager;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.INativeImage;

public final class NativeImage implements INativeImage {
    private int mHeight = 0;
    private int mWidth = 0;

    public NativeImage(Context context, AjxLoaderManager ajxLoaderManager, String str) {
    }

    public final int width() {
        return this.mWidth;
    }

    public final int height() {
        return this.mHeight;
    }
}
