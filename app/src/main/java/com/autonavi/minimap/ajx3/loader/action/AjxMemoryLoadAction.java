package com.autonavi.minimap.ajx3.loader.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.net.Uri.Builder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxLoadExecutor;
import com.autonavi.minimap.ajx3.loader.AjxMemoryDataPool;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.nio.ByteBuffer;
import pl.droidsonroids.gif.GifDrawable;

public class AjxMemoryLoadAction extends AbstractLoadAction {
    public static final String SCHEME_AJX3_MEMORY = "ajx.memory";

    public int handleMemoryPolicy(int i) {
        return i | 4;
    }

    public GifDrawable loadGif(@NonNull Context context, @NonNull PictureParams pictureParams) {
        return null;
    }

    public AjxMemoryLoadAction(AjxLoadExecutor ajxLoadExecutor) {
        super(ajxLoadExecutor);
    }

    public float[] readImageSize(@NonNull Context context, @NonNull PictureParams pictureParams) {
        byte[] dataBytes = getDataBytes(pictureParams.realUrl);
        if (dataBytes == null || dataBytes.length <= 0) {
            return new float[3];
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(dataBytes, 0, dataBytes.length, options);
        return new float[]{(float) options.outWidth, (float) options.outHeight, 2.0f};
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback, @Nullable View view, @Nullable IAjxContext iAjxContext) {
        loadImage(context, pictureParams, imageCallback);
    }

    public void loadImage(@NonNull Context context, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        Uri buildAjxInnerUri = buildAjxInnerUri(pictureParams.realUrl);
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        doLoadImageSync(context, buildAjxInnerUri, handleMemoryPolicy(i), imageCallback);
    }

    public byte[] loadImage(@NonNull Context context, @NonNull PictureParams pictureParams) {
        byte[] dataBytes = getDataBytes(pictureParams.realUrl);
        if (pictureParams.isNeedScale && dataBytes != null && dataBytes.length > 0) {
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(dataBytes, 0, dataBytes.length);
            if (decodeByteArray != null) {
                pictureParams.bitmapWidth = decodeByteArray.getWidth();
                pictureParams.bitmapHeight = decodeByteArray.getHeight();
                ByteBuffer allocate = ByteBuffer.allocate(decodeByteArray.getByteCount());
                decodeByteArray.copyPixelsToBuffer(allocate);
                return allocate.array();
            }
        }
        return dataBytes;
    }

    public Bitmap loadBitmap(@NonNull Context context, @NonNull PictureParams pictureParams) {
        Uri buildAjxInnerUri = buildAjxInnerUri(pictureParams.realUrl);
        int i = 0;
        if (pictureParams.isVolatile) {
            i = handleVolatile(0);
        }
        if (PathUtils.isGif(pictureParams.realUrl)) {
            i = handleGifPolicy(i);
        }
        return this.mExecutor.doLoadBitmap(context, buildAjxInnerUri, handleMemoryPolicy(i));
    }

    public static Bitmap loadBitmapWithUri(Uri uri) {
        String path = uri.getPath();
        if (path != null && path.startsWith("/")) {
            path = path.substring(1);
        }
        byte[] dataBytes = getDataBytes(path);
        if (dataBytes == null || dataBytes.length <= 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(dataBytes, 0, dataBytes.length);
    }

    private static Uri buildAjxInnerUri(@NonNull String str) {
        return new Builder().scheme(SCHEME_AJX3_MEMORY).path(str).build();
    }

    private static byte[] getDataBytes(String str) {
        long j;
        try {
            j = Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            j = -1;
        }
        if (j != -1) {
            return AjxMemoryDataPool.getInstance().getDataBytes(j);
        }
        return new byte[0];
    }
}
