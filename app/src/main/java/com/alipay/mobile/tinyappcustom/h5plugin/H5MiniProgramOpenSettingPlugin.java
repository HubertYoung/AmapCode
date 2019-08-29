package com.alipay.mobile.tinyappcustom.h5plugin;

import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcustom.permission.MiniProgramOpenSettingActivity;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class H5MiniProgramOpenSettingPlugin extends H5SimplePlugin {
    public static final String JS_FUNCTION_NAME = "openSetting";
    private static Map<String, H5BridgeContext> a = new HashMap(20);

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(JS_FUNCTION_NAME);
    }

    public void onRelease() {
        H5Log.d("H5MiniProgramOpenSettingPlugin", "[onRelease] enter.");
        a.clear();
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!TextUtils.equals(JS_FUNCTION_NAME, event.getAction())) {
            return false;
        }
        try {
            a(event, context);
        } catch (Throwable e) {
            H5Log.e("H5MiniProgramOpenSettingPlugin", "[handleEvent] openSetting Exception: " + e.toString(), e);
        }
        return true;
    }

    private static void a(H5Event event, H5BridgeContext bridgeContext) {
        H5Log.d("H5MiniProgramOpenSettingPlugin", "[openSetting] enter.");
        H5Page h5Page = (H5Page) event.getTarget();
        String userId = TinyAppService.get().getUserId();
        if (TextUtils.isEmpty(userId)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            H5Log.d("H5MiniProgramOpenSettingPlugin", "[openSetting] userId is null");
            return;
        }
        String appId = H5Utils.getString(event.getParam(), (String) "tinyAppId");
        if (TextUtils.isEmpty(appId)) {
            appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            if (TextUtils.isEmpty(appId)) {
                appId = H5Utils.getString(h5Page.getParams(), (String) "parentAppId");
                if (TextUtils.isEmpty(appId)) {
                    bridgeContext.sendError(event, Error.INVALID_PARAM);
                    H5Log.d("H5MiniProgramOpenSettingPlugin", "[openSetting] userId is null");
                    return;
                }
            }
        }
        H5Log.d("H5MiniProgramOpenSettingPlugin", "[openSetting] userId = " + userId + ", appId = " + appId);
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        Intent intent = new Intent(microApplicationContext.getApplicationContext(), MiniProgramOpenSettingActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("app_id", appId);
        microApplicationContext.startActivity(microApplicationContext.findTopRunningApp(), intent);
        a.put(a(userId, appId), bridgeContext);
        H5Log.d("H5MiniProgramOpenSettingPlugin", "[openSetting] finish.");
    }

    public static void sendOpenSettingResult(String userId, String appId, Map<String, Boolean> userAllSetting) {
        if (TextUtils.isEmpty(userId)) {
            H5Log.w("H5MiniProgramOpenSettingPlugin", "[sendOpenSettingResult] userId maybe empty");
        } else if (TextUtils.isEmpty(appId)) {
            H5Log.w("H5MiniProgramOpenSettingPlugin", "[sendOpenSettingResult] appId maybe empty");
        } else {
            H5BridgeContext h5BridgeContext = null;
            try {
                h5BridgeContext = a.get(a(userId, appId));
                if (h5BridgeContext == null) {
                    H5Log.w("H5MiniProgramOpenSettingPlugin", "[sendOpenSettingResult] h5BridgeContext is null");
                    if (h5BridgeContext != null) {
                        try {
                            a.remove(a(userId, appId));
                        } catch (Throwable e1) {
                            H5Log.e("H5MiniProgramOpenSettingPlugin", "[sendOpenSettingResult] Remove h5BridgeContext Exception: " + e1.toString(), e1);
                        }
                    }
                } else {
                    JSONObject jsonObject = new JSONObject();
                    if (userAllSetting == null || userAllSetting.isEmpty()) {
                        H5Log.w("H5MiniProgramOpenSettingPlugin", "[sendOpenSettingResult] userAllSetting maybe empty");
                        jsonObject.put((String) "authSetting", (Object) new JSONObject());
                        h5BridgeContext.sendBridgeResult(jsonObject);
                        if (h5BridgeContext != null) {
                            try {
                                a.remove(a(userId, appId));
                            } catch (Throwable e12) {
                                H5Log.e("H5MiniProgramOpenSettingPlugin", "[sendOpenSettingResult] Remove h5BridgeContext Exception: " + e12.toString(), e12);
                            }
                        }
                    } else {
                        JSONObject authSettingObject = new JSONObject();
                        for (Entry entry : userAllSetting.entrySet()) {
                            authSettingObject.put((String) entry.getKey(), entry.getValue());
                        }
                        jsonObject.put((String) "authSetting", (Object) authSettingObject);
                        h5BridgeContext.sendBridgeResult(jsonObject);
                        H5Log.d("H5MiniProgramOpenSettingPlugin", "[sendOpenSettingResult] sendBridgeResult finished");
                        if (h5BridgeContext != null) {
                            try {
                                a.remove(a(userId, appId));
                            } catch (Throwable e13) {
                                H5Log.e("H5MiniProgramOpenSettingPlugin", "[sendOpenSettingResult] Remove h5BridgeContext Exception: " + e13.toString(), e13);
                            }
                        }
                    }
                }
            } catch (Throwable e14) {
                H5Log.e("H5MiniProgramOpenSettingPlugin", "[sendOpenSettingResult] Remove h5BridgeContext Exception: " + e14.toString(), e14);
            }
        }
    }

    private static String a(String userId, String appId) {
        return String.format("%s_%s", new Object[]{userId, appId});
    }
}
