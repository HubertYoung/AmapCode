package android.support.v4.view.animation;

import android.graphics.Path;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;

public class PathInterpolatorCompat {
    private PathInterpolatorCompat() {
    }

    public static Interpolator create(Path path) {
        if (VERSION.SDK_INT >= 21) {
            return PathInterpolatorCompatApi21.a(path);
        }
        return PathInterpolatorCompatBase.a(path);
    }

    public static Interpolator create(float f, float f2) {
        if (VERSION.SDK_INT >= 21) {
            return PathInterpolatorCompatApi21.a(f, f2);
        }
        return PathInterpolatorCompatBase.a(f, f2);
    }

    public static Interpolator create(float f, float f2, float f3, float f4) {
        if (VERSION.SDK_INT >= 21) {
            return PathInterpolatorCompatApi21.a(f, f2, f3, f4);
        }
        return PathInterpolatorCompatBase.a(f, f2, f3, f4);
    }
}
