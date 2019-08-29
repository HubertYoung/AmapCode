package com.alipay.zoloz.hardware.camera.a;

import android.hardware.Camera.Size;
import java.util.Comparator;

/* compiled from: AndroidCameraUtil */
public final class a {
    private static a b = null;
    public C0110a a = new C0110a();

    /* renamed from: com.alipay.zoloz.hardware.camera.a.a$a reason: collision with other inner class name */
    /* compiled from: AndroidCameraUtil */
    public class C0110a implements Comparator<Size> {
        public C0110a() {
        }

        public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            Size size = (Size) obj;
            Size size2 = (Size) obj2;
            if (size.width == size2.width) {
                return 0;
            }
            if (size.width > size2.width) {
                return 1;
            }
            return -1;
        }
    }

    private a() {
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            if (b == null) {
                aVar = new a();
                b = aVar;
            } else {
                aVar = b;
            }
        }
        return aVar;
    }

    public static boolean a(Size size, float f) {
        if (((double) Math.abs((((float) size.width) / ((float) size.height)) - f)) <= 0.03d) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0012 A[SYNTHETIC, Splitter:B:6:0x0012] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int c() {
        /*
            r1 = 0
            r2 = -1
            java.lang.String r0 = android.os.Build.VERSION.SDK     // Catch:{ Throwable -> 0x0021 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Throwable -> 0x0021 }
            r3 = 8
            if (r0 <= r3) goto L_0x0025
            int r0 = android.hardware.Camera.getNumberOfCameras()     // Catch:{ Throwable -> 0x0021 }
        L_0x0010:
            if (r1 >= r0) goto L_0x002b
            android.hardware.Camera$CameraInfo r3 = new android.hardware.Camera$CameraInfo     // Catch:{ Throwable -> 0x0027 }
            r3.<init>()     // Catch:{ Throwable -> 0x0027 }
            android.hardware.Camera.getCameraInfo(r1, r3)     // Catch:{ Throwable -> 0x0027 }
            int r3 = r3.facing     // Catch:{ Throwable -> 0x0027 }
            r4 = 1
            if (r3 != r4) goto L_0x0028
            r0 = r1
        L_0x0020:
            return r0
        L_0x0021:
            r0 = move-exception
            r0.toString()
        L_0x0025:
            r0 = r1
            goto L_0x0010
        L_0x0027:
            r3 = move-exception
        L_0x0028:
            int r1 = r1 + 1
            goto L_0x0010
        L_0x002b:
            r0 = r2
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.zoloz.hardware.camera.a.a.c():int");
    }

    public static int b() {
        return c();
    }
}
