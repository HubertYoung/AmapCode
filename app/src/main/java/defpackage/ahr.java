package defpackage;

import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import com.autonavi.amap.app.AMapAppGlobal;

/* renamed from: ahr reason: default package */
/* compiled from: OrientationUtil */
public final class ahr {
    private static Boolean a;
    private static Boolean b;

    public static boolean a() {
        if (a != null) {
            return a.booleanValue();
        }
        Boolean valueOf = Boolean.valueOf(!agq.a(AMapAppGlobal.getApplication(), 3));
        a = valueOf;
        return valueOf.booleanValue();
    }

    public static boolean b() {
        if (b != null) {
            return b.booleanValue();
        }
        if (a()) {
            boolean a2 = agq.a(AMapAppGlobal.getApplication(), 2);
            boolean z = true;
            boolean a3 = agq.a(AMapAppGlobal.getApplication(), 1);
            if (!a2 || !a3) {
                z = false;
            }
            b = Boolean.valueOf(z);
        } else {
            b = Boolean.valueOf(agq.a(AMapAppGlobal.getApplication(), 3));
        }
        return b.booleanValue();
    }

    public static float a(@NonNull float[] fArr, @NonNull float[] fArr2) {
        float[] fArr3 = new float[3];
        float[] fArr4 = new float[9];
        SensorManager.getRotationMatrix(fArr4, null, fArr, fArr2);
        SensorManager.getOrientation(fArr4, fArr3);
        float degrees = (float) Math.toDegrees((double) fArr3[0]);
        return degrees < 0.0f ? degrees + 360.0f : degrees;
    }
}
