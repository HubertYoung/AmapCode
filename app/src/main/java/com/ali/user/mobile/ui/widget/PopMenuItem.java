package com.ali.user.mobile.ui.widget;

import android.graphics.drawable.Drawable;

public class PopMenuItem {
    private CharSequence a;
    private int b = 0;
    private Drawable c;
    private int d;

    public PopMenuItem(CharSequence charSequence) {
        this.a = charSequence;
    }

    public final CharSequence a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public final Drawable c() {
        return this.c;
    }

    public final int d() {
        return this.d;
    }
}
