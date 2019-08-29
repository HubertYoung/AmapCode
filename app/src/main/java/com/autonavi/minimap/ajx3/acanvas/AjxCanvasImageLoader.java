package com.autonavi.minimap.ajx3.acanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.acanvas.ACanvasImage;
import com.autonavi.minimap.acanvas.IACanvasImageLoader;
import com.autonavi.minimap.acanvas.IACanvasImageLoaderCallback;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.IAjxImageLoader;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.lang.ref.WeakReference;
import pl.droidsonroids.gif.GifDrawable;

public class AjxCanvasImageLoader implements IACanvasImageLoader {
    private final IAjxContext mAjxContext;

    static class WeakReferenceCallback implements ImageCallback {
        WeakReference<IACanvasImageLoaderCallback> mCallbackWeakReference;
        WeakReference<ACanvasImage> mImageWeakReference;

        public void onPrepareLoad(Drawable drawable) {
        }

        public WeakReferenceCallback(@NonNull ACanvasImage aCanvasImage, @NonNull IACanvasImageLoaderCallback iACanvasImageLoaderCallback) {
            this.mCallbackWeakReference = new WeakReference<>(iACanvasImageLoaderCallback);
            this.mImageWeakReference = new WeakReference<>(aCanvasImage);
        }

        public void onGifLoaded(GifDrawable gifDrawable) {
            IACanvasImageLoaderCallback iACanvasImageLoaderCallback = (IACanvasImageLoaderCallback) this.mCallbackWeakReference.get();
            if (iACanvasImageLoaderCallback != null) {
                iACanvasImageLoaderCallback.onFailed();
            }
        }

        public void onBitmapLoaded(Bitmap bitmap) {
            IACanvasImageLoaderCallback iACanvasImageLoaderCallback = (IACanvasImageLoaderCallback) this.mCallbackWeakReference.get();
            if (iACanvasImageLoaderCallback != null) {
                ACanvasImage aCanvasImage = (ACanvasImage) this.mImageWeakReference.get();
                if (aCanvasImage == null || bitmap == null) {
                    iACanvasImageLoaderCallback.onFailed();
                } else {
                    iACanvasImageLoaderCallback.onLoaded(aCanvasImage, bitmap);
                }
            }
        }

        public void onBitmapFailed(Drawable drawable) {
            IACanvasImageLoaderCallback iACanvasImageLoaderCallback = (IACanvasImageLoaderCallback) this.mCallbackWeakReference.get();
            if (iACanvasImageLoaderCallback != null) {
                iACanvasImageLoaderCallback.onFailed();
            }
        }
    }

    public AjxCanvasImageLoader(IAjxContext iAjxContext) {
        this.mAjxContext = iAjxContext;
    }

    public void load(@NonNull Context context, @NonNull ACanvasImage aCanvasImage, boolean z, @NonNull IACanvasImageLoaderCallback iACanvasImageLoaderCallback) {
        String preProcessUrl = PathUtils.preProcessUrl(aCanvasImage.getSrc());
        String scheme = Uri.parse(preProcessUrl).getScheme();
        String jsPath = this.mAjxContext.getJsPath();
        if (TextUtils.isEmpty(scheme) && !TextUtils.isEmpty(jsPath)) {
            preProcessUrl = PathUtils.processPath(jsPath.substring(0, jsPath.lastIndexOf("/") + 1), preProcessUrl);
        }
        IAjxImageLoader lookupLoader = Ajx.getInstance().lookupLoader(preProcessUrl);
        if (lookupLoader == null) {
            iACanvasImageLoaderCallback.onFailed();
            return;
        }
        lookupLoader.loadImage(this.mAjxContext.getDomTree().getRootView(), this.mAjxContext, PictureParams.make(this.mAjxContext, preProcessUrl, z), new WeakReferenceCallback(aCanvasImage, iACanvasImageLoaderCallback));
    }
}
