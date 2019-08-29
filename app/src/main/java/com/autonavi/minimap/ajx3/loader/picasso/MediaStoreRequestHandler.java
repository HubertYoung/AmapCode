package com.autonavi.minimap.ajx3.loader.picasso;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Thumbnails;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.RequestHandler.Result;
import com.sina.weibo.sdk.utils.FileUtils;
import java.io.IOException;

class MediaStoreRequestHandler extends ContentStreamRequestHandler {
    private static final String[] CONTENT_ORIENTATION = {CaptureParam.ORIENTATION_MODE};

    enum PicassoKind {
        MICRO(3, 96, 96),
        MINI(1, 512, 384),
        FULL(2, -1, -1);
        
        final int androidKind;
        final int height;
        final int width;

        private PicassoKind(int i, int i2, int i3) {
            this.androidKind = i;
            this.width = i2;
            this.height = i3;
        }
    }

    MediaStoreRequestHandler(Context context) {
        super(context);
    }

    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        return "content".equals(uri.getScheme()) && "media".equals(uri.getAuthority());
    }

    public Result load(Request request, int i) throws IOException {
        Bitmap bitmap;
        ContentResolver contentResolver = this.context.getContentResolver();
        int exifOrientation = getExifOrientation(contentResolver, request.uri);
        String type = contentResolver.getType(request.uri);
        int i2 = 1;
        boolean z = type != null && type.startsWith(FileUtils.VIDEO_FILE_START);
        if (request.hasSize()) {
            PicassoKind picassoKind = getPicassoKind(request.targetWidth, request.targetHeight);
            if (z || picassoKind != PicassoKind.FULL) {
                long parseId = ContentUris.parseId(request.uri);
                Options createBitmapOptions = createBitmapOptions(request);
                createBitmapOptions.inJustDecodeBounds = true;
                calculateInSampleSize(request.targetWidth, request.targetHeight, picassoKind.width, picassoKind.height, createBitmapOptions, request);
                if (z) {
                    if (picassoKind != PicassoKind.FULL) {
                        i2 = picassoKind.androidKind;
                    }
                    bitmap = Thumbnails.getThumbnail(contentResolver, parseId, i2, createBitmapOptions);
                } else {
                    bitmap = Images.Thumbnails.getThumbnail(contentResolver, parseId, picassoKind.androidKind, createBitmapOptions);
                }
                Bitmap bitmap2 = bitmap;
                if (bitmap2 != null) {
                    Result result = new Result(bitmap2, null, null, LoadedFrom.DISK, exifOrientation);
                    return result;
                }
            } else {
                Result result2 = new Result(null, null, getInputStream(request), LoadedFrom.DISK, exifOrientation);
                return result2;
            }
        }
        Result result3 = new Result(null, null, getInputStream(request), LoadedFrom.DISK, exifOrientation);
        return result3;
    }

    static PicassoKind getPicassoKind(int i, int i2) {
        if (i <= PicassoKind.MICRO.width && i2 <= PicassoKind.MICRO.height) {
            return PicassoKind.MICRO;
        }
        if (i > PicassoKind.MINI.width || i2 > PicassoKind.MINI.height) {
            return PicassoKind.FULL;
        }
        return PicassoKind.MINI;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0033  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int getExifOrientation(android.content.ContentResolver r8, android.net.Uri r9) {
        /*
            r0 = 0
            r1 = 0
            java.lang.String[] r4 = CONTENT_ORIENTATION     // Catch:{ RuntimeException -> 0x0030, all -> 0x0028 }
            r5 = 0
            r6 = 0
            r7 = 0
            r2 = r8
            r3 = r9
            android.database.Cursor r8 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ RuntimeException -> 0x0030, all -> 0x0028 }
            if (r8 == 0) goto L_0x0022
            boolean r9 = r8.moveToFirst()     // Catch:{ RuntimeException -> 0x0031, all -> 0x0020 }
            if (r9 != 0) goto L_0x0016
            goto L_0x0022
        L_0x0016:
            int r9 = r8.getInt(r0)     // Catch:{ RuntimeException -> 0x0031, all -> 0x0020 }
            if (r8 == 0) goto L_0x001f
            r8.close()
        L_0x001f:
            return r9
        L_0x0020:
            r9 = move-exception
            goto L_0x002a
        L_0x0022:
            if (r8 == 0) goto L_0x0027
            r8.close()
        L_0x0027:
            return r0
        L_0x0028:
            r9 = move-exception
            r8 = r1
        L_0x002a:
            if (r8 == 0) goto L_0x002f
            r8.close()
        L_0x002f:
            throw r9
        L_0x0030:
            r8 = r1
        L_0x0031:
            if (r8 == 0) goto L_0x0036
            r8.close()
        L_0x0036:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.loader.picasso.MediaStoreRequestHandler.getExifOrientation(android.content.ContentResolver, android.net.Uri):int");
    }
}
