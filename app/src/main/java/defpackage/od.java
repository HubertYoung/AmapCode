package defpackage;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

/* renamed from: od reason: default package */
/* compiled from: NaviLightnessManager */
public final class od {
    private static od a = new od();
    private int b;
    private int c;

    private od() {
    }

    public static od a() {
        return a;
    }

    public final void a(Context context, boolean z, int i) {
        this.c = rf.b(context);
        if (i == -1) {
            a(context, z);
        }
    }

    public final void a(Context context, boolean z) {
        boolean a2 = rf.a(context);
        int b2 = rf.b(context);
        if (!a2 && z) {
            int i = 102;
            String str = Build.MODEL;
            if (!TextUtils.isEmpty(str) && ("MX4".equals(str) || str.startsWith("M46"))) {
                i = 204;
            }
            if (b2 <= i) {
                i = b2;
            }
            this.b = i;
            rf.a(context, this.b);
        }
    }

    public final void a(Context context) {
        boolean a2 = rf.a(context);
        if (this.b == rf.b(context) && !a2) {
            rf.a(context, this.c);
        }
    }
}
