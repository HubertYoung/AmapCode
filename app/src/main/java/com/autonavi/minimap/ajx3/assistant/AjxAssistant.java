package com.autonavi.minimap.ajx3.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.view.View.OnLongClickListener;
import com.autonavi.minimap.ajx3.assistant.AjxAssistantMenu.OnMenuChangeListener;

public class AjxAssistant {
    public static AjxAssistantMenu menuLayout;

    public static void showMenu(String[] strArr, Activity activity, OnMenuChangeListener onMenuChangeListener) {
        if (activity != null) {
            if (VERSION.SDK_INT < 23 || Settings.canDrawOverlays(activity)) {
                if (menuLayout == null) {
                    AjxAssistantMenu ajxAssistantMenu = new AjxAssistantMenu(activity);
                    menuLayout = ajxAssistantMenu;
                    ajxAssistantMenu.setMenuListener(onMenuChangeListener);
                    menuLayout.addItem(strArr);
                }
                return;
            }
            activity.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"));
        }
    }

    public static boolean isMeunShown() {
        return menuLayout != null;
    }

    public static void dismissMenu() {
        if (menuLayout != null) {
            menuLayout.destroy();
            menuLayout = null;
        }
    }

    public static void setLongClickListener(OnLongClickListener onLongClickListener) {
        if (menuLayout != null) {
            menuLayout.setLongClickListener(onLongClickListener);
        }
    }
}
