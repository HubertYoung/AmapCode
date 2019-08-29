package com.autonavi.minimap.ajx3.modules.platform;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.exception.InvalidParamJsException;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleImage;
import com.autonavi.minimap.ajx3.util.PathUtil;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AjxModuleImage extends AbstractModuleImage {
    private static final String FILE_SCHEME = "file:/";
    private static final int FILE_SCHEME_LEN = 6;

    public AjxModuleImage(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public String getType(String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith(FILE_SCHEME)) {
            return "";
        }
        Options options = new Options();
        BitmapFactory.decodeFile(str.substring(FILE_SCHEME_LEN), options);
        return options.outMimeType;
    }

    public void extractThumbnail(String str, String str2, int i, int i2, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || !str.startsWith(FILE_SCHEME) || !str2.startsWith(FILE_SCHEME)) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(Boolean.FALSE);
            }
            return;
        }
        final String substring = str.substring(FILE_SCHEME_LEN);
        final String substring2 = str2.substring(FILE_SCHEME_LEN);
        ahn b = ahn.b();
        final int i3 = i;
        final int i4 = i2;
        final JsFunctionCallback jsFunctionCallback2 = jsFunctionCallback;
        AnonymousClass1 r1 = new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:15:0x0050  */
            /* JADX WARNING: Removed duplicated region for block: B:24:0x0069  */
            /* JADX WARNING: Removed duplicated region for block: B:31:0x007c  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r8 = this;
                    r0 = 1
                    r1 = 0
                    android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    r2.<init>()     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    com.autonavi.minimap.ajx3.modules.platform.AjxModuleImage r3 = com.autonavi.minimap.ajx3.modules.platform.AjxModuleImage.this     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    java.lang.String r4 = r3     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    int r5 = r4     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    int r6 = r5     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    android.graphics.Bitmap r3 = r3.getImageThumbnail(r4, r5, r6, r2)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    java.lang.String r5 = r6     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    r4.<init>(r5)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    java.io.BufferedOutputStream r5 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    r6.<init>(r4)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    r5.<init>(r6)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    java.lang.String r2 = r2.outMimeType     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    if (r2 == 0) goto L_0x0048
                    java.lang.String r4 = "jpeg"
                    boolean r4 = r2.endsWith(r4)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    r6 = 100
                    if (r4 == 0) goto L_0x0039
                    android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    boolean r2 = r3.compress(r2, r6, r5)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    goto L_0x0049
                L_0x0039:
                    java.lang.String r4 = "png"
                    boolean r2 = r2.endsWith(r4)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    if (r2 == 0) goto L_0x0048
                    android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    boolean r2 = r3.compress(r2, r6, r5)     // Catch:{ Exception -> 0x0077, all -> 0x0063 }
                    goto L_0x0049
                L_0x0048:
                    r2 = 0
                L_0x0049:
                    r5.close()     // Catch:{ Exception -> 0x0078, all -> 0x005e }
                    com.autonavi.minimap.ajx3.core.JsFunctionCallback r3 = r7
                    if (r3 == 0) goto L_0x0087
                    com.autonavi.minimap.ajx3.core.JsFunctionCallback r3 = r7
                    java.lang.Object[] r0 = new java.lang.Object[r0]
                    java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
                    r0[r1] = r2
                L_0x005a:
                    r3.callback(r0)
                    return
                L_0x005e:
                    r3 = move-exception
                    r7 = r3
                    r3 = r2
                    r2 = r7
                    goto L_0x0065
                L_0x0063:
                    r2 = move-exception
                    r3 = 0
                L_0x0065:
                    com.autonavi.minimap.ajx3.core.JsFunctionCallback r4 = r7
                    if (r4 == 0) goto L_0x0076
                    com.autonavi.minimap.ajx3.core.JsFunctionCallback r4 = r7
                    java.lang.Object[] r0 = new java.lang.Object[r0]
                    java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
                    r0[r1] = r3
                    r4.callback(r0)
                L_0x0076:
                    throw r2
                L_0x0077:
                    r2 = 0
                L_0x0078:
                    com.autonavi.minimap.ajx3.core.JsFunctionCallback r3 = r7
                    if (r3 == 0) goto L_0x0087
                    com.autonavi.minimap.ajx3.core.JsFunctionCallback r3 = r7
                    java.lang.Object[] r0 = new java.lang.Object[r0]
                    java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
                    r0[r1] = r2
                    goto L_0x005a
                L_0x0087:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.platform.AjxModuleImage.AnonymousClass1.run():void");
            }
        };
        b.execute(r1);
    }

    /* access modifiers changed from: private */
    public Bitmap getImageThumbnail(String str, int i, int i2, Options options) {
        int i3 = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inJustDecodeBounds = false;
        int min = Math.min(options.outWidth / i, options.outHeight / i2);
        if (min > 0) {
            i3 = min;
        }
        options.inSampleSize = i3;
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(str, options), i, i2, 2);
    }

    public void compress(String str, String str2, int i, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.isEmpty(str) || !str.startsWith(FILE_SCHEME)) {
            if (jsFunctionCallback != null) {
                StringBuilder sb = new StringBuilder("originPath is ");
                sb.append(str);
                sb.append(",must startwith file:/");
                jsFunctionCallback.callback(new InvalidParamJsException(sb.toString()));
            }
        } else if (TextUtils.isEmpty(str) || !str2.startsWith(FILE_SCHEME)) {
            if (jsFunctionCallback != null) {
                StringBuilder sb2 = new StringBuilder("destPath is ");
                sb2.append(str2);
                sb2.append(",must startwith file:/");
                jsFunctionCallback.callback(new InvalidParamJsException(sb2.toString()));
            }
        } else {
            String processPath = PathUtil.processPath(getContext(), str);
            String processPath2 = PathUtil.processPath(getContext(), str2);
            final String noSchemeUrl = PathUtils.getNoSchemeUrl(PathUtils.rectifyFileScheme(processPath));
            final String noSchemeUrl2 = PathUtils.getNoSchemeUrl(PathUtils.rectifyFileScheme(processPath2));
            final int i2 = i * 1024;
            final JsFunctionCallback jsFunctionCallback2 = jsFunctionCallback;
            AnonymousClass2 r0 = new Runnable() {
                /* JADX WARNING: Code restructure failed: missing block: B:15:0x0060, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:18:0x006b, code lost:
                    if (r0.length() > ((long) r5)) goto L_0x009f;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:20:0x0078, code lost:
                    if (defpackage.ahd.a(new java.io.File(r2), r1) == false) goto L_0x0086;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:22:0x007c, code lost:
                    if (r3 == null) goto L_0x009e;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:23:0x007e, code lost:
                    r3.callback(new java.lang.Object[0]);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:24:0x0085, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:26:0x0088, code lost:
                    if (r3 == null) goto L_0x009e;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:27:0x008a, code lost:
                    r3.callback(new com.autonavi.minimap.ajx3.exception.InternalJsException("copy file failed "));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:28:0x009e, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a3, code lost:
                    if (android.os.Build.VERSION.SDK_INT >= 26) goto L_0x00c2;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a5, code lost:
                    r0 = new android.graphics.BitmapFactory.Options();
                    r0.inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888;
                    r0.inJustDecodeBounds = true;
                    android.graphics.BitmapFactory.decodeFile(r2, r0);
                    r0 = ((r0.outWidth * r0.outHeight) * 4) / 16777216;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c2, code lost:
                    r0 = 0;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c3, code lost:
                    r0 = com.autonavi.minimap.ajx3.modules.platform.AjxModuleImage.access$100(r2, r0);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c9, code lost:
                    if (r0 != null) goto L_0x00f2;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:36:0x00cd, code lost:
                    if (r3 == null) goto L_0x00f2;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:37:0x00cf, code lost:
                    r0 = r3;
                    r5 = new java.lang.StringBuilder("compress failed, can not decodeFile: ");
                    r5.append(r2);
                    r0.callback(new com.autonavi.minimap.ajx3.exception.InternalJsException(r5.toString()));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:38:0x00f1, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:39:0x00f2, code lost:
                    r0 = com.autonavi.minimap.ajx3.modules.platform.AjxModuleImage.compressByQuality(r0, (long) r5, true);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:40:0x00f9, code lost:
                    if (r0 != null) goto L_0x0114;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:42:0x00fd, code lost:
                    if (r3 == null) goto L_0x0114;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ff, code lost:
                    r3.callback(new com.autonavi.minimap.ajx3.exception.InternalJsException("compress failed, bitmap error"));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:44:0x0113, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:45:0x0114, code lost:
                    r1 = null;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
                    r4 = new java.io.FileOutputStream(r4);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
                    r4.write(r0);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:50:0x0121, code lost:
                    if (r3 == null) goto L_0x012a;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:51:0x0123, code lost:
                    r3.callback(new java.lang.Object[0]);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
                    r4.close();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:54:0x012d, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:55:0x012e, code lost:
                    r0 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:57:0x0131, code lost:
                    if (r3 != null) goto L_0x0133;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:58:0x0133, code lost:
                    r1 = r3;
                    r6 = new java.lang.StringBuilder("file write fail ");
                    r6.append(r0.getMessage());
                    r1.callback(new com.autonavi.minimap.ajx3.exception.InternalJsException(r6.toString()));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:59:0x0157, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:60:0x0158, code lost:
                    r0 = th;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:61:0x0159, code lost:
                    r1 = r4;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:62:0x015b, code lost:
                    r0 = e;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:63:0x015c, code lost:
                    r1 = r4;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:64:0x015e, code lost:
                    r0 = th;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:65:0x0160, code lost:
                    r0 = e;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:68:0x0163, code lost:
                    if (r3 != null) goto L_0x0165;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:69:0x0165, code lost:
                    r4 = r3;
                    r8 = new java.lang.StringBuilder("file write fail ");
                    r8.append(r0.getMessage());
                    r4.callback(new com.autonavi.minimap.ajx3.exception.InternalJsException(r8.toString()));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:70:0x0189, code lost:
                    if (r1 == null) goto L_0x01b9;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
                    r1.close();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:73:0x018e, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:74:0x018f, code lost:
                    r0 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:76:0x0192, code lost:
                    if (r3 != null) goto L_0x0194;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:77:0x0194, code lost:
                    r1 = r3;
                    r6 = new java.lang.StringBuilder("file write fail ");
                    r6.append(r0.getMessage());
                    r1.callback(new com.autonavi.minimap.ajx3.exception.InternalJsException(r6.toString()));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:78:0x01b8, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:79:0x01b9, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:80:0x01ba, code lost:
                    if (r1 != null) goto L_0x01bc;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
                    r1.close();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:83:0x01c0, code lost:
                    r1 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:85:0x01c3, code lost:
                    if (r3 != null) goto L_0x01c5;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:86:0x01c5, code lost:
                    r4 = r3;
                    r7 = new java.lang.StringBuilder("file write fail ");
                    r7.append(r1.getMessage());
                    r4.callback(new com.autonavi.minimap.ajx3.exception.InternalJsException(r7.toString()));
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:87:0x01e9, code lost:
                    throw r0;
                 */
                /* JADX WARNING: Removed duplicated region for block: B:69:0x0165 A[Catch:{ all -> 0x015e }] */
                /* JADX WARNING: Removed duplicated region for block: B:71:0x018b A[SYNTHETIC, Splitter:B:71:0x018b] */
                /* JADX WARNING: Removed duplicated region for block: B:79:0x01b9 A[RETURN] */
                /* JADX WARNING: Removed duplicated region for block: B:81:0x01bc A[SYNTHETIC, Splitter:B:81:0x01bc] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r10 = this;
                        java.io.File r0 = new java.io.File
                        java.lang.String r1 = r2
                        r0.<init>(r1)
                        boolean r1 = r0.exists()
                        r2 = 1
                        r3 = 0
                        if (r1 == 0) goto L_0x01ed
                        boolean r1 = r0.isFile()
                        if (r1 != 0) goto L_0x0017
                        goto L_0x01ed
                    L_0x0017:
                        java.io.File r1 = new java.io.File
                        java.lang.String r4 = r4
                        r1.<init>(r4)
                        java.io.File r4 = r1.getParentFile()
                        java.lang.String r5 = r4.getAbsolutePath()
                        java.lang.String r5 = r5.intern()
                        monitor-enter(r5)
                        boolean r6 = r4.exists()     // Catch:{ all -> 0x01ea }
                        if (r6 != 0) goto L_0x0061
                        boolean r6 = r4.mkdirs()     // Catch:{ all -> 0x01ea }
                        if (r6 != 0) goto L_0x0061
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3     // Catch:{ all -> 0x01ea }
                        if (r0 == 0) goto L_0x005f
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3     // Catch:{ all -> 0x01ea }
                        java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x01ea }
                        com.autonavi.minimap.ajx3.exception.InternalJsException r6 = new com.autonavi.minimap.ajx3.exception.InternalJsException     // Catch:{ all -> 0x01ea }
                        java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ all -> 0x01ea }
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x01ea }
                        java.lang.String r8 = "mkdirs failed "
                        r7.<init>(r8)     // Catch:{ all -> 0x01ea }
                        java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x01ea }
                        r7.append(r4)     // Catch:{ all -> 0x01ea }
                        java.lang.String r4 = r7.toString()     // Catch:{ all -> 0x01ea }
                        r2[r3] = r4     // Catch:{ all -> 0x01ea }
                        r6.<init>(r2)     // Catch:{ all -> 0x01ea }
                        r1[r3] = r6     // Catch:{ all -> 0x01ea }
                        r0.callback(r1)     // Catch:{ all -> 0x01ea }
                    L_0x005f:
                        monitor-exit(r5)     // Catch:{ all -> 0x01ea }
                        return
                    L_0x0061:
                        monitor-exit(r5)     // Catch:{ all -> 0x01ea }
                        long r4 = r0.length()
                        int r0 = r5
                        long r6 = (long) r0
                        int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                        if (r0 > 0) goto L_0x009f
                        java.io.File r0 = new java.io.File
                        java.lang.String r4 = r2
                        r0.<init>(r4)
                        boolean r0 = defpackage.ahd.a(r0, r1)
                        if (r0 == 0) goto L_0x0086
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3
                        if (r0 == 0) goto L_0x009e
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3
                        java.lang.Object[] r1 = new java.lang.Object[r3]
                        r0.callback(r1)
                        return
                    L_0x0086:
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3
                        if (r0 == 0) goto L_0x009e
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3
                        java.lang.Object[] r1 = new java.lang.Object[r2]
                        com.autonavi.minimap.ajx3.exception.InternalJsException r2 = new com.autonavi.minimap.ajx3.exception.InternalJsException
                        java.lang.String r4 = "copy file failed "
                        java.lang.String[] r4 = new java.lang.String[]{r4}
                        r2.<init>(r4)
                        r1[r3] = r2
                        r0.callback(r1)
                    L_0x009e:
                        return
                    L_0x009f:
                        int r0 = android.os.Build.VERSION.SDK_INT
                        r1 = 26
                        if (r0 >= r1) goto L_0x00c2
                        android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options
                        r0.<init>()
                        android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.ARGB_8888
                        r0.inPreferredConfig = r1
                        r0.inJustDecodeBounds = r2
                        java.lang.String r1 = r2
                        android.graphics.BitmapFactory.decodeFile(r1, r0)
                        int r1 = r0.outWidth
                        int r0 = r0.outHeight
                        int r1 = r1 * r0
                        int r1 = r1 * 4
                        r0 = 16777216(0x1000000, float:2.3509887E-38)
                        int r0 = r1 / r0
                        goto L_0x00c3
                    L_0x00c2:
                        r0 = 0
                    L_0x00c3:
                        java.lang.String r1 = r2
                        android.graphics.Bitmap r0 = com.autonavi.minimap.ajx3.modules.platform.AjxModuleImage.compressBySampleSize(r1, r0)
                        if (r0 != 0) goto L_0x00f2
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r3
                        if (r1 == 0) goto L_0x00f2
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3
                        java.lang.Object[] r1 = new java.lang.Object[r2]
                        com.autonavi.minimap.ajx3.exception.InternalJsException r4 = new com.autonavi.minimap.ajx3.exception.InternalJsException
                        java.lang.String[] r2 = new java.lang.String[r2]
                        java.lang.StringBuilder r5 = new java.lang.StringBuilder
                        java.lang.String r6 = "compress failed, can not decodeFile: "
                        r5.<init>(r6)
                        java.lang.String r6 = r2
                        r5.append(r6)
                        java.lang.String r5 = r5.toString()
                        r2[r3] = r5
                        r4.<init>(r2)
                        r1[r3] = r4
                        r0.callback(r1)
                        return
                    L_0x00f2:
                        int r1 = r5
                        long r4 = (long) r1
                        byte[] r0 = com.autonavi.minimap.ajx3.modules.platform.AjxModuleImage.compressByQuality(r0, r4, r2)
                        if (r0 != 0) goto L_0x0114
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r3
                        if (r1 == 0) goto L_0x0114
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3
                        java.lang.Object[] r1 = new java.lang.Object[r2]
                        com.autonavi.minimap.ajx3.exception.InternalJsException r2 = new com.autonavi.minimap.ajx3.exception.InternalJsException
                        java.lang.String r4 = "compress failed, bitmap error"
                        java.lang.String[] r4 = new java.lang.String[]{r4}
                        r2.<init>(r4)
                        r1[r3] = r2
                        r0.callback(r1)
                        return
                    L_0x0114:
                        r1 = 0
                        java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0160 }
                        java.lang.String r5 = r4     // Catch:{ Exception -> 0x0160 }
                        r4.<init>(r5)     // Catch:{ Exception -> 0x0160 }
                        r4.write(r0)     // Catch:{ Exception -> 0x015b, all -> 0x0158 }
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3     // Catch:{ Exception -> 0x015b, all -> 0x0158 }
                        if (r0 == 0) goto L_0x012a
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3     // Catch:{ Exception -> 0x015b, all -> 0x0158 }
                        java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x015b, all -> 0x0158 }
                        r0.callback(r1)     // Catch:{ Exception -> 0x015b, all -> 0x0158 }
                    L_0x012a:
                        r4.close()     // Catch:{ IOException -> 0x012e }
                        return
                    L_0x012e:
                        r0 = move-exception
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r3
                        if (r1 == 0) goto L_0x0157
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r3
                        java.lang.Object[] r4 = new java.lang.Object[r2]
                        com.autonavi.minimap.ajx3.exception.InternalJsException r5 = new com.autonavi.minimap.ajx3.exception.InternalJsException
                        java.lang.String[] r2 = new java.lang.String[r2]
                        java.lang.StringBuilder r6 = new java.lang.StringBuilder
                        java.lang.String r7 = "file write fail "
                        r6.<init>(r7)
                        java.lang.String r0 = r0.getMessage()
                        r6.append(r0)
                        java.lang.String r0 = r6.toString()
                        r2[r3] = r0
                        r5.<init>(r2)
                        r4[r3] = r5
                        r1.callback(r4)
                    L_0x0157:
                        return
                    L_0x0158:
                        r0 = move-exception
                        r1 = r4
                        goto L_0x01ba
                    L_0x015b:
                        r0 = move-exception
                        r1 = r4
                        goto L_0x0161
                    L_0x015e:
                        r0 = move-exception
                        goto L_0x01ba
                    L_0x0160:
                        r0 = move-exception
                    L_0x0161:
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r4 = r3     // Catch:{ all -> 0x015e }
                        if (r4 == 0) goto L_0x0189
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r4 = r3     // Catch:{ all -> 0x015e }
                        java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x015e }
                        com.autonavi.minimap.ajx3.exception.InternalJsException r6 = new com.autonavi.minimap.ajx3.exception.InternalJsException     // Catch:{ all -> 0x015e }
                        java.lang.String[] r7 = new java.lang.String[r2]     // Catch:{ all -> 0x015e }
                        java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x015e }
                        java.lang.String r9 = "file write fail "
                        r8.<init>(r9)     // Catch:{ all -> 0x015e }
                        java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x015e }
                        r8.append(r0)     // Catch:{ all -> 0x015e }
                        java.lang.String r0 = r8.toString()     // Catch:{ all -> 0x015e }
                        r7[r3] = r0     // Catch:{ all -> 0x015e }
                        r6.<init>(r7)     // Catch:{ all -> 0x015e }
                        r5[r3] = r6     // Catch:{ all -> 0x015e }
                        r4.callback(r5)     // Catch:{ all -> 0x015e }
                    L_0x0189:
                        if (r1 == 0) goto L_0x01b9
                        r1.close()     // Catch:{ IOException -> 0x018f }
                        return
                    L_0x018f:
                        r0 = move-exception
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r3
                        if (r1 == 0) goto L_0x01b8
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r1 = r3
                        java.lang.Object[] r4 = new java.lang.Object[r2]
                        com.autonavi.minimap.ajx3.exception.InternalJsException r5 = new com.autonavi.minimap.ajx3.exception.InternalJsException
                        java.lang.String[] r2 = new java.lang.String[r2]
                        java.lang.StringBuilder r6 = new java.lang.StringBuilder
                        java.lang.String r7 = "file write fail "
                        r6.<init>(r7)
                        java.lang.String r0 = r0.getMessage()
                        r6.append(r0)
                        java.lang.String r0 = r6.toString()
                        r2[r3] = r0
                        r5.<init>(r2)
                        r4[r3] = r5
                        r1.callback(r4)
                    L_0x01b8:
                        return
                    L_0x01b9:
                        return
                    L_0x01ba:
                        if (r1 == 0) goto L_0x01e9
                        r1.close()     // Catch:{ IOException -> 0x01c0 }
                        goto L_0x01e9
                    L_0x01c0:
                        r1 = move-exception
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r4 = r3
                        if (r4 == 0) goto L_0x01e9
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r4 = r3
                        java.lang.Object[] r5 = new java.lang.Object[r2]
                        com.autonavi.minimap.ajx3.exception.InternalJsException r6 = new com.autonavi.minimap.ajx3.exception.InternalJsException
                        java.lang.String[] r2 = new java.lang.String[r2]
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder
                        java.lang.String r8 = "file write fail "
                        r7.<init>(r8)
                        java.lang.String r1 = r1.getMessage()
                        r7.append(r1)
                        java.lang.String r1 = r7.toString()
                        r2[r3] = r1
                        r6.<init>(r2)
                        r5[r3] = r6
                        r4.callback(r5)
                    L_0x01e9:
                        throw r0
                    L_0x01ea:
                        r0 = move-exception
                        monitor-exit(r5)     // Catch:{ all -> 0x01ea }
                        throw r0
                    L_0x01ed:
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3
                        if (r0 == 0) goto L_0x0205
                        com.autonavi.minimap.ajx3.core.JsFunctionCallback r0 = r3
                        java.lang.Object[] r1 = new java.lang.Object[r2]
                        com.autonavi.minimap.ajx3.exception.InternalJsException r2 = new com.autonavi.minimap.ajx3.exception.InternalJsException
                        java.lang.String r4 = "bitmap originPath no exists or no file"
                        java.lang.String[] r4 = new java.lang.String[]{r4}
                        r2.<init>(r4)
                        r1[r3] = r2
                        r0.callback(r1)
                    L_0x0205:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.platform.AjxModuleImage.AnonymousClass2.run():void");
                }
            };
            ahm.c(r0);
        }
    }

    public static byte[] compressByQuality(Bitmap bitmap, long j, boolean z) {
        if (bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0 || j <= 0) {
            return null;
        }
        AMapLog.i("ajx.image.compress", "start compress, size = ".concat(String.valueOf(j)));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = 100;
            bitmap.compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
            if (((long) byteArrayOutputStream.size()) <= j) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AMapLog.i("ajx.image.compress", "end compress");
                return byteArray;
            }
            byteArrayOutputStream.reset();
            int i2 = 0;
            bitmap.compress(CompressFormat.JPEG, 0, byteArrayOutputStream);
            if (((long) byteArrayOutputStream.size()) >= j) {
                byte[] byteArray2 = byteArrayOutputStream.toByteArray();
                if (z && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                AMapLog.i("ajx.image.compress", "end compress");
                return byteArray2;
            }
            int i3 = 0;
            while (i2 < i) {
                i3 = (i2 + i) / 2;
                byteArrayOutputStream.reset();
                bitmap.compress(CompressFormat.JPEG, i3, byteArrayOutputStream);
                int i4 = (((long) byteArrayOutputStream.size()) > j ? 1 : (((long) byteArrayOutputStream.size()) == j ? 0 : -1));
                if (i4 == 0) {
                    break;
                } else if (i4 > 0) {
                    i = i3 - 1;
                } else {
                    i2 = i3 + 1;
                }
            }
            if (i == i3 - 1) {
                byteArrayOutputStream.reset();
                bitmap.compress(CompressFormat.JPEG, i2, byteArrayOutputStream);
            }
            byte[] byteArray3 = byteArrayOutputStream.toByteArray();
            if (z && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            AMapLog.i("ajx.image.compress", "end compress");
            return byteArray3;
        } finally {
            if (z && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            AMapLog.i("ajx.image.compress", "end compress");
        }
    }

    /* access modifiers changed from: private */
    public static Bitmap compressBySampleSize(String str, int i) {
        while (i > 0) {
            try {
                Options options = new Options();
                options.inJustDecodeBounds = false;
                options.inSampleSize = i;
                return BitmapFactory.decodeFile(str, options);
            } catch (OutOfMemoryError unused) {
                i = i <= 0 ? 2 : i * 2;
            }
        }
        return BitmapFactory.decodeFile(str);
    }
}
