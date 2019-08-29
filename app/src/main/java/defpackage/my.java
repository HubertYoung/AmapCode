package defpackage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;

/* renamed from: my reason: default package */
/* compiled from: NightModeBaseAttrsWrapper */
public class my<T extends View> {
    protected T a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;
    protected int f;
    protected int g;
    protected boolean h;

    public my(Context context, AttributeSet attributeSet, int i, T t) {
        this.a = t;
        a(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void a(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.NightMode, i, 0);
        this.d = obtainStyledAttributes.getResourceId(R.styleable.NightMode_dayModeBackGround, -1);
        this.e = obtainStyledAttributes.getResourceId(R.styleable.NightMode_nightModeBackGround, -1);
        this.f = obtainStyledAttributes.getResourceId(R.styleable.NightMode_dayModeBackGroundDisable, -1);
        this.g = obtainStyledAttributes.getResourceId(R.styleable.NightMode_nightModeBackGroundDisable, -1);
        obtainStyledAttributes.recycle();
    }

    public void a(boolean z) {
        if (this.d > 0 && !z) {
            this.a.setBackgroundResource(this.d);
        }
        if (this.e > 0 && z) {
            this.a.setBackgroundResource(this.e);
        }
        if (this.f > 0 && !z && !this.a.isEnabled()) {
            this.a.setBackgroundResource(this.f);
        }
        if (this.g > 0 && z && !this.a.isEnabled()) {
            this.a.setBackgroundResource(this.g);
        }
        this.h = z;
    }

    public final void a(int i, int i2) {
        if (i != this.d || i2 != this.e) {
            this.d = i;
            this.e = i2;
            a(this.h);
        }
    }
}
