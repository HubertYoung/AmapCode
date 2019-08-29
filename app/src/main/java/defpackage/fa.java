package defpackage;

import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorInt;

/* renamed from: fa reason: default package */
/* compiled from: SimpleColorFilter */
public final class fa extends PorterDuffColorFilter {
    public fa(@ColorInt int i) {
        super(i, Mode.SRC_ATOP);
    }
}
