package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.action.IAjxImageLoadAction;

public final class AjxHttpLoader extends AbstractAjxLoader {
    public static final String DOMAIN_HTTP = "http://";
    public static final String DOMAIN_HTTPS = "https://";
    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";

    public AjxHttpLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray) {
        super(context, sparseArray);
    }

    public final String processingPath(@NonNull PictureParams pictureParams) {
        return pictureParams.url;
    }

    public final float[] readImageSize(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = pictureParams.url;
        pictureParams.imageSize = 0.0f;
        return lookupAction(1).readImageSize(this.mContext, pictureParams);
    }

    public final void loadImage(@Nullable View view, @Nullable IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = pictureParams.url;
        pictureParams.imageSize = 0.0f;
        lookupAction(1).loadImage(this.mContext, pictureParams, imageCallback, view, iAjxContext);
    }

    public final byte[] loadImage(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = pictureParams.url;
        pictureParams.imageSize = 0.0f;
        return lookupAction(1).loadImage(this.mContext, pictureParams);
    }

    public final void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = pictureParams.url;
        pictureParams.imageSize = 0.0f;
        pictureParams.isPreLoad = true;
        lookupAction(1).loadImage(this.mContext, pictureParams, imageCallback);
    }
}
