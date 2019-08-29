package defpackage;

import android.text.SpannableStringBuilder;

/* renamed from: ahy reason: default package */
/* compiled from: RichText */
public final class ahy extends SpannableStringBuilder {
    private int a = 33;

    public static ahy a(CharSequence charSequence) {
        return new ahy(charSequence);
    }

    public static ahy a(CharSequence charSequence, Object obj) {
        return new ahy(charSequence, obj);
    }

    private ahy(CharSequence charSequence) {
        super(charSequence);
    }

    private ahy(CharSequence charSequence, Object obj) {
        super(charSequence);
        a(obj, 0, charSequence.length());
    }

    private void a(Object obj, int i, int i2) {
        setSpan(obj, i, i2, this.a);
    }

    public final ahy b(CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }

    public final ahy b(CharSequence charSequence, Object obj) {
        super.append(charSequence);
        a(obj, length() - charSequence.length(), length());
        return this;
    }
}
