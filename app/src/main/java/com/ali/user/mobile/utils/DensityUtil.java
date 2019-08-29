package com.ali.user.mobile.utils;

import android.content.Context;

public class DensityUtil {
    private static float a;
    private static float b;

    public static float a(float f, int i) {
        float f2 = 1.0f;
        switch (i) {
            case 0:
                f2 = 0.875f;
                break;
            case 2:
                f2 = 1.125f;
                break;
            case 3:
                f2 = 1.25f;
                break;
            case 4:
                f2 = 1.375f;
                break;
        }
        return f2 * f;
    }

    public static boolean a(float f, float f2) {
        return ((int) f) == ((int) f2);
    }

    public static int a(Context context, float f) {
        try {
            if (a == 0.0f) {
                a = context.getResources().getDisplayMetrics().density;
            }
        } catch (Throwable unused) {
        }
        return (int) ((f * a) + 0.5f);
    }

    public static float b(Context context, float f) {
        try {
            if (b == 0.0f) {
                b = context.getResources().getDisplayMetrics().scaledDensity;
            }
        } catch (Throwable unused) {
        }
        return f / b;
    }
}
