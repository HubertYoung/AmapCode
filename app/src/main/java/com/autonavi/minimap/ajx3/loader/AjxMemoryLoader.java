package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.action.IAjxImageLoadAction;
import com.autonavi.minimap.ajx3.util.PathUtils;

public class AjxMemoryLoader extends AbstractAjxLoader {
    public static final String DOMAIN = "memory://";
    public static final String SCHEME_MEMORY = "memory";

    public AjxMemoryLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray) {
        super(context, sparseArray);
    }

    public String processingPath(@NonNull PictureParams pictureParams) {
        return pictureParams.url;
    }

    public float[] readImageSize(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = PathUtils.getNoSchemeUrl(pictureParams.url);
        pictureParams.imageSize = 0.0f;
        return lookupAction(5).readImageSize(this.mContext, pictureParams);
    }

    public void loadImage(@Nullable View view, @Nullable IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = PathUtils.getNoSchemeUrl(pictureParams.url);
        pictureParams.imageSize = 0.0f;
        lookupAction(5).loadImage(this.mContext, pictureParams, imageCallback, view, iAjxContext);
    }

    public byte[] loadImage(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = PathUtils.getNoSchemeUrl(pictureParams.url);
        pictureParams.imageSize = 0.0f;
        return lookupAction(5).loadImage(this.mContext, pictureParams);
    }

    public void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = PathUtils.getNoSchemeUrl(pictureParams.url);
        pictureParams.imageSize = 0.0f;
        lookupAction(5).loadImage(this.mContext, pictureParams, imageCallback);
    }
}
