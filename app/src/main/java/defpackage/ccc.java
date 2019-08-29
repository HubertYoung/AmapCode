package defpackage;

import android.app.Activity;

/* renamed from: ccc reason: default package */
/* compiled from: ScreenUtil */
public final class ccc {
    public static int a(Activity activity) {
        if (activity == null) {
            return 0;
        }
        return ags.a(activity).height() - ags.d(activity.getBaseContext());
    }
}
