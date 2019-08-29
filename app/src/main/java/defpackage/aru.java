package defpackage;

import android.content.Context;
import android.graphics.Point;

/* renamed from: aru reason: default package */
/* compiled from: MapHomeUtil */
public final class aru {
    public static Point a(Context context, int i) {
        if (context == null) {
            return null;
        }
        return new Point(ags.a(context).width() / 2, ((ags.a(context).height() - bet.a(context, i)) / 2) + (euk.a(context) / 2));
    }
}
