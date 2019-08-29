package defpackage;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.support.annotation.Nullable;
import java.io.Closeable;

/* renamed from: ij reason: default package */
/* compiled from: Utils */
public final class ij {
    private static final PathMeasure a = new PathMeasure();
    private static final Path b = new Path();
    private static final Path c = new Path();
    private static final float[] d = new float[4];
    private static final float e = ((float) Math.sqrt(2.0d));

    public static int a(float f, float f2, float f3, float f4) {
        int i = f != 0.0f ? (int) (f * 527.0f) : 17;
        if (f2 != 0.0f) {
            i = (int) (((float) (i * 31)) * f2);
        }
        if (f3 != 0.0f) {
            i = (int) (((float) (i * 31)) * f3);
        }
        return f4 != 0.0f ? (int) (((float) (i * 31)) * f4) : i;
    }

    public static Path a(PointF pointF, PointF pointF2, PointF pointF3, PointF pointF4) {
        Path path = new Path();
        path.moveTo(pointF.x, pointF.y);
        if (pointF3 == null || pointF4 == null || (pointF3.length() == 0.0f && pointF4.length() == 0.0f)) {
            path.lineTo(pointF2.x, pointF2.y);
        } else {
            Path path2 = path;
            path2.cubicTo(pointF3.x + pointF.x, pointF.y + pointF3.y, pointF2.x + pointF4.x, pointF2.y + pointF4.y, pointF2.x, pointF2.y);
        }
        return path;
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception unused) {
            }
        }
    }

    public static float a(Matrix matrix) {
        d[0] = 0.0f;
        d[1] = 0.0f;
        d[2] = e;
        d[3] = e;
        matrix.mapPoints(d);
        return ((float) Math.hypot((double) (d[2] - d[0]), (double) (d[3] - d[1]))) / 2.0f;
    }

    public static void a(Path path, float f, float f2, float f3) {
        eu.a("applyTrimPathIfNeeded");
        a.setPath(path, false);
        float length = a.getLength();
        if (f == 1.0f && f2 == 0.0f) {
            eu.b("applyTrimPathIfNeeded");
        } else if (length < 1.0f || ((double) Math.abs((f2 - f) - 1.0f)) < 0.01d) {
            eu.b("applyTrimPathIfNeeded");
        } else {
            float f4 = f * length;
            float f5 = f2 * length;
            float f6 = f3 * length;
            float min = Math.min(f4, f5) + f6;
            float max = Math.max(f4, f5) + f6;
            if (min >= length && max >= length) {
                min = (float) ii.a(min, length);
                max = (float) ii.a(max, length);
            }
            if (min < 0.0f) {
                min = (float) ii.a(min, length);
            }
            if (max < 0.0f) {
                max = (float) ii.a(max, length);
            }
            int i = (min > max ? 1 : (min == max ? 0 : -1));
            if (i == 0) {
                path.reset();
                eu.b("applyTrimPathIfNeeded");
                return;
            }
            if (i >= 0) {
                min -= length;
            }
            b.reset();
            a.getSegment(min, max, b, true);
            if (max > length) {
                c.reset();
                a.getSegment(0.0f, max % length, c, true);
                b.addPath(c);
            } else if (min < 0.0f) {
                c.reset();
                a.getSegment(min + length, length, c, true);
                b.addPath(c);
            }
            path.set(b);
            eu.b("applyTrimPathIfNeeded");
        }
    }

    public static float a(Context context) {
        if (VERSION.SDK_INT >= 17) {
            return Global.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f);
        }
        return System.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f);
    }

    public static void a(Path path, @Nullable ft ftVar) {
        if (ftVar != null) {
            a(path, ((Float) ftVar.b.a()).floatValue() / 100.0f, ((Float) ftVar.c.a()).floatValue() / 100.0f, ((Float) ftVar.d.a()).floatValue() / 360.0f);
        }
    }

    public static boolean a(ev evVar, int i) {
        if (evVar.l < 4) {
            return false;
        }
        if (evVar.l > 4) {
            return true;
        }
        if (evVar.m < i) {
            return false;
        }
        return evVar.m > i || evVar.n >= 0;
    }
}
