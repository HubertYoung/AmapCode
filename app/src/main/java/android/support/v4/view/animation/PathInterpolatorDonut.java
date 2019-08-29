package android.support.v4.view.animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Interpolator;

class PathInterpolatorDonut implements Interpolator {
    private final float[] a;
    private final float[] b;

    public PathInterpolatorDonut(Path path) {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        int i = ((int) (length / 0.002f)) + 1;
        this.a = new float[i];
        this.b = new float[i];
        float[] fArr = new float[2];
        for (int i2 = 0; i2 < i; i2++) {
            pathMeasure.getPosTan((((float) i2) * length) / ((float) (i - 1)), fArr, null);
            this.a[i2] = fArr[0];
            this.b[i2] = fArr[1];
        }
    }

    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int i = 0;
        int length = this.a.length - 1;
        while (length - i > 1) {
            int i2 = (i + length) / 2;
            if (f < this.a[i2]) {
                length = i2;
            } else {
                i = i2;
            }
        }
        float f2 = this.a[length] - this.a[i];
        if (f2 == 0.0f) {
            return this.b[i];
        }
        float f3 = this.b[i];
        return f3 + (((f - this.a[i]) / f2) * (this.b[length] - f3));
    }

    /* JADX WARNING: Illegal instructions before constructor call commented (this can break semantics) */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PathInterpolatorDonut(float r3, float r4) {
        /*
            r2 = this;
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            r1 = 0
            r0.moveTo(r1, r1)
            r1 = 1065353216(0x3f800000, float:1.0)
            r0.quadTo(r3, r4, r1, r1)
            r2.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.animation.PathInterpolatorDonut.<init>(float, float):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call commented (this can break semantics) */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PathInterpolatorDonut(float r9, float r10, float r11, float r12) {
        /*
            r8 = this;
            android.graphics.Path r7 = new android.graphics.Path
            r7.<init>()
            r0 = 0
            r7.moveTo(r0, r0)
            r5 = 1065353216(0x3f800000, float:1.0)
            r6 = 1065353216(0x3f800000, float:1.0)
            r0 = r7
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r0.cubicTo(r1, r2, r3, r4, r5, r6)
            r8.<init>(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.animation.PathInterpolatorDonut.<init>(float, float, float, float):void");
    }
}
