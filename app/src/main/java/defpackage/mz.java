package defpackage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.amap.bundle.drive.common.dialog.nightmode.widget.NightModeCheckBox;
import com.autonavi.minimap.R;

/* renamed from: mz reason: default package */
/* compiled from: NightModeCheckBoxAttrsWrapper */
public final class mz extends my<NightModeCheckBox> {
    protected int i;
    protected int j;

    public mz(Context context, AttributeSet attributeSet, int i2, NightModeCheckBox nightModeCheckBox) {
        super(context, attributeSet, i2, nightModeCheckBox);
    }

    /* access modifiers changed from: protected */
    public final void a(Context context, AttributeSet attributeSet, int i2) {
        super.a(context, attributeSet, i2);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.NightMode, i2, 0);
        this.i = obtainStyledAttributes.getResourceId(R.styleable.NightMode_dayModeButtonDrawable, 0);
        this.j = obtainStyledAttributes.getResourceId(R.styleable.NightMode_nightModeButtonDrawable, 0);
        obtainStyledAttributes.recycle();
    }

    public final void a(boolean z) {
        super.a(z);
        if (this.i > 0 && !z) {
            a(this.i);
        }
        if (this.j > 0 && z) {
            a(this.j);
        }
    }

    private void a(int i2) {
        Drawable drawable = ((NightModeCheckBox) this.a).getResources().getDrawable(i2);
        ((NightModeCheckBox) this.a).setMinimumHeight(drawable.getIntrinsicHeight());
        ((NightModeCheckBox) this.a).setMinimumWidth(drawable.getIntrinsicWidth());
        ((NightModeCheckBox) this.a).setButtonDrawable(drawable);
    }
}
