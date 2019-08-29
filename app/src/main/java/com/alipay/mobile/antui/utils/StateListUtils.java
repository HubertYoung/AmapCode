package com.alipay.mobile.antui.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class StateListUtils {
    public static StateListDrawable getStateListDrawable(Drawable normal, Drawable pressed, Drawable disabled) {
        return getStateListDrawable(normal, pressed, disabled, null);
    }

    public static StateListDrawable getStateListDrawable(Drawable normal, Drawable pressed, Drawable disabled, Drawable selected) {
        if (normal == null) {
            return null;
        }
        if (pressed == null) {
            pressed = normal;
        }
        if (disabled == null) {
            disabled = normal;
        }
        if (selected == null) {
            selected = normal;
        }
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{16842919}, pressed);
        sd.addState(new int[]{-16842910}, disabled);
        sd.addState(new int[]{16842913}, selected);
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
}
