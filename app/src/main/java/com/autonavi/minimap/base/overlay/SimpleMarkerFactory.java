package com.autonavi.minimap.base.overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.MeasureSpec;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.R;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import defpackage.amh;
import defpackage.brx;
import defpackage.bty;

final class SimpleMarkerFactory {
    public static float POI_HL_X_RATIO = 0.5f;
    public static float POI_HL_Y_RATIO = 0.92f;
    private static HashSet<Integer> lineTextureIdCache = new HashSet<>();
    private static ConcurrentHashMap<Integer, Marker> sMarkerCache = new ConcurrentHashMap<>();

    SimpleMarkerFactory() {
    }

    protected static void clearMarkerCache() {
        sMarkerCache.clear();
    }

    protected static void clearLineTextureCache() {
        lineTextureIdCache.clear();
    }

    protected static Marker createMarker(int i, int i2, float f, float f2, bty bty) {
        return createMarker(i, i2, f, f2, false, bty);
    }

    protected static Marker createMarker(int i, int i2, float f, float f2, boolean z, bty bty) {
        Marker marker = sMarkerCache.get(Integer.valueOf(i));
        if (marker != null && bty.j() == brx.d) {
            return marker;
        }
        if (i == R.drawable.b_poi_hl && i2 == 5) {
            i2 = 9;
            f = POI_HL_X_RATIO;
            f2 = POI_HL_Y_RATIO;
        }
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

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x004b */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0057 A[SYNTHETIC, Splitter:B:25:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0062 A[SYNTHETIC, Splitter:B:31:0x0062] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static com.autonavi.minimap.base.overlay.Marker createMarkerWithRawResource(int r4, int r5, float r6, float r7, defpackage.bty r8) {
        /*
            java.util.concurrent.ConcurrentHashMap<java.lang.Integer, com.autonavi.minimap.base.overlay.Marker> r0 = sMarkerCache
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            java.lang.Object r0 = r0.get(r1)
            com.autonavi.minimap.base.overlay.Marker r0 = (com.autonavi.minimap.base.overlay.Marker) r0
            if (r0 == 0) goto L_0x0017
            int r1 = r8.j()
            int r2 = defpackage.brx.d
            if (r1 != r2) goto L_0x0017
            return r0
        L_0x0017:
            amh r0 = new amh
            r0.<init>()
            r0.a = r4
            r0.d = r5
            r5 = 0
            android.content.res.Resources r1 = r8.a()     // Catch:{ Exception -> 0x004b }
            java.io.InputStream r4 = r1.openRawResource(r4)     // Catch:{ Exception -> 0x004b }
            android.graphics.Bitmap r5 = android.graphics.BitmapFactory.decodeStream(r4)     // Catch:{ Exception -> 0x0047, all -> 0x0042 }
            r0.b = r5     // Catch:{ Exception -> 0x0047, all -> 0x0042 }
            if (r4 == 0) goto L_0x0039
            r4.close()     // Catch:{ IOException -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r4 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r4)
        L_0x0039:
            r0.e = r6
            r0.f = r7
            com.autonavi.minimap.base.overlay.Marker r4 = createMarker(r8, r0)
            return r4
        L_0x0042:
            r5 = move-exception
            r3 = r5
            r5 = r4
            r4 = r3
            goto L_0x0060
        L_0x0047:
            r5 = r4
            goto L_0x004b
        L_0x0049:
            r4 = move-exception
            goto L_0x0060
        L_0x004b:
            com.autonavi.minimap.base.overlay.Marker r4 = new com.autonavi.minimap.base.overlay.Marker     // Catch:{ all -> 0x0049 }
            r6 = -999(0xfffffffffffffc19, float:NaN)
            int r7 = r0.d     // Catch:{ all -> 0x0049 }
            r8 = 0
            r4.<init>(r6, r7, r8, r8)     // Catch:{ all -> 0x0049 }
            if (r5 == 0) goto L_0x005f
            r5.close()     // Catch:{ IOException -> 0x005b }
            goto L_0x005f
        L_0x005b:
            r5 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r5)
        L_0x005f:
            return r4
        L_0x0060:
            if (r5 == 0) goto L_0x006a
            r5.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x006a
        L_0x0066:
            r5 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r5)
        L_0x006a:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.base.overlay.SimpleMarkerFactory.createMarkerWithRawResource(int, int, float, float, bty):com.autonavi.minimap.base.overlay.Marker");
    }

    protected static Marker createMarker(int i, Bitmap bitmap, int i2, float f, float f2, boolean z, bty bty) {
        if (z) {
            Marker marker = sMarkerCache.get(Integer.valueOf(i));
            if (marker != null && bty.j() == brx.d) {
                return marker;
            }
        }
        amh amh = new amh();
        amh.a = i;
        amh.d = i2;
        amh.b = bitmap;
        amh.e = f;
        amh.f = f2;
        return createMarker(bty, amh);
    }

    protected static Marker createMarker(int i, View view, int i2, float f, float f2, boolean z, bty bty) {
        View view2 = view;
        view2.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        view2.layout(0, 0, view2.getMeasuredWidth(), view2.getMeasuredHeight());
        view2.buildDrawingCache();
        return createMarker(i, view2.getDrawingCache(), i2, f, f2, z, bty);
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
            sMarkerCache.put(Integer.valueOf(amh.a), marker);
            OverlayDebugUtil.writeOverlayTextureId(amh.a);
            return marker;
        }
    }

    protected static void createLineTexure(bty bty, int i, int i2, Bitmap bitmap) {
        if (bitmap != null && bty.j() != brx.d) {
            amh amh = new amh();
            amh.a = i2;
            if (!(i2 == -1 || i2 == -999)) {
                amh.b = bitmap;
            }
            amh.d = 4;
            setProperTextureProperty(i, amh);
            addMarkerToEngine(bty, amh);
            lineTextureIdCache.add(Integer.valueOf(i2));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0045 A[SYNTHETIC, Splitter:B:25:0x0045] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0050 A[SYNTHETIC, Splitter:B:32:0x0050] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void createLineTexure(defpackage.bty r3, int r4, int r5) {
        /*
            java.util.HashSet<java.lang.Integer> r0 = lineTextureIdCache
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x0015
            int r0 = r3.j()
            int r1 = defpackage.brx.d
            if (r0 != r1) goto L_0x0015
            return
        L_0x0015:
            amh r0 = new amh
            r0.<init>()
            r0.a = r5
            r1 = -1
            if (r5 == r1) goto L_0x005a
            r1 = -999(0xfffffffffffffc19, float:NaN)
            if (r5 == r1) goto L_0x005a
            r1 = 0
            android.content.res.Resources r2 = r3.a()     // Catch:{ Exception -> 0x004e, all -> 0x0042 }
            java.io.InputStream r2 = r2.openRawResource(r5)     // Catch:{ Exception -> 0x004e, all -> 0x0042 }
            android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeStream(r2)     // Catch:{ Exception -> 0x0040, all -> 0x003d }
            r0.b = r1     // Catch:{ Exception -> 0x0040, all -> 0x003d }
            if (r2 == 0) goto L_0x005a
            r2.close()     // Catch:{ IOException -> 0x0038 }
            goto L_0x005a
        L_0x0038:
            r1 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r1)
            goto L_0x005a
        L_0x003d:
            r3 = move-exception
            r1 = r2
            goto L_0x0043
        L_0x0040:
            r1 = r2
            goto L_0x004e
        L_0x0042:
            r3 = move-exception
        L_0x0043:
            if (r1 == 0) goto L_0x004d
            r1.close()     // Catch:{ IOException -> 0x0049 }
            goto L_0x004d
        L_0x0049:
            r4 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r4)
        L_0x004d:
            throw r3
        L_0x004e:
            if (r1 == 0) goto L_0x0059
            r1.close()     // Catch:{ IOException -> 0x0054 }
            goto L_0x0059
        L_0x0054:
            r3 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r3)
            return
        L_0x0059:
            return
        L_0x005a:
            r1 = 4
            r0.d = r1
            setProperTextureProperty(r4, r0)
            addMarkerToEngine(r3, r0)
            java.util.HashSet<java.lang.Integer> r3 = lineTextureIdCache
            java.lang.Integer r4 = java.lang.Integer.valueOf(r5)
            r3.add(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.base.overlay.SimpleMarkerFactory.createLineTexure(bty, int, int):void");
    }

    protected static void createRasterTexure(bty bty, int i, Bitmap bitmap) {
        if (bitmap != null && bty.j() != brx.d) {
            amh amh = new amh();
            amh.a = i;
            if (i > 0 && i != -999) {
                amh.b = bitmap;
                amh.d = 4;
                addMarkerToEngine(bty, amh);
                lineTextureIdCache.add(Integer.valueOf(i));
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0058 A[SYNTHETIC, Splitter:B:29:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0063 A[SYNTHETIC, Splitter:B:36:0x0063] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void createRasterTexure(defpackage.bty r3, int r4) {
        /*
            java.util.HashSet<java.lang.Integer> r0 = lineTextureIdCache
            if (r0 == 0) goto L_0x0019
            java.util.HashSet<java.lang.Integer> r0 = lineTextureIdCache
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x0019
            int r0 = r3.j()
            int r1 = defpackage.brx.d
            if (r0 != r1) goto L_0x0019
            return
        L_0x0019:
            amh r0 = new amh
            r0.<init>()
            r0.a = r4
            if (r4 <= 0) goto L_0x006d
            r1 = -999(0xfffffffffffffc19, float:NaN)
            if (r4 == r1) goto L_0x006d
            r1 = 0
            android.content.res.Resources r2 = r3.a()     // Catch:{ Exception -> 0x0061, all -> 0x0054 }
            java.io.InputStream r2 = r2.openRawResource(r4)     // Catch:{ Exception -> 0x0061, all -> 0x0054 }
            android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeStream(r2)     // Catch:{ Exception -> 0x0052, all -> 0x0050 }
            r0.b = r1     // Catch:{ Exception -> 0x0052, all -> 0x0050 }
            r1 = 4
            r0.d = r1     // Catch:{ Exception -> 0x0052, all -> 0x0050 }
            addMarkerToEngine(r3, r0)     // Catch:{ Exception -> 0x0052, all -> 0x0050 }
            java.util.HashSet<java.lang.Integer> r3 = lineTextureIdCache     // Catch:{ Exception -> 0x0052, all -> 0x0050 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x0052, all -> 0x0050 }
            r3.add(r4)     // Catch:{ Exception -> 0x0052, all -> 0x0050 }
            if (r2 == 0) goto L_0x004f
            r2.close()     // Catch:{ IOException -> 0x004a }
            goto L_0x004f
        L_0x004a:
            r3 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r3)
            return
        L_0x004f:
            return
        L_0x0050:
            r3 = move-exception
            goto L_0x0056
        L_0x0052:
            r1 = r2
            goto L_0x0061
        L_0x0054:
            r3 = move-exception
            r2 = r1
        L_0x0056:
            if (r2 == 0) goto L_0x0060
            r2.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0060
        L_0x005c:
            r4 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r4)
        L_0x0060:
            throw r3
        L_0x0061:
            if (r1 == 0) goto L_0x006c
            r1.close()     // Catch:{ IOException -> 0x0067 }
            goto L_0x006c
        L_0x0067:
            r3 = move-exception
            com.amap.bundle.blutils.log.DebugLog.error(r3)
            return
        L_0x006c:
            return
        L_0x006d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.base.overlay.SimpleMarkerFactory.createRasterTexure(bty, int):void");
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

    public static void removeMarker(int i, bty bty, int i2, c cVar) {
        if (sMarkerCache.remove(Integer.valueOf(i)) != null) {
            bty.c().a(i2, i);
        }
    }
}
