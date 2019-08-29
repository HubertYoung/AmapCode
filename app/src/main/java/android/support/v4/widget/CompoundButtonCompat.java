package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;

public final class CompoundButtonCompat {
    private static final CompoundButtonCompatImpl IMPL;

    static class Api23CompoundButtonImpl extends LollipopCompoundButtonImpl {
        Api23CompoundButtonImpl() {
        }

        public final Drawable a(CompoundButton compoundButton) {
            return CompoundButtonCompatApi23.a(compoundButton);
        }
    }

    static class BaseCompoundButtonCompat implements CompoundButtonCompatImpl {
        BaseCompoundButtonCompat() {
        }

        public void a(CompoundButton compoundButton, ColorStateList colorStateList) {
            CompoundButtonCompatDonut.a(compoundButton, colorStateList);
        }

        public ColorStateList b(CompoundButton compoundButton) {
            return CompoundButtonCompatDonut.a(compoundButton);
        }

        public void a(CompoundButton compoundButton, Mode mode) {
            CompoundButtonCompatDonut.a(compoundButton, mode);
        }

        public Mode c(CompoundButton compoundButton) {
            return CompoundButtonCompatDonut.b(compoundButton);
        }

        public Drawable a(CompoundButton compoundButton) {
            return CompoundButtonCompatDonut.c(compoundButton);
        }
    }

    interface CompoundButtonCompatImpl {
        Drawable a(CompoundButton compoundButton);

        void a(CompoundButton compoundButton, ColorStateList colorStateList);

        void a(CompoundButton compoundButton, Mode mode);

        ColorStateList b(CompoundButton compoundButton);

        Mode c(CompoundButton compoundButton);
    }

    static class LollipopCompoundButtonImpl extends BaseCompoundButtonCompat {
        LollipopCompoundButtonImpl() {
        }

        public final void a(CompoundButton compoundButton, ColorStateList colorStateList) {
            CompoundButtonCompatLollipop.a(compoundButton, colorStateList);
        }

        public final ColorStateList b(CompoundButton compoundButton) {
            return CompoundButtonCompatLollipop.a(compoundButton);
        }

        public final void a(CompoundButton compoundButton, Mode mode) {
            CompoundButtonCompatLollipop.a(compoundButton, mode);
        }

        public final Mode c(CompoundButton compoundButton) {
            return CompoundButtonCompatLollipop.b(compoundButton);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            IMPL = new Api23CompoundButtonImpl();
        } else if (i >= 21) {
            IMPL = new LollipopCompoundButtonImpl();
        } else {
            IMPL = new BaseCompoundButtonCompat();
        }
    }

    private CompoundButtonCompat() {
    }

    public static void setButtonTintList(@NonNull CompoundButton compoundButton, @Nullable ColorStateList colorStateList) {
        IMPL.a(compoundButton, colorStateList);
    }

    @Nullable
    public static ColorStateList getButtonTintList(@NonNull CompoundButton compoundButton) {
        return IMPL.b(compoundButton);
    }

    public static void setButtonTintMode(@NonNull CompoundButton compoundButton, @Nullable Mode mode) {
        IMPL.a(compoundButton, mode);
    }

    @Nullable
    public static Mode getButtonTintMode(@NonNull CompoundButton compoundButton) {
        return IMPL.c(compoundButton);
    }

    @Nullable
    public static Drawable getButtonDrawable(@NonNull CompoundButton compoundButton) {
        return IMPL.a(compoundButton);
    }
}
