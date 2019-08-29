package defpackage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.amap.bundle.drive.common.dialog.nightmode.widget.NightModeCompassView;
import com.autonavi.minimap.R;

/* renamed from: na reason: default package */
/* compiled from: NightModeCompassViewWrapper */
public final class na extends my<NightModeCompassView> {
    public na(Context context, AttributeSet attributeSet, int i, NightModeCompassView nightModeCompassView) {
        super(context, attributeSet, i, nightModeCompassView);
    }

    /* access modifiers changed from: protected */
    public final void a(Context context, AttributeSet attributeSet, int i) {
        super.a(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.NightMode, i, 0);
        this.b = obtainStyledAttributes.getResourceId(R.styleable.NightMode_dayModeSrc, 0);
        this.c = obtainStyledAttributes.getResourceId(R.styleable.NightMode_nightModeSrc, 0);
        obtainStyledAttributes.recycle();
    }

    public final void a(boolean z) {
        super.a(z);
        if (this.b <= 0 || z) {
            if (this.c > 0 && z) {
                ((NightModeCompassView) this.a).setImageResource(this.c);
            }
            return;
        }
        ((NightModeCompassView) this.a).setImageResource(this.b);
    }

    public final void b(int i, int i2) {
        if (i != this.b || i2 != this.c) {
            this.b = i;
            this.c = i2;
            a(this.h);
        }
    }
}
