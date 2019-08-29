package defpackage;

import com.autonavi.jni.ae.guide.model.MotionVector3D;

/* renamed from: epr reason: default package */
/* compiled from: MotionVector */
public final class epr extends MotionVector3D {
    public static boolean a(epr epr) {
        return (Double.compare(epr.z, 0.0d) | (Double.compare(epr.x, 0.0d) | Double.compare(epr.y, 0.0d))) == 0;
    }

    public final float[] a() {
        return new float[]{(float) this.x, (float) this.y, (float) this.z};
    }

    public static void a(epr epr, double d, double d2, double d3) {
        epr.x = d;
        epr.y = d2;
        epr.z = d3;
    }
}
