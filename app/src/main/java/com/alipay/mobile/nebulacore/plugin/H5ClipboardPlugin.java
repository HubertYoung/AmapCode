package com.alipay.mobile.nebulacore.plugin;

import android.net.Uri;
import android.text.ClipboardManager;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5ClipboardPlugin extends H5SimplePlugin {
    public static final String TAG = "H5ClipboardPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.SET_CLIPBOARD);
        filter.addAction(CommonEvents.GET_CLIPBOARD);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.SET_CLIPBOARD.equals(action)) {
            setClipboard(event, bridgeContext);
        } else if (CommonEvents.GET_CLIPBOARD.equals(action)) {
            a(bridgeContext);
        }
        return true;
    }

    public void setClipboard(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject callParam = event.getParam();
        if (callParam == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String text = H5Utils.getString(callParam, (String) "text");
        if (text.startsWith("https://ds.alipay.com/error/securityLink.htm") || text.startsWith("https://ds.alipay.com/error/redirectLink.htm")) {
            text = Uri.parse(text).getQueryParameter("url");
            H5Log.d(TAG, "competitive link special text is " + text);
        }
        ((ClipboardManager) H5Environment.getContext().getSystemService("clipboard")).setText(text);
        bridgeContext.sendSuccess();
    }

    private static void a(H5BridgeContext bridgeContext) {
        String text;
        CharSequence cs = null;
        try {
            cs = ((ClipboardManager) H5Environment.getContext().getSystemService("clipboard")).getText();
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
        if (cs != null) {
            text = cs.toString();
        } else {
            text = "";
        }
        JSONObject data = new JSONObject();
        data.put((String) "text", (Object) text);
        bridgeContext.sendBridgeResult(data);
    }
}
