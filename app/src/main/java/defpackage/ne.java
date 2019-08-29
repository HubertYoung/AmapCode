package defpackage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.amap.bundle.drive.common.dialog.nightmode.widget.NightModeLinearLayout;
import com.autonavi.minimap.R;

/* renamed from: ne reason: default package */
/* compiled from: NightModeLinearLayoutAttrsWrapper */
public final class ne extends my<NightModeLinearLayout> {
    public ne(Context context, AttributeSet attributeSet, int i, NightModeLinearLayout nightModeLinearLayout) {
        super(context, attributeSet, i, nightModeLinearLayout);
    }

    /* access modifiers changed from: protected */
    public final void a(Context context, AttributeSet attributeSet, int i) {
        super.a(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.NightMode, i, 0);
        this.b = obtainStyledAttributes.getResourceId(R.styleable.NightMode_dayModeSrc, -1);
        this.c = obtainStyledAttributes.getResourceId(R.styleable.NightMode_nightModeSrc, -1);
        obtainStyledAttributes.recycle();
    }

    public final void a(boolean z) {
        super.a(z);
        if (this.b <= 0 || z) {
            if (this.c > 0 && z) {
                ((NightModeLinearLayout) this.a).setBackgroundResource(this.c);
            }
            return;
        }
        ((NightModeLinearLayout) this.a).setBackgroundResource(this.b);
    }
}
