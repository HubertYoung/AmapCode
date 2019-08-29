package com.autonavi.minimap.ajx3.widget.animator.interpolator;

import android.graphics.PointF;
import android.view.animation.Interpolator;

public class CubicBezierInterpolator implements Interpolator {
    protected PointF a;
    private PointF b;
    protected PointF c;
    private PointF end;
    private PointF start;

    private CubicBezierInterpolator(PointF pointF, PointF pointF2) throws IllegalArgumentException {
        this.a = new PointF();
        this.b = new PointF();
        this.c = new PointF();
        if (pointF.x < 0.0f || pointF.x > 1.0f) {
            throw new IllegalArgumentException("startX value must be in the range [0, 1]");
        } else if (pointF2.x < 0.0f || pointF2.x > 1.0f) {
            throw new IllegalArgumentException("endX value must be in the range [0, 1]");
        } else {
            this.start = pointF;
            this.end = pointF2;
        }
    }

    public CubicBezierInterpolator(float f, float f2, float f3, float f4) {
        this(new PointF(f, f2), new PointF(f3, f4));
    }

    public float getInterpolation(float f) {
        return getBezierCoordinateY(getXForTime(f));
    }

    private float getBezierCoordinateY(float f) {
        this.c.y = this.start.y * 3.0f;
        this.b.y = ((this.end.y - this.start.y) * 3.0f) - this.c.y;
        this.a.y = (1.0f - this.c.y) - this.b.y;
        return f * (this.c.y + ((this.b.y + (this.a.y * f)) * f));
    }

    private float getXForTime(float f) {
        float f2 = f;
        for (int i = 1; i < 14; i++) {
            float bezierCoordinateX = getBezierCoordinateX(f2) - f;
            if (((double) Math.abs(bezierCoordinateX)) < 0.001d) {
                break;
            }
            f2 -= bezierCoordinateX / getXDerivate(f2);
        }
        return f2;
    }

    private float getXDerivate(float f) {
        return this.c.x + (f * ((this.b.x * 2.0f) + (this.a.x * 3.0f * f)));
    }

    private float getBezierCoordinateX(float f) {
        this.c.x = this.start.x * 3.0f;
        this.b.x = ((this.end.x - this.start.x) * 3.0f) - this.c.x;
        this.a.x = (1.0f - this.c.x) - this.b.x;
        return f * (this.c.x + ((this.b.x + (this.a.x * f)) * f));
    }
}
