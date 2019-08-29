package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso;
import com.autonavi.minimap.ajx3.loader.picasso.RequestCreator;
import com.autonavi.minimap.ajx3.loader.picasso.Target;
import com.autonavi.minimap.ajx3.util.LogHelper;
import pl.droidsonroids.gif.GifDrawable;

public class DefaultImageExecutor implements AjxLoadExecutor {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public void doLoadImage(@NonNull Context context, @NonNull Uri uri, int i, @NonNull final ImageCallback imageCallback) {
        final RequestCreator load = Picasso.with(context).load(uri);
        LoadPolicy.handleMemoryPolicy(load, i);
        if (Looper.myLooper() == Looper.getMainLooper()) {
            load.into((Target) ImageTarget.getInstance(imageCallback));
        } else {
            HANDLER.post(new Runnable() {
                public void run() {
                    load.into((Target) ImageTarget.getInstance(imageCallback));
                }
            });
        }
    }

    public Bitmap doLoadBitmap(@NonNull Context context, @NonNull Uri uri, int i) {
        try {
            RequestCreator load = Picasso.with(context).load(uri);
            LoadPolicy.handleMemoryPolicy(load, i);
            return load.getBitmap();
        } catch (Exception e) {
            LogHelper.e("DefaultImageExecutor: log bitmap error!!! e = ".concat(String.valueOf(e)));
            return null;
        }
    }

    public GifDrawable doLoadGif(@NonNull Context context, @NonNull Uri uri, int i) {
        try {
            RequestCreator load = Picasso.with(context).load(uri);
            LoadPolicy.handleMemoryPolicy(load, i);
            return load.getGif();
        } catch (Exception e) {
            LogHelper.e("DefaultImageExecutor: log gif error!!! e = ".concat(String.valueOf(e)));
            return null;
        }
    }
}
