package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.action.IAjxImageLoadAction;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.PathUtils;

public class Ajx3ResourceLoader extends AbstractAjxLoader {
    private static final String BASE_FILE = "base.js";
    public static final String DOMAIN = "ajx://";
    public static final String DOMAIN_AJX3_RESOURCE = "ajx.resource://";
    public static final String SCHEME_AJX = "ajx";

    public Ajx3ResourceLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray) {
        super(context, sparseArray);
    }

    public String processingPath(@NonNull PictureParams pictureParams) {
        if (!AjxFileInfo.isDebug) {
            return PathUtils.getNoSchemeUrl(pictureParams.url);
        }
        return debugProcessingPath(pictureParams.url);
    }

    public float[] readImageSize(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url);
        pictureParams.imageSize = 0.0f;
        return lookupAction(0).readImageSize(this.mContext, pictureParams);
    }

    public void loadImage(@Nullable View view, @Nullable IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url);
        pictureParams.imageSize = 0.0f;
        lookupAction(0).loadImage(this.mContext, pictureParams, imageCallback, view, iAjxContext);
    }

    public byte[] loadImage(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url);
        pictureParams.imageSize = 0.0f;
        return lookupAction(0).loadImage(this.mContext, pictureParams);
    }

    public void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url);
        pictureParams.imageSize = 0.0f;
        lookupAction(0).loadImage(this.mContext, pictureParams, imageCallback);
    }

    private String debugProcessingPath(@NonNull String str) {
        if (!AjxFileInfo.isReadFromAjxFile || !str.endsWith(BASE_FILE)) {
            return PathUtils.getNoSchemeUrl(str);
        }
        return BASE_FILE;
    }
}
