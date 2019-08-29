package com.alipay.multimedia.adjuster.api;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.text.TextUtils;
import android.util.Size;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.multimedia.adjuster.api.data.APMInsertReq;
import com.alipay.multimedia.adjuster.api.data.APMSaveReq;
import com.alipay.multimedia.adjuster.api.data.ICache;
import com.alipay.multimedia.adjuster.utils.IOUilts;
import com.alipay.multimedia.adjuster.utils.Log;
import com.alipay.multimedia.adjuster.utils.PathUtils;
import com.sina.weibo.sdk.utils.FileUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class APMSandboxProcessor {
    private static final int BUFFER_SIZE = 8192;
    private static final String TAG = "APMSandboxProcessor";
    public static final String TEMP_DIR = "/tmp/";
    private static final Log logger = Log.getLogger((String) TAG);
    private static Context mAppContext = null;
    private static ICache sCache = null;

    public static void setApplicationContext(Context context) {
        if (mAppContext == null) {
            mAppContext = context;
        }
    }

    public static void registerICache(ICache cache) {
        if (sCache == null) {
            sCache = cache;
        }
    }

    private static ContentResolver getContentResolver() {
        if (mAppContext != null) {
            return mAppContext.getContentResolver();
        }
        return null;
    }

    public static boolean checkFileExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        if (isContentUriPath(path)) {
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(Uri.parse(path));
                if (inputStream != null) {
                    IOUilts.close(inputStream);
                    return true;
                }
            } catch (FileNotFoundException e) {
                return false;
            } finally {
                IOUilts.close(inputStream);
            }
        }
        File file = new File(PathUtils.extractPath(path));
        if (!file.isFile() || !file.exists()) {
            return false;
        }
        return true;
    }

    public static Bitmap decodeBitmap(String path, Options options) {
        if (TextUtils.isEmpty(path) || options == null) {
            return null;
        }
        boolean z = false;
        if (isContentUriPath(path)) {
            return decodeBitmapByContentUri(getContentResolver(), path, options);
        }
        try {
            return BitmapFactory.decodeFile(PathUtils.extractPath(path), options);
        } catch (Throwable e) {
            logger.e(e, "decodeBitmap exp=", new Object[0]);
            return z;
        }
    }

    public static Bitmap decodeBitmapByContentUri(ContentResolver resolver, String uri, Options options) {
        Bitmap bitmap = null;
        if (!(resolver == null || TextUtils.isEmpty(uri) || options == null)) {
            try {
                ParcelFileDescriptor fileDescriptor = resolver.openFileDescriptor(Uri.parse(uri), UploadQueueMgr.MSGTYPE_REALTIME);
                if (fileDescriptor != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
                    if (VERSION.SDK_INT >= 16) {
                        IOUilts.close((Closeable) fileDescriptor);
                    }
                } else if (VERSION.SDK_INT >= 16) {
                    IOUilts.close((Closeable) fileDescriptor);
                }
            } catch (Throwable th) {
                if (VERSION.SDK_INT >= 16) {
                    IOUilts.close((Closeable) null);
                }
                throw th;
            }
        }
        return bitmap;
    }

    public static boolean isContentUriPath(String uri) {
        return PathUtils.isContentUriPath(uri);
    }

    public static String insertMediaFile(APMInsertReq req) {
        return insertMediaFile(req.getUri(), req.getContext(), req.getMimeType(), req.getDisplayName(), req.getDescription(), req.getSourceData(), req.getSavePrimaryDir(), req.getSaveSecondaryDir());
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x0176  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x019e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String insertMediaFile(android.net.Uri r17, android.content.Context r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, java.lang.Object r22, java.lang.String r23, java.lang.String r24) {
        /*
            if (r17 == 0) goto L_0x0004
            if (r22 != 0) goto L_0x0006
        L_0x0004:
            r11 = 0
        L_0x0005:
            return r11
        L_0x0006:
            r0 = r22
            boolean r14 = r0 instanceof java.lang.String
            if (r14 == 0) goto L_0x0012
            java.lang.String r22 = (java.lang.String) r22
            java.lang.String r22 = com.alipay.multimedia.adjuster.utils.PathUtils.extractPath(r22)
        L_0x0012:
            r0 = r22
            boolean r14 = r0 instanceof android.graphics.Bitmap
            if (r14 == 0) goto L_0x002b
            android.content.ContentResolver r14 = getContentResolver()
            android.graphics.Bitmap r22 = (android.graphics.Bitmap) r22
            r0 = r22
            r1 = r20
            r2 = r21
            r3 = r19
            java.lang.String r11 = insertImage(r14, r0, r1, r2, r3)
            goto L_0x0005
        L_0x002b:
            r0 = r22
            boolean r14 = r0 instanceof java.lang.String
            if (r14 == 0) goto L_0x0062
            r0 = r17
            r1 = r19
            boolean r14 = isImages(r0, r1)
            if (r14 == 0) goto L_0x0062
            android.content.ContentResolver r14 = getContentResolver()     // Catch:{ FileNotFoundException -> 0x004e }
            java.lang.String r22 = (java.lang.String) r22     // Catch:{ FileNotFoundException -> 0x004e }
            r0 = r22
            r1 = r20
            r2 = r21
            r3 = r19
            java.lang.String r11 = insertImage(r14, r0, r1, r2, r3)     // Catch:{ FileNotFoundException -> 0x004e }
            goto L_0x0005
        L_0x004e:
            r6 = move-exception
            com.alipay.multimedia.adjuster.utils.Log r14 = logger
            java.lang.String r15 = "insertMediaFile Failed to insert media file"
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r0 = r16
            r14.e(r6, r15, r0)
            r11 = 0
            goto L_0x0005
        L_0x0062:
            r0 = r22
            boolean r14 = r0 instanceof java.io.InputStream
            if (r14 != 0) goto L_0x0079
            com.alipay.multimedia.adjuster.utils.Log r14 = logger
            java.lang.String r15 = "insertMediaFile fail by invalid dataSource!!!"
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r14.d(r15, r16)
            r11 = 0
            goto L_0x0005
        L_0x0079:
            android.content.ContentValues r13 = new android.content.ContentValues
            r13.<init>()
            android.net.Uri r14 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            r0 = r17
            if (r14 != r0) goto L_0x00ce
            java.lang.String r14 = "_display_name"
            r0 = r20
            r13.put(r14, r0)
            java.lang.String r14 = "mime_type"
            r0 = r19
            r13.put(r14, r0)
            boolean r14 = isAndroidQ()
            if (r14 == 0) goto L_0x00ad
            java.lang.String r14 = "description"
            r0 = r21
            r13.put(r14, r0)
            java.lang.String r14 = "primary_directory"
            r0 = r23
            r13.put(r14, r0)
            java.lang.String r14 = "secondary_directory"
            r0 = r24
            r13.put(r14, r0)
        L_0x00ad:
            r12 = 0
            r11 = 0
            android.content.ContentResolver r5 = r18.getContentResolver()
            r7 = 0
            r0 = r17
            android.net.Uri r12 = r5.insert(r0, r13)     // Catch:{ Exception -> 0x01a7 }
            if (r12 != 0) goto L_0x0127
            r14 = 0
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r14)
            r0 = r22
            boolean r14 = r0 instanceof java.io.InputStream
            if (r14 == 0) goto L_0x00cb
            java.io.InputStream r22 = (java.io.InputStream) r22
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r22)
        L_0x00cb:
            r11 = 0
            goto L_0x0005
        L_0x00ce:
            android.net.Uri r14 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            r0 = r17
            if (r14 != r0) goto L_0x00fe
            java.lang.String r14 = "_display_name"
            r0 = r20
            r13.put(r14, r0)
            java.lang.String r14 = "mime_type"
            r0 = r19
            r13.put(r14, r0)
            boolean r14 = isAndroidQ()
            if (r14 == 0) goto L_0x00ad
            java.lang.String r14 = "description"
            r0 = r21
            r13.put(r14, r0)
            java.lang.String r14 = "primary_directory"
            r0 = r23
            r13.put(r14, r0)
            java.lang.String r14 = "secondary_directory"
            r0 = r24
            r13.put(r14, r0)
            goto L_0x00ad
        L_0x00fe:
            android.net.Uri r14 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            r0 = r17
            if (r14 != r0) goto L_0x00ad
            java.lang.String r14 = "_display_name"
            r0 = r20
            r13.put(r14, r0)
            java.lang.String r14 = "mime_type"
            r0 = r19
            r13.put(r14, r0)
            boolean r14 = isAndroidQ()
            if (r14 == 0) goto L_0x00ad
            java.lang.String r14 = "primary_directory"
            r0 = r23
            r13.put(r14, r0)
            java.lang.String r14 = "secondary_directory"
            r0 = r24
            r13.put(r14, r0)
            goto L_0x00ad
        L_0x0127:
            java.lang.String r14 = "w"
            android.os.ParcelFileDescriptor r10 = r5.openFileDescriptor(r12, r14)     // Catch:{ Exception -> 0x01a7 }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x01a7 }
            java.io.FileDescriptor r14 = r10.getFileDescriptor()     // Catch:{ Exception -> 0x01a7 }
            r8.<init>(r14)     // Catch:{ Exception -> 0x01a7 }
            r14 = 8192(0x2000, float:1.14794E-41)
            byte[] r4 = new byte[r14]     // Catch:{ Exception -> 0x014b, all -> 0x01a4 }
        L_0x013a:
            r0 = r22
            java.io.InputStream r0 = (java.io.InputStream) r0     // Catch:{ Exception -> 0x014b, all -> 0x01a4 }
            r14 = r0
            int r9 = r14.read(r4)     // Catch:{ Exception -> 0x014b, all -> 0x01a4 }
            r14 = -1
            if (r9 == r14) goto L_0x0181
            r14 = 0
            r8.write(r4, r14, r9)     // Catch:{ Exception -> 0x014b, all -> 0x01a4 }
            goto L_0x013a
        L_0x014b:
            r6 = move-exception
            r7 = r8
        L_0x014d:
            com.alipay.multimedia.adjuster.utils.Log r14 = logger     // Catch:{ all -> 0x0194 }
            java.lang.String r15 = "insertMediaFile Failed to insert media file"
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0194 }
            r16 = r0
            r0 = r16
            r14.e(r6, r15, r0)     // Catch:{ all -> 0x0194 }
            if (r12 == 0) goto L_0x0166
            r14 = 0
            r15 = 0
            r5.delete(r12, r14, r15)     // Catch:{ all -> 0x0194 }
            r12 = 0
        L_0x0166:
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r7)
            r0 = r22
            boolean r14 = r0 instanceof java.io.InputStream
            if (r14 == 0) goto L_0x0174
            java.io.InputStream r22 = (java.io.InputStream) r22
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r22)
        L_0x0174:
            if (r12 == 0) goto L_0x017a
            java.lang.String r11 = r12.toString()
        L_0x017a:
            android.content.Context r14 = mAppContext
            com.alipay.multimedia.adjuster.utils.FileUtils.scanPictureAsync(r14, r11)
            goto L_0x0005
        L_0x0181:
            r8.flush()     // Catch:{ Exception -> 0x014b, all -> 0x01a4 }
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r8)
            r0 = r22
            boolean r14 = r0 instanceof java.io.InputStream
            if (r14 == 0) goto L_0x01a9
            java.io.InputStream r22 = (java.io.InputStream) r22
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r22)
            r7 = r8
            goto L_0x0174
        L_0x0194:
            r14 = move-exception
        L_0x0195:
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r7)
            r0 = r22
            boolean r15 = r0 instanceof java.io.InputStream
            if (r15 == 0) goto L_0x01a3
            java.io.InputStream r22 = (java.io.InputStream) r22
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r22)
        L_0x01a3:
            throw r14
        L_0x01a4:
            r14 = move-exception
            r7 = r8
            goto L_0x0195
        L_0x01a7:
            r6 = move-exception
            goto L_0x014d
        L_0x01a9:
            r7 = r8
            goto L_0x0174
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.adjuster.api.APMSandboxProcessor.insertMediaFile(android.net.Uri, android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.Object, java.lang.String, java.lang.String):java.lang.String");
    }

    private static boolean isAndroidQ() {
        return VERSION.SDK_INT >= 10000;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0045  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String insertImage(android.content.ContentResolver r10, android.graphics.Bitmap r11, java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
            r9 = 0
            r8 = 0
            android.content.ContentValues r4 = new android.content.ContentValues
            r4.<init>()
            java.lang.String r5 = "title"
            r4.put(r5, r12)
            java.lang.String r5 = "description"
            r4.put(r5, r13)
            java.lang.String r6 = "mime_type"
            boolean r5 = android.text.TextUtils.isEmpty(r14)
            if (r5 != 0) goto L_0x004f
            r5 = r14
        L_0x001a:
            r4.put(r6, r5)
            r3 = 0
            r2 = 0
            android.net.Uri r5 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI     // Catch:{ Exception -> 0x005f }
            android.net.Uri r3 = r10.insert(r5, r4)     // Catch:{ Exception -> 0x005f }
            if (r11 == 0) goto L_0x0070
            java.io.OutputStream r1 = r10.openOutputStream(r3)     // Catch:{ Exception -> 0x005f }
            boolean r5 = android.text.TextUtils.isEmpty(r14)     // Catch:{ all -> 0x005a }
            if (r5 != 0) goto L_0x0052
            java.lang.String r5 = "image/png"
            boolean r5 = r5.equalsIgnoreCase(r14)     // Catch:{ all -> 0x005a }
            if (r5 == 0) goto L_0x0052
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ all -> 0x005a }
            r6 = 100
            r11.compress(r5, r6, r1)     // Catch:{ all -> 0x005a }
        L_0x0040:
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r1)     // Catch:{ Exception -> 0x005f }
        L_0x0043:
            if (r3 == 0) goto L_0x0049
            java.lang.String r2 = r3.toString()
        L_0x0049:
            android.content.Context r5 = mAppContext
            com.alipay.multimedia.adjuster.utils.FileUtils.scanPictureAsync(r5, r2)
            return r2
        L_0x004f:
            java.lang.String r5 = "image/jpeg"
            goto L_0x001a
        L_0x0052:
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ all -> 0x005a }
            r6 = 80
            r11.compress(r5, r6, r1)     // Catch:{ all -> 0x005a }
            goto L_0x0040
        L_0x005a:
            r5 = move-exception
            com.alipay.multimedia.adjuster.utils.IOUilts.close(r1)     // Catch:{ Exception -> 0x005f }
            throw r5     // Catch:{ Exception -> 0x005f }
        L_0x005f:
            r0 = move-exception
            com.alipay.multimedia.adjuster.utils.Log r5 = logger
            java.lang.String r6 = "Failed to insert image"
            java.lang.Object[] r7 = new java.lang.Object[r9]
            r5.e(r0, r6, r7)
            if (r3 == 0) goto L_0x0043
            r10.delete(r3, r8, r8)
            r3 = 0
            goto L_0x0043
        L_0x0070:
            com.alipay.multimedia.adjuster.utils.Log r5 = logger     // Catch:{ Exception -> 0x005f }
            java.lang.String r6 = "Failed to create thumbnail, removing original"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x005f }
            r5.e(r6, r7)     // Catch:{ Exception -> 0x005f }
            r5 = 0
            r6 = 0
            r10.delete(r3, r5, r6)     // Catch:{ Exception -> 0x005f }
            r3 = 0
            goto L_0x0043
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.adjuster.api.APMSandboxProcessor.insertImage(android.content.ContentResolver, android.graphics.Bitmap, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    private static final String insertImage(ContentResolver cr, String imagePath, String name, String description, String mimeType) {
        FileInputStream stream = new FileInputStream(imagePath);
        try {
            Bitmap bm = BitmapFactory.decodeFile(imagePath);
            String ret = insertImage(cr, bm, name, description, mimeType);
            bm.recycle();
            return ret;
        } finally {
            IOUilts.close((InputStream) stream);
        }
    }

    private static final Bitmap StoreThumbnail(ContentResolver cr, Bitmap source, long id, float width, float height, int kind) {
        Matrix matrix = new Matrix();
        matrix.setScale(width / ((float) source.getWidth()), height / ((float) source.getHeight()));
        Bitmap thumb = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        ContentValues values = new ContentValues(4);
        values.put("kind", Integer.valueOf(kind));
        values.put("image_id", Integer.valueOf((int) id));
        values.put("height", Integer.valueOf(thumb.getHeight()));
        values.put("width", Integer.valueOf(thumb.getWidth()));
        try {
            OutputStream thumbOut = cr.openOutputStream(cr.insert(Thumbnails.EXTERNAL_CONTENT_URI, values));
            thumb.compress(CompressFormat.JPEG, 100, thumbOut);
            thumbOut.close();
            return thumb;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e2) {
            return null;
        }
    }

    public static int deleteMediaFile(Context context, String uri) {
        if (context == null || TextUtils.isEmpty(uri)) {
            return -1;
        }
        if (isContentUriPath(uri)) {
            return context.getContentResolver().delete(Uri.parse(uri), null, null);
        }
        new File(PathUtils.extractPath(uri)).deleteOnExit();
        return 0;
    }

    private static boolean isImages(Uri uri, String mimeType) {
        boolean isImage;
        if (uri == Media.EXTERNAL_CONTENT_URI) {
            isImage = true;
        } else {
            isImage = false;
        }
        boolean isMimeType = TextUtils.isEmpty(mimeType) ? true : mimeType.startsWith(FileUtils.IMAGE_FILE_START) && !mimeType.equalsIgnoreCase("image/gif");
        if (!isImage || !isMimeType) {
            return false;
        }
        return true;
    }

    public static boolean isLocalFile(String path) {
        return PathUtils.isLocalFile(path) || isContentUriPath(path);
    }

    public static String copyContentUriFile(APMSaveReq req) {
        if (req == null || TextUtils.isEmpty(req.getUri())) {
            return null;
        }
        InputStream inputStream = null;
        String savaPath = req.getSavePth();
        try {
            if (TextUtils.isEmpty(req.getSavePth()) && sCache != null) {
                savaPath = sCache.getCacheRootDir() + TEMP_DIR + System.currentTimeMillis();
            }
            if (TextUtils.isEmpty(savaPath)) {
                IOUilts.close((InputStream) null);
                return null;
            }
            inputStream = getContentResolver().openInputStream(Uri.parse(req.getUri()));
            if (inputStream == null || !com.alipay.multimedia.adjuster.utils.FileUtils.safeCopyToFile(inputStream, new File(savaPath))) {
                IOUilts.close(inputStream);
                return null;
            }
            logger.d("copyContentUriFile dest=" + savaPath, new Object[0]);
            return savaPath;
        } catch (Throwable th) {
            com.alipay.multimedia.adjuster.utils.FileUtils.delete(new File(savaPath));
        } finally {
            IOUilts.close(inputStream);
        }
    }

    public static boolean isSandBoxSdk() {
        return VERSION.SDK_INT > 28 || (VERSION.SDK_INT == 28 && VERSION.PREVIEW_SDK_INT > 0);
    }

    public static Bitmap loadThumbnail(Uri uri, Size size) {
        Bitmap bitmap = null;
        if (!isSandBoxSdk()) {
            return bitmap;
        }
        try {
            return getContentResolver().loadThumbnail(uri, size, null);
        } catch (Exception e) {
            logger.d("loadThumbnail exp=" + e.toString(), new Object[0]);
            return bitmap;
        }
    }
}
