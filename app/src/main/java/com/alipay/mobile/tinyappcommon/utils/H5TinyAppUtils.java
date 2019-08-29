package com.alipay.mobile.tinyappcommon.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptor;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptorManager;

public class H5TinyAppUtils {
    public static boolean isRemoteDebugConnected(String appId) {
        if (!TinyAppRemoteDebugInterceptorManager.get().isRemoteDebug()) {
            return false;
        }
        TinyAppRemoteDebugInterceptor interceptor = TinyAppRemoteDebugInterceptorManager.get().getTinyAppRemoteDebugInterceptor();
        if (interceptor != null) {
            return interceptor.isRemoteDebugConnected(appId);
        }
        return false;
    }

    public static boolean isVConsolePanelOpened() {
        if (TinyAppStorage.getInstance().getDebugPanelH5Page() != null) {
            return true;
        }
        return false;
    }

    public static void sendMsgToRemoteWorkerOrVConsole(String appId, String type, String msg) {
        if (isRemoteDebugConnected(appId)) {
            sendMsgToRemoteWorker(type + ":" + msg);
            return;
        }
        H5Page debugH5Page = TinyAppStorage.getInstance().getDebugPanelH5Page();
        if (debugH5Page != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "type", (Object) type);
            jsonObject.put((String) "content", (Object) msg);
            JSONObject consoleData = new JSONObject();
            consoleData.put((String) "data", (Object) jsonObject);
            debugH5Page.getBridge().sendToWeb(H5VConsolePlugin.ON_TINY_DEBUG_CONSOLE, consoleData, null);
        }
    }

    public static void sendMsgToRemoteWorker(String msg) {
        TinyAppRemoteDebugInterceptor interceptor = TinyAppRemoteDebugInterceptorManager.get().getTinyAppRemoteDebugInterceptor();
        if (interceptor != null) {
            interceptor.sendMessageToRemoteWorker(msg);
        }
    }
}
