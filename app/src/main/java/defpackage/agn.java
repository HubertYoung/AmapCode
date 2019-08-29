package defpackage;

import android.content.Context;
import android.util.DisplayMetrics;

/* renamed from: agn reason: default package */
/* compiled from: DimenUtil */
public final class agn {
    public static int a(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int b(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int a(DisplayMetrics displayMetrics, float f) {
        return (int) ((f * displayMetrics.density) + 0.5f);
    }
}
