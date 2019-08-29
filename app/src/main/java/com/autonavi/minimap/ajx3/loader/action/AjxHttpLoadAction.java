package com.autonavi.minimap.ajx3.loader.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxLoadExecutor;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.ImageSizeUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.PathUtils;
import pl.droidsonroids.gif.GifDrawable;

public class AjxHttpLoadAction extends AbstractLoadAction {
    public Bitmap loadBitmap(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public GifDrawable loadGif(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public AjxHttpLoadAction() {
    }

    public AjxHttpLoadAction(AjxLoadExecutor ajxLoadExecutor) {
        super(ajxLoadExecutor);
    }

    public float[] readImageSize(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return new float[]{0.0f, 0.0f, (float) ImageSizeUtils.getImageSizeByName(ImageSizeUtils.getSizeNameForNetworkFile(Uri.decode(pictureParams.realUrl)))};
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext) {
        StringBuilder sb = new StringBuilder("AjxHttpLoaderAction: loadImage url = %s");
        sb.append(pictureParams.realUrl);
        LogHelper.d(sb.toString());
        loadImage(context, pictureParams, imageCallback);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        StringBuilder sb = new StringBuilder("AjxHttpLoaderAction: loadImage(context) ajxPath = %s");
        sb.append(pictureParams.realUrl);
        LogHelper.d(sb.toString());
        String decode = Uri.decode(pictureParams.realUrl);
        pictureParams.imageSize = (float) ImageSizeUtils.getImageSizeByName(ImageSizeUtils.getSizeNameForNetworkFile(decode));
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (pictureParams.isPreLoad) {
            i = handlePreLoadPolicy(i);
        }
        if (PathUtils.isGif(decode)) {
            i = handleGifPolicy(i);
        }
        this.mExecutor.doLoadImage(context, Uri.parse(decode), i, imageCallback);
    }
}
