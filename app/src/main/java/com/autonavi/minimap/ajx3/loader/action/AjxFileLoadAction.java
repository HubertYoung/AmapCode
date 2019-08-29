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
import com.autonavi.minimap.ajx3.loader.AjxLoadExecutor;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.FileUtil;
import com.autonavi.minimap.ajx3.util.ImageSizeUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.nio.ByteBuffer;
import pl.droidsonroids.gif.GifDrawable;

public class AjxFileLoadAction extends AbstractLoadAction {
    private int handleSnapshot(int i) {
        return i | 4;
    }

    public AjxFileLoadAction() {
    }

    public AjxFileLoadAction(AjxLoadExecutor ajxLoadExecutor) {
        super(ajxLoadExecutor);
    }

    public float[] readImageSize(@NonNull Context context, @NonNull PictureParams pictureParams) {
        float[] bitmapSize = ImageSizeUtils.getBitmapSize(getRealPath(context, pictureParams));
        return new float[]{bitmapSize[0], bitmapSize[1], pictureParams.imageSize};
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext) {
        StringBuilder sb = new StringBuilder("AjxFileLoadAction: loadImage url = ");
        sb.append(pictureParams.realUrl);
        LogHelper.d(sb.toString());
        loadImage(context, pictureParams, imageCallback);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        String realPath = getRealPath(context, pictureParams);
        if (!FileUtil.checkFileInvalid(realPath)) {
            StringBuilder sb = new StringBuilder("image file:");
            sb.append(realPath);
            sb.append(" doesn't exist!");
            LogHelper.addEngineLoge(sb.toString());
            imageCallback.onBitmapFailed(null);
            return;
        }
        boolean startsWith = realPath.startsWith("/");
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (PathUtils.isGif(realPath)) {
            i = handleGifPolicy(i);
        }
        if (isSnapshot(realPath)) {
            i = handleSnapshot(i);
        }
        if (pictureParams.isRunOnUI || pictureParams.isSyncLoadImg) {
            StringBuilder sb2 = new StringBuilder("file://");
            sb2.append(startsWith ? "" : "/");
            sb2.append(realPath);
            doLoadImageSync(context, Uri.parse(sb2.toString()), i, imageCallback);
            return;
        }
        AjxLoadExecutor ajxLoadExecutor = this.mExecutor;
        StringBuilder sb3 = new StringBuilder("file://");
        sb3.append(startsWith ? "" : "/");
        sb3.append(realPath);
        ajxLoadExecutor.doLoadImage(context, Uri.parse(sb3.toString()), i, imageCallback);
    }

    public byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams) {
        String realPath = getRealPath(context, pictureParams);
        if (!FileUtil.checkFileInvalid(realPath)) {
            StringBuilder sb = new StringBuilder("image file:");
            sb.append(realPath);
            sb.append(" doesn't exist!");
            LogHelper.addEngineLoge(sb.toString());
            return null;
        } else if (!pictureParams.isNeedScale) {
            return FileUtil.getFileBytes(realPath);
        } else {
            Bitmap loadBitmap = FileUtil.loadBitmap(context, realPath, pictureParams.imageSize);
            if (loadBitmap == null) {
                return null;
            }
            pictureParams.bitmapWidth = loadBitmap.getWidth();
            pictureParams.bitmapHeight = loadBitmap.getHeight();
            ByteBuffer allocate = ByteBuffer.allocate(loadBitmap.getByteCount());
            loadBitmap.copyPixelsToBuffer(allocate);
            return allocate.array();
        }
    }

    public Bitmap loadBitmap(@NonNull Context context, @NonNull PictureParams pictureParams) {
        String realPath = getRealPath(context, pictureParams);
        if (!FileUtil.checkFileInvalid(realPath)) {
            StringBuilder sb = new StringBuilder("image file:");
            sb.append(realPath);
            sb.append(" doesn't exist!");
            LogHelper.addEngineLoge(sb.toString());
            return null;
        }
        boolean startsWith = realPath.startsWith("/");
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (isSnapshot(realPath)) {
            i = handleSnapshot(i);
        }
        AjxLoadExecutor ajxLoadExecutor = this.mExecutor;
        StringBuilder sb2 = new StringBuilder("file://");
        sb2.append(startsWith ? "" : "/");
        sb2.append(realPath);
        return ajxLoadExecutor.doLoadBitmap(context, Uri.parse(sb2.toString()), i);
    }

    public GifDrawable loadGif(@NonNull Context context, @NonNull PictureParams pictureParams) {
        String realPath = getRealPath(context, pictureParams);
        if (!FileUtil.checkFileInvalid(realPath)) {
            StringBuilder sb = new StringBuilder("image file:");
            sb.append(realPath);
            sb.append(" doesn't exist!");
            LogHelper.addEngineLoge(sb.toString());
            return null;
        }
        boolean startsWith = realPath.startsWith("/");
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (isSnapshot(realPath)) {
            i = handleSnapshot(i);
        }
        AjxLoadExecutor ajxLoadExecutor = this.mExecutor;
        StringBuilder sb2 = new StringBuilder("file://");
        sb2.append(startsWith ? "" : "/");
        sb2.append(realPath);
        return ajxLoadExecutor.doLoadGif(context, Uri.parse(sb2.toString()), handleGifPolicy(i));
    }

    private static String getRealPath(@NonNull Context context, @NonNull PictureParams pictureParams) {
        String str = pictureParams.realUrl;
        if (!TextUtils.isEmpty(str) && Math.abs(pictureParams.imageSize) > 0.001f) {
            return str;
        }
        String sizeNameInFile = ImageSizeUtils.getSizeNameInFile(context, str);
        String imagePathBySize = ImageSizeUtils.getImagePathBySize(str, sizeNameInFile);
        pictureParams.imageSize = (float) ImageSizeUtils.getImageSizeByName(sizeNameInFile);
        pictureParams.realUrl = imagePathBySize;
        return imagePathBySize;
    }

    private boolean isSnapshot(String str) {
        return !TextUtils.isEmpty(str) && str.contains("/ajx3/snapshot/snapshot");
    }
}
