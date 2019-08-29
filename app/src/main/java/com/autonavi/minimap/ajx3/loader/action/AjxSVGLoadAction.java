package com.autonavi.minimap.ajx3.loader.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxLoadExecutor;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import pl.droidsonroids.gif.GifDrawable;

public class AjxSVGLoadAction extends AbstractLoadAction {
    public Bitmap loadBitmap(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public GifDrawable loadGif(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public float[] readImageSize(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public AjxSVGLoadAction(AjxLoadExecutor ajxLoadExecutor) {
        super(ajxLoadExecutor);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext) {
        loadImage(context, pictureParams, imageCallback);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        if (!pictureParams.isSyncLoadImg) {
            this.mExecutor.doLoadImage(context, buildSVGUri(pictureParams), handleSVG(0), imageCallback);
        } else {
            doLoadImageSync(context, buildSVGUri(pictureParams), handleSVG(0), imageCallback);
        }
    }

    private static Uri buildSVGUri(@NonNull PictureParams pictureParams) {
        Builder builder = new Builder();
        if (!TextUtils.isEmpty(pictureParams.SVGData)) {
            builder.scheme("path").path("data.svg").appendQueryParameter("data", pictureParams.SVGData);
        } else {
            Uri parse = Uri.parse(pictureParams.realUrl);
            Builder scheme = builder.scheme(parse.getScheme());
            StringBuilder sb = new StringBuilder();
            sb.append(parse.getAuthority());
            sb.append(parse.getPath());
            scheme.path(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(pictureParams.patchIndex);
        Builder appendQueryParameter = builder.appendQueryParameter("patch", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(pictureParams.width);
        Builder appendQueryParameter2 = appendQueryParameter.appendQueryParameter("width", sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(pictureParams.height);
        Builder appendQueryParameter3 = appendQueryParameter2.appendQueryParameter("height", sb4.toString());
        StringBuilder sb5 = new StringBuilder();
        sb5.append(pictureParams.SVGColor);
        appendQueryParameter3.appendQueryParameter("color", sb5.toString());
        return builder.build();
    }
}
