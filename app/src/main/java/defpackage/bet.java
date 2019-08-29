package defpackage;

import android.content.Context;

/* renamed from: bet reason: default package */
/* compiled from: DimensUtil */
public final class bet {
    public static int a(Context context, int i) {
        if (context == null) {
            return 0;
        }
        return (int) ((((float) i) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
