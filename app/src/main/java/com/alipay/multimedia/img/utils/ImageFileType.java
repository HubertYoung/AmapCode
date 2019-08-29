package com.alipay.multimedia.img.utils;

import android.os.Looper;
import android.text.TextUtils;
import com.alipay.multimedia.img.Format;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.io.IOUtils;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageFileType implements Format {
    private static final byte[] HEAD_GIF = {HEAD_GIF_0, 73, 70, 56};
    public static final byte HEAD_GIF_0 = 71;
    private static final byte[] HEAD_HEVC = {HEAD_HEVC_0, 69, 86, 67};
    public static final byte HEAD_HEVC_0 = 72;
    private static final byte[] HEAD_HEVC_ORI = {0, 0, 0, 1};
    public static final byte HEAD_HEVC_ORI_0 = 0;
    private static final byte[] HEAD_JPG = {-1, -40};
    public static final byte HEAD_JPG_0 = -1;
    private static final byte[] HEAD_PNG = {HEAD_PNG_0, 80, 78, HEAD_GIF_0, 13, 10, 26, 10};
    private static final byte HEAD_PNG_0 = -119;
    private static final byte[] HEAD_WEBP = {HEAD_WEBP_0, 73, 70, 70};
    public static final byte HEAD_WEBP_0 = 82;
    private static final String TAG = "ImageFileType";
    private static final Map<Integer, String> sTypeSuffix;

    static {
        HashMap hashMap = new HashMap(5);
        sTypeSuffix = hashMap;
        hashMap.put(Integer.valueOf(1), ".png");
        sTypeSuffix.put(Integer.valueOf(0), ".jpg");
        sTypeSuffix.put(Integer.valueOf(2), ".gif");
        sTypeSuffix.put(Integer.valueOf(4), ".webp");
        sTypeSuffix.put(Integer.valueOf(5), Format.SUFFIX_HEVC);
        try {
            MMNativeEngineApi.loadLibrariesOnce(new SoLibLoader());
        } catch (Throwable t) {
            LogUtils.e(TAG, "load native so error", t);
        }
    }

    public static String getSuffixByType(int type) {
        return sTypeSuffix.get(Integer.valueOf(type));
    }

    public static int detectImageFileType(File imageFile) {
        return getTypeByHeader(6, getImageFileHeader(imageFile));
    }

    public static int detectImageFileType(InputStream inputStream) {
        return getTypeByHeader(6, getImageFileHeader(inputStream));
    }

    public static int detectImageDataType(byte[] data) {
        if (data == null || data.length <= 0) {
            return 6;
        }
        byte[] header = new byte[8];
        System.arraycopy(data, 0, header, 0, 8);
        return getTypeByHeader(6, header);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
        if (matchImageFileHeader(HEAD_WEBP, r2) == false) goto L_0x002f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        if (matchImageFileHeader(HEAD_HEVC, r2) == false) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003f, code lost:
        if (matchImageFileHeader(HEAD_HEVC_ORI, r2) == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0017, code lost:
        if (matchImageFileHeader(HEAD_JPG, r2) == false) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0021, code lost:
        if (matchImageFileHeader(HEAD_GIF, r2) == false) goto L_0x0025;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getTypeByHeader(int r1, byte[] r2) {
        /*
            r0 = 0
            byte r0 = r2[r0]
            switch(r0) {
                case -119: goto L_0x0007;
                case -1: goto L_0x0011;
                case 0: goto L_0x0039;
                case 71: goto L_0x001b;
                case 72: goto L_0x002f;
                case 82: goto L_0x0025;
                default: goto L_0x0006;
            }
        L_0x0006:
            return r1
        L_0x0007:
            byte[] r0 = HEAD_PNG
            boolean r0 = matchImageFileHeader(r0, r2)
            if (r0 == 0) goto L_0x0011
            r1 = 1
            goto L_0x0006
        L_0x0011:
            byte[] r0 = HEAD_JPG
            boolean r0 = matchImageFileHeader(r0, r2)
            if (r0 == 0) goto L_0x001b
            r1 = 0
            goto L_0x0006
        L_0x001b:
            byte[] r0 = HEAD_GIF
            boolean r0 = matchImageFileHeader(r0, r2)
            if (r0 == 0) goto L_0x0025
            r1 = 2
            goto L_0x0006
        L_0x0025:
            byte[] r0 = HEAD_WEBP
            boolean r0 = matchImageFileHeader(r0, r2)
            if (r0 == 0) goto L_0x002f
            r1 = 4
            goto L_0x0006
        L_0x002f:
            byte[] r0 = HEAD_HEVC
            boolean r0 = matchImageFileHeader(r0, r2)
            if (r0 == 0) goto L_0x0039
            r1 = 5
            goto L_0x0006
        L_0x0039:
            byte[] r0 = HEAD_HEVC_ORI
            boolean r0 = matchImageFileHeader(r0, r2)
            if (r0 == 0) goto L_0x0006
            r1 = 5
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.img.utils.ImageFileType.getTypeByHeader(int, byte[]):int");
    }

    private static boolean matchImageFileHeader(byte[] toMatchHeader, byte[] header) {
        if (header.length < toMatchHeader.length) {
            return false;
        }
        boolean flag = true;
        for (int i = 1; i < toMatchHeader.length && flag; i++) {
            if (toMatchHeader[i] == header[i]) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0081  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] getImageFileHeader(java.io.File r10) {
        /*
            r5 = 8
            byte[] r3 = new byte[r5]
            if (r10 == 0) goto L_0x0034
            boolean r5 = r10.exists()
            if (r5 == 0) goto L_0x0034
            boolean r5 = r10.isFile()
            if (r5 == 0) goto L_0x0034
            long r6 = r10.length()
            r8 = 0
            int r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r5 <= 0) goto L_0x0034
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0039 }
            r2.<init>(r10)     // Catch:{ Exception -> 0x0039 }
            r2.read(r3)     // Catch:{ Exception -> 0x0088, all -> 0x0085 }
            boolean r5 = isInMainThread()
            if (r5 == 0) goto L_0x0035
            r4 = r2
            com.alipay.multimedia.img.utils.ImageFileType$1 r5 = new com.alipay.multimedia.img.utils.ImageFileType$1
            r5.<init>(r4)
            com.googlecode.androidannotations.api.BackgroundExecutor.execute(r5)
        L_0x0034:
            return r3
        L_0x0035:
            com.alipay.multimedia.io.IOUtils.closeQuietly(r2)
            goto L_0x0034
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            java.lang.String r5 = "ImageFileType"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0070 }
            java.lang.String r7 = "read file: "
            r6.<init>(r7)     // Catch:{ all -> 0x0070 }
            java.lang.StringBuilder r6 = r6.append(r10)     // Catch:{ all -> 0x0070 }
            java.lang.String r7 = ", error: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x0070 }
            java.lang.String r7 = r0.getMessage()     // Catch:{ all -> 0x0070 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x0070 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0070 }
            com.alipay.multimedia.img.utils.LogUtils.w(r5, r6)     // Catch:{ all -> 0x0070 }
            boolean r5 = isInMainThread()
            if (r5 == 0) goto L_0x006c
            r4 = r1
            com.alipay.multimedia.img.utils.ImageFileType$1 r5 = new com.alipay.multimedia.img.utils.ImageFileType$1
            r5.<init>(r4)
            com.googlecode.androidannotations.api.BackgroundExecutor.execute(r5)
            goto L_0x0034
        L_0x006c:
            com.alipay.multimedia.io.IOUtils.closeQuietly(r1)
            goto L_0x0034
        L_0x0070:
            r5 = move-exception
        L_0x0071:
            boolean r6 = isInMainThread()
            if (r6 == 0) goto L_0x0081
            r4 = r1
            com.alipay.multimedia.img.utils.ImageFileType$1 r6 = new com.alipay.multimedia.img.utils.ImageFileType$1
            r6.<init>(r4)
            com.googlecode.androidannotations.api.BackgroundExecutor.execute(r6)
        L_0x0080:
            throw r5
        L_0x0081:
            com.alipay.multimedia.io.IOUtils.closeQuietly(r1)
            goto L_0x0080
        L_0x0085:
            r5 = move-exception
            r1 = r2
            goto L_0x0071
        L_0x0088:
            r0 = move-exception
            r1 = r2
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.img.utils.ImageFileType.getImageFileHeader(java.io.File):byte[]");
    }

    private static byte[] getImageFileHeader(InputStream inputStream) {
        byte[] header = new byte[8];
        if (inputStream != null) {
            final InputStream fis = inputStream;
            try {
                fis.read(header);
                if (isInMainThread()) {
                    BackgroundExecutor.execute((Runnable) new Runnable() {
                        public final void run() {
                            IOUtils.closeQuietly(fis);
                        }
                    });
                } else {
                    IOUtils.closeQuietly(fis);
                }
            } catch (Exception e) {
                LogUtils.w(TAG, "read inputstream file exp= " + e.getMessage());
                if (isInMainThread()) {
                    BackgroundExecutor.execute((Runnable) new Runnable() {
                        public final void run() {
                            IOUtils.closeQuietly(fis);
                        }
                    });
                } else {
                    IOUtils.closeQuietly(fis);
                }
            } catch (Throwable th) {
                if (isInMainThread()) {
                    BackgroundExecutor.execute((Runnable) new Runnable() {
                        public final void run() {
                            IOUtils.closeQuietly(fis);
                        }
                    });
                } else {
                    IOUtils.closeQuietly(fis);
                }
                throw th;
            }
        }
        return header;
    }

    public static int detectImageFileType(String file) {
        if (!TextUtils.isEmpty(file)) {
            return detectImageFileType(new File(file));
        }
        return 6;
    }

    public static boolean isJPEG(File file) {
        return detectImageFileType(file) == 0;
    }

    public static boolean isJPEG(byte[] data) {
        return detectImageDataType(data) == 0;
    }

    public static boolean isJPEG(int ImageType) {
        return ImageType == 0;
    }

    public static boolean isGif(byte[] data) {
        return detectImageDataType(data) == 2;
    }

    public static boolean isGif(File file) {
        return detectImageFileType(file) == 2;
    }

    public static boolean isGif(int ImageType) {
        return ImageType == 2;
    }

    public static int getHevcVer() {
        if (StaticOptions.supportNativeProcess) {
            try {
                return MMNativeEngineApi.getHevcDecoderVersion();
            } catch (MMNativeException e) {
                LogUtils.e(TAG, "getHevcVer exp code=" + e.getCode(), e);
            }
        }
        return -1;
    }

    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
