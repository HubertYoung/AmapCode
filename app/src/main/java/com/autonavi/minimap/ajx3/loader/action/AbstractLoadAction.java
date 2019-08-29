package com.autonavi.minimap.ajx3.loader.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.autonavi.minimap.ajx3.loader.AjxLoadExecutor;
import com.autonavi.minimap.ajx3.loader.DefaultImageExecutor;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.PathUtils;
import pl.droidsonroids.gif.GifDrawable;

public abstract class AbstractLoadAction implements IAjxImageLoadAction {
    public static AjxLoadExecutor AJX_LOAD_EXECUTOR = new DefaultImageExecutor();
    protected AjxLoadExecutor mExecutor;

    public int handleGifPolicy(int i) {
        return i | 16 | 4 | 64;
    }

    public int handlePreLoadPolicy(int i) {
        return i | 4;
    }

    public int handleSVG(int i) {
        return i | 32;
    }

    public int handleVolatile(int i) {
        return i | 2 | 8;
    }

    public AbstractLoadAction() {
        this(null);
    }

    public AbstractLoadAction(AjxLoadExecutor ajxLoadExecutor) {
        this.mExecutor = ajxLoadExecutor == null ? AJX_LOAD_EXECUTOR : ajxLoadExecutor;
    }

    /* access modifiers changed from: 0000 */
    public void doLoadImageSync(Context context, Uri uri, int i, ImageCallback imageCallback) {
        Bitmap bitmap;
        GifDrawable gifDrawable;
        if (PathUtils.isGif(uri)) {
            gifDrawable = this.mExecutor.doLoadGif(context, uri, handleGifPolicy(i));
            bitmap = null;
        } else {
            bitmap = this.mExecutor.doLoadBitmap(context, uri, i);
            gifDrawable = null;
        }
        if (gifDrawable != null) {
            imageCallback.onGifLoaded(gifDrawable);
        } else if (bitmap != null) {
            imageCallback.onBitmapLoaded(bitmap);
        } else {
            imageCallback.onBitmapFailed(null);
        }
    }
}
