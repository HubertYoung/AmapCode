package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class TextViewCompat {
    static final TextViewCompatImpl IMPL;

    static class BaseTextViewCompatImpl implements TextViewCompatImpl {
        BaseTextViewCompatImpl() {
        }

        public void a(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        }

        public void b(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }

        public void a(@NonNull TextView textView, int i, int i2, int i3, int i4) {
            textView.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
        }

        public int a(TextView textView) {
            return TextViewCompatDonut.a(textView);
        }

        public int b(TextView textView) {
            return TextViewCompatDonut.b(textView);
        }
    }

    static class JbMr1TextViewCompatImpl extends JbTextViewCompatImpl {
        JbMr1TextViewCompatImpl() {
        }

        public void a(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            TextViewCompatJbMr1.a(textView, drawable, drawable2, drawable3, drawable4);
        }

        public void b(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            TextViewCompatJbMr1.b(textView, drawable, drawable2, drawable3, drawable4);
        }

        public void a(@NonNull TextView textView, int i, int i2, int i3, int i4) {
            TextViewCompatJbMr1.a(textView, i, i2, i3, i4);
        }
    }

    static class JbMr2TextViewCompatImpl extends JbMr1TextViewCompatImpl {
        JbMr2TextViewCompatImpl() {
        }

        public final void a(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            TextViewCompatJbMr2.a(textView, drawable, drawable2, drawable3, drawable4);
        }

        public final void b(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            TextViewCompatJbMr2.b(textView, drawable, drawable2, drawable3, drawable4);
        }

        public final void a(@NonNull TextView textView, int i, int i2, int i3, int i4) {
            TextViewCompatJbMr2.a(textView, i, i2, i3, i4);
        }
    }

    static class JbTextViewCompatImpl extends BaseTextViewCompatImpl {
        JbTextViewCompatImpl() {
        }

        public final int a(TextView textView) {
            return TextViewCompatJb.a(textView);
        }

        public final int b(TextView textView) {
            return TextViewCompatJb.b(textView);
        }
    }

    interface TextViewCompatImpl {
        int a(TextView textView);

        void a(@NonNull TextView textView, int i, int i2, int i3, int i4);

        void a(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4);

        int b(TextView textView);

        void b(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4);
    }

    private TextViewCompat() {
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 18) {
            IMPL = new JbMr2TextViewCompatImpl();
        } else if (i >= 17) {
            IMPL = new JbMr1TextViewCompatImpl();
        } else if (i >= 16) {
            IMPL = new JbTextViewCompatImpl();
        } else {
            IMPL = new BaseTextViewCompatImpl();
        }
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        IMPL.a(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        IMPL.b(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, int i, int i2, int i3, int i4) {
        IMPL.a(textView, i, i2, i3, i4);
    }

    public static int getMaxLines(@NonNull TextView textView) {
        return IMPL.a(textView);
    }

    public static int getMinLines(@NonNull TextView textView) {
        return IMPL.b(textView);
    }
}
