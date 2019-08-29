package defpackage;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;

@TargetApi(5)
/* renamed from: eqv reason: default package */
/* compiled from: EclairPhtoGestureDetector */
public class eqv extends equ {
    private int f = -1;
    private int g = 0;

    public eqv(Context context) {
        super(context);
    }

    /* access modifiers changed from: 0000 */
    public final float a(MotionEvent motionEvent) {
        try {
            return motionEvent.getX(this.g);
        } catch (Exception unused) {
            return motionEvent.getX();
        }
    }

    /* access modifiers changed from: 0000 */
    public final float b(MotionEvent motionEvent) {
        try {
            return motionEvent.getY(this.g);
        } catch (Exception unused) {
            return motionEvent.getY();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x004b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean c(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getAction()
            r0 = r0 & 255(0xff, float:3.57E-43)
            r1 = 3
            r2 = -1
            r3 = 0
            if (r0 == r1) goto L_0x0045
            r1 = 6
            if (r0 == r1) goto L_0x0019
            switch(r0) {
                case 0: goto L_0x0012;
                case 1: goto L_0x0045;
                default: goto L_0x0011;
            }
        L_0x0011:
            goto L_0x0047
        L_0x0012:
            int r0 = r6.getPointerId(r3)
            r5.f = r0
            goto L_0x0047
        L_0x0019:
            int r0 = r6.getAction()
            int r1 = android.os.Build.VERSION.SDK_INT
            r1 = 65280(0xff00, float:9.1477E-41)
            r0 = r0 & r1
            int r0 = r0 >> 8
            int r1 = r6.getPointerId(r0)
            int r4 = r5.f
            if (r1 != r4) goto L_0x0047
            if (r0 != 0) goto L_0x0031
            r0 = 1
            goto L_0x0032
        L_0x0031:
            r0 = 0
        L_0x0032:
            int r1 = r6.getPointerId(r0)
            r5.f = r1
            float r1 = r6.getX(r0)
            r5.b = r1
            float r0 = r6.getY(r0)
            r5.c = r0
            goto L_0x0047
        L_0x0045:
            r5.f = r2
        L_0x0047:
            int r0 = r5.f
            if (r0 == r2) goto L_0x004d
            int r3 = r5.f
        L_0x004d:
            int r0 = r6.findPointerIndex(r3)
            r5.g = r0
            boolean r6 = super.c(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eqv.c(android.view.MotionEvent):boolean");
    }
}
