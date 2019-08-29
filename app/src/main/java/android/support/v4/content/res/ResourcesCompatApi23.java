package android.support.v4.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;

class ResourcesCompatApi23 {
    ResourcesCompatApi23() {
    }

    public static int a(Resources resources, int i, Theme theme) throws NotFoundException {
        return resources.getColor(i, theme);
    }

    public static ColorStateList b(Resources resources, int i, Theme theme) throws NotFoundException {
        return resources.getColorStateList(i, theme);
    }
}
