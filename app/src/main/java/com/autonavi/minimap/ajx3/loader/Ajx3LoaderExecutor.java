package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import pl.droidsonroids.gif.GifDrawable;

public class Ajx3LoaderExecutor extends DefaultImageExecutor {
    private static final String SCHEME_FILE = "file";

    public void doLoadImage(@NonNull Context context, @NonNull Uri uri, int i, @NonNull ImageCallback imageCallback) {
        ku.a().c("auiLog", "【imageloader】 url:".concat(String.valueOf(uri)));
        super.doLoadImage(context, uri, i, imageCallback);
    }

    public Bitmap doLoadBitmap(@NonNull Context context, @NonNull Uri uri, int i) {
        ku.a().c("auiLog", "【imageloader】 url:".concat(String.valueOf(uri)));
        return super.doLoadBitmap(context, uri, i);
    }

    public GifDrawable doLoadGif(@NonNull Context context, @NonNull Uri uri, int i) {
        ku.a().c("auiLog", "【imageloader】 url:".concat(String.valueOf(uri)));
        return super.doLoadGif(context, uri, i);
    }
}
