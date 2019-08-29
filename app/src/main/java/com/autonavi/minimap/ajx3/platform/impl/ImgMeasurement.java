package com.autonavi.minimap.ajx3.platform.impl;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxLoaderManager;
import com.autonavi.minimap.ajx3.loader.IAjxImageLoader;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.IImgMeasurement;

public class ImgMeasurement implements IImgMeasurement {
    private Context context;
    private AjxLoaderManager loaderManager;

    public ImgMeasurement(Context context2, AjxLoaderManager ajxLoaderManager) {
        this.context = context2;
        this.loaderManager = ajxLoaderManager;
    }

    public float[] measure(String str, float f, int i, float f2, int i2, int i3) {
        float f3;
        if (TextUtils.isEmpty(str)) {
            return new float[]{0.0f, 0.0f};
        }
        IAjxImageLoader lookupLoader = this.loaderManager.lookupLoader(str);
        if (lookupLoader == null) {
            return new float[]{0.0f, 0.0f};
        }
        PictureParams pictureParams = new PictureParams();
        pictureParams.url = str;
        pictureParams.patchIndex = i3;
        float[] readImageSize = lookupLoader.readImageSize(pictureParams);
        float f4 = 0.0f;
        if (readImageSize == null || readImageSize.length != 3) {
            f3 = 0.0f;
        } else {
            f4 = (readImageSize[0] / readImageSize[2]) * 2.0f;
            f3 = (readImageSize[1] / readImageSize[2]) * 2.0f;
        }
        if (1 != i && (2 != i || f4 <= f)) {
            f = f4;
        }
        if (1 != i2 && (2 != i2 || f3 <= f2)) {
            f2 = f3;
        }
        return new float[]{f, f2};
    }
}
