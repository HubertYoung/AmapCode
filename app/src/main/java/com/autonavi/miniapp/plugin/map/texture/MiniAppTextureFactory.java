package com.autonavi.miniapp.plugin.map.texture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.base.overlay.Marker;

public class MiniAppTextureFactory {
    public static Marker createMarker(int i, int i2, float f, float f2, bty bty) {
        return createMarker(i, i2, f, f2, false, bty);
    }

    public static Marker createMarker(int i, int i2, float f, float f2, boolean z, bty bty) {
        amh amh = new amh();
        amh.a = i;
        amh.d = i2;
        try {
            amh.b = BitmapFactory.decodeResource(bty.a(), i);
        } catch (Throwable th) {
            amh.b = null;
            th.printStackTrace();
        }
        amh.e = f;
        amh.f = f2;
        amh.g = z;
        return createMarker(bty, amh);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:15|14|17|18|(2:20|21)|24) */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0032, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x004f, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0050, code lost:
        com.amap.bundle.blutils.log.DebugLog.error(r4);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0034 */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0040 A[SYNTHETIC, Splitter:B:20:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004b A[SYNTHETIC, Splitter:B:26:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.minimap.base.overlay.Marker createMarkerWithRawResource(int r3, int r4, float r5, float r6, defpackage.bty r7) {
        /*
            amh r0 = new amh
            r0.<init>()
            r0.a = r3
            r0.d = r4
            r4 = 0
            android.content.res.Resources r1 = r7.a()     // Catch:{ Exception -> 0x0034 }
            java.io.InputStream r3 = r1.openRawResource(r3)     // Catch:{ Exception -> 0x0034 }
            android.graphics.Bitmap r4 = android.graphics.BitmapFactory.decodeStream(r3)     // Catch:{ Exception -> 0x0030, all -> 0x002b }
            r0.b = r4     // Catch:{ Exception -> 0x0030, all -> 0x002b }
            if (r3 == 0) goto L_0x0022
            r3.close()     // Catch:{ IOException -> 0x001e }
            goto L_0x0022
        L_0x001e:
            r3 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r3)
        L_0x0022:
            r0.e = r5
            r0.f = r6
            com.autonavi.minimap.base.overlay.Marker r3 = createMarker(r7, r0)
            return r3
        L_0x002b:
            r4 = move-exception
            r2 = r4
            r4 = r3
            r3 = r2
            goto L_0x0049
        L_0x0030:
            r4 = r3
            goto L_0x0034
        L_0x0032:
            r3 = move-exception
            goto L_0x0049
        L_0x0034:
            com.autonavi.minimap.base.overlay.Marker r3 = new com.autonavi.minimap.base.overlay.Marker     // Catch:{ all -> 0x0032 }
            r5 = -999(0xfffffffffffffc19, float:NaN)
            int r6 = r0.d     // Catch:{ all -> 0x0032 }
            r7 = 0
            r3.<init>(r5, r6, r7, r7)     // Catch:{ all -> 0x0032 }
            if (r4 == 0) goto L_0x0048
            r4.close()     // Catch:{ IOException -> 0x0044 }
            goto L_0x0048
        L_0x0044:
            r4 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r4)
        L_0x0048:
            return r3
        L_0x0049:
            if (r4 == 0) goto L_0x0053
            r4.close()     // Catch:{ IOException -> 0x004f }
            goto L_0x0053
        L_0x004f:
            r4 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r4)
        L_0x0053:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.map.texture.MiniAppTextureFactory.createMarkerWithRawResource(int, int, float, float, bty):com.autonavi.minimap.base.overlay.Marker");
    }

    public static Marker createMarker(int i, Bitmap bitmap, int i2, float f, float f2, bty bty) {
        amh amh = new amh();
        amh.a = i;
        amh.d = i2;
        amh.b = bitmap;
        amh.e = f;
        amh.f = f2;
        return createMarker(bty, amh);
    }

    private static Marker createMarker(bty bty, amh amh) {
        if (amh == null) {
            throw new NullPointerException();
        } else if (amh.b != null && amh.b.isRecycled()) {
            throw new IllegalStateException("Can't create Marker with recycled bitmap.");
        } else if (amh.b == null) {
            AMapLog.d("SimpleMarkerFactory", "createMarker failed due to null bitmap!");
            return new Marker(-999, amh.d, 0, 0);
        } else {
            Marker marker = new Marker(amh.a, amh.d, amh.b.getWidth(), amh.b.getHeight());
            addMarkerToEngine(bty, amh);
            return marker;
        }
    }

    public static void createLineTexture(bty bty, int i, int i2, Bitmap bitmap) {
        if (bitmap != null && bty.j() != brx.d) {
            amh amh = new amh();
            amh.a = i2;
            if (!(i2 == -1 || i2 == -999)) {
                amh.b = bitmap;
            }
            amh.d = 4;
            setProperTextureProperty(i, amh);
            addMarkerToEngine(bty, amh);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x002e A[SYNTHETIC, Splitter:B:18:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x003a A[SYNTHETIC, Splitter:B:27:0x003a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void createLineTexture(defpackage.bty r3, int r4, int r5) {
        /*
            amh r0 = new amh
            r0.<init>()
            r0.a = r5
            r1 = -1
            if (r5 == r1) goto L_0x0044
            r1 = -999(0xfffffffffffffc19, float:NaN)
            if (r5 == r1) goto L_0x0044
            r1 = 0
            android.content.res.Resources r2 = r3.a()     // Catch:{ Exception -> 0x0037, all -> 0x002a }
            java.io.InputStream r5 = r2.openRawResource(r5)     // Catch:{ Exception -> 0x0037, all -> 0x002a }
            android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeStream(r5)     // Catch:{ Exception -> 0x0038, all -> 0x0028 }
            r0.b = r1     // Catch:{ Exception -> 0x0038, all -> 0x0028 }
            if (r5 == 0) goto L_0x0044
            r5.close()     // Catch:{ IOException -> 0x0023 }
            goto L_0x0044
        L_0x0023:
            r5 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r5)
            goto L_0x0044
        L_0x0028:
            r3 = move-exception
            goto L_0x002c
        L_0x002a:
            r3 = move-exception
            r5 = r1
        L_0x002c:
            if (r5 == 0) goto L_0x0036
            r5.close()     // Catch:{ IOException -> 0x0032 }
            goto L_0x0036
        L_0x0032:
            r4 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r4)
        L_0x0036:
            throw r3
        L_0x0037:
            r5 = r1
        L_0x0038:
            if (r5 == 0) goto L_0x0043
            r5.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x0043
        L_0x003e:
            r3 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r3)
            return
        L_0x0043:
            return
        L_0x0044:
            r5 = 4
            r0.d = r5
            setProperTextureProperty(r4, r0)
            addMarkerToEngine(r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.map.texture.MiniAppTextureFactory.createLineTexture(bty, int, int):void");
    }

    public static void createRasterTexture(bty bty, int i, Bitmap bitmap) {
        if (bitmap != null && bty.j() != brx.d) {
            amh amh = new amh();
            amh.a = i;
            if (i > 0 && i != -999) {
                amh.b = bitmap;
                amh.d = 4;
                addMarkerToEngine(bty, amh);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0034 A[SYNTHETIC, Splitter:B:20:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0040 A[SYNTHETIC, Splitter:B:29:0x0040] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void createRasterTexture(defpackage.bty r3, int r4) {
        /*
            amh r0 = new amh
            r0.<init>()
            r0.a = r4
            if (r4 <= 0) goto L_0x004a
            r1 = -999(0xfffffffffffffc19, float:NaN)
            if (r4 == r1) goto L_0x004a
            r1 = 0
            android.content.res.Resources r2 = r3.a()     // Catch:{ Exception -> 0x003d, all -> 0x0030 }
            java.io.InputStream r4 = r2.openRawResource(r4)     // Catch:{ Exception -> 0x003d, all -> 0x0030 }
            android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeStream(r4)     // Catch:{ Exception -> 0x003e, all -> 0x002e }
            r0.b = r1     // Catch:{ Exception -> 0x003e, all -> 0x002e }
            r1 = 4
            r0.d = r1     // Catch:{ Exception -> 0x003e, all -> 0x002e }
            addMarkerToEngine(r3, r0)     // Catch:{ Exception -> 0x003e, all -> 0x002e }
            if (r4 == 0) goto L_0x002d
            r4.close()     // Catch:{ IOException -> 0x0028 }
            goto L_0x002d
        L_0x0028:
            r3 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r3)
            return
        L_0x002d:
            return
        L_0x002e:
            r3 = move-exception
            goto L_0x0032
        L_0x0030:
            r3 = move-exception
            r4 = r1
        L_0x0032:
            if (r4 == 0) goto L_0x003c
            r4.close()     // Catch:{ IOException -> 0x0038 }
            goto L_0x003c
        L_0x0038:
            r4 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r4)
        L_0x003c:
            throw r3
        L_0x003d:
            r4 = r1
        L_0x003e:
            if (r4 == 0) goto L_0x0049
            r4.close()     // Catch:{ IOException -> 0x0044 }
            goto L_0x0049
        L_0x0044:
            r3 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r3)
            return
        L_0x0049:
            return
        L_0x004a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.map.texture.MiniAppTextureFactory.createRasterTexture(bty, int):void");
    }

    private static void setProperTextureProperty(int i, amh amh) {
        if (amh != null) {
            switch (i) {
                case 1:
                    amh.g = true;
                    amh.h = false;
                    return;
                case 2:
                    amh.g = false;
                    amh.h = false;
                    return;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    amh.g = false;
                    amh.h = true;
                    break;
            }
        }
    }

    private static void addMarkerToEngine(bty bty, amh amh) {
        if (amh != null) {
            bty.a(amh);
        }
    }
}
