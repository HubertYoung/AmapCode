package com.alipay.mobile.tinyappcommon.h5plugin;

import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5ShowOptionMenuItemPlugin extends H5SimplePlugin {
    public static final String HIDE_DESKTOP_MENU = "hideDesktopMenu";
    public static final String HIDE_FAVORITE_MENU = "hideFavoriteMenu";
    public static final String SHOW_DESKTOP_MENU = "showDesktopMenu";
    public static final String SHOW_FAVORITE_MENU = "showFavoriteMenu";
    private static final String TAG = H5ShowOptionMenuItemPlugin.class.getSimpleName();
    private H5Page h5Page;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("showFavoriteMenu");
        filter.addAction(HIDE_FAVORITE_MENU);
        filter.addAction("showDesktopMenu");
        filter.addAction(HIDE_DESKTOP_MENU);
    }

    public void onRelease() {
        this.h5Page = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (event.getTarget() instanceof H5Page) {
            this.h5Page = (H5Page) event.getTarget();
        }
        if ("showFavoriteMenu".equals(action)) {
            showMenuItem(event, "showFavoriteMenu");
        } else if (HIDE_FAVORITE_MENU.equals(action)) {
            hideMenuItem(context, "showFavoriteMenu");
        } else if ("showDesktopMenu".equals(action)) {
            showMenuItem(event, "showDesktopMenu");
        } else if (HIDE_DESKTOP_MENU.equals(action)) {
            hideMenuItem(context, "showDesktopMenu");
        }
        return true;
    }

    private void showMenuItem(H5Event event, String key) {
        boolean show = H5Utils.getBoolean(event.getParam(), key, true);
        if (show) {
            shouldShowMenuItem(show, key);
        }
    }

    private void hideMenuItem(H5BridgeContext context, String key) {
        shouldShowMenuItem(false, key);
        context.sendSuccess();
    }

    private void shouldShowMenuItem(boolean show, String key) {
        if (this.h5Page != null) {
            H5Log.d(TAG, "shouldShowShare.." + show);
            this.h5Page.setExtra(key, Boolean.valueOf(show));
        }
    }
}
