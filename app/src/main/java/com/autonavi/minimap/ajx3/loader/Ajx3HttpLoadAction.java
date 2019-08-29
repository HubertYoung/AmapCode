package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.action.AjxHttpLoadAction;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.ImageSizeUtils;
import com.autonavi.minimap.ajx3.util.PathUtils;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import java.lang.ref.WeakReference;

public class Ajx3HttpLoadAction extends AjxHttpLoadAction {

    static class ViewPreDrawListener implements OnPreDrawListener {
        private WeakReference<ImageCallback> callback;
        private WeakReference<AjxLoadExecutor> loadExecutor;
        private PictureParams pictureParams;
        private int policy;
        private String url;
        private WeakReference<View> view;

        public ViewPreDrawListener(View view2, String str, PictureParams pictureParams2, ImageCallback imageCallback, AjxLoadExecutor ajxLoadExecutor, int i) {
            this.url = str;
            this.pictureParams = pictureParams2;
            this.view = new WeakReference<>(view2);
            this.callback = new WeakReference<>(imageCallback);
            this.loadExecutor = new WeakReference<>(ajxLoadExecutor);
            this.policy = i;
        }

        public boolean onPreDraw() {
            ImageCallback imageCallback = (ImageCallback) this.callback.get();
            AjxLoadExecutor ajxLoadExecutor = (AjxLoadExecutor) this.loadExecutor.get();
            if (imageCallback == null || ajxLoadExecutor == null) {
                return true;
            }
            View view2 = (View) this.view.get();
            if (view2 == null) {
                return true;
            }
            ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
            if (!viewTreeObserver.isAlive()) {
                return true;
            }
            int width = view2.getWidth();
            int height = view2.getHeight();
            if (width <= 0 || height <= 0) {
                return true;
            }
            viewTreeObserver.removeOnPreDrawListener(this);
            String access$000 = Ajx3HttpLoadAction.processingURL(view2.getContext(), this.url, width, height);
            if (this.pictureParams != null) {
                this.pictureParams.realUrl = access$000;
                ajxLoadExecutor.doLoadImage(view2.getContext(), Uri.parse(access$000), this.policy, imageCallback);
            }
            return true;
        }
    }

    public byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public Ajx3HttpLoadAction() {
    }

    public Ajx3HttpLoadAction(@NonNull AjxLoadExecutor ajxLoadExecutor) {
        super(ajxLoadExecutor);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext) {
        String decode = Uri.decode(pictureParams.realUrl);
        StringBuilder sb = new StringBuilder();
        sb.append(decode);
        sb.append(decode.contains("?") ? "&ent=-1" : "");
        String sb2 = sb.toString();
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (pictureParams.isPreLoad) {
            i = handlePreLoadPolicy(i);
        }
        int handleGifPolicy = PathUtils.isGif(sb2) ? handleGifPolicy(i) : i;
        pictureParams.imageSize = (float) ImageSizeUtils.getImageSizeByName(ImageSizeUtils.getSizeNameForNetworkFile(sb2));
        if (TextUtils.isEmpty(sb2) || (!sb2.contains("$w") && !sb2.contains("$h"))) {
            this.mExecutor.doLoadImage(context, Uri.parse(sb2), handleGifPolicy, imageCallback);
        } else if (view == null) {
            imageCallback.onBitmapFailed(null);
        } else {
            int width = view.getWidth();
            int height = view.getHeight();
            if ((width == 0 || height == 0) && (view instanceof ViewExtension)) {
                ViewExtension viewExtension = (ViewExtension) view;
                float size = viewExtension.getSize("width");
                float size2 = viewExtension.getSize("height");
                int standardUnitToPixelOfLayout = DimensionUtils.standardUnitToPixelOfLayout(size);
                height = DimensionUtils.standardUnitToPixelOfLayout(size2);
                width = standardUnitToPixelOfLayout;
            }
            if (width == 0 || height == 0) {
                ViewPreDrawListener viewPreDrawListener = new ViewPreDrawListener(view, sb2, pictureParams, imageCallback, this.mExecutor, handleGifPolicy);
                view.getViewTreeObserver().addOnPreDrawListener(viewPreDrawListener);
                return;
            }
            String processingURL = processingURL(context, sb2, width, height);
            pictureParams.realUrl = processingURL;
            this.mExecutor.doLoadImage(context, Uri.parse(processingURL), handleGifPolicy, imageCallback);
        }
    }

    /* access modifiers changed from: private */
    public static String processingURL(@NonNull Context context, @NonNull String str, int i, int i2) {
        float f = (float) (context.getApplicationContext().getResources().getDisplayMetrics().densityDpi / 320);
        if (f > 1.0f) {
            i = (int) (((float) i) / f);
            i2 = (int) (((float) i2) / f);
        }
        return str.replaceAll("\\$w", String.valueOf(i)).replaceAll("\\$h", String.valueOf(i2));
    }
}
