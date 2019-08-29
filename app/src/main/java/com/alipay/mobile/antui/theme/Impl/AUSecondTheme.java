package com.alipay.mobile.antui.theme.Impl;

import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;

public class AUSecondTheme extends AUAbsTheme {
    private static final AUSecondTheme instence = new AUSecondTheme();

    public static final AUSecondTheme getInstence() {
        return instence;
    }

    private AUSecondTheme() {
        initTheme();
    }

    /* access modifiers changed from: protected */
    public void initTheme() {
        put(AUThemeKey.MAIN_BTN_BACKGROUND, Integer.valueOf(R.drawable.au_button_bg_for_main2));
        put(AUThemeKey.FLOATMENU_ICON_COLOR, Integer.valueOf(R.color.AU_COLOR_APP_GREEN));
        put(AUThemeKey.FLOATMENU_TEXTCOLOR, Integer.valueOf(R.color.AU_COLOR_APP_GREEN));
        put(AUThemeKey.FLOATMENU_LINE_COLOR, Integer.valueOf(R.drawable.popmenu_list_devider2));
        put(AUThemeKey.FLOATMENU_SINGLELINE_BG, Integer.valueOf(R.drawable.pop_list_corner_round2));
        put(AUThemeKey.FLOATMENU_MULTILINEFIRST_BG, Integer.valueOf(R.drawable.pop_list_corner_round_top2));
        put(AUThemeKey.FLOATMENU_MULTILINELAST_BG, Integer.valueOf(R.drawable.pop_list_corner_round_bottom2));
        put(AUThemeKey.FLOATMENU_MULTILINE_DEFAULT_BG, Integer.valueOf(R.drawable.pop_list_corner_shape2));
        put(AUThemeKey.TITLEBAR_BACKGROUND_COLOR, Integer.valueOf(R.drawable.drawable_titlebar_bg2));
        put(AUThemeKey.TITLEBAR_TITLE_TEXTCOLOR, Integer.valueOf(R.color.AU_COLOR_UNIVERSAL_BG));
        put(AUThemeKey.TITLEBAR_ICON_COLOR, Integer.valueOf(R.color.AU_COLOR_UNIVERSAL_BG));
    }
}
