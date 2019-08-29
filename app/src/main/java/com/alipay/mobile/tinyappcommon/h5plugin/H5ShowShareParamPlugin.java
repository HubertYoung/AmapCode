package com.alipay.mobile.tinyappcommon.h5plugin;

import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.dynamicpanel.H5TinyPopMenu;

public class H5ShowShareParamPlugin extends H5SimplePlugin {
    public static final String HIDE_SHARE_MENU = "hideShareMenu";
    public static final String SET_SHOW_SHARE_MENU = "setShowShareMenu";
    private static final String TAG = H5ShowShareParamPlugin.class.getSimpleName();
    private H5Page h5Page;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(SET_SHOW_SHARE_MENU);
        filter.addAction(HIDE_SHARE_MENU);
    }

    public void onRelease() {
        this.h5Page = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (event.getTarget() instanceof H5Page) {
            this.h5Page = (H5Page) event.getTarget();
        }
        if (SET_SHOW_SHARE_MENU.equals(action)) {
            setShowShareItem(event);
        } else if (HIDE_SHARE_MENU.equals(action)) {
            hideShareMenu(context);
        }
        return true;
    }

    private void setShowShareItem(H5Event event) {
        boolean show = H5Utils.getBoolean(event.getParam(), (String) H5TinyPopMenu.SHOW_SHARE_POP_MENU, true);
        if (TinyAppMiniServicePlugin.appIsMiniService(event.getH5page()) || show) {
            shouldShowShare(show);
        }
    }

    private void shouldShowShare(boolean show) {
        if (this.h5Page != null) {
            H5Log.d(TAG, "shouldShowShare.." + show);
            this.h5Page.setExtra(H5TinyPopMenu.SHOW_SHARE_POP_MENU, Boolean.valueOf(show));
        }
    }

    private void hideShareMenu(H5BridgeContext context) {
        shouldShowShare(false);
        context.sendSuccess();
    }
}
