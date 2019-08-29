package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import java.io.File;

/* renamed from: byi reason: default package */
/* compiled from: AlbumUtil */
public final class byi {
    public static final String a;
    public static final String b;

    static {
        StringBuilder sb = new StringBuilder("autonavi");
        sb.append(File.separator);
        sb.append("saved");
        sb.append(File.separator);
        a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb2.append(File.separator);
        sb2.append(a);
        b = sb2.toString();
    }

    public static Bitmap a(@Nullable Drawable drawable) {
        if (drawable == null || drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x009e, code lost:
        if (r7.isRecycled() == false) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00bb, code lost:
        if (r7.isRecycled() == false) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00d1, code lost:
        if (r7.isRecycled() == false) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00d3, code lost:
        r7.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        android.provider.MediaStore.Images.Media.insertImage(r6.getContentResolver(), r3.getAbsolutePath(), r1, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00e2, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00e3, code lost:
        r7.printStackTrace();
     */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00af A[SYNTHETIC, Splitter:B:44:0x00af] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00c5 A[SYNTHETIC, Splitter:B:55:0x00c5] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00fc A[SYNTHETIC, Splitter:B:70:0x00fc] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x010a  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:52:0x00c0=Splitter:B:52:0x00c0, B:41:0x00aa=Splitter:B:41:0x00aa} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r6, android.graphics.Bitmap r7) {
        /*
            java.lang.String r0 = android.os.Environment.getExternalStorageState()
            java.lang.String r1 = "mounted"
            boolean r0 = r0.equals(r1)
            r1 = 0
            if (r0 == 0) goto L_0x001f
            java.lang.String r0 = a
            java.io.File r2 = new java.io.File
            r2.<init>(r0)
            boolean r0 = r2.exists()
            if (r0 != 0) goto L_0x001d
            r2.mkdir()
        L_0x001d:
            r0 = 1
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            r2 = 0
            if (r0 == 0) goto L_0x010e
            if (r7 != 0) goto L_0x0027
            goto L_0x010e
        L_0x0027:
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r0 = r0.getAbsolutePath()
            long[] r0 = defpackage.ahd.e(r0)
            r3 = r0[r1]
            r0 = 1024(0x400, double:5.06E-321)
            long r3 = r3 / r0
            long r3 = r3 / r0
            r0 = 0
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x0040
            return r2
        L_0x0040:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = b
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x0066
            boolean r1 = r0.mkdirs()
            if (r1 != 0) goto L_0x0066
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x0065
            boolean r1 = r0.canRead()
            if (r1 == 0) goto L_0x0065
            boolean r1 = r0.canWrite()
            if (r1 != 0) goto L_0x0066
        L_0x0065:
            return r2
        L_0x0066:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            long r3 = java.lang.System.currentTimeMillis()
            r1.append(r3)
            java.lang.String r3 = ".jpg"
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.io.File r3 = new java.io.File
            r3.<init>(r0, r1)
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00be, IOException -> 0x00a8, all -> 0x00a5 }
            r0.<init>(r3)     // Catch:{ FileNotFoundException -> 0x00be, IOException -> 0x00a8, all -> 0x00a5 }
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ FileNotFoundException -> 0x00a3, IOException -> 0x00a1 }
            r5 = 100
            r7.compress(r4, r5, r0)     // Catch:{ FileNotFoundException -> 0x00a3, IOException -> 0x00a1 }
            r0.flush()     // Catch:{ FileNotFoundException -> 0x00a3, IOException -> 0x00a1 }
            r0.close()     // Catch:{ FileNotFoundException -> 0x00a3, IOException -> 0x00a1 }
            r0.close()     // Catch:{ IOException -> 0x0096 }
            goto L_0x009a
        L_0x0096:
            r0 = move-exception
            r0.printStackTrace()
        L_0x009a:
            boolean r0 = r7.isRecycled()
            if (r0 != 0) goto L_0x00d6
            goto L_0x00d3
        L_0x00a1:
            r4 = move-exception
            goto L_0x00aa
        L_0x00a3:
            r4 = move-exception
            goto L_0x00c0
        L_0x00a5:
            r6 = move-exception
            r0 = r2
            goto L_0x00fa
        L_0x00a8:
            r4 = move-exception
            r0 = r2
        L_0x00aa:
            r4.printStackTrace()     // Catch:{ all -> 0x00f9 }
            if (r0 == 0) goto L_0x00b7
            r0.close()     // Catch:{ IOException -> 0x00b3 }
            goto L_0x00b7
        L_0x00b3:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00b7:
            boolean r0 = r7.isRecycled()
            if (r0 != 0) goto L_0x00d6
            goto L_0x00d3
        L_0x00be:
            r4 = move-exception
            r0 = r2
        L_0x00c0:
            r4.printStackTrace()     // Catch:{ all -> 0x00f9 }
            if (r0 == 0) goto L_0x00cd
            r0.close()     // Catch:{ IOException -> 0x00c9 }
            goto L_0x00cd
        L_0x00c9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00cd:
            boolean r0 = r7.isRecycled()
            if (r0 != 0) goto L_0x00d6
        L_0x00d3:
            r7.recycle()
        L_0x00d6:
            android.content.ContentResolver r7 = r6.getContentResolver()     // Catch:{ FileNotFoundException -> 0x00e2 }
            java.lang.String r0 = r3.getAbsolutePath()     // Catch:{ FileNotFoundException -> 0x00e2 }
            android.provider.MediaStore.Images.Media.insertImage(r7, r0, r1, r2)     // Catch:{ FileNotFoundException -> 0x00e2 }
            goto L_0x00e6
        L_0x00e2:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00e6:
            android.content.Intent r7 = new android.content.Intent
            java.lang.String r0 = "android.intent.action.MEDIA_SCANNER_SCAN_FILE"
            android.net.Uri r1 = android.net.Uri.fromFile(r3)
            r7.<init>(r0, r1)
            r6.sendBroadcast(r7)
            java.lang.String r6 = r3.getAbsolutePath()
            return r6
        L_0x00f9:
            r6 = move-exception
        L_0x00fa:
            if (r0 == 0) goto L_0x0104
            r0.close()     // Catch:{ IOException -> 0x0100 }
            goto L_0x0104
        L_0x0100:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0104:
            boolean r0 = r7.isRecycled()
            if (r0 != 0) goto L_0x010d
            r7.recycle()
        L_0x010d:
            throw r6
        L_0x010e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.byi.a(android.content.Context, android.graphics.Bitmap):java.lang.String");
    }
}
