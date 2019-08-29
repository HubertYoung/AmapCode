package com.autonavi.minimap.ajx3.loader.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.FileUtil;
import com.autonavi.minimap.ajx3.util.ImageSizeUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import pl.droidsonroids.gif.GifDrawable;

public class AjxResourceLoadAction extends AbstractLoadAction {
    public static final String QUERY_KEY_AJX_PATCH = "patch";
    public static final String SCHEME_AJX3_RESOURCE = "ajx.resource";

    public AjxResourceLoadAction() {
    }

    public AjxResourceLoadAction(AjxLoadExecutor ajxLoadExecutor) {
        super(ajxLoadExecutor);
    }

    public float[] readImageSize(@NonNull Context context, @NonNull PictureParams pictureParams) {
        int i;
        int i2;
        String realPath = getRealPath(context, pictureParams);
        int[] imgDimonsions = AjxFileInfo.getImgDimonsions(realPath, pictureParams.patchIndex);
        if (imgDimonsions == null || imgDimonsions.length < 2) {
            i2 = 0;
            i = 0;
        } else {
            i = imgDimonsions[0];
            i2 = imgDimonsions[1];
        }
        if (i <= 0 || i2 <= 0) {
            Bitmap loadAjxBitmap = loadAjxBitmap(realPath, pictureParams.patchIndex);
            if (loadAjxBitmap != null) {
                i = loadAjxBitmap.getWidth();
                i2 = loadAjxBitmap.getHeight();
            }
        }
        return new float[]{(float) i, (float) i2, pictureParams.imageSize};
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext) {
        StringBuilder sb = new StringBuilder("AjxResourceLoadAction: loadImage ajxPath = ");
        sb.append(pictureParams.realUrl);
        LogHelper.d(sb.toString());
        loadImage(context, pictureParams, imageCallback);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        String realPath = getRealPath(context, pictureParams);
        Uri buildAjxInnerUri = buildAjxInnerUri(realPath, pictureParams.patchIndex);
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (PathUtils.isGif(realPath)) {
            i = handleGifPolicy(i);
        }
        if (pictureParams.isRunOnUI || pictureParams.isSyncLoadImg) {
            doLoadImageSync(context, buildAjxInnerUri, i, imageCallback);
        } else {
            this.mExecutor.doLoadImage(context, buildAjxInnerUri, i, imageCallback);
        }
    }

    public Bitmap loadBitmap(@NonNull Context context, @NonNull PictureParams pictureParams) {
        StringBuilder sb = new StringBuilder("AjxResourceLoadAction: loadBitmap ajxPath = ");
        sb.append(pictureParams.realUrl);
        LogHelper.d(sb.toString());
        Uri buildAjxInnerUri = buildAjxInnerUri(getRealPath(context, pictureParams), pictureParams.patchIndex);
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        return this.mExecutor.doLoadBitmap(context, buildAjxInnerUri, i);
    }

    public GifDrawable loadGif(@NonNull Context context, @NonNull PictureParams pictureParams) {
        Uri buildAjxInnerUri = buildAjxInnerUri(getRealPath(context, pictureParams), pictureParams.patchIndex);
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        return this.mExecutor.doLoadGif(context, buildAjxInnerUri, handleGifPolicy(i));
    }

    public byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams) {
        StringBuilder sb = new StringBuilder("AjxResourceLoadAction: loadImage ajxPath = ");
        sb.append(pictureParams.realUrl);
        LogHelper.d(sb.toString());
        byte[] fileDataByPath = AjxFileInfo.getFileDataByPath(getRealPath(context, pictureParams), pictureParams.patchIndex);
        if (!pictureParams.isNeedScale) {
            return fileDataByPath;
        }
        if (fileDataByPath != null && fileDataByPath.length > 0) {
            Bitmap loadBitmap = FileUtil.loadBitmap(context, fileDataByPath, pictureParams.imageSize);
            if (loadBitmap != null) {
                pictureParams.bitmapWidth = loadBitmap.getWidth();
                pictureParams.bitmapHeight = loadBitmap.getHeight();
                ByteBuffer allocate = ByteBuffer.allocate(loadBitmap.getByteCount());
                loadBitmap.copyPixelsToBuffer(allocate);
                return allocate.array();
            }
        }
        return new byte[0];
    }

    private static String getRealPath(@NonNull Context context, @NonNull PictureParams pictureParams) {
        String sizeNameFromAjxFile = ImageSizeUtils.getSizeNameFromAjxFile(context, pictureParams.realUrl, pictureParams.patchIndex);
        String imagePathBySize = ImageSizeUtils.getImagePathBySize(pictureParams.realUrl, sizeNameFromAjxFile);
        int imageSizeByName = ImageSizeUtils.getImageSizeByName(sizeNameFromAjxFile);
        pictureParams.realUrl = imagePathBySize;
        pictureParams.imageSize = (float) imageSizeByName;
        return imagePathBySize;
    }

    private static Uri buildAjxInnerUri(@NonNull String str, int i) {
        return new Builder().scheme(SCHEME_AJX3_RESOURCE).path(str).appendQueryParameter("patch", String.valueOf(i)).build();
    }

    public static GifDrawable loadAjxGifDrawable(@NonNull String str, int i) {
        if (PathUtils.isGif(str)) {
            byte[] fileDataByPath = AjxFileInfo.getFileDataByPath(str, i);
            if (fileDataByPath != null) {
                try {
                    return new GifDrawable(fileDataByPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Bitmap loadAjxBitmap(@NonNull String str, int i) {
        byte[] fileDataByPath = AjxFileInfo.getFileDataByPath(str, i);
        if (fileDataByPath != null) {
            return BitmapFactory.decodeByteArray(fileDataByPath, 0, fileDataByPath.length);
        }
        return null;
    }

    public static Bitmap loadAjxBitmapWithUri(@NonNull Uri uri) {
        String loadRealPathWithUri = loadRealPathWithUri(uri);
        int patchIndexFromUri = getPatchIndexFromUri(uri);
        if (!TextUtils.isEmpty(loadRealPathWithUri)) {
            return loadAjxBitmap(loadRealPathWithUri, patchIndexFromUri);
        }
        return null;
    }

    public static GifDrawable loadAjxGifDrawableWithUri(@NonNull Uri uri) {
        int patchIndexFromUri = getPatchIndexFromUri(uri);
        String loadRealPathWithUri = loadRealPathWithUri(uri);
        if (!TextUtils.isEmpty(loadRealPathWithUri)) {
            return loadAjxGifDrawable(loadRealPathWithUri, patchIndexFromUri);
        }
        return null;
    }

    public static int getPatchIndexFromUri(@NonNull Uri uri) {
        try {
            return Integer.parseInt(uri.getQueryParameter("patch"));
        } catch (NumberFormatException e) {
            StringBuilder sb = new StringBuilder("AjxResourceLoaderAction: load patch index of url = ");
            sb.append(uri);
            sb.append(" error, error = ");
            sb.append(e);
            LogHelper.e(sb.toString());
            return 0;
        }
    }

    public static String loadRealPathWithUri(@NonNull Uri uri) {
        String path = uri.getPath();
        return (path == null || !path.startsWith("/")) ? path : path.substring(1);
    }
}
