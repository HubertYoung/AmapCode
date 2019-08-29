package defpackage;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import com.autonavi.minimap.R;

/* renamed from: dwn reason: default package */
/* compiled from: ViewUtil */
public final class dwn {
    public static void a(View view, int i) {
        if (view != null) {
            Drawable background = view.getBackground();
            if (background instanceof LayerDrawable) {
                background = ((LayerDrawable) background).findDrawableByLayerId(R.id.foreground);
            }
            if (background instanceof GradientDrawable) {
                ((GradientDrawable) background).setColor(i);
            } else {
                view.setBackgroundColor(i);
            }
        }
    }

    public static void a(View view, int i, int i2) {
        if (view != null) {
            Drawable background = view.getBackground();
            if (background instanceof LayerDrawable) {
                background = ((LayerDrawable) background).findDrawableByLayerId(R.id.foreground);
            }
            if (background instanceof GradientDrawable) {
                ((GradientDrawable) background).setStroke(i, i2);
            }
        }
    }
}
