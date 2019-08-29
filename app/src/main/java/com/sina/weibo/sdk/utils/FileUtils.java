package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;

public class FileUtils {
    public static final String IMAGE_FILE_START = "image/";
    public static final String VIDEO_FILE_START = "video/";

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003e, code lost:
        if (r8 != null) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0049, code lost:
        if (r8 != null) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004b, code lost:
        r8.close();
     */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0052  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getPath(android.content.Context r8, android.net.Uri r9) {
        /*
            r0 = 0
            if (r9 == 0) goto L_0x0068
            if (r8 != 0) goto L_0x0006
            goto L_0x0068
        L_0x0006:
            java.lang.String r1 = "content"
            java.lang.String r2 = r9.getScheme()
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x0056
            java.lang.String r1 = "_data"
            java.lang.String[] r4 = new java.lang.String[]{r1}
            android.content.ContentResolver r2 = r8.getContentResolver()     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            r5 = 0
            r6 = 0
            r7 = 0
            r3 = r9
            android.database.Cursor r8 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            if (r8 == 0) goto L_0x003e
            java.lang.String r9 = "_data"
            int r9 = r8.getColumnIndexOrThrow(r9)     // Catch:{ Exception -> 0x003c }
            boolean r1 = r8.moveToFirst()     // Catch:{ Exception -> 0x003c }
            if (r1 == 0) goto L_0x003e
            java.lang.String r9 = r8.getString(r9)     // Catch:{ Exception -> 0x003c }
            if (r8 == 0) goto L_0x003b
            r8.close()
        L_0x003b:
            return r9
        L_0x003c:
            r9 = move-exception
            goto L_0x0046
        L_0x003e:
            if (r8 == 0) goto L_0x0067
            goto L_0x004b
        L_0x0041:
            r9 = move-exception
            r8 = r0
            goto L_0x0050
        L_0x0044:
            r9 = move-exception
            r8 = r0
        L_0x0046:
            r9.printStackTrace()     // Catch:{ all -> 0x004f }
            if (r8 == 0) goto L_0x0067
        L_0x004b:
            r8.close()
            goto L_0x0067
        L_0x004f:
            r9 = move-exception
        L_0x0050:
            if (r8 == 0) goto L_0x0055
            r8.close()
        L_0x0055:
            throw r9
        L_0x0056:
            java.lang.String r8 = "file"
            java.lang.String r1 = r9.getScheme()
            boolean r8 = r8.equalsIgnoreCase(r1)
            if (r8 == 0) goto L_0x0067
            java.lang.String r8 = r9.getPath()
            return r8
        L_0x0067:
            return r0
        L_0x0068:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.utils.FileUtils.getPath(android.content.Context, android.net.Uri):java.lang.String");
    }

    public static String getMIMEType(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf < 0) {
            return "*/*";
        }
        String substring = name.substring(lastIndexOf, name.length());
        if (TextUtils.isEmpty(substring) && substring.length() < 2) {
            return "*/*";
        }
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(substring.substring(1, substring.length()).toLowerCase());
    }

    public static boolean isImageFile(Context context, Uri uri) {
        return getMIMEType(new File(getPath(context, uri))).startsWith(IMAGE_FILE_START);
    }

    public static boolean isVideoFile(Context context, Uri uri) {
        return getMIMEType(new File(getPath(context, uri))).startsWith(VIDEO_FILE_START);
    }
}
