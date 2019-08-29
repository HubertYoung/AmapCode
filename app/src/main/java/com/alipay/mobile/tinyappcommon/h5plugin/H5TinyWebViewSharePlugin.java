package com.alipay.mobile.tinyappcommon.h5plugin;

import android.view.View;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.tinyappcommon.dynamicpanel.H5TinyPopMenu;

public class H5TinyWebViewSharePlugin extends H5SimplePlugin {
    public static final String ACTION_SHARE = "webViewShare";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_SHARE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (ACTION_SHARE.equals(event.getAction())) {
            doWebViewShare(event, context);
        }
        return true;
    }

    private void doWebViewShare(H5Event event, H5BridgeContext context) {
        if (!(event.getTarget() instanceof H5Page)) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5Page h5Page = (H5Page) event.getTarget();
        if (h5Page.getH5TitleBar() == null || h5Page.getH5TitleBar().getContentView() == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        View v = h5Page.getH5TitleBar().getContentView().findViewWithTag(H5TinyPopMenu.TAG_VIEW);
        if (v == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        Object tagObject = v.getTag(H5TinyPopMenu.TAG_VIEW_KEY);
        if (!(tagObject instanceof H5TinyPopMenu)) {
            context.sendError(event, Error.UNKNOWN_ERROR);
        } else {
            ((H5TinyPopMenu) tagObject).fireShareEvent(event, context);
        }
    }
}
