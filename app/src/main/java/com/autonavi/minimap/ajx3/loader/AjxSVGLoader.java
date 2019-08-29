package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.action.IAjxImageLoadAction;

public class AjxSVGLoader extends AbstractAjxLoader {
    public static final String SVG_SEGMENT = "svg";

    public void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
    }

    public AjxSVGLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray) {
        super(context, sparseArray);
    }

    public String processingPath(@NonNull PictureParams pictureParams) {
        return pictureParams.url;
    }

    public float[] readImageSize(@NonNull PictureParams pictureParams) {
        return lookupAction(7).readImageSize(this.mContext, pictureParams);
    }

    public void loadImage(@Nullable View view, @Nullable IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        lookupAction(7).loadImage(this.mContext, pictureParams, imageCallback, view, iAjxContext);
    }

    public byte[] loadImage(@NonNull PictureParams pictureParams) {
        return lookupAction(7).loadImage(this.mContext, pictureParams);
    }
}
