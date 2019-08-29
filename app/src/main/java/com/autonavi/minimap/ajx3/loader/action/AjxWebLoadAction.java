package com.autonavi.minimap.ajx3.loader.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
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

public class AjxWebLoadAction extends AbstractLoadAction {
    public static final String PATH_IMAGE = "image";
    public static final String REAL_PATH = "realPath";
    public static final String SCHEME_AJX3_WEB = "ajx.web";

    public byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public AjxWebLoadAction() {
    }

    public AjxWebLoadAction(AjxLoadExecutor ajxLoadExecutor) {
        super(ajxLoadExecutor);
    }

    public float[] readImageSize(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return new float[]{0.0f, 0.0f, getImageSize(context)};
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext) {
        StringBuilder sb = new StringBuilder("AjxWebLoadAction: loadImage ajxPath = ");
        sb.append(pictureParams.realUrl);
        LogHelper.d(sb.toString());
        loadImage(context, pictureParams, imageCallback);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (pictureParams.isPreLoad) {
            i = handlePreLoadPolicy(i);
        }
        if (PathUtils.isGif(pictureParams.realUrl)) {
            i = handleGifPolicy(i);
        }
        this.mExecutor.doLoadImage(context, getRealPath(context, pictureParams), i, imageCallback);
    }

    public Bitmap loadBitmap(@NonNull Context context, @NonNull PictureParams pictureParams) {
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        return this.mExecutor.doLoadBitmap(context, getRealPath(context, pictureParams), i);
    }

    public GifDrawable loadGif(@NonNull Context context, @NonNull PictureParams pictureParams) {
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        return this.mExecutor.doLoadGif(context, getRealPath(context, pictureParams), handleGifPolicy(i));
    }

    private Uri getRealPath(@NonNull Context context, @NonNull PictureParams pictureParams) {
        float imageSize = getImageSize(context);
        Uri buildInnerUri = buildInnerUri(pictureParams.realUrl);
        pictureParams.realUrl = buildInnerUri.toString();
        pictureParams.imageSize = imageSize;
        return buildInnerUri;
    }

    private float getImageSize(Context context) {
        return (float) ImageSizeUtils.getImageSizeByName(ImageSizeUtils.getSizeNameForWeb(ImageSizeUtils.getImageSizeByPhone(context)));
    }

    private Uri buildInnerUri(@NonNull String str) {
        return new Builder().scheme(SCHEME_AJX3_WEB).path("image").appendQueryParameter(REAL_PATH, str).build();
    }
}
