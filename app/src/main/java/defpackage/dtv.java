package defpackage;

import android.os.Handler;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import java.util.Map;

/* renamed from: dtv reason: default package */
/* compiled from: DecodeHandler */
final class dtv extends Handler {
    private final dtz a;
    private final MultiFormatReader b = new MultiFormatReader();
    private boolean c = true;

    dtv(dtz dtz, Map<DecodeHintType, Object> map) {
        this.b.setHints(map);
        this.a = dtz;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x007a A[Catch:{ ReaderException -> 0x0072, all -> 0x006b, Throwable -> 0x0086 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleMessage(android.os.Message r12) {
        /*
            r11 = this;
            boolean r0 = r11.c
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            int r0 = r12.what
            int r1 = com.autonavi.minimap.R.id.decode
            r2 = 0
            if (r0 != r1) goto L_0x0095
            java.lang.Object r0 = r12.obj
            byte[] r0 = (byte[]) r0
            int r1 = r12.arg1
            int r12 = r12.arg2
            dtz r3 = r11.a
            if (r3 == 0) goto L_0x0094
            dtz r3 = r11.a
            android.os.Handler r3 = r3.c()
            r4 = 1
            dtz r5 = r11.a     // Catch:{ Throwable -> 0x0086 }
            dtt r5 = r5.e()     // Catch:{ Throwable -> 0x0086 }
            if (r5 == 0) goto L_0x0086
            if (r0 == 0) goto L_0x0086
            if (r1 <= 0) goto L_0x0086
            if (r12 <= 0) goto L_0x0086
            r5 = 0
            int r6 = r0.length     // Catch:{ Throwable -> 0x0086 }
            byte[] r6 = new byte[r6]     // Catch:{ Throwable -> 0x0086 }
            r7 = 0
        L_0x0032:
            if (r7 >= r12) goto L_0x0049
            r8 = 0
        L_0x0035:
            if (r8 >= r1) goto L_0x0046
            int r9 = r8 * r12
            int r9 = r9 + r12
            int r9 = r9 - r7
            int r9 = r9 - r4
            int r10 = r7 * r1
            int r10 = r10 + r8
            byte r10 = r0[r10]     // Catch:{ Throwable -> 0x0086 }
            r6[r9] = r10     // Catch:{ Throwable -> 0x0086 }
            int r8 = r8 + 1
            goto L_0x0035
        L_0x0046:
            int r7 = r7 + 1
            goto L_0x0032
        L_0x0049:
            dtz r0 = r11.a     // Catch:{ Throwable -> 0x0086 }
            dtt r0 = r0.e()     // Catch:{ Throwable -> 0x0086 }
            com.google.zxing.PlanarYUVLuminanceSource r12 = r0.a(r6, r12, r1)     // Catch:{ Throwable -> 0x0086 }
            if (r12 == 0) goto L_0x0077
            com.google.zxing.BinaryBitmap r0 = new com.google.zxing.BinaryBitmap     // Catch:{ Throwable -> 0x0086 }
            com.google.zxing.common.HybridBinarizer r1 = new com.google.zxing.common.HybridBinarizer     // Catch:{ Throwable -> 0x0086 }
            r1.<init>(r12)     // Catch:{ Throwable -> 0x0086 }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0086 }
            com.google.zxing.MultiFormatReader r12 = r11.b     // Catch:{ ReaderException -> 0x0072, all -> 0x006b }
            com.google.zxing.Result r12 = r12.decodeWithState(r0)     // Catch:{ ReaderException -> 0x0072, all -> 0x006b }
            com.google.zxing.MultiFormatReader r0 = r11.b     // Catch:{ Throwable -> 0x0086 }
            r0.reset()     // Catch:{ Throwable -> 0x0086 }
            goto L_0x0078
        L_0x006b:
            r12 = move-exception
            com.google.zxing.MultiFormatReader r0 = r11.b     // Catch:{ Throwable -> 0x0086 }
            r0.reset()     // Catch:{ Throwable -> 0x0086 }
            throw r12     // Catch:{ Throwable -> 0x0086 }
        L_0x0072:
            com.google.zxing.MultiFormatReader r12 = r11.b     // Catch:{ Throwable -> 0x0086 }
            r12.reset()     // Catch:{ Throwable -> 0x0086 }
        L_0x0077:
            r12 = r5
        L_0x0078:
            if (r12 == 0) goto L_0x0086
            if (r3 == 0) goto L_0x0087
            int r0 = com.autonavi.minimap.R.id.decode_succeeded     // Catch:{ Throwable -> 0x0086 }
            android.os.Message r12 = android.os.Message.obtain(r3, r0, r12)     // Catch:{ Throwable -> 0x0086 }
            r12.sendToTarget()     // Catch:{ Throwable -> 0x0086 }
            goto L_0x0087
        L_0x0086:
            r2 = 1
        L_0x0087:
            if (r2 == 0) goto L_0x0094
            if (r3 == 0) goto L_0x0094
            int r12 = com.autonavi.minimap.R.id.decode_failed
            android.os.Message r12 = android.os.Message.obtain(r3, r12)
            r12.sendToTarget()
        L_0x0094:
            return
        L_0x0095:
            int r12 = r12.what
            int r0 = com.autonavi.minimap.R.id.quit
            if (r12 != r0) goto L_0x00a4
            r11.c = r2
            android.os.Looper r12 = android.os.Looper.myLooper()
            r12.quit()
        L_0x00a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dtv.handleMessage(android.os.Message):void");
    }
}
