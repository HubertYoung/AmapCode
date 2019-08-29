package com.ali.user.mobile.common.api;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.ali.user.mobile.ui.widget.WidgetUtil;
import java.util.HashMap;

public class UIConfigManager {
    private static HashMap<String, Object> a;

    public static void a(HashMap<String, Object> hashMap) {
        a = hashMap;
    }

    private static int a(String str) {
        if (a != null) {
            Object obj = a.get(str);
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
        }
        return Integer.MAX_VALUE;
    }

    private static Drawable b(String str) {
        if (a != null) {
            Object obj = a.get(str);
            if (obj instanceof Drawable) {
                return ((Drawable) obj).getConstantState().newDrawable();
            }
        }
        return null;
    }

    public static Adapter a() {
        if (a != null) {
            Object obj = a.get("VIEW_CUSTOMISE_ADAPTER");
            if (obj instanceof Adapter) {
                return (Adapter) obj;
            }
        }
        return null;
    }

    public static Drawable c() {
        return b((String) "PASSWORD_SHOW_ICON");
    }

    public static Drawable d() {
        return b((String) "PASSWORD_HIDE_ICON");
    }

    private static ColorStateList k() {
        if (a != null) {
            Object obj = a.get("COMMON_BUTTON_TEXT_COLOR");
            if (obj instanceof ColorStateList) {
                return (ColorStateList) obj;
            }
        }
        return null;
    }

    public static int e() {
        return a((String) "COMMON_TEXT_LINK_COLOR");
    }

    public static Drawable f() {
        return b((String) "INPUT_BACKGROUND_LINE_FOCUSED");
    }

    public static Drawable g() {
        return b((String) "REGISTER_SUCCESS_ICON");
    }

    public static int h() {
        return a((String) "REGISTER_SUCCESS_TEXT_COLOR");
    }

    public static LoginHistoryCallback i() {
        if (a != null) {
            Object obj = a.get("LOGIN_HISTORY_CYCLE_CALLBACK");
            if (obj instanceof LoginHistoryCallback) {
                return (LoginHistoryCallback) obj;
            }
        }
        return null;
    }

    public static OnViewLoadFinishCallBack j() {
        if (a != null) {
            Object obj = a.get("LOGIN_VIEW_LOAD_FINISHED_CALLBACK");
            if (obj instanceof OnViewLoadFinishCallBack) {
                return (OnViewLoadFinishCallBack) obj;
            }
        }
        return null;
    }

    public static String b() {
        if (a != null) {
            Object obj = a.get("LOGIN_ACCOUNT_HINT_TEXT");
            if (obj instanceof String) {
                return (String) obj;
            }
        }
        return null;
    }

    public static void a(Button button) {
        if (button != null) {
            Drawable b = b((String) "COMMON_BUTTON_BACKGROUD");
            if (b != null) {
                WidgetUtil.a((View) button, b);
            }
            ColorStateList k = k();
            if (k != null) {
                button.setTextColor(k);
            }
        }
    }

    public static void b(Button button) {
        if (button != null) {
            Drawable b = b((String) "SUB_BUTTON_BACKGROUD");
            if (b != null) {
                WidgetUtil.a((View) button, b);
            }
            ColorStateList k = k();
            if (k != null) {
                button.setTextColor(k);
            }
        }
    }

    public static void a(APTitleBar aPTitleBar) {
        if (aPTitleBar != null) {
            int a2 = a((String) "TITLE_BAR_TEXT_COLOR");
            if (a2 != Integer.MAX_VALUE) {
                aPTitleBar.getTitleTextView().setTextColor(a2);
            }
            Drawable b = b((String) "TITLE_BAR_BACKGROUND");
            if (b != null) {
                WidgetUtil.a((View) aPTitleBar.getTitlebarBg(), b);
            }
            Drawable b2 = b((String) "TITLE_BAR_BACK_BACKGROUND");
            if (b2 != null) {
                WidgetUtil.a((View) aPTitleBar.getImageBackButton(), b2);
            }
            Drawable b3 = b((String) "TITLE_BAR_BACK_IMG");
            if (b3 != null) {
                aPTitleBar.getImageBackButton().setImageDrawable(b3);
            }
            Drawable b4 = b((String) "TITLE_BAR_LEFTLINE_BACKGROUND");
            if (b4 != null) {
                WidgetUtil.a(aPTitleBar.getLeftLine(), b4);
            }
        }
    }
}
