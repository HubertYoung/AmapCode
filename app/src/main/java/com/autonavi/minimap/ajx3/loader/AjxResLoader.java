package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.action.IAjxImageLoadAction;

public class AjxResLoader extends AbstractAjxLoader {
    public static final String DOMAIN = "res://image/";
    public static final String SCHEME_RES = "res";

    public AjxResLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray) {
        super(context, sparseArray);
    }

    public String processingPath(@NonNull PictureParams pictureParams) {
        return getResourceIdStr(pictureParams.url);
    }

    public float[] readImageSize(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = getResourceName(pictureParams.url);
        pictureParams.imageSize = 0.0f;
        return lookupAction(4).readImageSize(this.mContext, pictureParams);
    }

    public void loadImage(@Nullable View view, @Nullable IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = getResourceName(pictureParams.url);
        pictureParams.imageSize = 0.0f;
        lookupAction(4).loadImage(this.mContext, pictureParams, imageCallback, view, iAjxContext);
    }

    public byte[] loadImage(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = getResourceName(pictureParams.url);
        pictureParams.imageSize = 0.0f;
        return lookupAction(4).loadImage(this.mContext, pictureParams);
    }

    public void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = getResourceName(pictureParams.url);
        pictureParams.imageSize = 0.0f;
        lookupAction(4).loadImage(this.mContext, pictureParams, imageCallback);
    }

    public static int getResId(Context context, String str) {
        return context.getResources().getIdentifier(getResourceIdStr(str), ResUtils.DRAWABLE, context.getPackageName());
    }

    public static String getResourceIdStr(@NonNull String str) {
        String resourceName = getResourceName(str);
        int lastIndexOf = resourceName.lastIndexOf(46);
        return lastIndexOf > 0 ? resourceName.substring(0, lastIndexOf) : resourceName;
    }

    public static String getResourceName(@NonNull String str) {
        return str.startsWith(DOMAIN) ? str.substring(12) : str;
    }
}
