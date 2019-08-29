package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import java.io.File;

public class ImageSizeUtils {
    private static final int IMAGE_SIZE_1X = 1;
    private static final int IMAGE_SIZE_2X = 2;
    private static final int IMAGE_SIZE_3X = 3;
    public static final int IMAGE_SIZE_DEFAULT = 2;

    public static String getSizeName(int i) {
        switch (i) {
            case 1:
                return "@1x";
            case 2:
                return "@2x";
            case 3:
                return "@3x";
            default:
                return "";
        }
    }

    public static String getSizeNameForWeb(int i) {
        switch (i) {
            case 1:
                return "@2x";
            case 2:
                return "@2x";
            case 3:
                return "@3x";
            default:
                return "@2x";
        }
    }

    public static String getSizeNameInFile(@NonNull Context context, @NonNull String str) {
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf < 0) {
            return "";
        }
        int imageSizeByPhone = getImageSizeByPhone(context);
        String sizeName = getSizeName(imageSizeByPhone);
        String substring = str.substring(0, lastIndexOf);
        String substring2 = str.substring(lastIndexOf, str.length());
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append(sizeName);
        sb.append(substring2);
        File file = new File(sb.toString());
        while (!file.exists()) {
            String nextSizeName = getNextSizeName(sizeName, imageSizeByPhone);
            if (TextUtils.equals(nextSizeName, sizeName)) {
                break;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(substring);
            sb2.append(nextSizeName);
            sb2.append(substring2);
            File file2 = new File(sb2.toString());
            sizeName = nextSizeName;
            file = file2;
        }
        return sizeName;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:15|16|(1:18)(1:19)|(3:21|22|36)(1:35)) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0053, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r6 = getNextSizeName(r2, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005d, code lost:
        if (android.text.TextUtils.equals(r6, r2) == false) goto L_0x005f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005f, code lost:
        r2 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0061, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0068, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0069, code lost:
        r6.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006d, code lost:
        if (r0 != null) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0073, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0074, code lost:
        r9.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0077, code lost:
        throw r8;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0055 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getSizeNameInAsset(@android.support.annotation.NonNull android.content.Context r8, @android.support.annotation.NonNull java.lang.String r9) {
        /*
            java.lang.String r0 = "."
            int r0 = r9.lastIndexOf(r0)
            if (r0 >= 0) goto L_0x000b
            java.lang.String r8 = ""
            return r8
        L_0x000b:
            android.content.res.AssetManager r1 = r8.getAssets()
            int r8 = getImageSizeByPhone(r8)
            java.lang.String r2 = getSizeName(r8)
            int r3 = r9.length()
            java.lang.String r3 = r9.substring(r0, r3)
            java.lang.String r4 = "android_asset/"
            int r4 = r9.indexOf(r4)
            int r4 = r4 + 14
            java.lang.String r9 = r9.substring(r4, r0)
            r0 = 0
            r4 = 1
            r5 = 0
        L_0x002e:
            if (r4 == 0) goto L_0x0078
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0055 }
            r6.<init>()     // Catch:{ IOException -> 0x0055 }
            r6.append(r9)     // Catch:{ IOException -> 0x0055 }
            r6.append(r2)     // Catch:{ IOException -> 0x0055 }
            r6.append(r3)     // Catch:{ IOException -> 0x0055 }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0055 }
            java.io.InputStream r6 = r1.open(r6)     // Catch:{ IOException -> 0x0055 }
            if (r6 == 0) goto L_0x0050
            r6.close()     // Catch:{ Exception -> 0x004c }
            goto L_0x0050
        L_0x004c:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0050:
            r0 = r6
            r4 = 0
            goto L_0x002e
        L_0x0053:
            r8 = move-exception
            goto L_0x006d
        L_0x0055:
            java.lang.String r6 = getNextSizeName(r2, r8)     // Catch:{ all -> 0x0053 }
            boolean r7 = android.text.TextUtils.equals(r6, r2)     // Catch:{ all -> 0x0053 }
            if (r7 != 0) goto L_0x0061
            r2 = r6
            goto L_0x0062
        L_0x0061:
            r4 = 0
        L_0x0062:
            if (r0 == 0) goto L_0x002e
            r0.close()     // Catch:{ Exception -> 0x0068 }
            goto L_0x002e
        L_0x0068:
            r6 = move-exception
            r6.printStackTrace()
            goto L_0x002e
        L_0x006d:
            if (r0 == 0) goto L_0x0077
            r0.close()     // Catch:{ Exception -> 0x0073 }
            goto L_0x0077
        L_0x0073:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0077:
            throw r8
        L_0x0078:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.ImageSizeUtils.getSizeNameInAsset(android.content.Context, java.lang.String):java.lang.String");
    }

    public static String getSizeNameForNetworkFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(".");
        int lastIndexOf2 = str.lastIndexOf("/");
        if (lastIndexOf < 3 || lastIndexOf2 > lastIndexOf) {
            return "";
        }
        String substring = str.substring(lastIndexOf - 3, lastIndexOf);
        return substring.startsWith(AUScreenAdaptTool.PREFIX_ID) ? substring : "";
    }

    public static String getSizeNameFromAjxFile(@NonNull Context context, String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf < 0) {
            return "";
        }
        int imageSizeByPhone = getImageSizeByPhone(context);
        String sizeName = getSizeName(imageSizeByPhone);
        String substring = str.substring(lastIndexOf, str.length());
        boolean z = false;
        String substring2 = str.substring(0, lastIndexOf);
        while (true) {
            StringBuilder sb = new StringBuilder();
            sb.append(substring2);
            sb.append(sizeName);
            sb.append(substring);
            if (!AjxFileInfo.isFileExists(sb.toString(), i)) {
                String nextSizeName = getNextSizeName(sizeName, imageSizeByPhone);
                if (TextUtils.equals(nextSizeName, sizeName)) {
                    break;
                }
                sizeName = nextSizeName;
            } else {
                z = true;
                break;
            }
        }
        return z ? sizeName : "";
    }

    public static String getImagePathBySize(@NonNull String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf < 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, lastIndexOf));
        sb.append(str2);
        sb.append(str.substring(lastIndexOf, str.length()));
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getImageSizeByName(java.lang.String r5) {
        /*
            r0 = 2
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            r1 = -1
            int r2 = r5.hashCode()
            r3 = 63143(0xf6a7, float:8.8482E-41)
            r4 = 1
            if (r2 == r3) goto L_0x002e
            r3 = 63174(0xf6c6, float:8.8526E-41)
            if (r2 == r3) goto L_0x0024
            r3 = 63205(0xf6e5, float:8.8569E-41)
            if (r2 == r3) goto L_0x001a
            goto L_0x0038
        L_0x001a:
            java.lang.String r2 = "@3x"
            boolean r5 = r5.equals(r2)
            if (r5 == 0) goto L_0x0038
            r5 = 0
            goto L_0x0039
        L_0x0024:
            java.lang.String r2 = "@2x"
            boolean r5 = r5.equals(r2)
            if (r5 == 0) goto L_0x0038
            r5 = 1
            goto L_0x0039
        L_0x002e:
            java.lang.String r2 = "@1x"
            boolean r5 = r5.equals(r2)
            if (r5 == 0) goto L_0x0038
            r5 = 2
            goto L_0x0039
        L_0x0038:
            r5 = -1
        L_0x0039:
            switch(r5) {
                case 0: goto L_0x003f;
                case 1: goto L_0x0040;
                case 2: goto L_0x003d;
                default: goto L_0x003c;
            }
        L_0x003c:
            goto L_0x0040
        L_0x003d:
            r0 = 1
            goto L_0x0040
        L_0x003f:
            r0 = 3
        L_0x0040:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.ImageSizeUtils.getImageSizeByName(java.lang.String):int");
    }

    public static int getImageSizeByPhone(@NonNull Context context) {
        float f = context.getResources().getDisplayMetrics().density;
        if (f >= 2.5f) {
            return 3;
        }
        return f >= 1.5f ? 2 : 1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005a A[FALL_THROUGH] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getNextSizeName(java.lang.String r4, int r5) {
        /*
            r0 = 1
            if (r5 == r0) goto L_0x000c
            java.lang.String r1 = "@1x"
            boolean r1 = android.text.TextUtils.equals(r1, r4)
            if (r1 == 0) goto L_0x000c
            return r4
        L_0x000c:
            if (r5 != r0) goto L_0x0017
            java.lang.String r1 = ""
            boolean r1 = android.text.TextUtils.equals(r1, r4)
            if (r1 == 0) goto L_0x0017
            return r4
        L_0x0017:
            r1 = -1
            int r2 = r4.hashCode()
            r3 = 63143(0xf6a7, float:8.8482E-41)
            if (r2 == r3) goto L_0x0040
            r3 = 63174(0xf6c6, float:8.8526E-41)
            if (r2 == r3) goto L_0x0036
            r3 = 63205(0xf6e5, float:8.8569E-41)
            if (r2 == r3) goto L_0x002c
            goto L_0x004a
        L_0x002c:
            java.lang.String r2 = "@3x"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x004a
            r4 = 0
            goto L_0x004b
        L_0x0036:
            java.lang.String r2 = "@2x"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x004a
            r4 = 1
            goto L_0x004b
        L_0x0040:
            java.lang.String r2 = "@1x"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x004a
            r4 = 2
            goto L_0x004b
        L_0x004a:
            r4 = -1
        L_0x004b:
            switch(r4) {
                case 0: goto L_0x0057;
                case 1: goto L_0x0054;
                case 2: goto L_0x004f;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x005a
        L_0x004f:
            if (r5 != r0) goto L_0x005a
            java.lang.String r4 = "@2x"
            goto L_0x005c
        L_0x0054:
            java.lang.String r4 = ""
            goto L_0x005c
        L_0x0057:
            java.lang.String r4 = "@2x"
            goto L_0x005c
        L_0x005a:
            java.lang.String r4 = ""
        L_0x005c:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.ImageSizeUtils.getNextSizeName(java.lang.String, int):java.lang.String");
    }

    public static float[] getBitmapSize(@NonNull String str) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return new float[]{(float) options.outWidth, (float) options.outHeight};
    }

    public static float[] getBitmapSize(@NonNull Resources resources, int i) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        return new float[]{(float) options.outWidth, (float) options.outHeight};
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0019, code lost:
        r4 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0019 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0007] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static float[] getBitmapSize(@android.support.annotation.NonNull android.content.res.AssetManager r3, java.lang.String r4) {
        /*
            r0 = 1
            r1 = 0
            r2 = 0
            java.io.InputStream r3 = r3.open(r4)     // Catch:{ IOException -> 0x0021, all -> 0x001b }
            android.graphics.BitmapFactory$Options r4 = new android.graphics.BitmapFactory$Options     // Catch:{ IOException -> 0x0022, all -> 0x0019 }
            r4.<init>()     // Catch:{ IOException -> 0x0022, all -> 0x0019 }
            r4.inJustDecodeBounds = r0     // Catch:{ IOException -> 0x0022, all -> 0x0019 }
            android.graphics.BitmapFactory.decodeStream(r3, r1, r4)     // Catch:{ IOException -> 0x0022, all -> 0x0019 }
            int r1 = r4.outWidth     // Catch:{ IOException -> 0x0022, all -> 0x0019 }
            int r4 = r4.outHeight     // Catch:{ IOException -> 0x0023, all -> 0x0019 }
            com.autonavi.minimap.ajx3.util.CloseableUtils.close(r3)
            goto L_0x0027
        L_0x0019:
            r4 = move-exception
            goto L_0x001d
        L_0x001b:
            r4 = move-exception
            r3 = r1
        L_0x001d:
            com.autonavi.minimap.ajx3.util.CloseableUtils.close(r3)
            throw r4
        L_0x0021:
            r3 = r1
        L_0x0022:
            r1 = 0
        L_0x0023:
            com.autonavi.minimap.ajx3.util.CloseableUtils.close(r3)
            r4 = 0
        L_0x0027:
            r3 = 2
            float[] r3 = new float[r3]
            float r1 = (float) r1
            r3[r2] = r1
            float r4 = (float) r4
            r3[r0] = r4
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.ImageSizeUtils.getBitmapSize(android.content.res.AssetManager, java.lang.String):float[]");
    }
}
