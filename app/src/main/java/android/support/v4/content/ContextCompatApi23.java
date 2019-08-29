package android.support.v4.content;

import android.content.Context;
import android.content.res.ColorStateList;

class ContextCompatApi23 {
    ContextCompatApi23() {
    }

    public static ColorStateList a(Context context, int i) {
        return context.getColorStateList(i);
    }

    public static int b(Context context, int i) {
        return context.getColor(i);
    }
}
