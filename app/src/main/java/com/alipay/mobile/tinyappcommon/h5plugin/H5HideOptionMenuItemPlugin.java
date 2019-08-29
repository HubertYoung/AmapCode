package com.alipay.mobile.tinyappcommon.h5plugin;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.dynamicpanel.H5TinyPopMenu;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;

public class H5HideOptionMenuItemPlugin extends H5SimplePlugin {
    public static final String HIDE_MENU_ITEM = "hideOptionMenuItem";
    private static final String TAG = H5HideOptionMenuItemPlugin.class.getSimpleName();
    private H5Page h5Page;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(HIDE_MENU_ITEM);
    }

    public void onRelease() {
        this.h5Page = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (event.getTarget() instanceof H5Page) {
            this.h5Page = (H5Page) event.getTarget();
        }
        if (HIDE_MENU_ITEM.equals(event.getAction())) {
            JSONObject object = event.getParam();
            String name = object.getString("name");
            String scope = object.getString("scope");
            if ("add2Desktop".equals(name)) {
                if ("all".equals(scope)) {
                    H5TinyPopMenu.shouldShowAdd2Desktop.set(false);
                    context.sendSuccess();
                } else if ("single".equals(scope)) {
                    hideMenuItem(context, "showDesktopMenu");
                }
            } else if ("favorite".equals(name)) {
                if ("all".equals(scope)) {
                    H5TinyPopMenu.shouldShowFavorite.set(false);
                    context.sendSuccess();
                } else if ("single".equals(scope)) {
                    hideMenuItem(context, "showFavoriteMenu");
                }
            } else if ("backToHome".equals(name)) {
                String appId = TinyAppParamUtils.getHostAppId(this.h5Page);
                if ("all".equals(scope)) {
                    if (TextUtils.isEmpty(appId)) {
                        context.sendError(event, Error.UNKNOWN_ERROR);
                    } else {
                        TinyAppStorage.getInstance().setShouldShowBackToHomeInTitleBar(appId, false);
                        context.sendSuccess();
                    }
                } else if ("single".equals(scope)) {
                    hideMenuItem(context, H5TinyPopMenu.SHOW_BACK_TO_HOME_TITLE_BAR);
                }
                try {
                    Intent broadcastHideBackToHome = new Intent(H5TinyPopMenu.HIDE_TITLE_BAR_BACK_TO_HOME);
                    broadcastHideBackToHome.putExtra("appId", appId);
                    LocalBroadcastManager.getInstance(H5Utils.getContext()).sendBroadcast(broadcastHideBackToHome);
                } catch (Throwable e) {
                    H5Log.e(TAG, e);
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void showMenuItem(H5Event event, String key) {
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
            H5Log.d(TAG, "should " + key + "..." + show);
            this.h5Page.setExtra(key, Boolean.valueOf(show));
        }
    }
}
