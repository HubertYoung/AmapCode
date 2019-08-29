package defpackage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.amap.bundle.drive.common.dialog.nightmode.widget.NightModeTextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: ng reason: default package */
/* compiled from: NightModeTextViewAttrsWrapper */
public final class ng extends my<NightModeTextView> {
    public ng(Context context, AttributeSet attributeSet, int i, NightModeTextView nightModeTextView) {
        super(context, attributeSet, i, nightModeTextView);
    }

    /* access modifiers changed from: protected */
    public final void a(Context context, AttributeSet attributeSet, int i) {
        super.a(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.NightMode, i, 0);
        this.b = obtainStyledAttributes.getResourceId(R.styleable.NightMode_dayModeSrc, -1);
        this.c = obtainStyledAttributes.getResourceId(R.styleable.NightMode_nightModeSrc, -1);
        obtainStyledAttributes.recycle();
    }

    public final void b(int i, int i2) {
        if (i != this.b || i2 != this.c) {
            this.b = i;
            this.c = i2;
            a(this.h);
        }
    }

    public final void a(boolean z) {
        super.a(z);
        if (this.b <= 0 || z) {
            if (this.c > 0 && z) {
                ((NightModeTextView) this.a).setTextColor(AMapAppGlobal.getApplication().getResources().getColorStateList(this.c));
            }
            return;
        }
        ((NightModeTextView) this.a).setTextColor(AMapAppGlobal.getApplication().getResources().getColorStateList(this.b));
    }
}
