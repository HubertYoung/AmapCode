package com.alipay.mobile.antui.theme;

import android.text.TextUtils;
import com.alipay.mobile.antui.theme.Impl.AUDefaultTheme;
import com.alipay.mobile.antui.theme.Impl.AUSecondTheme;
import java.util.HashMap;
import java.util.Map;

public class AUThemeManager {
    public static String PREFERENCE_NAME = "antui_skin_pref";
    public static final String THEMEKEY_DEFAULT = "defalue";
    public static final String THEMEKEY_SECOND = "second";
    private static String mCurrentThemeKey = THEMEKEY_DEFAULT;
    private static final Map<String, AUAbsTheme> themeMap = new HashMap();

    static {
        themeMap.put(THEMEKEY_DEFAULT, AUDefaultTheme.getInstence());
        themeMap.put(THEMEKEY_SECOND, AUSecondTheme.getInstence());
    }

    public static AUAbsTheme getTheme(String themeKey) {
        if (TextUtils.isEmpty(themeKey) || themeMap.containsKey(themeKey)) {
            themeKey = mCurrentThemeKey;
        }
        return themeMap.get(themeKey);
    }

    public static AUAbsTheme getCurrentTheme() {
        return getTheme(mCurrentThemeKey);
    }

    public static void putTheme(String themeKey, AUAbsTheme theme) {
        themeMap.put(themeKey, theme);
    }

    public static AUAbsTheme getDefaultTheme() {
        return AUDefaultTheme.getInstence();
    }

    public static String getCurrentThemeKey() {
        return mCurrentThemeKey;
    }

    public static void setCurrentThemeKey(String currentThemeKey) {
        mCurrentThemeKey = currentThemeKey;
    }
}
