package com.alipay.mobile.nebulacore.pushbiz;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.plugin.H5PushBizWindowReceiver;

public class H5PushBizPlugin extends H5SimplePlugin {
    public static final String pushBizAppId = "20001111";
    public static String pushBizWindowAction = "NEBULANOTIFY_bizWindowCompleted";
    private String a = "pushBizWindow";
    private String b = "h5_bizWindowUrlConfig";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(this.a);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (!this.a.endsWith(event.getAction())) {
            return super.handleEvent(event, bridgeContext);
        }
        String type = H5Utils.getString(event.getParam(), (String) "type");
        if (TextUtils.isEmpty(type)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return true;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            JSONObject jsonObject = H5Utils.parseObject(h5ConfigProvider.getConfig(this.b));
            if (jsonObject != null && !jsonObject.isEmpty()) {
                String url = H5Utils.getString(jsonObject, type);
                if (!TextUtils.isEmpty(url)) {
                    if (!url.endsWith("?")) {
                        url = url + "?";
                    }
                    JSONObject data = H5Utils.getJSONObject(event.getParam(), "data", null);
                    if (data != null && !data.isEmpty()) {
                        for (String key : data.keySet()) {
                            try {
                                url = url + key + "=" + data.get(key).toString() + "&";
                            } catch (Exception e) {
                                H5Log.e((String) "H5PushBizPlugin", (Throwable) e);
                            }
                        }
                    }
                    H5Page h5Page = null;
                    String appId = "";
                    if (event.getTarget() instanceof H5Page) {
                        h5Page = (H5Page) event.getTarget();
                        appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
                    }
                    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Utils.getContext());
                    IntentFilter filter = new IntentFilter();
                    if (!TextUtils.isEmpty(appId) && h5Page != null) {
                        pushBizWindowAction = "NEBULANOTIFY_bizWindowCompleted_" + type + "_" + appId;
                        filter.addAction(pushBizWindowAction);
                        manager.registerReceiver(new H5PushBizWindowReceiver(bridgeContext), filter);
                        H5Log.d("H5PushBizPlugin", "pushBizWindowAction is " + pushBizWindowAction);
                        Bundle param = new Bundle();
                        param.putString("url", url + "appId=" + appId);
                        H5PushBizUtil.setH5BridgeContext(bridgeContext);
                        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, pushBizAppId, param);
                    }
                    return true;
                }
            }
        }
        bridgeContext.sendError(12, (String) "not found config");
        return true;
    }
}
