package com.autonavi.minimap.ajx3.htmcompat;

import android.support.annotation.ColorInt;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import com.alipay.mobile.antui.basic.AUCardInteractView;
import java.util.HashMap;
import java.util.Locale;

class ColorUtils {
    private static final HashMap<String, Integer> S_COLOR_NAME_MAP;

    ColorUtils() {
    }

    @ColorInt
    static int getHtmlColor(String str) {
        Integer num = S_COLOR_NAME_MAP.get(str.toLowerCase(Locale.ROOT));
        if (num != null) {
            return num.intValue();
        }
        try {
            return convertValueToInt(str, -1);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    private static int convertValueToInt(CharSequence charSequence, int i) {
        int i2;
        int i3;
        int i4;
        if (charSequence == null) {
            return i;
        }
        String charSequence2 = charSequence.toString();
        int length = charSequence2.length();
        int i5 = 10;
        if ('-' == charSequence2.charAt(0)) {
            i3 = 1;
            i2 = -1;
        } else {
            i3 = 0;
            i2 = 1;
        }
        if ('0' != charSequence2.charAt(i3)) {
            if ('#' == charSequence2.charAt(i3)) {
                i4 = i3 + 1;
            }
            return Integer.parseInt(charSequence2.substring(i3), i5) * i2;
        } else if (i3 == length - 1) {
            return 0;
        } else {
            int i6 = i3 + 1;
            char charAt = charSequence2.charAt(i6);
            if ('x' == charAt || 'X' == charAt) {
                i4 = i3 + 2;
            } else {
                i5 = 8;
                i3 = i6;
                return Integer.parseInt(charSequence2.substring(i3), i5) * i2;
            }
        }
        i5 = 16;
        return Integer.parseInt(charSequence2.substring(i3), i5) * i2;
    }

    static {
        HashMap<String, Integer> hashMap = new HashMap<>();
        S_COLOR_NAME_MAP = hashMap;
        hashMap.put("black", Integer.valueOf(-16777216));
        S_COLOR_NAME_MAP.put("darkgray", Integer.valueOf(-12303292));
        S_COLOR_NAME_MAP.put("gray", Integer.valueOf(-7829368));
        S_COLOR_NAME_MAP.put("lightgray", Integer.valueOf(-3355444));
        S_COLOR_NAME_MAP.put("white", Integer.valueOf(-1));
        S_COLOR_NAME_MAP.put("red", Integer.valueOf(SupportMenu.CATEGORY_MASK));
        S_COLOR_NAME_MAP.put("green", Integer.valueOf(-16711936));
        S_COLOR_NAME_MAP.put("blue", Integer.valueOf(-16776961));
        S_COLOR_NAME_MAP.put("yellow", Integer.valueOf(InputDeviceCompat.SOURCE_ANY));
        S_COLOR_NAME_MAP.put("cyan", Integer.valueOf(-16711681));
        S_COLOR_NAME_MAP.put("magenta", Integer.valueOf(-65281));
        S_COLOR_NAME_MAP.put("aqua", Integer.valueOf(-16711681));
        S_COLOR_NAME_MAP.put("fuchsia", Integer.valueOf(-65281));
        S_COLOR_NAME_MAP.put("darkgrey", Integer.valueOf(-12303292));
        S_COLOR_NAME_MAP.put(AUCardInteractView.GREY_STYLE, Integer.valueOf(-7829368));
        S_COLOR_NAME_MAP.put("lightgrey", Integer.valueOf(-3355444));
        S_COLOR_NAME_MAP.put("lime", Integer.valueOf(-16711936));
        S_COLOR_NAME_MAP.put("maroon", Integer.valueOf(-8388608));
        S_COLOR_NAME_MAP.put("navy", Integer.valueOf(-16777088));
        S_COLOR_NAME_MAP.put("olive", Integer.valueOf(-8355840));
        S_COLOR_NAME_MAP.put("purple", Integer.valueOf(-8388480));
        S_COLOR_NAME_MAP.put("silver", Integer.valueOf(-4144960));
        S_COLOR_NAME_MAP.put("teal", Integer.valueOf(-16744320));
    }
}
