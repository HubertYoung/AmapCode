package com.alipay.mobile.antui.theme.Impl;

import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;

public class AUDefaultTheme extends AUAbsTheme {
    private static final AUDefaultTheme instence = new AUDefaultTheme();

    public static final AUDefaultTheme getInstence() {
        return instence;
    }

    private AUDefaultTheme() {
        initTheme();
    }

    /* access modifiers changed from: protected */
    public void initTheme() {
        put(AUThemeKey.MAIN_BTN_HEIGHT, Integer.valueOf(R.dimen.AU_HEIGHT10));
        put(AUThemeKey.MAIN_BTN_BACKGROUND, Integer.valueOf(R.drawable.au_button_bg_for_main));
        put(AUThemeKey.MAIN_BTN_TEXTCOLOR, Integer.valueOf(R.color.au_button_textcolor_white));
        put(AUThemeKey.MAIN_BTN_TEXTSIZE, Integer.valueOf(R.dimen.AU_TEXTSIZE5));
        put(AUThemeKey.SUB_BTN_HEIGHT, Integer.valueOf(R.dimen.AU_HEIGHT10));
        put(AUThemeKey.SUB_BTN_BACKGROUND, Integer.valueOf(R.drawable.au_button_bg_for_sub));
        put(AUThemeKey.SUB_BTN_TEXTCOLOR, Integer.valueOf(R.color.au_button_textcolor_black));
        put(AUThemeKey.SUB_BTN_TEXTSIZE, Integer.valueOf(R.dimen.AU_TEXTSIZE5));
        put(AUThemeKey.WARN_BTN_HEIGHT, Integer.valueOf(R.dimen.AU_HEIGHT10));
        put(AUThemeKey.WARN_BTN_BACKGROUND, Integer.valueOf(R.drawable.au_button_bg_for_warn));
        put(AUThemeKey.WARN_BTN_TEXTCOLOR, Integer.valueOf(R.color.au_button_textcolor_white));
        put(AUThemeKey.WARN_BTN_TEXTSIZE, Integer.valueOf(R.dimen.AU_TEXTSIZE5));
        put(AUThemeKey.ASS_TRANS_BTN_HEIGHT, Integer.valueOf(R.dimen.AU_SPACE7));
        put(AUThemeKey.ASS_TRANS_BTN_BACKGROUND, Integer.valueOf(R.drawable.au_button_bg_for_ass_transparent));
        put(AUThemeKey.ASS_TRANS_BTN_TEXTCOLOR, Integer.valueOf(R.color.au_button_textcolor_blue));
        put(AUThemeKey.ASS_TRANS_BTN_TEXTSIZE, Integer.valueOf(R.dimen.AU_TEXTSIZE3));
        put(AUThemeKey.NO_RECT_BTN_HEIGHT, Integer.valueOf(R.dimen.AU_SPACE12));
        put(AUThemeKey.NO_RECT_BTN_BACKGROUND, Integer.valueOf(17170445));
        put(AUThemeKey.NO_RECT_BTN_TEXTCOLOR, Integer.valueOf(R.color.AU_COLOR9));
        put(AUThemeKey.NO_RECT_BTN_TEXTSIZE, Integer.valueOf(R.dimen.AU_TEXTSIZE5));
    }
}
