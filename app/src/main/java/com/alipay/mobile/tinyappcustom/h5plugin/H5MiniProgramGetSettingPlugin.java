package com.alipay.mobile.tinyappcustom.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.tinypermission.H5ApiManagerImpl;
import com.alipay.mobile.tinyappcustom.b.c;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.util.HashMap;
import java.util.Map;

public class H5MiniProgramGetSettingPlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("getSetting");
    }

    public void onRelease() {
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!TextUtils.equals("getSetting", event.getAction())) {
            return false;
        }
        try {
            a(event, context);
        } catch (Throwable e) {
            H5Log.e("H5MiniProgramGetSettingPlugin", "[handleEvent] openSetting Exception: " + e.toString(), e);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        H5Log.d("H5MiniProgramGetSettingPlugin", "[getSetting] enter.");
        H5Page h5Page = (H5Page) event.getTarget();
        final String userId = TinyAppService.get().getUserId();
        if (TextUtils.isEmpty(userId)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            H5Log.d("H5MiniProgramGetSettingPlugin", "[getSetting] userId is null");
            return;
        }
        final String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        if (TextUtils.isEmpty(appId)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            H5Log.d("H5MiniProgramGetSettingPlugin", "[getSetting] userId is null");
            return;
        }
        H5Log.d("H5MiniProgramGetSettingPlugin", "[getSetting] userId = " + userId + ", appId = " + appId);
        final H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            H5Log.d("H5MiniProgramGetSettingPlugin", "getSetting,service is null");
            return;
        }
        final H5BridgeContext h5BridgeContext = bridgeContext;
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public void run() {
                Map permissionsMap = ((H5ApiManagerImpl) h5TinyAppService).getAllPermissions(userId, appId);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put((String) "appId", (Object) appId);
                    jsonObject.put((String) "userId", (Object) userId);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add(jsonObject);
                    JSONObject resultObject = H5Utils.parseObject(c.a("alipay.openservice.mini.authinfo.query", jsonArray.toString(), null, false, new JSONObject(), null, null, null).b());
                    if (resultObject != null && resultObject.containsKey("authorized")) {
                        if (permissionsMap == null) {
                            permissionsMap = new HashMap();
                        }
                        if (resultObject.getBoolean("authorized").booleanValue()) {
                            permissionsMap.put("userInfo", Boolean.valueOf(true));
                        } else {
                            permissionsMap.put("userInfo", Boolean.valueOf(false));
                        }
                    }
                } catch (Throwable e2) {
                    H5Log.e("H5MiniProgramGetSettingPlugin", "[handleEvent] openSetting Exception: " + e2.toString(), e2);
                }
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put((String) "authSetting", (Object) JSONObject.parseObject(JSON.toJSONString(permissionsMap)));
                H5Log.d("H5MiniProgramGetSettingPlugin", "[getSetting] result= " + jsonObject2.toString());
                h5BridgeContext.sendBridgeResult(jsonObject2);
            }
        });
    }
}
