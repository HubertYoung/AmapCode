package com.alipay.mobile.tinyappcommon.h5plugin;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;

public class H5DebugConsolePlugin extends H5SimplePlugin {
    private static final String ON_MESSAGE_FROM_VCONSOLE = "onMessageFromVConsole";
    private static final String SEND_MESSAGE_FROM_VCONSOLE_TO_APPX = "sendMsgFromVConsoleToAppx";
    /* access modifiers changed from: private */
    public static final String TAG = H5DebugConsolePlugin.class.getSimpleName();
    private static final String TINY_DEBUG_CONSOLE = "tinyDebugConsole";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(TINY_DEBUG_CONSOLE);
        filter.addAction(SEND_MESSAGE_FROM_VCONSOLE_TO_APPX);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (TINY_DEBUG_CONSOLE.equals(event.getAction())) {
            tinyDebugConsole(event);
        } else if (SEND_MESSAGE_FROM_VCONSOLE_TO_APPX.equals(event.getAction())) {
            sendMessageFromVConsoleToAppx(event);
        }
        return true;
    }

    private void sendMessageFromVConsoleToAppx(final H5Event event) {
        H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
            public void run() {
                try {
                    String data = H5Utils.getString(event.getParam(), (String) "data");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put((String) "content", (Object) data);
                    H5Page topH5Page = TinyappUtils.getTopH5Page();
                    if (topH5Page != null) {
                        topH5Page.getBridge().sendDataWarpToWeb(H5DebugConsolePlugin.ON_MESSAGE_FROM_VCONSOLE, jsonObject, null);
                    }
                } catch (Exception e) {
                    H5Log.e(H5DebugConsolePlugin.TAG, "exception detail: ", e);
                }
            }
        });
    }

    private void tinyDebugConsole(final H5Event event) {
        H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
            public void run() {
                try {
                    String content = H5Utils.getString(event.getParam(), (String) "content");
                    String consoleType = H5Utils.getString(event.getParam(), (String) "type");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put((String) "type", (Object) consoleType);
                    jsonObject.put((String) "content", (Object) content);
                    JSONObject consoleData = new JSONObject();
                    consoleData.put((String) "data", (Object) jsonObject);
                    H5Page debugH5Page = TinyAppStorage.getInstance().getDebugPanelH5Page();
                    if (debugH5Page != null) {
                        debugH5Page.getBridge().sendToWeb(H5VConsolePlugin.ON_TINY_DEBUG_CONSOLE, consoleData, null);
                    }
                } catch (Exception e) {
                    H5Log.e(H5DebugConsolePlugin.TAG, "exception detail: ", e);
                }
            }
        });
    }
}
