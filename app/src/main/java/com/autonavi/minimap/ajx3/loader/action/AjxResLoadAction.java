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
import com.autonavi.minimap.ajx3.loader.AjxResLoader;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.ImageSizeUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.PathUtils;
import pl.droidsonroids.gif.GifDrawable;

public class AjxResLoadAction extends AbstractLoadAction {
    private static final String RES_DRAWABLE = "drawable";

    public byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public AjxResLoadAction() {
    }

    public AjxResLoadAction(AjxLoadExecutor ajxLoadExecutor) {
        super(ajxLoadExecutor);
    }

    public float[] readImageSize(@NonNull Context context, @NonNull PictureParams pictureParams) {
        int resId = AjxResLoader.getResId(context, pictureParams.realUrl);
        int imageSizeByPhone = ImageSizeUtils.getImageSizeByPhone(context);
        if (resId > 0) {
            float[] bitmapSize = ImageSizeUtils.getBitmapSize(context.getResources(), resId);
            return new float[]{bitmapSize[0], bitmapSize[1], (float) imageSizeByPhone};
        }
        return new float[]{0.0f, 0.0f, (float) imageSizeByPhone};
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext) {
        StringBuilder sb = new StringBuilder("AjxResLoadAction: loadImage ajxPath = ");
        sb.append(pictureParams.realUrl);
        LogHelper.d(sb.toString());
        loadImage(context, pictureParams, imageCallback);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (PathUtils.isGif(pictureParams.realUrl)) {
            i = handleGifPolicy(i);
        }
        if (pictureParams.isRunOnUI || pictureParams.isSyncLoadImg) {
            doLoadImageSync(context, getRealPath(context, pictureParams), i, imageCallback);
        } else {
            this.mExecutor.doLoadImage(context, getRealPath(context, pictureParams), i, imageCallback);
        }
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

    static Uri getRealPath(@NonNull Context context, @NonNull PictureParams pictureParams) {
        int imageSizeByPhone = ImageSizeUtils.getImageSizeByPhone(context);
        Uri buildAjxInnerUri = buildAjxInnerUri(context, pictureParams.realUrl);
        pictureParams.imageSize = (float) imageSizeByPhone;
        pictureParams.realUrl = buildAjxInnerUri.toString();
        return buildAjxInnerUri;
    }

    static Uri buildAjxInnerUri(@NonNull Context context, @NonNull String str) {
        return new Builder().scheme("android.resource").authority(context.getPackageName()).path("drawable").appendPath(str).build();
    }
}
