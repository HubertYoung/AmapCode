package com.autonavi.minimap.ajx3.loader.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxAssetLoader;
import com.autonavi.minimap.ajx3.loader.AjxLoadExecutor;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.CloseableUtils;
import com.autonavi.minimap.ajx3.util.FileUtil;
import com.autonavi.minimap.ajx3.util.ImageSizeUtils;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import pl.droidsonroids.gif.GifDrawable;

public class AjxAssetLoadAction extends AbstractLoadAction {
    public AjxAssetLoadAction() {
    }

    public AjxAssetLoadAction(AjxLoadExecutor ajxLoadExecutor) {
        super(ajxLoadExecutor);
    }

    public float[] readImageSize(@NonNull Context context, @NonNull PictureParams pictureParams) {
        String realPath = getRealPath(context, pictureParams);
        if (realPath.startsWith(AjxAssetLoader.DOMAIN_ASSET)) {
            realPath = realPath.substring(22);
        }
        float[] bitmapSize = ImageSizeUtils.getBitmapSize(context.getAssets(), realPath);
        return new float[]{bitmapSize[0], bitmapSize[1], pictureParams.imageSize};
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext) {
        loadImage(context, pictureParams, imageCallback);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (PathUtils.isGif(pictureParams.realUrl)) {
            i = handleGifPolicy(i);
        }
        if (pictureParams.isRunOnUI || pictureParams.isSyncLoadImg) {
            doLoadImageSync(context, Uri.parse(getRealPath(context, pictureParams)), i, imageCallback);
        } else {
            this.mExecutor.doLoadImage(context, Uri.parse(getRealPath(context, pictureParams)), i, imageCallback);
        }
    }

    public byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams) {
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open(getRealPath(context, pictureParams).substring(22));
            try {
                if (pictureParams.isNeedScale) {
                    Bitmap loadBitmap = FileUtil.loadBitmap(context, inputStream, pictureParams.imageSize);
                    if (loadBitmap != null) {
                        pictureParams.bitmapWidth = loadBitmap.getWidth();
                        pictureParams.bitmapHeight = loadBitmap.getHeight();
                        ByteBuffer allocate = ByteBuffer.allocate(loadBitmap.getByteCount());
                        loadBitmap.copyPixelsToBuffer(allocate);
                        byte[] array = allocate.array();
                        CloseableUtils.close(inputStream);
                        return array;
                    }
                    CloseableUtils.close(inputStream);
                    return new byte[0];
                }
                byte[] fileBytes = FileUtil.getFileBytes(inputStream);
                CloseableUtils.close(inputStream);
                return fileBytes;
            } catch (IOException unused) {
            } catch (Throwable th) {
                th = th;
                CloseableUtils.close(inputStream);
                throw th;
            }
        } catch (IOException unused2) {
            inputStream = null;
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
            CloseableUtils.close(inputStream);
            throw th;
        }
    }

    public Bitmap loadBitmap(@NonNull Context context, @NonNull PictureParams pictureParams) {
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        return this.mExecutor.doLoadBitmap(context, Uri.parse(getRealPath(context, pictureParams)), i);
    }

    public GifDrawable loadGif(@NonNull Context context, @NonNull PictureParams pictureParams) {
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        return this.mExecutor.doLoadGif(context, Uri.parse(getRealPath(context, pictureParams)), handleGifPolicy(i));
    }

    private String getRealPath(@NonNull Context context, @NonNull PictureParams pictureParams) {
        String str = pictureParams.realUrl;
        if (!TextUtils.isEmpty(str) && Math.abs(pictureParams.imageSize) > 0.001f) {
            return str;
        }
        if (!str.startsWith(AjxAssetLoader.DOMAIN_ASSET)) {
            str = AjxAssetLoader.DOMAIN_ASSET.concat(String.valueOf(str));
        }
        String sizeNameInAsset = ImageSizeUtils.getSizeNameInAsset(context, str);
        String imagePathBySize = ImageSizeUtils.getImagePathBySize(str, sizeNameInAsset);
        pictureParams.imageSize = (float) ImageSizeUtils.getImageSizeByName(sizeNameInAsset);
        pictureParams.realUrl = imagePathBySize;
        return imagePathBySize;
    }
}
