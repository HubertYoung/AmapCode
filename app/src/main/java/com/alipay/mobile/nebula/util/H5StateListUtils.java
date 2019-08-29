package com.alipay.mobile.nebula.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class H5StateListUtils {
    public static StateListDrawable getStateListDrawable(Drawable normal, Drawable pressed, Drawable disabled) {
        if (normal == null) {
            return null;
        }
        if (pressed == null) {
            pressed = normal;
        }
        if (disabled == null) {
            disabled = normal;
        }
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{16842919}, pressed);
        sd.addState(new int[]{-16842910}, disabled);
        sd.addState(new int[0], normal);
        return sd;
    }

    public static ColorStateList getColorStateList(int normalColor, int pressedColor, int disableColor) {
        if (normalColor == 0) {
            return null;
        }
        if (pressedColor == 0) {
            pressedColor = normalColor;
        }
        if (disableColor == 0) {
            disableColor = normalColor;
        }
        return new ColorStateList(new int[][]{new int[]{16842919}, new int[]{-16842910}, new int[0]}, new int[]{pressedColor, disableColor, normalColor});
    }

    public static ColorStateList getStateColor(int normalColor) {
        int pressed = normalColor & 1728053247;
        return getColorStateList(normalColor, pressed, pressed);
    }
}
